package ru.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anton.models.Question;
import ru.anton.repo.AnswerRepository;
import ru.anton.repo.QuestionRepository;


@Controller
//@RequestMapping("/tests")
public class ExaminerController {


    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @GetMapping("/")
    public String home(){

        return "home";
    }

    @Autowired
    public ExaminerController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        super();
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/corrects")
    public String getCorrectAnswers(Model model){
        model.addAttribute("corrects", answerRepository.findAll());
        return "corrects";
    }

    @GetMapping("/corrects/{id}")
    public String getSingleCorrectAnswer(@PathVariable("id") long id, Model model){
        model.addAttribute("correct", answerRepository.findById(id));
        return "correct";

    }

    @GetMapping("/questions")
    public String getAllCustomers(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "questions";
    }

    @GetMapping("{id}")
    public String getSingleQuestion(@PathVariable("id") long id, Model model){
        model.addAttribute("question", questionRepository.findById(id));
        return "question";
    }

    @PostMapping("/answer/{id}")
    public String getAnswer(Model model, @ModelAttribute Question answer, @PathVariable("id") int id){
        model.addAttribute("answers",  answer);
        model.addAttribute("correctAnswer", answerRepository.findById(id));
        model.addAttribute("id", id);
        return "answer";
    }

}
