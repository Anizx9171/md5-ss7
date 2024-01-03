package com.ra.service.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserRequest;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.User;
import com.ra.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserResponse::new);
    }

    @Override
    public UserResponse saveOrUpdate(UserRequest userRequest) {
        User user = User.builder()
                .userName(userRequest.getUserName())
                .fullName(userRequest.getFullName())
                .password(userRequest.getPassword())
                .build();
        User newUser = userRepository.save(user);
        return UserResponse.builder().userName(newUser.getUserName()).fullName(newUser.getFullName()).id(newUser.getId()).status(newUser.getStatus()).build();
    }

    @Override
    public UserResponse findById(Long id) throws CustomException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return UserResponse.builder()
                    .id(user.getId())
                    .userName(user.getUserName())
                    .fullName(user.getFullName())
                    .status(user.getStatus())
                    .build();
        }
        throw new CustomException("not found");
    }
}

