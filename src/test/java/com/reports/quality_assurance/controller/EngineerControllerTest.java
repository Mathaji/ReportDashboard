package com.reports.quality_assurance.controller;

import com.reports.quality_assurance.controller.dto.RegisterQualityAssuranceEngineerDTO;
import com.reports.quality_assurance.controller.dto.RegisteredQualityAssuranceEngineerDTO;
import com.reports.quality_assurance.service.QualityAssuranceEngineerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test suite for {@link EngineerController} to verify HTTP endpoints, request parsing, input validation and response payloads
 *
 * @author Karabo Phoshoko
 * @since 26.0.0
 */

@WebMvcTest(EngineerController.class)
public class EngineerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QualityAssuranceEngineerService qualityAssuranceEngineerService;

    private RegisterQualityAssuranceEngineerDTO registerEngineerRequestBody_1;
    private RegisterQualityAssuranceEngineerDTO registerEngineerRequestBody_2;

    private static final String ROOT_URL = "/v1/engineer/";
    private static final String REGISTER_ENDPOINT = "register";
    private static final String ALL_ENGINEERS_ENDPOINT = "getEngineers";
    private static final LocalDateTime TIME_ACCOUNT_CREATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        // Test data
        final String firstName_1;
        final String lastName_1;
        final String username_1;

        final String firstname_2;
        final String lastName_2;
        final String username_2;

        // Assignment of values for the first test
        firstName_1 = "Karabo";
        lastName_1  = "Phoshoko";
        username_1  = "phosk002";

        //Assignment of values for the second test
        firstname_2 = "Nehal";
        lastName_2  = "Mohanlal";
        username_2  = "mohan002";

        // set values
        registerEngineerRequestBody_1 = new RegisterQualityAssuranceEngineerDTO();
        registerEngineerRequestBody_1.setFirstName(firstName_1);
        registerEngineerRequestBody_1.setLastName(lastName_1);
        registerEngineerRequestBody_1.setUsername(username_1);

        registerEngineerRequestBody_2 = new RegisterQualityAssuranceEngineerDTO();
        registerEngineerRequestBody_2.setFirstName(firstname_2);
        registerEngineerRequestBody_2.setLastName(lastName_2);
        registerEngineerRequestBody_2.setUsername(username_2);

    }

    @Test
    void successfulRegisterEngineerRequest() throws Exception
    {
        final String registerUrl;
        final String jsonPayload_1;
        final String jsonPayload_2;
        final RegisteredQualityAssuranceEngineerDTO registeredDTOMockResponse_1;
        final RegisteredQualityAssuranceEngineerDTO registeredDTOMockResponse_2;

        registerUrl = String.format("%s%s", ROOT_URL, REGISTER_ENDPOINT);

        // First user test config
        jsonPayload_1 = objectMapper.writeValueAsString(registerEngineerRequestBody_1);

        registeredDTOMockResponse_1 = new RegisteredQualityAssuranceEngineerDTO(1L,"phosk002", TIME_ACCOUNT_CREATED);

        Mockito.when(qualityAssuranceEngineerService.registerNewEngineer(org.mockito.ArgumentMatchers.any(RegisterQualityAssuranceEngineerDTO.class)))
                     .thenReturn(registeredDTOMockResponse_1);

        // First Test
        mockMvc.perform(post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)

                // Assert HTTP status Code
                .content(jsonPayload_1)).andExpect(status().isCreated())

                // Assert JSON Payload
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("phosk002"))
                .andExpect(jsonPath("$.timeAccountCreated").value(notNullValue()));

        // Second user test config
        jsonPayload_2 = objectMapper.writeValueAsString(registerEngineerRequestBody_2);

        registeredDTOMockResponse_2 = new RegisteredQualityAssuranceEngineerDTO(2L, "mohan002", TIME_ACCOUNT_CREATED);
        Mockito.when(qualityAssuranceEngineerService.registerNewEngineer(org.mockito.ArgumentMatchers.any(RegisterQualityAssuranceEngineerDTO.class)))
                .thenReturn(registeredDTOMockResponse_2);

        // Second Test
        mockMvc.perform(post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)

                // Assert HTTP status Code
                .content(jsonPayload_2)).andExpect(status().isCreated())

                // Assert JSON Payload
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("mohan002"))
                .andExpect(jsonPath("$.timeAccountCreated").value(notNullValue()));
    }
}
