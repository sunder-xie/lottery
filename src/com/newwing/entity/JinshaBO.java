package com.newwing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 接口表
 */
@Entity
@Table(name="biz_jinsha")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "biz_jinsha_sequence")
public class JinshaBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String shijian;// 时间
	private String startTimeStr;// 比赛开始时间
	private String saishi;// 所属赛事
	private String qiuduiMain;// 主队名称
	private String qiuduiClient;// 客队名称
	private Double wholeDuying;// 全场独赢
	private String wholeRangqiu;// 全场让球数
	private Double wholeRangqiu1;// 全场让球1
	private Double wholeRangqiu2;// 全场让球2
	
	private String wholeDaxiao;// 大小让球数
	private Double wholeDaxiao1;// 全场大小1
	private Double wholeDaxiao2;// 全场大小2
	private Double halfDuying;// 半场独赢
	private Double halfRangqiu1;// 半场让球1
	private Double halfRangqiu2;// 半场让球2
	private Double halfDaxiao1;// 半场大小1
	private Double halfDaxiao2;// 半场大小2
	private String status;// 状态
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

	public Double getWholeDuying() {
		return wholeDuying;
	}

	public Double getWholeRangqiu1() {
		return wholeRangqiu1;
	}

	public Double getWholeRangqiu2() {
		return wholeRangqiu2;
	}

	public Double getWholeDaxiao1() {
		return wholeDaxiao1;
	}

	public Double getWholeDaxiao2() {
		return wholeDaxiao2;
	}

	public Double getHalfDuying() {
		return halfDuying;
	}

	public Double getHalfRangqiu1() {
		return halfRangqiu1;
	}

	public Double getHalfRangqiu2() {
		return halfRangqiu2;
	}

	public Double getHalfDaxiao1() {
		return halfDaxiao1;
	}

	public Double getHalfDaxiao2() {
		return halfDaxiao2;
	}

	public String getStatus() {
		return status;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public void setSaishi(String saishi) {
		this.saishi = saishi;
	}

	public void setWholeDuying(Double wholeDuying) {
		this.wholeDuying = wholeDuying;
	}

	public void setWholeRangqiu1(Double wholeRangqiu1) {
		this.wholeRangqiu1 = wholeRangqiu1;
	}

	public void setWholeRangqiu2(Double wholeRangqiu2) {
		this.wholeRangqiu2 = wholeRangqiu2;
	}

	public void setWholeDaxiao1(Double wholeDaxiao1) {
		this.wholeDaxiao1 = wholeDaxiao1;
	}

	public void setWholeDaxiao2(Double wholeDaxiao2) {
		this.wholeDaxiao2 = wholeDaxiao2;
	}

	public void setHalfDuying(Double halfDuying) {
		this.halfDuying = halfDuying;
	}

	public void setHalfRangqiu1(Double halfRangqiu1) {
		this.halfRangqiu1 = halfRangqiu1;
	}

	public void setHalfRangqiu2(Double halfRangqiu2) {
		this.halfRangqiu2 = halfRangqiu2;
	}

	public void setHalfDaxiao1(Double halfDaxiao1) {
		this.halfDaxiao1 = halfDaxiao1;
	}

	public void setHalfDaxiao2(Double halfDaxiao2) {
		this.halfDaxiao2 = halfDaxiao2;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQiuduiMain() {
		return qiuduiMain;
	}

	public void setQiuduiMain(String qiuduiMain) {
		this.qiuduiMain = qiuduiMain;
	}

	public String getQiuduiClient() {
		return qiuduiClient;
	}

	public void setQiuduiClient(String qiuduiClient) {
		this.qiuduiClient = qiuduiClient;
	}

	public String getWholeRangqiu() {
		return wholeRangqiu;
	}

	public void setWholeRangqiu(String wholeRangqiu) {
		this.wholeRangqiu = wholeRangqiu;
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

	public String getWholeDaxiao() {
		return wholeDaxiao;
	}

	public void setWholeDaxiao(String wholeDaxiao) {
		this.wholeDaxiao = wholeDaxiao;
	}

}