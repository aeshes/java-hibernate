package simple;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.testng.annotations.Test;

public class PersonTest extends BaseTest {

    @Test
    public void testSavePerson() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Person person = new Person();
        person.setName("Hello");

        session.save(person);

        tx.commit();
        session.close();
    }
}
