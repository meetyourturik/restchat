package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.rest.objects.OperationDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> user);

    List<User> fromDTOList(List<UserDTO> userDTO);

    default Timestamp timestampToString(String str) {
        return str == null ? null : Timestamp.valueOf(str);
    }

    default String stringToTimestamp(Timestamp timestamp) {return timestamp == null ? null : timestamp.toString();}

    default UserUpdate toUserUpdate(List<OperationDTO> operations) {
        // null - do nothing, Optional.of(value) - set value (null sets null? aka deletes)
        UserUpdate update = new UserUpdate();

        for (OperationDTO operation: operations) {
            OperationDTO.OperationType type = operation.getOperationType();
            String stringValue = type.equals(OperationDTO.OperationType.REPLACE) ? operation.getValue() : null;
            String path = operation.getPath();
            switch (path) {
                case "username": update.setUsername(Optional.ofNullable(stringValue));
                    break;
                case "status": update.setStatus(stringValue == null ? Optional.empty() : Optional.of(UserStatus.valueOf(stringValue)));
                    break;
                case "email": update.setEmail(Optional.ofNullable(stringValue));
                    break;
                case "timezone": update.setTimezone(Optional.ofNullable(stringValue));
                    break;
                case "language": update.setLanguage(Optional.ofNullable(stringValue));
                    break;
                case "deletionDate": update.setDeletionDate(Optional.ofNullable(this.timestampToString(stringValue)));
                    break;
                case "chatPermission": update.setChatPermission(stringValue == null ? Optional.empty() : Optional.of(ChatPermission.valueOf(stringValue)));
                    break;
                case "ip": update.setIp(Optional.ofNullable(stringValue));
                    break;
                default: throw new RuntimeException("no such path " + path); // TODO: replace with actual exception
            }
        }
        return update;
    }
}
