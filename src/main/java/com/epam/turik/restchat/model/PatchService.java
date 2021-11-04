package com.epam.turik.restchat.model;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PatchService {
    public User applyPatch(User user, UserUpdate patch)  {
        if (patch.getUsername() != null) {
            user.setUsername(patch.getUsername().isPresent() ? patch.getUsername().get() : null);
        }
        // TODO etc...
        return user;
    }
}
