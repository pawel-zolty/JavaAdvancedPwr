package main.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Use this class with finally block
 */
public class MsSqlDbConnectionProvider {

	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;";
	static final String DataBaseName = "lab07db";
	static final String USER = "sa";
	static final String PASS = "pawel13!";

	private Connection connection = null;

	public Connection getConnection() {
		try {
			registerMsSqlJdbcDriver();
			String connectionString = getConnectionString();
			connection = createConnection(connectionString);
			return connection;
		} catch (ClassNotFoundException e) {
			System.err.println("Driver EXCEPTION");
			e.printStackTrace();
		} catch (SQLException se) {
			System.err.println("SQL EXCEPTION");
			se.printStackTrace();
		} catch (Exception se) {
			System.err.println("EXCEPTION");
			se.printStackTrace();
		}
		return null;
	}

	private Connection createConnection(String connectionString) throws SQLException {
		return DriverManager.getConnection(connectionString);
	}

	private String getConnectionString() {
		System.out.println("Connecting to database...");
		String connectionString = DB_URL + "databaseName=" + DataBaseName + ";user=" + USER + ";password=" + PASS + ";";
		return connectionString;
	}

	private void registerMsSqlJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}
}
