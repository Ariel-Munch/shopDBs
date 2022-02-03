package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity


@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueCodeNameAndLang", columnNames = { "codeName", "lang_id" }) })
public class MailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)    
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lang_id", referencedColumnName = "id")
    Lang lang ;

    String codeName;

    @Column(nullable = false)    
    String  subject;
    
    @Lob
    @Column(nullable = false, columnDefinition = "text", length=102400)
    String text;

    String header;
    Boolean htmlBody;

}
