package clb_sqlserver_jdbc;

import java.sql.Connection;
import org.apache.log4j.Logger; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BaseDao {
  
  // 使用log4j记录日志  
  private static Logger logger = Logger.getLogger(BaseDao.class);  
  // 连接驱动  
  private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
  // 连接路径  
  private static final String URL = "jdbc:sqlserver://192.168.0.168\\mssqlserver_zl;DatabaseName=CLB_Hospital_DW";  
  // 用户名  
  private static final String USERNAME = "sa";  
  // 密码  
  private static final String PASSWORD = "cdr123!@#";  
  
  public static Connection conn=null;
  public static PreparedStatement pst=null;
    
  //静态代码块  
  static {  
      try {  
          // 加载驱动  
          Class.forName(DRIVER); 
      } catch (ClassNotFoundException e) {  
          e.printStackTrace();  
      }  
  }  

  /* 
   * 获取数据库连接 
   */  
  public static Connection getConnection() {  
      logger.debug("开始连接数据库");  
      try{  
          conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);  
      }catch(SQLException e){  
          e.printStackTrace();  
          logger.error("数据库连接失败！");  
      }  
      logger.debug("数据库连接成功");  
      return conn;  
  }  

  /* 
   * 关闭数据库连接，注意关闭的顺序 
   */  
  public void close(ResultSet rs, PreparedStatement ps, Connection conn) {  
      if(rs!=null){  
          try{  
              rs.close();  
              rs=null;  
          }catch(SQLException e){  
              e.printStackTrace();  
              logger.error("关闭ResultSet失败");  
          }  
      }  
      if(ps!=null){  
          try{  
              ps.close();  
              ps=null;  
          }catch(SQLException e){  
              e.printStackTrace();  
              logger.error("关闭PreparedStatement失败");  
          }  
      }  
      if(conn!=null){  
          try{  
              conn.close();  
              conn=null;  
          }catch(SQLException e){  
              e.printStackTrace();  
              logger.error("关闭Connection失败");  
          }  
      }  
  }
  
  public boolean zsg(String sql,Object...obj){
	  try {
		pst=conn.prepareStatement(sql);
		for (int i = 0; i < obj.length; i++) {
			pst.setObject(i+1, obj[i]);
		}
		return pst.executeUpdate()>0;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}			  
  }
  
  public ResultSet select(String sql,Object...obj){
	  try {
		pst=conn.prepareStatement(sql);
		for (int i = 0; i < obj.length; i++) {
			pst.setObject(i+1, obj[i]);
		}
		return pst.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}			  
  }
  public static void main(String[] args) {
	
  }
}
