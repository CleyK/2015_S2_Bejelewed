package main;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

public class Model {
	
	protected Vue v;
	protected Random rand = new Random();
	protected int randInt, newRandInt;
	protected JButton[][] boutonGrid = new JButton[8][8];
	protected static int[][] tabInt = new int[8][8];
	protected int[] stockValeur = new int[64];
	final int TABSIZE = boutonGrid.length;
	protected ControlBouton controlbut = new ControlBouton(this,v);
	JPanel pGame = new JPanel(new GridLayout(8,8,5,5));
	
	protected int valueLevel, valueTries,valueScore, code;
	protected ImageIcon red = new ImageIcon("src/res/red.png");
	protected ImageIcon pink = new ImageIcon("src/res/pink.png");
	protected ImageIcon orange = new ImageIcon("src/res/orange.png");
	protected ImageIcon yellow = new ImageIcon("src/res/yellow.png");
	protected ImageIcon green = new ImageIcon("src/res/green.png");
	protected ImageIcon blue = new ImageIcon("src/res/blue.png");
	protected ImageIcon purple = new ImageIcon("src/res/purple.png");
	protected ImageIcon grey = new ImageIcon("src/res/grey.png");
	
	
	Model(){
		valueLevel = 1;
		valueTries = 15;
		valueScore = 0;
	}
	
/**
 * créé les boutons mais pas les icones
 */
	public void creerBoutons(){
		randInt=0;
		int k=0;
		int valueTestJ = -1 ;
		int compteurValJ = 0 ;
		
		for(int j=0;j<TABSIZE;j++){	
			compteurValJ=0;
			for(int i=0;i<TABSIZE;i++){			
				randInt=rand.nextInt(8);			
					if(j==0){					
						valueTestJ = randInt;
						compteurValJ=0;				
					}else{				
						if(randInt == valueTestJ){					
							compteurValJ += 1;
							if(compteurValJ == 2){						
								while(randInt==valueTestJ){								
									randInt=rand.nextInt(8);								
								}
							}
						}else{
							compteurValJ = 0;
							valueTestJ = randInt;
						}
					}
					
					/**
					 * Verifie si la valeur actuelle de randInt est identique à celle des deux lignes au dessus.
					 * Pour cela on a utilisé un tableau pour pouvoir comparer plus facilement les valeurs.
					 */
					
					if(k>15){
						if(randInt == stockValeur[k-8] && randInt == stockValeur[k-16]){
							randInt=rand.nextInt(8);
							}
					}
				System.out.print(randInt + " ");
				boutonGrid[i][j] = new JButton();
				tabInt[i][j] = randInt;
				stockValeur[k] = randInt;
				k+=1;
				}
			System.out.print("\n");
			
			
			}	
		}
/**
 * après la création des boutons dans creerBoutons(), initBoutons() 
 * associe les icones en fonction des valeurs du tableau d'entier tabInt
 *
 */
	public void initBoutons(){
		for(int j=0;j<TABSIZE;j++){
			for(int i = 0;i<TABSIZE;i++){
				boutonGrid[i][j].setIcon(getImageIcon(getTabIntNumb(i, j)));	
				pGame.add(boutonGrid[i][j]);
				pGame.setOpaque(false);
				boutonGrid[i][j].setBorder(BorderFactory.createEmptyBorder());
				boutonGrid[i][j].setOpaque(false);
				boutonGrid[i][j].setActionCommand(i + "" + j);
				boutonGrid[i][j].setContentAreaFilled(false);
				boutonGrid[i][j].addActionListener(controlbut);
			//	hideBoutons();
			}
		}
	}
	public void hideBoutons(){
		for(int j=0;j<TABSIZE;j++){
			for(int i = 0;i<TABSIZE;i++){
				boutonGrid[i][j].setVisible(false);
			//	boutonGrid[i][j].setDisabledIcon(getImageIcon(getTabIntNumb(i, j)));
			}
		}
	}
	public void showBoutons(){
		for(int j=0;j<TABSIZE;j++){
			for(int i = 0;i<TABSIZE;i++){
				boutonGrid[i][j].setVisible(true);
			}
		}
	}
	
	
/**
 *  Permet de réactualiser les icones (swap) en parcourant tabInt
 */
	public void updateTab(){
		for(int j=0;j<TABSIZE;j++){
			for(int i = 0;i<TABSIZE;i++){
				boutonGrid[i][j].setIcon(getImageIcon(getTabIntNumb(i, j)));	
				System.out.print(tabInt[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	/**
 * retourne une image en fonction de l'entier saisi
 * @param i
 * @return
 */
	public ImageIcon getImageIcon(int i){
		switch(i){
		case 0:
			return red;
		case 1:
			return pink;
		case 2:
			return orange;
		case 3:
			return yellow;
		case 4:
			return green;
		case 5:
			return blue;
		case 6:
			return purple;
		case 7:
			return grey;			
		}
		return null;
	}
	
	
	
	//------GETTERS -----//
	
	/**
	 * retourne l'indice passé dans la case du tableau initialisé
	 * @param i
	 * @param j
	 * @return
	 */
	public int getTabIntNumb(int i, int j){
		return tabInt[i][j];
	}
	
	public int getLevel(){
		return valueLevel;
	}
	
	public int getTries(){
		return valueTries;
	}
	
	public int getScore(){
		return valueScore;
	}

	
	//------SETTERS -----//

	public int setTries(){
		valueTries -=1;
		return valueTries;
	}
	public int setLevel(){
		valueLevel += 1;
		return valueLevel;
	}
	public int setScore(int i){
		valueScore += i;
		return valueScore;
	}

	
	
}
