package ru.anton.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.anton.models.Question;
import ru.anton.repo.QuestionRepository;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private static final String QUESTION_URL =
            "https://raw.githubusercontent.com/Kolamin/Questions/main/Thermal_power_plants.txt";

    private QuestionRepository questionRepository;

    @Autowired
    public DataLoader(QuestionRepository questionRepository) {
        super();
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUESTION_URL))
                .build();
        HttpResponse<String> httpResponse =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Reader stringReader = new StringReader(httpResponse.body());

        StringBuilder fileContent;
        try (BufferedReader br = new BufferedReader(stringReader)) {


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

        String[] result = Arrays.copyOfRange(split, 1, split.length);

        for (String s : result) {
            String[] strings = s.split("\\n");
            questionRepository.save(new Question(strings[1],Arrays.copyOfRange(strings, 2, strings.length)));
        }
    }
}
