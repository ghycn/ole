/**
 * @项目名称: ole
 * @文件名称: TreeNode.java
 * @author tianlihu
 * @Date: 2015-5-9
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class TreeNode implements Serializable {

	private static final long serialVersionUID = 4083579947137126329L;

	private String title;
	private boolean isForder;
	private String key;
	private boolean expand = true;
	private boolean select;

	private List<TreeNode> children;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isIsForder() {
		return isForder;
	}

	public void setIsForder(boolean isForder) {
		this.isForder = isForder;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public List<TreeNode> getChildren() {
		if (children == null) {
			children = Collections.emptyList();
		}
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
		if (children != null && !children.isEmpty()) {
			this.isForder = true;
		}
	}
}
