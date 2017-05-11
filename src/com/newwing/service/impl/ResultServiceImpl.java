package com.newwing.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.entity.ParamBO;
import com.newwing.entity.ResultBO;
import com.newwing.service.IBewinService;
import com.newwing.service.IJinshaService;
import com.newwing.service.IResultService;
import com.newwing.util.DateUtil;
import com.newwing.util.Logger;
import com.newwing.util.SoundUtils;

@SuppressWarnings("all")
@Service("resultService")
public class ResultServiceImpl extends BaseServiceImpl implements IResultService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IBewinService bewinService;
	@Autowired
	private IJinshaService jinshaService;
	
	public void autoReptile() throws Exception {
		try {
			List<BewinBO> bewinBOList = new ArrayList<BewinBO>();
			this.bewinService.autoReptile(bewinBOList);
			List<JinshaBO> jinshaBOList = new ArrayList<JinshaBO>();
//			this.jinshaService.autoReptile(jinshaBOList);
			this.jinshaService.autoReptileTiyu(jinshaBOList);
			this.autoCalculate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void autoCalculate() throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始自动计算结果 <<<<<<<<<<<<<<<<<<<<");
		StringBuilder hql = new StringBuilder();
		hql.append(" from BewinBO b, JinshaBO j " );
		hql.append(" where j.qiuduiMain = b.qiuduiMain " );
		hql.append(" and j.status = '0' ");
		hql.append(" and b.status = '0' ");
		hql.append(" and j.qiuduiClient = b.qiuduiClient ");
		hql.append(" and j.wholeRangqiu = b.wholeRangqiu ");
		hql.append(" and j.shijian = '" + DateUtil.dateToStrDate(new Date()) + "'");
		List<Object[]> resultList = this.baseDAO.find(hql.toString());
		List<ResultBO> finalResultList = new ArrayList<ResultBO>();
		
		if (resultList != null && resultList.size() > 0) {
			for (Object[] obj : resultList) {
				BewinBO bewinBO = (BewinBO)obj[0];
				JinshaBO jinshaBO = (JinshaBO)obj[1];
				// 保存必赢的计算结果
				ResultBO resultBOBewin = new ResultBO();
				resultBOBewin.setQiuduiBewin(bewinBO.getQiuduiMain());
				resultBOBewin.setQiuduiJinsha(bewinBO.getQiuduiClient());// 该字段应改为客队名称
				resultBOBewin.setWholeRangqiu(bewinBO.getWholeRangqiu());
				resultBOBewin.setWholeRangqiu1(bewinBO.getWholeRangqiu1());// 主水
				resultBOBewin.setWholeRangqiu2(jinshaBO.getWholeRangqiu2());// 客水:取金沙的客水
				resultBOBewin.setStatus("0");
				resultBOBewin.setStartTimeStr(bewinBO.getStartTimeStr());
				Double host1 = bewinBO.getWholeRangqiu1();
				Double client1 = jinshaBO.getWholeRangqiu2();// 客水:取金沙的客水
				Double result1 = 1/(1/(host1 + 1) + 1/(client1 + 1));
				
				Double host1Daxiao = bewinBO.getWholeDaxiao1();
				Double client1Daxiao = bewinBO.getWholeDaxiao2();
				Double result1Daxiao = 1/(1/(host1Daxiao + 1) + 1/(client1Daxiao + 1));
				
				resultBOBewin.setJieguoWhole(result1);
				resultBOBewin.setJieguoWholeDaxiao(result1Daxiao);
				resultBOBewin.setShijian(DateUtil.dateToStrDate(new Date()));
				resultBOBewin.setSaishi("BEWIN");
				resultBOBewin.setUpdateTime(new Date());
				
				resultBOBewin.setWholeDaxiao(bewinBO.getWholeDaxiao());
				resultBOBewin.setWholeDaxiao1(host1Daxiao);
				resultBOBewin.setWholeDaxiao2(client1Daxiao);
				
				Double paramValue = new Double(1);
				List<ParamBO> paramBOList = this.baseDAO.find("FROM ParamBO");
				if (paramBOList != null && paramBOList.size() > 0) {
					ParamBO paramBO = paramBOList.get(0);
					paramValue = paramBO.getParamValue();
				}
//				this.baseDAO.saveOrUpdate(resultBOBewin);
				
				// 保存金沙的计算结果
				ResultBO resultBOJinsha = new ResultBO();
				resultBOJinsha.setQiuduiBewin(jinshaBO.getQiuduiMain());
				resultBOJinsha.setQiuduiJinsha(jinshaBO.getQiuduiClient());// 该字段应改为客队名称
				resultBOJinsha.setStatus("0");
				resultBOJinsha.setWholeRangqiu(jinshaBO.getWholeRangqiu());
				resultBOJinsha.setWholeRangqiu1(jinshaBO.getWholeRangqiu1());// 主水
				resultBOJinsha.setWholeRangqiu2(bewinBO.getWholeRangqiu2());// 客水：取必赢的客水
				Double host2 = jinshaBO.getWholeRangqiu1() == null? 0: jinshaBO.getWholeRangqiu1();
				Double client2 = bewinBO.getWholeRangqiu2() == null? 0: bewinBO.getWholeRangqiu2();// 客水：取必赢的客水
				Double result2 = 1/(1/(host2 + 1) + 1/(client2 + 1));
				
				Double host2Daxiao = jinshaBO.getWholeDaxiao1() == null? 0: jinshaBO.getWholeDaxiao1();
				Double client2Daxiao = bewinBO.getWholeDaxiao2() == null? 0: bewinBO.getWholeDaxiao2();// 客水：取必赢的客水
				Double result2Daxiao = 1/(1/(host2Daxiao + 1) + 1/(client2Daxiao + 1));
				
				resultBOJinsha.setJieguoWhole(result2);
				resultBOJinsha.setJieguoWholeDaxiao(result2Daxiao);
				resultBOJinsha.setShijian(DateUtil.dateToStrDate(new Date()));
				resultBOJinsha.setSaishi("JINSHA");
				resultBOJinsha.setStartTimeStr(bewinBO.getStartTimeStr());
				resultBOJinsha.setUpdateTime(new Date());
				resultBOJinsha.setWholeDaxiao(jinshaBO.getWholeDaxiao());
				resultBOJinsha.setWholeDaxiao1(host2Daxiao);
				resultBOJinsha.setWholeDaxiao2(client2Daxiao);
				
//				this.baseDAO.saveOrUpdate(resultBOJinsha);
				finalResultList.add(resultBOBewin);
				finalResultList.add(resultBOJinsha);
			}
		}
		this.baseDAO.executeHql(" delete from ResultBO where shijian = '" + DateUtil.dateToStrDate(new Date()) + "'");// 删除当日存量数据
		// 保存今日数据 TODO 回头再想办法做更新的提示
		for (ResultBO resultBO : finalResultList) {
			this.baseDAO.saveOrUpdate(resultBO);
		}
//		for (ResultBO resultBO : finalResultList) {
//			String hqlTemp = "from ResultBO where 1 = 1 "
//					+ " and qiuduiBewin = '" + resultBO.getQiuduiBewin() + "'"
//					+ " and qiuduiJinsha = '" + resultBO.getQiuduiJinsha() + "'"
//					+ " and wholeRangqiu = '" + resultBO.getWholeRangqiu() + "'"
////					+ " and wholeRangqiu1 = " + resultBO.getWholeRangqiu1()
////					+ " and wholeRangqiu2 = " + resultBO.getWholeRangqiu2()
//					+ " and shijian = '" + resultBO.getShijian() + "'";// 主队名、客队名、让球、主水、客水、时间
//			List<ResultBO> tempResultList = this.baseDAO.find(hqlTemp);
//			if (tempResultList != null && tempResultList.size() > 0) {
//				resultBO = tempResultList.get(0);
//				resultBO.setStatus("2");
//				this.baseDAO.saveOrUpdate(resultBO);
//				continue;
//			}
//			this.baseDAO.saveOrUpdate(resultBO);
//		}
		
//		this.baseDAO.executeHql(" update ResultBO set status = 1 where shijian != '" + DateUtil.dateToStrDate(new Date()) + "'");
//		this.baseDAO.executeHql(" delete from ResultBO where shijian != '" + DateUtil.dateToStrDate(new Date()) + "'");// 删除存量数据
		this.baseDAO.executeHql(" delete from RelativeBO where qiuduiBewin is null and shijian != '" + DateUtil.dateToStrDate(new Date()) + "'");
		logger.info(">>>>>>>>>>>>>>>>>>>> 完成自动计算结果 <<<<<<<<<<<<<<<<<<<<");
	}

	public void autoPlay() throws Exception {
		List<ResultBO> resultList = this.baseDAO.find("FROM ResultBO where status = '0'");// TODO 需要增加一个flag字段
		if (resultList != null && resultList.size() > 0) {
			SoundUtils.play("C:/sound.wav");
		}
	}
	
}