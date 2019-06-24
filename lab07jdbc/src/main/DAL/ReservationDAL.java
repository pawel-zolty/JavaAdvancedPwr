package main.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import main.POJO.ReservationPOJO;
import main.java.DateTimestampConverter;

public class ReservationDAL extends BaseDAL {

	public ReservationDAL() {
		super();
	}

	public ReservationPOJO getReservationById(int id) throws SQLException, Exception {
		try {
			String sql = String.format("SELECT Id, Date, ClientId FROM Reservations Where Id = %s;", id);
			ResultSet result = this.executeStatement(sql);

			if (result.next()) {
				Date date = result.getDate("Date");
				int clientId = result.getInt("ClientId");
				int reservationId = result.getInt("Id");				
				return new ReservationPOJO(reservationId, clientId, date);
			} else {
				throw new Exception("Nie ma takiego zamówienia");
			}

		} finally {
			this.closeStatement();
			this.closeConnection();
		}
	}

	public ArrayList<ReservationPOJO> getAllReservations() throws SQLException {
		try {
			String sql = "SELECT Id, Date, ClientId FROM Reservations;";
			ResultSet result = this.executeStatement(sql);
			ArrayList<ReservationPOJO> reservations = new ArrayList<>();

			while (result.next()) {
				Date date = result.getDate("Date");
				int clientId = result.getInt("ClientId");
				int reservationId = result.getInt("Id");				
				reservations.add(new ReservationPOJO(reservationId, clientId, date));
			}

			return reservations;
		} finally {
			this.closeStatement();
			this.closeConnection();
		}
	}

	public ReservationPOJO createReservation(ReservationPOJO reservationData) throws Exception {
		try {
			int clientId = reservationData.getClientId();
			Date date = reservationData.getDate();			
			java.sql.Date dateSql = DateTimestampConverter.convert(date);						
			String sql = String.format("INSERT INTO Reservations (ClientId, Date) VALUES ('%s', ?);", clientId);
			
			int id = this.executePreparedStatement(sql, 1, dateSql);

			if (id != -1)
				return new ReservationPOJO(id, clientId, date);
			return reservationData;

		} finally {
			this.closePreparedStatement();
			this.closeConnection();
		}
	}
}