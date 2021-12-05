package model.db;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PBEStringEncryptor {
	private static String jaspytEncryptionKey = "KVJK_Key";
	private static String jaspytAlgorithm = "PBEWithMD5AndDES";	
	
	//public PBEStringEncryptor() {	}	

	public static String getEncryptedString(String passwordToEncrypt) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(jaspytEncryptionKey);
		encryptor.setAlgorithm(jaspytAlgorithm);
		String encryptText = encryptor.encrypt(passwordToEncrypt);
		
		/*
		while(encryptText.length() >= 60){
			encryptText = encryptor.encrypt(passwordToEncrypt);
		}
		*/
		return encryptText;
	}

	public static String getDecryptedString(String passwordToDecrypt) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(jaspytEncryptionKey);
		encryptor.setAlgorithm(jaspytAlgorithm);
		String decryptText = encryptor.decrypt(passwordToDecrypt);
		return decryptText;
	}
}
