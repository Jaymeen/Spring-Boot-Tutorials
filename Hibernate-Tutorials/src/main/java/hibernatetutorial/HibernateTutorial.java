package hibernatetutorial;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTutorial {
    public static void main(String[] args) {
        Student s1 = new Student("Harshil Aghara", 24, 1);

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            // For writes/updates/deletes transactions are required.
            // CREATE
//            Transaction transaction = session.beginTransaction();
//            session.persist(s1);
//            transaction.commit();

            // UPDATE
//            s1.age = 26;
//            Transaction transaction = session.beginTransaction();
//            session.merge(s1);
//            transaction.commit();

            // DELETE
//            Transaction transaction = session.beginTransaction();
//            session.remove(s1);
//            transaction.commit();

            // For reads transactions are not required.
            // READ
//            Student s2 = session.get(Student.class, 1);
//            System.out.println(s2);
        }
        catch (HibernateException hibernateException) {
            System.out.println("Hibernate Exception: " + hibernateException);
        }
    }
}
