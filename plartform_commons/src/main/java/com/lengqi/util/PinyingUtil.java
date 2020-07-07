package com.lengqi.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyingUtil {
    /**
     * 将汉字转拼音
     * @param content
     * @return
     */
    public static String str2Pinyin(String content){

        if(content == null){
            return null;
        }

        //关闭音调的设置
        HanyuPinyinOutputFormat outputFormat
                = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        //将字符串转换成字符数组
        char[] chars = content.toCharArray();

        //准备一个StringBuilder对象，拼接拼音的
        StringBuilder sb = new StringBuilder();

        //循环字符数组
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            try {
                //调用工具生成每个字符的拼音
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
                if(pinyin != null){
                    //{zhong, chong}
                    //如果不等于null，说明有拼音
                    //后面的音 貌似更加符合城市的发音
                    sb.append(pinyin[pinyin.length - 1]);
                } else {
                    //如果等于null,说明不是汉字，或者无法转拼音，直接拼原字符
                    sb.append(c);
                }

            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }

        //输出拼音
        return sb.toString();
    }

    public static void main(String[] args)  {
        String s = str2Pinyin("长春");
        System.out.println(s);
    }
}
