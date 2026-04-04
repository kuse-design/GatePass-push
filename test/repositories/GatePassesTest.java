//package repositories;
//
//import com.gatepass.data.models.GatePass;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class GatePassesTest {
//
//    private GatePasses repo;
//
//    @BeforeEach
//    public void startApp() {
//        repo = new GatePasses();
//    }
//
//    @Test
//    public void  testGatePassIsEmpty() {
//        assertEquals(0,repo.count());
//    }
//
//    @Test
//    public void testThatICanSave() {
//        repo.save(new GatePass());
//        assertEquals(1, repo.count());
//    }
//
//    @Test
//    public void testThatICanSaveTwice(){
//        repo.save(new GatePass());
//        repo.save(new GatePass());
//        assertEquals(2, repo.count());
//
//    }
//
//
//    @Test
//    public void testThatICanFindGatePassById() {
//        GatePass gatePass1 = new GatePass();
//        repo.save(gatePass1);
//        GatePass gatePass2 = new GatePass();
//        repo.save(gatePass2);
//
//        GatePass found = repo.findById(2);
//        assertEquals(gatePass2, found);
//    }
//
//
//    @Test
//    public void testThatICanDeleteByGatePassId(){
//        GatePass gatePass1 = new GatePass();
//        repo.save(gatePass1);
//        GatePass found = repo.findById(1);
//        repo.deleteById(1);
//        assertEquals(0, repo.count());
//
//    }
//
//    @Test
//    public void testThatICanDeleteByIdFromMultiplePass(){
//        GatePass gatePass1 = new GatePass();
//        repo.save(gatePass1);
//        GatePass gatePass2 = new GatePass();
//        repo.save(gatePass2);
//        GatePass gatePass3 = new GatePass();
//        repo.save(gatePass3);
//
//        GatePass found = repo.findById(2);
//        repo.deleteById(2);
//        assertEquals(2, repo.count());
//    }
//
//    @Test
//    public void testICanDeleteAllGatePasses(){
//        GatePass gatePass1 = new GatePass();
//        repo.save(gatePass1);
//        GatePass gatePass2 = new GatePass();
//        repo.save(gatePass2);
//        GatePass gatePass3 = new GatePass();
//        repo.save(gatePass3);
//
//        repo.deleteAll();
//        assertEquals(0,repo.count());
//
//    }
//}
//
