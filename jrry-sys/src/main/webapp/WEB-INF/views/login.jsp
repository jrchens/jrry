<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-Hans">

<head>
    <meta charset="UTF-8">
    <title>用户登陆</title>

    <%@ include file="./common/css.jsp" %>
    <%@ include file="./common/js.jsp" %>

</head>

<body>
<div id="overlay"></div>
<%--<c:if test="${not empty create_error}">--%>
<%--<div class="easyui-panel" data-options="closable:true" title="错误信息"--%>
<%--style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${create_error}</div>--%>
<%--</c:if>--%>


<%--<c:remove var="shiroSavedRequest"/>--%>
<%--<c:remove var="relogin"/>--%>

<form:form id="login_form" method="post"
           modelAttribute="loginUser" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-window" title="用户登陆"
           data-options="width:420, height:150, collapsible:false,minimizable:false,maximizable:false,closable:false,draggable:false,resizable:false"
           action="${pageContext.request.contextPath}/login">

    <table class="ext-data-table" style="width:400px;">
        <tbody>

        <tr>
            <td>用户名</td>
            <td><form:input path="username" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,value:'admin'"/><form:errors
                    path="username"/></td>
        </tr>
        <tr>
            <td>密&#12288;码</td>
            <td><form:password path="password" cssClass="easyui-passwordbox"
                               data-options="required:true,fit:true"/><form:errors
                    path="password"/></td>
        </tr>

        <tr>
            <td colspan="2">

                <c:if test="${not empty param.t}">
                    <span style="float: left; color:red; padding-left: 24px;">
                    <c:if test="${param.t == 1}">username not exists</c:if>
                    <c:if test="${param.t == 2}">password verify failed</c:if>
                    <c:if test="${param.t == 3 or param.t == 4 or param.t == 5}">user login failed</c:if>
                    </span>
                </c:if>

                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-user-circle', width: 80,
                        onClick: function(){

                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});

                        var form = $('#login_form');
                        var username = $('#username',form).textbox('getValue');
                        var password = $('#password',form).passwordbox('getValue');
                        password = CryptoJS.MD5(username.concat(':').concat(CryptoJS.MD5(password).toString())).toString();
                        $('#password',form).passwordbox('setValue',password);
                        form.submit();
                        }">登陆</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-eraser', width: 80,
                        onClick: function(){
                        var form = $('#login_form');
                        form[0].reset();
                        }">重置</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>

</body>

</html>