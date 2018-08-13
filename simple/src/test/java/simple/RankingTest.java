package simple;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;

public class RankingTest extends BaseTest {

    @Test
    public void testSaveRanking() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Person subject = savePerson(session, "SubjectPerson");
        Person observer = savePerson(session, "ObserverPerson");
        Skill skill = saveSkill(session, "Java");

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(8);

        session.save(ranking);

        tx.commit();
        session.close();
    }

    @Test
    public void testRankings() {
        populateRankingData();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Ranking r where r.subject.name = :name and r.skill.name = :skill");
        query.setParameter("name", "aaa");
        query.setParameter("skill", "Java");

        int sum = 0;
        int count = 0;
        for (Ranking r : (List<Ranking>) query.list()) {
            count++;
            sum += r.getRanking();
            System.out.println(r);
        }
        int average = sum / count;

        tx.commit();
        session.close();
        
        assertEquals(average, 7);
    }

    private void populateRankingData() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        createData(session, "aaa", "xxx", "Java", 6);
        createData(session, "aaa", "yyy", "Java", 7);
        createData(session, "aaa", "zzz", "Java", 8);
        tx.commit();
        session.close();
    }

    private void createData(Session session, String subjectName, String observerName, String skillName, Integer rankingValue) {
        Person subject = savePerson(session, subjectName);
        Person observer = savePerson(session, observerName);
        Skill skill = saveSkill(session, skillName);

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rankingValue);

        session.save(ranking);
    }
}
