package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class ControlBouton implements ActionListener{
	
	Model m;
	Vue v;
	String val1, val2;
	int compteur=0;
	char val1i ,val1j, val2i, val2j;
	int val1x,val1y,val2x,val2y;
	int numIcon, compteIcon, compteIcon_Y ,k=0, tmp_J;
	int[] bestscore = new int[6];
	boolean testCase, testCoup1, testCoup2;
	
	BufferedWriter bw;
	
	public final String scorePath = "src/res/Scores.txt";

	public ControlBouton(Model m, Vue v){
		this.m = m;
		this.v = v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(compteur == 0){
			System.out.println("pressed 1 !");
			val1 = e.getActionCommand();
			//caracteres
			val1i = val1.charAt(0);
			val1j = val1.charAt(1);	
			//int
			val1x =  Character.getNumericValue(val1i);
			val1y =  Character.getNumericValue(val1j);
			compteur += 1;
			System.out.println(val1i + " " + val1j);
			System.out.println(val1x + " " + val1y);

		}else{
			System.out.println("pressed 2 !");
			val2 =e.getActionCommand();
			//caracteres
			val2i = val2.charAt(0);
			val2j = val2.charAt(1);
			//int
			val2x =  Character.getNumericValue(val2i);
			val2y =  Character.getNumericValue(val2j);
			
			System.out.println(val2i + " " + val2j);
			System.out.println(val2x + " " + val2y);
			
			testCase= m.getTabIntNumb(val1x, val1y) == m.getTabIntNumb(val2x, val2y);
			
			testCoup1=coupOK(val1x,val1y,val2x,val2y);
			testCoup2=coupOK(val2x,val2y,val1x,val1y);
			System.out.println(testCase);
			
			if(!testCoup1 && !testCoup2){
				m.setTries();
				System.out.println(m.getTries());
				
				if(m.getTries()==0){
					showMessage();
					m.hideBoutons();
				}
			}
			
			if(( testCoup1 || testCoup2 ) && !testCase){
				System.out.println("swap!");
				swap(val1x, val1y, val2x, val2y);	
				m.updateTab();
				System.out.println("début de chute?");
				chuteHorizontale();
				chuteVerticale();
				
			}	
			chuteHorizontale();
			chuteVerticale();
			
			compteur = 0;
			
			m.updateTab();
			
		}
		
	}
	
	/**
	 * récupère d'abord l'indice passé dans la première case (getTabIntNumb), le déporte dans
	 * une variable temporaire, puis déporte la valeur de la deuxième case
	 * dans la première... Pour intervertir les valeurs
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 */
	public void swap(int i1, int j1, int i2, int j2){	
		int tmp;
		tmp = m.getTabIntNumb(i1, j1);
		m.tabInt[i1][j1] = m.getTabIntNumb(i2, j2);
		m.tabInt[i2][j2] = tmp;
		
	}
	
/**
 * récupère les coordonnées des deux cases saisies et si le coup est valide, 
 * le déplacement est effectué
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @return
 */
	public boolean coupOK(int x1,int y1, int x2, int y2){
		
	/* DEPLACEMENTS VERTICAUX */
		//CASE 1 : deux en bas à droite (horizontalement)
		try {
			if(x1+2 <= m.TABSIZE){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1,y1+1)
						&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+2,y1+1)){
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		//}//deux en bas à gauche (horizontalement)
		try{
			if(x1-2 >= 0){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-1,y1+1)
					&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-2,y1+1)){
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}//deux en bas (verticalement)
		try{
			if(y1+3 <= m.TABSIZE){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1,y1+2)
						&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1,y1+3)){
					return true;
				}
			}
		}catch (Exception e) {
			
			e.printStackTrace();
		}//un à gauche, un à droite, en bas
		try{
			if(x1+1 <= m.TABSIZE && x1-1 >= 0){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-1, y1+1)
					&& (m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+1))){
				return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}//un à gauche, deux à droite
		try{
			if(x1-1>=0 && x1+2 <= m.TABSIZE){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-1, y1+1)
						&& (m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+1))
						&& (m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+2, y1+1))){
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//deux à gauche, un à droite
		try{
			if(x1+1 <= m.TABSIZE && x1-2 >= 0){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+1)
					&& (m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-1, y1+1))
					&& (m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1-2, y1+1))){
					return true;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//CASE 2 : deux en haut à gauche 
		try{
			if(x2-2>=0  && y2-2 >=0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1)
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-2, y2-1))){
			return true;
			}}
		}catch (Exception e) {
			e.printStackTrace();
		}//deux en haut à droite
		try{
			if(x2+2 <= m.TABSIZE && y2-1>=0){
				if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+1,y2-1)
						&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+2,y2-1)){
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}//deux en haut
		try{ 
			if( y2-3 >=0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2,y2-2)
				&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2,y2-3)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux cases autour, en haut
		try{ 
			if(x2+1 <= m.TABSIZE || x2-1 >= 0)
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1)
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+1, y2-1))){
			return true;
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux à droite, un à gauche
		try{
			if(x2+2 <= m.TABSIZE && y2-1>=0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1)
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+1, y2-1))
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+2, y2-1))){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux à gauche, un à droite
		try{
			if(x2-2 >= 0 && y2-1 >= 0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2+1, y2-1)
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1))
				&& (m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-2, y2-1))){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}
	/* DEPLACEMENTS HORIZONTAUX */
		try{
			if(x1+1 <= m.TABSIZE || y1-2>=0){
			if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1-1)
				&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1-2)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux en bas à droite
		try{
			if(x1+1 <= m.TABSIZE || y1+2<= m.TABSIZE){
			if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+1)
				&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+2)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux à droite
		try{
			if(x1+3 <= m.TABSIZE){
			if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+2, y1)
				&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+3, y1)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux autour
		try{ 
			if(x1+1 <= m.TABSIZE){
				if(m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1-1)
				&& m.getTabIntNumb(x1, y1) == m.getTabIntNumb(x1+1, y1+1)){
					return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//CASE 2 : deux en haut à gauche 
		try{ 
			if(x2-1 >= 0 && y2-2>=0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1)
				&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-2)){
			return true;}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux en bas à gauche
		try{
			if(x2-1 >= 0 && y2+2<=m.TABSIZE){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2+1)
				&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2+2)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux à gauche
		try{ 
			if(x2-3 >= 0){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-2, y2)
				&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-3, y2)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}//deux autour
		try{
			if(x2-1 >= 0 || y2-1>=0 || y2+1<=m.TABSIZE){
			if(m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2-1)
				&& m.getTabIntNumb(x2, y2) == m.getTabIntNumb(x2-1, y2+1)){
			return true;
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("case 2 not OK");
		
		return false;
		

	}

/**
 * fonction qui réalise la chute lorsque trois icones sont alignées horizontalement
 */
	public void chuteHorizontale(){
		numIcon = m.getTabIntNumb(0, 0);
		compteIcon = 0;
		int tmp;
		for(int j = 0; j<m.TABSIZE;j++){
			compteIcon = 0;
			for(int i = 0;i<m.TABSIZE; i++){
				
				if(m.getTabIntNumb(i, j) == numIcon){
					
					/*si on enlève cette condition, lorsque les deux premières icones 
					 * seront identiques, la chute horizontale s'activera 
					 * ==> OutOfBounds */
					if(i==0 && j==0){
						compteIcon=0;
					}else{
						compteIcon+=1;
					}
					if(compteIcon>=2){
						System.out.println(compteIcon+1 + " fois l'icone " + numIcon);
						
						int tmp_j = j;
						while(tmp_j>0){
							k=compteIcon;
							while(k!=-1){
								m.tabInt[i-k][tmp_j] = m.getTabIntNumb(i-k,tmp_j-1);
								k-=1;
							}
							tmp_j=tmp_j-1;
						}
						if(tmp_j == 0){
							k=compteIcon;
							System.out.println("k = " + k);
							while(k != -1){
								System.out.println("i-k = " + (i-k));
								m.tabInt[i-k][0] = m.rand.nextInt(8);
								k=k-1;
							}
						}
						if(compteIcon == 2){
							m.setScore(100*m.getLevel());
					//		v.xp.setValue((v.xp.getValue()+3)%100);
						}
						else if(compteIcon == 3){
							m.setScore(300*m.getLevel());
						//	v.xp.setValue((v.xp.getValue()+5)%100);
						}else if(compteIcon == 4){
							m.setScore(1000*m.getLevel());
						//	v.xp.setValue((v.xp.getValue()+10)%100);
						}
						
						m.updateTab();
					}
					
				}else{
					compteIcon=0;
					numIcon=m.getTabIntNumb(i, j);
				}
			}
		}
	}
/**
 * fonction qui réalise la chute lorsque trois icones sont alignées verticalement
 * 
 * j est la coordonnée qui ne change pas, d'où un k variant de 0 au max
 * tmp_J est variable et remonte à chaque iteration
 */
	public void chuteVerticale(){
		numIcon=m.getTabIntNumb(0, 0);
		
		for(int i=0; i<m.TABSIZE;i++){
			compteIcon_Y = 0;
			for(int j=0; j<m.TABSIZE;j++){
				
				if(m.getTabIntNumb(i, j)==numIcon){
					compteIcon_Y+=1;
					if(compteIcon_Y>=2){
						System.out.println(compteIcon_Y+1 + " fois l'icone " + m.getTabIntNumb(i, j));
						tmp_J = j;
						System.out.println("J = " + tmp_J);
						k=0;
			
						while(j-k!=-1){
							if(tmp_J-compteIcon_Y-1<=0){
								m.tabInt[i][j-k]=m.rand.nextInt(8);
							}else{
								try{	
									m.tabInt[i][j-k] = m.getTabIntNumb(i, tmp_J-compteIcon_Y-1);
								}catch(Exception e){
									e.printStackTrace();
								}
								tmp_J -=1 ;
							}
							k+=1;
							
						}
						m.updateTab();
						
					}
					/* Compte les points accumulés*/
					if(compteIcon_Y==2){
						m.setScore(m.getLevel()*100);
							
					//NullPointerException q.q
					//	v.xp.setValue((v.xp.getValue()+4)%100);
					
					}
					else if(compteIcon_Y==3){
						m.setScore(m.getLevel()*300);	
						//v.xp.setValue((v.xp.getValue()+8)%100);
						
					}
					else if(compteIcon_Y==4){
						m.setScore(m.getLevel()*1000);
						//v.xp.setValue((v.xp.getValue()+15)%100);
						
					}
				}else{
					compteIcon_Y=0;
					numIcon = m.getTabIntNumb(i, j);
				}
			}
		}
		
	}

/*	public void endOfGame(){
		if(m.getTries()==0){
			showMessage();
			
		}
	
	}*/
	
/**
 * Message qui s'affiche lorsque tous les essais sont utilisés
 */
	public void showMessage(){
		JOptionPane.showMessageDialog(v, "Fin de parties, vous avez utilisé tous vos essais.",
				"END", JOptionPane.INFORMATION_MESSAGE); 
				System.out.println("PLUS D ESSAIS");
				m.hideBoutons();
				compareScore();
				v.stopProgress();
				
	}
/**
 * fonction qui compare le score obtenu avec les meilleurs
 * scores stockés
 * Problème: réécrit tout le fichier...ou rien du tout.
 */
	public void compareScore(){
		int cpt = 2;
		String s;
		try{
			BufferedReader br = new BufferedReader(new FileReader(scorePath));
			while((s=br.readLine())!= null){
				bestscore[cpt] = Integer.parseInt(s);
				cpt+=2;
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(scorePath));
		//	String nw = Integer.toString(m.getScore());
			if(m.getScore()>bestscore[2]){
				
				//bestscore[2] = bw.write(" " + nw);
				//bestscore[2] = Integer.parseInt(nw);
				
				bw.write("1st : \n " + Integer.toString(m.getScore()) + "\n2nd : \n " + Integer.toString(bestscore[4]) + "\n3rd : " + Integer.toString(bestscore[6]));
				System.out.println("Réécriture 1");
			}
			else if(m.getScore()>bestscore[4]){
			
				//bestscore[4] = 
				//		bw.write("" + m.getScore());
			//	bw.write("2nd : \n " + m.getScore());
				bw.write("1st : \n " + Integer.toString(bestscore[2]) + "\n2nd : \n " + Integer.toString(m.getScore()) + "\n3rd : " + Integer.toString(bestscore[6]));
				
						System.out.println("Réécriture 2");
			}
			else if(m.getScore()>bestscore[6]){
				
			//	bestscore[6] = 
				//		bw.write("" + m.getScore());
				//bw.write("3rd : \n " + m.getScore());
				bw.write("1st : \n " + Integer.toString(bestscore[2]) + "\n2nd : \n " + Integer.toString(bestscore[4]) + "\n3rd : " + Integer.toString(m.getScore()));
				
				System.out.println("Réécriture 3");
			}
			bw.close();
		}catch(Exception exe){
			exe.printStackTrace();
		}
		
		

	}
}
