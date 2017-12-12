package com.jiang.rpc.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description:rpc服务应答结构
 * @author hc
 *
 */
@Data
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = -1502616780755638439L;

	private String messageId;

	private String error;

	private Object result;
}
