package com.newwing.service;

import java.util.List;

import com.newwing.entity.RelativeBO;

public interface IRelativeService extends IBaseService {
	
	/**
	 * 查找项目下所有模块
	 * @param projectNo
	 * @return
	 * @throws Exception
	 */
	public List<RelativeBO> queryModuleListByProjectNo(String projectNo) throws Exception;
	
}
