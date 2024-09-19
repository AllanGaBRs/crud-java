package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.security.Security;

public class Functions {

	private static UserDao userDao = DaoFactory.createUserDao();
	
	public static void register(Scanner sc) {
		System.out.println("Cadastro");
		System.out.print("Nick: ");
		String nick = sc.nextLine();
		String email = Security.checkEmail(sc);
		System.out.print("Password: ");
		String password = sc.nextLine();
		User user = new User(null, nick, email, password);
		if(userDao.findByEmail(user) != null) {
			System.out.println("Email ja cadastrado.");
			return;
		}
		userDao.insert(user);
		System.out.println("ID: " + user.getId());
	}
	
	private static User login(Scanner sc) {
		System.out.println("Login");
		String email = Security.checkEmail(sc);
		System.out.print("Password: ");
		String password = sc.nextLine();
		User user = new User(email, password);
		return Security.userVerification(user);
	}
	
	public static User delete(Scanner sc) {
		User user = login(sc);
		if(user == null) {
			return null;
		}
		char decision = 'a';
		while(decision != 's' && decision != 'n'){
			System.out.println("Deseja deletar a conta ID: " + user.getId() + " Nick: " + user.getName() + " ?[s/n]");
			decision = sc.next().toLowerCase().charAt(0);
			if(decision == 's') {
				userDao.deleteById(user.getId());
				System.out.println("Usuário " + user.getId() + " " + user.getName() + " deletado");
			}
			if(decision == 'n') {
				System.out.println("Ok");
			}			
		}
		return user;
	}
	
	public static void listAll(){
		List<User> users = userDao.findAll();
		System.out.println("Lista de Usuários cadastrados: ");
		for(User user : users) {
			System.out.println("ID: " + user.getId() + " Nick: " + user.getName());
		}
	}
	
	public static void changePassword(Scanner sc) {
		User user = login(sc);
		if(user == null) {
			return;
		}
		char decision = 'a';
		while(decision != 's' && decision != 'n'){
			System.out.println("Deseja mudar a senha da conta ID: " + user.getId() + " Nick: " + user.getName() + " ?[s/n]");
			decision = sc.next().toLowerCase().charAt(0);
			if(decision == 's') {
				Security.sendCodeByEmail(user);
				System.out.print("Digite o codigo que foi enviado ao seu email: ");
				sc.nextLine();
				String code = sc.nextLine().toUpperCase();
				if(code.equals(userDao.findPasscode(user))) {
					System.out.print("Digite a nova senha: ");
					String newPassword = sc.nextLine();
					user.setPassword(newPassword);
					userDao.update(user);
					System.out.println("Senha do Usuário " + user.getId() + " " + user.getName() + " alterada com sucesso!");
				}else {
					System.out.println("Codigo incorreto!");
				}
			}
			if(decision == 'n') {
				System.out.println("Ok");
			}			
		}
		
	}
}
