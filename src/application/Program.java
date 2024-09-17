package application;

import model.dao.DaoFactory;
import model.dao.UserDao;

public class Program {

	public static void main(String[] args) {

		UserDao userDao = DaoFactory.createUserDao();
		System.out.println(userDao);
	}
}
