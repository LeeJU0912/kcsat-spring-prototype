package hpclab.ksatengmaker_spring.questionGenerator.domain;

import lombok.Getter;

@Getter
public enum QuestionType {
    BLANK_AB("빈칸A,B"),
    ARGUMENT("필자주장"),
    PURPOSE("글의목적"),
    INFO_UNMATCH("안내문불일치"),
    ORDERING("글의순서"),
    FEELING_CHANGE("심경변화"),
    TARGET_UNMATCH("대상정보불일"),
    MAIN_IDEA("글의요지"),
    TITLE("글의제목"),
    INSERT_SENT("문장삽입"),
    BLANK("빈칸"),
    TOPIC("글의주제"),
    INFO_MATCH("안내문일치");

    private final String krName;

    QuestionType(String krName) {
        this.krName = krName;
    }
}
