package org.lucius.framework.utils.export;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.w3c.dom.Element;


/**
 * Copyright: Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date: 2015年10月29日 上午8:39:33 Author: zhangle Version: 1.0.0.0 Description: 设置值
 */
public abstract class ValueSet {

    protected Element element;

    protected Map<String, String> map;

    public ValueSet(Element element, Map<String, String> map) {
        super();
        this.map = map;
        this.element = element;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * 设置值
     * 
     * @param value
     *            原始值
     * @return
     */
    public String value(Object value) {
        return value(value, null);
    }
    
    /**
     * 设置值
     * 
     * @param value
     *            原始值
     * @param rowValue
     *            值所属对象           
     * @return
     */
    public String value(Object value, Object rowValue) {
        return value(value, rowValue, null);
    }

    /**
     * 设置值
     * 
     * @param value
     *            原始值
     * @param rowValue
     *            值所属对象              
     * @param langCode
     *            语言信息
     * @return
     */
    public abstract String value(Object value, Object rowValue, LangCode langCode);

}
