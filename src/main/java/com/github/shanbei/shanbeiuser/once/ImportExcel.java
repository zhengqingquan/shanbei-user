package com.github.shanbei.shanbeiuser.once;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用easyExcel导入Excel的数据。
 * 优势在以更小的内存读取更多的数据。
 */
@Data
@EqualsAndHashCode
public class ImportExcel {

    private String string;
    private String date;
    private String doubleData;
}
