/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author twane
 */
public class Prototypes {

	Points points;
	AireDeDessin aire;
	Point pointX;
	ArrayList<Point> protosA = new ArrayList<Point>();
	ArrayList<Point> protosB = new ArrayList<Point>();

	public Prototypes(Points points, AireDeDessin aire) {
		this.points = points;
		this.aire = aire;
		pointX = points.pointX;
	}

	public void afficherSolution(int nb_proto) {
		Point proto;
		creationPrototypes(nb_proto);
		proto = meilleurPrototype();
		
		aire.dessinerPrototypes(protosA, protosB);
		aire.repaint();
	}

	public Point meilleurPrototype() {
		Point meilleurProto = prototypePlusProche(protosA, protosB, pointX);
		return meilleurProto;
	}

	public void creationPrototypes(int nb_proto) {

		ArrayList<Point> pointsA = points.pointsA;
		ArrayList<Point> pointsB = points.pointsB;
		Random alea = new Random();
		int tailleA = points.pointsA.size();
		int tailleB = points.pointsB.size();


		//Definition de nb_protos initiaux
		for (int i = 0; i < nb_proto; i++) {
			protosA.add(pointsA.get(alea.nextInt(tailleA)));
			protosB.add(pointsB.get(alea.nextInt(tailleB)));
		}

		System.out.println(protosA);
		System.out.println(protosB);
		//Ajustement des prototypes
		int choix = 0;
		int classePointChoisi;
		Point pointChoisi;
		Point protoProche;
		double sigma = 0.25;
		for (int i = 0; i < nb_proto * 100; i++) {
			choix = alea.nextInt(tailleA + tailleB);
			if (choix < tailleA) {
				pointChoisi = pointsA.get(choix);
				classePointChoisi = 1;
			} else {
				pointChoisi = pointsB.get(choix - tailleA);
				classePointChoisi = 2;
			}

			protoProche = prototypePlusProche(protosA, protosB, pointX);
			int indexDansA = protosA.indexOf(protoProche);
			int indexDansB = protosB.indexOf(protoProche);
			int classeProtoProche;

			if (indexDansA >= 0) {
				classeProtoProche = 1;
			} else {
				classeProtoProche = 2;
			}
			// si le proto est de la meme classe que le point tiré aléatoirement
			//On le rapproche
			Point newPosProto;
			if (classePointChoisi == classeProtoProche) {
				newPosProto = new Point((int) (protoProche.x + (pointChoisi.x - protoProche.x) * sigma), (int) (protoProche.y + (pointChoisi.y - protoProche.y) * sigma));
			} else {
				newPosProto = new Point((int) (protoProche.x - (pointChoisi.x - protoProche.x) * sigma), (int) (protoProche.y - (pointChoisi.y - protoProche.y) * sigma));
			}
			if (classeProtoProche == 1) {
				protosA.set(indexDansA, newPosProto);
			} else {
				protosB.set(indexDansB, newPosProto);
			}
			sigma *= 0.99;
		}

	}

	public Point prototypePlusProche(ArrayList<Point> protosA, ArrayList<Point> protosB, Point p) {
		double distanceMini = Double.MAX_VALUE;
		Point protoProche = new Point();

		for (Point protoC : protosA) {
			if (distance(protoC, p) < distanceMini) {
				protoProche = protoC;
			}
		}
		for (Point protoC : protosB) {
			if (distance(protoC, p) < distanceMini) {
				protoProche = protoC;
			}
		}
		return protoProche;
	}

	public double distance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
}
