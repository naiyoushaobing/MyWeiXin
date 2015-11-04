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
			// 默认返回的文本消息内容
			String respContent =null;
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			//获取用户发出的文本信息
			String Content=requestMap.get("Content");
			
			//创建文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			
			// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
			String keyWord = Content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
			// 如果歌曲名称为空
			if ("".equals(keyWord)) {
				respContent = TextService.getUsage();
			} else {
				String[] kwArr = keyWord.split("@");
				// 歌曲名称
				String musicTitle = kwArr[0];
				// 演唱者默认为空
				String musicAuthor = "";
				if (2 == kwArr.length)
					musicAuthor = kwArr[1];

				// 搜索音乐
				Music music = MusicSearch.searchMusic(musicTitle, musicAuthor);
				// 未搜索到音乐
				if (null == music) {
					respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
				} else {
					// 音乐消息
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
			//封装
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return respMessage;
	}
}
