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
		<script >
			function del(id) {
				if (confirm("您确定要删除该记录吗？")) {
					location.href="deleteRelative.do?relativeId=" + id;
				} else {
				}
			}
		</script>
	</head>
	
	<body id="wrapper">
		<div class="container-fluid animated bounceIn cardBg" id="main-container" >
			<div id="page-content" class="clearfix " style="background:#fff;margin:20px 31px; padding-top: 0px">
				<h2>当日名字匹配：当前采集【${relativeList.size()}】场</h2>
				<div class="row-fluid">
					<div class="row-fluid">
						<form id="userCriteriaQuery" method="post">
<!-- 							<a class='btn btn-mini btn-info btn-rbg' style="margin-top:0"  href="relativeToAdd.do">新增</a> -->
							<table id="table_report" class="table table-striped table-bordered table-hover"
									style="margin:10px 0 0 0; padding-top: 0px">
		                    	<thead>
		                        	<tr>
										<th class="center">葡京球队名称</th>
										<th class="center">金沙球队名称</th>
										<th class="center">更新时间</th>
										<th class="center">操作</th>
		                            </tr>
		                   		</thead>
		                        <c:choose>
									<c:when test="${!empty relativeList}">
				                    	<tbody>
				                        	<c:forEach items="${relativeList}" var="relative" varStatus="vs">
				                            	<tr class="odd gradeX">
													<td class="center">${relative.qiuduiBewin}</td>
													<td class="center">${relative.qiuduiJinsha}</td>
													<td class="center">${relative.updateTime}</td>
													<td class="center">
<!-- 														<a class='btn btn-mini btn-danger btn-rbg' style="margin-top:0"  -->
<%-- 																onclick="del(${relative.id});">删除</a> --%>
														<a class='btn btn-mini btn-info btn-rbg' style="margin-top:0" 
																href="toUpdateRelative.do?relativeId=${relative.id}">修改</a>
													</td>
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