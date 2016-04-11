/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author lester.john
 */

public class DBConnect {

	/* the default framework is embedded */
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    private String dbName = "derbydb";
    private String dbPath = "C:\\Users\\lester.john\\workspaces\\PixlTone\\SecPackage\\WorkFiles\\";
    Properties props = new Properties();

    public void setProperties(String newProtocol, String newDBPath, String newDBName, String userName, String passWord) {
    	protocol = newProtocol;
    	dbName = newDBName;
    	dbPath = newDBPath;
    	props.put("user", userName);
    	props.put("password", passWord);
    	props.put("dbName", dbPath+dbName);
    }

    public void createDB() throws Exception {
    	File f = new File(dbPath+dbName);
    	if(f.exists() == false) {
    		connectDB("false");
    	} 
    	else {
    		connectDB("true");
    	}
    }
    
    public void connectDB(String cDB) throws Exception {
    	DriverManager.getConnection(protocol + dbPath + dbName + ";create=" + cDB, props);
    }
    
    public void  shutdownDB() throws Exception {
    	
    	// The shutdown=true attribute shuts down Derby
    	// to shut down a specific database only, but keep the
        // engine running (for example for connecting to other
        // databases), specify a database in the connection URL:
        //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");

    	DriverManager.getConnection(protocol + dbPath + dbName + ";shutdown=true");
    }
}
