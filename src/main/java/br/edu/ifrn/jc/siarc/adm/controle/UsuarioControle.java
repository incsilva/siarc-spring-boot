package br.edu.ifrn.jc.siarc.adm.controle;

import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifrn.jc.siarc.adm.dominio.Role;
import br.edu.ifrn.jc.siarc.adm.dominio.Usuario;
import br.edu.ifrn.jc.siarc.adm.dominio.UsuarioRepositorio;
import br.edu.ifrn.jc.siarc.adm.dominio.dtos.RoleUsuarioForm;

@Controller
public class UsuarioControle {

    private PasswordEncoder encoder;
    private UsuarioRepositorio usuarioRepo;
    private final List<String> roles;

    public UsuarioControle(UsuarioRepositorio usuarioRepo, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.usuarioRepo = usuarioRepo;
        this.roles = Role.getRoles();
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "/login";
    }

    @GetMapping("/adm/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("listaUsuarios", usuarioRepo.findAll());
        return "adm/usuarios/index";
    }

    @GetMapping("/adm/usuarios/novo")
    public String novoUsuario(Model model) {

        model.addAttribute("usuario", new Usuario("", "", "", ""));
        model.addAttribute("roles", roles);

        return "adm/usuarios/novo";
    }

    @PostMapping("/adm/usuarios/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
            Model model) {

        Usuario usuarioEncontrado = usuarioRepo.findByUsername(usuario.getUsername());
        Usuario emailEncontrado = usuarioRepo.findByEmail(usuario.getEmail());
        Usuario matriculaEncontrada = usuarioRepo.findByMatricula(usuario.getMatricula());

        if (usuarioEncontrado != null && usuarioEncontrado.getId() != usuario.getId()) {
            bindingResult.addError(new FieldError("usuario", "username", "Nome de usu??rio j?? est?? em uso."));
        }

        if (emailEncontrado != null && emailEncontrado.getId() != usuario.getId()) {
            bindingResult.addError(new FieldError("usuario", "email", "O Email informado j?? est?? em uso."));
        }

        if (matriculaEncontrada != null && matriculaEncontrada.getId() != usuario.getId()) {
            bindingResult.addError(new FieldError("usuario", "matricula", "A matr??cula informada j?? est?? em uso."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roles);
            return "adm/usuarios/novo";
        }

        usuario.setPassword(encoder.encode(usuario.getPassword()));

        usuarioRepo.save(usuario);
        return "redirect:/adm/usuarios";
    }

    @GetMapping("/adm/usuarios/{id}")
    public String alterarUsuario(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usu??rio inv??lido.");
        }
        model.addAttribute("usuario", usuarioOpt.get());
        return "adm/usuarios/alterar_usuario";
    }

    @GetMapping("/adm/usuarios/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") long id) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usu??rio inv??lido.");
        }

        usuarioRepo.delete(usuarioOpt.get());
        return "redirect:/adm/usuarios";
    }

    @GetMapping("/adm/usuarios/alterar/role/{id}")
    public String getAlterarPapelUsuario(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usu??rio inv??lido.");
        }

        RoleUsuarioForm roleUsuarioForm = new RoleUsuarioForm(usuarioOpt.get());

        model.addAttribute("roleUsuarioForm", roleUsuarioForm);
        model.addAttribute("roles", roles);

        return "adm/usuarios/alterar_role";
    }

    @PostMapping("/adm/usuarios/alterar/role")
    public String alterarPapelUsuario(@Valid @ModelAttribute("roleUsuarioForm") RoleUsuarioForm roleUsuarioForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roles);
            return "adm/usuarios/alterar_role";
        }

        Usuario usuarioAlterado = usuarioRepo.findById(roleUsuarioForm.getId())
                .orElseThrow(() -> new InvalidParameterException("Usu??rio Inv??lido!"));
        usuarioAlterado.setRole(roleUsuarioForm.getRole());

        usuarioRepo.save(usuarioAlterado);

        return "redirect:/adm/usuarios";
    }

    @GetMapping("/adm/usuarios/busca")
    public String busca(ModelMap model) {
        List<Usuario> users = (List<Usuario>) usuarioRepo.findAll();
        model.addAttribute("users", users);
        return "adm/usuarios/busca";
    }

    @GetMapping("/adm/usuarios/buscar")
    public String buscar(@RequestParam("nome") String nome, ModelMap model) {

        List<Usuario> users = (List<Usuario>) usuarioRepo.findAllByNome(nome);
        model.addAttribute("users", users);
        return "adm/usuarios/busca";
    }

    @GetMapping("/adm/usuarios/info/{id}")
    public String detalharUsuario(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuOpt = usuarioRepo.findById(id);

        if (usuOpt.isEmpty()) {
            throw new IllegalArgumentException("Usu??rio inv??lido.");
        }

        model.addAttribute("usuario", usuOpt.get());

        return "adm/usuarios/info";
    }
}