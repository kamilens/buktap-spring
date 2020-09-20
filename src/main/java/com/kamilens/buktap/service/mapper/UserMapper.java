package com.kamilens.buktap.service.mapper;

import com.kamilens.buktap.entity.User;
import com.kamilens.buktap.entity.enumeration.UserRole;
import com.kamilens.buktap.entity.enumeration.UserStatus;
import com.kamilens.buktap.service.dto.UserRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", expression = "java(getDefaultUserRole())")
    @Mapping(target = "status", expression = "java(getDefaultUserStatus())")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "favGenres", ignore = true)
    @Mapping(target = "library", ignore = true)
    User registerDTOToEntity(UserRegisterDTO userRegisterDTO);

    default UserRole getDefaultUserRole() {
        return UserRole.USER;
    }

    default UserStatus getDefaultUserStatus() {
        return UserStatus.WAITING_FOR_VERIFICATION;
    }

}
