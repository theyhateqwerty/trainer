package ru.theyhateqwerty.storage;

import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.trainer.domain.repo.QuestionRepository;

import java.util.*;

public class QuestionInMemoryStorage implements QuestionRepository {

    private final Map<Long, OpenQuestionCard> questions;

    public QuestionInMemoryStorage() {
        questions = new HashMap<>();
    }

    @Override
    public List<OpenQuestionCard> findAll() { return new ArrayList<>(questions.values()); }

    @Override
    public Optional<OpenQuestionCard> getById(Long id) {
        return Optional.ofNullable(questions.get(id));
    }

    @Override
    public void add(OpenQuestionCard questionCard) {
        questions.put(questionCard.getId(), questionCard);
    }

    @Override
    public void update(OpenQuestionCard questionCard) {
        questions.put(questionCard.getId(), questionCard);
    }

    @Override
    public void remove(Long id) {
        questions.remove(id);
    }
}
