package io.github.koodijaba.lautasofta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(indexes = {
        @Index(columnList = "board_id, modified_at DESC")
})
@SolrDocument(collection = "lautasofta")
public class Thread extends Post {
    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("thread")
    private List<Reply> replies;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
