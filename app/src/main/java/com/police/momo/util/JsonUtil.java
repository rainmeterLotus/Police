package com.police.momo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonUtil
{
	private static Gson	gson	= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	public static String toJson(Object src)
	{
		return gson.toJson(src);
	}

	public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException
	{
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException
	{
		return gson.fromJson(json, typeOfT);
	}
}
