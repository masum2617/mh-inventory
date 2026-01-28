package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.UserRequestDto;
import com.mh.inventory.dtos.UserResponseDto;

public interface UserService {
    Response<UserResponseDto> createUser(UserRequestDto userRequestDto);
}
