package io.github.koodijaba.lautasofta.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.solr.core.mapping.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Post extends AuditedEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 1, max = 20000)
    @NotNull
    @Indexed("content_t")
    private String content;
}
