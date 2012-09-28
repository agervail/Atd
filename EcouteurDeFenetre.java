import java.awt.Component;
import java.awt.event.*;


public class EcouteurDeFenetre implements ComponentListener {
	AireDeDessin mon_dessin;
	
	public EcouteurDeFenetre(AireDeDessin mon_des){
		this.mon_dessin = mon_des;
	}
	
	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	//Quand la fenêtre est redimensionnée cela appelle la fonction
	//nouvelle image.
	public void componentResized(ComponentEvent e) {
		Component g = e.getComponent();
		mon_dessin.redimensionImage(g.getWidth(),g.getHeight());
	}

	public void componentShown(ComponentEvent e) {

	}

}
