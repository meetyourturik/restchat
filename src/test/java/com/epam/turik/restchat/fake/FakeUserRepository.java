package com.epam.turik.restchat.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import org.springframework.data.domain.Example;

import java.util.*;

public class FakeUserRepository implements UserRepository {
    private final Map<Long, UserEntity> users = new HashMap<>();
    private long counter = 1L;

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    @Override
    public List<UserEntity> findAll(Example<UserEntity> userEntityExample) {
        UserEntity probe = userEntityExample.getProbe();
        List<UserEntity> result = new ArrayList<>();

        String username = probe.getUsername();
        String language = probe.getLanguage();
        UserStatus status = probe.getStatus();
        ChatPermission permission = probe.getChatPermission();

        for (UserEntity entity : users.values()) {
            if ((username == null || username.isEmpty() || entity.getUsername().startsWith(username)) &&
                (language == null || language.isEmpty() || entity.getLanguage().startsWith(language)) &&
                (status == null || entity.getStatus().equals(status)) &&
                (permission == null || entity.getChatPermission().equals(permission))
            ) {
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public <S extends UserEntity> S save(S s) {
        Long id = s.getId();
        if (id != null) {
            users.put(id, s);
        } else {
            long nextId = counter++;
            s.setId(nextId);
            users.put(nextId, s);
        }
        return s;
    }

    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        for (S s : iterable) {
            Long id = s.getId();
            if (id != null) {
                users.put(id, s);
            } else {
                long nextId = counter++;
                s.setId(nextId);
                users.put(nextId, s);
            }
        }
        return iterable;
    }

    @Override
    public Optional<UserEntity> findById(Long aLong) {
        return this.findUserById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return users.containsKey(aLong);
    }

    @Override
    public Iterable<UserEntity> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Iterable<UserEntity> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void deleteById(Long aLong) {
        users.remove(aLong);
    }

    @Override
    public void delete(UserEntity userEntity) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> iterable) {

    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
