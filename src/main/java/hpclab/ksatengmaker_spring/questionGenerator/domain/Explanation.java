package hpclab.ksatengmaker_spring.questionGenerator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Explanation {

    @Id
    @GeneratedValue
    @Column(name = "explanation_id")
    private Long id;

    private String translation;
    private String explanation;

//    @OneToOne(mappedBy = "explanation", fetch = FetchType.LAZY)
//    private Question question;
}