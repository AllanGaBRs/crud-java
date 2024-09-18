package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {

	void insert(User obj);
	void update(User obj);
	void inactivateById(Integer id);
	User findById(Integer id);
	User findByEmailPassword(User obj);
	List<User> findAll();
	
}
