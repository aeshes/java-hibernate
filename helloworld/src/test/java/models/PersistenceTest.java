package models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

public class PersistenceTest {
    SessionFactory factory;

    @BeforeSuite
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(models.Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void saveMessage() {
        Message msg = new Message("Hello, World");
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(msg);
        tx.commit();
        session.close();
    }

    @Test(dependsOnMethods = "saveMessage")
    public void readMessage() {
        Session session = factory.openSession();

        @SuppressWarnings("unchecked")
        List<Message> list = (List<Message>) session.createQuery("from Message").list();

        if (list.size() > 1) {
            Assert.fail("Message configuration in error; "
            + "table should contain only one"
            + " Set ddl to drop-create");
        }

        if (list.size() == 0) {
            Assert.fail("Read of initial message failed; "
            + "check saveMessage() for errors.");
        }

        for (Message m : list) {
            System.out.println(m);
        }
        session.close();
    }
}
