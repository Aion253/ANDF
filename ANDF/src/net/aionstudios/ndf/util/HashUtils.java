package net.aionstudios.ndf.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Winter Roberts
 *
 */
public class HashUtils {

	private static MessageDigest md;
	
	/**
	 * Hashes a string given a hashing algorithm from HashAlgorithms.
	 * 
	 * @param hashingAlgorithm The hashing algorithm to be used. Available from the HashAlgorithms class.
	 * @param str The string to be hashed.
	 * @return The hashed String in UTF-8 format.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public final static String hash(String hashingAlgorithm, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		md = MessageDigest.getInstance(hashingAlgorithm);
		return bytesHex(md.digest(str.getBytes("UTF-8")));
	}
	
	/**
	 * Hashes a string given a hashing algorithm from HashAlgorithms. The salt is added and makes common hash words which may have rainbow tables unrecognizable. Even if someone knew the salt the values would be different and more time would have to be spent to decode the hashed value.
	 * 
	 * @param hashingAlgorithm The hashing algorithm to be used. Available from the HashAlgorithms class.
	 * @param str The string to be hashed.
	 * @param salt The string to added before hashing.
	 * @return The hashed and salted String in UTF-8 format.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public final static String hash(String hashingAlgorithm, String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		md = MessageDigest.getInstance(hashingAlgorithm);
		str = salt+str;
		return bytesHex(md.digest(str.getBytes("UTF-8")));
	}
	
	/**
	 * Converts a byte array to a hexidecimal store-able value.
	 * 
	 * @param bt An array of bytes to be converted to hex.
	 * @return A hexidecimal alpha-numeric un-spaced representation of the provided bytes.
	 */
	private final static String bytesHex(byte[] bt){
		StringBuffer result = new StringBuffer();
		for (byte b : bt) {
		    result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}
