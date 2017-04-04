/**
 * @(#)ModuleEnum.java 1.0 2015年10月27日
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015年10月27日
 * Author:      zhangle
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

import java.io.InputStream;

/**
 * Copyright: Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date: 2016年8月9日 上午9:19:42 Author: zhangle Version: 1.0.0.0 Description:
 * 导出模板名称定义枚举类
 */
public enum ModuleEnum {

    KEYWORD_COUNT_YEAR, 
    KEYWORD_COUNT_MONTH, 
    KEYWORD_COUNT_DAY, 
    KEYWORD_COUNT_AREA, 
    KEYWORD_COUNT_TOP10, 
    SPINFO_COUNT_YEAR, 
    SPINFO_COUNT_AREA, 
    SERVICE_CODE_COUNT_SP, 
    SERVICE_CODE_COUNT_YEAR, 
    SERVICE_CODE_COUNT_AREA,
    ISLI_CODE_COUNT_YEAR,
    ISLI_CODE_COUNT_MONTH,
    ISLI_CODE_COUNT_DAY,
    ISLI_CODE_COUNT_SP,
    LC_REGISTRATIONS_YEAR,
    LC_REGISTRATIONS_TYPE,
    PARSE_LCCODE_COUNT_YEAR,
    PARSE_LCCODE_COUNT_MONTH,
    PARSE_LCCODE_COUNT_DAY,
    PARSE_LCCODE_COUNT_AREA,
    PARSE_LCCODE_COUNT_CHINA,
    PARSE_LCCODE_COUNT_RATION_TYPE,
    PARSE_LCCODE_COUNT_TOP100;

    /**
     * 获取模板数据流
     * 
     * @return
     */
    public InputStream getTemplateStream() {
        try {
            return ExportUtil.class.getResourceAsStream(
                    "/template/export/" + this.toString() + ".ftl");
        } catch (Exception e) {
        }
        return null;
    }
}
