package com.card.utils;

import java.util.Random;

/**
 * 各种id生成策略
 */
public class IDUtils {

	/**
	 * 图片名生成
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	/**
	 * 商品id生成
	 */
	public static long genItemId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}
	
	public static void main(String[] args) {
		for(int i=0;i< 100;i++)
		System.out.println(genItemId());
	}
	
	/**
	 * 随机生成i位数密码，含英文字母
	 * 
	 */

	public static String getPassword(int i) {
		Random rd = new Random(); // 创建随机对象
		String n = ""; // 保存随机数
		int rdGet; // 取得随机数
		do {
			if (rd.nextInt() % 2 == 1) {
				rdGet = Math.abs(rd.nextInt()) % 10 + 48; // 产生48到57的随机数(0-9的键位值)
			} else {
				rdGet = Math.abs(rd.nextInt()) % 26 + 97; // 产生97到122的随机数(a-z的键位值)
			}
			char num1 = (char) rdGet; // int转换char
			String dd = Character.toString(num1);
			n += dd;
		} while (n.length() < i);// 设定长度，此处假定长度小于i
		return n;

	}
}
