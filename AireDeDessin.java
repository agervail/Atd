import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;


class AireDeDessin extends JComponent {
	private static final long serialVersionUID = 1L;
	Point actuel = new Point(-1,-1);
	Point precedent = new Point(-1,-1);
	BufferedImage tmp;
	Graphics2D dessinable;
	Point taille = new Point(800,800);
	Edition images;
	JFrame frame;
	int mode = 0;

	public AireDeDessin(Edition ed, JFrame fr){
		images = ed;
		frame = fr;
		this.nouvelleImage();
	}

	public void nouvelleImage(){
		if(getSize().width != 0 || getSize().height != 0){
				taille.x = getSize().width;
				taille.y = getSize().height;
		}
		images.NouvelleImage();
		images.InsererEtape(new BufferedImage(taille.x,taille.y,BufferedImage.TYPE_INT_RGB));

		dessinable = images.etapes.peek().createGraphics();
		dessinable.setPaint(Color.white);
		dessinable.fillRect(0, 0, taille.x,taille.y);
		this.repaint();
}
	
	//appellee des le debut par ecouteur de fenetre
	public void redimensionImage(int largeur, int hauteur){
		//Si la taille augmente on redimensionne
		if(largeur > taille.x || hauteur > taille.y){
			//On prend le max pour ne pas perdre une partie de la fenetre
			taille.x = Math.max(largeur, taille.x)+50;
			taille.y = Math.max(hauteur,taille.y)+50;
			//On sauvegarde le dessin précédent
			BufferedImage sauv_img = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
			sauv_img = images.etapes.peek();
			
			//On cree la nouvelle image
			
			images.InsererEtape(new BufferedImage(taille.x,taille.y,BufferedImage.TYPE_INT_RGB));
			dessinable = images.etapes.peek().createGraphics();
			dessinable.setPaint(Color.white);
			dessinable.fillRect(0, 0, taille.x,taille.y);
			
			//On redessine l'ancienne image sur la nouvelle
			dessinable.drawImage(sauv_img,null,0,0);
			dessinable.setPaint(Color.black);
		}
	}
	
	//Efface un carré de 10 par 10 aux coordonnées x et y
	public void effacer(int x, int y){
		dessinable.setPaint(Color.white);
		dessinable.fillRect(x-5, y-5, 10,10);

	}
	
	//Dessine une ligne depuis les précédentes coordonnées jusqu'a x et y
	public void dessiner_ligne(int x, int y){
		dessinable.setPaint(Color.black);
		dessinable.drawLine(precedent.x,precedent.y,x,y);
		precedent.x = actuel.x;
		precedent.y = actuel.y;
	}

	public void sauvegarder() {
		JFileChooser choix = new JFileChooser();

		while(choix.showSaveDialog(this) != JFileChooser.APPROVE_OPTION ){}
		try{
		ImageIO.write(images.etapes.peek(), "png", choix.getSelectedFile());
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

	public void charger() {
		JFileChooser choix = new JFileChooser();

		while(choix.showOpenDialog(this) != JFileChooser.APPROVE_OPTION ){}
		try{
			this.nouvelleImage();
			images.NouvelleImage();
			images.InsererEtape(ImageIO.read(choix.getSelectedFile()));
			dessinable = images.etapes.peek().createGraphics();
			this.repaint();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	public void ModeCopier() {
		mode = 1;
	}
	public void ModeColler(){
		mode = 2;
	}

	public void Coller(Point clickColler) {
		images.InsererEtape(images.etapes.peek());
		dessinable = images.etapes.peek().createGraphics();
		dessinable.drawImage(tmp, null, clickColler.x,clickColler.y);
		repaint();
		mode = 0;
	}
	public void Copier(Point clickCopier, Point mouse) {
		images.Detruire();
		tmp = images.etapes.peek().getSubimage(Math.min(clickCopier.x,mouse.x),Math.min(clickCopier.y,mouse.y),Math.abs(clickCopier.x-mouse.x),Math.abs(clickCopier.y-mouse.y));
		dessinable = images.etapes.peek().createGraphics();
		repaint();
	}
	public void dessiner_rectangle(Point clickCopier, Point mouse) {
		images.Annuler();
		images.InsererEtape(images.etapes.peek());
		dessinable = images.etapes.peek().createGraphics();
		dessinable.setPaint(Color.red);
		dessinable.drawRect(Math.min(clickCopier.x,mouse.x),Math.min(clickCopier.y,mouse.y),Math.abs(clickCopier.x-mouse.x),Math.abs(clickCopier.y-mouse.y));
		
	}

	
	public void paintComponent(Graphics g) {
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        Graphics2D drawable = (Graphics2D) g;
        //Dessine l'image sur le 'dessin'
        frame.setSize(images.Largeur(), images.Hauteur());
		drawable.drawImage(images.etapes.peek(), null, 0,0);
    }

}
