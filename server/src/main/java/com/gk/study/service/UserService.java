package com.gk.study.service;


import com.gk.study.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUserList(String keyword);
    User getAdminUser(User user);
    User getNormalUser(User user);
    void createUser(User user);
    void deleteUser(String id);

    void updateUser(User user);

    User getUserByToken(String token);
    User getUserByUserName(String username);

    User getUserDetail(String userId);

    User getUserDetailByUsernameAndEmail(String username, String email);

    User getUserDetailByNickName(String nickname);

    User getUserByEmail(String email);

    User userDetailByNickname(String id, String nickname);

    User userDetailByPhone(String id, String phone);

    User userDetailByEmail(String id, String email);
}
