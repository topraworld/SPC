package org.compiere.pos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Letter_KeyBoard
{
    private JTextField txt;
    private PopUpKeyboard keyboard;
    private SubOrder subOrder;
    private SubCurrentLine subCurrentLine;
    
    //use for sub order    
    public Letter_KeyBoard(JTextField ref , SubOrder subOrder)
    {   
        this.txt = ref;
        this.subOrder = subOrder;
		this.keyboard = new PopUpKeyboard(txt);
        
        txt.addMouseListener(new MouseAdapter()
        {   
			@Override
            public void mouseClicked(MouseEvent e)
            {
            	Point p = txt.getLocationOnScreen();
                p.y += 30;
                keyboard.setLocation(p);
                keyboard.setVisible(Letter_KeyBoard.this.txt.isEditable());
                
            }
        });   
    }
    
    public Letter_KeyBoard(JTextField ref , SubCurrentLine scl)
    {   
        this.txt = ref;
        this.subCurrentLine = scl;
		this.keyboard = new PopUpKeyboard(txt);
        
        txt.addMouseListener(new MouseAdapter()
        {   
			@Override
            public void mouseClicked(MouseEvent e)
            {
            	Point p = txt.getLocationOnScreen();
                p.y += 30;
                keyboard.setLocation(p);
                keyboard.setVisible(Letter_KeyBoard.this.txt.isEditable());
                //keyboard.show();
            }
        });   
    }
    
    private class PopUpKeyboard extends JDialog implements ActionListener
    {	
        private JTextField txt;

        String [] alpha1 = {"Q" , "W" , "E" , "R" ,"T" , "Y" , "U" , "I" , "O" , "P"};
        String [] alpha2 = {"A" , "S" , "D" , "F" ,"G" , "H" , "J" , "K" , "L" , "OK"};
        String [] alpha3 = {"Z" , "X" , "C" , "V" ,"B" , "N" , "M" , "." , " " , "DL"};
        String [] numeric = {"0" , "1" , "2" , "3" ,"4" , "5" , "6" , "7" , "8" , "9"};
        
        public PopUpKeyboard(JTextField txt)
        {
        	
            this.txt = txt;
            setLayout(new GridLayout(0, 10));
            
            for(String i:numeric){
            	createButton(i);
            }
            
            for(String i:alpha1){
            	createButton(i);
            }
            for(String i:alpha2){
            	if(i.length() == 2)
            		createButton(i , "");
            	else
            		createButton(i);
            }
            for(String i:alpha3){
            	
            	if(i.length() == 2)
            		createButton(i , "");
            	else
            		createButton(i);
            }
            pack();
        }

        private void createButton(String label)
        {
            JButton btn = new JButton(label);
            btn.setForeground(Color.BLUE);
            this.createButton(btn);
        }
        
        private void createButton(JButton btn)
        {
            
            btn.addActionListener(this);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(75, 75));
            Font font = btn.getFont();
            float size = font.getSize() + 15.0f;
            btn.setFont(font.deriveFont(size));
            add(btn);
        }
        
        private void createButton(String label , String color)
        {
            JButton btn = new JButton(label);
            btn.setBackground(new Color(204, 216, 255));
            btn.setForeground(Color.RED);
            this.createButton(btn);
        }

        @SuppressWarnings("deprecation")
		@Override
        public void actionPerformed(ActionEvent e)
        {	
            String actionCommand = e.getActionCommand();
            
            if(actionCommand ==null || actionCommand.length() < 0)
            	return;
            
            //clear whole text
            if(actionCommand.equals("CL")){
            	txt.setText("");
            }else if(actionCommand.equals("OK")){
            	//save the reference
            	if(txt.getName().equals("Reference")){
            		
            		Letter_KeyBoard.this.subOrder.setReference(txt.getText());
            		this.setVisible(false);
            		this.dispose();
            		System.gc();
            		
            	}else if(txt.getName().equals("Name")){
            		
                		this.setVisible(false);
                		this.dispose();
                		System.gc();
                		
                		Letter_KeyBoard.this.subCurrentLine.findProduct();
                		Letter_KeyBoard.this.subCurrentLine.actionPerformed(e);
            	}
            	
            }else if(actionCommand.equals("EX")){
            	this.hide();
            	
            }else if(actionCommand.equals("DL")){
            	if(!txt.getText().equals(""))
            	txt.setText(txt.getText().substring(0, txt.getText().length()-1));
            }else{
            	txt.setText(txt.getText() + actionCommand);
            }
        }
    }
}