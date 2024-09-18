package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

	private static final String user = "root";
	private static final String password = "";
	private static final String dburl = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE_NAME = "CRUD";
	private static Statement st = null;
	private static Connection conn = null;

	//Metodo para criar o banco automaticamente
	public static void createDatabase() {
		try {
			conn = DriverManager.getConnection(dburl, user, password);
			st = conn.createStatement();
			String createDataBase = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
			st.execute(createDataBase);
			//Metodo para criar a tabela
			createTable();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	//Metodo para criar a tabela
	private static void createTable() {
		try {
			conn = DriverManager.getConnection(dburl, user, password);
			st = conn.createStatement();
			String useDatabase = "USE " + DATABASE_NAME;
			st.executeUpdate(useDatabase);
			String createTable = "CREATE TABLE IF NOT EXISTS USERS("
					+ "id int primary key auto_increment, "
					+ "name varchar(255) not null, "
					+ "email varchar(255) not null, "
					+ "password varchar(255) not null, "
					+ "passcode varchar(255)"
					+ ")";
			st.execute(createTable);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
