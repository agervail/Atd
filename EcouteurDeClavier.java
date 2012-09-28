import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class EcouteurDeClavier implements KeyListener {
	Edition images;
	AireDeDessin mon_dessin;
	
	public EcouteurDeClavier(Edition img, AireDeDessin des) {
		images = img;
		mon_dessin = des;
	}

	public void keyPressed(KeyEvent e) {
	    if(e.getKeyChar() == 26){
	    	if(images.etapes.size() > 1){
	    		images.Annuler();
	    		mon_dessin.repaint();
	    	}
	    }
	    else if(e.getKeyChar() == 25){
	    	if(!images.etapesDepiles.empty()){
	    		images.Retablir();
	    		mon_dessin.repaint();
	    	}
	    }
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

}
