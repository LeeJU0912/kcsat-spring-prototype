package hpclab.ksatengmaker_spring.community.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.community.domain.QPost;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findPostsByQuestionTypeAndTitle(Pageable pageable, String title, QuestionType type) {
        QPost post = new QPost("post");

        List<Post> result;

        if (title == null || title.isEmpty()) {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.questionType.eq(type))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        else if (type == null) {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.postTitle.likeIgnoreCase("%" + title + "%"))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        else {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.postTitle.likeIgnoreCase("%" + title + "%").and(post.questionType.eq(type)))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Post> findHotPosts(Pageable pageable) {
        QPost post = new QPost("post");

        List<Post> result = queryFactory.selectFrom(post)
                .where(post.isHotPost.isTrue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageNumber())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Post> findHotPostsByQuestionTypeAndTitle(Pageable pageable, String title, QuestionType type) {
        QPost post = new QPost("post");

        List<Post> result;

        if (title == null || title.isEmpty()) {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.questionType.eq(type).and(post.isHotPost.isTrue()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        else if (type == null) {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.postTitle.likeIgnoreCase("%" + title + "%").and(post.isHotPost.isTrue()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        else {
            result = queryFactory
                    .selectFrom(post)
                    .where(post.postTitle.likeIgnoreCase("%" + title + "%").and(post.questionType.eq(type)).and(post.isHotPost.isTrue()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        return new PageImpl<>(result, pageable, result.size());
    }
}
