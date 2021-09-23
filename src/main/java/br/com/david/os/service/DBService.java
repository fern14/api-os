package br.com.david.os.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.david.os.domain.Cliente;
import br.com.david.os.domain.OS;
import br.com.david.os.domain.Tecnico;
import br.com.david.os.enuns.Prioridade;
import br.com.david.os.enuns.Status;
import br.com.david.os.repository.ClienteRepository;
import br.com.david.os.repository.OSRepository;
import br.com.david.os.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OSRepository osRepository;

	public void instanciaDb() {

		Tecnico t1 = new Tecnico(null, "David Fernandes", "426.226.860-80", "(28) 99999-9999");
		Cliente c1 = new Cliente(null, "Maria Fernandes", "649.714.490-00", "(27) 88888-8888");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}

}
