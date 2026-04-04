package com.gatepass.services;

import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Resident;
import com.gatepass.data.models.Types;
import com.gatepass.data.repositories.GatePassRepository;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GateAccessServiceTest {

    @Autowired
    private GateAccessService gateAccessService;

    @Autowired
    private GatePassRepository gatePassRepository;

    @Autowired
    private ResidentRepository residentRepository;

    private Resident savedResident;

    @BeforeEach
    void setUp() {
        gatePassRepository.deleteAll();
        residentRepository.deleteAll();

        Resident resident = new Resident();
        resident.setPhoneNumber("08012345678");
        resident.setEnabled(true);

        savedResident = residentRepository.save(resident);
    }

    @Test
    void testThatICanGenerateExitCode() {
        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setCode("123456");

        GenerateExitCodeResponse response =
                gateAccessService.generateExitCode(request);

        assertNotNull(response);
        assertNotNull(response.getCode());

        GatePass savedPass = gatePassRepository.findByCode(response.getCode());
        assertNotNull(savedPass);
        assertEquals(Types.EXIT, savedPass.getPassType());
    }

    @Test
    void testThatIGenerateResidentEntryCode() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(savedResident.getId());
        request.setPhoneNumber(savedResident.getPhoneNumber());
        request.setValidTill(LocalTime.now().plusHours(2));

        GenerateResidentEntryCodeResponse response =
                gateAccessService.generateResidentEntryCode(request);

        assertNotNull(response);
        assertNotNull(response.getCode());
        assertEquals("Resident entry code generated", response.getMessage());

        GatePass pass = gatePassRepository.findByCode(response.getCode());
        assertNotNull(pass);
        assertEquals(Types.ENTRY, pass.getPassType());
    }

    @Test
    void testThatICanGenerateVisitorEntryCode() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentId(savedResident.getId());
        request.setVisitorName("John Doe");
        request.setVisitorphoneNumber("09000000000");
        request.setPurposeOfVisit("Delivery");

        GenerateVisitorEntryCodeResponse response =
                gateAccessService.generateVisitorEntryCode(request);

        assertNotNull(response);
        assertNotNull(response.getCode());
        assertEquals("Visitor entry code generated", response.getMessage());

        GatePass pass = gatePassRepository.findByCode(response.getCode());
        assertNotNull(pass);
        assertEquals(Types.ENTRY, pass.getPassType());
    }

    @Test
    void testThatICanDisableCode() {
        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setCode("12345");

        GenerateExitCodeResponse response =
                gateAccessService.generateExitCode(request);

        String code = response.getCode();

        String result = gateAccessService.disableCode(code);
        assertEquals("Code disabled", result);

        GatePass pass = gatePassRepository.findByCode(code);
        assertNotNull(pass);
        assertFalse(pass.isActive());
    }

    @Test
    void testThatICanValidateCodeForResidentEntry() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(savedResident.getId());
        request.setPhoneNumber(savedResident.getPhoneNumber());
        request.setValidTill(LocalTime.now().plusHours(2));

        GenerateResidentEntryCodeResponse response =
                gateAccessService.generateResidentEntryCode(request);

        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(response.getCode());

        ValidateCodeResponse result =
                gateAccessService.validateCode(validateRequest);

        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals("Entry allowed", result.getMessage());
    }

    @Test
    void testThatVisitorCannotEnterWhenCodeIsDisabled() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentId(savedResident.getId());
        request.setVisitorName("Jane Doe");
        request.setVisitorphoneNumber("09011111111");
        request.setPurposeOfVisit("Meeting");

        GenerateVisitorEntryCodeResponse response =
                gateAccessService.generateVisitorEntryCode(request);

        String code = response.getCode();

        gateAccessService.disableCode(code);

        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(code);

        ValidateCodeResponse result =
                gateAccessService.validateCode(validateRequest);

        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals("Code is disabled", result.getMessage());
    }

    @Test
    void testThatICanExtendTime() {
        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setCode("12345");

        GenerateExitCodeResponse response =
                gateAccessService.generateExitCode(request);

        String code = response.getCode();

        gateAccessService.extendTime(code, "2030-12-30 10:30");

        GatePass pass = gatePassRepository.findByCode(code);
        assertNotNull(pass);
        assertNotNull(pass.getExpiresAt());
    }

    @Test
    void testThatICanGenerateEntryCodeTwice() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(savedResident.getId());
        request.setPhoneNumber(savedResident.getPhoneNumber());
        request.setValidTill(LocalTime.now().plusHours(2));

        GenerateResidentEntryCodeResponse firstResponse =
                gateAccessService.generateResidentEntryCode(request);

        GenerateResidentEntryCodeResponse secondResponse =
                gateAccessService.generateResidentEntryCode(request);

        assertNotNull(firstResponse.getCode());
        assertNotNull(secondResponse.getCode());
        assertNotEquals(firstResponse.getCode(), secondResponse.getCode());

        GatePass firstPass = gatePassRepository.findByCode(firstResponse.getCode());
        GatePass secondPass = gatePassRepository.findByCode(secondResponse.getCode());

        assertNotNull(firstPass);
        assertNotNull(secondPass);
        assertEquals(Types.ENTRY, firstPass.getPassType());
        assertEquals(Types.ENTRY, secondPass.getPassType());
    }
}