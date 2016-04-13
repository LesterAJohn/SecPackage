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
import javax.crypto.SecretKey;

/**
 *
 * @author lester.john
 */

public class DBConnect {

    // private static String framework = "embedded";
    private static String protocol = "jdbc:derby:";
    private static String dbName = "derbydb";
    private static String dbPath = "C:\\Users\\lester.john\\workspaces\\PixlTone\\SecPackage\\WorkFiles\\";
    static ArrayList<Statement> statements = new ArrayList<Statement>();
    static PreparedStatement sqlCommand;
    static Properties props = new Properties();
    static Connection conn = null;
    static Statement sqlConn = null;
    static String tableName = "keys";

    public void setProperties(String newProtocol, String newDBPath, String newDBName, String userName, String passWord) {
    	protocol = newProtocol;
    	dbName = newDBName;
    	dbPath = newDBPath;
    	props.put("user", userName);
    	props.put("password", passWord);
    	props.put("dbName", dbPath+dbName);
    }

    public static void createDB() throws Exception {
    	connectDB((new File(dbPath + dbName)).exists() ? "false" : "true");
    }
    
    public static void connectDB(String cDB) throws SQLException {
    	conn = DriverManager.getConnection(protocol + dbPath + dbName + ";create=" + cDB, props);
    	conn.setAutoCommit(true);
    	sqlConn = conn.createStatement();
    	statements.add(sqlConn);
    	
    }
    
    public void createKeyTable(String tableName) throws SQLException {
    	sqlConn.execute("create table "+ tableName +" (priKey varchar(256) , pubKey varchar(256))");
    }
    
    public void createKeyTableEntry(SecretKey priKey, SecretKey pubKey) throws SQLException {
    	sqlCommand = conn.prepareStatement("insert into "+ tableName +" values (?, ?)");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.execute();
    }

    public ResultSet readKeyTableEntry(SecretKey pubKey) throws SQLException {
    	sqlCommand = conn.prepareStatement("select * from " + tableName + "where pubKey=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, pubKey.toString());
    	return sqlCommand.executeQuery();
    }
    
    public void updateKeyTableEntry(SecretKey priKey, SecretKey pubKey) throws SQLException {
    	sqlCommand = conn.prepareStatement("update "+ tableName +" set priKey=?, pubKey=? where priKey=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.setString(3, priKey.toString());
    	sqlCommand.execute();
    }
    
    public void deleteKeyTableEntry(SecretKey priKey, SecretKey pubKey) throws SQLException {
    	sqlCommand = conn.prepareStatement("delete from "+ tableName +" set priKey=?, pubKey=? where priKey=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.setString(3, priKey.toString());
    	sqlCommand.execute();    	
    }
    
    public void dropKeyDB(String tableName) throws Exception {
    	sqlCommand.execute("drop table "+ tableName);
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