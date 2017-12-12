package com.jiang.rpc.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description:rpc服务请求结构
 * @author hc
 *
 */
@Data
public class MessageRequest implements Serializable {
	private static final long serialVersionUID = 2376494678357051266L;

	private String messageId;

	private String className;

	private String methodName;

	private Class<?>[] typeParameters;

	private Object[] parameters;
}
