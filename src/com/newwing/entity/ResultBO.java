package com.newwing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_result")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "biz_result_sequence")
public class ResultBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String shijian;// 时间
	private String startTimeStr;// 比赛开始时间
	private String saishi;// 赛事
	private String qiuduiBewin;// 必赢球队
	private String qiuduiJinsha;// 金沙球队
	private Double jieguoWhole;// 全场结果
	
	private String wholeDaxiao;// 全场让球数
	private Double wholeDaxiao1;// 全场让球1
	private Double wholeDaxiao2;// 全场让球2
	private Double jieguoWholeDaxiao;// 全场结果-大小
	
	private String jieguoHalf;// 半场结果
	private String status;// 
	private String wholeRangqiu;// 全场让球数
	private Double wholeRangqiu1;// 全场让球1
	private Double wholeRangqiu2;// 全场让球2
	private Date updateTime;// 更新时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getShijian() {
		return shijian;
	}

	public String getSaishi() {
		return saishi;
	}

	public String getQiuduiBewin() {
		return qiuduiBewin;
	}

	public String getQiuduiJinsha() {
		return qiuduiJinsha;
	}

	public Double getJieguoWhole() {
		return jieguoWhole;
	}

	public String getJieguoHalf() {
		return jieguoHalf;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public void setSaishi(String saishi) {
		this.saishi = saishi;
	}

	public void setQiuduiBewin(String qiuduiBewin) {
		this.qiuduiBewin = qiuduiBewin;
	}

	public void setQiuduiJinsha(String qiuduiJinsha) {
		this.qiuduiJinsha = qiuduiJinsha;
	}

	public void setJieguoWhole(Double jieguoWhole) {
		this.jieguoWhole = jieguoWhole;
	}

	public void setJieguoHalf(String jieguoHalf) {
		this.jieguoHalf = jieguoHalf;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWholeRangqiu() {
		return wholeRangqiu;
	}

	public void setWholeRangqiu(String wholeRangqiu) {
		this.wholeRangqiu = wholeRangqiu;
	}

	public Double getWholeRangqiu1() {
		return wholeRangqiu1;
	}

	public void setWholeRangqiu1(Double wholeRangqiu1) {
		this.wholeRangqiu1 = wholeRangqiu1;
	}

	public Double getWholeRangqiu2() {
		return wholeRangqiu2;
	}

	public void setWholeRangqiu2(Double wholeRangqiu2) {
		this.wholeRangqiu2 = wholeRangqiu2;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getJieguoWholeDaxiao() {
		return jieguoWholeDaxiao;
	}

	public void setJieguoWholeDaxiao(Double jieguoWholeDaxiao) {
		this.jieguoWholeDaxiao = jieguoWholeDaxiao;
	}

	public String getWholeDaxiao() {
		return wholeDaxiao;
	}

	public Double getWholeDaxiao1() {
		return wholeDaxiao1;
	}

	public Double getWholeDaxiao2() {
		return wholeDaxiao2;
	}

	public void setWholeDaxiao(String wholeDaxiao) {
		this.wholeDaxiao = wholeDaxiao;
	}

	public void setWholeDaxiao1(Double wholeDaxiao1) {
		this.wholeDaxiao1 = wholeDaxiao1;
	}

	public void setWholeDaxiao2(Double wholeDaxiao2) {
		this.wholeDaxiao2 = wholeDaxiao2;
	}
	
}
	