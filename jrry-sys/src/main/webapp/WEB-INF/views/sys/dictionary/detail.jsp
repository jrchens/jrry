<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="sys_dictionary_detail_form" method="post"
           modelAttribute="dictionary" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="字典管理-详情"
           data-options="inline: true" action="${pageContext.request.contextPath}/sys/dictionary/async-remove">
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
                    path="code"/></td>
            <td>名称</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="name"/></td>
        </tr>
</c:if>

<c:if test="${not empty dictionary.parentCode }">
        <tr>
            <td>值</td>
            <td><form:input path="value" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="value"/></td>
            <td>名称</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="name"/></td>
        </tr>
</c:if>        
        
        <tr>
            <td>禁用</td>
            <td><span class="easyui-switchbutton" data-options="
                            checked:${dictionary.disabled},
                            onChange:function(checked){
                            var form = $('#sys_dictionary_create_form');
                            $('#disabled',form).val(checked);
                        },readonly:true"></span><form:errors path="disabled"/></td>
            <td>排序</td>
            <td><form:input path="srt" cssClass="easyui-numberbox"
                            data-options="min:1,required:true,fit:true,readonly:true"/><form:errors
                    path="srt"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${pageContext.request.contextPath}/sys/dictionary/edit?id=${dictionary.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:${dictionary.id}};
                                $.post('${pageContext.request.contextPath}/sys/dictionary/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${pageContext.request.contextPath}/sys/dictionary/index';
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
                    location.href = '${pageContext.request.contextPath}/sys/dictionary/back?parentCode=${dictionary.parentCode }';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>


<c:if test="${empty  dictionary.parentCode}">
<div class="ext-div-line"></div>
<table id="sys_dictionary_detail_datagrid" class="easyui-datagrid"
       data-options="title: '字典管理-列表',
            url: '${pageContext.request.contextPath}/sys/dictionary/async-query',
            method: 'get',
            sortName: 'srt',
            sortOrder: 'asc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            queryParams:{parentCode:'${dictionary.code }'},
            onDblClickRow: function(index,row){
                location.href = '${pageContext.request.contextPath}/sys/dictionary/detail?id='+row.id;
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#sys_dictionary_detail_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = '${pageContext.request.contextPath}/sys/dictionary/create?parentCode=${dictionary.code }';
                            }
                        },
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#sys_dictionary_detail_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = '${pageContext.request.contextPath}/sys/dictionary/edit?id='+row.id;
                            }
                        },
                        {   iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#sys_dictionary_detail_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                                    if (r) {
                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${pageContext.request.contextPath}/sys/dictionary/async-remove',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#sys_dictionary_detail_datagrid').datagrid('reload');
                                            }else{
                                                $.messager.show({msg:data.message});
                                            }
                                        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                            $('#overlay').hide();
                                            thisButton.linkbutton('enable');
                                        });

                                    }
                                });
                            }
                        },
                        {   iconCls:'ext-icon fa fa-eye',
                            handler:function(){
                                var row = $('#sys_dictionary_detail_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = '${pageContext.request.contextPath}/sys/dictionary/detail?id='+row.id;
                            }
                        }
                    ]
                });

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'name'">名称</th>
        <th data-options="field:'value'">值</th>
        <th data-options="field:'srt'">排序</th>
        <th data-options="field:'disabled'">禁用</th>
    </tr>
    </thead>
</table>
</c:if>
