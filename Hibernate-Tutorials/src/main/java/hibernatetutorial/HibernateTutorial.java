package hibernatetutorial;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HibernateTutorial {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                List<Laptop> laptopList = Arrays.asList(new Laptop(100, "Lenovo", "LEGION Y540", 72000.0), new Laptop(101, "Dell", "XPS", 60000.0 ), new Laptop(102, "Macbook", "Air", 118000.0));

                List<Student> studentList = Arrays.asList(new Student(1, "Harshil", 25, new Address("Mumbai", "Maharashtra", "400001")), new Student(2, "Jitesh", 26, new Address("Delhi", "Delhi", "400002")), new Student(3, "Ishan", 24, new Address("Chennai", "Tamil Nadu", "400003")));

                laptopList.get(0).students.addAll(Arrays.asList(studentList.get(0), studentList.get(1)));
                laptopList.get(1).students.addAll(Arrays.asList(studentList.get(0), studentList.get(1), studentList.get(2)));
                laptopList.get(2).students.add(studentList.get(2));

                studentList.get(0).laptops.addAll(Arrays.asList(laptopList.get(0), laptopList.get(1)));
                studentList.get(1).laptops.addAll(Arrays.asList(laptopList.get(0), laptopList.get(1)));
                studentList.get(2).laptops.addAll(Arrays.asList(laptopList.get(1), laptopList.get(2)));

                Transaction tx = session.beginTransaction();

                for(Laptop laptop : laptopList) {
                    session.persist(laptop);
                }

                for(Student student : studentList) {
                    session.persist(student);
                }

                tx.commit();
            }
        }
        catch (HibernateException hibernateException) {
            System.out.println("Hibernate Exception: " + hibernateException);
        }
    }
}
