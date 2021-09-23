package br.com.david.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.david.os.domain.Cliente;
import br.com.david.os.domain.Pessoa;
import br.com.david.os.dtos.ClienteDto;
import br.com.david.os.repository.ClienteRepository;
import br.com.david.os.repository.PessoaRepository;
import br.com.david.os.service.exceptions.DataIntegratyViolationException;
import br.com.david.os.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));

	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDto clienteDTO) {
		if (findByCPF(clienteDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		return repository.save(new Cliente(null, clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone()));
	}

	public Cliente update(Long id, @Valid ClienteDto objDTO) {
		Cliente oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	public void delete(Long id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui ordens de serviço, não pode ser deletado!");
		}
		repository.deleteById(id);

	}

	private Pessoa findByCPF(ClienteDto clienteDTO) {
		Pessoa obj = pessoaRepository.findByCPF(clienteDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
