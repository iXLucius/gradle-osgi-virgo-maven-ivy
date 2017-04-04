/**
 * @(#)FieldBean.java 1.0 2015年10月28日
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015年10月28日
 * Author:      zhangle
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

import java.util.Map;

/**
 * Copyright: Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date: 2015年10月29日 上午8:42:33 Author: zhangle Version: 1.0.0.0 Description:
 * 字段类型
 */
public class FieldBean {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段样式
     */
    private Style style;

    /**
     * 值样式
     */
    private Style valueStyle;

    /**
     * 设置值
     */
    private ValueSet valueSet;

    /**
     * 字段标题集合
     */
    private Map<String, String> titles;

    public FieldBean(String name, Style style, Style valueStyle,
            ValueSet valueSet, Map<String, String> titles) {
        super();
        this.name = name;
        this.style = style;
        this.titles = titles;
        this.valueSet = valueSet;
        this.valueStyle = valueStyle;
    }

    public String getName() {
        return name;
    }

    public Style getStyle() {
        return style;
    }

    public Style getValueStyle() {
        return valueStyle;
    }

    public ValueSet getValueSet() {
        return valueSet;
    }

    /**
     * 获取字段标题
     * 
     * @param langCode
     *            语言信息
     * @return
     */
    public String getTitle(LangCode langCode) {
        if (langCode == null || titles == null
                || !titles.containsKey(langCode.toString())) {
            return null;
        }
        return titles.get(langCode.toString());
    }

    public Map<String, String> getTitles() {
        return titles;
    }

}
