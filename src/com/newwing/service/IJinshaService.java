package com.newwing.service;

import java.util.List;

import com.newwing.entity.JinshaBO;

public interface IJinshaService extends IBaseService {
	
	public void autoReptile(List<JinshaBO> jinshaBOList) throws Exception;
	
	public void autoReptileTiyu(List<JinshaBO> jinshaBOList) throws Exception;
	
}
