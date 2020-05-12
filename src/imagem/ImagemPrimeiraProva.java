package imagem;

import java.awt.image.BufferedImage;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ImagemPrimeiraProva {
	/*
	 * identifica o que h� na imagem e retorna em String. Pode retornar reta
	 * horizontal, reta vertical, reta diagonal ou reta secund�ria
	 */
	public String identificaPadrao(BufferedImage imagem) {
		int largura = imagem.getWidth();
		int altura = imagem.getHeight();
		int posXPretoInicial = -1; // ir� armazenar a posi��o de x do primeiro pixel preto encontrado
		int posYPretoInicial = -1; // ir� analisar a posi��o de y do primeiro pixel preto encontrado
		boolean achouPreto = false; // vari�vel de aux�lio para sair dos la�os que percorrem a matriz
		// percorre a matriz
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				int imagemArgb = imagem.getRGB(x, y);
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				// quando encontra o pixel preto (r0g0b0) sa� da matriz com a posi��o salva
				if ((red == 0) && (green == 0) && (blue == 0)) {
					posXPretoInicial = x;
					posYPretoInicial = y;
					achouPreto = true;
					break;
				}
			}
			if (achouPreto == true) {
				break;
			}
		}
		// analisa se a reta � horizontal
		int pixelArgb = imagem.getRGB((posXPretoInicial + 1), posYPretoInicial);
		int red = (pixelArgb >> 16) & 0xFF;
		int green = (pixelArgb >> 8) & 0xFF;
		int blue = pixelArgb & 0xFF;
		// verifica se pixel horizontalmente vizinho � preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa � uma reta horizontal";
		// analisa se a reta � vertical
		pixelArgb = imagem.getRGB(posXPretoInicial, (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel verticalmente vizinho � preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa � uma reta vertical";
		// analisa se a reta � diagonal principal
		pixelArgb = imagem.getRGB((posXPretoInicial + 1), (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel diagonalmente vizinho � preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa � uma reta na diagonal principal";
		// analisa se a reta � diagonal secund�ria
		pixelArgb = imagem.getRGB((posXPretoInicial - 1), (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel diagonalmente vizinho � preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa � uma reta na diagonal secund�ria";
		// se n�o for nenhuma das op��es
		return "Nenhuma das op��es";
	}

	/*
	 * mostra as informa��es dos pixels de uma regi�o passada
	 */
	public void mostraPixels(BufferedImage imagem, Scanner scan) {
		int larguraA = -1;
		int alturaA = -1;
		int larguraB = -1;
		int alturaB = -1;
		do {
			try {
				System.out.println("Escolha a largura do ponto inicial");
				System.out.println("(0 at� " + (imagem.getWidth() - 1) + ")");
				System.out.print("largura A: ");
				larguraA = scan.nextInt();
				scan.nextLine();
				if ((larguraA < 0) || (larguraA > (imagem.getWidth() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				larguraA = -1;
			}
		} while ((larguraA < 0) || (larguraA > (imagem.getWidth() - 1)));
		do {
			try {
				System.out.println("Escolha a altura do ponto inicial");
				System.out.println("(0 at� " + (imagem.getHeight() - 1) + ")");
				System.out.print("altura A: ");
				alturaA = scan.nextInt();
				scan.nextLine();
				if ((alturaA < 0) || (alturaA > (imagem.getHeight() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				alturaA = -1;
			}
		} while ((alturaA < 0) || (alturaA > (imagem.getHeight() - 1)));
		do {
			try {
				System.out.println("Escolha a largura do ponto final");
				System.out.println("(0 at� " + (imagem.getWidth() - 1) + ")");
				System.out.print("largura B: ");
				larguraB = scan.nextInt();
				scan.nextLine();
				if ((larguraB < 0) || (larguraB > (imagem.getWidth() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				larguraB = -1;
			}
		} while ((larguraB < 0) || (larguraB > (imagem.getWidth() - 1)));
		do {
			try {
				System.out.println("Escolha a altura do ponto final");
				System.out.println("(0 at� " + (imagem.getHeight() - 1) + ")");
				System.out.print("altura B: ");
				alturaB = scan.nextInt();
				scan.nextLine();
				if ((alturaB < 0) || (alturaB > (imagem.getHeight() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				alturaB = -1;
			}
		} while ((alturaB < 0) || (alturaB > (imagem.getHeight() - 1)));
		System.out.println("largura/altura");
		System.out.println("Ponto inicial: " + (larguraA) + "/" + (alturaA));
		System.out.println("Ponto final: " + (larguraB) + "/" + (alturaB));
		// cuida para que a largura inicial e a altura inicial sempre sejam as menores
		// feito para n�o dar problema na hora de percorrer os la�os
		int aux;
		if (larguraB < larguraA) {
			aux = larguraB;
			larguraB = larguraA;
			larguraA = aux;
		}
		if (alturaB < alturaA) {
			aux = alturaB;
			alturaB = alturaA;
			alturaA = aux;
		}
		for (int y = alturaA; y <= alturaB; y++) {
			for (int x = larguraA; x <= larguraB; x++) {
				int imagemArgb = imagem.getRGB(x, y);
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				System.out.println("x" + x + "y" + y + ": " + (red) + " - " + (green) + " - " + (blue));
			}
		}
	}

}
