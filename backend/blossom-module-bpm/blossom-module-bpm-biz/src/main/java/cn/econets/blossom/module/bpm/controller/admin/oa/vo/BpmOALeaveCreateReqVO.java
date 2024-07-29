package cn.econets.blossom.module.bpm.controller.admin.oa.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.AssertTrue;

@Schema(description = "Management Backend - Leave application creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmOALeaveCreateReqVO extends BpmOALeaveBaseVO {

    @AssertTrue(message = "End time，Need to be after the start time")
    public boolean isEndTimeValid() {
        return !getEndTime().isBefore(getStartTime());
    }

}
