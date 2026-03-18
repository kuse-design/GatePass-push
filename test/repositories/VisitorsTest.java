package repositories;

import data.models.Visitor;
import data.repositories.Visitors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorsTest {

    private Visitors repo;

    @BeforeEach
    void setup() {
        repo = new Visitors();
    }

    @Test
    void testThatICanSaveVisitor() {
        repo.save(new Visitor());
        assertEquals(1, repo.count());

    }

    @Test
    void testThatICanFindVisitorById() {
        Visitor visitors1 = new Visitor();
        repo.save(visitors1);
        Visitor visitors2 = new Visitor();
        repo.save(visitors2);

        Visitor found = repo.findById(2);
        assertEquals(visitors2, found);
    }

    @Test
    void testThatICanDeleteVisitorById() {
        Visitor visitors1 = new Visitor();
        repo.save(visitors1);
        Visitor visitors2 = new Visitor();
        repo.save(visitors2);

        Visitor found = repo.findById(2);
        repo.deleteById(1);
        assertEquals(1, repo.count());
    }

    @Test
    void testThatICanDeleteAllVisitor() {
        Visitor visitors1 = new Visitor();
        repo.save(visitors1);
        Visitor visitors2 = new Visitor();
        repo.save(visitors2);

        Visitor found = repo.findById(2);
        repo.deleteAll();
        assertEquals(0, repo.count());

    }
}
