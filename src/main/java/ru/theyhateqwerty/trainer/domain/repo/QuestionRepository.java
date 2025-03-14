package ru.theyhateqwerty.trainer.domain.repo;

import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<OpenQuestionCard> findAll();
    Optional<OpenQuestionCard> getById(Long id);
    void add(OpenQuestionCard questionCard);
    void update(OpenQuestionCard questionCard);
    void remove(Long id);
}
