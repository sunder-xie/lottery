<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<h2>最终大小结果：【${resultList.size()}】场有效</h2>
				<div class="row-fluid">
					<div class="row-fluid">
						<form id="userCriteriaQuery" method="post" action="queryResultList.do">
							结果参数：<input type="text" name ="paramValue" value="${paramValue}" />
							<button class='btn btn-mini btn-info btn-rbg' type="submit">查询</button>
							<font style="color:red;">红色字体表示葡京的数据</font>
							<table id="table_report" class="table table-striped table-bordered table-hover"
									style="margin:10px 0 0 0; padding-top: 0px">
		                    	<thead>
		                    		<tr>
		                    			<th class="center">日期</th>
		                    			<th class="center">开始时间</th>
		                    			<th class="center">主队名称</th>
										<th class="center">客队名称</th>
										<th class="center">让球数</th>
										<th class="center">主水</th>
										<th class="center">客水</th>
										<th class="center">全场结果</th>
										<th class="center">更新时间</th>
		                            </tr>
		                   		</thead>
		                        <c:choose>
									<c:when test="${!empty resultList}">
				                    	<tbody>
				                        	<c:forEach items="${resultList}" var="result" varStatus="vs">
				                            	<tr class="odd gradeX">
				                            		<td class="center">${result.shijian}</td>
													<td class="center">${result.startTimeStr}</td>
													<td class="center">${result.qiuduiBewin}</td>
													<td class="center">${result.qiuduiJinsha}</td>
													<td class="center">${result.wholeDaxiao}</td>
													<td class="center"><c:if test="${result.saishi == 'BEWIN'}"><p style="color:red;"></c:if>${result.wholeDaxiao1}<c:if test="${result.saishi == 'BEWIN'}"></p></c:if></td>
													<td class="center"><c:if test="${result.saishi == 'JINSHA'}"><p style="color:red;"></c:if>${result.wholeDaxiao2}<c:if test="${result.saishi == 'JINSHA'}"></p></c:if></td>
													<td class="center">${result.jieguoWholeDaxiao}</td>
													<td class="center">${result.updateTime}</td>
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