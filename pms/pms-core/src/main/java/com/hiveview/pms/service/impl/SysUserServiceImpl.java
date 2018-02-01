package com.hiveview.pms.service.impl;

import com.hiveview.base.common.BaseConstants;
import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.base.util.encry.EncryUtils;
import com.hiveview.pms.dao.SysUserMapper;
import com.hiveview.pms.entity.sys.SysUser;
import com.hiveview.pms.service.SysUserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseCrudServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public CrudMapper init() {
        return sysUserMapper;
    }

    @Override
    @Transactional
    public void modifyData(SysUser dto) {
        Assert.notNull(dto,"参数不能为空");
        Assert.isTrue(null != dto.getId() && null != dto.getStatus(),"ID和修改状态不能为空");
        //状态只有1,2
        Assert.isTrue(dto.getStatus() == BaseConstants.ENABLE_STATUS || dto.getStatus() == BaseConstants.DISABLE_STATUS,"状态数据不正确");
        SysUser old=sysUserMapper.selectByPrimaryKey(dto.getId());
        Assert.notNull(old,"ID无效");
        Assert.isTrue(dto.getStatus() != old.getStatus(),"数据已经被修改");
        sysUserMapper.updateByPrimaryKeySelective(dto);
    }

    @Override
    @Transactional
    public int saveData(SysUser entity) throws ServiceException {
        Assert.notNull(entity,"参数不能为空");
        if(null != entity.getId()){
            if(StringUtils.hasText(entity.getPassword())){
                //有修改密码
                entity.setPassword(EncryUtils.encryPwd(entity.getPassword()));
            }else{
                entity.setPassword(null);//因为会传 空字符串 过来
            }
            return sysUserMapper.updateByPrimaryKeySelective(entity);
        }else{
            //新增
            entity.setPassword(EncryUtils.encryPwd(entity.getPassword()));
            entity.setStatus(BaseConstants.ENABLE_STATUS);//默认启用状态
            entity.setUsername(entity.getEmail());
            Map<String,Object>  map=new HashMap<>();
            map.put("username",entity.getEmail());
            int count=sysUserMapper.selectCount(map);
            Assert.isTrue(count == 0,"该电子邮箱已经注册过了");
            return sysUserMapper.insert(entity);
        }
    }
}