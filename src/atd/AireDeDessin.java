/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

/**
 *
 * @author twane
 */
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

class AireDeDessin extends JComponent {

	private static final long serialVersionUID = 1L;
	Point actuel = new Point(-1, -1);
	Point precedent = new Point(-1, -1);
	BufferedImage tmp;
	Graphics2D dessinable;
	Point taille;// = new Point(taille_fenetre.width, taille_fenetre.height-100);
	JFrame frame;
	BufferedImage image;
	Points points;
	int mode = 0;

	public AireDeDessin(JFrame fr, Dimension taille_fenetre, Points points) {
		taille = new Point(taille_fenetre.width, taille_fenetre.height - 200);
		image = new BufferedImage(taille.x, taille.y, BufferedImage.TYPE_INT_RGB);
		frame = fr;
		this.points = points;
		this.nouvelleImage();
	}

	public void nouvelleImage() {
		if (getSize().width != 0 || getSize().height != 0) {
			taille.x = getSize().width;
			taille.y = getSize().height;
		}
		//images.NouvelleImage();
		//images.InsererEtape(new BufferedImage(taille.x, taille.y, BufferedImage.TYPE_INT_RGB));

		//dessinable = images.etapes.peek().createGraphics();
		dessinable = image.createGraphics();
		dessinable.setPaint(Color.white);
		dessinable.fillRect(0, 0, taille.x, taille.y);
		this.repaint();
	}

	//Efface un carré de 10 par 10 aux coordonnées x et y
	public void effacer_tout() {
		dessinable.setPaint(Color.white);
		dessinable.fillRect(0, 0, taille.x, taille.y);
	}

	//Dessine une ligne depuis les précédentes coordonnées jusqu'a x et y
	public void dessiner_point(int x, int y, int classe) {
		if (classe == 1) {
			dessinable.setPaint(Color.red);
		}
		if (classe == 2) {
			dessinable.setPaint(Color.yellow);
		}
		if (classe == 3) {
			dessiner_tout();
		}
		if (classe == 1 || classe == 2) {
			dessinable.fillOval(x - 10, y - 10, 20, 20);
			dessinable.setPaint(Color.black);
			dessinable.drawOval(x - 10, y - 10, 20, 20);
		}
	}

	public void dessiner_point(Point p, int classe) {
		if (classe == 1) {
			dessinable.setPaint(Color.red);
		}
		if (classe == 2) {
			dessinable.setPaint(Color.yellow);
		}
		if (classe == 3) {
			dessiner_tout();
		}

		if (classe == 1 || classe == 2) {
			dessinable.fillOval(p.x - 10, p.y - 10, 20, 20);
			dessinable.setPaint(Color.black);
			dessinable.drawOval(p.x - 10, p.y - 10, 20, 20);
		}
	}

	public void dessiner_tout() {
		int a = 0, b = 0;
		Stack<Integer> ordreInverse = points.inverserOrdre();
		int classeTemp;

		effacer_tout();

		while (!ordreInverse.empty()) {
			classeTemp = ordreInverse.pop();
			if (classeTemp == 1) {
				dessiner_point(points.pointsA.get(a), 1);
				a++;
			}
			if (classeTemp == 2) {
				dessiner_point(points.pointsB.get(b), 2);
				b++;
			}
			if (classeTemp == 3) {
				dessinable.setPaint(Color.blue);
				dessinable.fillOval(points.pointX.x - 10, points.pointX.y - 10, 20, 20);
				dessinable.setPaint(Color.black);
				dessinable.drawOval(points.pointX.x - 10, points.pointX.y - 10, 20, 20);
			}
		}
	}

	public void dessinerKppv(ArrayList<Point> ppvMaj, ArrayList<Point> ppvMin) {
		dessiner_tout();
		Double teta1, teta2;
		Point o = points.pointX;
		Double aOuv = 0.5;
		int r = 10;
		for (Point p : ppvMaj) {
			teta1 = teta(p, o);
			teta2 = teta1+Math.PI;
			
			int[] arrayX = {p.x, p.x + (int) (r * Math.cos(teta1 - aOuv)), o.x + (int) (r * Math.cos(teta2 + aOuv)), o.x, o.x + (int) (r * Math.cos(teta2 - aOuv)), p.x + (int) (r * Math.cos(teta1 + aOuv))};
			int[] arrayY = {p.y, p.y + (int) (r * Math.sin(teta1 - aOuv)), o.y + (int) (r * Math.sin(teta2 + aOuv)), o.y, o.y + (int) (r * Math.sin(teta2 - aOuv)), p.y + (int) (r * Math.sin(teta1 + aOuv))};

			dessinable.setPaint(Color.orange);
			dessinable.fillPolygon(arrayX, arrayY, 6);
		}
		aOuv = 0.3;
		for (Point p : ppvMin) {
			teta1 = teta(p, o);
			teta2 = teta1+Math.PI;

			int[] arrayX = {p.x, p.x + (int) (r * Math.cos(teta1 - aOuv)), o.x + (int) (r * Math.cos(teta2 + aOuv)), o.x, o.x + (int) (r * Math.cos(teta2 - aOuv)), p.x + (int) (r * Math.cos(teta1 + aOuv))};
			int[] arrayY = {p.y, p.y + (int) (r * Math.sin(teta1 - aOuv)), o.y + (int) (r * Math.sin(teta2 + aOuv)), o.y, o.y + (int) (r * Math.sin(teta2 - aOuv)), p.y + (int) (r * Math.sin(teta1 + aOuv))};

			dessinable.setPaint(Color.pink);
			dessinable.fillPolygon(arrayX, arrayY, 6);
		}
	}

	public void dessinerRectEntreDeuxPoints(Point a, Point b, int eppaisseur) {
		dessinable.fillRect(mode, mode, mode, mode);
	}

	public double distance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	public double teta(Point a, Point b) {
		double x = b.x - a.x;
		double y = b.y - a.y;
		double teta = 0;
		/*if (x > 0) {
			if (y >= 0) {
				teta = Math.atan(y / x);
			} else { //y<0
				teta = Math.atan(y / x) + Math.PI * 2;
			}
		} else if (x == 0) {
			if (y > 0) {
				teta = Math.PI / 2;
			}
			if (y < 0) {
				teta = Math.PI * 3 / 2;
			}
		} else { //x < 0
			teta = Math.atan(y / x) + Math.PI;
		}*/
		teta = 2 * Math.atan(y/(x+distance(a,b)));
		return teta;
	}

	public void paintComponent(Graphics g) {
		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		Graphics2D drawable = (Graphics2D) g;
		//Dessine l'image sur le 'dessin'
		//frame.setSize(image.getWidth(), image.getHeight());
		drawable.drawImage(image, null, 0, 0);
	}
}
