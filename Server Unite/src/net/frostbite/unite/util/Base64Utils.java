package net.frostbite.unite.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

	public static String encode(String value) throws Exception {
		   byte[] message = value.getBytes(StandardCharsets.UTF_8);
		   return Base64.getEncoder().encodeToString(message);
	   }

	   public static String decode(String value) throws Exception {
		   byte[] decoded = Base64.getDecoder().decode(value);
		   return (new String(decoded, StandardCharsets.UTF_8));
	   }
	   
	
}
