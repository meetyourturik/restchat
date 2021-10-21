package com.epam.turik.restchat;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import org.springframework.data.domain.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {
    private Map<Long, UserEntity> users = new HashMap<>();
    private long counter = 1l;

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return Optional.of(users.get(id));
    }

    @Override
    public List<UserEntity> findAll(Example<UserEntity> userEntityExample) {
        return null;
    }

    @Override
    public <S extends UserEntity> S save(S s) {
        users.put(counter, s);
        s.setId(counter++);
        return s;
    }

    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<UserEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<UserEntity> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(UserEntity userEntity) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
