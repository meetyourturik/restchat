package com.epam.turik.restchat.model.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class FakeUserRepository implements UserRepository {
    private final Map<Long, UserEntity> users = new HashMap<>();
    private long counter = 1L;

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
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
    public <S extends UserEntity> List<S> saveAll(Iterable<S> iterable) {
        List<S> res = new ArrayList<>();
        for (S s : iterable) {
            Long id = s.getId();
            if (id != null) {
                users.put(id, s);
            } else {
                long nextId = counter++;
                s.setId(nextId);
                users.put(nextId, s);
            }
            res.add(s);
        }
        return res;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserEntity> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<UserEntity> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        S probe = example.getProbe();
        List<S> result = new ArrayList<>();

        String username = probe.getUsername();
        String language = probe.getLanguage();
        UserStatus status = probe.getStatus();
        ChatPermission permission = probe.getChatPermission();

        for (UserEntity uentity : users.values()) {
            S entity = (S) uentity;
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
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        return false;
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
    public List<UserEntity> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<UserEntity> findAllById(Iterable<Long> iterable) {
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
