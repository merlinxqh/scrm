package com.hiveview.base.util.serializer;

/**
 * Created by mike on 16-5-24.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.base.common.TreeEntity;
import com.hiveview.base.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 * Li.XiaoChao
 * @version 2014-6-29
 */
public class ObjectUtils{

    /**
     * 注解到对象复制，只复制能匹配上的方法。
     * @param annotation
     * @param object
     */
    public static void annotationToObject(final Object annotation, Object object){
        if (annotation != null){
            Class<?> annotationClass = annotation.getClass();
            Class<?> objectClass = object.getClass();
            for (Method m : objectClass.getMethods()){
                if (StringUtils.startsWith(m.getName(), "set")){
                    try {
                        String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
                        Object obj = annotationClass.getMethod(s).invoke(annotation);
                        if (obj != null && !"".equals(obj.toString())){
                            if (object == null){
                                object = objectClass.newInstance();
                            }
                            m.invoke(object, obj);
                        }
                    } catch (Exception e) {
                        // 忽略所有设置失败方法
                    }
                }
            }
        }
    }

    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serialize(final Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null){
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化对象
     * @param bytes
     * @return
     */
    public static Object unserialize(final byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            if (bytes != null && bytes.length > 0){
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String json,Class<T> clz){
        if(StringUtils.isNotEmpty(json)){
            return JSON.parseObject(json,clz);
        }
        return null;
    }

    /**
     * Copy对象
     * 经测试 通过fastJson 序列化 比 该方法高效
     * @param resourceObj
     * @param targetObject
     * @param <R>
     * @param <T>
     * @return
     */
    @Deprecated
    public static <R,T> T copyObject(R resourceObj,T targetObject){
        BeanUtils.copyProperties(resourceObj,targetObject);
        return targetObject;
    }


    /**
     * 通过fastJson copy对象
     * @param resourceObj
     * @param clz
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R,T> T copyObject(R resourceObj,Class<T> clz){
        return JSON.parseObject(JSON.toJSONString(resourceObj),clz);
    }

    /**
     * copy list
     * @param rList
     * @param clz
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R,T> List<T> copyListObject(List<R> rList,Class<T> clz){
        return JSONArray.parseArray(JSON.toJSONString(rList),clz);
    }

    /**
     * 将对象转map 用于dto转查询参数
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String,Object> changeToMap(T obj){
        if(null != obj){
            Map<String,Object> params=copyObject(obj,Map.class);
            /**
             * 字符串去空格符
             */
            for(String key:params.keySet()){
                Object object=params.get(key);
                if(object instanceof String){
                    object=((String) object).trim();
                    params.put(key,object);
                }
            }
            return params;
        }
        return null;
    }

    /**
     * 根据key获取value 处理 key为 obj.objItem.key 这种情况
     * @param map
     * @param key
     * @return
     */
    public static String getMapValue(Map map,String key){
        if(map.containsKey(key)){
            return map.get(key).toString();
        }
        if(key.contains(".")){
            String subKey=key.substring(0,key.indexOf("."));
            if(null != map && map.containsKey(subKey)){
                Map subMap= changeToMap(map.get(subKey));
                key=key.substring(key.indexOf(".")+1);//去除 第一个key
                return getMapValue(subMap,key);
            }
        }else{
            if(null != map && map.containsKey(key)){
                return map.get(key).toString();
            }
        }
        return "";
    }

    /**
     * 对时间格式化 (时间戳格式)
     * @param map
     * @param key
     */
    public static void mapFormatDate(Map<String,Object> map,String key){
        String dateVal=getMapValue(map,key);
        if(org.springframework.util.StringUtils.hasText(dateVal)){
            Date date=new Date(Long.valueOf(dateVal));
            if(null != date){
                map.put(key, DateUtils.formatDate(date,DateUtils.DATE_PATTERN_COMPLEX));
            }
        }
    }


    /**
     * 对集合 进行分组操作
     *   用于 数据量较大 分批处理
     * @param list
     * @param periodSize  分组长度
     * @param eachIndex   当前循环序号
     * @param <T>
     * @return
     */
    public static <T> List<T> getPeriodList(List<T> list,int periodSize, int eachIndex){
        //集合为空 或者 长度 小于 分段长度 不处理
        if(CollectionUtils.isEmpty(list) || list.size() <= periodSize){
            return list;
        }

        /**
         * 循环 序号 小于0 或者 大于 最大 值
         */
        if(eachIndex < 0 || eachIndex > list.size() / periodSize){
            return null;
        }

        if(eachIndex != list.size()/periodSize){
            int start = eachIndex * periodSize;
            int end = (eachIndex+1)*periodSize - 1;
            return getListByBetweenSize(list,start,end);
        }else{
            if(list.size() % periodSize == 0){
                //数组长度 是 分段长度 的整数倍 最后一次循环不做处理
                return null;
            }else{
                int start = eachIndex * periodSize;
                int end = list.size() - 1;
                return getListByBetweenSize(list,start,end);
            }
        }

    }

