package com.newwing.service;

import java.util.List;

import com.newwing.entity.JinshaBO;

public interface BakIJinshaService extends IBaseService {
	
	public String analyzeAndSave(String filePath) throws Exception;
	
	public void autoReptile(List<JinshaBO> jinshaBOList) throws Exception;
	
}
