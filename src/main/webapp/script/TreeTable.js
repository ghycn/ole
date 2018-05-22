var _treeTableIcons = {};
var showLine = false;
if (showLine) {
	_treeTableIcons['empty'] = ctx+'/static/images/treetable/empty.gif';
	_treeTableIcons['folder'] = ctx+'/static/images/treetable/folder.gif';
	_treeTableIcons['folderopen'] = ctx+'/static/images/treetable/folderopen.gif';
	_treeTableIcons['join'] = ctx+'/static/images/treetable/join.gif';
	_treeTableIcons['joinbottom'] = ctx+'/static/images/treetable/joinbottom.gif';
	_treeTableIcons['line'] = ctx+'/static/images/treetable/line.gif';
	_treeTableIcons['minus'] = ctx+'/static/images/treetable/minus.gif';
	_treeTableIcons['minusbottom'] = ctx+'/static/images/treetable/minusbottom.gif';
	_treeTableIcons['nolines_minus'] = ctx+'/static/images/treetable/nolines_minus.gif';
	_treeTableIcons['nolines_plus'] = ctx+'/static/images/treetable/nolines_plus.gif';
	_treeTableIcons['page'] = ctx+'/static/images/treetable/page.gif';
	_treeTableIcons['plus'] = ctx+'/static/images/treetable/plus.gif';
	_treeTableIcons['plusbottom'] = ctx+'/static/images/treetable/plusbottom.gif';
} else {
	_treeTableIcons['empty'] = ctx+'/static/images/treetable/empty.gif';
	_treeTableIcons['folder'] = ctx+'/static/images/treetable/folder.gif';
	_treeTableIcons['folderopen'] = ctx+'/static/images/treetable/folderopen.gif';
	_treeTableIcons['join'] = ctx+'/static/images/treetable/empty.gif';
	_treeTableIcons['joinbottom'] = ctx+'/static/images/treetable/empty.gif';
	_treeTableIcons['line'] = ctx+'/static/images/treetable/empty.gif';
	_treeTableIcons['minus'] = ctx+'/static/images/treetable/nolines_minus.gif';
	_treeTableIcons['minusbottom'] = ctx+'/static/images/treetable/nolines_minus.gif';
	_treeTableIcons['nolines_minus'] = ctx+'/static/images/treetable/nolines_minus.gif';
	_treeTableIcons['nolines_plus'] = ctx+'/static/images/treetable/nolines_plus.gif';
	_treeTableIcons['page'] = ctx+'/static/images/treetable/page.gif';
	_treeTableIcons['plus'] = ctx+'/static/images/treetable/nolines_plus.gif';
	_treeTableIcons['plusbottom'] = ctx+'/static/images/treetable/nolines_plus.gif';
}

function TreeTable(layout, model, divId, id) {
	this.divId = divId;
	this.mapping = {};
	this.model = model;
	this.layout = layout;
	this.addNode = _addNode;
	this.startup = _startup;
	this.setRoot = _setRoot;
	this.getRoot = _getRoot;
	this.expandNode = _expandNode;
	this.expandAll = _expandAll;
	TreeTable.prototype.instants[this.id = id ? id : TreeTable.prototype._treeIdPrefix + TreeTable.prototype.index++] = this;
}

function TreeNode(item) {
	this.item = item;
	this.nodes = [];
}

TreeTable.prototype._treeIdPrefix = 'treeTable_';
TreeTable.prototype.instants = {};
TreeTable.prototype.index = 0;
TreeNode.prototype.index = 0;

function _addNode(parentNode, childNode) {
	if (parentNode) {
		childNode.parentId = parentNode.id;
		childNode.id = parentNode.id + '_' + TreeNode.prototype.index++;
		parentNode.nodes[parentNode.nodes.length] = childNode;
		childNode.parent = parentNode;
	} else {
		childNode.id = this.id + '_' + TreeNode.prototype.index++;
	}
	childNode.isOpened = true;
	this.mapping[childNode.id]=childNode;
}

