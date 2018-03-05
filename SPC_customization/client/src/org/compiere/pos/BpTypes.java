package org.compiere.pos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BpTypes extends javax.swing.JDialog implements ActionListener{
	
	private javax.swing.JButton btnChild;
    private javax.swing.JButton btnGen;
	private javax.swing.JButton btnPrg;
	private javax.swing.JButton btnSen;
    private javax.swing.JButton btnSpc;
    private javax.swing.JPanel jPanel1;
    private int bp_id = 0;
    private String[] c_bparterTypes;
    private PosBasePanel parent;
    
    public BpTypes(PosBasePanel parent , String[] c_bparterTypes) {
        super(parent.getCFrame(), true);
        this.c_bparterTypes = c_bparterTypes;
        this.parent = parent;
        initComponents();
        this.setLocationRelativeTo(null);   
    }
	
	private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGen = new javax.swing.JButton();
        btnSpc = new javax.swing.JButton();
        btnChild = new javax.swing.JButton();
        btnPrg = new javax.swing.JButton();
        btnSen = new javax.swing.JButton();
        
        btnGen.setEnabled(false);
        btnSpc.setEnabled(false);
        btnChild.setEnabled(false);
        btnSen.setEnabled(false);
        btnPrg.setEnabled(false);
        
        btnGen.addActionListener(this);
        btnSpc.addActionListener(this);
        btnChild.addActionListener(this);
        btnSen.addActionListener(this);
        btnPrg.addActionListener(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Select Business Partner Type");
        setAlwaysOnTop(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnGen.setBackground(new java.awt.Color(0, 255, 204));
        btnGen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGen.setForeground(new java.awt.Color(255, 0, 0));
        btnGen.setText("GENERAL");
        btnGen.setPreferredSize(new java.awt.Dimension(111, 25));

        btnSpc.setBackground(new java.awt.Color(0, 255, 204));
        btnSpc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSpc.setForeground(new java.awt.Color(255, 0, 0));
        btnSpc.setText("SPECIAL");
        btnSpc.setPreferredSize(new java.awt.Dimension(111, 25));

        btnChild.setBackground(new java.awt.Color(0, 255, 204));
        btnChild.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChild.setForeground(new java.awt.Color(255, 0, 0));
        btnChild.setText("CHILD");
        btnChild.setPreferredSize(new java.awt.Dimension(111, 25));

        btnPrg.setBackground(new java.awt.Color(0, 255, 204));
        btnPrg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrg.setForeground(new java.awt.Color(255, 0, 0));
        btnPrg.setText("PREGNANT");

        btnSen.setBackground(new java.awt.Color(0, 255, 204));
        btnSen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSen.setForeground(new java.awt.Color(255, 0, 0));
        btnSen.setText("SENIOR");
        btnSen.setPreferredSize(new java.awt.Dimension(111, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSpc, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btnChild, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btnPrg, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btnGen, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btnSen, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(null);
        
        //Validate BP groups
        for(String type : c_bparterTypes){
        	
        	if(type.equals("GENERAL")){
        		btnGen.setEnabled(true);
        	}else if(type.equals("SPECIAL")){
        		btnSpc.setEnabled(true);
        	}else if(type.equals("PREGNANT")){
        		btnPrg.setEnabled(true);
        	}else if(type.equals("CHILD")){
        		btnChild.setEnabled(true);
        	}else if(type.equals("SENIOR")){
        		btnSen.setEnabled(true);
        	}
        }
        
        pack();
        
        //set default focus to general
        btnGen.requestFocusInWindow();
        rootPane.setDefaultButton(btnGen);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action =  e.getActionCommand();
		if(e == null || action.length() == 0)
			return;
		if(action.equals("GENERAL")){
	    	this.bp_id = this.parent.p_pos.get_ValueAsInt("BPGENARAL");
	    }else if(action.equals("SPECIAL")){
	    	this.bp_id = this.parent.p_pos.get_ValueAsInt("BPSPECIAL");
	    }else if(action.equals("PREGNANT")){
	    	this.bp_id = this.parent.p_pos.get_ValueAsInt("BPPREGNANT");
	    }else if(action.equals("CHILD")){
	    	this.bp_id = this.parent.p_pos.get_ValueAsInt("BPCHILD");
	    }else if(action.equals("SENIOR")){
	    	this.bp_id = this.parent.p_pos.get_ValueAsInt("BPSCITIZEN");
	    }else{
	    	return;
	    }
		
		this.parent.f_order.setBp_id(this.bp_id);
		this.parent.f_order.getBpartner().setText(action);
		this.dispose();
	}
}
