package io.github.koodijaba.lautasofta.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.solr.core.mapping.Indexed;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.Instant;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Post extends AuditedEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(max = 20000)
    @Indexed("content_t")
    private String content;

}
