package ru.theyhateqwerty.trainer;

import ru.theyhateqwerty.trainer.controller.ConsoleController;
import ru.theyhateqwerty.trainer.domain.repo.QuestionRepository;
import ru.theyhateqwerty.trainer.domain.service.QuestionService;
import ru.theyhateqwerty.trainer.storage.QuestionInMemoryStorage;

public class App 
{
    public static void main( String[] args )
    {
        QuestionRepository repository = new QuestionInMemoryStorage();
        QuestionService service = new QuestionService(repository);
        ConsoleController controller = new ConsoleController(service);
        controller.interactWithUser();
    }
}
