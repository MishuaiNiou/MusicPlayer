package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataUtils {

	private static List<Map<String,String>> list = new ArrayList<Map<String,String>>();

	public static List<Map<String, String>> getList() {
		return list;
	}
	
	public static Map<String,String> getMusicMap(int index){
		return list.get(index);
	}
}
