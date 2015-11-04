package com.zz.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz.message.resp.Article;
import com.zz.message.resp.NewsMessage;
import com.zz.message.resp.TextMessage;
import com.zz.util.MessageUtil;
/**
 * 
 * @author zz
 * 订阅欢迎界面的图文信息返回
 */
public class WelcomeService {
	public static String processRequest(Map<String, String> requestMap) {
		String respMessage = null;
		try {


			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
		
			//创建图文消息
            NewsMessage newsMessage = new NewsMessage();  
            newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
            newsMessage.setFuncFlag(0);
            
            List<Article> articlelist=new ArrayList<Article>();
            	Article article1 = new Article();  
                article1.setTitle("欢迎您订阅瓜小瓜");  
                article1.setDescription("我将全心全意为您服务！！");  
                article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/kUU3UjX0ncDv2hgAxe36upExG82xpqUHt9fcQy4G7GWeABpXAo5hDIGjuS77HpLFluTTmNVlDMS2iawNPiaQ059g/0");  
                article1.setUrl("http://mp.weixin.qq.com/mp/appmsg/show?__biz=MzA3NjIxODIxNw==&appmsgid=10000006&itemidx=1&sign=90a2fb4d178a312a8c6d7a4a4ec570b9&uin=MTcwMDE1MTA4MA%3D%3D&key=234b3ec6051a4a5400e875116c97e12ec2c291f54da644813b0949aa8f1657bfd96ed848c83a872c20a1f5586f62b1f1&devicetype=android-16&version=25000338&lang=zh_CN");

                
                articlelist.add(article1);

                // 设置图文消息个数  
                newsMessage.setArticleCount(articlelist.size());  
                // 设置图文消息包含的图文集合  
                newsMessage.setArticles(articlelist);  
                // 将图文消息对象转换成xml字符串  
                respMessage = MessageUtil.newsMessageToXml(newsMessage);             
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
			return respMessage;
	}
}
