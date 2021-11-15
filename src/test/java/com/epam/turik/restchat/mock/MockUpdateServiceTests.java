package com.epam.turik.restchat.mock;

import com.epam.turik.restchat.model.UpdateService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
public class MockUpdateServiceTests {
    @InjectMocks
    UpdateService updateService;

    private User user;

    @BeforeEach
    private void init() {
        user = new User();
        user.setUsername("before");
    }

    @Test
    void testUpdateField() {
        UserUpdate patch = new UserUpdate();
        patch.setUsername(Optional.of("after"));
        updateService.applyUpdate(user, patch);
        assertEquals("after", user.getUsername());
    }

    @Test
    void testRemoveField() {
        UserUpdate patch = new UserUpdate();
        patch.setUsername(Optional.empty());
        updateService.applyUpdate(user, patch);
        assertNull(user.getUsername());
    }
}
