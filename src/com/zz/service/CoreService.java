package com.zz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.zz.message.resp.Article;
import com.zz.message.resp.NewsMessage;
import com.zz.message.resp.TextMessage;
import com.zz.util.MessageUtil;

/**
 * ���ķ�����
 * 
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent =null;

			// xml�������
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				//�����ı����Խ����Զ��ظ�
				respMessage = TextService.processRequest(requestMap);
			}
			// ͼƬ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "�����͵���ͼƬ��Ϣ��";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// ����λ����Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				//���õ�ַ����
				respMessage = AddrSearchService.processRequest(requestMap);
			}
			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "�����͵���������Ϣ��";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "�����͵�����Ƶ��Ϣ��";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = WelcomeService.processRequest(requestMap);
					
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
					respContent="��ӭ�´ζ��ģ�";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
 								
					//����menuservice
					respContent="";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				
					
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
		

	}
}

