<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>

<table id="sys_user_user_role_create_datagrid" class="easyui-datagrid"
       data-options="title: '角色列表',
            url: '${pageContext.request.contextPath}/sys/role/async-query',
            method: 'get',
            queryParams: {exclusiveRoleNames:'${roleNames}'},
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: [
                        {
                            iconCls:'ext-icon fa fa-floppy-o',
                            handler:function(){
                                var rows = $('#sys_user_user_role_create_datagrid').datagrid('getChecked');
                                if(rows.length == 0){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                var roleNames = [];
                                $.each(rows,function(idx,ele){
                                    roleNames.push(ele.roleName);
                                });

                                var form = $('#sys_user_user_role_create_form');
                                $('#roleNames',form).val(roleNames.join(','));
                                form.submit();

                            }
                        },{
                            iconCls:'ext-icon fa fa-arrow-left',
                            handler:function(){
                                location.href = '${pageContext.request.contextPath}/sys/user/detail?id=${user.id}';
                            }
                        }
            ],
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            }
       ">
    <thead>
    <tr>
        <th data-options="field:'_',checkbox:true">#</th>
        <th data-options="field:'roleName'">角色名</th>
        <th data-options="field:'viewname'">显示名</th>
    </tr>
    </thead>
</table>

<form id="sys_user_user_role_create_form" method="post" action="${pageContext.request.contextPath}/sys/user-role-relation/user/save/${user.username}">
    <input type="hidden" id="roleNames" name="roleNames" value="">
</form>