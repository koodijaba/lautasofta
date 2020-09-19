package io.github.koodijaba.lautasofta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Reply extends Post {
    @ManyToOne(optional = false)
    @ToString.Exclude
    @JsonIgnoreProperties("replies")
    private Thread thread;
}
