package io.github.koodijaba.lautasofta.web;

import io.github.koodijaba.lautasofta.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BoardRepository boardRepository;

    @Autowired
    public HomeController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "index";
    }
}
