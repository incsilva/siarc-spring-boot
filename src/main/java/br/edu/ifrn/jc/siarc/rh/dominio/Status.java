package br.edu.ifrn.jc.siarc.rh.dominio;

import java.util.ArrayList;
import java.util.List;

public enum Status {
	ATIVO("ATIVO"), INATIVO("INATIVO");

	private String nome;

	private Status(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	@Override
	public String toString() {
		return getNome();
	}

	public static List<String> getStatus() {
		List<String> statuses = new ArrayList<String>();
		for (Status status : values()) {
			statuses.add(status.getNome());
		}

		return statuses;
	}
}
