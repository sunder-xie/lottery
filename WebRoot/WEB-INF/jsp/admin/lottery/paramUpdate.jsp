<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<h2>项目管理 >> 参数设定</h2>
				<div class="row-fluid">
					<div class="row-fluid">
						<form id="userCriteriaQuery" method="post" action="updateParam.do"  >
							<input type="hidden" value="${paramBO.id}" name ="id" />
							<table id="table_report" class="table table-striped table-bordered table-hover"
									style="margin:10px 0 0 0; padding-top: 0px">
				                <tbody>
				                	<tr class="odd gradeX">
				                    	<td class="center">参数值：</td>
										<td class="center"><input type="text" name ="paramValue" value="${paramBO.paramValue}" /></td>
			                    	</tr>
			                    	<tr class="odd gradeX">
										<td class="center" colspan="2">
											<input type="submit" value ="提交" class='btn btn-mini btn-info btn-rbg' />
											<a class='btn btn-mini btn-danger btn-rbg' onclick="history.go(-1)">返回</a>
										</td>
			                    	</tr>
				           		</tbody>
		                    </table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>