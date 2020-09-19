package io.github.koodijaba.lautasofta.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Board extends AuditedEntity {
    @Id
    private String name;
}
