package eci.cosw;

import eci.cosw.data.AppConfiguration;
import eci.cosw.data.TodoRepository;
import eci.cosw.data.UserRepository;
import eci.cosw.data.model.Todo;
import eci.cosw.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private List<String> descriptions;
    private List<Integer> priorities;
    private List<String> dueDates;
    private List<String> responsibles;
    private List<String> status;

    // -----

    private List<String> names;
    private List<String> emails;

    public void defineFields() {
        descriptions = new ArrayList<>(Arrays.asList("des0", "des1", "des2", "des3", "des4", "des5"));
        priorities = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        dueDates = new ArrayList<>();
        responsibles = new ArrayList<>();
        status = new ArrayList<>(Arrays.asList("pending", "done", "canceled"));

        // -----

        names = new ArrayList<>(Arrays.asList("Alejandro Anzola", "David Vaca", "Juan Moreno", "Maria Paula", "Steven Mendivelso", "Alba Cecilia"));
        emails = new ArrayList<>(Arrays.asList("cra7avila@mail.com", "blabla@mail.com", "juan-moreno@mail.com", "jackie@mail.com", "fabian@mail.com"));
    }

    public <T> T pickRandom(List<T> l) {
        int len = l.size();
        return l.get(new Random().nextInt(len));
    }

    public String generateRandomLocalDate() {
        long minDay = LocalDate.of(1990, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2030, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate.toString();
    }

    public String generateRandomDate() {
        Random ran = new Random();
        String res;
        do {
            res = generateRandomLocalDate();
        } while(dueDates.contains(res));

        dueDates.add(res);
        return res;
    }

    private static String generateRandomString(Random random, int length){
        return random.ints(48,122)
                .filter(i-> (i<57 || i>65) && (i <90 || i>97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public String generateRandomResponsible() {
        Random ran = new Random();
        String res;
        do {
            res = generateRandomString(ran, 15);
        } while(responsibles.contains(res));

        responsibles.add(res);
        return res;
    }

    public Todo generateRandomTodo() {
        return new Todo(pickRandom(descriptions),
                pickRandom(priorities),
                generateRandomDate(),
                generateRandomResponsible(),
                pickRandom(status));
    }

    public User generateRandomUser() {
        return new User(pickRandom(names),
                pickRandom(emails));
    }

    public void populateDatabase() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
        defineFields();

        for (int i = 0; i < 25; i++) {
            todoRepository.save(generateRandomTodo());
        }

        for (int i = 0; i < 10; i++) {
            userRepository.save(generateRandomUser());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        populateDatabase();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        for (Todo todo : todoRepository.findAll()) {
            System.out.println(todo);
        }

        for (User user: userRepository.findAll()) {
            System.out.println(user);
        }
    }

}