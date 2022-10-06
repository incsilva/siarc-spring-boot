package br.edu.ifrn.jc.siarc.rh.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public String novoAparelho(@Valid @ModelAttribute("aparelho") Aparelho aparelho, BindingResult bindingResult,
			Model model) {

		Aparelho aparelhoEncontrado = aparelhoRepo.findByNome(aparelho.getNome());

		if (aparelhoEncontrado != null && aparelhoEncontrado.getId() != aparelho.getId()) {
			bindingResult.addError(new FieldError("aparelho", "nome", "Nome de aparelho já está em uso."));
		}

		model.addAttribute("aparelho", new Aparelho(""));
		model.addAttribute("status", statuses);
		return "sys/aparelhos/form";
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

	@PostMapping("/sys/aparelhos/salvar")
	public String salvarAparelho(@Valid @ModelAttribute("aparelho") Aparelho aparelho, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("statuses", statuses);
			return "sys/aparelhos/form";
		}
		aparelhoRepo.save(aparelho);
		return "redirect:/sys/aparelhos";
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
}
