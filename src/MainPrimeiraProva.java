import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import imagem.ImagemPrimeiraProva;

public class MainPrimeiraProva {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Identifica padrão em imagem (prova 1)");
		System.out.println("Identifica reta horizontal, reta vertical e também retas diagonais");
		String menu = "Menu \n1. Carrega imagem \n2. Identifica padrão (método da avaliação) \n3. Verifica proporção \n4. Verifica pixels de uma área da imagem \n0. Sai do programa";
		int op = -1;
		BufferedImage imagem = null;
		ImagemPrimeiraProva imagemPP = new ImagemPrimeiraProva();
		boolean existeImg = false;

		do {
			try {
				System.out.println();
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("Carrega uma imagem para o programa");
					// aqui procura arquivos de imagens de uma pasta do programa e mostra em uma
					// lista
					// o usuário então pode escolher uma imagem da lista que será carregada para o
					// programa
					// sem imagem carregada, ao escolher outra opção do programa, ele informa que
					// não há imagem no programa
					String[] nomesCaminho;
					// mostra lista de imagens da pasta provas
					File arquivo = new File("imgs/provas");
					nomesCaminho = arquivo.list();
					System.out.println("\n-- Lista de arquivos --");
					for (int i = 0; i < nomesCaminho.length; i++) {
						System.out.println((i + 1) + ". " + nomesCaminho[i]);
					}
					// se houver um arquivo na pasta pelo menos, pede para escolher imagem
					if (nomesCaminho.length > 0) {
						// pede para usuário escolher uma imagem da lista
						int opImg = -1;
						do {
							try {
								System.out.println("\nEscolha uma imagem da lista acima");
								System.out.print("opção: ");
								opImg = scan.nextInt();
								scan.nextLine();
								if ((opImg < 1) || (opImg > nomesCaminho.length))
									System.out.println("opção inválida");
							} catch (InputMismatchException e) {
								System.out.println("opção inválida");
								opImg = -1;
								scan.nextLine();
							}
						} while ((opImg < 1) || (opImg > nomesCaminho.length));
						String nomeDaImagem = "imgs/provas/";
						String nomeArq = nomesCaminho[(opImg - 1)];
						nomeDaImagem = nomeDaImagem.concat(nomeArq);
						try {
							imagem = ImageIO.read(new File(nomeDaImagem));
							System.out.println("Imagem " + nomeArq + " carregada");
							existeImg = true;
						} catch (IOException e) {
							System.out.println("Erro com BufferedImage");
						}
					} else {
						System.out.println("Não há arquivo na pasta");
					}
					break;

				case 2:
					System.out.println("Identifica padrão em imagem (método da avaliação)");
					// se existir imagem carregada identifica o padrão dela
					if (existeImg == true) {
						System.out.println(imagemPP.identificaPadrao(imagem));
					} else {
						System.out.println("Não existe imagem no programa");
					}
					break;

				case 3:
					System.out.println("Proporção da imagem (largura x altura)");
					if (existeImg == true) {
						System.out.println(imagem.getWidth() + " x " + imagem.getHeight());
					} else {
						System.out.println("Não existe imagem no programa");
					}
					break;

				case 4:
					System.out.println("Mostra informações de pixels dentro da coordenada passada");
					if (existeImg == true) {
						imagemPP.mostraPixels(imagem, scan);
					} else {
						System.out.println("Não existe imagem no programa");
					}
					break;

				case 0:
					System.out.println("Você escolheu sair");
					break;

				default:
					System.out.println("opção inválida");
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
