package com.asylum.apimanagement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.asylum.apimanagement.exceptions.ApplicationNotFoundException;
import com.asylum.apimanagement.model.Application;
import com.asylum.apimanagement.service.ApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // remove to enable security

class ApplicationControllerTests {

	@Autowired
	MockMvc mvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@MockBean
	private ApplicationServiceImpl service;

	Date mockdate = new Date(19990607);

	Application app1 = new Application(1, "Ananya", "Samar", "Pupala", "Male", mockdate, "India", "India", "2173777889",
			"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
			"Bachelors", "Computer Engineering", false, false, false, false);
	Application app2 = new Application(2, "Ayaan", "Samar", "Pupala", "Male", mockdate, "India", "India", "2173777889",
			"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
			"Bachelors", "Computer Engineering", false, false, false, false);
	Application app3 = new Application(3, "Dhanashree", "Samar", "Pupala", "Male", mockdate, "India", "India",
			"2173777889",
			"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
			"Bachelors", "Computer Engineering", false, false, false, false);

	@Test
	public void getAllRecords_success() throws Exception {
		List<Application> appList = new ArrayList<Application>();
		appList.add(app1);
		appList.add(app2);
		appList.add(app3);

		Mockito.when(service.findAll()).thenReturn(appList);

		mvc.perform(MockMvcRequestBuilders
				.get("/api/applications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Ayaan")));

		assertTrue(true);
	}

	@Test
	public void getApplicationById() throws Exception {

		Mockito.when(service.findById(app1.getId())).thenReturn(java.util.Optional.of(app1));

		mvc.perform(MockMvcRequestBuilders
				.get("/api/applications/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Ananya")));

	}

	@Test
	public void createApplication_success() throws Exception {

		Application newApp = new Application(4, "Samar", "Samar", "Pupala", "Male", mockdate, "India", "India",
				"2173777889",
				"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
				"Bachelors", "Computer Engineering", false, false, false, false);

		Mockito.when(service.saveApplication(any(Application.class))).thenReturn(newApp);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newApp));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Samar")));

	}

	@Test
	public void createApplication_longPhone() throws Exception {

		Application newApp = new Application(4, "Samar", "Samar", "Pupala", "Male", mockdate, "India", "India",
				"2173777885559",
				"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
				"Bachelors", "Computer Engineering", false, false, false, false);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newApp));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Validation Error")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorDetails.[0]",
						Matchers.is(" Phone number must be a number with less than 12 digits")));

	}

	@Test
	public void createApplication_invalidMiddleName() throws Exception {

		Application newApp = new Application(4, "Samar", "Samarsffwfwfwffwefwefewfwfewfwefewf", "Pupala", "Male",
				mockdate, "India", "India",
				"2173777889",
				"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
				"Bachelors", "Computer Engineering", false, false, false, false);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newApp));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Validation Error")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorDetails.[0]",
						Matchers.is(" Middle name must be less than 20 characters")));

	}

	@Test
	public void updateApplication_success() throws Exception {

		Application newApp = new Application(2, "Samar", "Samar", "Pupala", "Male", mockdate, "India", "India",
				"2173777889",
				"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
				"Bachelors", "Computer Engineering", false, false, false, false);

		Mockito.when(service.findById(newApp.getId())).thenReturn(java.util.Optional.of(newApp));
		Mockito.when(service.saveApplication(any(Application.class))).thenReturn(newApp);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newApp));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Samar")));
	}

	@Test
	public void updateApplication_NotFound() throws Exception {

		Application newApp = new Application(2, "Samar", "Samar", "Pupala", "Male", mockdate, "India", "India",
				"2173777889",
				"ayaansp@gmail.com", "12000 Market St, Unit 252, Reston, VA, 20190", "Indian", 168, "Brown", "Brown",
				"Bachelors", "Computer Engineering", false, false, false, false);

		Mockito.when(service.findById(newApp.getId())).thenReturn(null);
		Mockito.when(service.saveApplication(any(Application.class))).thenReturn(newApp);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newApp));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ApplicationNotFoundException));
	}

	@Test
	public void updateApplication_NoApplication() throws Exception {

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/applications")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(null));

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));
	}

	@Test
	public void deleteApplication_success() throws Exception {

		Mockito.when(service.findById(app2.getId())).thenReturn(java.util.Optional.of(app2));

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/applications/2")
				.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void deleteApplication_NotFound() throws Exception {

		Mockito.when(service.findById(2)).thenReturn(null);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/applications/2")
				.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ApplicationNotFoundException));

	}
}
