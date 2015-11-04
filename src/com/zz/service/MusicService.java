package com.zz.service;

import java.util.Date;
import java.util.Map;

import com.zz.message.resp.Music;
import com.zz.message.resp.MusicMessage;
import com.zz.message.resp.TextMessage;
import com.zz.util.MessageUtil;

public class MusicService {
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
			
			
			// ������2���ּ����������+���ո�-���������ȥ��  
			String keyWord = Content.replaceAll("^����[\\+ ~!@#%^-_=]?", "");
			// �����������Ϊ��
			if ("".equals(keyWord)) {
				respContent = TextService.getUsage();
			} else {
				String[] kwArr = keyWord.split("@");
				// ��������
				String musicTitle = kwArr[0];
				// �ݳ���Ĭ��Ϊ��
				String musicAuthor = "";
				if (2 == kwArr.length)
					musicAuthor = kwArr[1];

				// ��������
				Music music = MusicSearch.searchMusic(musicTitle, musicAuthor);
				// δ����������
				if (null == music) {
					respContent = "�Բ���û���ҵ��������ĸ���<" + musicTitle + ">��";
				} else {
					// ������Ϣ
					MusicMessage musicMessage = new MusicMessage();
					musicMessage.setToUserName(fromUserName);
					musicMessage.setFromUserName(toUserName);
					musicMessage.setCreateTime(new Date().getTime());
					musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
					musicMessage.setMusic(music);
					respMessage = MessageUtil.musicMessageToXml(musicMessage);
					return respMessage;
				}
			}
			//��װ
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return respMessage;
	}
}
