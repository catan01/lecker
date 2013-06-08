package lecker.presenter;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Crypt {
	public static String encrypt(String value) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(value.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (Exception nsae) {
			nsae.printStackTrace();
		}
		return hashword;
	}
}

