package com.shequtong.yishequ.volley;

import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * TODO Json工具类
 * @author  Wanglei 
 * @data:  2015年6月2日 上午11:36:38 
 * @version:  V1.0
 */
public class GsonUtil {
	static private Gson mGson = null;
	
	/**
	 * 序列化类型为Date
	 * 实现一个类型适配器(TypeAdapter) 
	 * @author Administrator
	 *
	 */
	static public class UtilDateSerialization implements
			JsonSerializer<java.util.Date> {
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {

			return new JsonPrimitive(src.getTime());
		}

	}
	
	/**
	 * 反序列化类型为Date
	 * 实现一个类型适配器(TypeAdapter) 
	 * @author Administrator
	 *
	 */
	static public class UtilDateDeserialization implements
			JsonDeserializer<java.util.Date> {

		public Date deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return new java.util.Date(json.getAsLong());
		}

	}
	
	/**
	 * 实现一个自定义的实行适配器
	 * @return Gson
	 */
	static public Gson createGson() {
		if (mGson == null) {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapter(
					java.util.Date.class, new UtilDateSerialization())
					.registerTypeAdapter(java.util.Date.class,
							new UtilDateDeserialization());
	
			mGson = builder.create();
		}
		return mGson;
	}

}
