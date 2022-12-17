package br.edu.ifrn.jc.siarc.adm.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);

    Usuario findByMatricula(String matricula);

    Usuario getUserByMatricula(String matricula);

    Usuario findByResetPasswordToken(String token);

    @Query("SELECT u FROM Usuario u WHERE u.username like %?1% OR u.email like %?1% OR u.matricula like %?1% OR u.role like %?1%")
    List<Usuario> findAllByNome(String nome);
}
