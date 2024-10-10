package hpclab.ksatengmaker_spring.questionGenerator.repository;

import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Repository
@Getter
public class QuestionRepository {

    private final List<String> defaultDatasets;
    private final TreeMap<QuestionType, String> questionDefinitions;
    private final TreeMap<QuestionType, String> explanationDefinitions;

    public String getQuestionDefinition(QuestionType type) {
        return questionDefinitions.get(type);
    }

    public String getExplanationDefinition(QuestionType type) {
        return explanationDefinitions.get(type);
    }

    public TreeMap<QuestionType, String> getQuestionType() {
        TreeMap<QuestionType, String> questionTypes = new TreeMap<>();

        for (QuestionType type : QuestionType.values()) {
            questionTypes.put(type, type.getKrName());
        }

        return questionTypes;
    }

    public QuestionRepository() throws Exception {
        this.defaultDatasets = makeDatasets();
        this.questionDefinitions = makeQuestionDefinitions();
        this.explanationDefinitions = makeExplanationDefinitions();
    }

    @SuppressWarnings("unchecked")
    private TreeMap<QuestionType, String> makeQuestionDefinitions() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/dataset/K-SAT_questionDefinition.json");

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(reader);

        reader.close();

        QuestionType[] values = QuestionType.values();
        ArrayList<String> definition = (ArrayList<String>) object.get("definition");

        TreeMap<QuestionType, String> definitions = new TreeMap<>();

        for (int i = 0; i < values.length; i++) {
            definitions.put(values[i], definition.get(i));
        }

        return definitions;
    }

    @SuppressWarnings("unchecked")
    private TreeMap<QuestionType, String> makeExplanationDefinitions() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/dataset/K-SAT_explanationDefinition.json");

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(reader);

        reader.close();

        QuestionType[] values = QuestionType.values();
        ArrayList<String> definition = (ArrayList<String>) object.get("definition");

        TreeMap<QuestionType, String> definitions = new TreeMap<>();

        for (int i = 0; i < values.length; i++) {
            definitions.put(values[i], definition.get(i));
        }

        return definitions;
    }

    private List<String> makeDatasets() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/dataset/K-SAT_dataset.json");

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(reader);

        reader.close();

        return (ArrayList<String>) object.get("dataset");
    }
}
