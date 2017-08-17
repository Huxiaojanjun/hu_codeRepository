package clb_data_dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clb_data_bean.Employee;
import clb_data_bean.Tom;
import clb_sqlserver_jdbc.BaseDao;

public class TomDao extends BaseDao{
	
	public static List<Tom> getToms() throws SQLException {
	    BaseDao bd = new BaseDao();
	    List<Tom> list = new ArrayList<Tom>();
	    String sql = "SELECT patientID,patientName,patientSex,age,scheduledDateID,AnesthesiaCode FROM tom_test";
	    bd.getConnection();
	    ResultSet rs = bd.select(sql);
	    while (rs.next()) {
	      Tom tom = new Tom();
	      tom.setPatientID(rs.getString("patientID"));
	      tom.setPatientName(rs.getString("patientName"));
	      tom.setPatientSex(rs.getString("patientSex"));
	      tom.setAge(rs.getString("age"));
	      tom.setScheduledDateID(rs.getDate("scheduledDateID"));
	      tom.setAnesthesiaCode(rs.getString("AnesthesiaCode"));

	      list.add(tom);
	    }
	    return list;
	  }
}