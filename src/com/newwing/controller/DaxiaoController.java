package com.newwing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.entity.ParamBO;
import com.newwing.entity.ResultBO;
import com.newwing.service.IBewinService;
import com.newwing.service.IJinshaService;
import com.newwing.service.IParamService;
import com.newwing.service.IRelativeService;
import com.newwing.service.IResultService;
import com.newwing.util.DateUtil;
import com.newwing.util.Logger;

@Controller
@RequestMapping(value = "/daxiao")
@SuppressWarnings("unchecked")
public class DaxiaoController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "bewinService")
	private IBewinService bewinService;
	@Resource(name = "jinshaService")
	private IJinshaService jinshaService;
	@Resource(name = "relativeService")
	private IRelativeService relativeService;
	@Resource(name = "resultService")
	private IResultService resultService;
	@Resource(name = "paramService")
	private IParamService paramService;
	

	@RequestMapping("queryBewinList")
	public String queryBewinList(Model model) throws Exception {
		List<BewinBO> bewinList = this.bewinService.find("FROM BewinBO where status = '0'" 
				+ " and dataDate = '" + DateUtil.dateToStrDate(new Date()) + "'"
				+ " order by shijian, startTimeStr ");
		model.addAttribute("bewinList", bewinList);
		return "admin/daxiao/bewinList";
	}
	
	@RequestMapping("queryJinshaList")
	public String queryJinshaList(Model model) throws Exception {
		List<JinshaBO> jinshaList = this.jinshaService.find("FROM JinshaBO where status = '0'" 
				+ " and dataDate = '" + DateUtil.dateToStrDate(new Date()) + "'"
				+ " order by shijian, startTimeStr ");
		model.addAttribute("jinshaList", jinshaList);
		return "admin/daxiao/jinshaList";
	}
	
	@RequestMapping("queryResultList")
	public String queryResultList(Model model, Double paramValue) throws Exception {
		List<ParamBO> paramList = this.resultService.find("FROM ParamBO order by id asc ");
		ParamBO paramBO = paramList.get(0);
		if (paramValue == null || paramValue <= 0) {
			paramValue = paramBO.getParamValue();
		} else {
			paramBO.setParamValue(paramValue);
			this.paramService.update(paramBO);
		}
		
		List<ResultBO> resultList = this.resultService.find(
				" FROM ResultBO where (status = '0' or status = '2') "
				+ " and dataType = 'DAXIAO' "
				+ " and (jieguoWhole >= " + paramValue + " or jieguoWholeDaxiao >= " + paramValue + ")"
				+ " and dataDate = '" + DateUtil.dateToStrDate(new Date()) + "'"
				+ " order by shijian, startTimeStr ");
		model.addAttribute("resultList", resultList);
		model.addAttribute("paramValue", paramValue);
		return "admin/daxiao/resultList";
	}
	
}
