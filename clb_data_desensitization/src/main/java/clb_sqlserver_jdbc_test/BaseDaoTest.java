package clb_sqlserver_jdbc_test;

import clb_data_udf.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import clb_data_bean.Employee;
import clb_data_bean.Tom;
import clb_data_desensitization.DataMaskUtils;
import clb_data_service.EmpService;
import clb_data_service.TomService;

public class BaseDaoTest {

	public static void main(String[] args) throws SQLException {

		DataMaskUtils du = new DataMaskUtils();
		/**
		 * 
		 
		EmpService es = new EmpService();
		List<Employee> emps = es.getEmps();
		for (Employee employee : emps) {
			System.out.println(du.chineseName(employee.getEmployeeName(), 1, 2));
			System.out.println(du.fixedPhone(employee.getEmployeeCode(),
					employee.getEmployeeCode().length() - 2, employee.getEmployeeCode().length()));
		}
       */
		
		
		System.out.println("================================================================");
		
		//测试tom_test表的数据脱敏
		TomService ts = new TomService();
		List<Tom> toms = ts.getToms();

		for (Tom tom : toms) {
			//将表中patientSex字段值为‘女’全部替换为‘F’
			if(tom.getPatientSex().replaceAll(" ", "").equals("女")){
				System.out.println(du.chineseName(tom.getPatientSex(), 0, 1));
			}else{
				System.out.println(tom.getPatientSex());
		     }
	    }
		/**
		 * 可以指定需要脱敏的字段，并可以 查询出所有的数据
		 */
		for (Tom tom : toms) {
			if(!maskColumn("patientName").isEmpty()){
				tom.setPatientName(du.chineseName(tom.getPatientName(), 1, 2));
			}
			if(!maskColumn("patientID").isEmpty()){
				tom.setPatientID(du.chineseName(tom.getPatientID(), 1, 5));
			}
			System.out.println(tom);
			
		}
		
	}
	
	public static String maskColumn(String str){
		return str;
	}
}
