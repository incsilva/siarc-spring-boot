package br.edu.ifrn.jc.siarc.adm.dominio;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String username;

	@Email(message = "Informe um endereço de e-mail válido.")
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String matricula;

	@NotBlank
	@Column(nullable = false)
	@Size(min = 8, message = "A senha deve conter pelo menos 8 dígitos.")
	private String password;

	private String resetPasswordToken;

	@NotBlank
	@Column(nullable = false)
	private String role;

	@Deprecated
	protected Usuario() {
	}

	public Usuario(String username, String email, String matricula, String password) {
		this.username = username;
		this.email = email;
		this.matricula = matricula;
		this.password = password;
		this.role = Role.USER.getNome();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id;
	}
}
