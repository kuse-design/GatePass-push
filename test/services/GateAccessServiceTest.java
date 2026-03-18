package services;

import data.models.GatePass;
import data.models.Types;
import data.repositories.GatePassRepository;
import data.repositories.GatePasses;
import data.repositories.ResidentRepository;
import data.repositories.Residents;
import dtos.request.GenerateResidentEntryCodeRequest;
import dtos.request.GenerateVisitorEntryCodeRequest;
import dtos.request.ValidateCodeRequest;
import dtos.responses.GenerateResidentEntryCodeResponse;
import dtos.responses.GenerateVisitorEntryCodeResponse;
import dtos.responses.ValidateCodeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GateAccessServiceTest {
    private  GateAccessService gateAccessService;
    private GatePassRepository gatePassRepository;
    private ResidentRepository residentRepository;

    @BeforeEach
    void setUp() {
        residentRepository = new Residents();
        gatePassRepository = new GatePasses();
        gateAccessService = new GateAccessService(residentRepository, gatePassRepository);

    }


    @Test
    void testThatICanGenerateExitCode() {
        String exitCode = gateAccessService.generateExitCode("123456");
        assertNotNull(exitCode);
        GatePass savedPass = gatePassRepository.findByCode(exitCode);
        assertEquals(Types.EXIT, savedPass.getPassType());
    }

    @Test
    void testGenerateResidentEntryCode() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();

        GenerateResidentEntryCodeResponse response =
                gateAccessService.generateResidentEntryCode(request);

        assertNotNull(response.getCode());
        assertEquals("Resident entry code generated", response.getMessage());

        GatePass pass = gatePassRepository.findByCode(response.getCode());
        assertEquals(Types.ENTRY, pass.getPassType());
    }

    @Test
    void testThatICanGenerateVisitorEntryCode() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        GenerateVisitorEntryCodeResponse response = gateAccessService.generateVisitorEntryCode(request);

        assertNotNull(response.getCode());
        assertEquals("Visitor entry code generated", response.getMessage());

        GatePass pass = gatePassRepository.findByCode(response.getCode());
        assertEquals(Types.ENTRY,pass.getPassType());
    }

    @Test
    void testThatICanDisableCode() {
        String code = gateAccessService.generateExitCode("12345");
        String result = gateAccessService.disableCode(code);
        assertEquals("Code disabled", result);

        GatePass pass = gatePassRepository.findByCode(code);
        assertFalse(pass.isActive());
    }

    @Test
    void testThatICanValidateCodeForResidentEntry() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        GenerateResidentEntryCodeResponse response = gateAccessService.generateResidentEntryCode(request);

        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(response.getCode());
        ValidateCodeResponse result = gateAccessService.validateCode(validateRequest);

        assertTrue(result.isValid());
        assertEquals("Entry allowed", result.getMessage());
    }

    @Test
    void testThatVisitorCannotEnterWhenCodeIsDisabled() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        GenerateVisitorEntryCodeResponse response = gateAccessService.generateVisitorEntryCode(request);
        String code = response.getCode();
        gateAccessService.disableCode(code);
        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(code);
        ValidateCodeResponse result = gateAccessService.validateCode(validateRequest);

        assertFalse(result.isValid());
        assertEquals("Code is disabled", result.getMessage());
    }

    @Test
    void testThatICanExtendTime() {
        String code = gateAccessService.generateExitCode("12345");
        gateAccessService.extendTime(code, "2030-12-30 10:30");

        GatePass pass = gatePassRepository.findByCode(code);
        assertNotNull(pass.getExpiresAt());
    }

    @Test
    void testThatICanGenerateEntryCodeTwice() {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        GenerateResidentEntryCodeResponse firstResponse = gateAccessService.generateResidentEntryCode(request);
        GenerateResidentEntryCodeResponse secondResponse = gateAccessService.generateResidentEntryCode(request);

        assertNotNull(firstResponse.getCode());
        assertNotNull(secondResponse.getCode());
        assertNotEquals(firstResponse.getCode(), secondResponse.getCode());

        GatePass firstPass = gatePassRepository.findByCode(firstResponse.getCode());
        GatePass secondPass = gatePassRepository.findByCode(secondResponse.getCode());

        assertEquals(Types.ENTRY, firstPass.getPassType());
        assertEquals(Types.ENTRY, secondPass.getPassType());
    }
}