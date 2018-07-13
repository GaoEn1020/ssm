package com.htht.util;

import java.io.IOException;
import java.util.Map;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class RestfulUtils {
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(RestfulUtils.class);
	private static final MediaType Content_Type = MediaType
			.parse("application/json;charset=utf-8");
	
	public static Log createDI(String type, String name, String message, Map<String, Object> tags, Map<String, Object> fields){
		Log log = new Log();
		log.setName(name);
		log.setMessage(message);
		log.setType(type);
		log.setTags(tags);
		log.setFields(fields);
		return log;
	}

	public static Log createEI(String type, String name, String message, Map<String, Object> fields){
		Log log = new Log();
		log.setName(name);
		log.setMessage(message);
		log.setType(type);
		log.setFields(fields);
		return log;
	}
	public static String Post(String url, String content) {
		Response response = null;
		try {
			AppLogService.info(" post url {"+url+"} and param {"+content+"}");
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(Content_Type, content);
			Request request = new Request.Builder().post(body).url(url)
					.addHeader("content-type", "application/json").build();
			response = client.newCall(request).execute();
			AppLogService.info(" response code {"+response.code()+"} ");
			if (!response.isSuccessful()){
				throw new IOException("Unexpected code  {} " + response);
			}
			return response.body().string();
		} catch (Exception e) {
			AppLogService.error("error {"+ e.getMessage()+"} ");
		} finally {
			if (response != null) {
				try {
					response.body().close();
				} catch (IOException e) {
					AppLogService.error(" IOException" + e.getMessage());
				}
			}
		}
		return null;
	}
}
