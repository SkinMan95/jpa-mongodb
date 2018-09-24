package eci.cosw;

import eci.cosw.data.CustomerRepository;
import eci.cosw.data.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {


//    @Autowired
//    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        customerRepository.deleteAll();
//
//        customerRepository.save(new Customer("Alice", "Smith"));
//        customerRepository.save(new Customer("Bob", "Marley"));
//        customerRepository.save(new Customer("Jimmy", "Page"));
//        customerRepository.save(new Customer("Freddy", "Mercury"));
//        customerRepository.save(new Customer("Michael", "Jackson"));
//
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : customerRepository.findAll()) {
//            System.out.println(customer);
//        }
//        System.out.println();
    }

}