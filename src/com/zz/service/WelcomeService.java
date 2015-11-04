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
 * ���Ļ�ӭ�����ͼ����Ϣ����
 */
public class WelcomeService {
	public static String processRequest(Map<String, String> requestMap) {
		String respMessage = null;
		try {


			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
		
			//����ͼ����Ϣ
            NewsMessage newsMessage = new NewsMessage();  
            newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
            newsMessage.setFuncFlag(0);
            
            List<Article> articlelist=new ArrayList<Article>();
            	Article article1 = new Article();  
                article1.setTitle("��ӭ�����Ĺ�С��");  
                article1.setDescription("�ҽ�ȫ��ȫ��Ϊ�����񣡣�");  
                article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/kUU3UjX0ncDv2hgAxe36upExG82xpqUHt9fcQy4G7GWeABpXAo5hDIGjuS77HpLFluTTmNVlDMS2iawNPiaQ059g/0");  
                article1.setUrl("http://mp.weixin.qq.com/mp/appmsg/show?__biz=MzA3NjIxODIxNw==&appmsgid=10000006&itemidx=1&sign=90a2fb4d178a312a8c6d7a4a4ec570b9&uin=MTcwMDE1MTA4MA%3D%3D&key=234b3ec6051a4a5400e875116c97e12ec2c291f54da644813b0949aa8f1657bfd96ed848c83a872c20a1f5586f62b1f1&devicetype=android-16&version=25000338&lang=zh_CN");

                
                articlelist.add(article1);

                // ����ͼ����Ϣ����  
                newsMessage.setArticleCount(articlelist.size());  
                // ����ͼ����Ϣ������ͼ�ļ���  
                newsMessage.setArticles(articlelist);  
                // ��ͼ����Ϣ����ת����xml�ַ���  
                respMessage = MessageUtil.newsMessageToXml(newsMessage);             
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
			return respMessage;
	}
}
