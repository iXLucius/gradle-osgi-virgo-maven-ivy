/**
 * @(#)ReturnObject.java 1.0 2015-5-14
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-5-14
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.model;

import java.io.Serializable;

public class ReturnObject implements Serializable {

    private static final long serialVersionUID = -1138616007708649596L;

    private String returnCode = SystemConstants.SUCCESS.getStatusCode();
    private String returnMsg = SystemConstants.SUCCESS.getMessage();
    private Object data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
