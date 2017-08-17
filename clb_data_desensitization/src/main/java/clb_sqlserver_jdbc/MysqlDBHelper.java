package clb_sqlserver_jdbc;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class MysqlDBHelper {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://master:3306/clb_hadoop_metadata";
	private String user = "root";
	private String password = "root";
	private Connection conn;
	private Statement stmt;
	
	public MysqlDBHelper() {
		init();
	}
	
	public void init()  {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void execute(String sql) throws SQLException {
		stmt.execute(sql);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	public List<Map<String, Object>> executeQuery2(String sql) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while(rs.next()) {
			Map<String, Object> rowData = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnLabel(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		if(rs != null) {
			rs.close();
		}
		return list;
	}
	
	public Map<String, Object> queryWithObject(String sql) throws SQLException {
		Map<String, Object> obj = new HashMap<String, Object>();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		if(rs.next()) {
			for(int i = 1; i <= columnCount; i++) {
				obj.put(md.getColumnLabel(i), rs.getObject(i));
			}
		}
		if(rs != null) {
			rs.close();
		}
		return obj;
	}
	
	public void close(Connection conn, Statement stmt) throws SQLException {
		if(conn != null) {
			conn.close();
		}
		if(stmt != null) {
			stmt.close();
		}
	}
}
