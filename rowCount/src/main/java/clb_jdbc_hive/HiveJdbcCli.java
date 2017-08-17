package clb_jdbc_hive;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Hive的JavaApi 
 * 启动hive的远程服务接口命令行执行：hive –service hiveserver >/dev/null 2>/dev/null & 
 * 通过调用HiveJdbcCli可以对hive中的表进行删除、创建、查询表的描述信息、查询表的数据功能，
 * 此外，如果想对表中的数据进行修改，这里客可以通过修改数据文件进行更新，然后动用次api即可达到修改效果
 */
public class HiveJdbcCli  {

	public static String driverName = "org.apache.hive.jdbc.HiveDriver";  
	public static String url = "jdbc:hive2://192.168.0.111:10000/clb_hospital_dw";  
	public static String user = "root";  
	public static String password = "";  
	public static String sql = "";  
	public static ResultSet res;  
	public static final Logger log = Logger.getLogger(HiveJdbcCli.class);
	
	public static void main(String[] args) throws Exception {
		Connection conn = null;  
	    Statement stmt = null;  
	    try {  
	        conn = getConn();  
	        stmt = conn.createStatement();  
	        //显示数据库
	        showDatabases(stmt);
	        
	        // 第一步:存在就先删除  
//	        String tableName = dropTable(stmt);  

	        // 第二步:不存在就创建  
//	        createTable(stmt,tableName);  
	        // 第三步:查看创建的表  
//	        showTables(stmt, tableName);  

	        // 执行describe table操作  
//	        describeTables(stmt, tableName);  
	        // 执行load data into table操作  
//	        loadData(stmt, tableName);  
	        // 执行 select * query 操作  
	        selectData(stmt, "dim_employee");  

	        // 执行 regular hive query 统计操作  
//	        countData(stmt, tableName);  

	    } catch (ClassNotFoundException e) {  
	        e.printStackTrace();  
	        log.error(driverName + " not found!", e);  
	        System.exit(1);  
	    } catch (SQLException e) { 
	        e.printStackTrace();  
	        log.error("Connection error!", e);  
	        System.exit(1);  
	    } finally {  
	        try {  
	            if (conn != null) {  
	                conn.close();  
	                conn = null;  
	            }  
//	            if (stmt != null) {  
//	                stmt.close();  
//	                stmt = null;  
//	            }  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  
	
	/**
	 * 显示hive中的数据库
	 */
	public static void showDatabases(Statement stmt) throws SQLException {
		sql = "show databases";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("hive中的数据库如下：");
		while(rs.next()){
			System.out.println(rs.getString(1));//hive的索引从1开始
		}
	}

	/**
	 * 统计hive表中的条目数
	 */
	public static void countData(Statement stmt, String tableName)  
	        throws SQLException {  
	    sql = "select count(1) from " + tableName;  
	    System.out.println("Running:" + sql);  
	    res = stmt.executeQuery(sql);  
	    System.out.println("执行“regular hive query”运行结果:");  
	    while (res.next()) {  
	        System.out.println("count ------>" + res.getString(1));  
	    }  
	}  

	/**
	 * 查询hive表中的数据
	 */
	public static void selectData(Statement stmt, String tableName) throws SQLException {  
	    sql = "select * from clb_hospital_dw." + tableName+" limit 10";  
	    System.out.println("Running:" + sql);  
	    res = stmt.executeQuery(sql);
	    System.out.println("执行 select * query 运行结果:");  
	    while (res.next()) {  
	        System.out.println(res.getInt(1) + "\t" + res.getString(2)+ "\t" + res.getString(3));  
	    }  
	}  
	
	/**
	 * 加载数据文件到hive表中
	 */
	public static void loadData(Statement stmt, String tableName)  
	        throws SQLException {  
	    String filepath = "/data/file";  
	    sql = "load data local inpath '" + filepath + "' into table "  + tableName;  
	    System.out.println("Running:" + sql);  
	     stmt.execute(sql);  
	}  

	/**
	 * 描述hive表的信息
	 */
	public static void describeTables(Statement stmt, String tableName)  
	        throws SQLException {  
	    sql = "describe " + tableName;  
	    System.out.println("Running:" + sql);  
	    res = stmt.executeQuery(sql);  
	    System.out.println("执行 describe table 运行结果:");  
	    while (res.next()) {  
	        System.out.println(res.getString(1) + "\t" + res.getString(2));  
	    }  
	}  

	/**
	 * 显示表名
	 */
	public static void showTables(Statement stmt, String tableName)  
	        throws SQLException {  
	    sql = "show tables '" + tableName + "'";  
	    System.out.println("Running:" + sql);  
	    res = stmt.executeQuery(sql);  
	    System.out.println("执行 show tables 运行结果:");  
	    if (res.next()) {  
	        System.out.println(res.getString(1));  
	    }  
	}  

	/**
	 * 在hive中创建表名为tableName的表
	 */
	public static void createTable(Statement stmt, String tableName) throws SQLException {  
	    sql = "create table "  
	            + tableName  
	            + " (key int, value string)  row format delimited fields terminated by '\t'";  
	    stmt.execute(sql);  
	}  

	/**
	 * 删除表名为tableName的表
	 */
	public static String dropTable(Statement stmt) throws SQLException {  
	    // 创建的表名  
	    String tableName = "stu";  
	    sql = "drop table " + tableName;  
	    stmt.execute(sql);  
	    return tableName;  
	}  

	/**
	 * 连接jdbc
	 */
	public static Connection getConn() throws ClassNotFoundException, SQLException {  
	    Class.forName(driverName);  
	    Connection conn = DriverManager.getConnection(url, user, password);  
	    return conn;  
	}  
	

	public static void updateData(Statement stmt, String tableName) {
		
	}
	

}
