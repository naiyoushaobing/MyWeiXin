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
	 * 用户文本信息回复
	 */
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
				
				//对用户的文本消息进行匹配
				
				
				//返回音乐搜索导航
				if(Content.equals("1")){
					respContent=getUsage();
				}
				//返回美食搜索导航
				else if(Content.equals("2")){
					respContent=getAddruse();
				}
				//返回历史上的今天
				else if(Content.equals("3")){
					respContent=TodayInHistoryService.getTodayInHistoryInfo();
				}
				//返回翻译指南
				else if(Content.equals("4")){
					respContent=getTranslateUsage();
				}
				
				//开头为歌曲,则进行歌曲搜索
				else if(Content.startsWith("歌曲"))
				{	
					respMessage=MusicService.processRequest(requestMap);
					return respMessage;
				}
				
				//开头为翻译，则进行翻译
				else if(Content.startsWith("翻译")){
					if (Content.startsWith("翻译")) {
						String keyWord = Content.replaceAll("^翻译", "").trim();
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
					respContent="对不起没能给你帮助，我还需要继续努力哦！！";
				}
	            
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}catch(Exception e){
				e.printStackTrace();
			}
			
				
				return respMessage;
		}
		
		/**
		 * 歌曲点播使用指南
		 * 
		 * @return
		 */
		public static String getUsage() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("歌曲点播操作指南").append("\n\n");
			buffer.append("回复：歌曲+歌名").append("\n");
			buffer.append("例如：歌曲存在").append("\n");
			buffer.append("或者：歌曲存在@汪峰").append("\n\n");
			return buffer.toString();
		}
		/**
		 * 附近搜索指南
		 */
		public static String getAddruse(){
			StringBuffer buffer = new StringBuffer();
			buffer.append("附近教育机构使用指南").append("\n\n");
			buffer.append("点击窗口底部的\"+\"按钮").append("\n");
			buffer.append("选择\"位置\"，点\"发送\"").append("\n\n");
			return buffer.toString();
		}
		/**
		 * Q译通使用指南
		 * 
		 * @return
		 */
		public static String getTranslateUsage() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\ue148").append("翻译使用指南").append("\n\n");
			buffer.append("翻译功能为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
			buffer.append("    中 -> 英").append("\n");
			buffer.append("    英 -> 中").append("\n");
			buffer.append("    日 -> 中").append("\n\n");
			buffer.append("使用示例：").append("\n");
			buffer.append("    翻译我是中国人").append("\n");
			buffer.append("    翻译dream").append("\n");
			buffer.append("    翻译さようなら").append("\n\n");
		//	buffer.append("回复“?”显示主菜单");
			return buffer.toString();
		}
		
}
