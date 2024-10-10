package hpclab.ksatengmaker_spring.myBook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookQuestion is a Querydsl query type for BookQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookQuestion extends EntityPathBase<BookQuestion> {

    private static final long serialVersionUID = 1237094111L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookQuestion bookQuestion = new QBookQuestion("bookQuestion");

    public final hpclab.ksatengmaker_spring.community.domain.QBaseTimeEntity _super = new hpclab.ksatengmaker_spring.community.domain.QBaseTimeEntity(this);

    public final QBook book;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final hpclab.ksatengmaker_spring.questionGenerator.domain.QQuestion question;

    public QBookQuestion(String variable) {
        this(BookQuestion.class, forVariable(variable), INITS);
    }

    public QBookQuestion(Path<? extends BookQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookQuestion(PathMetadata metadata, PathInits inits) {
        this(BookQuestion.class, metadata, inits);
    }

    public QBookQuestion(Class<? extends BookQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBook(forProperty("book")) : null;
        this.question = inits.isInitialized("question") ? new hpclab.ksatengmaker_spring.questionGenerator.domain.QQuestion(forProperty("question")) : null;
    }

}

