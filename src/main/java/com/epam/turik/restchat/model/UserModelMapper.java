package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.rest.objects.OperationDTO;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserEntity toEntity(User user);

    User fromEntity(UserEntity userEntity);

    List<UserEntity> toEntityList(List<User> users);

    List<User> fromEntityList(List<UserEntity> userEntity);

    default String inetToString(Inet inet) {
        return inet == null ? null : inet.getAddress();
    }

    default Inet toInet(String string) {
        return string == null ? null : new Inet(string);
    }

    default UserUpdate toUserUpdate(List<OperationDTO> operations) {
        // null - do nothing, Optional.of(value) - set value (null sets null?)
        UserUpdate update = new UserUpdate();

        for (OperationDTO operation: operations) {
            OperationDTO.OperationType type = operation.getOperationType();
            String stringValue = type.equals(OperationDTO.OperationType.REPLACE) ? operation.getValue() : null;
            String path = operation.getPath();
            switch (path) {
                case "username": update.setUsername(Optional.ofNullable(stringValue));
                                 break;
                default: throw new RuntimeException("no such path " + path); // TODO: replace with actual exception
            }
        }
        return update;
    }
}
