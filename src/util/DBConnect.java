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

    private static String framework = "embedded";
    private static String protocol = "jdbc:derby:";
    private static String dbName = "derbydb";
    private static String dbPath = "C:\\Users\\lester.john\\workspaces\\PixlTone\\SecPackage\\WorkFiles\\";
    static ArrayList<Statement> statements = new ArrayList<Statement>();
    static PreparedStatement sqlCommand;
    static Properties props = new Properties();
    static Connection conn = null;
    static Statement sqlConn = null;

    public void setProperties(String newProtocol, String newDBPath, String newDBName, String userName, String passWord) {
    	protocol = newProtocol;
    	dbName = newDBName;
    	dbPath = newDBPath;
    	props.put("user", userName);
    	props.put("password", passWord);
    	props.put("dbName", dbPath+dbName);
    }

    public static void createDB() throws Exception {
    	File f = new File(dbPath+dbName);
    	if(f.exists() == true) {
    		connectDB("false");
    	} 
    	else {
    		connectDB("true");
    	}
    }
    
    public static void connectDB(String cDB) throws SQLException {
    	conn = DriverManager.getConnection(protocol + dbPath + dbName + ";create=" + cDB, props);
    	conn.setAutoCommit(true);
    	sqlConn = conn.createStatement();
    	statements.add(sqlConn);
    	
    }
    
    public void createKeyTable(String tableName) throws SQLException {
    	sqlConn.execute("create table "+tableName+"(priKey varchar(256) , pubKey varchar(256))");
    }
    
    public void createKeyTableEntry() throws SQLException {
    	sqlCommand = conn.prepareStatement("");
    	
    	sqlCommand.execute();
    }
    
    public void updateKeyTableEntry() throws SQLException {
    	sqlCommand = conn.prepareStatement("");
    	
    	sqlCommand.execute();
    }
    
    public ResultSet readKeyTableEntry() throws SQLException {
    	sqlCommand = conn.prepareStatement("");
    	
    	ResultSet outPut = sqlCommand.executeQuery();
    	return outPut;
    }
    
    public void deleteKeyTableEntry() throws SQLException {
    	sqlCommand = conn.prepareStatement("");
    	
    	sqlCommand.execute();    	
    }
    
    public void dbCommit() throws SQLException {
    	conn.commit();
    }
    
    public void  shutdownDB() throws SQLException {
    	
    	// The shutdown=true attribute shuts down Derby
    	// to shut down a specific database only, but keep the
        // engine running (for example for connecting to other
        // databases), specify a database in the connection URL:
        //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");

    	DriverManager.getConnection(protocol + dbPath + dbName + ";shutdown=true");
    }
}