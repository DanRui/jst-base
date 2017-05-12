package com.jst.common.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class ConnectionPool {

	final static int initPoolSize = 1;// 初始化连接
	final static int maxPoolSize = 20; // 最大连接数
	final static int maxidlPoolSize = 3; // 最大空闲连接数
	static Vector<Connection> idlePool = new Vector<Connection>();	
	static Vector<Connection> activePool = new Vector<Connection>();
	static String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	static String USERNAME = "";
	static String PASSWORD = "";
	static String URL = "";
	
	private static Logger log = Logger.getLogger(ConnectionPool.class);
	
	static {
		
		
		try {
			InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("jdbc.properties");
			
			Properties conf = new Properties();
			
			
			conf.load(inputStream);
			String TEMP_DRIVER_CLASS = conf.getProperty("driverclass");
			if (TEMP_DRIVER_CLASS != null && !TEMP_DRIVER_CLASS.equals("")) {
				DRIVER_CLASS = TEMP_DRIVER_CLASS;
			}
			String temp_username = conf.getProperty("username");
			if (temp_username != null && !temp_username.equals("")) {
				USERNAME = temp_username;
			}
			String temp_password = conf.getProperty("password");
			if (temp_password != null && !temp_password.equals("")) {
				PASSWORD = temp_password;
			}
			String tempUrl = conf.getProperty("url");
			if (tempUrl != null && !tempUrl.equals("")) {
				URL = tempUrl;
			}
			
			Class.forName(DRIVER_CLASS);

		} catch (Exception e) {
			log.error("get jdbc info error" + ":", e);
		}
		initConnect(); // 初始化连接池
		refreshPool();// 刷新连接池
	}

	public static void refreshPool() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < idlePool.size(); i++) {
					Connection tempcon = idlePool.get(i);
					refreshCon(tempcon);
				}
				for (int i = 0; i < activePool.size(); i++) {
					Connection tempcon = activePool.get(i);
					refreshCon(tempcon);
				}

			}
		}, 30 * 1000, 100 * 1000);
	}

	public static void refreshCon(Connection con) {
		try {
			java.sql.Statement stmt = con.createStatement();
			stmt.execute("select 1 from dual ");
			stmt.close();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void initConnect() {
		try {
			for (int i = 0; i < initPoolSize; i++) {
				Connection conn = getOneConn();
				if (conn != null) {
					idlePool.add(conn);
				}
			}
		} catch (Exception e) {
			log.error(new Date() + ":", e);

		}
	}

	
	private static Connection getOneConn() throws Exception {
		Connection conn = null;
		try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			log.error(new Date() + ":", e);
		}
		if(conn==null){
			throw new Exception ("无法获得数据库连接");
		}
		return conn;

	}
	
	
	public static synchronized Connection getConnction() throws Exception {
		Connection conn = null;
		if (idlePool.size() > 0) {
			conn = idlePool.remove(0);
			try {
			if (conn.isClosed()) { 
					conn = getOneConn(); 
				}	
			} catch (SQLException e) {
				log.error(new Date() + ":", e);
			}
			activePool.add(conn);
		} else if (maxPoolSize > activePool.size()+idlePool.size()) {
			Connection tempCon = getOneConn();
		   conn = tempCon;
		}else{
			throw new Exception ("连接池已满");
		}
		return conn;
	}
	
	

	public static synchronized void releaseConnection(Connection con) {

		if (con != null && activePool.contains(con)) {
			activePool.remove(con);
			if (idlePool.size() < maxidlPoolSize) {		
				idlePool.add(con);
			} else {
				try{
					con.close(); // 超过最大空闲数，则销毁
				}catch(Exception e){
					e.printStackTrace();
				}
				con = null; 
				System.out.println();
			}
		}

	}

	public static void main(String[] args) throws Exception {

		System.out.println(idlePool.size());
		System.out.println(activePool.size());
		Connection con = getConnction();
		System.out.println(idlePool.size());
		System.out.println(activePool.size());
		releaseConnection(con);
		System.out.println(idlePool.size());
		System.out.println(activePool.size());

	}

}
