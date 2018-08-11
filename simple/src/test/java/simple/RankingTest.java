package simple;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

public class RankingTest extends BaseTest {

    @Test
    public void testSaveRanking() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Person subject = savePerson(session, "SubjectPerson");
        Person observer = savePerson(session, "ObserverPerson");
        Skill skill = saveSkill(session, "Java");

        tx.commit();
        session.close();
    }
}
