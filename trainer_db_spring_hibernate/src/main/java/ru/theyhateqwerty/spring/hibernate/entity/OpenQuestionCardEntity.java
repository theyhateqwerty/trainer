package ru.theyhateqwerty.spring.hibernate.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "open_question_card")
public class OpenQuestionCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code") // Основной идентификатор базы данных
    private Long code;

    @Column
    private Long id; // Логический идентификатор

    @Column
    private String question;

    @Column
    private String expectedAnswer;

    // Геттеры и сеттеры
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

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
