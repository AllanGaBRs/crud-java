package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DB;
import database.DbException;
import model.dao.UserDao;
import model.entities.User;
import model.security.Security;

public class UserDaoJDBC implements UserDao {

	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;

	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(User obj) {
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement(
					"INSERT INTO users " + "(Name, Email, Password, Passcode) " + "VALUES " + "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, Security.hashPassword(obj.getPassword()));
			st.setString(4, obj.getPasscode());

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
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException rollback) {
				throw new DbException(rollback.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(User obj) {
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement(
					"UPDATE users " + "SET Name = ?, Email = ?, Password = ?, Passcode = ? " + "WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, Security.hashPassword(obj.getPassword()));
			st.setString(4, obj.getPasscode());
			st.setInt(5, obj.getId());

			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException rollback) {
				throw new DbException(rollback.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		try {
			conn.setAutoCommit(false);

			st = conn.prepareStatement("DELETE FROM users WHERE Id = ?");
			st.setInt(1, id);

			st.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException rollback) {
				throw new DbException(rollback.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public User findById(Integer id) {
		try {
			st = conn.prepareStatement("SELECT * FROM users " + "WHERE Id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateUser(rs);
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<User> findAll() {
		try {
			st = conn.prepareStatement("SELECT * FROM users");

			rs = st.executeQuery();

			Map<Integer, User> map = new HashMap<>();
			List<User> departments = new ArrayList<>();

			while (rs.next()) {

				User user = map.get(rs.getInt("Id"));

				if (user == null) {
					user = instantiateUser(rs);
					map.put(user.getId(), user);
				}

				departments.add(user);
			}

			return departments;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public User findByEmailPassword(User obj) {
		try {
			st = conn.prepareStatement("SELECT * FROM USERS WHERE email = ?");
			st.setString(1, obj.getEmail());

			rs = st.executeQuery();
			if (rs.next()) {
				String hashedPassword = rs.getString("password");
				if (Security.checkPassword(obj.getPassword(), hashedPassword)) {
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

	@Override
	public void updatePasscode(User obj) {
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement("UPDATE users " + "SET Passcode = ? " + "WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getPasscode());
			st.setInt(2, obj.getId());

			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException(e.getMessage());
			} catch (SQLException rollback) {
				throw new DbException(rollback.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public String findPasscode(User obj) {
		try {
			st = conn.prepareStatement("SELECT passcode FROM users " + "WHERE Id = ?");
			st.setInt(1, obj.getId());

			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("passcode");
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public User findByEmail(User obj) {
		try {
			st = conn.prepareStatement("SELECT * FROM USERS WHERE email = ?");
			st.setString(1, obj.getEmail());

			rs = st.executeQuery();
			if (rs.next()) {
				User user = instantiateUser(rs);
				return user;
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
		obj.setPasscode(rs.getString("Passcode"));
		return obj;
	}

}
