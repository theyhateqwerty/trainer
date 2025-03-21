package ru.theyhateqwerty.storage;


import ru.theyhateqwerty.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.domain.repo.QuestionRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuestionInMemoryStorage implements QuestionRepository {

    private final Map<Long, OpenQuestionCard> questions;

    public QuestionInMemoryStorage() {
        questions = new HashMap<>();
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        return questions.values().stream().toList();
    }

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
