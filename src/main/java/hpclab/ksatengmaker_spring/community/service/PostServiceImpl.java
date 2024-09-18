package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.community.dto.PostDto;
import hpclab.ksatengmaker_spring.community.dto.PostWriteForm;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.community.repository.PostRepository;
import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final QuestionJPARepository questionJPARepository;
    private final BookQuestionService bookQuestionService;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long savePost(PostWriteForm postWriteForm, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Post result;

        if (postWriteForm.getQId() == null) {
            result = Post.builder()
                    .postTitle(postWriteForm.getTitle())
                    .postContent(postWriteForm.getContent())
                    .member(member)
                    .build();
        }
        else {
            Question question = questionJPARepository.findById(postWriteForm.getQId()).orElseThrow(() -> new IllegalArgumentException("질문이 존재하지 않습니다."));

            result = Post.builder()
                    .postTitle(postWriteForm.getTitle())
                    .postContent(postWriteForm.getContent())
                    .questionType(question.getType())
                    .question(question)
                    .member(member)
                    .build();
        }

        postRepository.save(result);

        return result.getPId();
    }

    @Override
    public void saveQuestionFromPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UsernameNotFoundException("post not found"));

        Question question = post.getQuestion();
        question.upShareCounter();
        bookQuestionService.saveQuestion(question);
        questionJPARepository.save(question);
    }

    @Override
    public Page<PostDto> getPostList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return makePostPageDTO(pageable, posts);
    }

    @Override
    public Page<PostDto> getFindPostList(Pageable pageable, String keyword, QuestionType type) {
        Page<Post> posts = postRepository.findPostsByQuestionTypeAndTitle(pageable, keyword, type);
        return makePostPageDTO(pageable, posts);
    }

    @Override
    public Page<PostDto> getHotPostList(Pageable pageable) {
        Page<Post> hotPosts = postRepository.findHotPosts(pageable);
        return makePostPageDTO(pageable, hotPosts);
    }

    @Override
    public Page<PostDto> getFindHotPostList(Pageable pageable, String keyword, QuestionType type) {
        Page<Post> posts = postRepository.findHotPostsByQuestionTypeAndTitle(pageable, keyword, type);
        return makePostPageDTO(pageable, posts);
    }

    private Page<PostDto> makePostPageDTO(Pageable pageable, Page<Post> posts) {
        List<PostDto> postDto = new ArrayList<>();

        for (Post post : posts) {
            postDto.add(new PostDto(post));
        }

        return new PageImpl<>(postDto, pageable, posts.getTotalElements());
    }

    @Override
    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("찾는 게시글이 없습니다."));
        return new PostDto(post);
    }

    @Override
    public Long updatePost(Long postId, PostWriteForm postWriteForm) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.update(postWriteForm.getTitle(), postWriteForm.getContent());

        return post.getPId();
    }

    @Override
    public void removePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postRepository.delete(post);
    }

    @Override
    public String setPostCount(Long postId) {

        String viewCount = "post:viewCount:" + postId;
        String upVote = "post:upVote:" + postId;
        String downVote = "post:downVote:" + postId;

        redisTemplate.opsForValue().set(viewCount, "0");
        redisTemplate.opsForValue().set(upVote, "0");
        redisTemplate.opsForValue().set(downVote, "0");

        return redisTemplate.opsForValue().get(viewCount);
    }

    @Override
    public String increasePostViewCount(Long postId) {
        String pId = "post:viewCount:" + postId;

        String user = "post:userView:" + postId + ":" + getUserEmail();

        System.out.println(postId + user);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(user))) {
            // 예외 발생
        }
        else {
            redisTemplate.opsForValue().increment(pId);
            redisTemplate.opsForValue().set(user, "1");
        }

        return redisTemplate.opsForValue().get(pId);
    }

    @Override
    public String getPostViewCount(Long postId) {
        return redisTemplate.opsForValue().get("post:viewCount:" + postId);
    }

    @Override
    public String increasePostVoteCount(Long postId) {

        String upVote = "post:upVote:" + postId;
        String user = "post:userVote:" + postId + ":" + getUserEmail();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(user))) {
            // 예외 발생
        }
        else {
            redisTemplate.opsForValue().increment(upVote);
            redisTemplate.opsForValue().set(user, "1");
        }

        String nowVote = redisTemplate.opsForValue().get(upVote);

        if (Integer.parseInt(nowVote) >= 1) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("핫게 등록 실패."));

            post.gettingHot();

            postRepository.save(post);
        }

        return nowVote;
    }


    @Override
    public String decreasePostVoteCount(Long postId) {

        String downVote = "post:downVote:" + postId;
        String user = "post:userVote:" + postId + ":" + getUserEmail();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(user))) {
            // 예외 발생
        }
        else {
            redisTemplate.opsForValue().increment(downVote);
            redisTemplate.opsForValue().set(user, "1");
        }

        return redisTemplate.opsForValue().get(downVote);
    }

    @Override
    public String getIncreasePostVoteCount(Long postId) {
        return redisTemplate.opsForValue().get("post:upVote:" + postId);
    }

    @Override
    public String getDecreasePostVoteCount(Long postId) {
        return redisTemplate.opsForValue().get("post:downVote:" + postId);
    }

    private static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    @Scheduled(cron = "0 0 * * *", zone = "Asia/Seoul") // 매일 자정에 실행
    public void resetPostView() {
        log.info("cron reset post userViewChk");

        ScanOptions scanOptions = ScanOptions.scanOptions().match("post:userView:*").count(1000).build();

        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while(keys.hasNext()) {
            String key = new String(keys.next());
            redisTemplate.delete(key);
        }
    }
}