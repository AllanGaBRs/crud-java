package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import database.DB;
import database.DbException;
import model.dao.UserDao;
import model.entities.User;

public class UserDaoJDBC implements UserDao {

	private Connection conn;
	PreparedStatement st;
	ResultSet rs;

	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(User obj) {
		try {
			st = conn.prepareStatement("INSERT INTO users " + "(Name, Email, Password) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getPassword());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(User obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inactivateById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByEmailPassword(User obj) {
		try {
			st = conn.prepareStatement("SELECT * FROM USERS WHERE email = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getEmail());
			
			rs = st.executeQuery();
			if (rs.next()) {
				String hashedPassword = rs.getString("password");
				if (BCrypt.checkpw(obj.getPassword(), hashedPassword)) {
					// Senha correta
					User user = instantiateUser(rs);
					return user;
				}
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private User instantiateUser(ResultSet rs) throws SQLException {
		User obj = new User();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setPassword(rs.getString("Password"));
		return obj;
	}
}
