/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Armen Rizal (armen@goodwill.co.id) Bug Fix 1564496         *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.util.Properties;

import org.apache.struts.taglib.logic.MessagesNotPresentTag;
import org.compiere.apps.ADialog;
import org.compiere.util.Env;

/**
 *	Inventory Movement Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutMovement.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1879568 ] CalloutMouvement QtyAvailable issues
 */
public class CalloutMovement extends CalloutEngine
{
	/**
	 *  Product modified
	 * 		Set Attribute Set Instance
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param GridTab     Model Tab
	 *  @param GridField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		//	Set Attribute
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
			&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else
			mTab.setValue("M_AttributeSetInstance_ID", null);
		BigDecimal availableQty = checkQtyAvailable(ctx, mTab, WindowNo, M_Product_ID, null);
		
		//Get product locator as movement Locator to
		if(availableQty.compareTo(new BigDecimal(1)) >= 1){
			MProduct mProduct = MProduct.get(ctx, M_Product_ID);
			if(mProduct.getM_Locator_ID()!=0)
			mTab.setValue("M_LOCATORTO_ID", mProduct.getM_Locator_ID());
		}
		return "";
	}   //  product
	
	// Begin Armen 2006/10/01
	/**
	 *  Movement Line - MovementQty modified
	 *              called from MovementQty
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param GridTab     Model Tab
	 *  @param GridField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String qty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
	
		//CHECK FOR AVAILABLE FOR PRODUCT
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		if(M_Product_ID > 0){
			//GET PACKQTY
			BigDecimal PACKQTY = new BigDecimal(mTab.get_ValueAsString("PACKQTY"));
			//GET AVAILABLE QUANTITY
			BigDecimal availableQty = checkQtyAvailable(ctx, mTab, WindowNo, M_Product_ID, null);
			//GET PACK PRODUCT WISE QUANTITY
			MUOMConversion [] mConversions = MUOMConversion.getProductConversions(ctx, M_Product_ID);
			BigDecimal dr = new BigDecimal(1);
			for(MUOMConversion i : mConversions){ if(i.getC_UOM_Conversion_ID()!=0) dr = i.getDivideRate();}
			//VALIDATE FOR SUFFIENT AND INSUFFIENT
			if(availableQty.compareTo(PACKQTY.multiply(dr)) < 0){
				//UNAVAILABLE INVENTORY
				mTab.setValue("movementqty", availableQty);
				mTab.setValue("PACKQTY", availableQty.divide(dr));
			}
		}
		
		if (isCalloutActive() || value == null)
			return "";
		
		M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		checkQtyAvailable(ctx, mTab, WindowNo, M_Product_ID, (BigDecimal)value);
		//
		return "";
	} //  qty
	
	/**
	 * Movement Line - Locator modified
	 * 
	 * @param ctx      Context
	 * @param WindowNo current Window No
	 * @param GridTab     Model Tab
	 * @param GridField   Model Field
	 * @param value    The new value
	 * @return Error message or ""
	 */
	public String locator(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		if (value == null)
			return "";
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		checkQtyAvailable(ctx, mTab, WindowNo, M_Product_ID, null);
		return "";
	}

	/**
	 * Check available qty
	 * 
	 * @param ctx context
	 * @param mTab Model Tab
	 * @param WindowNo current Window No
	 * @param M_Product_ID product ID
	 * @param MovementQty movement qty (if null will be get from context "MovementQty")
	 */
	private BigDecimal checkQtyAvailable(Properties ctx, GridTab mTab, int WindowNo, int M_Product_ID, BigDecimal MovementQty) {
		
		BigDecimal available = new BigDecimal(0);
		// Begin Armen 2006/10/01
		if (M_Product_ID != 0) {
			MProduct product = MProduct.get(ctx, M_Product_ID);
			if (product.isStocked()) {
				if (MovementQty == null)
					MovementQty = (BigDecimal) mTab.getValue("MovementQty");
				int M_Locator_ID = Env.getContextAsInt(ctx, WindowNo, "M_Locator_ID");
				// If no locator, don't check anything and assume is ok
				if (M_Locator_ID <= 0)
					return new BigDecimal(0);
				int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
				available = MStorage.getQtyAvailable(0, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
				
				if (available == null)
					available = Env.ZERO;
				if (available.signum() == 0){
					mTab.setValue("movementqty", new BigDecimal("0"));
					mTab.setValue("PACKQTY", new BigDecimal("0"));
					mTab.fireDataStatusEEvent("NoQtyAvailable", "0", false);
				}
				else if (available.compareTo(MovementQty) < 0){
					mTab.setValue("movementqty", new BigDecimal("0"));
					mTab.setValue("PACKQTY", new BigDecimal("0"));
					mTab.fireDataStatusEEvent("InsufficientQtyAvailable", available.toString(), false);
				}	
			}
		}
		// End Armen
		return available;
	}
}	//	CalloutMove