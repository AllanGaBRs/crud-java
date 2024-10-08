package model.security;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.mindrot.jbcrypt.BCrypt;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class Security {
	private static UserDao userDao = DaoFactory.createUserDao();
	
	// Gerar hash da senha
	public static String hashPassword(String plainPassword) {
		// Gera o salt automaticamente
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}

	// Verificar se a senha fornecida corresponde ao hash
	public static boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

	public static String checkEmail(Scanner sc) {
		boolean validEmail = false;
		String email = "";
		while (!validEmail) {
			System.out.print("Digite seu email: ");
			email = sc.nextLine();

			// Expressão regular para validação de email
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(emailRegex);
			Matcher matcher = pattern.matcher(email);

			if (matcher.matches()) {
				validEmail = true;
				System.out.println("Email válido: " + email);
			} else {
				System.out.println("Email inválido. Por favor, tente novamente.");
			}
		}
		return email;
	}

	public static void sendCodeByEmail(User user) {
		System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		String code = code();
		String meuEmail = "inovatech.cisi@gmail.com";
		String minhaSenha = "colocar a senha aqui"; // Senha de app gerada no Gmail

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465); // Porta SSL
		email.setSSLOnConnect(true);
		email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));

		try {
			email.setFrom(meuEmail);
			email.setSubject("Mudança de senha");
			email.setMsg("Seu codigo é " + code);
			email.addTo(user.getEmail());
			email.send();
			
			user.setPasscode(code);
			userDao.updatePasscode(user);
			System.out.println("Email enviado com sucesso!");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	private static String code() {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int LENGTH = 8;
		SecureRandom random = new SecureRandom();

		StringBuilder sb = new StringBuilder(LENGTH);
		for (int i = 0; i < LENGTH; i++) {
			int index = random.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}
		return sb.toString();
	}
	
	public static User userVerification(User user) {
		User path = userVerify(user);
		if(path != null) {
			return path;
		}
		System.out.println("Dados inválidos. Tente novamente ou faça um cadastro");
		return null;
	}
	
	private static User userVerify(User user) {
		return userDao.findByEmailPassword(user);
	}
	
}
