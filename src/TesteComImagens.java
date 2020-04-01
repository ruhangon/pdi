import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class TesteComImagens {
	public static void main(String[] args) {
		System.out.println("    Teste com imagens    \n");
		Scanner scan = new Scanner(System.in);
		String caminhoImg = " ";
		BufferedImage minhaImagem = null;
		PixelWriter pw;
		boolean existeImg = false;
		String menu = "1. Cria imagem simples na proporção escolhida \n2. Altera 4 pixels específicos da imagem no buffer \n3. Confere 1 pixel da imagem \n"
				+ "4. Confere todos os pixels da imagem \n5. Confere largura x altura da imagem \n6. Carrega imagem para buffer \n0. Sai";
		int largura = -1;
		int altura = -1;

		int op = -1;

		do {
			try {
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
				op = scan.nextInt();
				scan.nextLine();
				switch (op) {
				case 1:
					System.out.println("Cria nova imagem simples");
					largura = -1;
					altura = -1;
					// pede as proporções da imagem
					do {
						try {
							System.out.println("Escolha a largura da imagem");
							System.out.print("largura: ");
							largura = scan.nextInt();
							scan.nextLine();
							if (largura < 0)
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							largura = -1;
						}
					} while (largura < 0);
					do {
						try {
							System.out.println("Escolha a altura da imagem");
							System.out.print("altura: ");
							altura = scan.nextInt();
							scan.nextLine();
							if (altura < 0)
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							altura = -1;
						}
					} while (altura < 0);
					int red = -1;
					int green = -1;
					int blue = -1;
					// escolhe o red
					do {
						try {
							System.out.println("Escolha o valor do red para essa cor (0 a 255)");
							System.out.print("valor: ");
							red = scan.nextInt();
							scan.nextLine();
							if ((red < 0) || (red > 255))
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							red = -1;
						}
					} while ((red < 0) || (red > 255));
					// escolhe o green
					do {
						try {
							System.out.println("Escolha o valor do green para essa cor (0 a 255)");
							System.out.print("valor: ");
							green = scan.nextInt();
							scan.nextLine();
							if ((green < 0) || (green > 255))
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							green = -1;
						}
					} while ((green < 0) || (green > 255));
					// escolhe o blue
					do {
						try {
							System.out.println("Escolha o valor do blue para essa cor (0 a 255)");
							System.out.print("valor: ");
							blue = scan.nextInt();
							scan.nextLine();
							if ((blue < 0) || (blue > 255))
								System.out.println("opção inválida");
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							blue = -1;
						}
					} while ((blue < 0) || (blue > 255));
					Color corPrincipal = new Color(red, green, blue);
					// cria a imagem com writableimage
					WritableImage novaImagem = new WritableImage(largura, altura);
					pw = novaImagem.getPixelWriter();
					// pinta a imagem com a cor principal
					for (int x = 0; x < largura; x++) {
						for (int y = 0; y < altura; y++) {
							pw.setArgb(x, y, corPrincipal.getRGB());
						}
					}
					// salva a imagem na pasta novas
					String larguraStr = Integer.toString(largura);
					String alturaStr = Integer.toString(altura);
					String nome = "imgs/novas/";
					nome = nome.concat(larguraStr);
					nome = nome.concat("x");
					nome = nome.concat(alturaStr);
					nome = nome.concat(".jpg");
					String tipoImg = "jpg";
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(novaImagem, null), tipoImg, new File(nome));
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("A imagem criada foi salva na pasta imgs/novas");
					System.out.println();
					break;

				case 2:
					System.out.println("Altera 4 pixels específicos da imagem no buffer (irá virar preto)");
					int n = 0;
					WritableImage imagemWI = new WritableImage(minhaImagem.getWidth(), minhaImagem.getHeight());
					pw = imagemWI.getPixelWriter();
					for (int x = 0; x < minhaImagem.getWidth(); x++) {
						for (int y = 0; y < minhaImagem.getHeight(); y++) {
							pw.setArgb(x, y, minhaImagem.getRGB(x, y));
						}
					}
					System.out.println("Largura: 0 até " + (minhaImagem.getWidth() - 1));
					System.out.println("Altura: 0 até " + (minhaImagem.getHeight() - 1));
					int opLarg = -1;
					int opAlt = -1;
					do {
						System.out.println("Pixel " + (n + 1));
						do {
							try {
								System.out.println("Digite a posição da largura do pixel que será alterado");
								System.out.print("posição: ");
								opLarg = scan.nextInt();
								scan.nextLine();
								if ((opLarg < 0) || (opLarg > (minhaImagem.getWidth() - 1)))
									System.out.println("opção inválida");
							} catch (InputMismatchException e) {
								System.out.println("opção inválida");
								scan.nextLine();
								opLarg = -1;
							}
						} while ((opLarg < 0) || (opLarg > (minhaImagem.getWidth() - 1)));
						do {
							try {
								System.out.println("Digite a posição da altura do pixel que será alterado");
								System.out.print("posição: ");
								opAlt = scan.nextInt();
								scan.nextLine();
								if ((opAlt < 0) || (opAlt > (minhaImagem.getHeight() - 1)))
									System.out.println("opção inválida");
							} catch (InputMismatchException e) {
								System.out.println("opção inválida");
								scan.nextLine();
								opAlt = -1;
							}
						} while ((opAlt < 0) || (opAlt > (minhaImagem.getHeight() - 1)));
						Color novoPixel = new Color(0, 0, 0);
						pw.setArgb(opLarg, opAlt, novoPixel.getRGB());
						n++;
					} while (n < 4);
					// salva a imagem na pasta novas
					String nomeNovaImg = "imgs/novas/imagemnovacomruido.jpg";
					String tipoNovaImg = "jpg";
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(imagemWI, null), tipoNovaImg, new File(nomeNovaImg));
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("A imagem alterada foi salva na pasta imgs/novas");
					System.out.println();
					break;

				case 3:
					System.out.println("Confere um pixel da imagem");

					System.out.println();
					break;

				case 4:
					System.out.println("Confere todos os pixels da imagem");
					System.out.println("largura x altura: red - green - blue");
					for (int x = 0; x < minhaImagem.getWidth(); x++) {
						for (int y = 0; y < minhaImagem.getHeight(); y++) {
							int imagemRGB = minhaImagem.getRGB(x, y);
							int r = (imagemRGB >> 16) & 0xFF;
							int g = (imagemRGB >> 8) & 0xFF;
							int b = imagemRGB & 0xFF;
System.out.print(x +"x"+ y +": ");
System.out.println(r +" - "+ g +" - "+ b);
						}
					}
					System.out.println();
					break;

				case 5:
					System.out.println("largura x altura");
					System.out.println(minhaImagem.getWidth() + "x" + minhaImagem.getHeight());
					System.out.println();
					break;

				case 6:
					System.out.println("Carrega imagem");
					existeImg = false;
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
					try {
						minhaImagem = ImageIO.read(new File(caminhoImg));
					} catch (IOException e) {
						System.out.println("Erro com BufferedImage");
					}
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

		System.out.println("\n\nFim do programa");

	}
}
