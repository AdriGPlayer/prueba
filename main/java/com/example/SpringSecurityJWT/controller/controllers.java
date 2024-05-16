package com.example.SpringSecurityJWT.controller;

import com.example.SpringSecurityJWT.controller.request.CreateUserDTO;
import com.example.SpringSecurityJWT.models.ERole;
import com.example.SpringSecurityJWT.models.RoleEntity;
import com.example.SpringSecurityJWT.models.UserEntity;
import com.example.SpringSecurityJWT.repositories.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class controllers{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello(){
        return  "Hello word not secured";
    }

    @GetMapping("/heloSecured")
    public String helloSecured(){
        return "Hello word secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        Set<RoleEntity> roles = createUserDTO.getRoles().stream().map(role -> RoleEntity.builder()
                .name(ERole.valueOf(role)).build()).collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .userName(createUserDTO.getUserName())
                .password(createUserDTO.getEmail())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam long id){
        userRepository.deleteById(id);
        return "se borro el usuario con id " + id;
    }
}
