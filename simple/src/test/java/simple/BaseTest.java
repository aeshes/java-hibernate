package simple;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.testng.annotations.*;

public abstract class BaseTest {
    SessionFactory factory;

    @BeforeSuite
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Skill.class);
        configuration.addAnnotatedClass(Ranking.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    @AfterSuite
    public void shutdown() {
        factory.close();
    }

    protected Person findPerson(Session session, String name) {
        Query query = session.createQuery("from Person p where p.name = :name");
        query.setParameter("name", name);
        Person person = (Person) query.uniqueResult();
        return person;
    }

    protected Person savePerson(Session session, String name) {
        Person p = findPerson(session, name);
        if (p == null) {
            p = new Person();
            p.setName(name);
            session.save(p);
        }
        return p;
    }

    protected Skill findSkill(Session session, String name) {
        Query query = session.createQuery("from Skill s where s.name = :name");
        query.setParameter("name", name);
        Skill skill = (Skill)query.uniqueResult();
        return skill;
    }

    protected Skill saveSkill(Session session, String name) {
        Skill s = findSkill(session, name);
        if (s == null) {
            s = new Skill();
            s.setName(name);
            session.save(s);
        }
        return s;
    }
}
