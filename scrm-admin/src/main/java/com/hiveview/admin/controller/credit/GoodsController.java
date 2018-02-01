package com.hiveview.admin.controller.credit;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.admin.rpc.credit.GoodsApiConsumer;
import com.hiveview.base.util.response.RespMsg;
import com.hiveview.common.api.PageDto;
import com.hiveview.credit.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lijuan on 2018/1/29.
 */
@RequestMapping("/credit/goods/*")
@Controller
public class GoodsController extends BaseController {
    @Autowired
    private GoodsApiConsumer goodsApiConsumer;

    /**
     * 商品列表
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("credit/goods/goods_list");
    }

    /**
     * 列表数据
     * @return
     */
    @RequestMapping(value = "listData",method = RequestMethod.GET)
    public @ResponseBody PageDto listData(PageDto page, GoodsDto params){
        return goodsApiConsumer.getPageData(page,params);
    }

    /**
     * 添加商品
     * @return
     */
    @RequestMapping(value = "add")
    public ModelAndView addGoods(){
        return new ModelAndView("credit/goods/goods_add");
    }

    /**
     * 保存商品
     * @return
     */
    @RequestMapping(value = "saveData", method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> saveData(GoodsDto dto){
        try {
            goodsApiConsumer.saveData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }
}
