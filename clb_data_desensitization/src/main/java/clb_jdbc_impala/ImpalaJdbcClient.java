package clb_jdbc_impala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
/**
 * 使用java连接impala
 * 使用impala连接hive，并对hive表进行操作查询
 * 连接的是192.168.0.112服务器
 *
 */
public class ImpalaJdbcClient {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive2://192.168.0.112:21050/;auth=noSasl";
	private static String user = "root";
	private static String password = "root";
	private static final Logger log = Logger.getLogger(ImpalaJdbcClient.class);
	
	/**
	 * impala连接hive驱动
	 */
	public Statement getConnectImpala() throws ClassNotFoundException,SQLException{
		Class.forName(driverName);
	    Connection conn = DriverManager.getConnection(url,user,password);
		return conn.createStatement();
	}
	
	/**
	 * show hive databases
	 */
	public void showHiveDatabase() throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		String sql = "show databases";
		ResultSet executeQuery = stmt.executeQuery(sql);
		System.out.println("查询到hive数据库名称如下：");
		while(executeQuery.next()){
			System.out.println(executeQuery.getString(1));
		}
	}
	
	
	/**
	 * use hive databases
	 */
	public String useHiveDatabase(String database) throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		String sql = "use "+database;
		boolean execute = stmt.execute(sql);
		if(execute!=true){
			System.out.println("使用数据库 "+database+"成功！");
		}else{
			System.out.println("没有该数据库！");
		}
		return database;
	}
	
	
	/**
	 * show hive tables
	 */
	public void showHiveTables(String databaseName) throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		String database = useHiveDatabase(databaseName);
		String sql = "show tables";
		ResultSet executeQuery = stmt.executeQuery(sql);
		System.out.println("查询到hive中数据库"+database+"里表名称如下：");
		while(executeQuery.next()){
			System.out.println(executeQuery.getString(1));
		}
	}
	
	
	
	/**
	 * create hive tables
	 * load data
	 */
	public void createHiveTable() throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		String tableName = "hu_table";
		stmt.execute("drop table if exists " + tableName);
		stmt.execute("create table "+tableName+
				"(key int,value string) row format delimited fields terminated by ','");
		//load data into table
		//NOTE:filepath has to be local to the server
		//NOTE:/tmp/a.txt is a ctrl-A separated file with two fields per line
		String filepath = "/test/hh.txt";
		String sql = "load data inpath '"+filepath +"' into table "+tableName;
		System.out.println("Running: "+sql);
		stmt.execute(sql);
	}
	
	/**
	 * describe hive tables
	 */
	public void describeHiveTable(String tableName) throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		String sql = "describe "+tableName;
		System.out.println("Running: "+sql);
		ResultSet res = stmt.executeQuery(sql);
		while(res.next()){
			System.out.println(res.getString(1)+"\t"+res.getString(2));
		}
	}
	
	
	/**
	 * select hive tables
	 */
	public void selectHiveTable(String tableName) throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		//select * query
		String sql = "select * from "+tableName;
		System.out.println("Running: "+sql);
		ResultSet res = stmt.executeQuery(sql);
		while(res.next()){
			System.out.println(res.getString(1)+"\t"+res.getString(2));
		}
	}
	
	/**
	 * regular hive tables
	 */
	public void regularHiveTable(String tableName) throws ClassNotFoundException, SQLException{
		Statement stmt = getConnectImpala();
		//regular hive query
		String sql = "select count(1) from "+ tableName;
		System.out.println("Running: "+sql);
		ResultSet res = stmt.executeQuery(sql);
		while(res.next()){
			System.out.println(res.getString(1));
		}
	}
	
	
	
	
	public static void main(String[] args) {
		try {
			
			ImpalaJdbcClient imc = new ImpalaJdbcClient();
			System.out.println("======================打印数据库======================");
			imc.showHiveDatabase();
			System.out.println("======================打印表======================");
			imc.showHiveTables("default");
			System.out.println("======================描述表======================");
			imc.describeHiveTable("stu");
			System.out.println("======================查询表======================");
			imc.selectHiveTable("stu");
			System.out.println("======================统计表行数======================");
			imc.regularHiveTable("stu");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(driverName+"not found!",e);
			System.exit(1);
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("Connection error!",e);
			System.exit(1);
		}
	}
	
}
