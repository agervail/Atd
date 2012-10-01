/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

/**
 *
 * @author twane
 */
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Atd_classe implements Runnable {

	AireDeDessin mon_dessin;
	JPanel menuHaut;
	JPanel menuAlgo;
	JPanel menuBas;
	public Dimension taille_fenetre = new Dimension(600, 600);
	Points points;
	Kppv kppv;
	Prototypes prototypes;
	JComboBox nb_kppv;
	JComboBox nb_proto;

	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("ATD");
		frame.setLayout(null);

		points = new Points();


		mon_dessin = new AireDeDessin(frame, taille_fenetre, points);
		mon_dessin.setBounds(0, 100, taille_fenetre.width, taille_fenetre.height - 200);

		kppv = new Kppv(points, mon_dessin);
		prototypes = new Prototypes(points, mon_dessin);
		

		menuHaut = new JPanel();
		menuAlgo = new JPanel();

		menuBas = new JPanel();
		// Creation du composant de dessin et ajout de l'objet de traitement
		// des evenements provenant de la souris et du redimmensionnement

		frame.setFocusable(true);

		ButtonGroup group = new ButtonGroup();
		JRadioButton classe_A = new JRadioButton("classe A");
		JRadioButton classe_B = new JRadioButton("classe B");
		JRadioButton classe_X = new JRadioButton("classe X");

		JButton bKppv = new JButton("kppv");
		nb_kppv = new JComboBox();
		nb_proto = new JComboBox();
		nb_proto.addItem("1");
		nb_proto.addItem("2");
		nb_proto.addItem("3");
		JButton bPrototypes = new JButton("prototypes");

		bPrototypes.setEnabled(false);
		bKppv.setEnabled(false);

		points.setComboBoxKppv(nb_kppv);
		points.setbKppv(bKppv);
		points.setbProto(bPrototypes);

		JButton recommencer = new JButton("Recommencer");
		JButton annuler = new JButton("Annuler");

		// JRadioButton

		bKppv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kppv.afficherSolution(nb_kppv.getSelectedIndex() * 2 + 1);
			}
		});
		bPrototypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prototypes.afficherSolution(nb_proto.getSelectedIndex()+1);
			}
		});
		recommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.recommencer();
				mon_dessin.effacer_tout();
				mon_dessin.repaint();
			}
		});
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.retirerPoint();
				mon_dessin.dessiner_tout();
				mon_dessin.repaint();
			}
		});
		classe_A.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.classeSelectionne = 1;
			}
		});
		classe_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.classeSelectionne = 2;
			}
		});
		classe_X.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.classeSelectionne = 3;
			}
		});

		//menuHaut.add(recommencer);
		//menuHaut.add(annuler);

		group.add(classe_A);
		group.add(classe_B);
		group.add(classe_X);

		JTextArea space1 = new JTextArea(1, 5);
		space1.setBackground(frame.getBackground());
		JTextArea space1bis = new JTextArea(1, 5);
		space1bis.setBackground(frame.getBackground());
		JTextArea space2 = new JTextArea(1, 10);
		space2.setBackground(frame.getBackground());

		menuHaut.add(classe_A);
		menuHaut.add(space1);
		menuHaut.add(classe_B);
		menuHaut.add(space1bis);
		menuHaut.add(classe_X);

		menuAlgo.add(bPrototypes);
		menuAlgo.add(nb_proto);
		menuAlgo.add(space2);
		menuAlgo.add(bKppv);
		menuAlgo.add(nb_kppv);

		menuBas.add(annuler);
		menuBas.add(recommencer);



		MouseAdapter adapter = new EcouteurDeSouris(mon_dessin, points);

		mon_dessin.addMouseListener(adapter);

		mon_dessin.addMouseMotionListener(adapter);

		//Ajout du menuHaut de selection

		menuHaut.setBounds(0, 0, taille_fenetre.width, 50);
		frame.add(menuHaut);

		menuAlgo.setBounds(0, 50, taille_fenetre.width, 50);
		frame.add(menuAlgo);

		menuBas.setBounds(0, 500, taille_fenetre.width, 100);
		frame.add(menuBas);

		// Ajout de notre composant de dessin dans la fenetre
		frame.add(mon_dessin);



		// Menu principal et edition


		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setSize(taille_fenetre);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Atd_classe());
	}
}