    public static <T> List<T> getListByBetweenSize(List<T> list, int start, int end){
        if(!CollectionUtils.isEmpty(list) && list.size() >= end + 1 && end >= start ){
            return list.subList(start,end+1);
        }
        return null;
    }


    /**
     * 通用树结构 数据
     * @param list
     * @param <T>
     * @return
     */
    public static <T> JSONArray wrapperGetTreeData(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return new JSONArray();
        }
        List<TreeEntity> tlist=copyListObject(list,TreeEntity.class);
        List<TreeEntity> flist=getFirstTreeList(tlist);
        JSONArray resArray=new JSONArray();
        flist.forEach(f->{
            JSONObject json=new JSONObject();
            json.put("text", f.getName());
            json.put("code", f.getCode());
            json.put("level",1);
            json.put("id",f.getId());
            json.put("longCode", f.getLongCode());
            resArray.add(json);

        });
        recursionTreeData(tlist,resArray);
        return resArray;
    }

    /**
     * 获取拼装json数据 & 设置 home节点
     * @param list
     * @param <T>
     * @return
     */
    public static <T> JSONArray wrapperGetTreeSetHome(List<T> list){
        JSONArray resArray = wrapperGetTreeData(list);
        return setTreeHomeNode(resArray);
    }

    /**
     * 设置默认HOME节点
     */
    public static JSONArray setTreeHomeNode(JSONArray array){
        JSONArray res=new JSONArray();
        JSONObject home=new JSONObject();
        home.put("code", "");
        home.put("level",0);
        home.put("id","ROOT");//标识 根节点
        home.put("parentCode","");
        home.put("text", "HOME");
        if(array.size()>0){
            home.put("nodes", array);
            home.put("tags", "["+array.size()+"]");
        }else{
            home.put("tags", "[0]");
        }
        res.add(home);
        return res;
    }

    /**
     * 处理 树结构选中
     * @param array 当前树结构数据
     * @param existsData 已经存在的数据
     */
    public static void wrapperCheckTreeData(JSONArray array, List<TreeEntity> existsData) {
        if(!CollectionUtils.isEmpty(array)){
            for(Object obj:array){
                recursionCheckData((JSONObject) obj, existsData);
            }
        }
    }

    public static void recursionCheckData(JSONObject json, List<TreeEntity> existsData){
        json.put("text", json.getString("text"));
        for(TreeEntity vo:existsData){
            if(vo.getCode().equals(json.getString("code"))){
                JSONObject state=new JSONObject();
                state.put("checked", true);
                json.put("state", state);
            }
        }
        if(json.containsKey("nodes")){
            JSONArray childArray=json.getJSONArray("nodes");
            json.put("tags", "["+childArray.size()+"]");
            for(Object obj:childArray){
                recursionCheckData((JSONObject)obj, existsData);
            }
        }else{
            json.put("tags", "[0]");
        }
    }


    /**
     * 递归获取 所有商品分类 json格式数据
     * @param all  所有数据
     * @param array 一级商品类目
     */
    public static void recursionTreeData(List<TreeEntity> all,JSONArray array){
        for(Object obj:array){
            JSONObject json=(JSONObject) obj;
            JSONArray carray=new JSONArray();
            for(TreeEntity vo:all){
                if(json.getString("code").equals(vo.getParentCode())){
                    JSONObject cjson=new JSONObject();
                    cjson.put("text", vo.getName());
                    cjson.put("code", vo.getCode());
                    cjson.put("level",vo.getLevel());
                    cjson.put("id",vo.getId());
                    cjson.put("parentCode",vo.getParentCode());
                    cjson.put("longCode", vo.getLongCode());
                    carray.add(cjson);
                }
            }
            if(carray.size()>0){
                recursionTreeData(all,carray);
                json.put("nodes", carray);
                json.put("tags", "["+carray.size()+"]");
            }
        }
    }


    /**
     * 获取一级 树结构数据
     * @param list
     * @return
     */
    private static List<TreeEntity> getFirstTreeList(List<TreeEntity> list) {
        List<TreeEntity> resList=new ArrayList<>();
        list.stream().filter(l-> l.getLevel() == 1).forEach(l->{
            resList.add(l);
        });
        return resList;
    }
}
