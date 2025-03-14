package ru.theyhateqwerty.trainer.domain.model;

import java.util.Objects;

public class OpenQuestionCard {

    private final Long id;

    private final String question;

    private final String expectedAnswer;

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id null");
        }
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос null или пустой");
        }
        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("Ответ null или пустой");
        }

        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(expectedAnswer);
    }

    @Override
    public String toString() {
        return "QuestionCard{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", expected_answer='" + expectedAnswer + '\'' +
                '}';
    }
}
