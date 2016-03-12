package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class Vue extends JFrame{
	
	protected Model m; 
	
//Declaration de toutes les variables
	
	boolean start=true;
	protected JMenuBar barreMenu ;
	protected JMenu jmOption;
	protected JMenuItem itemRestart, itemScore,itemCredit;
	protected JLabel jlLevel = new JLabel("Level : ");
	protected JLabel jlTries = new JLabel("Tries : "); 
	protected JLabel jlScore = new JLabel("Score : ");	
	protected JLabel jlvalueLevel = new JLabel();
	protected JLabel jlvalueTries = new JLabel();
	protected JLabel jlvalueScore = new JLabel();
	final ImageIcon bgImage = new ImageIcon("src/res/colorfulBg2.jpg");
	protected JLabel mainPanel = new JLabel(bgImage);	
	protected boolean timerscore = true;
	public JProgressBar xp;
	protected ControlBouton controlbut = new ControlBouton(m,this);
	//protected ControlMenu controlmenu = new ControlMenu(m, this);
	int k = 0;
	final static int defaultValue=50;
	static final int BAR_MAX = 100;
	static final int BAR_MIN = 0;
	protected Timer t;
	protected Timer t_score;
	protected ControlBouton cb;
	
//	JPanel pGame = new JPanel(new GridLayout(8,8,5,5));
	
	public Vue(Model m){

		this.m = m;
		initAttribut();	
		creerWidgets();
		creerMenu();
		//setTitle("Bejould very hard for a loser :(");
		setTitle("Bejeweled");
		add(mainPanel);
		setSize(700,700);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		pack();
		t.start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void initAttribut(){
		
		jlvalueLevel.setText(Integer.toString(m.getLevel())); 
		jlvalueTries.setText(Integer.toString(m.getTries())); 
		jlvalueScore.setText(Integer.toString(m.getScore()));

	}
	
	public void updateInfos(){
		t_score = new Timer(500, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jlvalueScore.setText(Integer.toString(m.getScore()));
				if(m.getTries()<0){
					timerscore = false;
					controlbut.showMessage();
					t_score.stop();
					t.stop();
					//break;
				}else{
					jlvalueTries.setText(Integer.toString(m.getTries()));
				}
			}
		});
		if(timerscore){
			t_score.start();
		}
	}
	
	public void creerProgress(){
		
		xp = new JProgressBar();
		Dimension size = xp.getPreferredSize();
		size.width = 300;
		xp.setValue(defaultValue);
		xp.setStringPainted(true);
		xp.setPreferredSize(size);	
	
	//barre de progression à modif ici
		
		t = new Timer(3000, new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
	
					xp.setValue((xp.getValue()-1)%100);

					
					if(xp.getValue()==0){
						System.out.println("END OF GAME");
						m.hideBoutons();
					}
					
					if(xp.getValue() == 100){
						System.out.println("GG, NEW LEVEL");
						m.pGame.removeAll();
						mainPanel.removeAll();
						m.setTries();
						System.out.println(m.getTries());
						initAttribut();
						creerWidgets();
						setVisible(true);
					}
				}
			
		});
		updateInfos();
	
	}
	
	public void stopProgress(){
		start = false;
	}
	
	//= creerVue
	public void creerWidgets(){
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		//Level, Tries, Score		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5,5,5,5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		mainPanel.add(jlLevel, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		gbc.insets = new Insets(5,5,5,5);
		mainPanel.add(jlvalueLevel, gbc);
		
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		gbc.insets = new Insets(5,20,5,5);
		mainPanel.add(jlTries, gbc);
		
		gbc.gridx = 3;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		gbc.insets = new Insets(5,5,5,5);
		mainPanel.add(jlvalueTries, gbc);
		
		gbc.gridx = 4;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		gbc.insets = new Insets(5,20,5,5);
		mainPanel.add(jlScore, gbc);
		
		gbc.gridx = 5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weighty = 0.15;
		gbc.weightx = 0.16;
		gbc.insets = new Insets(5,5,5,5);
		mainPanel.add(jlvalueScore, gbc);
		
		// panel pGame
		
		m.creerBoutons();
		m.initBoutons();

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.gridheight=4;
		gbc.weighty = 0.7;
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(30,15,20,10);
		mainPanel.add(m.pGame, gbc);
		
		// ProgressBar
		creerProgress();
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.weighty = 0.2;
		gbc.weightx = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.PAGE_END;
		
		mainPanel.add(xp,gbc);
		
	}
	
	/**
	 * teste horizontalement si les 2 valeurs précédentes ont déjà été saisies 
	 */
	public void creerMenu(){
		
		barreMenu = new JMenuBar();
		jmOption = new JMenu("Options");
		
		itemRestart = new JMenuItem("Nouvelle partie");
		itemScore = new JMenuItem("Meilleurs scores");
		itemCredit = new JMenuItem("Credits");
		
		jmOption.add(itemRestart);
		jmOption.add(itemScore);
		jmOption.add(itemCredit);
		
		barreMenu.add(jmOption);
		setJMenuBar(barreMenu);
	}
	
	public void setControlMenu(ControlMenu cm ){
		itemRestart.addActionListener(cm);
		itemScore.addActionListener(cm);
		itemCredit.addActionListener(cm);
	}
	
}





		
