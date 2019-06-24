package main.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public abstract class BaseDAL {
	private Connection connection = null;
	private Statement statement = null;
	protected PreparedStatement preparedStatement = null;
	private MsSqlDbConnectionProvider connectionProvider;
	private String generatedColumns[] = { "ID" };

	public BaseDAL() {
		connectionProvider = new MsSqlDbConnectionProvider();
	}

	protected void closeConnection() {
		try {
			if (connection != null) {
				// System.err.println("Close Connection");
				connection.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	protected void closeStatement() {
		try {
			if (statement != null) {
				// System.err.println("Close Statement");
				statement.close();
			}
		} catch (SQLException se2) {
		}
	}

	protected void closePreparedStatement() {
		try {
			if (preparedStatement != null) {
				// System.err.println("Close Prepared Statement");
				preparedStatement.close();
			}
		} catch (SQLException se2) {
		}
	}

	protected ResultSet executeStatement(String sql) {
		this.setConnection();
		ResultSet rs = null;
		try {
			this.statement = this.connection.createStatement();
			rs = this.statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// https://stackoverflow.com/questions/1915166/how-to-get-the-insert-id-in-jdbc
	protected int executePreparedStatement(String sql) throws Exception {
		this.setConnection();
		ResultSet rs = null;
		try {
			this.preparedStatement = this.connection.prepareStatement(sql, this.generatedColumns);
			this.preparedStatement.execute();
			rs = this.preparedStatement.getGeneratedKeys();
			return this.getInsertedItemId(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Blad");
		}
		
	}

	protected int executePreparedStatement(String sql, int dateIndex, java.sql.Date date) throws Exception {
		this.setConnection();
		ResultSet rs = null;
		try {
			this.preparedStatement = this.connection.prepareStatement(sql, this.generatedColumns);
			preparedStatement.setDate(dateIndex, date);
	
			this.preparedStatement.execute();
	
			rs = this.preparedStatement.getGeneratedKeys();
			return this.getInsertedItemId(rs);
		}  catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Blad");
		}		
	}

	private void setConnection() {
		connection = connectionProvider.getConnection();
	}

	private int getInsertedItemId(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}
}
