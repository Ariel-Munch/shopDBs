package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Lang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String lang;
    String name;
    LocalDateTime createdAt ;
    @Column( nullable = false, columnDefinition = "boolean default true")
    boolean fallback;
}
