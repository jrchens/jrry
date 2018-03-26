<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="sys_permission_detail_form" method="post"
           modelAttribute="permission" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="权限管理-详情"
           data-options="inline: true" action="${pageContext.request.contextPath}/sys/permission/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>类别</td>
            <td><form:input path="category" cssClass="easyui-combobox"
                            data-options="
                            fit:true,
                            url:'${pageContext.request.contextPath}/sys/dictionary/async-get',
                            method:'get',
                            queryParams:{parentCode:'SYS_MODULES'},
                            textField:'name',
                            loadFilter:function(data){
                                return data.data;
                            },readonly:true"/><form:errors
                    path="category"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
        </tr>

        <tr>
            <td>权限名</td>
            <td colspan="2"><form:input path="permission" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="permission"/></td>
            <td>&nbsp;</td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${pageContext.request.contextPath}/sys/permission/edit?id=${permission.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_permission_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_permission_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:${permission.id}};
                                $.post('${pageContext.request.contextPath}/sys/permission/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${pageContext.request.contextPath}/sys/permission/index';
                                    }else{
                                        $.messager.show({msg:data.message});
                                    }
                                },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                    $('#overlay').hide();
                                    thisButton.linkbutton('enable');
                                    thisButton.linkbutton({text:'删除'});
                                });

                            }
                        });

                    }">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = '${pageContext.request.contextPath}/sys/permission/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>
