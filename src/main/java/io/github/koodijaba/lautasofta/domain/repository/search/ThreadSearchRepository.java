package io.github.koodijaba.lautasofta.domain.repository.search;

import io.github.koodijaba.lautasofta.domain.Thread;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ThreadSearchRepository extends SolrCrudRepository<Thread, Integer> {
}
