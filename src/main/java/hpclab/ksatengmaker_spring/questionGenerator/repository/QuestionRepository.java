package hpclab.ksatengmaker_spring.questionGenerator.repository;

import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Getter
public class QuestionRepository {

    private static final String datasetFilePath = "/Users/leeju/IdeaProjects/KSatEngMaker_Spring/src/main/resources/static/dataset/K-SAT_dataset.json";
    private static final String definitionFilePath = "/Users/leeju/IdeaProjects/KSatEngMaker_Spring/src/main/resources/static/dataset/K-SAT_definition.json";

    private final List<String> defaultDatasets;
    private final TreeMap<QuestionType, String> definitions;

    public String getDefinition(QuestionType type) {
        return definitions.get(type);
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
        this.definitions = makeDefinitions();
    }

    @SuppressWarnings("unchecked")
    private TreeMap<QuestionType, String> makeDefinitions() throws Exception {
        Reader file = new FileReader(definitionFilePath);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(file);

        QuestionType[] values = QuestionType.values();
        ArrayList<String> definition = (ArrayList<String>) object.get("definition");

        TreeMap<QuestionType, String> definitions = new TreeMap<>();

        for (int i = 0; i < values.length; i++) {
            definitions.put(values[i], definition.get(i));
        }

        return definitions;
    }

    private List<String> makeDatasets() throws Exception {
        Reader file = new FileReader(datasetFilePath);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(file);

        return (ArrayList<String>) object.get("dataset");
    }
}
