package pl.poznan.demo.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc()
public class ProjectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void list() throws Exception {
		String jsonString = """
				{
				    "page": 0,
				    "size": "1"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/list").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.page").value(0)).andExpect(jsonPath("$.size").value(1))
				.andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content", hasSize(1)));
	}

	@Test
	void get() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/projects/1").with(httpBasic("user", "password")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
	}
	
	@Test
	void create_success() throws Exception {
		String jsonString = """
				{
				    "name": "project name"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.name").value("project name"));
	}
	
	@Test
	void create_blankProjectname() throws Exception {
		String jsonString = """
				{
				    "name": " "
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap()).andExpect(jsonPath("$.fields.name").isNotEmpty());
	}
	
	@Test
	void create_missingProjectname() throws Exception {
		String jsonString = """
				{
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap()).andExpect(jsonPath("$.fields.name").isNotEmpty());
	}
	
	@Test
	void update_success() throws Exception {
		String jsonString = """
				{
				    "name": "new project name"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("new project name"));
	}
	
	@Test
	void update_blankProjectname() throws Exception {
		String jsonString = """
				{
				    "name": " "
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap()).andExpect(jsonPath("$.fields.name").isNotEmpty());
	}
	
	@Test
	void update_missingProjectname() throws Exception {
		String jsonString = """
				{
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap()).andExpect(jsonPath("$.fields.name").isNotEmpty());
	}
	
	@Test
	void update_projectDoNotExist() throws Exception {
		String jsonString = """
				{
				    "name": "new project name"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/999").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap()).andExpect(jsonPath("$.fields.projectId").isNotEmpty());
	}
	
	@Test
	void delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/projects/999").with(httpBasic("user", "password"))).andExpect(status().isOk());
	}

}
