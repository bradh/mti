package mti.commons.encryption;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/*
 * Utility class that can be used to create a string encryptor/decryptor.
 * 
 * Includes a main method that can be run with arguments to encrypt a string
 * 
 * Note: Default configuration requires the JCE unlimited strength extension be installed.
 */
public class EncryptionHelper {

	public static final String DEFAULT_KEY = "1q2w3e4r";
	public static final String DEFAULT_ALG = "PBEWithMD5AndTripleDES";
	
	public static StringEncryptor createEncryptor(String key, String algorithm) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key != null ? key : DEFAULT_KEY);
		encryptor.setAlgorithm(algorithm != null ? algorithm : DEFAULT_ALG);
		
		return encryptor;
	}
	
	private static void help() {
		System.out.println("Arguments are [-d] <value> [<key> [<algorithm>]]");
	}
	
	public static void main(String[] args) {
		String key = null;
		String alg = null;
		String value = null;

		if (args.length < 1) {
			help();
			return;
		}
		
		int idx = 0;
		boolean decrypt = false;
		
		value = args[idx++];
		
		if ("-d".equals(value)) {
			if (idx >= args.length) {
				help();
				return;
			}
			
			decrypt = true;
			value = args[idx++];
		}
		
		if (idx < args.length)
			key = args[idx++];
		if (idx < args.length)
			alg = args[idx++];
		
		StringEncryptor encryptor = createEncryptor(key, alg);
		String result;
		
		if (decrypt) {
			result = encryptor.decrypt(value);
		} else {
			result = encryptor.encrypt(value);
		}
		
		System.out.println(result);
	}
}
