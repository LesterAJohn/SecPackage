/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author lester.john
 */
public class fileMgt {
    public void createByte(byte[] data, String path, String fileName) throws FileNotFoundException, IOException {
        try (FileOutputStream out = new FileOutputStream(path+fileName)) {
            out.write(data);
        }
    }
    
    public void createTxt(String data, String path, String fileName) {
        
    }
    
    public void deleteFile () {
        
    }

}
