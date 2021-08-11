package com.epam.turik.restchat;

import com.epam.turik.restchat.infrastructure.ComponentTest;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.UserController;
import com.epam.turik.restchat.rest.UserRestMapper;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentTest
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserController userController;

	@Autowired
	private UserRestMapper userRestMapper;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	@SneakyThrows
	@Test
	void getAllUsersReturnsNullWhenNoUsers() {
		mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void creatingUserMockMvc() {
		User user = new User();
		user.setUsername("test user");
		user.setStatus(UserStatus.ACTIVE);
		user.setEmail("test@user.us");
		user.setTimezone("EN/US");
		user.setLanguage("english");
		user.setDeletionDate(new Timestamp(System.currentTimeMillis()));
		user.setChatPermission(ChatPermission.EVERYONE);
		user.setIp("192.168.0.2");

		UserDTO userDTO = userRestMapper.toDTO(user);

		mockMvc.perform(post("/users", userDTO)).andDo(print());
	}

	@Test
	void creatingUser() {
		User user = new User();
		user.setUsername("test user");
		user.setStatus(UserStatus.ACTIVE);
		user.setEmail("test@user.us");
		user.setTimezone("EN/US");
		user.setLanguage("english");
		user.setDeletionDate(new Timestamp(System.currentTimeMillis()));
		user.setChatPermission(ChatPermission.EVERYONE);
		user.setIp("192.168.0.2");

		UserDTO userDTO = userRestMapper.toDTO(user);

		UserDTO createdUser = userController.createUser(userDTO);
		long id = createdUser.getId();
		user.setId(id);

		UserFilter filter = new UserFilter();
		List<UserDTO> users = userController.getAll(filter);
		assertThat(users).isNotEmpty();
		UserDTO onlyUser = users.get(0);

		assertThat(onlyUser).isEqualTo(createdUser);
	}
}
