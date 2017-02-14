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
public class Numeric_KeyBoard
{
    private JTextField txt;
    private PopUpKeyboard keyboard;
    private SubCurrentLine subCurrentLine;

    public Numeric_KeyBoard(JTextField ref , SubCurrentLine scl)
    {
    	txt = ref;
        keyboard = new PopUpKeyboard(txt);
        subCurrentLine = scl;
        
        txt.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
            	Point p = txt.getLocationOnScreen();
                p.y -= 600;
                p.x = 500;
                keyboard.setLocation(p);
                keyboard.setVisible(Numeric_KeyBoard.this.txt.isEnabled());
            }
        });
    }

    private class PopUpKeyboard extends JDialog implements ActionListener
    {
    	
        private JTextField txt;        
        String [] numeric = {"7" , "8" , "9" , "15" ,"4" , "5" , "6" , "30" , "1" , "2", "3", "60"};
        String [] numeric2 = {"0" , "-" , "." ,"CL" , "10" , "20" , "40" , "OK"};
        
        public PopUpKeyboard(JTextField txt)
        {
        	this.txt = txt;
            setLayout(new GridLayout(0, 4));
            
            for(String i:numeric){
            	if(i.length() == 2){
            		createButton(i , "");
            	}else{
            		createButton(i);
            	}
            }
            for(String i:numeric2){
            	
            	if(i.length() == 2){
            		createButton(i , "");
            	}else{
            		createButton(i);
            	}
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
            btn.setPreferredSize(new Dimension(90, 90));
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

        @Override
        public void actionPerformed(ActionEvent e)
        {	
            String actionCommand = e.getActionCommand();
            if(actionCommand.equals("OK")){
            	
            	Numeric_KeyBoard.this.subCurrentLine.setQty();
            	this.setVisible(false);
        		this.dispose();
            	
            }else if(actionCommand.equals("CL")){
            	txt.setText("");
            }else if(actionCommand.equals(".")){
            	
            	txt.setText(txt.getText() + actionCommand);
            	
            }else if(actionCommand.equals("-")){
            	txt.setText(actionCommand);
            }else if(actionCommand.length() == 2){
            	
            	if(txt.getText().endsWith("-")){
            		txt.setText("-"+actionCommand);
            	}else{
            		txt.setText(actionCommand);
            	}
            	
            }else{
            	txt.setText(txt.getText() + actionCommand);
            }
        }
    }
}