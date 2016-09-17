// encryption class from careymd solution to Assignment 4

package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	private static MessageDigest md;
	
	public static String MD5(String str){
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = str.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<digested.length;i++){
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("MD5 unavailable");
		}
		return null;
	}
}
