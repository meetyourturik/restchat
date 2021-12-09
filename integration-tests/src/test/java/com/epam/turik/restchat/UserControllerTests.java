package com.epam.turik.restchat;

import com.epam.turik.restchat.infrastructure.ComponentTest;
import com.epam.turik.restchat.rest.UserController;
import com.epam.turik.restchat.rest.UserRestMapper;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ComponentTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTests {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserController userController;

	@Autowired
	private UserRestMapper userRestMapper;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	@SneakyThrows
	@Test
	@Order(2)
	void getAllUsersReturnsNullWhenNoUsers() {
		mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	@Order(3)
	void creatingUserMockMvc() {
		UserDTO user = new UserDTO();
		user.setUsername("test user");
		user.setStatus("ACTIVE");
		user.setEmail("test@user.us");
		user.setTimezone("EN/US");
		user.setLanguage("english");
		user.setDeletionDate("2021-05-08 13:37:58.068");
		user.setChatPermission("EVERYONE");
		user.setIp("192.168.0.2");

		String s = mapper.writeValueAsString(user);

		mockMvc.perform(
			post("/users")
						.content(s)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	@Order(4)
	void getAllUsersWhenOneIsCreated() {
		ResultActions resultActions = mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
		MvcResult result = resultActions.andReturn();
		List<UserDTO> users = (List<UserDTO>) mapper.readValue(result.getResponse().getContentAsString(), List.class);
		assertThat(users.size()).isEqualTo(1);
	}

	@SneakyThrows
	@Test
	@Order(5)
	void deleteUser() {
		mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	@Order(6)
	void getAllUsersWhenOneIsDeleted() {
		ResultActions resultActions = mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
		MvcResult result = resultActions.andReturn();
		List<UserDTO> users = (List<UserDTO>) mapper.readValue(result.getResponse().getContentAsString(), List.class);
		assertThat(users).isEmpty();
	}
}
