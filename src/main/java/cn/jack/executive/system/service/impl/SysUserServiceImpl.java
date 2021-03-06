package cn.jack.executive.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jack.executive.system.dao.SysUserDao;
import cn.jack.executive.system.model.SysUser;
import cn.jack.executive.system.model.vo.UserSearchVo;
import cn.jack.executive.system.service.SysUserService;

/**
 * 用户管理模块服务实现类
 * @author JackChen
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SQLManager sqlManager;
	/**
	 * 根据条件查询用户列表
	 */
	@Override
	public PageQuery<SysUser> findUserByPage(UserSearchVo userSearchVo) {
		PageQuery<SysUser> query = new PageQuery<SysUser>();
		query.setPageNumber(userSearchVo.getPage());
		query.setPageSize(userSearchVo.getRows());
		query.setParas(userSearchVo);
		if(StringUtils.isNotBlank(userSearchVo.getSidx())){			
			query.setOrderBy(userSearchVo.getSidx()+" "+userSearchVo.getSord());
		}
		sqlManager.pageQuery("SysUser.findUserBy", SysUser.class, query);
		return query;
	}

	/**
	 * 根据用户ID查询指定用户信息
	 */
	@Override
	public SysUser findUserById(String uid) {
		return sysUserDao.single(uid);
	}

	/**
	 * 根据用户ID，作废用户
	 */
	@Override
	public boolean deleteByKey(String id) {
		SysUser user = new SysUser();
		user.setId(Integer.parseInt(id));
		user.setStatus("-1");
		return sysUserDao.updateById(user)>0;
	}

	@Override
	public void saveUser(SysUser user) {
		if(user.getId()==null){
			sysUserDao.insert(user);
		}else{
			sysUserDao.updateById(user);
		}
	}

}
