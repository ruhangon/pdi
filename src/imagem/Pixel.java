package imagem;

import java.util.ArrayList;

public class Pixel {
	private int alpha;
	private int red;
	private int green;
	private int blue;
	private ArrayList<Pixel> vizinhos = new ArrayList<Pixel>();

	public Pixel() {
	}

	public Pixel(int alpha, int red, int green, int blue) {
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public ArrayList<Pixel> getVizinhos() {
		return vizinhos;
	}

	public void setVizinhos(ArrayList<Pixel> vizinhos) {
		this.vizinhos = vizinhos;
	}

}
