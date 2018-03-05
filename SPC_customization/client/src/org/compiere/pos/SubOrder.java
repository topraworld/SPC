package org.compiere.pos;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.X_C_Order;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

public class SubOrder extends PosSubPanel implements ActionListener, FocusListener
{	
	private static final long serialVersionUID = 5895558315889871887L;

	public SubOrder (PosBasePanel posPanel)
	{
		super (posPanel);
	}	//	PosSubCustomer
	
	private CButton 		f_history;
	private	CTextField		f_name;
	private CButton 		f_bNew;
	private CButton 		f_bCredit;
	private CComboBox		f_location;
	private CComboBox		f_user;
	private CButton 		f_cashPayment;
	private CButton 		f_process;
	private CButton 		f_print2;
	private CTextField 		f_DocumentNo;
	private CButton 		f_logout;
	private JFormattedTextField f_tax;
	private JFormattedTextField f_total;
	private CButton 		bpartner;

	private CTextField discount;
	private MBPartner	m_bpartner;
	private int			m_M_PriceList_Version_ID = 0;
	private CTextField f_currency = new CTextField();
	private CButton f_breturn;
	JTextField reference;
	private CTextField order_type;
	private final String[] c_bparterTypes1 = { "GENERAL", "SPECIAL", "PREGNANT", "CHILD" , "SENIOR"};
	private final String[] c_bparterTypes2 = { "SPECIAL", "PREGNANT", "CHILD" , "SENIOR"};
	private static CLogger log = CLogger.getCLogger(SubOrder.class);
	boolean isReturn = false;
	private int bp_id = 0;

