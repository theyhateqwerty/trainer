package ru.theyhateqwerty.dao;

import org.springframework.stereotype.Repository;
import ru.theyhateqwerty.trainer.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.trainer.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {

    private static final String DDL_QUERY = """
          CREATE TABLE questions (id BIGINT PRIMARY KEY, question VARCHAR(100), answer VARCHAR(100))
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT * FROM questions
          """;
    private static final String FIND_BY_ID_QUERY = """
          SELECT * FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUESTION_QUERY = """
          INSERT INTO questions(id, question, answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_QUESTION_QUERY = """
          UPDATE questions SET question=?, answer=? WHERE id=?
          """;
    private static final String DELETE_QUESTION_QUERY = """
          DELETE FROM questions WHERE id=?
          """;
    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initDataBase();
    }

    public void initDataBase() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DDL_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                questions.add(constructQuestion(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public Optional<OpenQuestionCard> getById(Long id) {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(constructQuestion(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void add(OpenQuestionCard questionCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION_QUERY);){
            statement.setLong(1, questionCard.getId());
            statement.setString(2, questionCard.getQuestion());
            statement.setString(3, questionCard.getAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpenQuestionCard questionCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUESTION_QUERY);){
            statement.setString(1, questionCard.getQuestion());
            statement.setString(2, questionCard.getAnswer());
            statement.setLong(3, questionCard.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUESTION_QUERY);){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OpenQuestionCard constructQuestion(ResultSet resultSet) throws SQLException {
        return new OpenQuestionCard(
                resultSet.getLong("id"),
                resultSet.getString("question"),
                resultSet.getString("answer")
        );
    }
}
