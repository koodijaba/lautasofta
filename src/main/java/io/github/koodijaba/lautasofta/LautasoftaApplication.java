package io.github.koodijaba.lautasofta;

import io.github.koodijaba.lautasofta.domain.Board;
import io.github.koodijaba.lautasofta.domain.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LautasoftaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LautasoftaApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BoardRepository boardRepository) {
		Board board = new Board();
		board.setName("foo");
		return args -> boardRepository.save(board);
	}
}
