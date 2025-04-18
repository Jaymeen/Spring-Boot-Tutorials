package hibernatetutorial;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class HibernateTutorial {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                List<Laptop> laptops = Arrays.asList(new Laptop(100, "Lenovo", "LEGION Y540", 72000.0), new Laptop(101, "Dell", "XPS", 60000.0 ));

                Student student = new Student(1, "Harshil", 25, new Address("Mumbai", "Maharashtra", "400001"));

                laptops.get(0).student = student;
                laptops.get(1).student = student;

                Transaction tx = session.beginTransaction();

                session.persist(student);
                session.persist(laptops.get(0));
                session.persist(laptops.get(1));

                tx.commit();
            }
        }
        catch (HibernateException hibernateException) {
            System.out.println("Hibernate Exception: " + hibernateException);
        }
    }
}
