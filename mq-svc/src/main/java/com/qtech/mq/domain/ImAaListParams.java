package com.qtech.mq.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * OLAP
 * @TableName im_aa_list_parsed_detail
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ImAaListParams implements Serializable {
    private String aa1;
    private String aa2;
    private String aa3;
    private String backToPosition;
    private String blemish;
    private String clampOnOff;
    private String chartAlignment;
    private String chartAlignment1;
    private String chartAlignment2;
    private String delay;
    private String destroy;
    private String destroyStart;
    private String dispense;
    private String epoxyInspection;
    private String epoxyInspectionAuto;
    private String grab;
    private String gripperOpen;
    private String init;
    private String lpBlemish;
    private String lpOc;
    private String lpOcCheck;
    private String lpOn;
    private String lpOnBlemish;
    private String lpOff;
    private String moveToBlemishPos;
    private String mtfCheck;
    private String mtfCheck1;
    private String mtfCheck2;
    private String mtfCheck3;
    private String openCheck;
    private String recordPosition;
    private String reInit;
    private String saveOc;
    private String saveMtf;
    private String senserReset;
    private String sid;
    private String uvon;
    private String uvoff;
    private String vcmHall;
    private String vcmHall2;
    private String vcmMoveToZ;
    private String vcmMoveToZPos;
    private String vcmPowerOffCheck;
    private String vcmInit;
    private String vcmOisInit;
    private String vcmPowerOff;
    private String vcmPowerOn;
    private String vcmTop;
    private String vcmTopHall;
    private String vcmZ;
    private String vcmZHall;
    private String yLevel;

    // item 参数项
    private String aa1RoiCc;
    private String aa1RoiUl;
    private String aa1RoiUr;
    private String aa1RoiLl;
    private String aa1RoiLr;
    private String aa1Fc;
    private String aa1F1;
    private String aa1F2;
    private String aa1F3;
    private String aa1F4;
    private String aa1MtfOffAxisCheck1;
    private String aa1MtfOffAxisCheck2;
    private String aa1MtfOffAxisCheck3;
    private String aa1Target;
    private String aa1CcToCornerLimit;
    private String aa1CcToCornerLimitMin;
    private String aa1CornerScoreDifferenceRejectValue;
    private String aa1ZRef;
    private String aa1SrchStep;
    private String aa1GoldenGlueThicknessMin;
    private String aa1GoldenGlueThicknessMax;

    private String aa2RoiCc;
    private String aa2RoiUl;
    private String aa2RoiUr;
    private String aa2RoiLl;
    private String aa2RoiLr;
    private String aa2Fc;
    private String aa2F1;
    private String aa2F2;
    private String aa2F3;
    private String aa2F4;
    private String aa2MtfOffAxisCheck1;
    private String aa2MtfOffAxisCheck2;
    private String aa2MtfOffAxisCheck3;
    private String aa2Target;
    private String aa2CcToCornerLimit;
    private String aa2CcToCornerLimitMin;
    private String aa2CornerScoreDifferenceRejectValue;
    private String aa2ZRef;
    private String aa2SrchStep;
    private String aa2GoldenGlueThicknessMin;
    private String aa2GoldenGlueThicknessMax;

    private String aa3RoiCc;
    private String aa3RoiUl;
    private String aa3RoiUr;
    private String aa3RoiLl;
    private String aa3RoiLr;
    private String aa3Fc;
    private String aa3F1;
    private String aa3F2;
    private String aa3F3;
    private String aa3F4;
    private String aa3MtfOffAxisCheck1;
    private String aa3MtfOffAxisCheck2;
    private String aa3MtfOffAxisCheck3;
    private String aa3Target;
    private String aa3CcToCornerLimit;
    private String aa3CcToCornerLimitMin;
    private String aa3CornerScoreDifferenceRejectValue;
    private String aa3ZRef;
    private String aa3SrchStep;
    private String aa3GoldenGlueThicknessMin;
    private String aa3GoldenGlueThicknessMax;

    private String mtfCheckFc;
    private String mtfCheckF1;
    private String mtfCheckF2;
    private String mtfCheckF3;
    private String mtfCheckF4;
    private String mtfCheck1Fc;
    private String mtfCheck1F1;
    private String mtfCheck1F2;
    private String mtfCheck1F3;
    private String mtfCheck1F4;
    private String mtfCheck2Fc;
    private String mtfCheck2F1;
    private String mtfCheck2F2;
    private String mtfCheck2F3;
    private String mtfCheck2F4;
    private String mtfCheck3Fc;
    private String mtfCheck3F1;
    private String mtfCheck3F2;
    private String mtfCheck3F3;
    private String mtfCheck3F4;
    private String chartAlignmentXResMin;
    private String chartAlignmentXResMax;
    private String chartAlignmentYResMin;
    private String chartAlignmentYResMax;
    private String chartAlignment1XResMin;
    private String chartAlignment1XResMax;
    private String chartAlignment1YResMin;
    private String chartAlignment1YResMax;
    private String chartAlignment2XResMin;
    private String chartAlignment2XResMax;
    private String chartAlignment2YResMin;
    private String chartAlignment2YResMax;
    private String epoxyInspectionInterval;
    private String vcmCheckResultCheckMin;
    private String vcmCheckResultCheckMax;
    private String recordPositionUtXyzMove;
    private String ocCheckXOffsetMin;
    private String ocCheckXOffsetMax;
    private String ocCheckYOffsetMin;
    private String ocCheckYOffsetMax;
    private String saveOcXOffsetMin;
    private String saveOcXOffsetMax;
    private String saveOcYOffsetMin;
    private String saveOcYOffsetMax;
    private String saveMtfCcMin;
    private String saveMtfCcMax;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}