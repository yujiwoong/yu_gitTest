<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .cls1 {text-decoration:none;}
   .cls2 {text-align:center; font-size:30px;}
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${empty articlesList }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${!empty articlesList}" >
  	<!--articlesList로 포워딩된 글 목록을 <forEach>태그를 이용해 표시합니다.  -->
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" >
    <tr align="center">
    <!--<forEach>태그의 varStatus의 count속성을 이용해 글 번호를 1부터 자동으로 표시합니다.  -->
	<td width="5%">${articleNum.count}</td>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	  <!--왼쪽으로 30px만큼 여백을 준 후 글 제목들을 표시합니다.  -->
	  <span style="padding-right:30px"></span>
	   <c:choose>
	   	  <!--level값이 1보다 큰 경우는 자식글이므로 level값만큼부모글 밑에 공백으로 들여쓰기하여 자식 글임을 표시합니다.  -->
	      <c:when test='${article.level > 1 }'>  
	      	<!--부모 글 기준으로 왼쪽 여백을 level값만큼 채워 답글을 부모 글에 대해 들여쓰기를합니다.  -->
	         <c:forEach begin="1" end="${article.level }" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
	         	   <!--공백 다음에 자식 글을 표시합니다.  -->
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <c:otherwise>
	            <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%"><fmt:formatDate value="${article.writeDate}" /></td> 
	</tr>
    </c:forEach>
     </c:when>
    </c:choose>
</table>
<a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a>
</body>
</html>