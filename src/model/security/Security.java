package model.security;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

public class Security {

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
}
