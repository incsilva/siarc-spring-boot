package br.edu.ifrn.jc.siarc.adm.dominio.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ifrn.jc.siarc.adm.dominio.Usuario;

public class RoleUsuarioForm {

    private String username;
    private String email;
    private String matricula;
    @NotNull
    private long id;
    @NotBlank
    private String role;

    public RoleUsuarioForm() {
    }

    public RoleUsuarioForm(Usuario usuario) {
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.matricula = usuario.getMatricula();
        this.id = usuario.getId();
        this.role = usuario.getRole();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
