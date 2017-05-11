package com.newwing.service;

import java.util.List;

import com.newwing.entity.BewinBO;

public interface BakIBewinService extends IBaseService {
	
	public String analyzeAndSave(String filePath) throws Exception;
	
	public void autoReptile(List<BewinBO> bewinBOList) throws Exception;
	
}
