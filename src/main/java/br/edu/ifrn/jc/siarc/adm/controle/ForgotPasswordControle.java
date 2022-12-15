package br.edu.ifrn.jc.siarc.adm.controle;

import java.io.UnsupportedEncodingException;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.ifrn.jc.siarc.adm.dominio.Usuario;
import br.edu.ifrn.jc.siarc.seguranca.servico.UserDetailsServiceImpl;
import br.edu.ifrn.jc.siarc.seguranca.servico.UsuarioNotFoundException;
import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordControle {

	@Autowired
	private UserDetailsServiceImpl usuarioService;

//	@Autowired
//	private JavaMailSender mailSender;

	@GetMapping("/forgotPassword")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Esqueceu Senha");
		return "forgotPasswordForm";
	}
/* 
	@PostMapping("/forgotPassword")
	public String processForgotPasswordForm(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(45);

		try {
			usuarioService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/resetPassword?token=" + token;

			sendEmail(email, resetPasswordLink);

			model.addAttribute("message",
					"Enviamos um link para seu email com as instruções para realizar a mudança de senha.");
		} catch (UsuarioNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Erro ao enviar email.");
		}

		return "forgotPasswordForm";
	}

	private void sendEmail(String recipientEmail, String resetPasswordLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contato@siarc.com", "Suporte do SIARC");
		helper.setTo(recipientEmail);

		String subject = "Redefinição de Senha";

		String content = "<p>Olá,</p>" + "<p>Recebemos uma solicitação para redefinir a senha associada a este endereço de email.</p>"
				+ "<p>Clique no link abaixo para criar uma nova senha:</p>" + "<p><b><a href=\"" + resetPasswordLink
				+ "\">Modificar minha senha</a><b></p>"
				+ "<b><b><p>Se não foi você que solicitou a alteração de senha apenas ignore este e-mail.</p>";

		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}
*/
	@GetMapping("/resetPassword")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		Usuario usuario = usuarioService.getByResetPasswordToken(token);
		model.addAttribute("token", token);

		if (usuario == null) {
			model.addAttribute("title", "Redefinição de senha.");
			model.addAttribute("message", "Token inválido.");
			return "message";
		}
		
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Redefinição de senha.");

		return "resetPasswordForm";
	}

	@PostMapping("/resetPassword")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		Usuario usuario = usuarioService.getByResetPasswordToken(token);

		if (usuario == null) {
			model.addAttribute("title", "<h1>Redefinição de senha/h1>");
			model.addAttribute("message", "Token inválido.");
			return "message";
		} else {
			usuarioService.updatePassword(usuario, password);

			model.addAttribute("message", "Ótimo! Sua senha foi alterada com sucesso.");
		}

		return "message";
	}
}
