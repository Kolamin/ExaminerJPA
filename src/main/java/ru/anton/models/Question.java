package ru.anton.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 4000)
    private String question;
    @Column(length = 4000)
    private String[] testOptions;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    protected Question() {
    }

    public Question(String question, String[] testOptions) {
        this.question = question;
        this.testOptions = testOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getTestOptions() {
        return testOptions;
    }

    public void setTestOptions(String[] testOptions) {
        this.testOptions = testOptions;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", testOptions=" + Arrays.toString(testOptions) +
                ", answer='" + answer + '\'' +
                '}';
    }
}

