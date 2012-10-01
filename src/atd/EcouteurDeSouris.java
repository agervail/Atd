/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atd;

/**
 *
 * @author twane
 */
import java.awt.*;
import java.awt.event.*;

class EcouteurDeSouris extends MouseAdapter {

    AireDeDessin aire;
    Points points;

    public EcouteurDeSouris(AireDeDessin a, Points points) {
        aire = a;
        this.points = points;
    }

    //retient le bouton préssé
    public void mousePressed(MouseEvent e) {
    }

    //Effectue une opération en fonction du bouton préssé
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        Point p = new Point(e.getX(),e.getY());
        points.ajouterPoint(p);
        aire.dessiner_point(p,points.classeSelectionne);
        aire.repaint();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    }
}