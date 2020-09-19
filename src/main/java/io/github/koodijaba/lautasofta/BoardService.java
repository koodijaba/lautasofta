package io.github.koodijaba.lautasofta;

import io.github.koodijaba.lautasofta.domain.Board;
import io.github.koodijaba.lautasofta.domain.repository.jpa.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Cacheable("boards")
    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }
}
