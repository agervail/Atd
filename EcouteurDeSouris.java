import java.awt.*;
import java.awt.event.*;

class EcouteurDeSouris extends MouseAdapter {
	AireDeDessin aire;
	Point click = new Point(-1,-1);
	Point clickCopier = new Point(-1,-1);
	Point precedent = new Point(-1,-1);
	Point mouse = new Point(-1,-1);
	Point origine = new Point(-1,-1);
	Point clickColler = new Point(-1,-1);
	Edition images;
	int button;
	
	
	public EcouteurDeSouris(AireDeDessin a, Edition ed){
		aire = a;
		images = ed;
	}

	//retient le bouton préssé
    public void mousePressed(MouseEvent e) {
		aire.precedent.x = e.getX();
		aire.precedent.y = e.getY();
		button = e.getButton();
		if(aire.mode == 0){
			images.InsererEtape(images.etapes.peek());
			aire.dessinable = images.etapes.peek().createGraphics();
		}
		if(aire.mode == 2){
			images.ActiverCopier();
			clickColler.x = e.getX();
			clickColler.y = e.getY();
			aire.Coller(clickColler);
		}
		if(aire.mode == 1){
    		images.ActiverColler();
			images.InsererEtape(images.etapes.peek());
			aire.dessinable = images.etapes.peek().createGraphics();
			clickCopier.x = e.getX();
			clickCopier.y = e.getY();
		}
    }

    //Effectue une opération en fonction du bouton préssé
    public void mouseDragged(MouseEvent e) {
    	if(aire.mode == 0){
	    	if(button == MouseEvent.BUTTON1){
		    	click.x = e.getX();
		 		click.y = e.getY();
		 		aire.actuel.x = click.x;
		 		aire.actuel.y = click.y;
		 		aire.dessiner_ligne(click.x, click.y);
				aire.repaint();
	    	}
	    	if(button == MouseEvent.BUTTON3){
		    	click.x = e.getX();
		 		click.y = e.getY();
		 		aire.effacer(click.x, click.y);
				aire.repaint();
	    	}
    	}
    	if(aire.mode == 1){
    		mouse.x = e.getX();
	 		mouse.y = e.getY(); 
    		aire.dessiner_rectangle(clickCopier,mouse);
    		aire.repaint();
    	}
 	}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
    	if(button == MouseEvent.BUTTON1 && aire.mode == 1){
    		mouse.x = e.getX();
    		mouse.y = e.getY();
    		aire.Copier(clickCopier,mouse);
			aire.mode = 0;
    	}
    }
    public void mouseMoved(MouseEvent e) { }
}
