package com.mh.inventory.controllers;

import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.entity.User;
import com.mh.inventory.entity.UserEntity;
import com.mh.inventory.service.AuthFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthFeignClient authFeignClient;

    @GetMapping
    public Response getUser() {
        User user = User.builder()
                .id(1L)
                .fullName("Masum Hasan")
                .build();

        List<User> userList = (List<User>) List.of(user);
        return ResponseUtils.createSuccessResponse("User info", userList);
    }

    @GetMapping("/user-list")
    public List<UserEntity> getListOfUsers() {

        return authFeignClient.usersList();
    }




}
