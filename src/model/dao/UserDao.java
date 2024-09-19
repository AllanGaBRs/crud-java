package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {

	void insert(User obj);
	void update(User obj);
	void updatePasscode(User obj);
	void deleteById(Integer id);
	User findById(Integer id);
	User findByEmailPassword(User obj);
	User findByEmail(User obj);
	List<User> findAll();
	String findPasscode(User obj);
	
}
