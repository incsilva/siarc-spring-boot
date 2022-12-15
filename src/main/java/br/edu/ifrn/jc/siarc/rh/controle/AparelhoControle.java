package br.edu.ifrn.jc.siarc.rh.controle;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.edu.ifrn.jc.siarc.rh.dominio.Aparelho;
import br.edu.ifrn.jc.siarc.rh.dominio.AparelhoRepositorio;
import br.edu.ifrn.jc.siarc.rh.dominio.Status;

@Controller
public class AparelhoControle {

    private AparelhoRepositorio aparelhoRepo;
    private final List<String> statuses;

    public AparelhoControle(AparelhoRepositorio aparelhoRepo) {
        this.statuses = Status.getStatus();
        this.aparelhoRepo = aparelhoRepo;
    }

    @GetMapping("/sys/aparelhos")
    public String aparelhos(Model model) {
        model.addAttribute("listaAparelhos", aparelhoRepo.findAll());
        return "sys/aparelhos/index";
    }

    @GetMapping("/sys/aparelhos/ativos")
    public String aparelhosLigados(Model model) {
        List<Aparelho> ligados = (List<Aparelho>) aparelhoRepo.findAllAtivos();
        model.addAttribute("ligados", ligados);
        return "sys/aparelhos/ativo";
    }

    @GetMapping("/sys/aparelhos/novo")
    public String novoAparelho(Model model) {

        model.addAttribute("aparelho", new Aparelho(""));
        model.addAttribute("status", statuses);
        return "sys/aparelhos/form";
    }
    
    @GetMapping("/sys/aparelhos/busca")
    public String busca() {
        return "sys/aparelhos/busca";
    }
    
    @GetMapping("/sys/aparelhos/buscar")
    public String buscar(@RequestParam("nome") String nome, ModelMap model) {
        List<Aparelho> encontrados = (List<Aparelho>) aparelhoRepo.findAllByNome(nome);
        model.addAttribute("encontrados", encontrados);
        return "sys/aparelhos/busca";
    }

    @PostMapping("/sys/aparelhos/salvar")
    public String salvarAparelho(@Valid @ModelAttribute("aparelho") Aparelho aparelho, BindingResult bindingResult,
            Model model) {

        Aparelho nomeEncontrado = aparelhoRepo.findByNome(aparelho.getNome());
        Aparelho enderecoipEncontrado = aparelhoRepo.findByEnderecoip(aparelho.getEnderecoip());
        Aparelho enderecomacEncontrado = aparelhoRepo.findByEnderecomac(aparelho.getEnderecomac());

        if (nomeEncontrado != null && nomeEncontrado.getId() != aparelho.getId()) {
            bindingResult.addError(new FieldError("aparelho", "nome", "Este nome de Aparelho já existe."));
        }

        if (enderecoipEncontrado != null && enderecoipEncontrado.getId() != aparelho.getId()) {
            bindingResult.addError(new FieldError("aparelho", "enderecoip", "Este Endereço IP já existe."));
        }

        if (enderecomacEncontrado != null && enderecomacEncontrado.getId() != aparelho.getId()) {
            bindingResult.addError(new FieldError("aparelho", "enderecomac", "Este Endereço Mac já existe."));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", statuses);
            return "sys/aparelhos/form";
        }
        aparelhoRepo.save(aparelho);
        return "redirect:/sys/aparelhos";
    }

    @GetMapping("/sys/aparelhos/{id}")
    public String alterarAparelho(@PathVariable("id") long id, Model model) {
        Optional<Aparelho> aparelhoOpt = aparelhoRepo.findById(id);
        if (!aparelhoOpt.isPresent()) {
            throw new IllegalArgumentException("Aparelho inválido.");
        }
        model.addAttribute("aparelho", aparelhoOpt.get());
        return "sys/aparelhos/form";
    }

    @GetMapping("/sys/aparelhos/excluir/{id}")
    public String excluirAparelho(@PathVariable("id") long id) {
        Optional<Aparelho> aparelhoOpt = aparelhoRepo.findById(id);
        if (aparelhoOpt.isEmpty()) {
            throw new IllegalArgumentException("Aparelho inválido.");
        }
        aparelhoRepo.delete(aparelhoOpt.get());
        return "redirect:/sys/aparelhos";
    }

    @GetMapping("/sys/aparelhos/info/{id}")
    public String detalharAparelho(@PathVariable("id") long id, Model model) {
        Optional<Aparelho> aparelhoOpt = aparelhoRepo.findById(id);
        if (aparelhoOpt.isEmpty()) {
            throw new IllegalArgumentException("Aparelho inválido.");
        }
        model.addAttribute("aparelho", aparelhoOpt.get());
        return "sys/aparelhos/info";
    }
}
