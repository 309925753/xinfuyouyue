package com.xfyyim.cn.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hy on 2018/12/12.
 */

public class ArithUtils {

    // 默认除法算法精确度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点后10位，以后的数字四舍五入
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由scale参数
     * 指定精度，以后的数字四舍五入
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     */
    public static String round(long data) {
        final DecimalFormat df = new DecimalFormat("0.00");
        return df.format((double) data / 10000);
    }

    /*****
     * 转换10000
     * @param data
     * @return
     */
    public static long roundLong(String data) {
        return (long) Double.parseDouble(data) * 10000;
    }

    /**
     * 提供精确的小数位四舍五入处理
     */
    public static String roundLong(Long data) {

        final DecimalFormat df = new DecimalFormat("0.00");
        return df.format((float) data / 10000);
    }

    /**
     * 提供精确的小数2位不做四舍五入处理
     */
    public static String roundDouble(Long data) {
        String arth = "";
        try {
            BigDecimal bd = new BigDecimal((double) data / 10000);
            bd = bd.setScale(2, BigDecimal.ROUND_FLOOR);
            arth = bd.toString();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return arth;
    }


    /**
     * 提供精确的小数位四舍五入处理
     */
    public static String round3(float data) {

        final DecimalFormat df = new DecimalFormat("0.00");
        return df.format((float) data / 10000);
    }

    /**
     * 提供精确的小数位四舍五入处理
     */
    public static float round1(int data) {
        final DecimalFormat df = new DecimalFormat("0.00");
        return (float) data / 100;
    }

    /**
     * 提供精确的小数位四舍五入处理
     */
    public static String round2(Double data) {
        final DecimalFormat df = new DecimalFormat("0.00");
        return df.format(data / 10000);
    }

    private static final String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    private static final String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿"};
    public static String munberToChinese(String string) {
        String result = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {
            int num = string.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else if (num!=0){
                result += s1[num];
            }
        }
        return result;
    }


}
