package main;

public class Appli {

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Model model = new Model();
				Vue v  = new Vue(model); ;
				ControlMenu controlmenu = new ControlMenu(model,v);
				
			}
		});
	}
}
