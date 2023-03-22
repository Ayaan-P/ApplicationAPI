package com.asylum.apimanagement;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.asylum.apimanagement.dao.Request;
import com.asylum.apimanagement.dao.Request.GlobalOperator;
import com.asylum.apimanagement.dao.SearchRequest;
import com.asylum.apimanagement.dao.SearchRequest.Operation;
import com.asylum.apimanagement.model.Application;
import com.asylum.apimanagement.service.ApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(OrderAnnotation.class)
class IntegrationTests {

        public IntegrationTests() throws Exception {

        }

        @Autowired
        MockMvc mvc;
        @Autowired
        ApplicationServiceImpl service;
        Date mockdate = new Date(19990607);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer();

        Application app1 = new Application("Ananya", "Samar", "Pupala", "Male", mockdate, "India", "India",
                        "2173777889",
                        "ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown",
                        "Brown",
                        "Bachelors", "Computer Engineering", false, false, false, false);
        Application app2 = new Application("Ayaan", "Samar", "Pupala", "Male", mockdate, "India", "India",
                        "2173777889",
                        "ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown",
                        "Brown",
                        "Bachelors", "Computer Engineering", false, false, false, false);
        Application app3 = new Application("Ananya2", "Samar", "Pupala", "Male", mockdate, "India", "India",
                        "2173777889",
                        "ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown",
                        "Brown",
                        "Bachelors", "Computer Engineering", false, false, false, false);

        @Test
        @Order(-1)
        public void setUp() throws Exception {

                mvc.perform(MockMvcRequestBuilders.post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(app1)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
                mvc.perform(MockMvcRequestBuilders.post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(app2)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
                mvc.perform(MockMvcRequestBuilders.post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(app3)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());

        }

        @Test
        @Order(1)
        public void createApplication_duplicateApplication() throws Exception {

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(app2));
                mvc.perform(mockRequest)
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                                                Matchers.is("Application with same first name, last name, date of birth pair already exists")));

        }

        @Test
        @Order(1)
        public void getAllRecordsDB_success() throws Exception {

                mvc.perform(MockMvcRequestBuilders
                                .get("http://localhost:8081/api/applications")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Ayaan")));

        }

        @Test
        @Order(1)
        public void getApplicationById_fromDB() throws Exception {

                mvc.perform(MockMvcRequestBuilders
                                .get("/api/applications/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Ananya")));

        }

        @Test
        @Order(1)
        public void searchAnd() throws Exception {

                List<SearchRequest> searchReqs = new ArrayList<>();
                SearchRequest searchReq = new SearchRequest("firstName", "Ayaan", Operation.EQUAL);
                searchReqs.add(searchReq);
                Request req = new Request(searchReqs, GlobalOperator.AND);
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/applications/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(req)).param("page", "0").param("size", "2").param("sort", "ASC").param("orderBy", "id");

                mvc.perform(mockRequest)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Ayaan")));

        }

        @Test
        @Order(1)
        public void searchLike() throws Exception {

                List<SearchRequest> searchReqs = new ArrayList<>();
                SearchRequest searchReq = new SearchRequest("lastName", "P", Operation.LIKE);
                searchReqs.add(searchReq);
                Request req = new Request(searchReqs, GlobalOperator.AND);
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/applications/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(req)).param("page", "0").param("size", "2").param("sort", "ASC").param("orderBy", "id");

                mvc.perform(mockRequest)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Ananya")));

        }

        @Test
        @Order(1)
        public void searchLikePageOrder() throws Exception {

                List<SearchRequest> searchReqs = new ArrayList<>();
                SearchRequest searchReq = new SearchRequest("firstName", "An", Operation.LIKE);
                searchReqs.add(searchReq);
                Request req = new Request(searchReqs, GlobalOperator.AND);
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/applications/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(req)).param("page", "0").param("size", "2").param("sort", "DSC").param("orderBy", "id");

                mvc.perform(mockRequest)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Ananya2")));

        }

}
