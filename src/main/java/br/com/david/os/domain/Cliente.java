package br.com.david.os.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Cliente extends Pessoa implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cliente")
	private List<OS> List = new ArrayList<>();
	
	public Cliente() {
		super();
	}

	public Cliente(Long id, String nome, String cpf, String telefone) {
		super(id, nome, cpf, telefone);
	}

	public List<OS> getList() {
		return List;
	}

	public void setList(List<OS> list) {
		List = list;
	}
	
	

}
