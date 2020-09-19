package io.github.koodijaba.lautasofta.domain.repository.jpa;

import io.github.koodijaba.lautasofta.domain.Reply;
import io.github.koodijaba.lautasofta.domain.Thread;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ReplyRepository extends PagingAndSortingRepository<Reply, Integer> {
    List<Reply> findByThread(Thread thread, Sort sort);
    List<Reply> findByCreatedBy(String createdBy);
}
