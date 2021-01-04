package com.changhong.sei.basic;

import com.changhong.sei.core.context.Version;

/**
 * 实现功能：应用版本
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-01-04 15:34
 */
public class BasicVersion extends Version {
    public BasicVersion() {
        super(BasicVersion.class.getPackage().getName());
    }
}
