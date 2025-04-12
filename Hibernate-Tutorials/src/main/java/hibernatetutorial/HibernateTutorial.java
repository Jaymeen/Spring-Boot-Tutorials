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
                Student student = new Student("Harshil", 25, 1, new Address("Mumbai", "Maharashtra", "400001"));

                Transaction tx = session.beginTransaction();
                session.persist(student);
                tx.commit();
            }
        }
        catch (HibernateException hibernateException) {
            System.out.println("Hibernate Exception: " + hibernateException);
        }
    }
}
