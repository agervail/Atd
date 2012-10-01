/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author twane
 */
public class Kppv {

	Points points;
	AireDeDessin aire;
	int k;

	public Kppv(Points points, AireDeDessin aire) {
		this.points = points;
		this.aire = aire;
	}

	public void afficherSolution(int k) {
		this.k = k;
		ArrayList<Point> ppv = rechercheKppv();
		ArrayList<Point> ppvA = new ArrayList<Point>();
		ArrayList<Point> ppvB = new ArrayList<Point>();
		int nbA = 0, nbB = 0;
		for (Point p : ppv) {
			if (points.pointsA.contains(p)) {
				ppvA.add(p);
				nbA++;
			} else {
				ppvB.add(p);
				nbB++;
			}
		}
		if (nbA > nbB) {
			aire.dessinerKppv(ppvA, ppvB);
			aire.repaint();
		} else {
			aire.dessinerKppv(ppvB, ppvA);
			aire.repaint();
		}
	}

	public ArrayList<Point> rechercheKppv() {
		ArrayList<Point> ppv = new ArrayList<Point>();
		Point pointLoin = new Point();
		Double dMax = 0.;
		Point pointX = points.pointX;

		//On commence avec les k premiers
		int j = 0;
		int classeCourante;
		int a = 0, b = 0;
		Double dTemp = 0.;
		Point pTemp;

		for (int i = 0; i < k; i++) {
			classeCourante = points.ordre.get(j);
			if (classeCourante == 3) {
				j++;
			}
			classeCourante = points.ordre.get(j);

			if (classeCourante == 1) {
				pTemp = points.pointsA.get(a);
				ppv.add(pTemp);
				a++;
			} else {
				pTemp = points.pointsB.get(b);
				ppv.add(pTemp);
				b++;
			}
			dTemp = distance(pTemp, pointX);
			if (dTemp > dMax) {
				pointLoin = pTemp;
				dMax = dTemp;
			}

			j++;
		}

		System.out.println(a + " " + b);
		for (int i = k; i < points.ordre.size() - 1; i++) {
			System.out.println("b2");
			classeCourante = points.ordre.get(j);
			if (classeCourante == 3) {
				j++;
			}
			classeCourante = points.ordre.get(j);

			if (classeCourante == 1) {
				pTemp = points.pointsA.get(a);
				if (distance(pTemp, pointX) < dMax) {
					ppv.remove(pointLoin);

					ppv.add(pTemp);
					dMax = 0.;
					for (Point p : ppv) {
						dTemp = distance(p, pointX);
						if (dTemp > dMax) {
							pointLoin = p;
							dMax = dTemp;
						}
					}
				}
				a++;
			} else {
				pTemp = points.pointsB.get(b);
				if (distance(pTemp, pointX) < dMax) {
					ppv.remove(pointLoin);

					ppv.add(pTemp);
					dMax = 0.;
					for (Point p : ppv) {
						dTemp = distance(p, pointX);
						if (dTemp > dMax) {
							pointLoin = p;
							dMax = dTemp;
						}
					}
				}
				b++;
			}
			j++;
		}

		System.out.println("PPV : " + ppv);
		return ppv;
	}

	public double distance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
}
