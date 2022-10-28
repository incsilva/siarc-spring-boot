package br.edu.ifrn.jc.siarc.adm.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);

	Usuario findByEmail(String email);

	Usuario findByMatricula(String matricula);

	Usuario getUserByMatricula(String matricula);

}
