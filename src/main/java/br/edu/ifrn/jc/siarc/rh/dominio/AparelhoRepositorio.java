package br.edu.ifrn.jc.siarc.rh.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AparelhoRepositorio extends JpaRepository<Aparelho, Long> {

    Aparelho findByNome(String nome);

    Aparelho findByEnderecoip(String enderecoip);

    Aparelho findByEnderecomac(String enderecomac);

    @Query("SELECT a FROM Aparelho a WHERE a.status = 'ATIVO'")
    List<Aparelho> findAllAtivos();

    @Query("SELECT a FROM Aparelho a WHERE a.local like %?1% OR a.nome like %?1%")
    List<Aparelho> findAllByNome(String nome);

    @Query("SELECT a FROM Aparelho a WHERE a.status = 'INATIVO'")
    List<Aparelho> findAllInativos();

} 