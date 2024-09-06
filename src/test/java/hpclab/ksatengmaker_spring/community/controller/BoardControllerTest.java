package hpclab.ksatengmaker_spring.community.controller;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.community.repository.CommentRepository;
import hpclab.ksatengmaker_spring.community.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardControllerTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void board() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Post> result = postRepository.findAll(pageable);
        System.out.println(result);
    }
}