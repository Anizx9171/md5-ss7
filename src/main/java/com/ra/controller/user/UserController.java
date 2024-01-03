package com.ra.controller.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserRequest;
import com.ra.model.dto.response.UserResponse;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
    ){
        Pageable pageable = PageRequest.of(page,limit);
        Page<UserResponse> response = userService.findAll(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody UserRequest userRequest){
        try {
            UserResponse userResponse = userService.saveOrUpdate(userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("loi roi", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws CustomException {
        UserResponse userResponse = userService.findById(id);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
}
