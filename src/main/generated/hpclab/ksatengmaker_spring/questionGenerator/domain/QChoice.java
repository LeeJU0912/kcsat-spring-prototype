package hpclab.ksatengmaker_spring.questionGenerator.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChoice is a Querydsl query type for Choice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoice extends EntityPathBase<Choice> {

    private static final long serialVersionUID = 30637643L;

    public static final QChoice choice1 = new QChoice("choice1");

    public final StringPath choice = createString("choice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChoice(String variable) {
        super(Choice.class, forVariable(variable));
    }

    public QChoice(Path<? extends Choice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChoice(PathMetadata metadata) {
        super(Choice.class, metadata);
    }

}

