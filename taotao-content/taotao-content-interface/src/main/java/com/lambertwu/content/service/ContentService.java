package com.lambertwu.content.service;

import com.lambertwu.common.pojo.EasyUIDataGridResult;

public interface ContentService {

	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);
}
