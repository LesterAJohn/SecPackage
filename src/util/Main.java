package util;


import javax.crypto.SecretKey;


/*
Test Code for Library Functions
 */

/**
 *
 * @author lester.john
 */

public class Main {
    public static void main(String[] args) throws Exception {
        Encryption.ASEKeyGen();
        Encryption.RSAKeys();
        String encValue = Encryption.RSAencrypt("mypassword");
        String outPut = WebConnect.sendGet("http://www.oracle.com");

        /* String decValue = Encryption.RSAdecrypt(encValue); */
        System.out.println("PublicKey :" + Encryption.pubKey());
        System.out.println("PrivateKey :" + Encryption.priKey());  
        System.out.println("AESKey :" + Encryption.aesKey());

        /* System.out.println("Decrypted Value :" + decValue); */
        /* System.out.println("Webconnect :" + outPut); */        
        }
    }