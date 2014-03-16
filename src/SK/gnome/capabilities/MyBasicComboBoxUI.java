/*
 * $Id: MyBasicComboBoxUI.java,v 1.1 2009/09/23 14:03:21 motovsky Exp $
 *
 * Copyright (c) 2009 Gnome spol. s r.o. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Gnome spol. s r.o. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Gnome.
 */

package SK.gnome.capabilities;

import javax.swing.*;
import javax.swing.plaf.basic.*;

public class MyBasicComboBoxUI extends BasicComboBoxUI
{
  BasicComboPopup popup;

  protected ComboPopup createPopup() 
  {
    popup = new BasicComboPopup(comboBox);
    popup.getAccessibleContext().setAccessibleParent(comboBox);
    return popup;
  }

  public BasicComboPopup getPopup() 
  {
    return popup;
  }
}
