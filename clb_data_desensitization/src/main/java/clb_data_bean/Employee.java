package clb_data_bean;

public class Employee {
	private int EmployeeID;
	private String EmployeeCode;
	private String EmployeeName;
	private int DeptID;
	private String DeptCode;
	private String Position;
	private String JobTitile;
	private int Status;
	private int Forbidden;

	public int getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}

	public String getEmployeeCode() {
		return EmployeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		EmployeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public int getDeptID() {
		return DeptID;
	}

	public void setDeptID(int deptID) {
		DeptID = deptID;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getJobTitile() {
		return JobTitile;
	}

	public void setJobTitile(String jobTitile) {
		JobTitile = jobTitile;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getForbidden() {
		return Forbidden;
	}

	public void setForbidden(int forbidden) {
		Forbidden = forbidden;
	}

	public Employee(int employeeID, String employeeCode, String employeeName,
			int deptID, String deptCode, String position, String jobTitile,
			int status, int forbidden) {
		super();
		EmployeeID = employeeID;
		EmployeeCode = employeeCode;
		EmployeeName = employeeName;
		DeptID = deptID;
		DeptCode = deptCode;
		Position = position;
		JobTitile = jobTitile;
		Status = status;
		Forbidden = forbidden;
	}

	public Employee() {
		super();
	}

}
