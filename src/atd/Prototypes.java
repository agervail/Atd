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
	ArrayList<Point> protosA;
	ArrayList<Point> protosB;
	
	public Prototypes(Points points, AireDeDessin aire) {
		this.points = points;
		this.aire = aire;
	}

	public void afficherSolution(int nb_proto) {
		Point proto; 
		protosA = new ArrayList<Point>();
		protosB = new ArrayList<Point>();
		creationPrototypes(nb_proto);
		proto = meilleurPrototype();
		
		aire.dessinerPrototypes(protosA, protosB);
		if(protosA.contains(proto)){
		aire.dessinerMeilleurPrototype(proto,1);
		}else{
		aire.dessinerMeilleurPrototype(proto,2);
		}
		aire.repaint();
	}

	public Point meilleurPrototype() {
		Point meilleurProto = prototypePlusProche2(protosA, protosB, points.pointX);
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

		//Ajustement des prototypes
		int choix = 0;
		int classePointChoisi;
		Point pointChoisi;
		Point protoProche;
		double sigma = 0.25;
		int nbIterations = 100;
		for (int i = 0; i < nb_proto * nbIterations; i++) {
			choix = alea.nextInt(tailleA + tailleB);
			if (choix < tailleA) {
				pointChoisi = pointsA.get(choix);
				classePointChoisi = 1;
			} else {
				pointChoisi = pointsB.get(choix - tailleA);
				classePointChoisi = 2;
			}

			protoProche = prototypePlusProche(protosA, protosB, pointChoisi);
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
			sigma *= 0.995;
		}

	}
	public Point prototypePlusProche(ArrayList<Point> protosA, ArrayList<Point> protosB, Point p) {
		double distanceMini = Double.MAX_VALUE;
		Point protoProche = new Point();

		for (Point protoC1 : protosA) {
			if (distance(protoC1, p) < distanceMini) {
				protoProche = protoC1;
				distanceMini = distance(protoC1, p);
			}
		}
		for (Point protoC2 : protosB) {
			if (distance(protoC2, p) < distanceMini) {
				protoProche = protoC2;
				distanceMini = distance(protoC2, p);
			}
		}

		return protoProche;
	}
	public Point prototypePlusProche2(ArrayList<Point> protosA, ArrayList<Point> protosB, Point p) {
		double distanceMini = Double.MAX_VALUE;
		Point protoProche = new Point();
		
		System.out.println("A :"+protosA);
		System.out.println("B :"+protosB);
		System.out.println("X :"+p);
		System.out.println("Q :"+points.pointX);
		
		for (Point protoC1 : protosA) {
			if (distance(protoC1, p) < distanceMini) {
				protoProche = protoC1;
				distanceMini = distance(protoC1, p);
			}
		}
		for (Point protoC2 : protosB) {
			if (distance(protoC2, p) < distanceMini) {
				protoProche = protoC2;
				distanceMini = distance(protoC2, p);
			}
		}
		if(protosA.contains(protoProche)){
			System.out.println("				---> A");
		}
		if(protosB.contains(protoProche)){
			System.out.println("				---> B");
		}
		return protoProche;
	}

	public double distance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
}
