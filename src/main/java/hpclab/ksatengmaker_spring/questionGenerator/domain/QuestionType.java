package hpclab.ksatengmaker_spring.questionGenerator.domain;

import lombok.Getter;

@Getter
public enum QuestionType {
    WRONG("내용불일치"),
    GOOD_WORD("옳은낱말선택"),
    BLANK_AB("빈칸A,B"),
    UNMATCH("안맞는문장"),
    ARGUMENT("필자주장"),
    PURPOSE("글의목적"),
    SUMMARY("요약문A,B"),
    BAD_WORD("틀린낱말선택"),
    INFO_UNMATCH("안내문불일치"),
    FEELING("심경파악"),
    ORDERING("글의순서"),
    GRAMMAR("어법"),
    CHART_UNMATCH("표불일치"),
    MATCH("대상에 대한 내용일치"),
    FEELING_CHANGE("심경변화"),
    UNDERLINE("밑줄의미"),
    MAIN_IDEA("글의요지"),
    TITLE("글의제목"),
    INSERT_SENT("문장삽입"),
    BLANK("빈칸"),
    TOPIC("글의주제"),
    OTHER("다른대상"),
    INFO_MATCH("안내문일치");

    private final String krName;

    QuestionType(String krName) {
        this.krName = krName;
    }
}
