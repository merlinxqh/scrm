package com.hiveview.admin.controller.pms;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.admin.rpc.pms.SysRoleApiConsumer;
import com.hiveview.base.util.response.RespMsg;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.dto.RoleResourceDto;
import com.hiveview.pms.dto.SysRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by leo on 2017/11/6.
 * 系统角色管理
 */
@RequestMapping("/pms/role")
@Controller
public class SysRoleController extends BaseController{

    @Autowired
    private SysRoleApiConsumer roleApiConsumer;


    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("pms/role/role_list");
    }

    @RequestMapping(value = "listData")
    public @ResponseBody
    PageDto listData(PageDto page, SysRoleDto dto){
        return roleApiConsumer.getPageData(page,dto);
    }

    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public ModelAndView edit(ModelMap model,SysRoleDto dto){
        if(StringUtils.hasText(dto.getId())){
           model.put("data",roleApiConsumer.findById(dto.getId()));
        }
        return new ModelAndView("pms/role/role_edit");
    }

    @RequestMapping(value = "saveData",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> saveData(SysRoleDto dto){
        try {
            putOperatorInfo(dto);
            roleApiConsumer.saveData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

    /**
     * 数据 启用  禁用
     * @param dto
     * @return
     */
    @RequestMapping(value = "modifyData",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> modifyData(SysRoleDto dto){
        try {
            roleApiConsumer.modifyData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

    /**
     * 权限设置页面
     * @return
     */
    @RequestMapping(value = "resourceSetting",method = RequestMethod.GET)
    public ModelAndView resourceSetting(ModelMap model){
        model.put("roleCode",getString("roleCode"));
        return new ModelAndView("pms/role/role_resource_setup");
    }

    /**
     * 权限设置 数据保存
     * @return
     */
    @RequestMapping(value = "roleResourceSave",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> roleResourceSave(RoleResourceDto dto){
        try {
            roleApiConsumer.roleResourceSave(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

}
