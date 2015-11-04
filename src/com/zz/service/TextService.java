package com.zz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz.message.resp.Article;
import com.zz.message.resp.Music;
import com.zz.message.resp.MusicMessage;
import com.zz.message.resp.NewsMessage;
import com.zz.message.resp.TextMessage;
import com.zz.util.MessageUtil;

public class TextService {
	/**
	 * 
	 * @author zz
	 * �û��ı���Ϣ�ظ�
	 */
		public static String processRequest(Map<String, String> requestMap) {
			String respMessage = null;
			try {
				// Ĭ�Ϸ��ص��ı���Ϣ����
				String respContent =null;
				// ���ͷ��ʺţ�open_id��
				String fromUserName = requestMap.get("FromUserName");
				// �����ʺ�
				String toUserName = requestMap.get("ToUserName");
				//��ȡ�û��������ı���Ϣ
				String Content=requestMap.get("Content");
			
				//�����ı���Ϣ
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				
				//���û����ı���Ϣ����ƥ��
				
				
				//����������������
				if(Content.equals("1")){
					respContent=getUsage();
				}
				//������ʳ��������
				else if(Content.equals("2")){
					respContent=getAddruse();
				}
				//������ʷ�ϵĽ���
				else if(Content.equals("3")){
					respContent=TodayInHistoryService.getTodayInHistoryInfo();
				}
				//���ط���ָ��
				else if(Content.equals("4")){
					respContent=getTranslateUsage();
				}
				
				//��ͷΪ����,����и�������
				else if(Content.startsWith("����"))
				{	
					respMessage=MusicService.processRequest(requestMap);
					return respMessage;
				}
				
				//��ͷΪ���룬����з���
				else if(Content.startsWith("����")){
					if (Content.startsWith("����")) {
						String keyWord = Content.replaceAll("^����", "").trim();
						if ("".equals(keyWord)) {
							textMessage.setContent(getTranslateUsage());
						} 
						else {
							textMessage.setContent(TranslateService.translate(keyWord));
						}	
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
					return respMessage;

				}
				else
				{
					respContent="�Բ���û�ܸ���������һ���Ҫ����Ŭ��Ŷ����";
				}
	            
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}catch(Exception e){
				e.printStackTrace();
			}
			
				
				return respMessage;
		}
		
		/**
		 * �����㲥ʹ��ָ��
		 * 
		 * @return
		 */
		public static String getUsage() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("�����㲥����ָ��").append("\n\n");
			buffer.append("�ظ�������+����").append("\n");
			buffer.append("���磺��������").append("\n");
			buffer.append("���ߣ���������@����").append("\n\n");
			return buffer.toString();
		}
		/**
		 * ��������ָ��
		 */
		public static String getAddruse(){
			StringBuffer buffer = new StringBuffer();
			buffer.append("������������ʹ��ָ��").append("\n\n");
			buffer.append("������ڵײ���\"+\"��ť").append("\n");
			buffer.append("ѡ��\"λ��\"����\"����\"").append("\n\n");
			return buffer.toString();
		}
		/**
		 * Q��ͨʹ��ָ��
		 * 
		 * @return
		 */
		public static String getTranslateUsage() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\ue148").append("����ʹ��ָ��").append("\n\n");
			buffer.append("���빦��Ϊ�û��ṩרҵ�Ķ����Է������Ŀǰ֧�����·��뷽��").append("\n");
			buffer.append("    �� -> Ӣ").append("\n");
			buffer.append("    Ӣ -> ��").append("\n");
			buffer.append("    �� -> ��").append("\n\n");
			buffer.append("ʹ��ʾ����").append("\n");
			buffer.append("    ���������й���").append("\n");
			buffer.append("    ����dream").append("\n");
			buffer.append("    ���뤵�褦�ʤ�").append("\n\n");
		//	buffer.append("�ظ���?����ʾ���˵�");
			return buffer.toString();
		}
		
}
