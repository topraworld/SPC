package org.compiere.pos;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BpTypes implements ActionListener{
	
    public void show(){
    	
    	JPanel jPanel = new JPanel();
        JButton a = new JButton("General");
        JButton b = new JButton("Special");
        JButton c = new JButton("Child");
        JButton d = new JButton("Pregnant");
        JButton e = new JButton("Senior");
        
        JDialog dialog = new JDialog();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) ((screenSize.getWidth() - 650)/2);
        int h = (int) ((screenSize.getHeight() - 170)/2);
        dialog.setLocation(w ,h);
        
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false);
        dialog.requestFocus();
        
        dialog.setTitle("Please select a customer category");
        dialog.setSize(new Dimension(650, 170));
        
        dialog.getContentPane().add(jPanel);
        dialog.setVisible(true);
        
        jPanel.add(a);
        a.setPreferredSize(new Dimension(120, 120));
        a.setFont(new Font("Arial", Font.PLAIN, 18));
        jPanel.add(b);
   	 	b.setPreferredSize(new Dimension(120, 120));
   	 	b.setFont(new Font("Arial", Font.PLAIN, 18));
   	 	jPanel.add(c);
   	 	c.setPreferredSize(new Dimension(120, 120));
   	 	c.setFont(new Font("Arial", Font.PLAIN, 18));
   	 	jPanel.add(d);
   	 	d.setPreferredSize(new Dimension(120, 120));
   	 	d.setFont(new Font("Arial", Font.PLAIN, 18));
   	 	jPanel.add(e);
   	 	e.setPreferredSize(new Dimension(120, 120));
   	 	e.setFont(new Font("Arial", Font.PLAIN, 18));
   	 	
   	 	a.addActionListener(this);
   	 	b.addActionListener(this);
   	 	c.addActionListener(this);
   	 	d.addActionListener(this);
   	 	e.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}