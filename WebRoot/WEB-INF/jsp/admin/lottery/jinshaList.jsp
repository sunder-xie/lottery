﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setHeader("refresh","30");
%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<meta charset="utf-8" />
		<title></title>
		<%@ include file="../../common/top.jsp"%>
	</head>
	
	<body id="wrapper">
		<div class="container-fluid animated bounceIn cardBg" id="main-container" >
			<div id="page-content" class="clearfix " style="background:#fff;margin:20px 31px; padding-top: 0px">
				<h2>金沙数据：当前采集【${jinshaList.size()}】场</h2>
				<div class="row-fluid">
					<div class="row-fluid">
						<form id="userCriteriaQuery" method="post" action="upload.do" enctype="multipart/form-data">
<!-- 							<input type="hidden" value="jinsha" name="type" /> -->
<!-- 							<input type="file" name="uploadFile" /> -->
<!-- 							<input type="submit" value ="导入Excel数据" class='btn btn-mini btn-info btn-rbg' /> -->
<!-- 							<a style="margin-top:0;margin-left:500px" href="downloadTemplate.do?type=jinsha">下载模板</a> -->
							<table id="table_report" class="table table-striped table-bordered table-hover"
									style="margin:10px 0 0 0; padding-top: 0px">
		                    	<thead>
		                        	<tr>
		                    			<th class="center" rowspan="2">日期</th>
		                    			<th class="center" rowspan="2">开始时间</th>
		                    			<th class="center" rowspan="2">主队名称</th>
										<th class="center" rowspan="2">客队名称</th>
										<th class="center" colspan="3">让球</th>
										<th class="center" colspan="3">大小</th>
										<th class="center" rowspan="2">更新时间</th>
		                            </tr>
		                    		<tr>
										<th class="center">让球数</th>
										<th class="center">主水</th>
										<th class="center">客水</th>
										<th class="center">让球数</th>
										<th class="center">主水</th>
										<th class="center">客水</th>
		                            </tr>
		                   		</thead>
		                        <c:choose>
									<c:when test="${!empty jinshaList}">
				                    	<tbody>
				                        	<c:forEach items="${jinshaList}" var="jinsha" varStatus="vs">
				                            	<tr class="odd gradeX">
				                            		<td class="center">${jinsha.shijian}</td>
													<td class="center">${jinsha.startTimeStr}</td>
													<td class="center">${jinsha.qiuduiMain}</td>
													<td class="center">${jinsha.qiuduiClient}</td>
													<td class="center">${jinsha.wholeRangqiu}</td>
													<td class="center">${jinsha.wholeRangqiu1}</td>
													<td class="center">${jinsha.wholeRangqiu2}</td>
													<td class="center">${jinsha.wholeDaxiao}</td>
													<td class="center">${jinsha.wholeDaxiao1}</td>
													<td class="center">${jinsha.wholeDaxiao2}</td>
													<td class="center">${jinsha.updateTime}</td>
				                                </tr>
				                            </c:forEach>
				                        </tbody>
				                    </c:when>
									<c:otherwise>
										<tbody>
											<tr>
												<td colspan="100" style="text-align: center;">
													<font color="red">此页没有相关数据！</font>
												</td>
											</tr>
										</tbody>
									</c:otherwise>
								</c:choose>
		                    </table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>