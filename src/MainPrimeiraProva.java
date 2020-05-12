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
		System.out.println("Identifica padr�o em imagem (prova 1)");
		System.out.println("Identifica reta horizontal, reta vertical e tamb�m retas diagonais");
		String menu = "Menu \n1. Carrega imagem \n2. Identifica padr�o (m�todo da avalia��o) \n3. Verifica propor��o \n4. Verifica pixels de uma �rea da imagem \n0. Sai do programa";
		int op = -1;
		BufferedImage imagem = null;
		ImagemPrimeiraProva imagemPP = new ImagemPrimeiraProva();
		boolean existeImg = false;

		do {
			try {
				System.out.println();
				System.out.println(menu);
				System.out.print("escolha uma op��o: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("Carrega uma imagem para o programa");
					// aqui procura arquivos de imagens de uma pasta do programa e mostra em uma
					// lista
					// o usu�rio ent�o pode escolher uma imagem da lista que ser� carregada para o
					// programa
					// sem imagem carregada, ao escolher outra op��o do programa, ele informa que
					// n�o h� imagem no programa
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
						// pede para usu�rio escolher uma imagem da lista
						int opImg = -1;
						do {
							try {
								System.out.println("\nEscolha uma imagem da lista acima");
								System.out.print("op��o: ");
								opImg = scan.nextInt();
								scan.nextLine();
								if ((opImg < 1) || (opImg > nomesCaminho.length))
									System.out.println("op��o inv�lida");
							} catch (InputMismatchException e) {
								System.out.println("op��o inv�lida");
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
						System.out.println("N�o h� arquivo na pasta");
					}
					break;

				case 2:
					System.out.println("Identifica padr�o em imagem (m�todo da avalia��o)");
					// se existir imagem carregada identifica o padr�o dela
					if (existeImg == true) {
						System.out.println(imagemPP.identificaPadrao(imagem));
					} else {
						System.out.println("N�o existe imagem no programa");
					}
					break;

				case 3:
					System.out.println("Propor��o da imagem (largura x altura)");
					if (existeImg == true) {
						System.out.println(imagem.getWidth() + " x " + imagem.getHeight());
					} else {
						System.out.println("N�o existe imagem no programa");
					}
					break;

				case 4:
					System.out.println("Mostra informa��es de pixels dentro da coordenada passada");
					if (existeImg == true) {
						imagemPP.mostraPixels(imagem, scan);
					} else {
						System.out.println("N�o existe imagem no programa");
					}
					break;

				case 0:
					System.out.println("Voc� escolheu sair");
					break;

				default:
					System.out.println("op��o inv�lida");
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
