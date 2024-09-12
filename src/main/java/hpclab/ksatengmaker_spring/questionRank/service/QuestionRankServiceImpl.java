package hpclab.ksatengmaker_spring.questionRank.service;

import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionRankServiceImpl implements QuestionRankService {

    private final RedisTemplate<String, String> redisTemplate;
    private final QuestionJPARepository questionJPARepository;

    @Override
    @Scheduled(cron = "0 0 0 * * 1", zone = "Asia/Seoul") // 매주 월요일 자정에 실행
    public void updateQuestionRank() {
        log.info("cron update question rank");

//        ScanOptions scanOptions = ScanOptions.scanOptions().match("post:userView:*").count(1000).build();
//
//        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);
//
//        while(keys.hasNext()) {
//            String key = new String(keys.next());
//            redisTemplate.delete(key);
//        }



    }

    @Override
    public List<QuestionResponseForm> getQuestions() {
        List<QuestionResponseForm> questions = new ArrayList<>();



        return questions;
    }
}
