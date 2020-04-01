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
			System.out.println("digite também a extensão (jpg, png, dentre outras)");
			System.out.print("caminho: ");
			caminhoImg = scan.nextLine();
			if (new File(caminhoImg).exists())
				existeImg = true;
			if (existeImg == false)
				System.out.println("O caminho passado para a imagem não existe");
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
				// imagemRGB.getRGB() retorna um inteiro de 32 bits com as informações do argb
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
		System.out.println("A imagem com filtro cinza aritmético foi salva na pasta imgs/filtros");
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
		int opRgb = 0; // usado para descobrir em qual canal será feito a binarização
		do {
			try {
				System.out.println("Escolha em qual canal será feita a linearização");
				System.out.println("1. R, 2. G, 3. B");
				System.out.print("canal: ");
				opRgb = scan.nextInt();
				scan.nextLine();
				if ((opRgb < 1) || (opRgb > 3))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
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
		System.out.println("A imagem com filtro de limiarização foi salva na pasta imgs/filtros");
	}

	// método para aplicar filtro de negativa
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

	// método para aplicar filtro de eliminação de ruídos por mediana
	public void filtroDeEliminacaoDeRuidos(BufferedImage imagem, String caminho) {
		Scanner scan = new Scanner(System.in);
		WritableImage imagemWI = getWI(imagem);
		PixelReader pr = imagemWI.getPixelReader();
		PixelWriter pw = imagemWI.getPixelWriter();
		// escolhe qual vizinhança será usada (em x, em cruz ou 3 por 3 )
		int opViz = -1;
		do {
			try {
				System.out
						.println("Escolha qual vizinhança será aplicada para realizar o filtro de eliminação de ruído");
				System.out.println("1. Vizinhança em x, 2. Vizinhança em cruz, 3. Vizinhança 3 por 3");
				System.out.print("resposta: ");
				opViz = scan.nextInt();
				scan.nextLine();
				if ((opViz < 1) || (opViz > 3))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				opViz = -1;
			}
		} while ((opViz < 1) || (opViz > 3));
		// se escolheu a opção 1, então aplica o filtro com vizinhança em x
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
			System.out.println("A imagem com filtro de eliminação de ruído por X foi salva na pasta imgs/filtros");
		}
		// se escolheu a opção 2, então aplica o filtro com vizinhança em cruz
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
			System.out.println("A imagem com filtro de eliminação de ruído por cruz foi salva na pasta imgs/filtros");
		}
		// se escolheu a opção 3, então aplica o filtro com vizinhança 3 por 3
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
			System.out.println("A imagem com filtro de eliminação de ruído três por três foi salva na pasta imgs/filtros");
		}
	}

	// retorna o pixel em questão com os seus vizinhos preenchidos
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

	// retorna o pixel em questão com os seus vizinhos preenchidos
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

	// retorna o pixel em questão com os seus vizinhos preenchidos
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

	// calcula mediana dos canais para eliminar os ruídos
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

	// retorna a extensão do arquivo, exemplo .jpg
	public String retornaExtensao(String caminho) {
		int localPonto = -1; // pega a posição do ponto na palavra
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) == ('.')) {
				localPonto = i + 1;
				break;
			}
		}
		String extensao = caminho.substring(localPonto, caminho.length());
		return extensao;
	}

}
