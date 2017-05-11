package com.newwing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_param")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "biz_param_sequence")
public class ParamBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;// 主键ID
	private Double paramValue;// 参数值
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getParamValue() {
		return paramValue;
	}

	public void setParamValue(Double paramValue) {
		this.paramValue = paramValue;
	}
	
}