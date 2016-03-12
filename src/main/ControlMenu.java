package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ControlMenu implements ActionListener{
	
	public static JOptionPane credit;
	public final String scorePath = "src/res/Scores.txt";
	
	private Model m;
	private Vue v;
	public JLabel scores;
	private String[] bestscore = new String[9];
	
	public ControlMenu(Model m,Vue v){
	 	 this.m=m;
	 	 this.v = v;
	 	 v.setControlMenu(this);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==v.itemRestart){
			//clear the game view
			m.pGame.removeAll();
			v.mainPanel.removeAll();
			m.setTries();
			System.out.println(m.getTries());
			v.initAttribut();
			v.creerWidgets();
			v.setVisible(true);
			
			System.out.println("Nouvelle grille");
			
		}else if(e.getSource()==v.itemScore){
			//v.stopProgress();
			int cpt = 1;
			String s;
			try{
				BufferedReader br = new BufferedReader(new FileReader(scorePath));
				while((s=br.readLine())!= null){
					bestscore[cpt] = s;
					cpt+=1;
				}
				br.close();
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(
                    v,"Best scores saved \n \n" + bestscore[1] + bestscore[2] + "\n \n"
                    + bestscore[3] + bestscore[4] +"\n \n"
                    + bestscore[5] + bestscore[6],
                    "BEST SCORES", JOptionPane.INFORMATION_MESSAGE);
			
			System.out.println("Best scores");
			
			
		}
		else if(e.getSource()==v.itemCredit){
			//v.stopProgress();
			credit = new JOptionPane();
			JOptionPane.showMessageDialog(v, "Made by : \n \n NGUYEN Lan-Lynn \n S2A1 " , "Credits", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Credits");
		}
	}
	
	
}
