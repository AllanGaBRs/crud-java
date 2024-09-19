package application;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		Scanner sc = new Scanner(System.in);
		boolean continuar = true;

		while (continuar) {
			System.out.println("Escolha uma opção:");
			System.out.println("1 - Registrar");
			System.out.println("2 - Listar todos");
			System.out.println("3 - Excluir");
			System.out.println("4 - Alterar senha");
			System.out.println("5 - Sair");

			int opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1:
				Functions.register(sc);
				break;
			case 2:
				Functions.listAll();
				break;
			case 3:
				Functions.delete(sc);
				break;
			case 4:
				Functions.changePassword(sc);
				break;
			case 5:
				continuar = false;
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		}

		sc.close();
	}
}
