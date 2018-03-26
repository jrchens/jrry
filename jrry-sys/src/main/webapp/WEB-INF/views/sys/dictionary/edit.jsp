<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="sys_dictionary_edit_form" method="post"
           modelAttribute="dictionary" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="字典管理-编辑"
           data-options="inline: true" action="${pageContext.request.contextPath}/sys/dictionary/update">
    <form:hidden path="id"/>
    <form:hidden path="parentCode"/>
    <form:hidden path="disabled"/>
    <table class="ext-data-table">
        <tbody>

<c:if test="${empty dictionary.parentCode }">      
        <tr>
            <td>代码</td>
            <td><form:input path="code" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="code"/><form:hidden path="value"/></td>
            <td>名称</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="name"/></td>
        </tr>
</c:if>

<c:if test="${not empty dictionary.parentCode }">
        <tr>
            <td>值</td>
            <td><form:input path="value" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="value"/><form:hidden path="code"/></td>
            <td>名称</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="name"/></td>
        </tr>
</c:if>

        <tr>
            <td>禁用</td>
            <td><span class="easyui-switchbutton" data-options="
                            checked:${dictionary.disabled},onChange:function(checked){
                            var form = $('#sys_dictionary_edit_form');
                            $('#disabled',form).val(checked);
                        }"></span><form:errors path="disabled"/></td>
            <td>排序</td>
            <td><form:input path="srt" cssClass="easyui-numberbox"
                            data-options="min:1,required:true,fit:true"/><form:errors
                    path="srt"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                    $('#overlay').show();
                    var thisButton = $(this);
                    thisButton.linkbutton('disable');
                    thisButton.linkbutton({text:'加载中...'});
                    var form = $('#sys_dictionary_edit_form');
                    form.submit();
                    }">更新</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = '${pageContext.request.contextPath}/sys/dictionary/back?parentCode=${dictionary.parentCode }';
                    }">返回</a>
            </td>
        </tr>
        </tbody>
    </table>

</form:form>