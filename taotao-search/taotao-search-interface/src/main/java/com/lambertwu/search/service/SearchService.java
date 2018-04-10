package com.lambertwu.search.service;

import com.lambertwu.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int page, int rows) throws Exception;
}
