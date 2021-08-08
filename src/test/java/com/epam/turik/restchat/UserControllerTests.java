package com.epam.turik.restchat;

import com.epam.turik.restchat.infrastructure.ComponentTest;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.UserController;
import com.epam.turik.restchat.rest.UserRestMapper;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class UserControllerTests {

	@Autowired
	private UserController userController;

	@Autowired
	private UserRestMapper userRestMapper;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	@Test
	void getAllUsersReturnsNullWhenNoUsers() {
		UserFilter filter = new UserFilter();
		List<UserDTO> users = userController.getAll(filter);
		assertThat(users).isEmpty();
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
