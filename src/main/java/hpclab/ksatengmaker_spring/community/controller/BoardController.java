package hpclab.ksatengmaker_spring.community.controller;

import hpclab.ksatengmaker_spring.community.dto.*;
import hpclab.ksatengmaker_spring.community.service.*;
import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.myBook.service.BookService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    /**
     * 기능 구현 :
     * 1. 게시판 나타내기
     * 2. 게시글 검색
     * 3. 게시글 누르면 게시글 항목 표시
     * 4. 게시글 작성
     * 5. 댓글 작성
     * 6. 좋아요, 스크랩 버튼
     */

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final QuestionService questionService;
    private final BookQuestionService bookQuestionService;
    private final QuestionJPARepository questionJPARepository;
    private final BookService bookService;


    @GetMapping("/board")
    public String board(Model model, @PageableDefault(size = 10, sort = "pId", direction = Sort.Direction.DESC) Pageable pageable, String keyword, QuestionType type) {

        /**
         * 기능 구현 :
         * 1. 한 페이지 당 10개의 게시물 보이기 (게시글 제목, 본문 프리뷰)
         * 2.
         */

        model.addAttribute("questionType", questionService.questionTypeList());

        if ((keyword == null || keyword.isEmpty()) && type == null) {
            model.addAttribute("boardList", postService.getPostList(pageable));
        }
        else {
            model.addAttribute("boardList", postService.getFindPostList(pageable, keyword, type));
        }

        return "board/boardForm";
    }

    @GetMapping("/board/{id}")
    public String board(@PathVariable Long id, Model model) {
        String counter = postService.increasePostCount(id);
        PostDto post = postService.getPost(id);
        List<CommentResponseForm> comments = commentService.getComments(id);

        List<String> commentsLikeCounter = new ArrayList<>();

        for (CommentResponseForm comment : comments) {
            String commentCount = commentService.getCommentCount(comment.getCId());
            commentsLikeCounter.add(commentCount);
        }

        model.addAttribute("comments", comments);
        model.addAttribute("commentsLikeCounter", commentsLikeCounter);
        model.addAttribute("counter", counter);
        model.addAttribute("post", post);
        model.addAttribute("id", id);

        return "board/postDetail";
    }

    @GetMapping("/board/{id}/update")
    public String updateBoardForm(@PathVariable Long id, Model model, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        PostDto post = postService.getPost(id);

        if (!Objects.equals(userDetails.getUsername(), post.getEmail())) {
            return "redirect:/board";
        }

        model.addAttribute("post", post);
        model.addAttribute("id", id);

        return "board/postUpdate";
    }

    @PostMapping("/board/{id}/update")
    public String updateBoard(@PathVariable Long id, PostWriteForm form) {
        postService.updatePost(id, form);

        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/remove")
    public String removeBoard(@PathVariable Long id, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        PostDto post = postService.getPost(id);

        if (!Objects.equals(userDetails.getUsername(), post.getEmail())) {
            return "redirect:/board";
        }

        postService.removePost(id);

        return "redirect:/board";
    }

    /**
     *
     * @param id Post ID
     * @return Post에 걸려있던 Question 저장.
     */
    @GetMapping("/board/{id}/save")
    public String saveQuestionFromPost(@PathVariable Long id) {

        postService.saveQuestionFromPost(id);

        return "redirect:/board/{id}";
    }

    @GetMapping("/board/new")
    public String writePost(@ModelAttribute QuestionResponseForm question, Model model) {

        model.addAttribute("question", question);

        return "board/postForm";
    }

    @PostMapping("/board/new")
    public String submitPost(PostWriteForm form, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long pId = postService.savePost(form, userDetails.getUsername());
        postService.setPostCount(pId);

        return "redirect:/board";
    }

    @GetMapping("/board/myQuestions")
    public String getUserQuestions(Model model, Authentication auth) {
        Book book = bookService.findBook(((UserDetails) auth.getPrincipal()).getUsername());

        List<BookQuestion> bookQuestions = book.getBookQuestions();

        model.addAttribute("bookQuestions", bookQuestions);


        return "/board/postQuestionSelectForm";
    }

    @PostMapping("/board/uploadQuestion")
    public String uploadUserQuestion(@RequestParam Long qId, Model model) {

        System.out.println("qId = " + qId);

        Question question = questionJPARepository.findById(qId).orElseThrow(() -> new UsernameNotFoundException("찾는 문제가 존재하지 않습니다."));

        QuestionResponseForm result = QuestionResponseForm.builder()
                .qId(question.getId())
                .questionType(question.getType())
                .title(question.getTitle())
                .mainText(question.getMainText())
                .choices(question.getChoices().stream().map(Choice::getChoice).toList())
                .shareCounter(question.getShareCounter())
                .build();

        model.addAttribute("question", result);

        return "/board/postForm";
    }



    @PostMapping("/board/{id}/comment")
    public String writeComment(@PathVariable Long id, CommentWriteForm form, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Long cId = commentService.writeComment(form, id, userDetails.getUsername());

        commentService.setCommentCount(cId);

        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/comment/{cId}/commentRecommend")
    public String recommendComment(@PathVariable Long id, @PathVariable Long cId) {
        commentService.increaseCommentCount(cId);

        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/comment/{commentId}/remove")
    public String removeComment(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return "redirect:/board/" + id;
    }
}