package repositories;
import data.models.Resident;
import data.repositories.Residents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResidentTest {
    private Residents repo;

    @BeforeEach
    void setUp() {
        repo = new Residents();
    }

    @Test
    void repositoryStartsEmpty() {
        assertEquals(0, repo.count());
    }

    @Test
    void testThatICanSaveResident() {
        Resident resident = new Resident();
        repo.save(resident);

        assertEquals(1, repo.count());
    }

    @Test
    void testThatSavedResidentHasId() {
        Resident resident = new Resident();
        repo.save(resident);
        assertEquals(1, resident.getId());
    }

    @Test
    void testThatFindAllReturnsAllResidents() {
        repo.save(new Resident());
        repo.save(new Resident());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    void testThatICanDeleteResident() {
        Resident resident = new Resident();
        repo.save(resident);
        repo.delete(resident);

        assertEquals(0, repo.count());
    }

    @Test
    void testThatICanDeleteResidentById() {
        Resident resident = new Resident();
        repo.save(resident);

        repo.deleteById(resident.getId());

        assertEquals(0, repo.count());
    }

    @Test
    void testThatICanDeleteAllResidents() {
        repo.save(new Resident());
        repo.save(new Resident());

        repo.deleteAll();

        assertEquals(0, repo.count());
    }


}