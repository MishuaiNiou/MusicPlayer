package com.example.utils;

public class TimeFormatUtils {

	public static String getTimeString(long ts){
		int time = (int) ts/1000;
		int ms = time % 60;   //¼¸Ãë
		int ss = time / 60;	  //¼¸·Ö
		ss = ss>99?99:ss;
		StringBuffer str = new StringBuffer();
		str.append( ss<10 ? "0"+ss+":" : ss+":");
		str.append( ms<10 ? "0"+ms : ms+":");
		return str.toString();
	}

}
