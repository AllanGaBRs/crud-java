package model.security;

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
    
}
