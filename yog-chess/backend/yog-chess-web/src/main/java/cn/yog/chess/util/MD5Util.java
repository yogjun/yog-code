package cn.yog.chess.util;

import cn.yog.core.util.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private final static Logger logger = LoggerFactory.getLogger(MD5Util.class.getName());

	/**
	 * 通过MD5加密算法返回加密后的字符串
	 *
	 * @param text
	 *            明文（要加密的字符串）
	 * @return
	 */
	public static String createMD5(String text) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {// 理论上不会有这个异常
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		try {
			md.update(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LogUtil.info(logger, "Md5：{0}", e.getMessage());
		}
		byte[] b = md.digest();
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			int i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");// 不足两位，补0
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * 获取4位随机数字
	 *
	 * @return
	 */
	public static String getTokenRandom() {
		int token = 1000 + (int) (Math.random() * 9000);
		return String.valueOf(token);
	}

	/**
	 * 反转字符串
	 *
	 * @param str
	 * @return
	 */
	public static String reserveString(String str) {
		StringBuffer a = new StringBuffer(str);
		return a.reverse().toString();
	}

	public static void main(String[] args) {
		String test = "123456";
		LogUtil.info(logger, "Md5加密前:{0}", test);
		String md5 = MD5Util.createMD5(test);
		LogUtil.info(logger, "Md5加密后:{0}", md5);
	}

}
