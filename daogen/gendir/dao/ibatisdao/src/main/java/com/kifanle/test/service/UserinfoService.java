package com.kifanle.test.service;

import java.util.List;


import com.kifanle.test.pojo.UserinfoDO;
import com.kifanle.test.common.Result;
import com.kifanle.test.query.UserinfoQuery;
/**
 * @author microboss
 * @since 2013-05-04
 */
public interface UserinfoService{

	public Long addUserinfoDO(UserinfoDO userinfo);
	public UserinfoDO getUserinfoDObyKey(long userid);
	
    public List<UserinfoDO> getUserinfoDOsByKeys(List<Long> userids);
	
	public Integer deleteByKey(Long userid);
	
    public Integer deleteByKeys(List<Long> userids);
	
	public Integer updateUserinfoDO(UserinfoDO userinfo);
	/**
	 * 成批更新对象，需要设置对象中的keys。
	 */
    public Integer updateUserinfoDOsByKeys(UserinfoDO userinfo);

    			 
	 public UserinfoDO getUserInfoByEmail(String mobile,Byte gender);
					
	public Result<UserinfoDO> getUserinfoDOListWithPage(UserinfoQuery userinfoQuery);
}
