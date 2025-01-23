package pl.poznan.demo.controller;

import static org.hamcrest.Matchers.hasSize;
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
public class ProjectTaskControllerTest {

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

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks/list").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.page").value(0)).andExpect(jsonPath("$.size").value(1))
				.andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content", hasSize(1)));
	}
	
	@Test
	void list_useFilters() throws Exception {
		String jsonString = """
				{
				    "page": 0,
				    "size": "1",
				    "status": "NEW",
				    "priority": "HIGH",
				    "dueDate": "2027-01-23T16:00:00.028Z"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks/list").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.page").value(0)).andExpect(jsonPath("$.size").value(1))
				.andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content", hasSize(0)));
	}

	@Test
	void list_projectDoNotExist_returnEmptyPage() throws Exception {
		String jsonString = """
				{
				    "page": 0,
				    "size": "1"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/999/tasks/list").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.page").value(0)).andExpect(jsonPath("$.size").value(1))
				.andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content", hasSize(0)));
	}

	@Test
	void get() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/1").with(httpBasic("user", "password")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void get_taskDoNotExist_returnNull() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/999").with(httpBasic("user", "password")))
				.andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	void get_projectDoNotExist_returnNull() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/projects/999/tasks/1").with(httpBasic("user", "password")))
				.andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	void create_success() throws Exception {
		String jsonString = """
				{
				    "description": "task description",
				    "status": "NEW",
				    "priority": "LOW",
				    "dueDate": "2027-01-23T16:00:00.028Z"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.description").value("task description"));
	}

	@Test
	void create_invalidDueDate() throws Exception {
		String jsonString = """
				{
				    "description": "task description",
				    "status": "NEW",
				    "priority": "LOW",
				    "dueDate": "notADateValue"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.dueDate").isNotEmpty());
	}

	@Test
	void create_projectDoNotExist() throws Exception {
		String jsonString = """
				{
				    "description": "task description",
				    "status": "NEW",
				    "priority": "LOW"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/999/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.projectId").isNotEmpty());
	}

	@Test
	void create_missingTaskDescriptiob() throws Exception {
		String jsonString = """
				{
				    "status": "NEW",
				    "priority": "LOW"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.description").isNotEmpty());
	}

	@Test
	void create_missingTaskStatus() throws Exception {
		String jsonString = """
				{
				    "description": "task description",
				    "priority": "LOW"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.status").isNotEmpty());
	}

	@Test
	void create_missingTaskPriority() throws Exception {
		String jsonString = """
				{
				    "description": "task description",
				    "status": "NEW"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/projects/1/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.priority").isNotEmpty());
	}

	@Test
	void update_success() throws Exception {
		String jsonString = """
				{
				    "description": "new task description",
				    "status": "IN_PROGRESS",
				    "priority": "HIGH"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/1/tasks/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.description").value("new task description"))
				.andExpect(jsonPath("$.status").value("IN_PROGRESS")).andExpect(jsonPath("$.priority").value("HIGH"));
	}

	@Test
	void update_projectDoNotExist() throws Exception {
		String jsonString = """
				{
				    "description": "new task description",
				    "status": "IN_PROGRESS",
				    "priority": "HIGH"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/999/tasks/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.projectId").isNotEmpty());
	}

	@Test
	void update_taskDoNotExist() throws Exception {
		String jsonString = """
				{
				    "description": "new task description",
				    "status": "IN_PROGRESS",
				    "priority": "HIGH"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.put("/projects/1/tasks/999").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).with(httpBasic("user", "password"))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.fields").isMap())
				.andExpect(jsonPath("$.fields.projectId").isNotEmpty());
	}

	@Test
	void delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/projects/999/tasks/999").with(httpBasic("user", "password")))
				.andExpect(status().isOk());
	}

}
