package main.POJO;

import java.util.Date;

public class ReservationPOJO {
	private int Id;
	private int ClientId;
	private Date Date;

	public ReservationPOJO(int id, int clientId, java.util.Date date) {
		Id = id;
		ClientId = clientId;
		Date = date;
	}

	public ReservationPOJO(int clientId, java.util.Date date) {
		Id = -1;
		ClientId = clientId;
		Date = date;
	}

	public int getId() {
		return Id;
	}

	public int getClientId() {
		return ClientId;
	}

	public Date getDate() {
		return Date;
	}

}
