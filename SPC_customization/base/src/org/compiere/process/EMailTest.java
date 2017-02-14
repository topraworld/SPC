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
 *****************************************************************************/
package org.compiere.process;

import java.io.File;

import org.compiere.model.MClient;
import org.compiere.model.MStore;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Ini;

/**
 *	Client EMail Test
 *	
 *  @author Jorg Janke
 *  @version $Id: EMailTest.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class EMailTest extends SvrProcess
{
	/** Client Parameter			*/
	protected int	p_AD_Client_ID = 0;
	
	/**
	 * 	Get Parameters
	 */
	protected void prepare ()
	{
		p_AD_Client_ID = getRecord_ID();
		if (p_AD_Client_ID == 0)
			p_AD_Client_ID = Env.getAD_Client_ID(getCtx());
	}	//	prepare

	/**
	 * 	Process - Test EMail
	 *	@return info
	 */
	protected String doIt () throws Exception
	{
		MClient client = MClient.get (getCtx(), p_AD_Client_ID);
		log.info(client.toString());
		
		String to = "csupun063@gmail.com";
		String subject = "Test mail Dissa";
		
		
		//String responce = client.testHNEMail(to , subject , "");
		//System.out.println(responce);
		
		return "";
	}	//	doIt
	
}	//	EMailTest
