package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static final String user = "root";
	private static final String password = "";
	private static final String dburl = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE_NAME = "CRUD";
	private static Connection conn = null;
	private static Statement st = null;

	public static void createData() {
	}

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void createDatabase() {
		try {
			conn = DriverManager.getConnection(dburl, user, password);
			st = conn.createStatement();
			String createDataBase = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
			st.execute(createDataBase);
			createTable();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	private static void createTable() {
		try {
			conn = DriverManager.getConnection(dburl, user, password);
			st = conn.createStatement();
			String useDatabase = "USE " + DATABASE_NAME;
			st.executeUpdate(useDatabase);
			String createTable = "CREATE TABLE IF NOT EXISTS USERS("
					+ "id int primary key auto_increment, "
					+ "nome varchar(255) not null, "
					+ "email varchar(255) not null, "
					+ "senha varchar(255) not null, "
					+ "passcode varchar(255)"
					+ ")";
			st.execute(createTable);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
