package ru.theyhateqwerty.api.mapper;

import org.mapstruct.Mapper;
import ru.theyhateqwerty.api.dto.OpenQuestionCardDto;
import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;


import java.util.List;
@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    OpenQuestionCard mapToModel(OpenQuestionCardDto entity);
    OpenQuestionCardDto mapToDto(OpenQuestionCard question);
    List<OpenQuestionCard> mapToModel(List<OpenQuestionCardDto> entities);
    List<OpenQuestionCardDto> mapToDto(List<OpenQuestionCard> questions);

    OpenQuestionCardDto mapToDto(ru.theyhateqwerty.domain.model.OpenQuestionCard task);
}
