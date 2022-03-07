package com.guilherme.cursospring.domain;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	
	public Categoria() {
		
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/* O hash cria "atalhos para facilitar a busca. EX: vc vai buscar o nome Guilherme no BD
	 * o hash faz a busca ir em ordem alfabetica, por exemplo, assim, vai ignorar todas as letras
	 * e ir direto pro G, tornando a busca mais rápida.
	 */
	  
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	// o equals compara o valor exato de cada objeto
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
