package com.jae.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.jae.framework.service.impl.EntityServiceImpl;
import com.jae.weixin.model.WxToken;
import com.jae.weixin.service.WxTokenService;

@Service("WxTokenService")
public class WxTokenServiceImpl extends EntityServiceImpl<WxToken> implements WxTokenService  {

}
