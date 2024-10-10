package hpclab.ksatengmaker_spring.community.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -672370394L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final ListPath<Comment, QComment> comment = this.<Comment, QComment>createList("comment", Comment.class, QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath isHotPost = createBoolean("isHotPost");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final NumberPath<Long> pId = createNumber("pId", Long.class);

    public final StringPath postContent = createString("postContent");

    public final StringPath postTitle = createString("postTitle");

    public final hpclab.ksatengmaker_spring.questionGenerator.domain.QQuestion question;

    public final EnumPath<hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType> questionType = createEnum("questionType", hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.question = inits.isInitialized("question") ? new hpclab.ksatengmaker_spring.questionGenerator.domain.QQuestion(forProperty("question")) : null;
    }

}

