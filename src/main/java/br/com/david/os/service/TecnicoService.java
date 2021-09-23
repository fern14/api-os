package br.com.david.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.david.os.domain.Pessoa;
import br.com.david.os.domain.Tecnico;
import br.com.david.os.dtos.TecnicoDto;
import br.com.david.os.repository.PessoaRepository;
import br.com.david.os.repository.TecnicoRepository;
import br.com.david.os.service.exceptions.DataIntegratyViolationException;
import br.com.david.os.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Long id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));

	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDto tecnicoDTO) {
		if (findByCPF(tecnicoDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		return repository.save(new Tecnico(null, tecnicoDTO.getNome(), tecnicoDTO.getCpf(), tecnicoDTO.getTelefone()));
	}

	public Tecnico update(Long id, @Valid TecnicoDto objDTO) {
		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	public void delete(Long id) {
		Tecnico obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço, não pode ser deletado!");
		}
		repository.deleteById(id);

	}

	private Pessoa findByCPF(TecnicoDto tecnicoDto) {
		Pessoa obj = pessoaRepository.findByCPF(tecnicoDto.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
