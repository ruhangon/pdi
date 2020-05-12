package imagem;

import java.awt.image.BufferedImage;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ImagemPrimeiraProva {
	/*
	 * identifica o que há na imagem e retorna em String. Pode retornar reta
	 * horizontal, reta vertical, reta diagonal ou reta secundária
	 */
	public String identificaPadrao(BufferedImage imagem) {
		int largura = imagem.getWidth();
		int altura = imagem.getHeight();
		int posXPretoInicial = -1; // irá armazenar a posição de x do primeiro pixel preto encontrado
		int posYPretoInicial = -1; // irá analisar a posição de y do primeiro pixel preto encontrado
		boolean achouPreto = false; // variável de auxílio para sair dos laços que percorrem a matriz
		// percorre a matriz
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				int imagemArgb = imagem.getRGB(x, y);
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				// quando encontra o pixel preto (r0g0b0) saí da matriz com a posição salva
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
		// analisa se a reta é horizontal
		int pixelArgb = imagem.getRGB((posXPretoInicial + 1), posYPretoInicial);
		int red = (pixelArgb >> 16) & 0xFF;
		int green = (pixelArgb >> 8) & 0xFF;
		int blue = pixelArgb & 0xFF;
		// verifica se pixel horizontalmente vizinho é preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa é uma reta horizontal";
		// analisa se a reta é vertical
		pixelArgb = imagem.getRGB(posXPretoInicial, (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel verticalmente vizinho é preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa é uma reta vertical";
		// analisa se a reta é diagonal principal
		pixelArgb = imagem.getRGB((posXPretoInicial + 1), (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel diagonalmente vizinho é preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa é uma reta na diagonal principal";
		// analisa se a reta é diagonal secundária
		pixelArgb = imagem.getRGB((posXPretoInicial - 1), (posYPretoInicial + 1));
		red = (pixelArgb >> 16) & 0xFF;
		green = (pixelArgb >> 8) & 0xFF;
		blue = pixelArgb & 0xFF;
		// verifica se pixel diagonalmente vizinho é preto
		if ((red == 0) && (green == 0) && (blue == 0))
			return "Essa é uma reta na diagonal secundária";
		// se não for nenhuma das opções
		return "Nenhuma das opções";
	}

	/*
	 * mostra as informações dos pixels de uma região passada
	 */
	public void mostraPixels(BufferedImage imagem, Scanner scan) {
		int larguraA = -1;
		int alturaA = -1;
		int larguraB = -1;
		int alturaB = -1;
		do {
			try {
				System.out.println("Escolha a largura do ponto inicial");
				System.out.println("(0 até " + (imagem.getWidth() - 1) + ")");
				System.out.print("largura A: ");
				larguraA = scan.nextInt();
				scan.nextLine();
				if ((larguraA < 0) || (larguraA > (imagem.getWidth() - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				larguraA = -1;
			}
		} while ((larguraA < 0) || (larguraA > (imagem.getWidth() - 1)));
		do {
			try {
				System.out.println("Escolha a altura do ponto inicial");
				System.out.println("(0 até " + (imagem.getHeight() - 1) + ")");
				System.out.print("altura A: ");
				alturaA = scan.nextInt();
				scan.nextLine();
				if ((alturaA < 0) || (alturaA > (imagem.getHeight() - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				alturaA = -1;
			}
		} while ((alturaA < 0) || (alturaA > (imagem.getHeight() - 1)));
		do {
			try {
				System.out.println("Escolha a largura do ponto final");
				System.out.println("(0 até " + (imagem.getWidth() - 1) + ")");
				System.out.print("largura B: ");
				larguraB = scan.nextInt();
				scan.nextLine();
				if ((larguraB < 0) || (larguraB > (imagem.getWidth() - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				larguraB = -1;
			}
		} while ((larguraB < 0) || (larguraB > (imagem.getWidth() - 1)));
		do {
			try {
				System.out.println("Escolha a altura do ponto final");
				System.out.println("(0 até " + (imagem.getHeight() - 1) + ")");
				System.out.print("altura B: ");
				alturaB = scan.nextInt();
				scan.nextLine();
				if ((alturaB < 0) || (alturaB > (imagem.getHeight() - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				alturaB = -1;
			}
		} while ((alturaB < 0) || (alturaB > (imagem.getHeight() - 1)));
		System.out.println("largura/altura");
		System.out.println("Ponto inicial: " + (larguraA) + "/" + (alturaA));
		System.out.println("Ponto final: " + (larguraB) + "/" + (alturaB));
		// cuida para que a largura inicial e a altura inicial sempre sejam as menores
		// feito para não dar problema na hora de percorrer os laços
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
