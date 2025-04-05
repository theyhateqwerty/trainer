package ru.theyhateqwerty.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.theyhateqwerty.api.dto.OpenQuestionCardDto;
import ru.theyhateqwerty.api.mapper.QuestionDtoMapper;
import ru.theyhateqwerty.trainer.domain.service.QuestionService;


import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Questions", description = "Endpoint-ы, связанные с задачами")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionDtoMapper mapper;

    public QuestionController(QuestionService questionService, QuestionDtoMapper mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }
    @Operation(summary = "Получение всех задач",
            description = "Получены все задачи пользователя без ограничений"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OpenQuestionCardDto> list() {
        return mapper.mapToDto(questionService.getAll());
    }

    @Operation(summary = "Добавление новой задачи",
            description = "Создает новую задачу"
    )
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void add(@Parameter(description = "Новая задача") @RequestBody OpenQuestionCardDto dto) {
        questionService.save(mapper.mapToModel(dto));
    }

    @Operation(summary = "Обновление задачи",
            description = "Находит задачу и обновляет"
    )
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@Parameter(description = "Измененная задача") @RequestBody OpenQuestionCardDto dto) {
        questionService.save(mapper.mapToModel(dto));
    }

    @Operation(summary = "Удаление задачи",
            description = "Находит задачу по коду и удаляет, если она найдена"
    )
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "Код задачи для удаления") @PathVariable Long id) {
        questionService.getById(id);
    }
}

