package net.gymsrote.service.thirdparty.payment;

import javax.servlet.http.HttpServletRequest;

public final class Utils {
	
	private Utils() {}
	
	public static String buildUrl(String path, HttpServletRequest req) {
		return String.format("%s://%s%s", req.getScheme(), req.getServerName(), path);
	}
}
