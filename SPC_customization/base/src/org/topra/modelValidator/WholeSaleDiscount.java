package org.topra.modelValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSession;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

//org.topra.modelValidator.WholeSaleDiscount
public class WholeSaleDiscount implements ModelValidator{

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WholeSaleDiscount.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	private MOrderLine mOrderLine;
	
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}	
		else
			log.info("Initializing global validator: "+this.toString());
		
		engine.addModelChange(MOrderLine.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}
	
	@Override
	public String modelChange(PO po, int type) throws Exception {
		
		this.mOrderLine = (MOrderLine) po;
		
		if(po.get_TableName().equals(MOrderLine.Table_Name) && type == TYPE_AFTER_CHANGE){
			
			BigDecimal o = (BigDecimal) this.mOrderLine.get_ValueOld("qtyentered");
			BigDecimal n = (BigDecimal) this.mOrderLine.get_Value("qtyentered");
			if(o.compareTo(n) != 0){
				
			}
		}
		
		if(po.get_TableName().equals(MOrderLine.Table_Name) && type == TYPE_AFTER_NEW){
			this.setDiscount();
			return "";
		}
		if(po.get_TableName().equals(MOrderLine.Table_Name) && type == TYPE_AFTER_DELETE){
			this.setDiscount();
			return "";
		}
		
		return "";
	}
	
	private int getC_HEADERDISCOUNT_ID(MOrder mOrder){
		
		String sql = "SELECT C_HEADERDISCOUNT_ID FROM C_HEADERDISCOUNT WHERE C_DOCTYPETARGET_ID = ? AND AD_ORG_ID=? AND ISACTIVE = 'Y'";
		return DB.getSQLValue(mOrder.get_TrxName() , sql, mOrder.getC_DocTypeTarget_ID() , mOrder.getAD_Org_ID());
	}
	
	//
	private BigDecimal getDiscount(int C_HEADERDISCOUNT_ID , BigDecimal mOrderTotal){
		
		String sql = "SELECT GRANDTOTAL , DISCOUNT FROM C_HEADERDISCOUNT_LINE WHERE C_HEADERDISCOUNT_ID = ?";
		PreparedStatement pstmt = DB.prepareStatement(sql, this.mOrderLine.get_TrxName());
		
		ResultSet rs = null;
		BigDecimal GRANDTOTAL = new BigDecimal("0");
		BigDecimal DISCOUNT = new BigDecimal("0");
		
		try {
			pstmt.setInt(1, C_HEADERDISCOUNT_ID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GRANDTOTAL = new BigDecimal(rs.getString("GRANDTOTAL"));
				if(mOrderTotal.compareTo(GRANDTOTAL) >= 0)
					DISCOUNT = new BigDecimal(rs.getString("DISCOUNT"));
			}
			
		} catch (SQLException e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return DISCOUNT;
	}
	
	private void setDiscount(){
		
		MOrder mOrder = (MOrder) this.mOrderLine.getC_Order();
		int C_HEADERDISCOUNT_ID = this.getC_HEADERDISCOUNT_ID(mOrder);
		if(C_HEADERDISCOUNT_ID <= 0)
			return;
		
		BigDecimal dis;
		BigDecimal discount = getDiscount(C_HEADERDISCOUNT_ID, mOrder.getGrandTotal());
		for(MOrderLine line : mOrder.getLines()){
			
			dis = discount;
			dis = dis.divide(new BigDecimal(100));
			dis = new BigDecimal(1).subtract(dis);
			BigDecimal np = line.getPriceList().multiply(dis).setScale(4,RoundingMode.HALF_UP);
			line.setPriceActual(np);
			line.setPriceEntered(np.multiply(line.getProduct().getUnitsPerPallet()));
			line.save();
		}
	}
	
	private void setChangeDiscount(){
		//Check for header discout eligibility
		
		MOrder mOrder = (MOrder)this.mOrderLine.getC_Order();
		
		int C_HEADERDISCOUNT_ID = this.getC_HEADERDISCOUNT_ID(mOrder);
		if(C_HEADERDISCOUNT_ID <= 0)
			return;
		
		
		BigDecimal ot;
		//Get Total Order Value before save
		//More then one order lines
		if(mOrder.getLines().length > 0){
			String sql = "select SUM(linenetamt)  from c_orderline where c_order_id = ? AND c_orderline_id NOT IN(?)";
			ot = DB.getSQLValueBD(this.mOrderLine.get_TrxName(), sql, this.mOrderLine.getC_Order_ID() , this.mOrderLine.get_ID());
			ot = ot.add(this.mOrderLine.getLineNetAmt());
		}else{
			ot = mOrder.getGrandTotal();
		}
		
		BigDecimal dis;
		BigDecimal discount = getDiscount(C_HEADERDISCOUNT_ID, ot);
		for(MOrderLine line : ((MOrder)this.mOrderLine.getC_Order()).getLines()){
			dis = discount;
			dis = dis.divide(new BigDecimal(100));
			dis = new BigDecimal(1).subtract(dis);
			BigDecimal np = line.getPriceList().multiply(dis).setScale(4,RoundingMode.HALF_UP);
			line.setPriceActual(np);
			line.setPriceEntered(np.multiply(line.getProduct().getUnitsPerPallet()));
			line.setLineNetAmt();
			//line.setDiscount();
			line.save();
		}
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) { return null; }
	@Override
	public String docValidate(PO po, int timing) {	return null;}
}
