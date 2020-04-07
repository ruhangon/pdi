package imagem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Imagem {
	public String escolheImagem() {
		Scanner scan = new Scanner(System.in);
		boolean existeImg = false;
		String caminhoImg = " ";
		do {
			System.out.println("Indique o caminho da imagem a ser carregada");
			System.out.println("digite tamb�m a extens�o (jpg, png, dentre outras)");
			System.out.print("caminho: ");
			caminhoImg = scan.nextLine();
			if (new File(caminhoImg).exists())
				existeImg = true;
			if (existeImg == false)
				System.out.println("O caminho passado para a imagem n�o existe");
		} while (existeImg != true);
		return caminhoImg;
	}

	public Color retornaRGBEmXY(BufferedImage minhaImagem) {
		Scanner scan = new Scanner(System.in);
		int xImg = -1;
		int yImg = -1;
		// escolhe a coordenada x (largura)
		do {
			try {
				System.out.println("Largura");
				System.out.println("(0 at� " + (minhaImagem.getWidth() - 1) + ")");
				System.out.print("coordenada x: ");
				xImg = scan.nextInt();
				scan.nextLine();
				if ((xImg < 0) && (xImg > (minhaImagem.getWidth() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				xImg = -1;
			}
		} while ((xImg < 0) || (xImg > (minhaImagem.getWidth() - 1)));
		// escolhe a coordenada y (altura)
		do {
			try {
				System.out.println("Altura");
				System.out.println("(0 at� " + (minhaImagem.getHeight() - 1) + ")");
				System.out.print("coordenada y: ");
				yImg = scan.nextInt();
				scan.nextLine();
				if ((yImg < 0) && (yImg > (minhaImagem.getHeight() - 1)))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				yImg = -1;
			}
		} while ((yImg < 0) || (yImg > (minhaImagem.getHeight() - 1)));
		/*
		 * usa objeto da classe Color para conseguir valores do RGB separados. S� usando
		 * BufferedImage a coleta dos valores n�o � t�o simples
		 */
		Color infosImg = new Color(minhaImagem.getRGB(xImg, yImg));
		return infosImg;
	}

	public void filtroCinzaAritmetico(BufferedImage imagem) {
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		for (int x = 0; x < imagem.getWidth(); x++) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				// Color imagemRGB = new Color(imagem.getRGB(x, y));
				// int novaCor = ((imagemRGB.getRed() + imagemRGB.getGreen() +
				// imagemRGB.getBlue()) / 3);
				// imagemRGB.getRGB() retorna um inteiro de 32 bits com as informa��es do argb
				int imagemArgb = pr.getArgb(x, y);
				int alpha = (imagemArgb >> 24) & 0xFF;
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				int novaCorRgb = ((red + green + blue) / 3);
				Color novaCor = new Color(novaCorRgb, novaCorRgb, novaCorRgb, alpha);
				pw.setArgb(x, y, novaCor.getRGB());
			}
		}
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), "png",
					new File("imgs/filtros/novaimagemcomcinzaaritmetico.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem com filtro cinza aritm�tico foi salva na pasta imgs/filtros");
	}

	public void filtroCinzaPonderado(BufferedImage imagem, int perc1, int perc2, int perc3) {
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		for (int x = 0; x < imagem.getWidth(); x++) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				int imagemArgb = pr.getArgb(x, y);
				int alpha = (imagemArgb >> 24) & 0xFF;
				int red = (imagemArgb >> 16) & 0xFF;
				red = red * perc1;
				int green = (imagemArgb >> 8) & 0xFF;
				green = green * perc2;
				int blue = imagemArgb & 0xFF;
				blue = blue * perc3;
				int novaCorRgb = ((red + green + blue) / 100);
				Color novaCor = new Color(novaCorRgb, novaCorRgb, novaCorRgb, alpha);
				pw.setArgb(x, y, novaCor.getRGB());
			}
		}
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), "png",
					new File("imgs/filtros/novaimagemcomcinzaponderado.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem com filtro cinza ponderado foi salva na pasta imgs/filtros");
	}

	public void filtroParaLimiarizacao(BufferedImage imagem, int limiar, String caminho) {
		Scanner scan = new Scanner(System.in);
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		int opRgb = 0; // usado para descobrir em qual canal ser� feito a binariza��o
		do {
			try {
				System.out.println("Escolha em qual canal ser� feita a lineariza��o");
				System.out.println("1. R, 2. G, 3. B");
				System.out.print("canal: ");
				opRgb = scan.nextInt();
				scan.nextLine();
				if ((opRgb < 1) || (opRgb > 3))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				opRgb = 0;
			}
		} while ((opRgb < 1) || (opRgb > 3));
		// agora passa pela matriz de RGB da imagem para alterar os pixels do canal
		// escolhido
		for (int x = 0; x < imagem.getWidth(); x++) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				int imagemArgb = pr.getArgb(x, y);
				int alpha = (imagemArgb >> 24) & 0xFF;
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				// se opRgb for 1 altera o red
				if (opRgb == 1) {
					if (limiar > red)
						red = 0;
					if (limiar < red)
						red = 255;
					Color novaCor = new Color(red, green, blue, alpha);
					pw.setArgb(x, y, novaCor.getRGB());
				}
				// se opRgb for 2 altera o green
				if (opRgb == 2) {
					if (limiar > green)
						green = 0;
					if (limiar < green)
						green = 255;
					Color novaCor = new Color(red, green, blue, alpha);
					pw.setArgb(x, y, novaCor.getRGB());
				}
				// se opRgb for 3 altera o blue
				if (opRgb == 3) {
					if (limiar > blue)
						blue = 0;
					if (limiar < blue)
						blue = 255;
					Color novaCor = new Color(red, green, blue, alpha);
					pw.setArgb(x, y, novaCor.getRGB());
				}
			}
		}
		// descobre o tipo da imagem para poder salvar ela
		String tipoImg = retornaExtensao(caminho);
		// cria o caminho com o nome do arquivo
		String nomeDoArquivo = "imgs/filtros/novaimagemcomfiltrodelimiarizacao.";
		nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
		// salva a nova imagem na pasta filtros
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem com filtro de limiariza��o foi salva na pasta imgs/filtros");
	}

	// m�todo para aplicar filtro de negativa
	public void filtroDeNegativa(BufferedImage imagem, String caminho) {
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		for (int x = 0; x < imagem.getWidth(); x++) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				int imagemArgb = pr.getArgb(x, y);
				int alpha = (imagemArgb >> 24) & 0xFF;
				int red = (imagemArgb >> 16) & 0xFF;
				red = 255 - red;
				int green = (imagemArgb >> 8) & 0xFF;
				green = 255 - green;
				int blue = imagemArgb & 0xFF;
				blue = 255 - blue;
				Color novaCor = new Color(red, green, blue, alpha);
				pw.setArgb(x, y, novaCor.getRGB());
			}
		}
		// descobre o tipo da imagem para poder salvar ela
		String tipoImg = retornaExtensao(caminho);
		// cria o caminho com o nome do arquivo
		String nomeDoArquivo = "imgs/filtros/novaimagemcomfiltrodenegativa.";
		nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
		// salva a nova imagem na pasta filtros
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem com filtro de negativa foi salva na pasta imgs/filtros");
	}

	// m�todo para aplicar filtro de elimina��o de ru�dos por mediana
	public void filtroDeEliminacaoDeRuidos(BufferedImage imagem, String caminho) {
		Scanner scan = new Scanner(System.in);
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		// escolhe qual vizinhan�a ser� usada (em x, em cruz ou 3 por 3 )
		int opViz = -1;
		do {
			try {
				System.out
						.println("Escolha qual vizinhan�a ser� aplicada para realizar o filtro de elimina��o de ru�do");
				System.out.println("1. Vizinhan�a em x, 2. Vizinhan�a em cruz, 3. Vizinhan�a 3 por 3");
				System.out.print("resposta: ");
				opViz = scan.nextInt();
				scan.nextLine();
				if ((opViz < 1) || (opViz > 3))
					System.out.println("op��o inv�lida");
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				opViz = -1;
			}
		} while ((opViz < 1) || (opViz > 3));
		// se escolheu a op��o 1, ent�o aplica o filtro com vizinhan�a em x
		if (opViz == 1) {
			// passa pela matriz sem analisar os pixels das bordas da imagem
			for (int x = 1; x < (imagem.getWidth() - 1); x++) {
				for (int y = 1; y < (imagem.getHeight() - 1); y++) {
					int imagemArgb = pr.getArgb(x, y);
					int alpha = (imagemArgb >> 24) & 0xFF;
					int red = (imagemArgb >> 16) & 0xFF;
					int green = (imagemArgb >> 8) & 0xFF;
					int blue = imagemArgb & 0xFF;
					Pixel pixel = new Pixel(alpha, red, green, blue);
					pixel = vizinhancaEmX(imagemWI, pixel, x, y);
					Color novaCor = calculaMediana(pixel);
					pw.setArgb(x, y, novaCor.getRGB());
				}
			}
			// salva a imagem
			// descobre o tipo da imagem para poder salvar ela
			String tipoImg = retornaExtensao(caminho);
			// cria o caminho com o nome do arquivo
			String nomeDoArquivo = "imgs/filtros/novaimagemsemruidosporx.";
			nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
			// salva a nova imagem na pasta filtros
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("A imagem com filtro de elimina��o de ru�do por X foi salva na pasta imgs/filtros");
		}
		// se escolheu a op��o 2, ent�o aplica o filtro com vizinhan�a em cruz
		if (opViz == 2) {
			// passa pela matriz sem analisar os pixels das bordas da imagem
			for (int x = 1; x < (imagem.getWidth() - 1); x++) {
				for (int y = 1; y < (imagem.getHeight() - 1); y++) {
					int imagemArgb = pr.getArgb(x, y);
					int alpha = (imagemArgb >> 24) & 0xFF;
					int red = (imagemArgb >> 16) & 0xFF;
					int green = (imagemArgb >> 8) & 0xFF;
					int blue = imagemArgb & 0xFF;
					Pixel pixel = new Pixel(alpha, red, green, blue);
					pixel = vizinhancaEmCruz(imagemWI, pixel, x, y);
					Color novaCor = calculaMediana(pixel);
					pw.setArgb(x, y, novaCor.getRGB());
				}
			}
			// salva a imagem
			// descobre o tipo da imagem para poder salvar ela
			String tipoImg = retornaExtensao(caminho);
			// cria o caminho com o nome do arquivo
			String nomeDoArquivo = "imgs/filtros/novaimagemsemruidosporcruz.";
			nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
			// salva a nova imagem na pasta filtros
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("A imagem com filtro de elimina��o de ru�do por cruz foi salva na pasta imgs/filtros");
		}
		// se escolheu a op��o 3, ent�o aplica o filtro com vizinhan�a 3 por 3
		if (opViz == 3) {
			// passa pela matriz sem analisar os pixels das bordas da imagem
			for (int x = 1; x < (imagem.getWidth() - 1); x++) {
				for (int y = 1; y < (imagem.getHeight() - 1); y++) {
					int imagemArgb = pr.getArgb(x, y);
					int alpha = (imagemArgb >> 24) & 0xFF;
					int red = (imagemArgb >> 16) & 0xFF;
					int green = (imagemArgb >> 8) & 0xFF;
					int blue = imagemArgb & 0xFF;
					Pixel pixel = new Pixel(alpha, red, green, blue);
					pixel = vizinhancaTresPorTres(imagemWI, pixel, x, y);
					Color novaCor = calculaMediana(pixel);
					pw.setArgb(x, y, novaCor.getRGB());
				}
			}
			// salva a imagem
			// descobre o tipo da imagem para poder salvar ela
			String tipoImg = retornaExtensao(caminho);
			// cria o caminho com o nome do arquivo
			String nomeDoArquivo = "imgs/filtros/novaimagemsemruidostresportres.";
			nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
			// salva a nova imagem na pasta filtros
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(
					"A imagem com filtro de elimina��o de ru�do tr�s por tr�s foi salva na pasta imgs/filtros");
		}
	}

	// retorna o pixel em quest�o com os seus vizinhos preenchidos
	public Pixel vizinhancaEmX(WritableImage imagem, Pixel pixel, int x, int y) {
		PixelReader pr = imagem.getPixelReader();
		ArrayList<Pixel> pixelsViz = new ArrayList<Pixel>();
		int vizinhoArgb = pr.getArgb((x - 1), (y - 1));
		int alphaViz = (vizinhoArgb >> 24) & 0xFF;
		int redViz = (vizinhoArgb >> 16) & 0xFF;
		int greenViz = (vizinhoArgb >> 8) & 0xFF;
		int blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho1 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho1);
		vizinhoArgb = pr.getArgb((x + 1), (y - 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho2 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho2);
		vizinhoArgb = pr.getArgb((x - 1), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho3 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho3);
		vizinhoArgb = pr.getArgb((x + 1), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho4 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho4);
		pixel.setVizinhos(pixelsViz);
		return pixel;
	}

	// retorna o pixel em quest�o com os seus vizinhos preenchidos
	public Pixel vizinhancaEmCruz(WritableImage imagem, Pixel pixel, int x, int y) {
		PixelReader pr = imagem.getPixelReader();
		ArrayList<Pixel> pixelsViz = new ArrayList<Pixel>();
		int vizinhoArgb = pr.getArgb((x - 1), (y));
		int alphaViz = (vizinhoArgb >> 24) & 0xFF;
		int redViz = (vizinhoArgb >> 16) & 0xFF;
		int greenViz = (vizinhoArgb >> 8) & 0xFF;
		int blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho1 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho1);
		vizinhoArgb = pr.getArgb((x + 1), (y));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho2 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho2);
		vizinhoArgb = pr.getArgb((x), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho3 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho3);
		vizinhoArgb = pr.getArgb((x), (y - 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho4 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho4);
		pixel.setVizinhos(pixelsViz);
		return pixel;
	}

	// retorna o pixel em quest�o com os seus vizinhos preenchidos
	public Pixel vizinhancaTresPorTres(WritableImage imagem, Pixel pixel, int x, int y) {
		PixelReader pr = imagem.getPixelReader();
		ArrayList<Pixel> pixelsViz = new ArrayList<Pixel>();
		int vizinhoArgb = pr.getArgb((x - 1), (y - 1));
		int alphaViz = (vizinhoArgb >> 24) & 0xFF;
		int redViz = (vizinhoArgb >> 16) & 0xFF;
		int greenViz = (vizinhoArgb >> 8) & 0xFF;
		int blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho1 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho1);
		vizinhoArgb = pr.getArgb((x + 1), (y - 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho2 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho2);
		vizinhoArgb = pr.getArgb((x - 1), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho3 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho3);
		vizinhoArgb = pr.getArgb((x + 1), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho4 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho4);
		vizinhoArgb = pr.getArgb((x - 1), (y));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho5 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho5);
		vizinhoArgb = pr.getArgb((x + 1), (y));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho6 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho6);
		vizinhoArgb = pr.getArgb((x), (y + 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho7 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho7);
		vizinhoArgb = pr.getArgb((x), (y - 1));
		alphaViz = (vizinhoArgb >> 24) & 0xFF;
		redViz = (vizinhoArgb >> 16) & 0xFF;
		greenViz = (vizinhoArgb >> 8) & 0xFF;
		blueViz = vizinhoArgb & 0xFF;
		Pixel vizinho8 = new Pixel(alphaViz, redViz, greenViz, blueViz);
		pixelsViz.add(vizinho8);
		pixel.setVizinhos(pixelsViz);
		return pixel;
	}

	// calcula mediana dos canais para eliminar os ru�dos
	public Color calculaMediana(Pixel pixel) {
		int novoRed = -1;
		int novoGreen = -1;
		int novoBlue = -1;
		ArrayList<Integer> canaisRGB = new ArrayList<Integer>();
		canaisRGB.add(pixel.getRed());
		for (int i = 0; i < pixel.getVizinhos().size(); i++) {
			canaisRGB.add(pixel.getVizinhos().get(i).getRed());
		}
		Collections.sort(canaisRGB);
		if (canaisRGB.size() == 5)
			novoRed = canaisRGB.get(2);
		if (canaisRGB.size() == 9)
			novoRed = canaisRGB.get(4);
		canaisRGB.clear(); // depois de coletado o novo red limpa o arraylist
		canaisRGB.add(pixel.getGreen());
		for (int i = 0; i < pixel.getVizinhos().size(); i++) {
			canaisRGB.add(pixel.getVizinhos().get(i).getGreen());
		}
		Collections.sort(canaisRGB);
		if (canaisRGB.size() == 5)
			novoGreen = canaisRGB.get(2);
		if (canaisRGB.size() == 9)
			novoGreen = canaisRGB.get(4);
		canaisRGB.clear(); // depois de coletado o novo green limpa o arraylist
		canaisRGB.add(pixel.getBlue());
		for (int i = 0; i < pixel.getVizinhos().size(); i++) {
			canaisRGB.add(pixel.getVizinhos().get(i).getBlue());
		}
		Collections.sort(canaisRGB);
		if (canaisRGB.size() == 5)
			novoBlue = canaisRGB.get(2);
		if (canaisRGB.size() == 9)
			novoBlue = canaisRGB.get(4);
		canaisRGB.clear(); // depois de coletado o novo blue limpa o arraylist
		Color novaCor = new Color(novoRed, novoGreen, novoBlue, pixel.getAlpha());
		return novaCor;
	}

	/*
	 * M�todo para aplicar adi��o e subtra��o de imagem, se adicaoOuSubtracao for
	 * igual a 1 aplica adi��o, se for igual a 2 aplica subtra��o. Foi feito sem
	 * pondera��o em cada imagem, mas se necess�rio a adi��o pode ter isso. Com a
	 * pondera��o uma imagem pode ter o peso maior que a outra e aparecer mais,
	 * depois da adi��o feita
	 */
	public void adicaoESubtracaoDeImagem(BufferedImage imagem1, BufferedImage imagem2, String caminho,
			int adicaoOuSubtracao) {
		Scanner scan = new Scanner(System.in);
		int largura = imagem1.getWidth();
		int altura = imagem1.getHeight();
		if (imagem1.getWidth() < imagem2.getWidth())
			largura = imagem1.getWidth();
		if (imagem2.getWidth() < imagem1.getWidth())
			largura = imagem2.getWidth();
		if (imagem1.getHeight() < imagem2.getHeight())
			altura = imagem1.getHeight();
		if (imagem2.getHeight() < imagem1.getHeight())
			altura = imagem2.getHeight();
		// cria uma BufferedImage com a menor largura e a menor altura entre as duas
		// imagens
		BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
		WritableImage imagemWI = new WritableImage(imagem.getWidth(), imagem.getHeight());
		PixelWriter pw = imagemWI.getPixelWriter();
		// analisa os pixels e nos la�os abaixo preenche a nova imagem com filtro de
		// adi��o
		if (adicaoOuSubtracao == 1) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				for (int x = 0; x < imagem.getWidth(); x++) {
					int imagemArgb1 = imagem1.getRGB(x, y);
					int alphaImg1 = (imagemArgb1 >> 24) & 0xFF;
					int redImg1 = (imagemArgb1 >> 16) & 0xFF;
					int greenImg1 = (imagemArgb1 >> 8) & 0xFF;
					int blueImg1 = imagemArgb1 & 0xFF;
					int imagemArgb2 = imagem2.getRGB(x, y);
					int alphaImg2 = (imagemArgb2 >> 24) & 0xFF;
					int redImg2 = (imagemArgb2 >> 16) & 0xFF;
					int greenImg2 = (imagemArgb2 >> 8) & 0xFF;
					int blueImg2 = imagemArgb2 & 0xFF;
					int novoAlpha = alphaImg1 + alphaImg2;
					if (novoAlpha > 255)
						novoAlpha = 255;
					int novoRed = redImg1 + redImg2;
					if (novoRed > 255)
						novoRed = 255;
					int novoGreen = greenImg1 + greenImg2;
					if (novoGreen > 255)
						novoGreen = 255;
					int novoBlue = blueImg1 + blueImg2;
					if (novoBlue > 255)
						novoBlue = 255;
					Color novaCor = new Color(novoRed, novoGreen, novoBlue, novoAlpha);
					pw.setArgb(x, y, novaCor.getRGB());
				}
			}
		}
		// analisa os pixels e nos la�os abaixo preenche a nova imagem com filtro de
		// subtra��o
		if (adicaoOuSubtracao == 2) {
			// primeiro escolhe se ser� a imagem 1 menos a 2, ou vice-versa
			int imagemAnt = -1;
			do {
				try {
					System.out.println(
							"Voc� quer fazer a subtra��o imagem1 - imagem2 (1), ou a subtra��o imagem2 - imagem1 (2)?");
					System.out.print("resposta: ");
					imagemAnt = scan.nextInt();
					scan.nextLine();
					if ((imagemAnt < 1) || (imagemAnt > 2))
						System.out.println("op��o inv�lida");
				} catch (InputMismatchException e) {
					System.out.println("op��o inv�lida");
					scan.nextLine();
					imagemAnt = -1;
				}
			} while ((imagemAnt < 1) || (imagemAnt > 2));
			if (imagemAnt == 1) {
				for (int y = 0; y < imagem.getHeight(); y++) {
					for (int x = 0; x < imagem.getWidth(); x++) {
						int imagemArgb1 = imagem1.getRGB(x, y);
						int alphaImg1 = (imagemArgb1 >> 24) & 0xFF;
						int redImg1 = (imagemArgb1 >> 16) & 0xFF;
						int greenImg1 = (imagemArgb1 >> 8) & 0xFF;
						int blueImg1 = imagemArgb1 & 0xFF;
						int imagemArgb2 = imagem2.getRGB(x, y);
						int alphaImg2 = (imagemArgb2 >> 24) & 0xFF;
						int redImg2 = (imagemArgb2 >> 16) & 0xFF;
						int greenImg2 = (imagemArgb2 >> 8) & 0xFF;
						int blueImg2 = imagemArgb2 & 0xFF;
						int novoAlpha = alphaImg1 - alphaImg2;
						if (novoAlpha < 0)
							novoAlpha = 0;
						int novoRed = redImg1 - redImg2;
						if (novoRed < 0)
							novoRed = 0;
						int novoGreen = greenImg1 - greenImg2;
						if (novoGreen < 0)
							novoGreen = 0;
						int novoBlue = blueImg1 - blueImg2;
						if (novoBlue < 0)
							novoBlue = 0;
						Color novaCor = new Color(novoRed, novoGreen, novoBlue, novoAlpha);
						pw.setArgb(x, y, novaCor.getRGB());
					}
				}
			}
			if (imagemAnt == 2) {
				for (int y = 0; y < imagem.getHeight(); y++) {
					for (int x = 0; x < imagem.getWidth(); x++) {
						int imagemArgb1 = imagem1.getRGB(x, y);
						int alphaImg1 = (imagemArgb1 >> 24) & 0xFF;
						int redImg1 = (imagemArgb1 >> 16) & 0xFF;
						int greenImg1 = (imagemArgb1 >> 8) & 0xFF;
						int blueImg1 = imagemArgb1 & 0xFF;
						int imagemArgb2 = imagem2.getRGB(x, y);
						int alphaImg2 = (imagemArgb2 >> 24) & 0xFF;
						int redImg2 = (imagemArgb2 >> 16) & 0xFF;
						int greenImg2 = (imagemArgb2 >> 8) & 0xFF;
						int blueImg2 = imagemArgb2 & 0xFF;
						int novoAlpha = alphaImg2 - alphaImg1;
						if (novoAlpha < 0)
							novoAlpha = 0;
						int novoRed = redImg2 - redImg1;
						if (novoRed < 0)
							novoRed = 0;
						int novoGreen = greenImg2 - greenImg1;
						if (novoGreen < 0)
							novoGreen = 0;
						int novoBlue = blueImg2 - blueImg1;
						if (novoBlue < 0)
							novoBlue = 0;
						Color novaCor = new Color(novoRed, novoGreen, novoBlue, novoAlpha);
						pw.setArgb(x, y, novaCor.getRGB());
					}
				}
			}
		}
		// descobre o tipo da imagem para poder salvar ela, pega a extens�o da imagem1
		String tipoImg = retornaExtensao(caminho);
		// cria o caminho com o nome do arquivo
		String nomeDoArquivo = "";
		// em caso de adi��o
		if (adicaoOuSubtracao == 1)
			nomeDoArquivo = "imgs/filtros/novaimagemcomadicao.";
		// em caso de subtra��o
		if (adicaoOuSubtracao == 2)
			nomeDoArquivo = "imgs/filtros/novaimagemcomsubtracao.";
		nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
		// salva a nova imagem na pasta filtros
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem gerada de duas outras imagens foi salva na pasta imgs/filtros");
	}

	// faz marca��o em �rea passada pelo usu�rio
	public void fazMarcacao(BufferedImage imagem, String caminho) {
		System.out.println("Marca��o de imagem");
		Scanner scan = new Scanner(System.in);
		WritableImage imagemWI = getWI(imagem);
		PixelWriter pw = imagemWI.getPixelWriter();
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
		Color novaCor = new Color(0, 0, 0, 0);
		for (int y = alturaA; y <= alturaB; y++) {
			for (int x = larguraA; x <= larguraB; x++) {
				if (x == larguraA)
					pw.setArgb(x, y, novaCor.getRGB());
				if ((y == alturaA) && (x != larguraA) && (x != larguraB))
					pw.setArgb(x, y, novaCor.getRGB());
				if ((y == alturaB) && (x != larguraA) && (x != larguraB))
					pw.setArgb(x, y, novaCor.getRGB());
				if (x == larguraB)
					pw.setArgb(x, y, novaCor.getRGB());
			}
		}
		// descobre o tipo da imagem para poder salvar ela
		String tipoImg = retornaExtensao(caminho);
		// cria o caminho com o nome do arquivo
		String nomeDoArquivo = "imgs/filtros/novaimagemcommarcacao.";
		nomeDoArquivo = nomeDoArquivo.concat(tipoImg);
		// salva a nova imagem na pasta filtros
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoImg, new File(nomeDoArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A imagem com marca��o foi salva na pasta imgs/filtros");
	}

	// passa a imagem em buffer para uma WritableImage
	public WritableImage getWI(BufferedImage imagemBI) {
		WritableImage imagemWI = null;
		imagemWI = new WritableImage(imagemBI.getWidth(), imagemBI.getHeight());
		PixelWriter pw = imagemWI.getPixelWriter();
		for (int x = 0; x < imagemBI.getWidth(); x++) {
			for (int y = 0; y < imagemBI.getHeight(); y++) {
				pw.setArgb(x, y, imagemBI.getRGB(x, y));
			}
		}
		return imagemWI;
	}

	// retorna a extens�o do arquivo, exemplo .jpg
	public String retornaExtensao(String caminho) {
		int localPonto = -1; // pega a posi��o do ponto na palavra
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) == ('.')) {
				localPonto = i + 1;
				break;
			}
		}
		String extensao = caminho.substring(localPonto, caminho.length());
		return extensao;
	}

	public void mostraPixels(BufferedImage imagem) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Mostra os pixels que est�o dentro da marca��o passada");
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
		for (int x = larguraA; x <= larguraB; x++) {
			for (int y = alturaA; y <= alturaB; y++) {
				int imagemArgb = imagem.getRGB(x, y);
				int alpha = (imagemArgb >> 24) & 0xFF;
				int red = (imagemArgb >> 16) & 0xFF;
				int green = (imagemArgb >> 8) & 0xFF;
				int blue = imagemArgb & 0xFF;
				System.out.println("x" + x + "y" + y + ": " + (red) + " - " + (green) + " - " + (blue));
			}
		}

	}

}
