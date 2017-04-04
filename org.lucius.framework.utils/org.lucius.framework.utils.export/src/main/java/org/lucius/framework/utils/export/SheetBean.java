/**
 * @(#)SheetBean.java 1.0 2016年8月9日
 * @Copyright:  Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2016年8月9日
 * Author:      zhangle
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

import java.io.InputStream;
import java.util.Collection;

/**
 * Copyright: Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date: 2016年8月9日 上午9:29:43 Author: zhangle Version: 1.0.0.0 Description:
 * sheet实体类
 */
public class SheetBean {

    private String sheetName;

    private InputStream templateStream;

    private Collection<?> datalist;

    public SheetBean(String sheetName, InputStream templateStream,
            Collection<?> datalist) {
        super();
        this.sheetName = sheetName;
        this.templateStream = templateStream;
        this.datalist = datalist;
    }

    public SheetBean(int sheetIndex, String sheetName,
            InputStream templateStream, Collection<?> datalist) {
        this(sheetName, templateStream, datalist);
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public InputStream getTemplateStream() {
        return templateStream;
    }

    public void setTemplateStream(InputStream templateStream) {
        this.templateStream = templateStream;
    }

    public Collection<?> getDatalist() {
        return datalist;
    }

    public void setDatalist(Collection<?> datalist) {
        this.datalist = datalist;
    }

}
