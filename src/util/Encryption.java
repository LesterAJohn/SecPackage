package util;


import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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

        public static void ASEKeyGen() throws NoSuchAlgorithmException {
    
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256,rand);
        
        aesKey = generator.generateKey();
        }

        public static void RSAKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        pubKey = keyGen.genKeyPair().getPublic();
        priKey = keyGen.genKeyPair().getPrivate();
        }
        
        public static PublicKey pubKey() {
            return pubKey;
        }
        
        public static PrivateKey priKey() {
            return priKey;
        }
        
        public static SecretKey aesKey() {
            return aesKey;
        }
        
        public static String RSAencrypt(String Data) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE,pubKey);
        
        byte[] encrypted = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encode(encrypted);
                
        return encryptedValue;
        }
        
        public static String RSAdecrypt(String encryptedData) throws Exception {
        byte[] Buffer = new byte[128];
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE,priKey);
        
        byte[] decryptedData = Base64.decode(encryptedData);
        byte[] decodeValue = c.doFinal(decryptedData);
        String decryptedValue = new String(decodeValue);
            
        return decryptedValue;       
        }
}