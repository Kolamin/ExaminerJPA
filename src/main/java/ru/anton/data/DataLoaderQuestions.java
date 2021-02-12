package ru.anton.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.anton.models.CorrectAnswer;
import ru.anton.models.Question;
import ru.anton.repo.AnswerRepository;
import ru.anton.repo.QuestionRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Service
public class DataLoaderQuestions implements CommandLineRunner {

    private static final String QUESTION_URL =
            "https://raw.githubusercontent.com/Kolamin/Questions/main/Thermal_power_plants.txt";

    private static final String ANSWER_URL =
            "https://raw.githubusercontent.com/Kolamin/Questions/main/Answer.txt";

    private AnswerRepository answerRepository;

    private QuestionRepository questionRepository;

    public DataLoaderQuestions(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        HttpClient client;
        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUESTION_URL))
                .build();
        HttpResponse<String> httpResponse =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Reader stringReaderAllTest = new StringReader(httpResponse.body());

        HttpRequest newRequest = HttpRequest.newBuilder()
                .uri(URI.create(ANSWER_URL))
                .build();

        httpResponse = client.send(newRequest, HttpResponse.BodyHandlers.ofString());

        Reader strReaderCorrectAnswer = new StringReader(httpResponse.body());

        String[] splitAllTest = getStrings(stringReaderAllTest);

        String[] resultAllTest = Arrays.copyOfRange(splitAllTest, 1, splitAllTest.length);

        for (String s : resultAllTest) {
            String[] strings = s.split("\\n");
            questionRepository.save(new Question(strings[1],Arrays.copyOfRange(strings, 2, strings.length)));
        }

        String[] correctAnswers = getStrings(strReaderCorrectAnswer);

        String[] resultCorrectAnswer = Arrays.copyOfRange(correctAnswers, 1, correctAnswers.length);

        for (String s : resultCorrectAnswer) {
            String[] split = s.split("\\n");

           answerRepository.save(new CorrectAnswer(split[2]));
        }

    }

    private String[] getStrings(Reader stringReaderAllTest) throws IOException {
        StringBuilder fileContent;
        try (BufferedReader br = new BufferedReader(stringReaderAllTest)) {

            fileContent = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null) {
                if(st.contains("Правила по охране труда при эксплуатации тепловых энергоустановок"))
                    continue;
                if (st.contains("Мероприятия по оказани первой помощи (Приказ Минздрава России от 04.05.2012 № 477н)"))
                    continue;
                fileContent.append(st)
                        .append("\n");
            }
        }


        String[] split = fileContent.toString()
                .split("Вопрос \\d+");
        return split;
    }
}
