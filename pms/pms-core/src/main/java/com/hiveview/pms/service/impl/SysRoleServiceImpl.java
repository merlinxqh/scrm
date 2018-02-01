package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.base.util.id.IdWorker;
import com.hiveview.pms.dao.SysRoleMapper;
import com.hiveview.pms.dto.RoleResourceDto;
import com.hiveview.pms.entity.sys.SysRole;
import com.hiveview.pms.entity.sys.SysRoleResource;
import com.hiveview.pms.service.SysRoleResourceService;
import com.hiveview.pms.service.SysRoleService;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseCrudServiceImpl<SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleResourceService roleResourceService;

    @Override
    public CrudMapper init() {
        return sysRoleMapper;
    }

    @Override
    @Transactional
    public int saveData(SysRole entity) throws ServiceException {
        Assert.notNull(entity);
        Assert.hasText(entity.getName());
        if(null != entity.getId()){
            return sysRoleMapper.updateByPrimaryKeySelective(entity);
        }else{
            //新增角色
            entity.setCode(IdWorker.getStringCode());
            entity.setStatus(SysRole.ROLE_STATUS_ENABLE);//默认启用
            entity.setRoleType(SysRole.ROLE_TYPE_COMMON);
            return sysRoleMapper.insert(entity);
        }
    }

    @Override
    @Transactional
    public int modifyData(SysRole role) {
        Assert.notNull(role,"参数不能为空");
        Assert.isTrue(null != role.getId() && null != role.getStatus(),"缺失参数ID或者修改状态");
        //状态只有 1, 或者2
        Assert.isTrue(role.getStatus() == SysRole.ROLE_STATUS_ENABLE || role.getStatus() == SysRole.ROLE_STATUS_DISABLE,"状态参数无效");
        SysRole old=sysRoleMapper.selectByPrimaryKey(role.getId());
        Assert.notNull(old,"ID无效");
        Assert.isTrue(old.getStatus() != role.getStatus(),"该数据已经被修改");
        return sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional
    public void roleResourceSave(RoleResourceDto dto) {
       Assert.notNull(dto,"参数不能为空");
       Assert.hasText(dto.getRoleCode(),"角色编码不能为空");
       //先删除 之前数据
       roleResourceService.deleteByRoleCode(dto.getRoleCode());
       if(StringUtils.hasText(dto.getResourceCodes())){
           for(String resCode:dto.getResourceCodes().split(",")){
               if(StringUtils.hasText(resCode)){
                   SysRoleResource ror=new SysRoleResource();
                   ror.setResourceCode(resCode);
                   ror.setRoleCode(dto.getRoleCode());
                   roleResourceService.saveData(ror);
               }
           }
       }
    }
}