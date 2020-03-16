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
				+ "3. Descobre RGB em uma coordenada de uma imagem \n" + "4. Filtros de cinza \n"
				+ "0. Sai do programa";

		int op = -1;

		do {
			try {
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
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
							System.out.println("a imagem já deve estar no programa");
							System.out.print("opção: ");
							opImg = scan.nextInt();
							scan.nextLine();
							if ((opImg != 1) && (opImg != 2))
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							opImg = -1;
						}
					} while ((opImg != 1) && (opImg != 2));
					// agora chama o método passando a imagem escolhida como parâmetro
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

				case 4:
					// será aplicado na imagem 1 a princípio
					System.out.println("Escolha um dos filtros de cinza");
					int opFiltros = -1;
					do {
						try {
							System.out.println("Escolha o filtro de cinza por média aritmética ou ponderada");
							System.out.println("1. média aritmética, 2. média ponderada");
							System.out.print("opção: ");
							opFiltros = scan.nextInt();
							scan.nextLine();
							if ((opFiltros != 1) && (opFiltros != 2))
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							opFiltros = -1;
						}
					} while ((opFiltros != 1) && (opFiltros != 2));
					if (opFiltros == 1) {
						imagem.filtroCinzaAritmetico(minhaImagem1);
					}
					if (opFiltros == 2) {
						System.out.println("a fazer");
					}
					break;

				case 0:
					break;

				default:
					System.out.println("opção inválida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		System.out.println("\n\nFim do programa");

	}
}
