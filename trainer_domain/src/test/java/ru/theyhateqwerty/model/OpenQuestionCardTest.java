package ru.theyhateqwerty.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.theyhateqwerty.domain.model.OpenQuestionCard;

public class OpenQuestionCardTest {

    @Test
    @DisplayName("Создание OpenQuestionCard с корректными question и expectedAnswer успешно")
    void having_correctQuestionAndAnswer_when_newOpenQuestionCard_then_created() {
        String question = "Как дела?";
        String expectedAnswer = "Хорошо";
        OpenQuestionCard openQuestionCard = new OpenQuestionCard(1L, question, expectedAnswer);

        Assertions.assertEquals(question, openQuestionCard.getQuestion());
    }

    @Test
    @DisplayName("Создание OpenQuestionCard с question и expectedAnswer равным null выбрасывает исключение")
    void having_nullQuestionAndAnswer_when_newTask_then_exceptionThrown() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new OpenQuestionCard(1L,null, null));
    }

    @Test
    @DisplayName("передача правильного ответа в checkAnswer возвращает true")
    void having_CorrectAnswer_when_checkAnswer_then_true() {
        String question = "Как дела?";
        String expectedAnswer = "Хорошо";
        String answer = "Хорошо";
        OpenQuestionCard openQuestionCard = new OpenQuestionCard(1L, question, expectedAnswer);
        Assertions.assertTrue(openQuestionCard.checkAnswer(answer));
    }

    @Test
    @DisplayName("передача неправильного ответа в checkAnswer возвращает false")
    void having_IncorrectAnswer_when_checkAnswer_then_false() {
        String question = "Как дела?";
        String expectedAnswer = "Хорошо";
        String answer = "Плохо";
        OpenQuestionCard openQuestionCard = new OpenQuestionCard(1L, question, expectedAnswer);
        Assertions.assertFalse(openQuestionCard.checkAnswer(answer));
    }
}
