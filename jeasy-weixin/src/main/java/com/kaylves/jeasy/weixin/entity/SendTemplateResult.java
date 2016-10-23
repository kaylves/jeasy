package com.kaylves.jeasy.weixin.entity;
/**
 *发送消息模板返回结果 
 * @author kaylves
 * @date   2016年10月2日 下午3:55:44
 *
 */
public class SendTemplateResult extends BaseResult{
	/**
	 * 发送结果ID
	 */
	private String msgid;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
}
