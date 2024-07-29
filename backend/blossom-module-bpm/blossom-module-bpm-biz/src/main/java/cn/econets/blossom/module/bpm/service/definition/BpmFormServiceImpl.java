package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.form.BpmFormUpdateReqVO;
import cn.econets.blossom.module.bpm.convert.definition.BpmFormConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmFormDO;
import cn.econets.blossom.module.bpm.dal.mysql.definition.BpmFormMapper;
import cn.econets.blossom.module.bpm.enums.ErrorCodeConstants;
import cn.econets.blossom.module.bpm.enums.definition.BpmModelFormTypeEnum;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmFormFieldRespDTO;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmModelMetaInfoRespDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * Dynamic Form Service Implementation class
 *
 */
@Service
@Validated
public class BpmFormServiceImpl implements BpmFormService {

    @Resource
    private BpmFormMapper formMapper;

    @Override
    public Long createForm(BpmFormCreateReqVO createReqVO) {
        this.checkFields(createReqVO.getFields());
        // Insert
        BpmFormDO form = BpmFormConvert.INSTANCE.convert(createReqVO);
        formMapper.insert(form);
        // Return
        return form.getId();
    }

    @Override
    public void updateForm(BpmFormUpdateReqVO updateReqVO) {
        this.checkFields(updateReqVO.getFields());
        // Verify existence
        this.validateFormExists(updateReqVO.getId());
        // Update
        BpmFormDO updateObj = BpmFormConvert.INSTANCE.convert(updateReqVO);
        formMapper.updateById(updateObj);
    }

    @Override
    public void deleteForm(Long id) {
        // Verify existence
        this.validateFormExists(id);
        // Delete
        formMapper.deleteById(id);
    }

    private void validateFormExists(Long id) {
        if (formMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.FORM_NOT_EXISTS);
        }
    }

    @Override
    public BpmFormDO getForm(Long id) {
        return formMapper.selectById(id);
    }

    @Override
    public List<BpmFormDO> getFormList() {
        return formMapper.selectList();
    }

    @Override
    public List<BpmFormDO> getFormList(Collection<Long> ids) {
        return formMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BpmFormDO> getFormPage(BpmFormPageReqVO pageReqVO) {
        return formMapper.selectPage(pageReqVO);
    }


    @Override
    public BpmFormDO checkFormConfig(String configStr) {
        BpmModelMetaInfoRespDTO metaInfo = JsonUtils.parseObject(configStr, BpmModelMetaInfoRespDTO.class);
        if (metaInfo == null || metaInfo.getFormType() == null) {
            throw exception(MODEL_DEPLOY_FAIL_FORM_NOT_CONFIG);
        }
        // Verify form exists
        if (Objects.equals(metaInfo.getFormType(), BpmModelFormTypeEnum.NORMAL.getType())) {
            BpmFormDO form = getForm(metaInfo.getFormId());
            if (form == null) {
                throw exception(FORM_NOT_EXISTS);
            }
            return form;
        }
        return null;
    }

    /**
     * Verification Field，Avoid field Repeat
     *
     * @param fields field Array
     */
    private void checkFields(List<String> fields) {
        if (true) { // TODO Compatible Vue3 Workflow：Because of the new form designer，So no verification for the time being
            return;
        }
        Map<String, String> fieldMap = new HashMap<>(); // key Yes vModel，value Yes label
        for (String field : fields) {
            BpmFormFieldRespDTO fieldDTO = JsonUtils.parseObject(field, BpmFormFieldRespDTO.class);
            Assert.notNull(fieldDTO);
            String oldLabel = fieldMap.put(fieldDTO.getVModel(), fieldDTO.getLabel());
            // If it does not exist，Return directly
            if (oldLabel == null) {
                continue;
            }
            // If exists，An error is reported
            throw exception(ErrorCodeConstants.FORM_FIELD_REPEAT, oldLabel, fieldDTO.getLabel(), fieldDTO.getVModel());
        }
    }

}
