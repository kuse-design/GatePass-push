package com.gatepass.services;

import com.gatepass.data.models.Resident;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.dtos.request.OnboardResidentRequest;
import com.gatepass.dtos.responses.OnboardResidentResponse;
import com.gatepass.exceptions.ResidentAlreadyRegisteredException;
import com.gatepass.exceptions.ResidentDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResidentManagementServiceTest {
    @Autowired
    private ResidentManagementService residentManagementService;

    @Autowired
    private ResidentRepository residentRepository;

    @BeforeEach
    void setUp() {
        residentRepository.deleteAll();
    }

    @Test
    void testThatICanOnboardResidentSuccessfully() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John Doe");
        request.setPhoneNumber("123");

        OnboardResidentResponse response =
                residentManagementService.onboardResident(request);

        assertEquals("John Doe", response.getResidentName());
    }

    @Test
    void onboardedResidentShouldBeSavedInRepository() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John");
        request.setPhoneNumber("123");

        residentManagementService.onboardResident(request);

        Resident resident = residentRepository.findByPhoneNumber("123");

        assertNotNull(resident);
    }

    @Test
    void testThatDuplicateResidentShouldThrowException() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John");
        request.setPhoneNumber("123");

        residentManagementService.onboardResident(request);

        assertThrows(ResidentAlreadyRegisteredException.class, () -> {
            residentManagementService.onboardResident(request);
        });
    }


    @Test
    void testThatToviewResidentsShouldReturnEmptyListInitially() {
        assertEquals(0, residentManagementService.viewResident().size());
    }
    @Test
    void thatThatICanViewAllResidents() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John");
        request.setPhoneNumber("123");

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("456");

        residentManagementService.onboardResident(request);
        residentManagementService.onboardResident(request2);

        assertEquals(2, residentManagementService.viewResident().size());
    }

    @Test
    void testThatICanDeleteResidentSuccessfully() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("John");
        request.setPhoneNumber("123");

        residentManagementService.onboardResident(request);

        residentManagementService.deleteResident("123");

        assertEquals(0, residentRepository.count());
    }

    @Test
    void testThatdeletingNonExistingResidentShouldThrowException() {
        assertThrows(ResidentDoesNotExistException.class, () -> {
            residentManagementService.deleteResident("999");
        });
    }

    @Test
    void testThatICanDisableResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("123");
        residentManagementService.onboardResident(request);
        residentManagementService.disableResident("123");
        Resident resident = residentRepository.findByPhoneNumber("123");

        assertFalse(resident.isEnabled());
    }

    @Test
    void testThatdisablingNonExistingResidentShouldThrowException() {
        assertThrows(ResidentDoesNotExistException.class, () -> {
            residentManagementService.disableResident("123");
        });
    }

    @Test
    void shouldDeleteDisabledResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("123");

        residentManagementService.onboardResident(request);
        residentManagementService.disableResident("123");

        residentManagementService.deleteResident("123");

        assertEquals(0, residentRepository.count());
    }

    @Test
    void testThatICanAllowDifferentResidents() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("111");

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("kuse");
        request2.setPhoneNumber("222");

        residentManagementService.onboardResident(request);
        residentManagementService.onboardResident(request2);

        assertEquals(2, residentRepository.count());
    }

    @Test
    void thatThatICanAllowSameNameDifferentPhoneShouldBeAllowed() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("kuse");
        request.setPhoneNumber("111");

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("John");
        request2.setPhoneNumber("222");

        residentManagementService.onboardResident(request);
        residentManagementService.onboardResident(request2);

        assertEquals(2, residentRepository.count());
    }
}