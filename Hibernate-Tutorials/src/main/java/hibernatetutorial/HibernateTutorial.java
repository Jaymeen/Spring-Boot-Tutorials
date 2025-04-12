package hibernatetutorial;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTutorial {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                // Create Laptop & Save to DB
                Laptop laptop = new Laptop(100, "Lenovo", "LEGION Y540", 72000.0);
                Transaction tx = session.beginTransaction();
                session.persist(laptop);

                laptop = session.get(Laptop.class, 100);

                // Create Student & Save to DB
                Student student = new Student(1, "Harshil", 25, new Address("Mumbai", "Maharashtra", "400001"), laptop);
                session.persist(student);

                student = session.get(Student.class, 1);

                System.out.println(student);

                tx.commit();
            }
        }
        catch (HibernateException hibernateException) {
            System.out.println("Hibernate Exception: " + hibernateException);
        }
    }
}
