/**
 * @(#)TreeNode.java 1.0 2015-3-18
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 树形结构JSON数据VO对象
 * 
 * Modification History:
 * Date:        2015-3-18
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.model.tree;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TreeNode implements Serializable {

    private static final long serialVersionUID = -1515258524795457304L;

    /**
     * 树形结构ID
     */
    private String id;

    /**
     * 树形结构父ID
     */
    private String pId;
    /**
     * 树形结构名称
     */
    private String name;
    /**
     * 树形结构Title
     */
    private String title;
    /**
     * 当前节点是否是父节点
     */
    private Boolean isParent = false;
    /**
     * 当前节点的类型
     */
    private String tab;

    private Integer canDelete;

    /**
     * 当前节点对应的URL资源地址
     */
    private String file;
    /**
     * 节点是否展开，默认不展开
     */
    private Boolean open = false;

    /**
     * 当前节点是否被选中，默认未被选中
     */
    private Boolean checked = false;

    /**
     * 当前节点所对应的默认图标
     */
    private String icon = "";

    /**
     * 当前节点打开时所用的图片
     */
    private String iconOpen = "";
    /**
     * 当前节点关闭时所对应的图片
     */
    private String iconClose = "";
    /**
     * 当前节点层级
     */
    private Integer level;

    private boolean chkDisabled;

    private boolean nocheck;

    public String getId() {
        return id;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取pid
     * 
     * @return pid
     */
    public String getpId() {
        return pId;
    }

    /**
     * 设置pid
     * 
     * @param pId pid
     */
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
