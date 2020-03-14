import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import imagem.Imagem;

public class Main {
	public static void main(String[] args) {
		System.out.println("    Processamento digital de imagens    \n");
		Scanner scan = new Scanner(System.in);
		String caminhoImg = " ";
		Imagem imagem = new Imagem();
		BufferedImage minhaImagem1 = null;
		BufferedImage minhaImagem2 = null;
		Color infosImg1;
		Color infosImg2;
		String menu = "Menu \n" + "1. Escolhe imagem 1 \n" + "2. Escolhe imagem 2 \n"
				+ "3. Descobre RGB em uma coordenada de uma imagem \n" + "0. Sai do programa";

		int op = -1;

		do {
			try {
				System.out.println(menu);
				System.out.print("escolha uma op��o: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("Imagem 1");
					caminhoImg = imagem.escolheImagem();
					try {
						minhaImagem1 = ImageIO.read(new File(caminhoImg));
					} catch (IOException e) {
						System.out.println("Erro com BufferedImage");
					}
					break;

				case 2:
					System.out.println("Imagem 2");
					caminhoImg = imagem.escolheImagem();
					try {
						minhaImagem2 = ImageIO.read(new File(caminhoImg));
					} catch (IOException e) {
						System.out.println("Erro com BufferedImage");
					}
					break;

				case 3:
					int opImg = -1;
					do {
						try {
							System.out.println("Escolha a imagem 1 ou a imagem 2");
							System.out.println("a imagem j� deve estar no programa");
							System.out.print("op��o: ");
							opImg = scan.nextInt();
							scan.nextLine();
						} catch (InputMismatchException e) {
							System.out.println("op��o inv�lida");
							scan.nextLine();
							opImg = -1;
						}
					} while ((opImg != 1) && (opImg != 2));
					// agora chama o m�todo passando a imagem escolhida como par�metro
					if (opImg == 1) {
						infosImg1 = imagem.retornaRGBEmXY(minhaImagem1);
						System.out.println("Red - Green - Blue");
						System.out.println(
								infosImg1.getRed() + " - " + infosImg1.getGreen() + " - " + infosImg1.getBlue());
					}
					if (opImg == 2) {
						infosImg2 = imagem.retornaRGBEmXY(minhaImagem2);
						System.out.println("Red - Green - Blue");
						System.out.println(
								infosImg2.getRed() + " - " + infosImg2.getGreen() + " - " + infosImg2.getBlue());
					}
					break;

				case 0:
					break;

				default:
					System.out.println("op��o inv�lida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		System.out.println("\n\nFim do programa");

	}
}
