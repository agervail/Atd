import javax.swing.*;
import java.awt.event.*;

public class Photochope implements Runnable {
    Edition images = new Edition();
    AireDeDessin mon_dessin;
    
    public void run() {
        // Creation d'une fenetre
        JFrame frame = new JFrame("photochope");
        mon_dessin = new AireDeDessin(images,frame);
        // Creation du composant de dessin et ajout de l'objet de traitement
        // des evenements provenant de la souris et du redimmensionnement
        frame.setFocusable(true);

		MouseAdapter adapter = new EcouteurDeSouris(mon_dessin,images);
        KeyListener listen = new EcouteurDeClavier(images,mon_dessin);
		
        mon_dessin.addMouseListener(adapter);

		frame.addKeyListener(listen);
        mon_dessin.addMouseMotionListener(adapter);
        mon_dessin.addComponentListener(new EcouteurDeFenetre(mon_dessin));

        // Ajout de notre composant de dessin dans la fenetre
        frame.add(mon_dessin);
        // Menu principal et edition
        JMenu menu = new JMenu("Principal");
        JMenu edition = new JMenu("Edition");

        JMenuItem item0 = new JMenuItem("Nouveau");
        JMenuItem item1 = new JMenuItem("Sauvegarder");
        JMenuItem item2 = new JMenuItem("Charger");
        JMenuItem item3 = new JMenuItem("Quitter");
        
        JMenuItem item4 = new JMenuItem("Annuler");
        item4.setEnabled(false);
        JMenuItem item5 = new JMenuItem("Refaire");
        item5.setEnabled(false);
        JMenuItem item6 = new JMenuItem("Copier");
        JMenuItem item7 = new JMenuItem("Coller");
        item7.setEnabled(false);
        
        images.InfoBoutons(item4, item5,item6,item7);
        
        item0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mon_dessin.nouvelleImage();

			}
		});
        item3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        
        item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mon_dessin.sauvegarder();
			}
		});
        item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mon_dessin.charger();
			}
		});
        
        item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				images.Annuler();
				mon_dessin.repaint();
			}
		});
        item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				images.Retablir();
				mon_dessin.repaint();
			}
		});
        item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mon_dessin.ModeCopier();
			}
		});
        item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mon_dessin.ModeColler();
			}
		});
        
        menu.add(item0);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        
        edition.add(item4);
        edition.add(item5);
        edition.add(item6);
        edition.add(item7);

        // Barre de menu
        JMenuBar barre = new JMenuBar();
        barre.add(menu);
        barre.add(edition);
        frame.setJMenuBar(barre);

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et on demarre
        frame.setSize(800, 800);
        frame.setVisible(true);

    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Photochope());
    }
}
