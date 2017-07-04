package com.zhongqi.util;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by songrenfei on 2016/12/20.
 */

public class ExcelUtils {

    public List<List<List<String>>> resolveExcel(Workbook workbook) throws IOException{
        List<List<List<String>>> sheetList = new ArrayList<>();
        //Workbook workbook = null;
        /*
        try{
            workbook = new HSSFWorkbook(inputStream);
            System.out.println("excel format 97-2003");
        }catch (Exception e){
            workbook = new XSSFWorkbook(inputStream);
            System.out.println("excel format 2007-2010");
        }
        */
        Integer beginCount=0;
        if(workbook!=null) {
            Integer sheets = workbook.getNumberOfSheets();
            sheetList = new ArrayList<>();
            for (int i = 0; i < sheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if(sheet!=null) {
                    Integer rows = sheet.getLastRowNum()+1;
                    List<List<String>> rowList = new ArrayList<>();
                    for (int x = 0; x < rows; x++) {
                        Row row = sheet.getRow(x);

                        if(row!=null) {
                            Integer cells = Integer.valueOf(row.getLastCellNum());
                            if (x==0){
                                beginCount=cells;
                            }
                            List<String> cellList = new ArrayList<>();
                            for (int y = 0; y < beginCount; y++) {
                                Cell cell = row.getCell(y);
                                if(cell!=null) {
                                    cellList.add(getValue(cell));
                                }else{
                                    cellList.add("");
                                }
                            }
                            rowList.add(cellList);
                        }
                    }
                    sheetList.add(rowList);
                }
            }

            System.out.println(sheetList.toString());
            for(List<List<String>> rowList : sheetList){
                for(List<String> cellList : rowList){
                    for(String cell:cellList){
                        System.out.print(cell+"  |  ");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
            }
        }

        return sheetList;
    }


    // 获取单元格的值
    private String getValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        String cellStr = formatter.formatCellValue(cell);
        return  cellStr;
    }

    public Workbook exportExcel(List<List<Map<String, String>>> lists) {

//        Workbook workbook = new HSSFWorkbook();
        Workbook workbook = new XSSFWorkbook();
        if(lists!=null && lists.size()>0) {
            for (int n = 0; n < lists.size(); n++) {
                List<Map<String, String>> list = lists.get(n);

                Sheet sheet = workbook.createSheet("sheet_"+n);

                if (list != null && list.size() > 0) {
                    Map<String, String> mapColumn = list.get(0);

                    // 设置标题行（第一行）
                    Row rowTitle = sheet.createRow(0);
                    Set<String> keys = mapColumn.keySet();
                    for (String key : keys) {
                        rowTitle.createCell(Integer.valueOf(mapColumn.get(key))).setCellValue(key);
                    }

                    // 设置数据行
                    for (int i = 1; i < list.size(); i++) {
                        Row row = sheet.createRow(i);
                        Map<String, String> dataMap = list.get(i);
                        Set<String> dataKeys = dataMap.keySet();
                        for (String dataKey : dataKeys) {
                            row.createCell(Integer.valueOf(mapColumn.get(dataKey))).setCellValue(dataMap.get(dataKey));
                        }
                    }
                }
            }
        }
        return workbook;
    }

}
