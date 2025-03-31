package ru.theyhateqwerty.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import ru.theyhateqwerty.gui.controller.MainController;
import ru.theyhateqwerty.trainer.domain.service.QuestionService;

import javax.swing.*;
@SpringBootApplication
public class Main  {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Main.class)
                .headless(false).run(args);
        logger.info("Начало работы приложения");
        QuestionService questionService = ctx.getBean(QuestionService.class);
        SwingUtilities.invokeLater(new MainController(questionService));
        logger.info("Конец работы приложения");
    }
}
