package org.topraworld.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.ADialog;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;
import org.compiere.util.Trx;

//org.topraworld.ui.Cashier_SPC
public class Cashier_SPC implements FormPanel , ActionListener , KeyListener{
	 
	private FormFrame frame;
	
	private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JRadioButton rbnCard;
    private JRadioButton rbnCash;
    private JRadioButton rbnCheque;
    private JRadioButton rbnMix;
    private JScrollPane jScrollPane1;
    private JTable tblOrders;
    private JTextField txtDiscount;
    private JTextField txtDocNo;
    private JTextField txtGrossTotal;
    private JTextField txtNetAmount;
    private JButton btnClose;
    private JButton btnDelete;
    private JButton btnDown;
    private JButton btnPay;
    private JButton btnUp;
    private Trx trx;
    private Properties ctx;
	
	@Override
	public void init(int WindowNo, FormFrame frame) {
	
		this.frame = frame;
		trx = Trx.get(Trx.createTrxName("POS_Pay"), true);
		ctx = Env.getCtx();
		
		jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        
        jScrollPane1 = new javax.swing.JScrollPane();
        
        tblOrders = new javax.swing.JTable();
        
        txtDocNo = new javax.swing.JTextField();
        txtGrossTotal = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        txtNetAmount = new javax.swing.JTextField();
        txtDocNo.addActionListener(this);
        txtDocNo.addKeyListener(this);
        
        
        rbnCash = new javax.swing.JRadioButton();
        rbnCheque = new javax.swing.JRadioButton();
        rbnCard = new javax.swing.JRadioButton();
        rbnMix = new javax.swing.JRadioButton();
        
        rbnMix.setEnabled(false);
        
        rbnCash.addActionListener(this);
        rbnCheque.addActionListener(this);
        rbnCard.addActionListener(this);
        rbnMix.addActionListener(this);
        
        rbnCash.setSelected(true);
        
        btnDelete = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        
        btnDelete.addActionListener(this);
        btnDown.addActionListener(this);
        btnUp.addActionListener(this);
        
        btnClose = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        
        btnClose.addActionListener(this);
        btnPay.addActionListener(this);

        
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters"));

        jLabel1.setText("Document No");

        txtDocNo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDocNo.setForeground(new java.awt.Color(255, 0, 0));
        txtDocNo.setPreferredSize(new java.awt.Dimension(6, 40));
        txtDocNo.setActionCommand("DocNo");
        

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Payment Type");
        
        rbnCash.setText("Cash");
        rbnCheque.setText("Cheque");
        rbnCard.setText("Card");
        rbnMix.setText("Mix");
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDocNo, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbnCash)
                .addGap(36, 36, 36)
                .addComponent(rbnCard)
                .addGap(18, 18, 18)
                .addComponent(rbnMix)
                .addGap(26, 26, 26)
                .addComponent(rbnCheque)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtDocNo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rbnCash)
                .addComponent(rbnCheque)
                .addComponent(rbnCard)
                .addComponent(rbnMix))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Order Information"));

        tblOrders.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblOrders.setFont(new java.awt.Font("Tahoma", 0, 15));
        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] { },
            new String [] {
                "ID", "Business Partner", "Date Ordered", "Document No", "Net Amount", "Order Reference", "Document Status", "Payment Rule"
            }
        ) 	{
            Class[] types = new Class [] {
            	MOrder.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.setRowHeight(30);
        tblOrders.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblOrders.getTableHeader().setReorderingAllowed(false);
        //ID Colum
        tblOrders.getColumnModel().removeColumn(tblOrders.getColumnModel().getColumn(0));
        
        jScrollPane1.setViewportView(tblOrders);

        btnDelete.setText("Delete");

        btnDown.setText("Up");

        btnUp.setText("Down");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Gross Amount");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setPreferredSize(new java.awt.Dimension(41, 20));

        txtGrossTotal.setEditable(false);
        txtGrossTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtGrossTotal.setForeground(new java.awt.Color(255, 0, 0));
        txtGrossTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGrossTotal.setText("0.00");
        txtGrossTotal.setMinimumSize(new java.awt.Dimension(6, 40));
        txtGrossTotal.setPreferredSize(new java.awt.Dimension(3, 40));

        txtDiscount.setEditable(false);
        txtDiscount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDiscount.setForeground(new java.awt.Color(255, 0, 0));
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("0.00");
        txtDiscount.setPreferredSize(new java.awt.Dimension(3, 40));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Discount");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setPreferredSize(new java.awt.Dimension(41, 20));

        txtNetAmount.setEditable(false);
        txtNetAmount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNetAmount.setForeground(new java.awt.Color(255, 0, 0));
        txtNetAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNetAmount.setText("0.00");
        txtNetAmount.setPreferredSize(new java.awt.Dimension(3, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Net Amount");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setPreferredSize(new java.awt.Dimension(41, 20));

        btnClose.setText("Close");
        btnClose.setToolTipText("");
        
        btnPay.setText("Pay");
        btnPay.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGrossTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNetAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGrossTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.frame.getContentPane());
        this.frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        
        //Press F1 to pay
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0),"F1");
        btnPay.setToolTipText("Press F1");
        this.frame.getRootPane().getActionMap().put("F1",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {
            	btnPay.doClick();
            }
        });
      
        //Press F6 to new Order
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6,0),"F6");
        txtDocNo.setToolTipText("Press F6");
        this.frame.getRootPane().getActionMap().put("F6",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	txtDocNo.requestFocusInWindow();
            }
        });
        
        //Press escape to close
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"ESCAPE");
        btnClose.setToolTipText("Press Esc");
        this.frame.getRootPane().getActionMap().put("ESCAPE",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	btnClose.doClick();
            }
        });
        
        //Press Up
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"VK_UP");
        btnDown.setToolTipText("Press Up Arrorw");
        this.frame.getRootPane().getActionMap().put("VK_UP",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	btnDown.doClick();
            }
        });
        
        //Press down
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),"VK_DOWN");
        btnUp.setToolTipText("Press Down Arrorw");
        this.frame.getRootPane().getActionMap().put("VK_DOWN",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	btnUp.doClick();
            }
        });
        
        //Open Cash Drawer
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0),"VK_F3");
        this.frame.getRootPane().getActionMap().put("VK_F3",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	openCashDrawer();
            }
        });
        
        //Delete Lines
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl pressed D"), "Delete");
        btnDelete.setToolTipText("Press Ctl + D");
        this.frame.getRootPane().getActionMap().put("Delete",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	btnDelete.doClick();
            }
        });
        
        //Payment mode Cash
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl pressed N"), "Cash");
        rbnCash.setToolTipText("Press Ctl + N");
        this.frame.getRootPane().getActionMap().put("Cash",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	//rbnCash.setSelected(true);
            	rbnCash.doClick();
            }
        });
        
        //Payment mode Cheque
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl pressed Z"), "Cheque");
        rbnCheque.setToolTipText("Press Ctl + Z");
        this.frame.getRootPane().getActionMap().put("Cheque",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	//rbnCash.setSelected(true);
            	rbnCheque.doClick();
            }
        });
        
        //Payment mode Mix
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl pressed M"), "Miix");
        rbnMix.setToolTipText("Press Ctl + M");
        this.frame.getRootPane().getActionMap().put("Mix",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	rbnMix.doClick();
            }
        });
        
        //Payment mode Card
        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl pressed B"), "Mix");
        rbnCard.setToolTipText("Press Ctl + B");
        this.frame.getRootPane().getActionMap().put("Mix",new AbstractAction(){
            public void actionPerformed(ActionEvent ae)
            {	
            	rbnCard.doClick();
            }
        });
        
        this.frame.pack();
	}	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try{
			String action = e.getActionCommand();
			if (action == null || action.length() == 0)
				return;
			
			if(action.equals("DocNo") && this.txtDocNo.getText().length() > 0){
				this.addLine();
				return;
				
			}else if(action.equals("Pay")){
				
				if(tblOrders.getRowCount() > 0){
					JDialog dialog = new PayRule(this);
					dialog.setVisible(true);
					//get focus to document no
					this.getFocus(txtDocNo);
					
				}else{
					ADialog.error(0, this.frame, "No Order Lines.");
				}
				
			}else if(action.equals("Close")){
				
				this.frame.dispose();
			}else if(action.equals("Up")){
				
				int i = tblOrders.getSelectionModel().getMaxSelectionIndex();
				if(i == -1)
					tblOrders.getSelectionModel().setSelectionInterval(0, 0);
				else
					tblOrders.getSelectionModel().setSelectionInterval(i - 1, i -1);
				
			}else if(action.equals("Down")){
				
				int i = tblOrders.getSelectionModel().getMaxSelectionIndex();
				if(i == -1)
					tblOrders.getSelectionModel().setSelectionInterval(tblOrders.getRowCount() -1, tblOrders.getRowCount() -1);
				else if(i < tblOrders.getRowCount() -1)
					tblOrders.getSelectionModel().setSelectionInterval(i +1, i +1);
				
			}else if(action.equals("Delete") && tblOrders.getRowCount() > 0){
				if(tblOrders.getSelectedRow() > -1){
					((DefaultTableModel)tblOrders.getModel()).removeRow(tblOrders.getSelectedRow());
					this.setSum();
				}
			}else if(action.equals("Cash")){
				rbnCash.setSelected(true);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(false);
				
			}else if(action.equals("Card")){
				rbnCash.setSelected(false);
				rbnCard.setSelected(true);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(false);
				
			}else if(action.equals("Mix")){
				
				rbnCash.setSelected(false);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(true);
			}else if(action.equals("Cheque")){
				rbnCash.setSelected(false);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(true);
				rbnMix.setSelected(false);
			}
			
		}catch(Exception ex){
			ADialog.warn(0,this.frame, ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void addLine(){
		
		boolean isReturn = false;
		String DOCUMENTNO = txtDocNo.getText().trim();
		int  ids[] = MOrder.getAllIDs("C_ORDER", "DOCUMENTNO = '"+ DOCUMENTNO +"' AND DOCSTATUS = 'DR'", trx.getTrxName());
		
		if(ids.length > 0){
			MOrder mOrder = new MOrder(ctx, ids[0], trx.getTrxName());
			//Check whether order is type return
			if(mOrder.getC_DocTypeTarget_ID() == 1000050)
				isReturn = true;
			//POS Order
			if(!isReturn)
				this.createTableLine(mOrder);
			//Return
			else{
				//Manager Aproval
				if(mOrder.get_ValueAsBoolean("wf_returnaproval")){
					this.createTableLine(mOrder);
				}else{
					ADialog.error(0,this.frame, "MANAGER APROVAL REQUIRED!" , "It is required manager aproval to process this order!");
				}
			}
			
		}else{
			ADialog.warn(0,this.frame, "Invalid Document No!");
			txtDocNo.setText("");
		}
	}
	
	private void createTableLine(MOrder mOrder){
		
		//validate for already added and same payment rule for many order
		boolean isAdded = false;
		boolean isValidPayRule = false;
		
		DefaultTableModel dtm = (DefaultTableModel) tblOrders.getModel();
	    for (int i = 0 ; i < dtm.getRowCount() ; i++){
	    	isAdded = ((MOrder) dtm.getValueAt(i,0)).get_ID() == mOrder.get_ID();
	    	if(isAdded){
	    		ADialog.warn(0,this.frame, "Already Added!");
	    		txtDocNo.setText("");
	    		return;
	    	}else{
	    		//Same payment rule
	    		isValidPayRule = ((MOrder) dtm.getValueAt(i,0)).getPaymentRule().equals(mOrder.getPaymentRule());
	    		if(!isValidPayRule){
	    			ADialog.warn(0,this.frame, "Your are not allowed to add orders with different payment rules!");
		    		return;
	    		}
	    	}
	    }
		
	    if(!(isAdded && isValidPayRule)){
	    	
	    	String paymentRule = (mOrder.getPaymentRule().equals("B"))?"Cash": (mOrder.getPaymentRule().equals("K"))?"Card":(mOrder.getPaymentRule().equals("S"))?"Check":(mOrder.getPaymentRule().equals("M"))?"Mixed":"";
	    	Object[] row = { mOrder, mOrder.getBill_BPartner().getName(), mOrder.getCreated(), mOrder.getDocumentNo() , 
					mOrder.getGrandTotal() , mOrder.getPOReference() , mOrder.getDocStatusName() , paymentRule};
		    DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
		    model.addRow(row);
		    txtDocNo.setText("");
		    this.setSum();
		    this.setPaymentRule(mOrder);
	    }
	}
	
	private void setSum(){
		
		BigDecimal gt = new BigDecimal("0.00");
		BigDecimal dis = new BigDecimal("0.00");
		BigDecimal nt = new BigDecimal("0.00");
		
		DefaultTableModel dtm = (DefaultTableModel) tblOrders.getModel();
		//Loop through orders
	    for (int i = 0 ; i < dtm.getRowCount() ; i++){
	    	MOrder mOrder = (MOrder) dtm.getValueAt(i,0);
	    	//loop through each order lines
	    	MOrderLine[] lines = mOrder.getLines();
	    	for(MOrderLine mOrderLine : lines){
	    		gt = gt.add(mOrderLine.getPriceList().multiply(mOrderLine.getQtyEntered()));
	    		nt = nt.add(mOrderLine.getPriceActual().multiply(mOrderLine.getQtyEntered()));
	    	}
	    	gt =  gt.setScale(2, RoundingMode.HALF_DOWN);
	    	nt =  nt.setScale(2, RoundingMode.HALF_DOWN);
	    	dis = gt.subtract(nt);
	    }
	    
	    txtGrossTotal.setText(gt + "");
    	txtDiscount.setText(dis + "");
    	txtNetAmount.setText(nt + "");
    	
    	//Scoll to last added row
    	this.setLastScoll();
	}
	
	private void setLastScoll(){
		tblOrders.getSelectionModel().setSelectionInterval(tblOrders.getRowCount() -1, tblOrders.getRowCount() -1);
	}
	
	private void setPaymentRule(MOrder mOrder){
		
		String pr = mOrder.getPaymentRule();
		switch(pr){
			case  "B" :
				rbnCash.setSelected(true);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(false);
				break;
				
			case  "K" :
				rbnCash.setSelected(false);
				rbnCard.setSelected(true);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(false);
				break;
				
			case  "M" :
				
				rbnCash.setSelected(false);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(false);
				rbnMix.setSelected(true);
				break;
				
			case  "S" :
				rbnCash.setSelected(false);
				rbnCard.setSelected(false);
				rbnCheque.setSelected(true);
				rbnMix.setSelected(false);
				break;
		}
	}
	
	public void clear(){
		txtDiscount.setText("0.00");
		txtDocNo.setText("");
		txtGrossTotal.setText("0.00");
		txtNetAmount.setText("0.00");
		this.setDefaultPayRule();
		//clear table
		DefaultTableModel dtm = (DefaultTableModel) tblOrders.getModel();
		dtm.setRowCount(0);
	}
	
	public FormFrame getFrame() {
		return frame;
	}
	
	public JTable getOrderTable() {
		return this.tblOrders;
	}
	
	public String getPaymentRule(){
		return (rbnCash.isSelected())?"B":(rbnCard.isSelected()?"K":(rbnCheque.isSelected())?"S":"M");
	}
	
	public void setDefaultPayRule(){
		
		rbnCash.setSelected(true);
		rbnCard.setSelected(false);
		rbnCheque.setSelected(false);
		rbnMix.setSelected(false);
	}    
	
	public void openCashDrawer(){
	
		System.out.println("Cash Drawer Opened!");
	}
	
	public boolean printReciept(MOrder m_order){
		return ReportCtl.startDocumentPrint(ReportEngine.ORDER, m_order.getC_Order_ID(), null, Env.getWindowNo(this.frame), true);
	}
	
	public String getTotal(){
		return txtNetAmount.getText();
	}
	
	private void getFocus(final Component cmp){
    	this.frame.addWindowListener( new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	cmp.requestFocus();
            }
        });
    }
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) { }
	
	//This is for the barcode reader
	BigDecimal start = null;
	@Override
	public void keyReleased(KeyEvent e) {		
		if(txtDocNo.getText().length() == 1)
	          start = new BigDecimal(System.currentTimeMillis());
	    else if(txtDocNo.getText().length() == 6){
	    	if(start == null) return;	
          BigDecimal diff = new BigDecimal(System.currentTimeMillis()).subtract(start);
          if(diff.intValue() < 30){
              this.addLine();
          }
	    }
	}
	
	private boolean isValidPayRule(){
		
		boolean isvalid = false;
		//Validation is done based on the Order payment rule and the discount
		//Cash to Card is valid any time if no discounts
		
		
		
		
		return isvalid;
	}
}