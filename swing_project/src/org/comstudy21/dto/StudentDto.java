package org.comstudy21.dto;


public class StudentDto {

	private String id;
	private String name;
	private String email;
	private String phone;
	private String bday;
	private String gender;
	private String resume;
	private String salary;
	private String area;
	private String type;
	private String memo;
	
	public StudentDto() {
		
	}
	
	public StudentDto(String id, String name, String email, String phone, String bday, String gender, String resume,
			String salary, String area, String type, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.bday = bday;
		this.gender = gender;
		this.resume = resume;
		this.salary = salary;
		this.area = area;
		this.type = type;
		this.memo = memo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + name + ", " + email + ", " + phone + ", " + bday
				+ ", " + gender + ", " + resume + ", " + salary + ", " + area + ", "
				+ type + ", " + memo + "]";
	}
	
}

