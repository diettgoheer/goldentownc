package com.ss.goldentown.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

public class NameService {

	private static Random random = null;
	 
	private static Random getRandomInstance() {
	if (random == null) {
	random = new Random(new Date().getTime());
	}
	return random;
	}
	 
	public static String getChinese() {
	String str = null;
	int highPos, lowPos;
	Random random = getRandomInstance();
	highPos = (176 + Math.abs(random.nextInt(39)));
	lowPos = 161 + Math.abs(random.nextInt(93));
	byte[] b = new byte[2];
	b[0] = (new Integer(highPos)).byteValue();
	b[1] = (new Integer(lowPos)).byteValue();
	try {
	str = new String(b, "GB2312");
	} catch (UnsupportedEncodingException e) {
	e.printStackTrace();
	}
	return str;
	}
	
	public static String getSurname(String surname){
		double n = 0.02;
		String str;
		double p = Math.random();
		if(p>n)
			str = surname;
		else if(p<n/10)
			str = NameService.getFixedLengthChinese("", 2);
		else
			str = surname + NameService.getFixedLengthChinese("", 2);
		return str;
	} 
	
	public static String getFixedLengthChinese(String surname,int length) {
	String str = surname;
	for (int i = length-1; i > 0; i--) {
	str = str + NameService.getChinese();
	}
	return str;
	}
	 
	public static String getRandomLengthChiness(int start, int end) {
	String str = "";
	int length = new Random().nextInt(end + 1);
	if (length < start) {
	str = getRandomLengthChiness(start, end);
	} else {
	for (int i = 0; i < length; i++) {
	str = str + getChinese();
	}
	}
	return str;
	}
	 

	
}
