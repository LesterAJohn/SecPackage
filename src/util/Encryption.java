package util;


import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lester.john
 */
public class Encryption {
public static PublicKey pubKey;
public static PrivateKey priKey;
public static SecretKey aesKey;

        private static void ASEKeyGen() throws NoSuchAlgorithmException {
    
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256,rand);
        
        aesKey = generator.generateKey();
        }

        private static void RSAKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        pubKey = keyGen.genKeyPair().getPublic();
        priKey = keyGen.genKeyPair().getPrivate();
        }
        
        public static PublicKey pubKey() throws GeneralSecurityException, NoSuchProviderException {
        	if(pubKey == null)
				RSAKeys();
            return pubKey;
        }
        
        public static PrivateKey priKey() throws GeneralSecurityException, NoSuchProviderException {
        	if(priKey == null)
				RSAKeys();
            return priKey;
        }
        
        public static SecretKey aesKey() throws GeneralSecurityException, NoSuchProviderException {
        	if(aesKey == null)
				ASEKeyGen();
            return aesKey;
        }
        
        public static String RSAencrypt(String Data) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE,pubKey);
        
        return Base64.encode(c.doFinal(Data.getBytes()));
        }
        
        public static String RSAdecrypt(String encryptedData) throws Exception {
        byte[] Buffer = new byte[encryptedData.length()];
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE,priKey);
        
        return new String(c.doFinal(Base64.decode(encryptedData)));       
        }
}