package application;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class Program {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		UserDao userDao = DaoFactory.createUserDao();
		List<User> list = userDao.findAll();
		
		for(User user : list) {
			System.out.println(user.getName());
		}
		System.out.println();
		
		User user = userDao.findById(1);
		System.out.println(user.getName());
		
		/*Scanner sc = new Scanner(System.in);

		System.out.print("Nick: ");
		String nick = sc.nextLine();
		String email = Security.checkEmail(sc);

		System.out.print("Password: ");
		String password = sc.nextLine();
		BCrypt.hashpw(password, BCrypt.gensalt());

		User user = new User(nick, email, BCrypt.hashpw(password, BCrypt.gensalt()));
		userDao.insert(user);
		System.out.println("your id " + user.getId());
*/
	}
}
