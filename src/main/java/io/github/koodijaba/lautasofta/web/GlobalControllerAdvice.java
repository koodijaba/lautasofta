package io.github.koodijaba.lautasofta.web;

import io.github.koodijaba.lautasofta.BoardService;
import io.github.koodijaba.lautasofta.domain.Board;
import io.github.koodijaba.lautasofta.domain.repository.jpa.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final BoardService boardService;

    @Autowired
    public GlobalControllerAdvice(BoardService boardService) {
        this.boardService = boardService;
    }

    @ModelAttribute("boards")
    public Iterable<Board> boards() {
        return boardService.findAll();
    }
}