function _getRoot() {
	return this.rootNode;
}

var count=0;
var rootNodes = new Array();

function _setRoot(rootNode) {
	rootNodes[count++] = rootNode;
	this.addNode(null, rootNode);
	this.rootNode=rootNodes;
}

function _startup() {
	if (this.layout && this.layout.constructor == Array && this.layout.length > 0) {
		_makeupNodes(this);
		var tableHeaderStr = '<thead class="treeTableHeader"><tr>';
		for (var i = 0; i < layout.length; i++) {
			var headerClass = this.layout[i].headerClass ? ' class="' + this.layout[i].headerClass + '"' : '';
			tableHeaderStr += '<td' + headerClass + '>' + this.layout[i].name + '</td>';
		}
		tableHeaderStr += '</tr></thead>';
		var tableStr = '<table id="' + this.id + '" class="treeTable">' + tableHeaderStr;
		if(this.rootNode!=undefined){
			for (var i = 0; i < this.rootNode.length; i++) {
				tableStr+=_makeupHTML(this, this.layout, this.rootNode[i], this.rootNode[i], -1, -1, '');
			}
		}
		tableStr+='</table>';
		document.getElementById(this.divId).innerHTML = tableStr;
	}
}

function _makeupNodes(treeTable) {
	var model = treeTable.model;
	var jsonStore = model.store;
	if (jsonStore) {
		var childrenAttrs = model.childrenAttrs;
		_traverseModel(treeTable, null,jsonStore[0], jsonStore, childrenAttrs);
	}
}
var k=0;
function _traverseModel(treeTable, parentNode, item,items, childrenAttrs) {
	if (item) {
		var treeNode = new TreeNode(item);
		treeTable.addNode(parentNode, treeNode);
		var children = item[childrenAttrs];
		if (children && children.constructor == Array) {
			for ( var i = 0; i < children.length; i++) {
				_traverseModel(treeTable, treeNode, children[i],items, childrenAttrs);
			}
		}
		if(!parentNode){
			treeTable.setRoot(treeNode);
		}
		k++;
		_traverseModel(treeTable, null, items[k],items, childrenAttrs);
	}
}

function _makeupHTML(treeTable, layout, rootNode, treeNode, index, count, indent) {
	var htmlStr = '';
	if (treeNode && treeNode.item) {
		var isFolderNode = (treeNode.nodes.length > 0);
		htmlStr = '<tr id="' + treeNode.id + '" class="treeTableRow">\n';
		for (var colIdx = 0; colIdx < layout.length; colIdx++) {
			var tdText = layout[colIdx].get ? layout[colIdx].get(treeNode.item, colIdx, treeNode, treeTable) : _get(treeNode.item, colIdx, treeNode, treeTable);
			var className = layout[colIdx].className;
			var style = layout[colIdx].style;
			var icon = layout[colIdx].getIcon ? layout[colIdx].getIcon(treeNode.item, colIdx, treeNode, true, treeTable) : _getIcon(treeNode.item, colIdx, treeNode, true, treeTable);
			var iconElement = '';
			if (icon && icon != '') {
				iconElement = '<img src="' + icon + '" style="vertical-align: middle" alt="" />';
			}
			if (colIdx == 0){
				var imageStr = '';
				var eventStr = '';
				if (isFolderNode) {
//					eventStr = isFolderNode ? ' id="folder_' + treeNode.id + '" onclick="javascript:handleNodeClick(event)"' : '';
				}
				if (index == count - 1) {
					imageStr = _treeTableIcons[isFolderNode ? 'minusbottom' : 'joinbottom'];
				} else {
					imageStr = _treeTableIcons[isFolderNode ? (treeNode == rootNode ? 'nolines_minus' : 'minus') : 'join'];
				}
				tdText = indent + '<img src="' + imageStr + '" style="vertical-align: middle" alt="" ' + eventStr + ' />' + iconElement + tdText;
			} else {
				tdText = iconElement + tdText;
			}
			htmlStr += '  <td class="treeTableCell' + (className ? ' ' + className : '') + '" ' + (style ? 'style="' + style + '" ' : '') + '>' + tdText + '</td>\n';
		}
		htmlStr += '</tr>\n';
		for (var i = 0; i < treeNode.nodes.length; i++) {
			var nextIndent = indent + ((index != -1 && index != count - 1) ? '<img src="' + _treeTableIcons['line'] + '" style="vertical-align: middle" alt="" />' : '<img src="' + _treeTableIcons['empty'] + '" style="vertical-align: middle" alt="" />');
			htmlStr += _makeupHTML(treeTable, layout, rootNode, treeNode.nodes[i], i, treeNode.nodes.length, nextIndent);
		}
	}
	return htmlStr;
}

