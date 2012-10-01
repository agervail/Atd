/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author twane
 */
public class Points {

	ArrayList<Point> pointsA;// <int>;
	ArrayList<Point> pointsB;// <int>;
	int classeSelectionne;
	Point pointX;
	boolean xDefini;
	int xIndice;
	Stack<Integer> ordre;
	JComboBox comboKppv;
	JButton bKppv;
	JButton bProto;

	//= new ArrayList<String>();
	public Points() {
		this.pointsA = new ArrayList<Point>();
		this.pointsB = new ArrayList<Point>();
		pointX = new Point();
		classeSelectionne = 0;
		ordre = new Stack<Integer>();
		xDefini = false;
		xIndice = 0;
	}

	public void ajouterPoint(Point p) {
		if (classeSelectionne != 0) {

			if (classeSelectionne == 1) {
				pointsA.add(p);
				ordre.push(classeSelectionne);
				if ((pointsA.size() + pointsB.size()) % 2 == 1) {
					comboKppv.addItem(pointsA.size() + pointsB.size());
				}
			}
			if (classeSelectionne == 2) {
				pointsB.add(p);
				ordre.push(classeSelectionne);
				if ((pointsA.size() + pointsB.size()) % 2 == 1) {
					comboKppv.addItem(pointsA.size() + pointsB.size());
				}
			}
			if (classeSelectionne == 3) {
				pointX = p;
				if (!xDefini) {
					ordre.push(classeSelectionne);
					xIndice = ordre.size() - 1;
					xDefini = true;
				} else {
					ordre.removeElementAt(xIndice);
					ordre.push(classeSelectionne);
					xIndice = ordre.size() - 1;
				}
			}
			if (xDefini) {
				if (pointsA.size() + pointsB.size() >= 1) {
					bKppv.setEnabled(true);
				}
				if (pointsA.size() >= 2 && pointsB.size() >= 2) {
					bProto.setEnabled(true);
				}
			}
		}
	}

	public void retirerPoint() {
		if (!ordre.empty()) {
			int classe = ordre.pop();
			if (classe == 1) {
				pointsA.remove(pointsA.size() - 1);
				if ((pointsA.size() + pointsB.size()) % 2 == 0) {
					comboKppv.removeItemAt((pointsA.size() + pointsB.size()) / 2);
				}
			}
			if (classe == 2) {
				pointsB.remove(pointsB.size() - 1);
				if ((pointsA.size() + pointsB.size()) % 2 == 0) {
					comboKppv.removeItemAt((pointsA.size() + pointsB.size()) / 2);
				}
			}
			if (classe == 3) {
				xDefini = false;
				xIndice = -1;
				pointX = new Point();
			}
			if (!xDefini) {
				bKppv.setEnabled(false);
				bProto.setEnabled(false);
			} else if (!(pointsA.size() + pointsB.size() >= 1)) {
				bKppv.setEnabled(false);
			} else if (!(pointsA.size() >= 2 && pointsB.size() >= 2)) {
				bProto.setEnabled(false);
			}
		}
	}

	public void recommencer() {
		this.pointsA = new ArrayList<Point>();
		this.pointsB = new ArrayList<Point>();
		//classeSelectionne = 0;
		xIndice = -1;
		xDefini = false;
		ordre = new Stack<Integer>();
		pointX = new Point();
		comboKppv.removeAllItems();
		bKppv.setEnabled(false);
		bProto.setEnabled(false);
	}

	public void setComboBoxKppv(JComboBox box) {
		this.comboKppv = box;
	}

	public void setbKppv(JButton bKppv) {
		this.bKppv = bKppv;
	}

	public void setbProto(JButton bProto) {
		this.bProto = bProto;
	}

	public Stack<Integer> inverserOrdre() {
		Stack<Integer> res = new Stack<Integer>();
		Stack<Integer> pTemp = (Stack<Integer>) ordre.clone();
		while (!pTemp.empty()) {
			res.push(pTemp.pop());
		}
		return res;
	}
}
