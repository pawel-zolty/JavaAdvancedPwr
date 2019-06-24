package main.POJO;

public class ClientPOJO {
	private int Id;
	private String Name;
	private String Phone;

	public ClientPOJO(int id, String name, String phone) {
		Id = id;
		Name = name;
		Phone = phone;
	}

	public ClientPOJO(String name, String phone) {
		Id = -1;
		Name = name;
		Phone = phone;
	}

	public int getId() {
		return Id;
	}

	public String getName() {
		return Name;
	}

	public String getPhone() {
		return Phone;
	}
}
