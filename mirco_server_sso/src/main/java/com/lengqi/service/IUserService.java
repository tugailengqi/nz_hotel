package com.lengqi.service;

import com.lengqi.entity.User;

public interface IUserService {
    int insert(User user);
    User queryByUserName(String username);
}
