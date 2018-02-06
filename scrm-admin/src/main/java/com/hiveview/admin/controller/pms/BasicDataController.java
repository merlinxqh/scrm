package com.hiveview.admin.controller.pms;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.admin.rpc.pms.BasicDataApiConsumer;
import com.hiveview.base.util.response.RespMsg;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.dto.BasicDataDto;
import com.hiveview.pms.dto.BasicDataTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 基础数据
 */
@Controller
@RequestMapping("/basicData/*")
public class BasicDataController extends BaseController {

    @Autowired
    private BasicDataApiConsumer basicDataApiConsumer;


    /**
     *
     * 通用基础数据页面
     * 通过配置菜单+类型 访问
     * @param model
     * @return
     */
    @RequestMapping(value = "commonList",method = RequestMethod.GET)
    public ModelAndView commonList(ModelMap model){
        model.put("typeCode",getString("typeCode"));
//        BasicDataTypeEnum be=BasicDataTypeEnum.valueOf(getString("typeCode"));
//        if(null != be){
//            model.put("title",be.getTitle());
//        }
        return new ModelAndView("pms/basic/basicData_common");
    }


    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("pms/basic/basicData_list");
    }


    /**
     * 添加类型
     * @param model
     * @param dto
     * @return
     */
    @RequestMapping(value = "addType",method = RequestMethod.GET)
    public ModelAndView addType(ModelMap model, BasicDataTypeDto dto){
        if(StringUtils.hasText(dto.getParentCode())){//不是添加一级商品分类
            dto.setCode(dto.getParentCode());
            dto.setParentCode(null);
            List<BasicDataTypeDto> dtoList = basicDataApiConsumer.findTypeList(dto);
            if(!CollectionUtils.isEmpty(dtoList)){
                model.put("parent",dtoList.get(0));
            }
        }
        return new ModelAndView("pms/basic/basicData_type_edit");
    }

    /**
     * 编辑类型
     * @param model
     * @return
     */
    @RequestMapping(value = "editType",method = RequestMethod.GET)
    public ModelAndView editType(ModelMap model,@RequestParam(value = "id") String id){
        BasicDataTypeDto dto = basicDataApiConsumer.findTypeById(Long.valueOf(id));
        model.put("data",dto);
        return new ModelAndView("pms/basic/basicData_type_edit");
    }

    /**
     * 添加基础数据
     * @param model
     * @return
     */
    @RequestMapping(value = "addData",method = RequestMethod.GET)
    public ModelAndView addData(ModelMap model){
        model.put("typeCode",getString("typeCode"));
        return new ModelAndView("pms/basic/basicData_edit");
    }

    /**
     * 修改基础数据
     * @param model
     * @return
     */
    @RequestMapping(value = "editData",method = RequestMethod.GET)
    public ModelAndView editData(ModelMap model, @RequestParam(value = "id") String id){
        model.put("data",basicDataApiConsumer.findById(Long.valueOf(id)));
        return new ModelAndView("pms/basic/basicData_edit");
    }

    /**
     * 启用禁用
     * @return
     */
    @RequestMapping(value = "modifyData",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> modifyData(ModifyCommonDto dto){
        try {
            basicDataApiConsumer.moidfyData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

    /**
     * 获取基础数据类型 树结构数据
     * @return
     */
    @RequestMapping(value = "typeTreeData",method = RequestMethod.POST)
    public @ResponseBody Object typeTreeData(){
        List<BasicDataTypeDto> typeList = basicDataApiConsumer.findTypeList(null);
        return ObjectUtils.wrapperGetTreeSetHome(typeList);
    }

    /**
     * 分页数据
     * @return
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public PageDto listData(PageDto page, BasicDataDto params){
        return basicDataApiConsumer.findByPage(page, params);
    }

    /**
     * 基础数据保存
     * @return
     */
    @RequestMapping(value = "saveData",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> saveData(BasicDataDto dto){
        try {
            putOperatorInfo(dto);
            basicDataApiConsumer.saveData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

    /**
     * 基础数据类型保存
     * @return
     */
    @RequestMapping(value = "saveTypeData",method = RequestMethod.POST)
    public @ResponseBody RespMsg saveTypeData(BasicDataTypeDto dto){
        try {
            putOperatorInfo(dto);
            basicDataApiConsumer.saveTypeData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp(getExtMap("callbackMethod"));
    }

}
