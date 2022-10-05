package br.edu.ifrn.jc.siarc.rh.servico;

import org.springframework.stereotype.Service;

import br.edu.ifrn.jc.siarc.rh.dominio.AparelhoRepositorio;

@Service
public class AparelhoServico {

	final AparelhoRepositorio aparelhoRepositorio;

	public AparelhoServico(AparelhoRepositorio aparelhoRepositorio) {
		this.aparelhoRepositorio = aparelhoRepositorio;
	}
}
