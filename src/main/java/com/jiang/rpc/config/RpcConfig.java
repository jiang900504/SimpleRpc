package com.jiang.rpc.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jiang.rpc.core.MessageRecvExecutor;
import com.jiang.rpc.model.MessageKeyVal;
import com.jiang.rpc.service.Calculate;
import com.jiang.rpc.service.CalculateImpl;

@Configuration
public class RpcConfig {

	@Bean
	public MessageKeyVal messageKeyVal() {
		MessageKeyVal messageKeyVal = new MessageKeyVal();
		Map<String, Object> message = new HashMap<String, Object>();
		message.put(Calculate.class.getName(), new CalculateImpl());
		messageKeyVal.setMessageKeyVal(message);
		return messageKeyVal;
	}

	@Bean
	public MessageRecvExecutor messageRecvExecutor() {
		MessageRecvExecutor messageRecvExecutor = new MessageRecvExecutor("127.0.0.1:9999");
		return messageRecvExecutor;
	}
}