function _get(item, column, treeNode, treeTable) {
	var layout = treeTable.layout;
	return treeNode.item[layout[column].field];
}

function _getIcon(item, column, treeNode, isOpened, tableTree) {
	if (column == 0) {
		if (treeNode.nodes.length > 0) {
			return _treeTableIcons[isOpened ? 'folderopen' : 'folder'];
		} else {
			return _treeTableIcons['page'];
		}
	}
	return '';
}

function _expandNode(treeNode, isOpened) {
	_expand(this, treeNode, isOpened, false);
}

function _expandAll(isOpened) {
	var rootNode = this.getRoot();
	if (rootNode) {
		_expand(this, rootNode, isOpened, true);
	}
}

function _expand(treeTable, treeNode, isOpened, isOpenAll) {
	if (!treeNode) {
		return;
	}

	var subTreeNodes = treeNode.nodes;
	if (subTreeNodes && subTreeNodes.length > 0) {
		var source = document.getElementById('folder_' + treeNode.id);
		var trNode = source.parentNode.parentNode;
		var folderNode = source.nextSibling;
		var itemId = trNode.id;
		var parentNode = source.parentNode;
		while (parentNode.tagName.toLowerCase() != 'table') {
			parentNode = parentNode.parentNode;
		}
		
		folderNode.setAttribute('src', _treeTableIcons[isOpened ? 'folderopen' : 'folder']);
		var isRootNode = treeNode == treeTable.getRoot();
		if (isRootNode) {
			source.setAttribute('src', _treeTableIcons[isOpened ? 'nolines_minus' : 'nolines_plus']);
		} else {
			var isLastTreeNode = treeNode.parent.nodes[treeNode.parent.nodes.length - 1] == treeNode ? true : false;
			if (isLastTreeNode) {
				source.setAttribute('src', _treeTableIcons[isOpened ? 'minusbottom' : 'plusbottom']);
			} else {
				source.setAttribute('src', _treeTableIcons[isOpened ? 'minus' : 'plus']);
			}
		}

		for (var i = 0; i < subTreeNodes.length; i++) {
			var subTreeNode = subTreeNodes[i];
			var subTrNode = document.getElementById(subTreeNode.id);
			if (subTrNode) {
				subTrNode.style.display = isOpened ? 'table-row' : 'none';
			}
			if (!isOpenAll && isOpened && !subTreeNode.isOpened) {
				continue;
			}
			if (isOpenAll) {
				treeNode.isOpened = isOpened;
			}
			_expand(treeTable, subTreeNode, isOpened, isOpenAll);
		}
	}
}

function handleNodeClick(event) {
	var source = event.currentTarget || event.srcElement;
	var trNode = source.parentNode.parentNode;
	var itemId = trNode.id;
	var parentNode = source.parentNode;
	while (parentNode.tagName.toLowerCase() != 'table') {
		parentNode = parentNode.parentNode;
	}
	var treeTable = TreeTable.prototype.instants[parentNode.id];
	var treeNode = treeTable.mapping[itemId];
	var isOpened = treeNode.isOpened;
	treeTable.expandNode(treeNode, !isOpened);
	treeNode.isOpened = !isOpened;
}