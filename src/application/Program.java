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
		UserDao userDao = DaoFactory.createUserDao();
		
		Scanner sc = new Scanner(System.in);

		String email = Security.checkEmail(sc);

		System.out.print("Password: ");
		String password = sc.nextLine();

	
		User user = new User(email, password);
		
		user = Security.userVerification(user, sc);
		System.out.println(user.getEmail() + " " + user.getPassword());
		
		//Security.sendCodeByEmail(user);	
		//System.out.println("your id " + user.getId());

	}
}
