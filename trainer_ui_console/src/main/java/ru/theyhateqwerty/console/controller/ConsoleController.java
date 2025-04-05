package ru.theyhateqwerty.console.controller;

import org.springframework.stereotype.Component;
import ru.theyhateqwerty.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.domain.service.QuestionService;


import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {

    private static final String MENU = """
          Введите [1], чтобы показать все вопросы
          Введите [2], чтобы добавить вопрос
          Введите [3], чтобы удалить вопрос
          Введите [4], чтобы найти вопрос
          Введите [5], чтобы выйти
          """;

    private final QuestionService service;
    private final Scanner scanner;

    public ConsoleController(QuestionService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void interactWithUser() {
        while(true) {
            printMenu();
            String operationCode = scanner.nextLine();
            switch (operationCode) {
                case "1" -> printAllQuestions();
                case "2" -> addQuestion();
                case "3" -> removeQuestion();
                case "4" -> findQuestion();
                case "5" -> { return; }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu() {
        System.out.println(MENU);
    }

    private void printAllQuestions() {
        System.out.println(service.getAll());
    }

    private void addQuestion() {
        System.out.println("Введите id вопроса");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Введите вопрос");
        String question = scanner.nextLine();
        System.out.println("Введите ожидаемый ответ");
        String answer = scanner.nextLine();
        OpenQuestionCard questionCard = new OpenQuestionCard(id, question, answer);
        service.save(questionCard);
    }

    private void removeQuestion() {
        System.out.println("Введите id вопроса, который хотите удалить");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> questionCard = service.getById(id);
        if (questionCard.isPresent()) {
            System.out.println("Введите [Y], если точно хотите удалить вопрос " + questionCard.get());
            String confirmation = scanner.nextLine();
            if ("Y".equals(confirmation)) {
                service.delete(questionCard.get());
            }
        } else {
            System.out.println("Такого вопроса найти не удалось");
        }
    }

    private void findQuestion() {
        System.out.println("Введите id вопроса, который хотите найти");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> questionCard = service.getById(id);
        if (questionCard.isPresent()) {
            System.out.println(questionCard.get());
        } else {
            System.out.println("Такого вопроса найти не удалось");
        }
    }
}