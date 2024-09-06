package hpclab.ksatengmaker_spring.questionGenerator.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnswer is a Querydsl query type for Answer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswer extends EntityPathBase<Answer> {

    private static final long serialVersionUID = -20946840L;

    public static final QAnswer answer1 = new QAnswer("answer1");

    public final StringPath answer = createString("answer");

    public final StringPath explanation = createString("explanation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath translation = createString("translation");

    public QAnswer(String variable) {
        super(Answer.class, forVariable(variable));
    }

    public QAnswer(Path<? extends Answer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnswer(PathMetadata metadata) {
        super(Answer.class, metadata);
    }

}

