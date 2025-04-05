package ru.theyhateqwerty.console.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.theyhateqwerty.spring.jdbc.config.DbConfig;


@Configuration
@Import(DbConfig.class)
@ComponentScan(basePackages = "ru.theyhateqwerty")
@PropertySource("jdbc.properties")
public class SpringConfig {

}
