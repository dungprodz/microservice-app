package com.example.productservice.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListProductExcel {
    @ColumnWidth(20)
    @ExcelProperty("Product Name")
    private String productName;

    @ColumnWidth(20)
    @ExcelProperty("Product Code")
    private String productCode;

    @ColumnWidth(20)
    @ExcelProperty("Price")
    private BigDecimal price;
}
