package hpclab.ksatengmaker_spring.questionGenerator.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private QuestionType type;

    private String question;
    private String mainText;
    private String choices;
    private String answer;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "explanation_id")
//    private Explanation explanation;
//
//    public void setExplanation(Explanation explanation) {
//        this.explanation = explanation;
//        this.explanation.setQuestion(this);
//    }

    public static Question createQuestion(String question, String mainText, String choices, String answer) {
        Question now = new Question();
        now.question = question;
        now.mainText = mainText;
        now.choices = choices;
        now.answer = answer;

        return now;
    }


}
