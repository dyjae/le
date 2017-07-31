package com.jae.program.service.impl;


import com.jae.framework.service.impl.EntityServiceImpl;
import com.jae.program.model.User;
import com.jae.program.service.UserService;
import org.springframework.stereotype.Service;

@Service(value="userService")
public class UserServiceImpl extends EntityServiceImpl<User> implements UserService {

}
