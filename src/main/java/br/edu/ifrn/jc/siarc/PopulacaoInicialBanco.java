package br.edu.ifrn.jc.siarc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifrn.jc.siarc.adm.dominio.Role;
import br.edu.ifrn.jc.siarc.adm.dominio.Usuario;
import br.edu.ifrn.jc.siarc.adm.dominio.UsuarioRepositorio;
import br.edu.ifrn.jc.siarc.rh.dominio.Aparelho;
import br.edu.ifrn.jc.siarc.rh.dominio.AparelhoRepositorio;
import br.edu.ifrn.jc.siarc.rh.dominio.Status;

@Component
@Transactional
public class PopulacaoInicialBanco implements CommandLineRunner {

	@Autowired
	private AparelhoRepositorio aparelhoRepo;

	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		Aparelho a1 = new Aparelho("Aparelho 01");
		a1.setEnderecoip("121tttt2");
		a1.setEnderecomac("12912");
		a1.setLocal("Coaes");
		a1.setStatus(Status.INATIVO.getNome());
		a1.setCodigoirligar("On");
		a1.setCodigoirdesligar("Off");

		Aparelho a2 = new Aparelho("Aparelho 02");
		a2.setEnderecoip("212yyyy");
		a2.setEnderecomac("2129");
		a2.setLocal("Coaes");
		a2.setStatus(Status.INATIVO.getNome());
		a2.setCodigoirligar("On");
		a2.setCodigoirdesligar("Off");

		Aparelho a3 = new Aparelho("Aparelho 03");
		a3.setEnderecoip("122");
		a3.setEnderecomac("12ttt2");
		a3.setLocal("Coaes");
		a3.setStatus(Status.ATIVO.getNome());
		a3.setCodigoirligar("On");
		a3.setCodigoirdesligar("Off");

		aparelhoRepo.save(a1);
		aparelhoRepo.save(a2);
		aparelhoRepo.save(a3);

		Usuario u1 = new Usuario("alberi", "alberi@gmail.com", "2019106", passwordEncoder.encode("12345678"));
		u1.setRole(Role.ADMIN.getNome());

		Usuario u2 = new Usuario("user", "user@gmail.com", "2018106", passwordEncoder.encode("12345678"));

		usuarioRepo.save(u1);
		usuarioRepo.save(u2);
	}
}
