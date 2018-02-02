package com.hiveview.base.util.http;

import com.alibaba.fastjson.JSON;
import com.hiveview.base.util.serializer.ObjectUtils;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by leo on 2017/10/23.
 * http操作工具类 封装
 */
public class WrapperHttpUtils {

    /**
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url,Map<String,String> param){
//        return OkHttp3Utils.getInstance().doPost(url,param);
          return HttpClientUtils.post(url,param);
    }


    public static String doPost(String url){
        return doPost(url,null);
    }

    public static String doGet(String url,Map<String,String> param){
//        return OkHttp3Utils.getInstance().doGet(url,param);
        return HttpClientUtils.get(url,param);
    }

    public static String doGet(String url){
        return doGet(url,null);
    }

    /**
     * POST返回具体类型
     * @param url
     * @param param
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T postReturnObj(String url,Map<String,String> param,Class<T> clz){
        String resp=doPost(url,param);
        return ObjectUtils.parseObject(resp,clz);
    }

    public static <T> T postReturnObj(String url,Class<T> clz){
        return postReturnObj(url,null,clz);
    }

    /**
     * GET返回具体类型
     * @param url
     * @param param
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getReturnObj(String url,Map<String,String> param,Class<T> clz){
        String resp=doGet(url,param);
        return ObjectUtils.parseObject(resp,clz);
    }

    public static <T> T getReturnObj(String url,Class<T> clz){
        return getReturnObj(url,null,clz);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String url="http://localhost:8081/api/tv/v1.2/order/skuTakeOrder";
//        Map<String,String> map=new HashMap<>();
//        map.put("params","{\"userid\":1006641,\"goodsSkuSn\":\"442478018805010433_3\",\"goodsSn\":\"442478018805010433\",\"receiveId\":24,\"quantity\":1,\"isInvoice\":true,\"invoiceType\":\"单位\",\"invoiceTitle\":\"深圳水公司\",\"invoiceContent\":\"本发票用于购物\"}");
//        map.put("token","954efe020afd3d73bedc800097db4718_1006641");
//        map.put("sign","8B4AA72A908AFA7B06E722C1CC83E62A");
//        System.out.println(doPost(url,map));

        List<Demo> dlist=new ArrayList<>();
        char a=1;
        Character c=1233;
        dlist.add(new Demo(2,"er"));
        dlist.add(new Demo(5,"er"));
        dlist.add(new Demo(1,"er"));
        dlist.add(new Demo(4,"er"));
        Collections.sort(dlist,Comparator.comparingInt(Demo::getGrade));
        System.out.println(JSON.toJSONString(dlist));
    }

    @Data
    public static class Demo{
        private int grade;

        private String name;

        public Demo(int grade,String name){
            this.grade=grade;
            this.name=name;
        }
    }
}
