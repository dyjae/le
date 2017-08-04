package com.jae.len.service.impl;

import org.springframework.stereotype.Service;

import com.jae.framework.service.impl.EntityServiceImpl;
import com.jae.len.model.LeUser;
import com.jae.len.service.LeUserService;

@Service("LeUserService")
public class LeUserServiceImpl extends EntityServiceImpl<LeUser> implements LeUserService  {

}