	public void init()
	{	
		//	Content
		MigLayout layout = new MigLayout("ins 0 0","[fill|fill|fill|fill]","[nogrid]unrel[||]");
		setLayout(layout);
		
		Font bigFont = AdempierePLAF.getFont_Field().deriveFont(16f);

		String buttonSize = "w 50!, h 50!,";
		// NEW
		f_bNew = createButtonAction("New", KeyStroke.getKeyStroke(KeyEvent.VK_F2, Event.F2));
		add (f_bNew, buttonSize);
		
		f_bCredit = createButtonAction("InfoBPartner", KeyStroke.getKeyStroke(KeyEvent.VK_F2, Event.F3));
		add (f_bCredit, buttonSize);
		f_bCredit.setActionCommand("Credit");
		f_bCredit.setToolTipText("Card Payments");
	
		// EDIT
		f_breturn = createButtonAction("Reset", null);
		add(f_breturn, buttonSize);
		f_breturn.setToolTipText("Customer Return");
		
		// HISTORY
		f_history = createButtonAction("History", null);
 		add (f_history, buttonSize); 
		
		// CANCEL
		f_process = createButtonAction("Cancel", null);
 		add (f_process, buttonSize);
 		f_process.setEnabled(false);
 		
 		// PAYMENT
 		f_cashPayment = createButtonAction("Payment", null);
		f_cashPayment.setActionCommand("Cash");
		add (f_cashPayment, buttonSize); 
		f_cashPayment.setEnabled(false);
		
 		//Topra customization
 		f_print2 = createButtonAction("Print", null);
 		add (f_print2, buttonSize);
		//
		f_logout = createButtonAction ("Logout", null);
		add (f_logout, buttonSize + ", gapx 25, wrap");
		
 		// DOC NO
		add (new CLabel(Msg.getMsg(Env.getCtx(),"DocumentNo")), "");
		f_DocumentNo = new CTextField("");
		f_DocumentNo.setName("DocumentNo");
		f_DocumentNo.setEditable(false);
		f_DocumentNo.setFont(bigFont);
		f_DocumentNo.setForeground(Color.BLACK);
		add (f_DocumentNo, "wrap, growx, pushx");
		
		
		CLabel lTax = new CLabel (Msg.translate(Env.getCtx(), "TaxAmt"));
		add(lTax);
		f_tax = new JFormattedTextField(DisplayType.getNumberFormat(DisplayType.Amount));
		f_tax.setHorizontalAlignment(JTextField.TRAILING);
		f_tax.setEditable(false);
		f_tax.setFocusable(false);
		lTax.setLabelFor(f_tax);
		add(f_tax, "growx, pushx");
		f_tax.setValue (Env.ZERO);
		
		add(new CLabel(Msg.translate(Env.getCtx(), "Reference")), "");
		reference =  new JTextField(20);
		reference.setEditable(false);
		reference.setName("Reference");
		//lNet.setLabelFor(f_net);
		add(reference, "wrap, growx, pushx");
		reference.addActionListener(this);
		reference.setActionCommand("Reference");
		
		//set key board
		SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
               new Letter_KeyBoard(reference , SubOrder.this);
            }
        });
		
		add(new CLabel(Msg.translate(Env.getCtx(), "Discount")), "");
		discount = new CTextField("0.00");
		discount.setEditable(false);
		discount.setName("Discount");
		discount.setHorizontalAlignment(SwingConstants.RIGHT);
		add(discount, "growx, pushx");
		
		add(new CLabel(Msg.translate(Env.getCtx(), "Customer ")), "");
		bpartner = new CButton(" General ");
		add (bpartner, "wrap, growx, pushx");
		bpartner.setActionCommand("bpartner");
		bpartner.addActionListener(this);
		bpartner.setForeground(Color.BLUE);
		bpartner.setFont(bigFont);
		
		
		f_name = new CTextField();
		f_name.setEditable(true);
		f_name.setName("Name");
		f_name.setVisible(false);

		CLabel lTotal = new CLabel (Msg.translate(Env.getCtx(), "GrandTotal"));
		lTotal.setFont(bigFont);
		add(lTotal, "");
		f_total = new JFormattedTextField(DisplayType.getNumberFormat(DisplayType.Amount));
		f_total.setHorizontalAlignment(JTextField.TRAILING);
		f_total.setFont(bigFont);
		f_total.setEditable(false);
		f_total.setFocusable(false);
		lTotal.setLabelFor(f_total);
		f_total.setValue (Env.ZERO);
		add(f_total, "growx, pushx");
		
		add(new CLabel(Msg.translate(Env.getCtx(), "Order Type")), "");
		order_type = new CTextField("Customer Order");
		order_type.setFont(bigFont);
		order_type.setEditable(false);
		order_type.setForeground(Color.BLUE);
		add (order_type, "wrap, growx, pushx");
	}
	
	public void dispose()
	{
		if (f_name != null)
			f_name.removeFocusListener(this);
		f_name = null;
		removeAll();
		super.dispose();
		
		
		
	}	//	dispose

	@Override
	public void actionPerformed (final ActionEvent e)
	{	
		try{
			String action = e.getActionCommand();
			if (action == null || action.length() == 0)
				return;
			//	New
			if (action.equals("New"))
			{	
				new BpTypes(this.p_posPanel , c_bparterTypes1).show();
				order_type.setText("Customer Cash Order");
				order_type.setForeground(Color.BLUE);
				p_posPanel.newOrder("O" ,  bp_id);
				isReturn = false;
				return;
			}else if(action.equals("Credit")){
				
				new BpTypes(this.p_posPanel , c_bparterTypes2).show();
				order_type.setText("Card Payment Order");
				order_type.setForeground(Color.BLUE);
				p_posPanel.newOrder("Cr" ,  bp_id);
				isReturn = false;
				return;
			}
			
			if (action.equals("History"))
			{
				PosQuery qt = new QueryTicket(p_posPanel);
				qt.setVisible(true);
				return;
			}
			else if (action.equals("Cancel")){
				deleteOrder();
				return ;
			}	
			else if (action.equals("Print")){
				printOrder();
			}else if (action.equals("Logout"))
			{	
				
				p_posPanel.minimumSize();
				return;
			//for customer return type	
			}else if(action.equals("Reset")){
				new BpTypes(this.p_posPanel , c_bparterTypes1).show();
				order_type.setText("Customer Return");
				order_type.setForeground(Color.RED);
				p_posPanel.newOrder("R" , bp_id);
				isReturn = true;
			}else if(action.equals("bpartner")){
				
				if(p_posPanel.m_order != null){
					//check for card type or not using price list
					if(p_posPanel.m_order.getM_PriceList_ID() == 1016582){
						new BpTypes(this.p_posPanel , c_bparterTypes1).show();
					}else{
						new BpTypes(this.p_posPanel , c_bparterTypes1).show();
					}
					
					p_posPanel.m_order.setC_BPartner_ID(bp_id);
					MBPartner mbPartner = MBPartner.get(p_ctx, bp_id);
					//set f_order info to p_pos
					p_posPanel.f_order.setM_PriceList_Version_ID(mbPartner.getM_PriceList_ID());
					p_pos.setM_PriceList_ID(mbPartner.getM_PriceList_ID());
					p_posPanel.m_order.setM_PriceList_ID(mbPartner.getM_PriceList_ID());
					p_posPanel.m_order.saveEx();
					p_posPanel.f_curLine.f_name.requestFocusInWindow();
				}
			}else if(action.equals("Reference")){
				this.setReference(reference.getText());
			}
			
		}catch(Exception ex){
			
			ADialog.warn(0, this, ex.getMessage());
			ex.printStackTrace();
		}
			
	}		
		//	actionPerformed
	
	private void printOrder() {
		if(p_posPanel.m_order != null && f_print2.isEnabled()){
			printTicket();
		}
	}
	
	private void deleteOrder(){
		
		//void instead of delete
		if(ADialog.ask(0, this, "Do you want to void the record?")){
			MOrder mOrder = new MOrder(p_posPanel.getCtx(), p_posPanel.m_order.get_ID(), p_posPanel.getTrxName());
			
			if(mOrder.getDocStatus().equals(X_C_Order.DOCSTATUS_Drafted)){
				
				mOrder.setDocStatus(X_C_Order.DOCACTION_Void);
		 		mOrder.voidIt();
		 		mOrder.saveEx();
		 		//Stop new products
		 		p_posPanel.f_curLine.f_name.setEditable(false);
				p_posPanel.f_curLine.f_quantity.setEditable(false);
				p_posPanel.f_curLine.f_delete.setEnabled(false);
				f_process.setEnabled(false);
				f_print2.setEnabled(false);
				order_type.setText(order_type.getText() + " ..VOIDED..");
				//Allow for new Bill
				
				f_bNew.setEnabled(true);
				f_bCredit.setEnabled(true);
				f_breturn.setEnabled(true);
			}
			else{
				ADialog.error(0, this, "Order Processed.","You are not allowed to void!");
			}
		}
	}
	
	public void focusGained (FocusEvent e)
	{
		System.out.println("Hello");
	}

	/**
	 * 	Focus Lost
	 *	@param e
	 */
	public void focusLost (FocusEvent e)
	{
		if (e.isTemporary())
			return;
		log.info(e.toString());
		//findBPartner();
	}	//	focusLost
	
	public int getC_BPartner_ID ()
	{
		if (m_bpartner != null)
			return m_bpartner.getC_BPartner_ID();
		return 0;
	}	//	getC_BPartner_ID

	/**
	 * 	Get BPartner
	 *	@return BPartner
	 */
	public MBPartner getBPartner ()
	{
		return m_bpartner;
	}	//	getBPartner
	
	/**
	 * 	Get BPartner Location
	 *	@return C_BPartner_Location_ID
	 */
	public int getC_BPartner_Location_ID ()
	{
		if (m_bpartner != null)
		{
			KeyNamePair pp = (KeyNamePair)f_location.getSelectedItem();
			if (pp != null)
				return pp.getKey();
		}
		return 0;
	}	//	getC_BPartner_Location_ID
	
	/**
	 * 	Get BPartner Contact
	 *	@return AD_User_ID
	 */
	public int getAD_User_ID ()
	{
		if (m_bpartner != null)
		{
			KeyNamePair pp = (KeyNamePair)f_user.getSelectedItem();
			if (pp != null)
				return pp.getKey();
		}
		return 0;
	}	//	getC_BPartner_Location_ID

	/**
	 * 	Get M_PriceList_Version_ID.
	 * 	Set Currency
	 *	@return plv
	 */
	public int getM_PriceList_Version_ID()
	{
		if (m_M_PriceList_Version_ID == 0)
		{
			int M_PriceList_ID = p_pos.getM_PriceList_ID();
			if (m_bpartner != null && m_bpartner.getM_PriceList_ID() != 0)
				M_PriceList_ID = m_bpartner.getM_PriceList_ID();
			//
			MPriceList pl = MPriceList.get(p_ctx, M_PriceList_ID, null);
			setCurrency(MCurrency.getISO_Code(p_ctx, pl.getC_Currency_ID()));
			f_name.setToolTipText(pl.getName());
			//
			MPriceListVersion plv = pl.getPriceListVersion (p_posPanel.getToday());
			if (plv != null && plv.getM_PriceList_Version_ID() != 0)
				m_M_PriceList_Version_ID = plv.getM_PriceList_Version_ID();
		}
		return m_M_PriceList_Version_ID;
	}	//	getM_PriceList_Version_ID
	
	public void setM_PriceList_Version_ID(int M_PriceList_ID)
	{
		if(M_PriceList_ID > 0){
			MPriceList pl = MPriceList.get(p_ctx, M_PriceList_ID, null);
			setCurrency(MCurrency.getISO_Code(p_ctx, pl.getC_Currency_ID()));
			f_name.setToolTipText(pl.getName());
			//
			MPriceListVersion plv = pl.getPriceListVersion (p_posPanel.getToday());
			if (plv != null && plv.getM_PriceList_Version_ID() != 0)
				m_M_PriceList_Version_ID = plv.getM_PriceList_Version_ID();
		}
	}
	
	public void setMBpartner(MBPartner bPartner)
	{
		this.m_bpartner = bPartner;
	}
	
	//set curreency
	public void setCurrency(String currency) {
		if (currency == null)
			f_currency.setText("---");
		else
			f_currency.setText(currency);
	} //	setCurrency
	
	public void printTicket()
	{
		if (p_posPanel.m_order == null )
			return;
		
		if ( p_posPanel.m_order != null &&  p_posPanel.m_order.getLines().length > 0)
		{
			try 
			{	
				//To get two prints
				ReportCtl.startDocumentPrint(ReportEngine.ORDER, p_posPanel.m_order.getC_Order_ID(), null, Env.getWindowNo(this), true);
				int id = MPrintFormat.getPrintFormat_ID("Barcode", MOrder.Table_ID, Env.getAD_Client_ID(p_ctx));
				//Print format name is case sensitive "BARCODE" IS WRONG
				MPrintFormat mPrintFormat = new MPrintFormat(p_ctx, id, p_posPanel.m_order.get_TrxName());
				ReportCtl.startDocumentPrint(p_posPanel.getWindowNo(), mPrintFormat, p_posPanel.m_order.get_ID(), null, 0, "");
				
				System.out.println("p_posPanel.getWindowNo() : " + p_posPanel.getWindowNo());
				
				//Disable the order after get print
				p_posPanel.f_curLine.f_name.setEditable(false);
				p_posPanel.f_curLine.f_quantity.setEditable(false);
				p_posPanel.f_curLine.f_delete.setEnabled(false);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}	  
	}
	
	public void updateOrder()
	{
		if (p_posPanel != null )
		{
			MOrder order = p_posPanel.m_order;
			if (order != null)
			{
				f_print2.setEnabled(order.getLines().length != 0);
  				f_DocumentNo.setText(order.getDocumentNo());
  				f_bNew.setEnabled(order.getLines().length != 0);
  				f_breturn.setEnabled(order.getLines().length != 0);
  				f_bCredit.setEnabled(order.getLines().length != 0);
  				bpartner.setEnabled(order.getLines().length == 0);
  				f_process.setEnabled(true);
  				f_cashPayment.setEnabled(false);
  				Object ref  = order.get_Value("poreference") == null?"":order.get_Value("poreference");
  				reference.setText(ref.toString());
  				p_posPanel.f_curLine.f_name.setEditable(true);
			}
			else
			{
				this.clear();
			}
		}
	}	

	
	private void clear(){
		
		p_posPanel.m_order = null;
		f_DocumentNo.setText(null);
		f_bNew.setEnabled(true);
		f_breturn.setEnabled(true);
		f_bCredit.setEnabled(true);
		f_history.setEnabled(true);
		f_process.setEnabled(false);
		f_cashPayment.setEnabled(false);
		reference.setText(null);
		discount.setText("0.00");
		f_total.setText("0.00");
	}
	/**
	 * 	Set Sums from Table
	 */
	void setSums(PosOrderModel order)
	{
		int noLines = p_posPanel.f_curLine.m_table.getRowCount();
		if (order == null || noLines == 0)
		{
			f_total.setValue(Env.ZERO);
			f_tax.setValue(Env.ZERO);
			discount.setValue(Env.ZERO);
		}
		else
		{
			f_total.setValue(order.getGrandTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
			f_tax.setValue(order.getTaxAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
			
			BigDecimal discount = new BigDecimal("0.00");
			//calculate and set discount
			for(MOrderLine line : p_posPanel.m_order.getLines()){
				discount =discount.add(line.getQtyEntered().multiply((line.getPriceList().subtract(line.getPriceActual()))));
				discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
				this.discount.setText(discount.toString());
			}
		}
	}
	
	protected void setReference(String ref){
		if(!(ref == null || ref.equals("") || f_DocumentNo.getText() == null || f_DocumentNo.getText().equals(""))){
			MOrder order = p_posPanel.m_order;
			order.set_ValueOfColumn("POREFERENCE", ref);
			order.saveEx();
		}
		p_posPanel.f_curLine.f_name.requestFocusInWindow();
	}
	
	public int getBp_id() {
		return bp_id;
	}

	public void setBp_id(int bp_id) {
		this.bp_id = bp_id;
	}
	
	public CButton getBpartner() {
		return this.bpartner;
	}
}

