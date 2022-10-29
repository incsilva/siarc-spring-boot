package br.edu.ifrn.jc.siarc.seguranca.servico;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifrn.jc.siarc.adm.dominio.Usuario;
import br.edu.ifrn.jc.siarc.adm.dominio.UsuarioRepositorio;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.getUserByMatricula(matricula);

		if (usuario != null) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority(usuario.getRole()));

			User userSpring = new User(usuario.getUsername(), usuario.getPassword(), authorities);

			return userSpring;
		}
		throw new UsernameNotFoundException("Não encontramos nenhum usuário com a matrícula: " + matricula);
	}

	public void updateResetPasswordToken(String token, String email) throws UsuarioNotFoundException {
		Usuario usuario = usuarioRepo.findByEmail(email);

		if (usuario != null) {
			usuario.setResetPasswordToken(token);
			usuarioRepo.save(usuario);
		} else {
			throw new UsuarioNotFoundException(
					"Nenhum usuário com o email " + email + " foi encontrado. Verifique se está correto.");
		}
	}

	public Usuario getByResetPasswordToken(String token) {
		return usuarioRepo.findByResetPasswordToken(token);
	}

	public void updatePassword(Usuario usuario, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);

		usuario.setPassword(encodedPassword);
		usuario.setResetPasswordToken(null);

		usuarioRepo.save(usuario);
	}

}
