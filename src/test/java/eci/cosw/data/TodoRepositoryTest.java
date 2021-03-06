package eci.cosw.data;

import eci.cosw.data.model.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    public void findByResponsible() {
        todoRepository.deleteAll();

        todoRepository.save(new Todo("des0", 0, "", "alejandro", "active"));
        todoRepository.save(new Todo("des0", 1, "", "alejandra", "active"));
        todoRepository.save(new Todo("des0", 5, "", "alejandro", "active"));
        todoRepository.save(new Todo("des0", 0, "", "alejandra", "active"));

        for (Todo todo: todoRepository.findByResponsible("alejandro")) {
            Assert.assertTrue(todo.getResponsible().equals("alejandro"));
            System.out.println(todo);
        }
    }
}