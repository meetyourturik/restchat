package com.epam.turik.restchat.model;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateService {
    /**
     *
     * @param user
     * @param patch - null field means ignore, empty means remove, present means update
     */

    public void applyUpdate(User user, UserUpdate patch) {
        if (patch.getUsername() != null) {
            user.setUsername(patch.getUsername().isPresent() ? patch.getUsername().get() : null);
        }
        if (patch.getStatus() != null) {
            user.setStatus(patch.getStatus().isPresent() ? patch.getStatus().get() : null);
        }
        if (patch.getEmail() != null) {
            user.setEmail(patch.getEmail().isPresent() ? patch.getEmail().get() : null);
        }
        if (patch.getTimezone() != null) {
            user.setTimezone(patch.getTimezone().isPresent() ? patch.getTimezone().get() : null);
        }
        if (patch.getLanguage() != null) {
            user.setLanguage(patch.getLanguage().isPresent() ? patch.getLanguage().get() : null);
        }
        if (patch.getDeletionDate() != null) {
            user.setDeletionDate(patch.getDeletionDate().isPresent() ? patch.getDeletionDate().get() : null);
        }
        if (patch.getChatPermission() != null) {
            user.setChatPermission(patch.getChatPermission().isPresent() ? patch.getChatPermission().get() : null);
        }
        if (patch.getIp() != null) {
            user.setIp(patch.getIp().isPresent() ? patch.getIp().get() : null);
        }
    }
}
