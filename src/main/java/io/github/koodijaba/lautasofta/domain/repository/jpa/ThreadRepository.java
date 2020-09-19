package io.github.koodijaba.lautasofta.domain.repository.jpa;

import io.github.koodijaba.lautasofta.domain.Board;
import io.github.koodijaba.lautasofta.domain.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.Instant;

public interface ThreadRepository extends PagingAndSortingRepository<Thread, Integer> {
    Page<Thread> findByBoard(Board board, Pageable pageable);
    Page<Thread> findByCreatedBy(String createdBy, Pageable pageable);
    Page<Thread> findByCreatedByAndModifiedAtBefore(String createdBy, Instant before, Pageable pageable);
    Page<Thread> findByBoardAndModifiedAtIsBefore(Board board, Instant before, Pageable pageable);

}
