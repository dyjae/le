package com.jae.len.service.impl;

import org.springframework.stereotype.Service;

import com.jae.framework.service.impl.EntityServiceImpl;
import com.jae.len.model.LePhonetic;
import com.jae.len.service.LePhoneticService;

@Service("LePhoneticService")
public class LePhoneticServiceImpl extends EntityServiceImpl<LePhonetic> implements LePhoneticService  {

}
