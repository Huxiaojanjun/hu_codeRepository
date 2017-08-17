package clb_data_service;

import java.sql.SQLException;

import java.util.List;

import clb_data_bean.Employee;
import clb_data_dao.EmpDao;

public class EmpService {

	EmpDao ed = new EmpDao();
	
	public List<Employee> getEmps() throws SQLException{
		return ed.getEmps();
	}
	
	
	
}
