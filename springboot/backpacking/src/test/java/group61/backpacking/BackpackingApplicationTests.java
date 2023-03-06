package group61.backpacking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {BackPackingController.class, BackpackingApplication.class})
@WebMvcTest
class BackpackingApplicationTests {

	@Autowired private MockMvc mockMvc;

	@Test
	void testRegisterAndLogin() throws Exception {

		String randomName = UUID.randomUUID().toString();
		User newUser = new User(randomName + "@test.com", "123123123", randomName);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(newUser);

		MvcResult result = 
			mockMvc
				.perform(
					MockMvcRequestBuilders.post("/register")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		User returnedUser = mapper.readValue(result.getResponse().getContentAsString(), User.class);
		
		assertEquals(randomName + "@test.com", returnedUser.getEmail());
		assertEquals("123123123", returnedUser.getPassword());
		assertEquals(randomName, returnedUser.getUsername());

		testLogin();
	}

	private void testLogin() throws Exception {

		User userRight = new User("userusedforunittests@test.com", "123123123", "userusedforunittests");
		User userWrong = new User("userusedforunittests@test.com", "321321321", "userusedforunittests");

		ObjectMapper mapper = new ObjectMapper();
		String jsonRight = mapper.writeValueAsString(userRight);
		String jsonWrong = mapper.writeValueAsString(userWrong);

		mockMvc
			.perform(
				MockMvcRequestBuilders.post("/register")
					.content(jsonRight)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		MvcResult result1 = mockMvc
			.perform(
				MockMvcRequestBuilders.post("/login")
					.content(jsonRight)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		MvcResult result2 = mockMvc
			.perform(
				MockMvcRequestBuilders.post("/login")
					.content(jsonWrong)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		mapper.readValue(result1.getResponse().getContentAsString(), User.class);

		assertThrows(Exception.class, () -> {
			mapper.readValue(result2.getResponse().getContentAsString(), User.class);
		});
	}

}
