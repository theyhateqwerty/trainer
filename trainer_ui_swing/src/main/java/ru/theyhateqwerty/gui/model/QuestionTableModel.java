package ru.theyhateqwerty.gui.model;

import ru.theyhateqwerty.domain.model.OpenQuestionCard;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {
    private final String[] columnNames = new String[]{"ID", "Текст", "Ожидаемый ответ"};
    private final List<OpenQuestionCard> questions;

    public QuestionTableModel(List<OpenQuestionCard> questions) {
        this.questions = questions;
    }

    @Override
    public int getRowCount() {
        return questions.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OpenQuestionCard questionCard = questions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return questionCard.getId();
            case 1:
                return questionCard.getQuestion();
            case 2:
                return questionCard.getExpectedAnswer();
            default:
                return null;
        }
    }
}
