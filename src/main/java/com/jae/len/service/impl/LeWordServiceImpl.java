package com.jae.len.service.impl;

import org.springframework.stereotype.Service;

import com.jae.framework.service.impl.EntityServiceImpl;
import com.jae.len.model.LeWord;
import com.jae.len.service.LeWordService;

@Service("LeWordService")
public class LeWordServiceImpl extends EntityServiceImpl<LeWord> implements LeWordService  {

}
