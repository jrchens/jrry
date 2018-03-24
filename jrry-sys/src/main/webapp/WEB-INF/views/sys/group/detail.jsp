<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="sys_group_detail_form" method="post"
           modelAttribute="group" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="群组管理-详情"
           data-options="inline: true" action="${pageContext.request.contextPath}/sys/group/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>群组名</td>
            <td><form:input path="groupName" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="groupName"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${pageContext.request.contextPath}/sys/group/edit?id=${group.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:${group.id}};
                                $.post('${pageContext.request.contextPath}/sys/group/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${pageContext.request.contextPath}/sys/group/index';
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
                    location.href = '${pageContext.request.contextPath}/sys/group/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>


<div class="ext-div-line"></div>
<table id="sys_group_detail_user_datagrid" class="easyui-datagrid"
       data-options="title: '用户列表',
            url: '${pageContext.request.contextPath}/sys/user-group-relation/group/async-query',
            method: 'get',
            queryParams: {groupName:'${group.groupName}'},
            cls: 'ext-datagrid-float-left',
            width:450,
            pagination: true,
            sortName: 'rel.id',
            sortOrder: 'desc',
            singleSelect: true,
            rownumbers: true,
            minHeight: 300,
            striped: true,
            <%--onClickRow: function(index,row){--%>
                <%--if(row.def){--%>
                    <%--$('#sys_group_detail_user_datagrid_delete').linkbutton('disable');--%>
                    <%--$('#sys_group_detail_user_datagrid_tag').linkbutton('disable');--%>
                <%--}else{--%>
                    <%--$('#sys_group_detail_user_datagrid_delete').linkbutton('enable');--%>
                    <%--$('#sys_group_detail_user_datagrid_tag').linkbutton('enable');--%>
                <%--}--%>
            <%--},--%>
            <%--toolbar: [--%>
                        <%--{   iconCls:'ext-icon fa fa-plus',--%>
                            <%--handler:function(){--%>
                                <%--location.href = '${pageContext.request.contextPath}/sys/user-group-relation/user/create/${userVO.username}';--%>
                            <%--}--%>
                        <%--},--%>
                        <%--{--%>
                            <%--id: 'sys_group_detail_user_datagrid_delete',--%>
                            <%--iconCls:'ext-icon fa fa-trash',--%>
                            <%--handler:function(){--%>
                                <%--var thisButton = $(this);--%>
                                <%--var row = $('#sys_group_detail_user_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>

                                <%--$.messager.confirm('确认', '确认删除记录吗?', function(r) {--%>
                                    <%--if (r) {--%>
                                        <%--// var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);--%>
                                        <%--// $('#sys_group_datagrid').datagrid('deleteRow', index);--%>

                                        <%--$('#overlay').show();--%>
                                        <%--thisButton.linkbutton('disable');--%>

                                        <%--var reqData = {id:row.id};--%>
                                        <%--$.post('${pageContext.request.contextPath}/sys/user-group-relation/user/async-delete',reqData,function(data,textStatus,jqXHR){--%>
                                            <%--if(data.success){--%>
                                                <%--$('#sys_group_detail_user_datagrid').datagrid('reload');--%>
                                            <%--}else{--%>
                                                <%--$.messager.show({msg:data.message});--%>
                                            <%--}--%>
                                        <%--},'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){--%>
                                            <%--$('#overlay').hide();--%>
                                            <%--thisButton.linkbutton('enable');--%>
                                        <%--});--%>

                                    <%--}--%>
                                <%--});--%>
                            <%--}--%>
                        <%--},--%>
                        <%--{--%>
                            <%--id: 'sys_group_detail_user_datagrid_tag',--%>
                            <%--iconCls:'ext-icon fa fa-tag',--%>
                            <%--handler:function(){--%>
                                <%--var thisButton = $(this);--%>
                                <%--var row = $('#sys_group_detail_user_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>

                                        <%--// var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);--%>
                                        <%--// $('#sys_group_datagrid').datagrid('deleteRow', index);--%>

                                        <%--$('#overlay').show();--%>
                                        <%--thisButton.linkbutton('disable');--%>

                                        <%--var reqData = {id:row.id};--%>
                                        <%--$.post('${pageContext.request.contextPath}/sys/user-group-relation/user/async-update-def',reqData,function(data,textStatus,jqXHR){--%>
                                            <%--if(data.success){--%>
                                                <%--$('#sys_group_detail_user_datagrid').datagrid('reload');--%>
                                            <%--}else{--%>
                                                <%--$.messager.show({msg:data.message});--%>
                                            <%--}--%>
                                        <%--},'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){--%>
                                            <%--$('#overlay').hide();--%>
                                            <%--thisButton.linkbutton('enable');--%>
                                        <%--});--%>

                            <%--}--%>
                        <%--}--%>
            <%--],--%>
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'username'">用户名</th>
        <th data-options="field:'viewname'">显示名</th>
        <th data-options="field:'def'">默认组</th>
    </tr>
    </thead>
</table>