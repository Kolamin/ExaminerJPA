package ru.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.anton.repo.QuestionRepository;


@Controller
@RequestMapping("/tests")
public class ExaminerController {


    private final QuestionRepository questionRepository;

    @Autowired
    public ExaminerController(QuestionRepository questionRepository) {
        super();
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions")
    public String getAllCustomers(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "tests/questions";
    }

}
