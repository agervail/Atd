import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Stack;

import javax.swing.JMenuItem;


public class Edition {
	Stack<BufferedImage> etapes = new Stack<BufferedImage>();
	Stack<BufferedImage> etapesDepiles = new Stack<BufferedImage>();
	JMenuItem bAnnuler;
	JMenuItem bRetablir;
	JMenuItem bCopier;
	JMenuItem bColler;
	
	public void InfoBoutons(JMenuItem annuler, JMenuItem retablir, JMenuItem copier, JMenuItem coller){
		bAnnuler = annuler;
		bRetablir = retablir;
		bCopier = copier;
		bColler = coller;
	}
	public void InsererEtape(BufferedImage img){
		etapes.push(deepCopy(img));
		if(etapes.size() > 1){
			try{
				bAnnuler.setEnabled(true);
			}catch(Exception e){}
			try{
				bRetablir.setEnabled(false);
			}catch(Exception e){}
		}
		etapesDepiles.clear();
	}
	public void Annuler(){
		etapesDepiles.push(etapes.pop());
		bRetablir.setEnabled(true);
		if(etapes.size() == 1){
			bAnnuler.setEnabled(false);
		}
	}
	public void Retablir(){
		etapes.push(etapesDepiles.pop());
		bAnnuler.setEnabled(true);
		if(etapesDepiles.empty()){
			bRetablir.setEnabled(false);
		}
	}
	public int Largeur(){
		return etapes.peek().getWidth();
	}
	public int Hauteur(){
		return etapes.peek().getHeight();
	}
	public void NouvelleImage(){
		try{
		bRetablir.setEnabled(false);
		bAnnuler.setEnabled(false);
		etapes.clear();
		etapesDepiles.clear();
		}catch(Exception e){}
	}
	//Fonction qui renvoie une deepCopy de la BuffereImage
	//passée en paramêtre
	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public void ActiverColler() {
		bCopier.setEnabled(false);
		bColler.setEnabled(true);
	}
	public void ActiverCopier() {
		bCopier.setEnabled(true);
		bColler.setEnabled(false);
	}
	public void Detruire() {
		etapes.pop();
		if(etapes.size() == 1){
			bAnnuler.setEnabled(false);
		}
	}

}
