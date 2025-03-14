package ru.theyhateqwerty.trainer.domain.model;

import java.util.Objects;

public class OpenQuestionCard {

    private final String question;

    private final String expectedAnswer;

    public OpenQuestionCard(String question, String expectedAnswer) {
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос null или пустой");
        }
        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("Ответ null или пустой");
        }

        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(expectedAnswer);
    }

}
