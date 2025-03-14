package org.nano.nstitleskills.util.number;

import java.text.DecimalFormat;

public class Unit {
    public static String unitDouble(double v){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(v);
    }
}
