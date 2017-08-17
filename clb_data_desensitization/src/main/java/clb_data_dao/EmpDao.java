package clb_data_dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clb_data_bean.Employee;
import clb_sqlserver_jdbc.BaseDao;

public class EmpDao extends BaseDao{
	
	public static List<Employee> getEmps() throws SQLException {
	    BaseDao bd = new BaseDao();
	    List<Employee> list = new ArrayList<Employee>();
	    String sql = "SELECT * FROM DIM_Employee";
	    bd.getConnection();
	    ResultSet rs = bd.select(sql);
	    while (rs.next()) {
	      Employee emp = new Employee();
	            emp.setEmployeeID(rs.getInt("EmployeeID"));
	            emp.setEmployeeCode(rs.getString("EmployeeCode"));
	            emp.setEmployeeName(rs.getString("EmployeeName"));
	            emp.setDeptID(rs.getInt("DeptID"));
	            emp.setDeptCode(rs.getString("DeptCode"));
	            emp.setPosition(rs.getString("Position"));
	            emp.setJobTitile(rs.getString("JobTitile"));
	            emp.setStatus(rs.getInt("Status"));
	            emp.setForbidden(rs.getInt("Forbidden"));
	            list.add(emp);
	    }
	    return list;
	  }
}