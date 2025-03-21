package ru.theyhateqwerty.domain.service;

import org.springframework.stereotype.Service;
import ru.theyhateqwerty.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.domain.repo.QuestionRepository;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<OpenQuestionCard> getAll() {
        return questionRepository.findAll();
    }

    public Optional<OpenQuestionCard> getById(Long id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }

        return questionRepository.getById(id);
    }

    public boolean contains(OpenQuestionCard questionCard) {
        if (isQuestionInvalid(questionCard)) {
            return false;
        }

        return questionRepository.getById(questionCard.getId()).isPresent();
    }

    public void save(OpenQuestionCard questionCard) {
        if (isQuestionInvalid(questionCard)) {
            return;
        }
        if (contains(questionCard)) {
            questionRepository.update(questionCard);
        } else {
            questionRepository.add(questionCard);
        }
    }

    public void delete(OpenQuestionCard questionCard) {
        if (isQuestionInvalid(questionCard)) {
            return;
        }

        questionRepository.remove(questionCard.getId());
    }

    private boolean isQuestionInvalid(OpenQuestionCard questionCard) {
        return Objects.isNull(questionCard) || Objects.isNull(questionCard.getId());
    }
}
