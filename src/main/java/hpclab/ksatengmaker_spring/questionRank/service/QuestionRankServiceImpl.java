package hpclab.ksatengmaker_spring.questionRank.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionRankServiceImpl implements QuestionRankService {

    private final RedisTemplate<String, String> redisTemplate;
    private final QuestionJPARepository questionJPARepository;

    private Double redditRankingAlgorithm(double up, LocalDateTime time) {
        double convertedTime = (double) time.atZone(ZoneOffset.UTC).toEpochSecond();
        return log10(up) + sin(up) * convertedTime / 45000;
    }

    @Override
    @Scheduled(cron = "0 * * * *", zone = "Asia/Seoul") // 매 시간마다 실행
    public void updateQuestionRank() {
        log.info("cron update question rank");

        List<Question> questions = questionJPARepository.findAll();

        questions.sort((o1, o2) -> Double.compare(redditRankingAlgorithm(Double.valueOf(o2.getShareCounter()), o2.getCreatedDate()), redditRankingAlgorithm(Double.valueOf(o1.getShareCounter()), o1.getCreatedDate())));

        for (int i = 1; i <= min(questions.size(), 5); i++) {
            redisTemplate.opsForValue().set("question:rank:" + i, String.valueOf(questions.get(i - 1).getId()));
        }
    }

    @Override
    public List<QuestionResponseForm> getRankedQuestions() {
        List<QuestionResponseForm> questions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String qIdString = redisTemplate.opsForValue().get("question:rank:" + i);
            if (qIdString == null) {
                break;
            }

            Long qId = Long.parseLong(qIdString);

            Question question = questionJPARepository.findById(qId).orElseThrow(() -> new IllegalArgumentException("없는 문제입니다."));

            questions.add(QuestionResponseForm.builder()
                    .qId(question.getId())
                    .questionType(question.getType())
                    .title(question.getTitle())
                    .mainText(question.getMainText())
                    .choices(question.getChoices().stream().map(Choice::getChoice).toList())
                    .shareCounter(question.getShareCounter())
                    .build());
        }

        return questions;
    }
}
