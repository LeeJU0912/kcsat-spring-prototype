package hpclab.ksatengmaker_spring.questionGenerator.domain;

import hpclab.ksatengmaker_spring.community.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

    private QuestionType type;

    private String title;

    private Long shareCounter;

    @Column(length = 2048)
    private String mainText;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "QUESTION_ID")
    private List<Choice> choices;

    private String answer;

    @Column(length = 2048)
    private String translation;

    @Column(length = 2048)
    private String explanation;

    public void upShareCounter() {
        shareCounter++;
    }
}