/**
 * @(#)Lang.java 1.0 2015年9月17日
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015年9月17日
 * Author:      zhangl
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

import org.apache.commons.lang3.StringUtils;

/**
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015年9月18日 上午10:45:17
 * Author:      zhangl
 * Version:     1.0.0.0
 * Description: 语言标识
 */
public enum LangCode {
    
    ZH_CN,
    EN_US;
    
    /**
     * 根据字符串返回Lang对象
     * @param lang
     *          lang字符串
     * @return
     */
    public static LangCode getLang(String lang) {
        if (StringUtils.isEmpty(lang)) {
            return LangCode.ZH_CN;
        }
        try {
            return LangCode.valueOf(lang.toUpperCase());
        } catch (Exception e) { }
        return LangCode.ZH_CN;
    }

}

