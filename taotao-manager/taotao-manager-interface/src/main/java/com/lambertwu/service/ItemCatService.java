package com.lambertwu.service;

import java.util.List;

import com.lambertwu.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(long parentId);
}
