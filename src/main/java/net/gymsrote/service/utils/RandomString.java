package net.gymsrote.service.utils;

import java.util.Random;

public final class RandomString {
	private static final String SALT_DIGITS = "1234567890";
	private static final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final Random rand = new Random();
	
	public static String get(int length) {
        StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {
            int index = (int) (rand.nextFloat() * SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        return salt.toString();
    }
	
	public static String generateUsername(String username) {
		int length = rand.nextInt(3, 6);
        StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {
            int index = (int) (rand.nextFloat() * SALT_DIGITS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        
        return username.concat("_".concat(salt.toString()));
    }
	
	public static String generateOtp(int length) {
        StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {
            int index = (int) (rand.nextFloat() * SALT_DIGITS.length());
            salt.append(SALT_DIGITS.charAt(index));
        }
        return salt.toString();
    }
	
	private RandomString() {}
}
