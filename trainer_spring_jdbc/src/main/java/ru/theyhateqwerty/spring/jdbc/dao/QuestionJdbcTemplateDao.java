package ru.theyhateqwerty.spring.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.trainer.domain.repo.QuestionRepository;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcTemplateDao implements QuestionRepository {
    private static final String DDL_QUERY = """
          CREATE TABLE questions (ID int AUTO_INCREMENT PRIMARY KEY, question TEXT NOT NULL, expected_answer TEXT NOT NULL)
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, question, expected_answer FROM questions
          """;
    private static final String FIND_BY_CODE_QUERY = """
          SELECT id, question, expected_answer FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUERY = """
          INSERT INTO questions (id, question, expected_answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_QUERY = """
          UPDATE questions SET question=?, expected_answer=? WHERE id=?
          """;
    private static final String DELETE_QUERY = """
          DELETE FROM questions WHERE id=?
          """;
    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcTemplateDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        initSchema();
    }



    @Override
    public List<OpenQuestionCard> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY,
                (ResultSet rs, int rowNum) ->
                        new OpenQuestionCard(
                                rs.getLong("id"),
                                rs.getString("question"),
                                rs.getString("expected_answer"))
        );
    }

    @Override
    public Optional<OpenQuestionCard> getById(Long id) {
        List<OpenQuestionCard> questions = jdbcTemplate.query(FIND_BY_CODE_QUERY,
                (ResultSet rs, int rowNum) ->
                        new OpenQuestionCard(
                                rs.getLong("id"),
                                rs.getString("question"),
                                rs.getString("expected_answer")), id);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }


    @Override
    public void add(OpenQuestionCard question) {
        jdbcTemplate.update(INSERT_QUERY, question.getId(), question.getQuestion(), question.getExpectedAnswer());
    }

    @Override
    public void update(OpenQuestionCard question) {
        jdbcTemplate.update(UPDATE_QUERY, question.getQuestion(), question.getExpectedAnswer(), question.getId());
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    private void initSchema() {
        jdbcTemplate.update(DDL_QUERY);
    }
}

