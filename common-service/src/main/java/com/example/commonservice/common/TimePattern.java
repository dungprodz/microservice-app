package com.example.commonservice.common;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public enum TimePattern {
    DD_MM_YYYY(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
    YYYY_MM_DD_HH_MM(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
    YYYY_MM_DD_HH_MM_SS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    YYYY_MM_DD_HH_MM_SS_SSS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
    YYYY_MM_DD_T_HH_MM_SS_SSSZ(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")),
    YYYY_MM_DD(DateTimeFormatter.ofPattern("yyyyMMdd")),
    YYYY_MM_DD_SLASH(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
    YYYY_MM_DD_MINUS(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    HH_MM_DD_MM_YYYY(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")),
    HH_MM_SS_DD_MM_YYYY(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")),
    YYYY_MM_DD_T_HH_MM_SS(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
    HH_MM(DateTimeFormatter.ofPattern("HH:mm")),
    HH_MM_COMMA_NGAY_DD_MM_YYYY(DateTimeFormatter.ofPattern("HH:mm', ngày' dd/MM/yyyy")),
    HH_MM_NGAY_DD_MM_YYYY(DateTimeFormatter.ofPattern("HH:mm' ngày' dd/MM/yyyy"));

    private final DateTimeFormatter formatter;

    private TimePattern(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }
}
