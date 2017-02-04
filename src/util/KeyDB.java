package src.util;

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

public class KeyDB {

    // private static String framework = "embedded";
    private static String protocol = "jdbc:derby:";
    private static String dbName = "derbydb";
    //private static String dbPath = "C:\\Users\\lester.john\\workspaces\\PixlTone\\HomeServer\\";
    private static String dbPath = System.getProperty("user.home") + "/workspaces/PixlTone/HomeServer/";
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
    	// props.put("user", userName);
    	// props.put("password", passWord);
    	// props.put("dbName", dbPath+dbName);
    }

    public static void connectDB() throws Exception {
    	attachDB((new File(dbPath + dbName)).exists() ? "false" : "true");
    }
    
    public static void attachDB(String cDB) throws SQLException {
    	conn = DriverManager.getConnection(protocol + dbPath + dbName + ";create=" + cDB, props);
    	conn.setAutoCommit(true);
    	sqlConn = conn.createStatement();
    	statements.add(sqlConn);
    }
    
    public String createKeyTable(String tableName) throws SQLException {
    	String $ = null;
    	try {
			connectDB();
			$ = "ok";
		} catch (Exception e) {
			$ = "fail";
			e.printStackTrace();
		}
    	
    	if ($ == "ok")
			try {
				sqlConn.execute("create table " + tableName
						+ " (priKey varchar(256), pubKey varchar(256), deviceID varchar(256)), uName varchar(60), dName varchar(60), passPhrase varchar(120)");
				$ = "ok";
				dbCommit();
			} catch (Exception e) {
				if (e.getMessage().contains(tableName.toUpperCase()))
					$ = "fail";
			}
		return $;
    }
    
    public void createKeyTableEntry(SecretKey priKey, SecretKey pubKey, String deviceID) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("insert into "+ tableName +" values (?, ?, ?)");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.setString(3, deviceID);
    	sqlCommand.execute();
    	dbCommit();
    }

    public String readSKeyTableEntry(SecretKey priKey, SecretKey pubKey) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("select * from " + tableName + (priKey != null ? "where priKey=?" : "where pubKey=?"));
		statements.add(sqlCommand);
    	if(priKey == null) sqlCommand.setString(1, pubKey.toString());
    	if(pubKey == null) sqlCommand.setString(1, priKey.toString());
		ResultSet valSelect = sqlCommand.executeQuery();
		
    	while(valSelect.next()) {
    		// System.out.println(valSelect.getString("priKey"));
    		// System.out.println(valSelect.getString("pubKey"));
    		// System.out.println(valSelect.getString("deviceID"));
    	}
	    return valSelect.getString("priKey") + " : " + valSelect.getString("pubKey") + " : "
				+ valSelect.getString("deviceID") + " : " + valSelect.getString("uName") + " : "
				+ valSelect.getString("dName") + " : " + valSelect.getString("passPhrase");
    }
    	
    public String readDIDTableEntry(String deviceID) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("select * from " + tableName + "where deviceID=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, deviceID);
    	ResultSet valSelect = sqlCommand.executeQuery();
		
    	while(valSelect.next()) {
    		// System.out.println(valSelect.getString("priKey"));
    		// System.out.println(valSelect.getString("pubKey"));
    		// System.out.println(valSelect.getString("deviceID"));
    	}
	    return valSelect.getString("priKey") + " : " + valSelect.getString("pubKey") + " : "
				+ valSelect.getString("deviceID") + " : " + valSelect.getString("uName") + " : "
				+ valSelect.getString("dName") + " : " + valSelect.getString("passPhrase");
    }
    
    public String readNameTableEntry(String uName, String dName) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("select * from " + tableName + (uName != null ? "where uName=?" : "where dName=?"));
    	statements.add(sqlCommand);
    	if(dName == null) sqlCommand.setString(1, uName);
    	if(uName == null) sqlCommand.setString(1, dName);
    	ResultSet valSelect = sqlCommand.executeQuery();
		
    	while(valSelect.next()) {
    		// System.out.println(valSelect.getString("priKey"));
    		// System.out.println(valSelect.getString("pubKey"));
    		// System.out.println(valSelect.getString("deviceID"));
    	}
	    return valSelect.getString("priKey") + " : " + valSelect.getString("pubKey") + " : "
				+ valSelect.getString("deviceID") + " : " + valSelect.getString("uName") + " : "
				+ valSelect.getString("dName") + " : " + valSelect.getString("passPhrase");
    }
    
    public void updateKeyTableEntry(SecretKey priKey, SecretKey pubKey, String DeviceID) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("update "+ tableName +" set priKey=?, pubKey=? where priKey=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.setString(3, priKey.toString());
    	sqlCommand.execute();
    	dbCommit();
    }
    
    public void deleteKeyTableEntry(SecretKey priKey, SecretKey pubKey, String DeviceID) throws SQLException {
    	String $ = null;
    	try {
			attachDB("false");
			$ = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			$ = "fail";
		}
    	sqlCommand = conn.prepareStatement("delete from "+ tableName +" set priKey=?, pubKey=? where priKey=?");
    	statements.add(sqlCommand);
    	sqlCommand.setString(1, priKey.toString());
    	sqlCommand.setString(2, pubKey.toString());
    	sqlCommand.setString(3, priKey.toString());
    	sqlCommand.execute();
    	dbCommit();
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

    	if (!conn.isClosed()) {
			conn.commit();
			conn.close();
		}
		DriverManager.getConnection(protocol + dbPath + dbName + ";shutdown=true");
    }
}
