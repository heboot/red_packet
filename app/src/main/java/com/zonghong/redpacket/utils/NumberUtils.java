package com.zonghong.redpacket.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtils {
    public static String formatDouble(double value) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        String str = df.format(value);


        if (value == 0) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();

        nf.setGroupingUsed(false); //关闭分组，显示将不再以千位符分隔

        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);

        String result = nf.format(value);

        if (result.indexOf(".") < 1) {
            return result + ".00";
        }

        return nf.format(value);
    }

}
