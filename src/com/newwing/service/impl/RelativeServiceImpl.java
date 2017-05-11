package com.newwing.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newwing.entity.RelativeBO;
import com.newwing.service.IRelativeService;

@SuppressWarnings("all")
@Service("relativeService")
public class RelativeServiceImpl extends BaseServiceImpl implements IRelativeService {

	public List<RelativeBO> queryModuleListByProjectNo(String projectNo) throws Exception {
		StringBuffer hql = new StringBuffer(" From RelativeBO where projectNo = '" + projectNo + "'");
		List<RelativeBO> moduleBOList = this.baseDAO.find(hql.toString());
		
		return moduleBOList;
	}
}