package ru.theyhateqwerty.domain.repo;

import ru.theyhateqwerty.domain.model.OpenQuestionCard;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<OpenQuestionCard> findAll();
    Optional<ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard> getById(Long id);
    void add(OpenQuestionCard questionCard);
    void update(OpenQuestionCard questionCard);
    void remove(Long id);
}
