package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.UserRequestDto;
import com.mh.inventory.dtos.UserResponseDto;
import com.mh.inventory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public Response<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        return null;
    }
}
