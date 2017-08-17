package clb_data_bean;

import java.util.Date;

public class Tom {

	private String patientID;
	private String patientName;
	private String patientSex;
	private String age;
	private Date scheduledDateID;
	private String AnesthesiaCode;
	
	public String getPatientID() {
		return patientID;
	}
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientSex() {
		return patientSex;
	}
	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getScheduledDateID() {
		return scheduledDateID;
	}
	public void setScheduledDateID(Date scheduledDateID) {
		this.scheduledDateID = scheduledDateID;
	}
	public String getAnesthesiaCode() {
		return AnesthesiaCode;
	}
	public void setAnesthesiaCode(String anesthesiaCode) {
		AnesthesiaCode = anesthesiaCode;
	}
	@Override
	public String toString() {
		return "Tom [patientID=" + patientID + ", patientName=" + patientName + ", patientSex=" + patientSex + ", age="
				+ age + ", scheduledDateID=" + scheduledDateID + ", AnesthesiaCode=" + AnesthesiaCode + "]";
	}
	

	
	
}
