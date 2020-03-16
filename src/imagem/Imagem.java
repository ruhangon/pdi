package imagem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
		PixelWriter pw = imagemWI.getPixelWriter();
		for (int x = 0; x < imagem.getWidth(); x++) {
			for (int y = 0; y < imagem.getHeight(); y++) {
				Color imagemRGB = new Color(imagem.getRGB(x, y));
				int novaCor = ((imagemRGB.getRed() + imagemRGB.getGreen() + imagemRGB.getBlue()) / 3);

			}
		}
		salvaImagem(imagemWI);
	}

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

	public void salvaImagem(WritableImage imagemWI) {
		File file = new File("teste.png");
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(imagemWI, null);
		ImageIO.write(renderedImage, "png", file);
	}

}
