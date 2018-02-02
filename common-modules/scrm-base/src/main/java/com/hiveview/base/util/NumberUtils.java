package com.hiveview.base.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by leo on 2017/11/23.
 */
/**
 * Created by leo on 2017/11/23.
 * 算数工具类
 */
public class NumberUtils {

    /**
     * 减法 默认两位小数点
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal subtract(BigDecimal a,BigDecimal... b){
        BigDecimal res=a;
        for(BigDecimal t:b){
            if(null != res){
                res=subtract(res,t,2);
            }
        }
        return res;
    }

    public static BigDecimal subtract(BigDecimal a,BigDecimal b,int scale){
        if(null != a && null != b){
            return a.subtract(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 加法默认两位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a,BigDecimal... b){
        BigDecimal res=a;
        for(BigDecimal t:b){
            if(null != res){
                res=add(res,t,2);
            }
        }
        return res;
    }

    public static BigDecimal add(BigDecimal a,BigDecimal b, int scale){
        if(null != a && null != b){
            return a.add(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 乘法 默认保留两位
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b){
        return multiply(a,b,2);
    }

    /**
     * 乘法
     * @param a
     * @param b
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b, int scale){
        if(null != a && null != b){
            return a.multiply(b).setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 除法 默认保留两位小数点
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b){
        return divide(a,b,2);
    }


    /**
     * 除法
     * @param a
     * @param b
     * @param scale 小数点位数
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale){
        if(null != a && null != b){
            return a.divide(b,scale, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 设置小数点位数
     * @param a
     * @return
     */
    public static BigDecimal setScale(BigDecimal a){
        return setScale(a,2);
    }

    /**
     * 设置小数点位数
     * @param a
     * @param scale
     * @return
     */
    public static BigDecimal setScale(BigDecimal a, int scale){
        if(null != a){
            return a.setScale(scale,BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    /**
     * 校验手机号码
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean checkPhone(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }



    public static void main(String[] args) {
        BigDecimal a=new BigDecimal("20");
        BigDecimal b=new BigDecimal("3");
        BigDecimal c=new BigDecimal("1.3");
        System.out.println(subtract(a,b,c));
//        System.out.println(multiply(a,b));
//        System.out.println(checkPhone("13258965421"));
    }
}
