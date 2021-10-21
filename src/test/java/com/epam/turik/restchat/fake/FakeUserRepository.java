package com.epam.turik.restchat.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import org.springframework.data.domain.Example;

import java.util.*;

public class FakeUserRepository implements UserRepository {
    private Map<Long, UserEntity> users = new HashMap<>();
    private long counter = 1l;

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    @Override
    public List<UserEntity> findAll(Example<UserEntity> userEntityExample) {
        return null;
    }

    @Override
    public <S extends UserEntity> S save(S s) {
        long nextId = counter++;
        s.setId(nextId);
        users.put(nextId, s);
        return s;
    }

    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        Iterator<S> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            S s = iterator.next();
            long nextId = counter++;
            s.setId(nextId);
            users.put(nextId, s);
        }
        return iterable;
    }

    @Override
    public Optional<UserEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return users.containsKey(aLong);
    }

    @Override
    public Iterable<UserEntity> findAll() {
        return new ArrayList<UserEntity>(users.values());
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

    }
}
