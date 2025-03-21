package ru.theyhateqwerty.gui.controller;


import ru.theyhateqwerty.domain.model.OpenQuestionCard;
import ru.theyhateqwerty.domain.service.QuestionService;
import ru.theyhateqwerty.gui.model.QuestionTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainController implements Runnable {
    private final QuestionService service;
    private JFrame mainFrame;
    private JPanel mainPanel;

    public MainController(QuestionService service) {
        this.service = service;
    }

    @Override
    public void run() {
        mainFrame = new JFrame("Time Management Tool");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(prepareMenu());
        prepareMainPanelForListTask();
        mainFrame.setVisible(true);
    }

    private void prepareMainPanelForAddTask(Optional<OpenQuestionCard> taskForEdit) {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("ID"), layoutConstraints);
        JTextField idField = new JTextField(15);
        layoutConstraints.gridx = 1;
        mainPanel.add(idField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Вопрос"), layoutConstraints);
        JTextField questionField = new JTextField(15);
        layoutConstraints.gridx = 1;
        mainPanel.add(questionField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        mainPanel.add(new JLabel("Ожидаемый ответ"), layoutConstraints);
        JTextField answerField = new JTextField(15);
        layoutConstraints.gridx = 1;
        mainPanel.add(answerField, layoutConstraints);

        JButton addButton = new JButton("Добавить");
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 2;

        taskForEdit.ifPresent(t -> {
            idField.setText(String.valueOf(t.getId()));
            questionField.setText(t.getQuestion());
            answerField.setText(t.getExpectedAnswer());
        });

        addButton.addActionListener(event -> {
            Long id = Long.parseLong(idField.getText());
            OpenQuestionCard question = new OpenQuestionCard(id, questionField.getText(), answerField.getText());
            service.save(question);
            prepareMainPanelForListTask();
        });

        mainPanel.add(addButton, layoutConstraints);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private void prepareMainPanelForListTask() {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new BorderLayout());
        JTable table = new JTable(new QuestionTableModel(service.getAll()));
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private JMenuBar prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu taskMenu = new JMenu("Task");
        JMenuItem addTask = new JMenuItem("Добавить вопрос");
        addTask.addActionListener(event -> prepareMainPanelForAddTask(Optional.empty()));
        taskMenu.add(addTask);
        menuBar.add(taskMenu);
        JMenuItem listTask = new JMenuItem("Просмотреть вопросы");
        listTask.addActionListener(event -> prepareMainPanelForListTask());
        taskMenu.add(listTask);

        JMenuItem removeTask = new JMenuItem("Удалить вопрос");
        removeTask.addActionListener(event -> {
            OpenQuestionCard questionToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос хотите удалить?",
                    "Удаление вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            if (questionToDelete != null) {
                service.delete(questionToDelete.getId());

                prepareMainPanelForListTask();
            }
        });
        taskMenu.add(removeTask);

        JMenuItem editTask = new JMenuItem("Обновить вопрос");
        editTask.addActionListener(event -> {
            OpenQuestionCard questionToEdit = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос хотите изменить?",
                    "Обновление вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            if (questionToEdit != null) {
                prepareMainPanelForAddTask(Optional.of(questionToEdit));
            }
        });
        taskMenu.add(editTask);

        menuBar.add(taskMenu);
        return menuBar;
    }
}