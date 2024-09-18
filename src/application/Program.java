package application;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.security.Security;

public class Program {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Scanner sc = new Scanner(System.in);
		UserDao userDao = DaoFactory.createUserDao();
		System.out.println(userDao);

		String plainPassword = "minhaSenha123";
		BCrypt.hashpw(plainPassword, BCrypt.gensalt());
		String cript = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
		boolean isCorrect = Security.checkPassword("minhaSenha123", cript);
		System.out.println(cript);
		System.out.println("Senha correta: " + isCorrect);  // Retorna true
	
		User user = new User("allan", "allan@", plainPassword); 
		//userDao.insert(user);
		System.out.println(user.getPassword());
		user = userDao.findByEmailPassword(user);
		
		user.setPassword("123");
		userDao.update(user);
		System.out.println(user.getPassword());

	}
}
