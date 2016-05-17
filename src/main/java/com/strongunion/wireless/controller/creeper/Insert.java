package com.strongunion.wireless.controller.creeper;

import java.sql.*;


public class Insert {
	 private  static final String driver = "com.mysql.jdbc.Driver";

     // URL指向要访问的数据库名clawer
	 private  static final String DBurl = "jdbc:mysql://127.0.0.1:3306/boldwei";

     // MySQL配置时的用户名
	 private static final String user = "root";

     // MySQL配置时的密码
	 private  static final String password = "mysql";

	 private PreparedStatement pstmt = null;
	 private Connection spiderconn = null;
public  void insertFilePath(String fileName,String filepath,String url ){
     try {
		Class.forName(driver);
		spiderconn = DriverManager.getConnection(DBurl, user, password);
		String sql = "insert into FilePath (filename,filepath,url) values (?,?,?)";
		pstmt = spiderconn.prepareStatement(sql);
		pstmt.setString(1, fileName);
		pstmt.setString(2, filepath);
	    pstmt.setString(3, url);
	    pstmt.executeUpdate();

	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch(SQLException e) {
       e.printStackTrace();
   }finally{
		    	try {
					pstmt.close();
	  				spiderconn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
}
}
