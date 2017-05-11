package com.newwing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.entity.ParamBO;
import com.newwing.entity.RelativeBO;
import com.newwing.entity.ResultBO;
import com.newwing.service.IBewinService;
import com.newwing.service.IJinshaService;
import com.newwing.service.IParamService;
import com.newwing.service.IRelativeService;
import com.newwing.service.IResultService;
import com.newwing.util.DateUtil;
import com.newwing.util.DownloadUtil;
import com.newwing.util.Logger;
import com.newwing.util.UploadFile;

@Controller
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class LotteryController {
	
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
	
	@RequestMapping("index.do")
	public String index(Model model) throws Exception {
		return "index";
	}

	@RequestMapping("queryBewinList")
	public String queryBewinList(Model model) throws Exception {
		List<BewinBO> bewinList = this.bewinService.find("FROM BewinBO where status = '0'" + " and shijian = '" + DateUtil.dateToStrDate(new Date()) + "'");
		model.addAttribute("bewinList", bewinList);
		return "admin/lottery/bewinList";
	}
	
	@RequestMapping("queryJinshaList")
	public String queryJinshaList(Model model) throws Exception {
		List<JinshaBO> jinshaList = this.jinshaService.find("FROM JinshaBO where status = '0'" + " and shijian = '" + DateUtil.dateToStrDate(new Date()) + "'");
		model.addAttribute("jinshaList", jinshaList);
		return "admin/lottery/jinshaList";
	}
	
	@RequestMapping("queryResultList")
	public String queryResultList(Model model, Double paramValue) throws Exception {
		List<ParamBO> paramList = this.resultService.find("FROM ParamBO ");
		ParamBO paramBO = paramList.get(0);
		if (paramValue == null || paramValue <= 0) {
			paramValue = paramBO.getParamValue();
		} else {
			paramBO.setParamValue(paramValue);
			this.paramService.update(paramBO);
		}
		
		List<ResultBO> resultList = this.resultService.find(
				" FROM ResultBO where (status = '0' or status = '2') "
				+ " and (jieguoWhole >= " + paramValue + " or jieguoWholeDaxiao >= " + paramValue + ")"
				+ " and shijian = '" + DateUtil.dateToStrDate(new Date()) + "'");
		model.addAttribute("resultList", resultList);
		model.addAttribute("paramValue", paramValue);
		return "admin/lottery/resultList";
	}
	
	@RequestMapping("queryRelativeList")
	public String queryRelativeList(Model model) throws Exception {
		List<RelativeBO> relativeList = this.relativeService.find(" FROM RelativeBO " 
				+ " where shijian = '" + DateUtil.dateToStrDate(new Date()) + "'"
				+ " order by qiuduiBewin asc");
		model.addAttribute("relativeList", relativeList);
		return "admin/lottery/relativeList";
	}
	
	@RequestMapping("relativeToAdd")
	public String relativeToAdd() throws Exception {
		return "admin/lottery/relativeAdd";
	}
	
	@RequestMapping("addRelative")
	public String addRelative(RelativeBO relativeBO) throws Exception {
		this.relativeService.saveOrUpdate(relativeBO);
		return "redirect:/queryRelativeList.do";
	}
	
	@RequestMapping("deleteRelative")
	public String deleteRelative(int relativeId) throws Exception {
		RelativeBO relativeBO = (RelativeBO) this.relativeService.get(
				RelativeBO.class, relativeId);
		this.relativeService.delete(relativeBO);
		return "redirect:/queryRelativeList.do";
	}
	
	@RequestMapping("toUpdateRelative")
	public String toUpdateRelative(Model model, int relativeId) throws Exception {
		RelativeBO relativeBO = (RelativeBO) this.relativeService.get(
				RelativeBO.class, relativeId);
		model.addAttribute("relative", relativeBO);
		return "admin/lottery/relativeUpdate";
	}
	
	@RequestMapping("updateRelative")
	public String updateRelative(Model model, RelativeBO relativeBO) throws Exception {
		RelativeBO relativeBOTemp = (RelativeBO)this.relativeService.get(RelativeBO.class, relativeBO.getId());
		relativeBOTemp.setQiuduiBewin(relativeBO.getQiuduiBewin());
		relativeBOTemp.setQiuduiJinsha(relativeBO.getQiuduiJinsha());
		relativeBOTemp.setShijian(DateUtil.dateToStrDate(new Date()));
		relativeBOTemp.setUpdateTime(new Date());
		relativeService.update(relativeBOTemp);
		return "redirect:/queryRelativeList.do";
	}
	
	@RequestMapping("toParam")
	public String toParam(Model model) throws Exception {
		List<ParamBO> paramList = this.paramService.find("FROM ParamBO");
		ParamBO paramBO = null;
		if (paramList != null && paramList.size() > 0) {
			paramBO = paramList.get(0);
		}
		model.addAttribute("paramBO", paramBO);
		return "admin/lottery/paramUpdate";
	}
	
	@RequestMapping("updateParam")
	public String updateParam(Model model, ParamBO paramBO) throws Exception {
		this.paramService.update(paramBO);
		return "redirect:/toParam.do";
	}
	
	@RequestMapping("upload")
	public String upload(HttpServletRequest request, String type,
			   MultipartFile uploadFile, Model model) throws Exception {
		String returnUrl = "";
		if ("Bewin".equalsIgnoreCase(type)) {// 上传必赢数据
			returnUrl = "redirect:/queryBewinList.do";
		} else if ("Jinsha".equalsIgnoreCase(type)) {// 上传金沙数据
			returnUrl = "redirect:/queryJinshaList.do";
		}
		// 判断上传的文件是否是空文件
		String originalFileName = uploadFile.getOriginalFilename();
		if (originalFileName == null || "".equals(originalFileName)) {
			return returnUrl;
		}

		// 判断上传的是否是Excel文件
		String subString = originalFileName.substring(originalFileName.lastIndexOf("."));
		if (!".xlsx".equals(subString) && !".xls".equals(subString)) {
			return returnUrl;
		}

		String jinshaFileUrl = UploadFile.uploadFile(request, uploadFile, type, uploadFile.getOriginalFilename());
//		if ("Bewin".equalsIgnoreCase(type)) {// 上传必赢数据
//			this.bewinService.analyzeAndSave(jinshaFileUrl);
//		} else if ("Jinsha".equalsIgnoreCase(type)) {// 上传金沙数据
//			this.jinshaService.analyzeAndSave(jinshaFileUrl);
//		}
		return returnUrl;
		
	}
	
	@RequestMapping("downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
		DownloadUtil downloadUtil = new DownloadUtil();
		String fileName = "";
		if ("bewin".equals(type)) {
			fileName = "【模板】必赢 - 20161225.xlsx";
		} else if ("jinsha".equals(type)) {
			fileName = "【模板】金沙 - 20161225.xlsx";
		}
		String filePath = request.getSession().getServletContext().getRealPath("uploadFiles");
		downloadUtil.download(filePath + "/" + fileName, fileName, response, false);
	}

	@RequestMapping("calculate")
	public String calculate(Model model) throws Exception {
//		this.resultService.calculate();// 旧的计算方式
		this.resultService.autoReptile();
		return "redirect:/queryResultList.do";
	}

}
