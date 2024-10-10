package hpclab.ksatengmaker_spring.questionGenerator.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = 678615568L;

    public static final QQuestion question = new QQuestion("question");

    public final hpclab.ksatengmaker_spring.community.domain.QBaseTimeEntity _super = new hpclab.ksatengmaker_spring.community.domain.QBaseTimeEntity(this);

    public final StringPath answer = createString("answer");

    public final ListPath<Choice, QChoice> choices = this.<Choice, QChoice>createList("choices", Choice.class, QChoice.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath explanation = createString("explanation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath mainText = createString("mainText");

    public final NumberPath<Long> shareCounter = createNumber("shareCounter", Long.class);

    public final StringPath title = createString("title");

    public final StringPath translation = createString("translation");

    public final EnumPath<QuestionType> type = createEnum("type", QuestionType.class);

    public QQuestion(String variable) {
        super(Question.class, forVariable(variable));
    }

    public QQuestion(Path<? extends Question> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestion(PathMetadata metadata) {
        super(Question.class, metadata);
    }

}

