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
	 * 附近地点搜索
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
				//获取用户的地址坐标
				String location_X=requestMap.get("Location_X");
				String location_Y=requestMap.get("Location_Y");
				//获取用户的位置信息
				String label=requestMap.get("Label");
				//获取地图的缩放大小
				String scale=requestMap.get("Scale");
				
				//百度POI（地点）搜索
				//百度查询链接
			
				String url="http://api.map.baidu.com/place/search?query="+
				URLEncoder.encode("美食","UTF-8")+"&location="+location_X+","+location_Y+
				"&radius=1000&output=html&src=yourCompanyName|yourAppName";

				
				//创建图文消息
	            NewsMessage newsMessage = new NewsMessage();  
	            newsMessage.setToUserName(fromUserName);  
	            newsMessage.setFromUserName(toUserName);  
	            newsMessage.setCreateTime(new Date().getTime());  
	            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
	            newsMessage.setFuncFlag(0);
	            
	            List<Article> articlelist=new ArrayList<Article>();
	            
	            Article article1 = new Article();
                article1.setTitle("附近美食搜索");  
                article1.setDescription("点击为您转到附近美食搜索");  
                article1.setPicUrl("");  
                article1.setUrl(url);
                
                
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
