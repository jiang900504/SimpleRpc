package com.jiang.rpc.core;

import java.lang.reflect.Proxy;

/**
 * @Description:Rpc客户端执行模块
 * @author hc
 *
 */
public class MessageSendExecutor {
	private RpcServerLoader loader = RpcServerLoader.getInstance();

	public MessageSendExecutor(String serverAddress) {
		loader.load(serverAddress);
	}

	public void stop() {
		loader.unLoad();
	}

	public static <T> T execute(Class<T> rpcInterface) {
		return (T) Proxy.newProxyInstance(rpcInterface.getClassLoader(), new Class<?>[] { rpcInterface },
				new MessageSendProxy<T>(rpcInterface));
	}
}
