package com.kifanle.test.service.impl;

import java.util.List;
import java.util.Collections;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.kifanle.test.pojo.UserinfoDO;
import com.kifanle.test.dao.UserinfoDAO;
import com.kifanle.test.common.Result;
import com.kifanle.test.query.UserinfoQuery;
import com.kifanle.test.service.UserinfoService;
/**
 * @author microboss
 * @since 2013-05-04
 */
@Service
public class UserinfoServiceImpl implements UserinfoService{

    private static final Log log = LogFactory.getLog(UserinfoServiceImpl.class);

	@Resource
	UserinfoDAO userinfoDAO;
	
	public Long addUserinfoDO(UserinfoDO userinfo){
		try{
			return userinfoDAO.addUserinfoDO(userinfo);
		}catch(SQLException e){
			log.error("dao addUserinfoDO error.userid:"+userinfo.toString(), e);
		}
		return 0L;
	}
	
	public UserinfoDO getUserinfoDObyKey(long userid){
		try{
			return userinfoDAO.getUserinfoDObyKey(userid);
		}catch(SQLException e){
			log.error("dao getUserinfoDObyKey error.userid:"+userid, e);
		}
		return null;
	}
	
    public List<UserinfoDO> getUserinfoDOsByKeys(List<Long> userids){
		if (userids.isEmpty()) {
			log.error("IllegalArgument erorr.keys is null");
			return Collections.emptyList();
		}
		try{
			return  (List<UserinfoDO>)userinfoDAO.getUserinfoDOsByKeys(userids);
		}catch(SQLException e){
			log.error("dao getUserinfoDOsByKeys erorr."+userids,e);
		}
		return Collections.emptyList();
	}	
	
	public Integer deleteByKey(Long userid){
		try{
			return userinfoDAO.deleteByKey(userid);
		}catch(SQLException e){
		    log.error("dao deleteByKey error. userid:" + userid, e);
		}
		return -1;
	}
	
    public Integer deleteByKeys(List<Long> userids){
		try{
		    return userinfoDAO.deleteByKeys(userids);
		}catch(SQLException e){
		     log.error("dao deleteByKeys error. userids:" + userids, e);
		}
		return -1;
	}
	
	public Integer updateUserinfoDO(UserinfoDO userinfo){
	    try{
		    return userinfoDAO.updateUserinfoDO(userinfo);
		}catch(SQLException e){
		   log.error("dao updateUserinfoDO error.userinfo:"+userinfo.toString(), e);
	    }
		return -1;
	}
	/**
	 * 成批更新对象，需要设置对象中的keys。
	 */
    public Integer updateUserinfoDOsByKeys(UserinfoDO userinfo){
		try{
		    return userinfoDAO.updateUserinfoDOsByKeys(userinfo);
		}catch(SQLException e){
		   log.error("dao updateUserinfoDOsByKeys error.userinfo:"+userinfo.toString(), e);
	    }
		return -1;
	}

    			 
	 public UserinfoDO getUserInfoByEmail(String mobile,Byte gender){
		try{
		    return (UserinfoDO)userinfoDAO.getUserInfoByEmail(mobile,gender);
		}catch(SQLException e){
			log.error("dao getUserInfoByEmail error",e);
		}
		return null;
	}	
					
		public Result<UserinfoDO> getUserinfoDOListWithPage(UserinfoQuery userinfoQuery){
		Result<UserinfoDO> rs = userinfoDAO.getUserinfoDOListWithPage(userinfoQuery);
		if(!rs.isSuccess()){
			log.error("get Userinfo error."+rs.getErrorMsg());
		}
		return rs;
	}	
    }
