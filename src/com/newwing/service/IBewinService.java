package com.newwing.service;

import java.util.List;

import com.newwing.entity.BewinBO;

public interface IBewinService extends IBaseService {
	
	public void autoReptile(List<BewinBO> bewinBOList) throws Exception;
	
}
