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
@Table(name="biz_relative")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "biz_relative_sequence")
public class RelativeBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private String qiuduiBewin;// 必赢球队
	private String qiuduiJinsha;// 金沙球队
	private String qiuduiXianshi;// 显示球队名称
	private String qiuduiNo;// 球队编号
	private String shijian;// 创建时间
	private Date updateTime;// 更新时间
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQiuduiBewin() {
		return qiuduiBewin;
	}

	public String getQiuduiJinsha() {
		return qiuduiJinsha;
	}

	public String getQiuduiXianshi() {
		return qiuduiXianshi;
	}

	public String getQiuduiNo() {
		return qiuduiNo;
	}

	public void setQiuduiBewin(String qiuduiBewin) {
		this.qiuduiBewin = qiuduiBewin;
	}

	public void setQiuduiJinsha(String qiuduiJinsha) {
		this.qiuduiJinsha = qiuduiJinsha;
	}

	public void setQiuduiXianshi(String qiuduiXianshi) {
		this.qiuduiXianshi = qiuduiXianshi;
	}

	public void setQiuduiNo(String qiuduiNo) {
		this.qiuduiNo = qiuduiNo;
	}

	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}