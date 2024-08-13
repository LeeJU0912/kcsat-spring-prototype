package hpclab.ksatengmaker_spring.questionGenerator.domain;

public enum QuestionType {
    PURPOSE("글의목적"),
    MAIN_IDEA("글의요지"),
    TITLE("글의제목"),
    TOPIC("글의주제"),
    OTHER("다른대상"),
    WRONG("내용불일치"),
    MATCH("내용일치"),
    FEELINGS("심경변화"),
    GOOD_WORD("옳은낱말선택"),
    BAD_WORD("틀린낱말선택"),
    INSERT_SENT("문장삽입"),
    UNDERLINE("밑줄의미"),
    BLANK_AB("빈칸A,B"),
    BLANK("빈칸"),
    GRAMMAR("어법"),
    SUMMARY("요약문A,B"),
    ORDERING("글의순서"),
    ARGUMENT("필자주장"),
    UNMATCH("안맞는문장");

    private final String krName;

    QuestionType(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
