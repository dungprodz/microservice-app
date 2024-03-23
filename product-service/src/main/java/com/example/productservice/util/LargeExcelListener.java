package com.example.productservice.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.productservice.entity.ProductEntity;
import com.example.productservice.model.ListProductExcel;
import com.example.productservice.model.response.UploadProductResponseBody;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@Getter
public class LargeExcelListener extends AnalysisEventListener<ListProductExcel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeExcelListener.class);
    private final List<Object> allData;
    private final String specialListId;
    private final int stt;
    private final Set<String> listCifById;
    private final String username;
    private final UploadProductResponseBody responseBody;
    private final Set<String> listCif;
    private final boolean checkTemplate;
    private final int count;

    public LargeExcelListener(List<Object> allData, String specialListId, int stt, Set<String> listCifById, String username, UploadProductResponseBody responseBody, Set<String> listCif, boolean checkTemplate, int count) {
        this.allData = allData;
        this.specialListId = specialListId;
        this.stt = stt;
        this.listCifById = listCifById;
        this.username = username;
        this.responseBody = responseBody;
        this.listCif = listCif;
        this.checkTemplate = checkTemplate;
        this.count = count;
    }

    @Override
    public void invoke(ListProductExcel product, AnalysisContext context) {
        // Xử lý dữ liệu, data là một hàng từ tệp Excel
//        try {
//            if (checkTemplate) {
//                if (data.size() > PLSpecialListColumns.values().length) {
//                    checkTemplate = false;
//                }
//                if (context.readRowHolder().getRowIndex() == 0) {
//                    for (PLSpecialListColumns columns : PLSpecialListColumns.values()) {
//                        if (!columns.getColName().equals(data.get(columns.getIndex()))) {
//                            checkTemplate = false;
//                        }
//                    }
//                } else {
//                    com.backbase.com.tpb.digital.produclayer.rest.spec.v1.speciallist.SpecialListDetail specialListDetailEntity = new SpecialListDetail();
//                    String cif;
//                    String detailPackage;
//                    String updatePeriod;
//                    String scoreType;
//                    String score;
//                    String segment;
//                    String subsegment;
//                    String customerGroup;
//                    String other;
//                    String type;
//                    cif = data.get(PLSpecialListColumns.CIF_NUMBER.getIndex());
//                    detailPackage = data.get(PLSpecialListColumns.SPECIAL_DETAIL_PACKAGE.getIndex());
//                    updatePeriod = data.get(PLSpecialListColumns.UPDATE_PERIOD.getIndex());
//                    scoreType = data.get(PLSpecialListColumns.SCORE_TYPE.getIndex());
//                    score = data.get(PLSpecialListColumns.SCORE.getIndex());
//                    segment = data.get(PLSpecialListColumns.SEGMENT.getIndex());
//                    subsegment = data.get(PLSpecialListColumns.SUBSEGMENT.getIndex());
//                    customerGroup = data.get(PLSpecialListColumns.CUSTOMER_GROUP.getIndex());
//                    other = data.get(PLSpecialListColumns.OTHER.getIndex());
//                    type = data.get(PLSpecialListColumns.TYPE.getIndex());
//                    specialListDetailEntity.setStt(stt++);
//                    specialListDetailEntity.setCifNumber(cif);
//                    specialListDetailEntity.setScoreType(scoreType);
//                    specialListDetailEntity.setScore(score);
//                    specialListDetailEntity.setSegment(segment);
//                    specialListDetailEntity.setSubsegment(subsegment);
//                    specialListDetailEntity.setCustomerGroup(customerGroup);
//                    specialListDetailEntity.setSpecDetailPackage(detailPackage);
//                    specialListDetailEntity.setOther(other);
//                    specialListDetailEntity.setSpecialListId(specialListId);
//                    specialListDetailEntity.setType(type);
//                    specialListDetailEntity.setUpdatePeriod(updatePeriod);
//                    if (specialListDetailEntity.getCifNumber().length() != 8
//                            || !org.apache.commons.lang3.StringUtils.isNumeric(specialListDetailEntity.getCifNumber())
//                            || listCifById.contains(cif)
//                            || !listCif.add(cif)
//                    ) {
//                        specialListDetailEntity.setStatus(Constants.ERROR_CIF);
//                        count++;
//                    } else {
//                        specialListDetailEntity.setStatus(Constants.INACTIVE);
//                    }
//                    listSpecialListDetail.add(specialListDetailEntity);
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("==== Exception  readFileExcel with ex: ====", e);
//            throw e;
//        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // Xử lý dữ liệu khi đọc xong file
        responseBody.setErrorCode(ErrorCode.SUCCESS);
        if (count != 0) {
            LOGGER.error("==== Exception doAfterAllAnalysed with count: {}====", count);
            responseBody.setErrorCode(ErrorCode.FILE_INVALID);
            responseBody.setErrorMessage(String.format(ErrorCode.MESS_INVALID_EXCEL_INFORMATION, count));
        } else {
            responseBody.setListId(specialListId);
        }
    }

}
