package com.zz.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.*;
//import com.google.gson.Gson;

/**
 * 
 * 
 * @date 2013-10-21
 */
public class TranslateService {
	/**
	 * ����http�����ȡ���ؽ��
	 * 
	 * @param requestUrl �����ַ
	 * @return
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// �����ص�������ת�����ַ���
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// �ͷ���Դ
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
		}
		return buffer.toString();
	}

	/**
	 * utf����
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ���루��->Ӣ Ӣ->�� ��->�� ��
	 * 
	 * @param source
	 * @return
	 */
	public static String translate(String source) {
		String dst = null;

		// ��װ��ѯ��ַ
		String requestUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=ARvGfweQNSvqXBaufrFSyE7z&q={keyWord}&from=auto&to=auto";
		// �Բ���q��ֵ����urlEncode utf-8����
		requestUrl = requestUrl.replace("{keyWord}", urlEncodeUTF8(source));

		// ��ѯ���������
		try {
			// ��ѯ����ȡ���ؽ��
			String json = httpRequest(requestUrl);
					
			//��json��ȡ��������
			JSONObject jsonObject=JSONObject.fromObject(json);
			dst=jsonObject.getString("trans_result");
			dst=dst.replace("{", "").replace("[", "").replace("src", "").replace(".", "").replace(",", "").replace("}", "");
			dst=dst.replace("\"", "").replace(":", "").replace("dst", "").replace(source, "").replace("]", "").trim();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == dst)
			dst = "����ϵͳ�쳣�����Ժ��ԣ�";
		return dst;
	}

	public static void main(String[] args) {
		// ��������The network really powerful
		System.out.println(translate("hello"));
	}
}