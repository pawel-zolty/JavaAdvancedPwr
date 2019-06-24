package main.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.POJO.ClientPOJO;

public class ClientDAL extends BaseDAL {

	public ClientDAL() {
		super();
	}

	public ClientPOJO getClientById(int id) throws Exception {
		try {
			String sql = String.format("SELECT Id, Name, Phone FROM Clients Where Id = %s;", id);
			ResultSet result = this.executeStatement(sql);

			if (result.next()) {
				String name = result.getString("Name");
				String phone = result.getString("Phone");
				int clientId = result.getInt("Id");
				return new ClientPOJO(clientId, name, phone);
			} else {
				throw new Exception("Nie ma takiego klienta");
			}

		} finally {
			this.closeStatement();
			this.closeConnection();
		}
	}

	public ArrayList<ClientPOJO> getAllClients() throws SQLException {
		try {
			String sql = "SELECT Id, Name, Phone FROM Clients";
			ResultSet result = this.executeStatement(sql);
			ArrayList<ClientPOJO> clients = new ArrayList<>();

			while (result.next()) {
				String name = result.getString("Name");
				String phone = result.getString("Phone");
				int clientId = result.getInt("Id");
				clients.add(new ClientPOJO(clientId, name, phone));
			}

			return clients;
		} finally {
			this.closeStatement();
			this.closeConnection();
		}
	}

	public ClientPOJO createClient(ClientPOJO clientData) throws Exception {
		try {
			String name = clientData.getName(), phone = clientData.getPhone();
			String sql = String.format("INSERT INTO Clients (Name, Phone) VALUES ('%s', '%s');", name, phone);

			int id = this.executePreparedStatement(sql);

			if (id != -1)
				return new ClientPOJO(id, name, phone);
			return clientData;

		} finally {
			this.closePreparedStatement();
			this.closeConnection();
		}
	}

}
