package ru.theyhateqwerty.spring.hibernate.mapper;

import org.mapstruct.Mapper;
import ru.theyhateqwerty.spring.hibernate.entity.OpenQuestionCardEntity;
import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;


import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    OpenQuestionCard mapToModel(OpenQuestionCardEntity entity);
    OpenQuestionCardEntity mapToEntity(OpenQuestionCard question);
    List<OpenQuestionCard> mapToModel(List<OpenQuestionCardEntity> entities);
    List<OpenQuestionCardEntity> mapToEntity(List<OpenQuestionCard> questions);
}
