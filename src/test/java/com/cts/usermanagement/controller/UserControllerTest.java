package com.cts.usermanagement.controller;

import com.cts.usermanagement.exception.CustomControllerAdvice;
import com.cts.usermanagement.model.User;
import com.cts.usermanagement.model.UserReqBody;
import com.cts.usermanagement.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @InjectMocks
    private UserController controller;


    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new CustomControllerAdvice()).build();
        this.mapper = new ObjectMapper();
    }

    @AfterEach
    void afterAll() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testSaveUserWhenUserCreated() throws Exception {

        //given
        UserReqBody userRequest = UserReqBody.builder()
                .userName("Vinod Rai")
                .department("IT")
                .managerName("Abhay Srivastva")
                .build();

        User user = User.builder()
                .userId(1234L)
                .build();

        //when
        when(userService.saveUser(any())).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRequest))
                .headers(new HttpHeaders());

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(1234)));


    }

    @Test
    public void testSaveUserWhenInvalidRequestBody() throws Exception {

        //given
        UserReqBody userRequest = UserReqBody.builder()
                .department("IT")
                .managerName("Abhay Srivastva")
                .build();

        User user = User.builder()
                .userId(1234L)
                .build();

        //when
        when(userService.saveUser(any())).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRequest))
                .headers(new HttpHeaders());

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].message", is("User name field should be not empty")));

    }

}
