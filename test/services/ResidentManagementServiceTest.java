package services;

import data.models.Resident;
import dtos.request.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResidentManagementServiceTest {

    private ResidentManagementService service;

    @BeforeEach
    void setUp() {
        service = new ResidentManagementService();
    }

    @Test
    void testThatICanOnboardResident() {

        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("12345");

        OnboardResidentResponse response = service.onboardResident(request);
        assertEquals("kuse", response.getResidentName());
        assertEquals("12345", response.getPhoneNumber());
    }

    @Test
    void testThatCannotOnboardDuplicateResident() {

        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("12345");
        service.onboardResident(request);
        assertThrows(ResidentAlreadyRegisteredException.class, () -> {
            service.onboardResident(request);
        });
    }

    @Test
    void testThatICanDeleteResident() {

        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("12345");

        service.onboardResident(request);
        String result = service.deleteResident("12345");
        assertEquals("Resident deleted successfully", result);
    }

    @Test
    void testThatICanViewResidents() {

        OnboardResidentRequest request1 = new OnboardResidentRequest();
        request1.setName("Jerry");
        request1.setPhoneNumber("123");

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("kuse");
        request2.setPhoneNumber("419");

        service.onboardResident(request1);
        service.onboardResident(request2);
        List<Resident> residents = service.viewResident();
        assertEquals(2, residents.size());
    }


    @Test
    void testDisableResident() {

        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John");
        request.setPhoneNumber("12345");

        service.onboardResident(request);
        String result = service.disableResident("12345");
        assertEquals("John disabled", result);
    }

    @Test
    void testThatToDisableNonExistingResidentThrowsException() {
        assertThrows(ResidentDoesNotExistException.class, () -> {
            service.disableResident("1234");
        });

    }
}