package cn.zno.smse.rest.service.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zno.smse.rest.service.SomeEndpoint;

@WebService(endpointInterface = "cn.zno.smse.rest.service.SomeEndpoint")
public class SomeEndpointImpl implements SomeEndpoint {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String ping(String input) {
		String msg = "hi ," + input;
		logger.info(msg);
		return msg;
	}

}
