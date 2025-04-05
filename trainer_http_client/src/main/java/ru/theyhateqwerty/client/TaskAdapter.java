package ru.theyhateqwerty.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.theyhateqwerty.api.dto.OpenQuestionCardDto;
import ru.theyhateqwerty.api.mapper.QuestionDtoMapper;
import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.trainer.domain.repo.QuestionRepository;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TaskAdapter implements QuestionRepository {
    private static final Logger logger = LogManager.getLogger(TaskAdapter.class);
    private static final String TASK_ROOT_URL_TEMPLATE = "%s/task";
    private static final String TASK_DELETE_URL_TEMPLATE = "%s/task/%s";
    private final HttpClient httpClient;
    private final QuestionDtoMapper mapper;
    private final String serverUrl;
    private final Gson gson;

    public TaskAdapter(@Value("${trainer.interactions.server_url}") String serverUrl,
                       QuestionDtoMapper mapper) {
        this.serverUrl = serverUrl;
        this.mapper = mapper;
        httpClient = HttpClient.newBuilder()
                .build();
        gson = new GsonBuilder().create();
    }


    @Override
    public List<OpenQuestionCard> findAll() {
        List<ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard> tasks = Collections.emptyList();
        try {
            String url = String.format(TASK_ROOT_URL_TEMPLATE, serverUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (validateResponse(response)) {
                List<OpenQuestionCardDto> dtos = gson
                        .fromJson(response.body(),
                                new TypeToken<List<OpenQuestionCardDto>>() {}.getType());
                tasks = mapper.mapToModel(dtos);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return tasks;
    }

    @Override
    public Optional<ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard> getById(Long code) {
        Optional<ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard> task = Optional.empty();
        try {
            String url = String.format(TASK_ROOT_URL_TEMPLATE, serverUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (validateResponse(response)) {
                List<OpenQuestionCardDto> dtos = gson.fromJson(response.body(), new TypeToken<List<OpenQuestionCardDto>>() {}.getType());
                List<ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard> tasks = mapper.mapToModel(dtos);
                task = tasks.stream().filter(t -> t.getId().equals(code)).findAny();
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public void add(OpenQuestionCard task) {
        OpenQuestionCardDto dto = mapper.mapToDto(task);
        String body = gson.toJson(dto);
        try {
            String url = String.format(TASK_ROOT_URL_TEMPLATE, serverUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(OpenQuestionCard task) {
        OpenQuestionCardDto dto = mapper.mapToDto(task);
        String body = gson.toJson(dto);
        try {
            String url = String.format(TASK_ROOT_URL_TEMPLATE, serverUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void remove(Long code) {
        try {
            String url = String.format(TASK_DELETE_URL_TEMPLATE, serverUrl, code);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private boolean validateResponse(HttpResponse<String> response) {
        return response != null
                && response.statusCode() == 200
                && response.body() != null
                && !"".equals(response.body());
    }
}
