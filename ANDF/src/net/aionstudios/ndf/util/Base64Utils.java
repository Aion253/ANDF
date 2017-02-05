package net.aionstudios.ndf.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 
 * @author Winter Roberts
 *
 */
public class Base64Utils {

	/**
	 * Encodes a string using the Base64 encoder
	 * 
	 * @param value	The string to be encoded
	 * @return A Base64 string
	 * @throws Exception
	 */
	public final static String encode(String value) throws Exception {
		byte[] message = value.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(message);
	}

	/**
	 * Decodes a string using Base64 decoder
	 * 
	 * @param value The string to be decoded
	 * @return A String
	 * @throws Exception
	 */
	public final static String decode(String value) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(value);
		return (new String(decoded, StandardCharsets.UTF_8));
	}
	   
	
}
