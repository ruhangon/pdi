import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import imagem.Imagem;

public class Main {

	public static int escolheImagem(Scanner scan) {
		int opImg = -1;
		do {
			try {
				System.out.println("Escolha uma imagem (1 ou 2)");
				System.out.print("imagem: ");
				opImg = scan.nextInt();
				scan.nextLine();
				if ((opImg < 1) || (opImg > 2))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				opImg = -1;
			}
		} while ((opImg < 1) || (opImg > 2));
		return opImg;
	}

	public static void main(String[] args) {
		System.out.println("    Processamento digital de imagens    \n");
		Scanner scan = new Scanner(System.in);
		String caminhoImg1 = " ";
		String caminhoImg2 = " ";
		Imagem imagem = new Imagem();
		BufferedImage minhaImagem1 = null;
		BufferedImage minhaImagem2 = null;
		Color infosImg1;
		Color infosImg2;
		int opImg = -1;
		int adicaoOuSubtracao = -1; // usado na adição ou subtração de imagens
		String menu = "Menu \n" + "1. Escolhe imagem 1 \n" + "2. Escolhe imagem 2 \n"
				+ "3. Descobre RGB em uma coordenada de uma imagem \n" + "4. Filtros de cinza \n"
				+ "5. Filtro de limiarização \n" + "6. Filtro de negativa \n" + "7. Filtro de eliminação de ruídos \n"
				+ "8. Adição de imagem \n" + "9. Subtração de imagem \n" + "10. Faz marcação em imagem \n"
				+ "20. Mostra pixels de uma área passada \n" + "0. Sai do programa";

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
					caminhoImg1 = imagem.escolheImagem();
					try {
						minhaImagem1 = ImageIO.read(new File(caminhoImg1));
					} catch (IOException e) {
						System.out.println("Erro com BufferedImage");
					}
					break;

				case 2:
					System.out.println("Imagem 2");
					caminhoImg2 = imagem.escolheImagem();
					try {
						minhaImagem2 = ImageIO.read(new File(caminhoImg2));
					} catch (IOException e) {
						System.out.println("Erro com BufferedImage");
					}
					break;

				case 3:
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
					// primeiro escolhe a imagem para aplicar o filtro
					opImg = -1;
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
					// escolhe qual filtro de cinza aplicar
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
					// de acordo com a imagem aplica o filtro pedido
					if (opImg == 1) {
						if (opFiltros == 1) {
							imagem.filtroCinzaAritmetico(minhaImagem1);
						}
						if (opFiltros == 2) {
							int perc1;
							int perc2;
							int perc3;
							int soma = 0; // soma dos percentuais pedidos abaixo precisa dar 100
							// abaixo pede os 3 percentuais
							do {
								perc1 = -1;
								perc2 = -1;
								perc3 = -1;
								// primeiro percentual
								do {
									try {
										System.out.println("Digite o primeiro percentual");
										System.out.print("percentual: ");
										perc1 = scan.nextInt();
										scan.nextLine();
										if ((perc1 < 0) || (perc1 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc1 = -1;
									}
								} while ((perc1 < 0) || (perc1 > 100));
								soma += perc1;
								// segundo percentual
								do {
									try {
										System.out.println("Digite o segundo percentual");
										System.out.print("percentual: ");
										perc2 = scan.nextInt();
										scan.nextLine();
										if ((perc2 < 0) || (perc2 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc2 = -1;
									}
								} while ((perc2 < 0) || (perc2 > 100));
								soma += perc2;
								// terceiro percentual
								do {
									try {
										System.out.println("Digite o terceiro percentual");
										System.out.print("percentual: ");
										perc3 = scan.nextInt();
										scan.nextLine();
										if ((perc3 < 0) || (perc3 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc3 = -1;
									}
								} while ((perc3 < 0) || (perc3 > 100));
								soma += perc3;
								if (soma != 100)
									System.out.println("a soma não deu 100, faça novamente");
							} while (soma != 100);
							imagem.filtroCinzaPonderado(minhaImagem1, perc1, perc2, perc3);
						}
					}
					if (opImg == 2) {
						if (opFiltros == 1) {
							imagem.filtroCinzaAritmetico(minhaImagem2);
						}
						if (opFiltros == 2) {
							int perc1;
							int perc2;
							int perc3;
							int soma = 0; // soma dos percentuais pedidos abaixo precisa dar 100
							// abaixo pede os 3 percentuais
							do {
								perc1 = -1;
								perc2 = -1;
								perc3 = -1;
								// primeiro percentual
								do {
									try {
										System.out.println("Digite o primeiro percentual");
										System.out.print("percentual: ");
										perc1 = scan.nextInt();
										scan.nextLine();
										if ((perc1 < 0) || (perc1 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc1 = -1;
									}
								} while ((perc1 < 0) || (perc1 > 100));
								soma += perc1;
								// segundo percentual
								do {
									try {
										System.out.println("Digite o segundo percentual");
										System.out.print("percentual: ");
										perc2 = scan.nextInt();
										scan.nextLine();
										if ((perc2 < 0) || (perc2 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc2 = -1;
									}
								} while ((perc2 < 0) || (perc2 > 100));
								soma += perc2;
								// terceiro percentual
								do {
									try {
										System.out.println("Digite o terceiro percentual");
										System.out.print("percentual: ");
										perc3 = scan.nextInt();
										scan.nextLine();
										if ((perc3 < 0) || (perc3 > 100))
											System.out.println("opção inválida");
									} catch (InputMismatchException e) {
										System.out.println("opção inválida");
										scan.nextLine();
										perc3 = -1;
									}
								} while ((perc3 < 0) || (perc3 > 100));
								soma += perc3;
								if (soma != 100)
									System.out.println("a soma não deu 100, faça novamente");
							} while (soma != 100);
							imagem.filtroCinzaPonderado(minhaImagem2, perc1, perc2, perc3);
						}
					}
					break;

				case 5:
					int limiar = -1;
					do {
						try {
							System.out.println("Digite o limiar");
							System.out.print("limiar: ");
							limiar = scan.nextInt();
							if ((limiar < 0) || (limiar > 255))
								System.out.println("opção inválida");
							scan.nextLine();
						} catch (InputMismatchException e) {
							System.out.println("opção inválida");
							scan.nextLine();
							limiar = -1;
						}
					} while ((limiar < 0) || (limiar > 255));
					opImg = -1;
					opImg = escolheImagem(scan);
					if (opImg == 1)
						imagem.filtroParaLimiarizacao(minhaImagem1, limiar, caminhoImg1);
					if (opImg == 2)
						imagem.filtroParaLimiarizacao(minhaImagem2, limiar, caminhoImg2);
					break;

				case 6:
					opImg = -1;
					opImg = escolheImagem(scan);
					if (opImg == 1)
						imagem.filtroDeNegativa(minhaImagem1, caminhoImg1);
					if (opImg == 2)
						imagem.filtroDeNegativa(minhaImagem2, caminhoImg2);
					break;

				case 7:
					opImg = -1;
					opImg = escolheImagem(scan);
					if (opImg == 1)
						imagem.filtroDeEliminacaoDeRuidos(minhaImagem1, caminhoImg1);
					if (opImg == 2)
						imagem.filtroDeEliminacaoDeRuidos(minhaImagem2, caminhoImg2);
					break;

				case 8:
					System.out.println("Imagem 1 + Imagem 2");
					adicaoOuSubtracao = 1;
					imagem.adicaoESubtracaoDeImagem(minhaImagem1, minhaImagem2, caminhoImg1, adicaoOuSubtracao);
					break;

				case 9:
					System.out.println("Imagem 1 - Imagem 2 ou Imagem 2 - Imagem 1");
					adicaoOuSubtracao = 2;
					imagem.adicaoESubtracaoDeImagem(minhaImagem1, minhaImagem2, caminhoImg1, adicaoOuSubtracao);
					break;

				case 10:
					opImg = -1;
					opImg = escolheImagem(scan);
					if (opImg == 1)
						imagem.fazMarcacao(minhaImagem1, caminhoImg1);
					if (opImg == 2)
						imagem.fazMarcacao(minhaImagem2, caminhoImg2);
					break;

				case 20:
					opImg = -1;
					opImg = escolheImagem(scan);
					if (opImg == 1)
						imagem.mostraPixels(minhaImagem1);
					if (opImg == 2)
						imagem.mostraPixels(minhaImagem2);
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
