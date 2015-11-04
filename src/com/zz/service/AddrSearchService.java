package com.zz.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.zz.message.resp.Article;
import com.zz.message.resp.NewsMessage;
import com.zz.util.MessageUtil;

public class AddrSearchService {
	/**
	 * 
	 * @author zz
	 * �����ص�����
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
				//��ȡ�û��ĵ�ַ����
				String location_X=requestMap.get("Location_X");
				String location_Y=requestMap.get("Location_Y");
				//��ȡ�û���λ����Ϣ
				String label=requestMap.get("Label");
				//��ȡ��ͼ�����Ŵ�С
				String scale=requestMap.get("Scale");
				
				//�ٶ�POI���ص㣩����
				//�ٶȲ�ѯ����
			
				String url="http://api.map.baidu.com/place/search?query="+
				URLEncoder.encode("��ʳ","UTF-8")+"&location="+location_X+","+location_Y+
				"&radius=1000&output=html&src=yourCompanyName|yourAppName";

				
				//����ͼ����Ϣ
	            NewsMessage newsMessage = new NewsMessage();  
	            newsMessage.setToUserName(fromUserName);  
	            newsMessage.setFromUserName(toUserName);  
	            newsMessage.setCreateTime(new Date().getTime());  
	            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
	            newsMessage.setFuncFlag(0);
	            
	            List<Article> articlelist=new ArrayList<Article>();
	            
	            Article article1 = new Article();
                article1.setTitle("������ʳ����");  
                article1.setDescription("���Ϊ��ת��������ʳ����");  
                article1.setPicUrl("");  
                article1.setUrl(url);
                
                
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
