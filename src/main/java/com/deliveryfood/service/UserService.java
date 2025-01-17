package com.deliveryfood.service;

import com.deliveryfood.dao.MemberDao;
import com.deliveryfood.dao.UserDao;
import com.deliveryfood.dto.UserDto;
import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService extends MemberService implements IUserService {

    private final UserDao userDao;

    public UserService(MemberDao memberDao, UserDao userDao) {
        super(memberDao);
        this.userDao = userDao;
    }

    @Override
    public boolean certification(UserRequest userRequest, String code) {
        // REGISTER_CODE 와 일치하면 인증 완료

        if(!super.certification(userRequest, code)) {
            // 멤버 이슈가 있음
            return false;
        }

        UserDto userDto = userDao.findById(userRequest.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        return true;
    }

    @Override
    public boolean register(UserInput userInput) {
        String uuid = UUID.randomUUID().toString();
        if(!super.register(userInput, uuid)) {
            // 멤버 이슈가 있음
            return false;
        }

        if(userDao.findById(uuid) != null) {
            // 중복 유저 존재
            return false;
        }

        UserDto userDto = UserDto.builder()
                .userId(uuid)
                .address(userInput.getAddress())
                .nickname(userInput.getNickname())
                .grade(UserDto.Grade.COMMON)
                .imagePath(userInput.getImagePath())
                .regDt(LocalDateTime.now())
                .build();

        userDao.register(userDto);
        return true;
    }

    @Override
    public boolean withdraw(UserRequest userRequest) {
        if(!super.withdraw(userRequest)) {
            // 멤버 이슈가 있음
            return false;
        }

        UserDto userDto = userDao.findById(userRequest.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        return true;
    }

    @Override
    public boolean login(UserRequest userRequest) {
        return super.login(userRequest);
    }

    @Override
    public boolean modifyUser(UserInput userInput) {
        UserDto userDto = userDao.findById(userInput.getEmail());
        if(userDto == null) {
            // 유저가 존재하지 않음
            return false;
        }

        userDto.setAddress(userInput.getAddress());
        userDto.setNickname(userInput.getNickname());
        userDto.setImagePath(userInput.getImagePath());
        userDao.updateUser(userDto);
        return true;
    }

    @Override
    public UserDto findUser(String email) {
        UserDto userDto = userDao.findById(email);
        if(userDto == null) {
            // 유저가 존재하지 않음
            return null;
        }

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
