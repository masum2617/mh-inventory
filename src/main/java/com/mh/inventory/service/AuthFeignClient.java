package com.mh.inventory.service;

import com.mh.inventory.config.FeignConfig;
import com.mh.inventory.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-inventory", configuration = FeignConfig.class, url = "${external.shared-url}")
public interface AuthFeignClient {

    @GetMapping("/api/user/get-users")
    public List<UserEntity> usersList();
}
