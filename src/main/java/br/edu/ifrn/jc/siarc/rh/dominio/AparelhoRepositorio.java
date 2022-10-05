package br.edu.ifrn.jc.siarc.rh.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AparelhoRepositorio extends JpaRepository<Aparelho, Long> {

	Aparelho findByNome(String nome);

	@Query(value = "select a from Aparelho a where a.nome like %?1%")
	List<Aparelho> findAllAtivos(String nome);

}