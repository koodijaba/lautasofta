package io.github.koodijaba.lautasofta;

import io.github.koodijaba.lautasofta.domain.*;
import io.github.koodijaba.lautasofta.domain.Thread;
import io.github.koodijaba.lautasofta.domain.repository.jpa.BoardRepository;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ReplyRepository;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ThreadRepository;
import io.github.koodijaba.lautasofta.domain.repository.search.ThreadSearchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = BoardRepository.class)
@EnableSolrRepositories(basePackageClasses = ThreadSearchRepository.class)
@EnableCaching
public class LautasoftaApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(LautasoftaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(authentication -> {
					if (authentication instanceof AnonymousAuthenticationToken) {
						return (String) authentication.getPrincipal();
					}
					return ((User) authentication.getPrincipal()).getUsername();
				});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll();
	}

	@Bean
	public CommandLineRunner runner(BoardRepository boardRepository, ThreadRepository threadRepository, ReplyRepository replyRepository) {
		Board board = new Board();
		board.setName("satunnainen");
		Board board2 = new Board();
		board2.setName("anime");

		return args -> {
			boardRepository.saveAll(List.of(board, board2));
			IntStream.range(1, 5)
					.parallel()
					.forEach(i -> {
						Thread thread = new Thread();
						thread.setBoard(board);
						thread.setContent("foo " + i);
						threadRepository.save(thread);
						IntStream.range(1, 5)
								.parallel()
								.forEach(j -> {
									Reply reply = new Reply();
									reply.setContent("Some reply " + j);
									reply.setThread(thread);
									replyRepository.save(reply);
								});
					});

		};
	}
}
