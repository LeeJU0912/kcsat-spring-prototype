package hpclab.ksatengmaker_spring.community.repository;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import hpclab.ksatengmaker_spring.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
