<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>上传文件</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/loadDoc" method="post" enctype="multipart/form-data">
    <input type="file" name="file" value="请选择文件">
    <input type="submit" value="上传" />
  </form>
  </body>
</html>
