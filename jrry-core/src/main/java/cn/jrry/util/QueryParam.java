package cn.jrry.util;

import com.google.common.collect.Maps;

import java.util.Map;

public class QueryParam {
    
    public static Map<String, Object> param(){
	
	Map<String, Object> queryParam = Maps.newHashMap();
	queryParam.put("page", 1);
	queryParam.put("rows", 10000);
	queryParam.put("sort", "id");
	queryParam.put("order", "desc");
	
	return queryParam;
    }

}
