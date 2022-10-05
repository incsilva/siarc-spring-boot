package br.edu.ifrn.jc.siarc.rh.dominio.dtos;

public class AparelhoDto {

	private String nome;
	private String local;
	private String status;

	public AparelhoDto(String nome, String local, String status) {
		this.nome = nome;
		this.local = local;
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
