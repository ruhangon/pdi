import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class TesteComImagens {
	public static void main(String[] args) {
		System.out.println("    Teste com imagens    \n");
		Scanner scan = new Scanner(System.in);
		String caminhoImg = " ";
		BufferedImage minhaImagem = null;
		String resposta = "S";
		boolean existeImg = false;
		Color infosImg;
		String menu = "Menu \n" + "1. Mostra caminho da imagem \n"
				+ "2. Mostra largura x altura (quantidade de pixels) \n"
				+ "3. Escolhe coordenadas x,y e verifica o RGB da coordenada \n" + "0. Sai";

		int op = -1;

		do {
			do {
				System.out.println("Indique o caminho da imagem a ser carregada");
				System.out.println("digite também a extensão (jpg, png, dentre outras)");
				System.out.print("caminho: ");
				caminhoImg = scan.nextLine();
				if (new File(caminhoImg).exists())
					existeImg = true;
				if (existeImg == false)
					System.out.println("O caminho passado para a imagem não existe");
			} while (existeImg != true);

			System.out.println();
			try {
				minhaImagem = ImageIO.read(new File(caminhoImg));
			} catch (IOException e) {
				System.out.println("Erro com BufferedImage");
			}

			do {
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
				op = scan.nextInt();
				scan.nextLine();
				try {
					switch (op) {
					case 1:
						System.out.println("Caminho da imagem");
						System.out.println(caminhoImg);
						System.out.println();
						break;

					case 2:
						System.out.println("largura x altura");
						System.out.println(minhaImagem.getWidth() + "x" + minhaImagem.getHeight());
						System.out.println();
						break;

					case 3:
						int xImg = -1;
						int yImg = -1;
						// escolhe a coordenada x (largura)
						do {
							try {
								System.out.println("Largura");
								System.out.println("(0 até " + (minhaImagem.getWidth() - 1) + ")");
								System.out.print("coordenada x: ");
								xImg = scan.nextInt();
								scan.nextLine();
								if ((xImg < 0) && (xImg > (minhaImagem.getWidth() - 1)))
									System.out.println("opção inválida");
							} catch (InputMismatchException e) {
								System.out.println("opção inválida");
								scan.nextLine();
								xImg = -1;
							}
						} while ((xImg < 0) || (xImg > (minhaImagem.getWidth() - 1)));
						// escolhe a coordenada y (altura)
						do {
							try {
								System.out.println("Altura");
								System.out.println("(0 até " + (minhaImagem.getHeight() - 1) + ")");
								System.out.print("coordenada y: ");
								yImg = scan.nextInt();
								scan.nextLine();
								if ((yImg < 0) && (yImg > (minhaImagem.getHeight() - 1)))
									System.out.println("opção inválida");
							} catch (InputMismatchException e) {
								System.out.println("opção inválida");
								scan.nextLine();
								yImg = -1;
							}
						} while ((yImg < 0) || (yImg > (minhaImagem.getHeight() - 1)));
						/*
						 * usa objeto da classe Color para conseguir valores do RGB separados. Só usando
						 * BufferedImage a coleta dos valores não é tão simples
						 */
						infosImg = new Color(minhaImagem.getRGB(xImg, yImg));
						int red = infosImg.getRed();
						int green = infosImg.getGreen();
						int blue = infosImg.getBlue();
						System.out.println(red + " - " + green + " - " + blue);
						System.out.println();
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

			do {
				System.out.println("Você deseja continuar no programa");
				System.out.print("resposta: ");
				resposta = scan.nextLine();
				if ((!resposta.equalsIgnoreCase("S")) && (!resposta.equalsIgnoreCase("N")))
					System.out.println("opção inválida");
			} while (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("N"));
		} while (!resposta.equalsIgnoreCase("N"));

		System.out.println("\n\nFim do programa");

	}
}
