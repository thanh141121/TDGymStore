package net.gymsrote.service.thirdparty.payment.momo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DataSigning {
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
	
	public static String sign(String key, String data) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);

		return bytesToHex(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
	}
	
	private static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
