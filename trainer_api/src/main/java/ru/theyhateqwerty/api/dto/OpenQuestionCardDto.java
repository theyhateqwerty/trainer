package ru.theyhateqwerty.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Задача в системе TM")
public class OpenQuestionCardDto {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Супер важная задача")
    private String question;

    @Schema(description = "Ожидаемый ответ", example = "аоаоа")
    private String expectedAnswer;


    public OpenQuestionCardDto(Long id, String question, String expectedAnswer) {
        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
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

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}
