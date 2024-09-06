package hpclab.ksatengmaker_spring.community.repository;

import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.community.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
