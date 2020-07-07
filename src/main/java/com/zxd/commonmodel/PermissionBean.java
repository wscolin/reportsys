package com.zxd.commonmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 数据权限字段
 * UserVo DATA_PERMISSIONS
 * 解析json实体类
 */
public class PermissionBean {

	class Permission{
		private String RES_KEY;
		private String NAME;
		private String ICONCLS;
		private String ACTION;
		public String getRES_KEY() {
			return RES_KEY;
		}
		public void setRES_KEY(String rES_KEY) {
			RES_KEY = rES_KEY;
		}
		public String getNAME() {
			return NAME;
		}
		public void setNAME(String nAME) {
			NAME = nAME;
		}
		public String getICONCLS() {
			return ICONCLS;
		}
		public void setICONCLS(String iCONCLS) {
			ICONCLS = iCONCLS;
		}
		public String getACTION() {
			return ACTION;
		}
		public void setACTION(String aCTION) {
			ACTION = aCTION;
		}
		
	}

	public static String getEcuserPermission(UserVO user){
		String ecuserper="BANK01";
		try{
			String permission= user.getDATA_PERMISSIONS();
			List<Permission> permissions=null;
			try{
				permissions=new Gson().fromJson(permission, new TypeToken<List<Permission>>(){}.getType());
			}catch(Exception e){
				e.printStackTrace();
			}
			if(null!=permissions){
				for(Permission Permission:permissions){
					String ecuserpertemp =Permission.getRES_KEY();
					if(Integer.parseInt(ecuserpertemp.substring(5,ecuserpertemp.length()))<Integer.parseInt(ecuserper.substring(5,ecuserper.length()))){
						ecuserper=ecuserpertemp;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ecuserper;
	}
}

