package com.github.shanbei.shanbeiuser.once;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;

import java.io.File;

/**
 * 最简单的读
 * <p>
 * 1. 创建excel对应的实体对象 参照{@link DemoData}
 * <p>
 * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
 * <p>
 * 3. 直接读即可
 */
public class ReadExcel {

    String fileName="D:\\github\\JavaPrj\\shanbei-user\\src\\main\\java\\com\\github\\shanbei\\shanbeiuser\\once\\新建 Microsoft Excel 工作表.xlsx";

    public void ReadExcel1(){
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法3：
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ImportExcel.class, new DemoDataListener()).sheet().doRead();
    }

    public void ReadExcel2(){
        // 写法4
        // 一个文件一个reader
        try (
                ExcelReader excelReader = EasyExcel.read(fileName, ImportExcel.class, new DemoDataListener()).build()) {
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            // 读取一个sheet
            excelReader.read(readSheet);
        }
    }

    public static void main(String[] args) {
        new ReadExcel().ReadExcel1();
    }

}
