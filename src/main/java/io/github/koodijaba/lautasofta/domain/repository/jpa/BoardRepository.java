package io.github.koodijaba.lautasofta.domain.repository.jpa;

import io.github.koodijaba.lautasofta.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, String> {
}
