<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>
<ul id="template_sys_left_menu">
    <%--<shiro:hasRole name="admin">--%>
        <%--<span style="display: block"><a href="${pageContext.request.contextPath}/admin/user/index">用户管理</a></span>--%>
        <%--<span style="display: block"><a href="${pageContext.request.contextPath}/admin/group/index">群组管理</a></span>--%>
        <%--<span style="display: block"><a href="${pageContext.request.contextPath}/admin/role/index">角色管理</a></span>--%>
        <%--<span style="display: block"><a href="${pageContext.request.contextPath}/admin/permission/index">权限管理</a></span>--%>
        <%--<span style="display: block"><a href="${pageContext.request.contextPath}/admin/config/index">配置管理</a></span>--%>
    <%--</shiro:hasRole>--%>
</ul>
<script type="text/javascript">
    var template_sys_left_menu_data = [
        <shiro:hasRole name="admin">
        {
            "text":"系统管理",
            "children":[
                {"text":"用户管理","attributes":{url:'${pageContext.request.contextPath}/sys/user/index'}},
                {"text":"群组管理","attributes":{url:'${pageContext.request.contextPath}/sys/group/index'}},
                {"text":"角色管理","attributes":{url:'${pageContext.request.contextPath}/sys/role/index'}},
                {"text":"权限管理","attributes":{url:'${pageContext.request.contextPath}/sys/permission/index'}},
                {"text":"配置管理","attributes":{url:'${pageContext.request.contextPath}/sys/config/index'}},
                {"text":"字典管理","attributes":{url:'${pageContext.request.contextPath}/sys/dictionary/index'}},
            ]
        }
        </shiro:hasRole>
        <shiro:hasAnyRoles name="admin,user">
        ,{
            "text":"代码样例",
            "children":[
                {"text":"单表操作","attributes":{url:'${pageContext.request.contextPath}/sys/user/index'}},
                {"text":"一对多","attributes":{url:'${pageContext.request.contextPath}/sys/group/index'}},
                {"text":"多对多","attributes":{url:'${pageContext.request.contextPath}/sys/role/index'}},
                {"text":"树","attributes":{url:'${pageContext.request.contextPath}/sys/permission/index'}},
            ]
        }
        </shiro:hasAnyRoles>
    ];

    $('#template_sys_left_menu').tree({
        data:template_sys_left_menu_data,
        lines:true,
        onClick: function (node) {
            if (node.attributes.url) location.href = node.attributes.url;
        }
    });

</script>