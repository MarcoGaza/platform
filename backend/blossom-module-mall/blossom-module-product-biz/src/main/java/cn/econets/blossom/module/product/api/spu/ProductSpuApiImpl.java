package cn.econets.blossom.module.product.api.spu;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Products SPU API Interface implementation class
 *
 */
@Service
@Validated
public class ProductSpuApiImpl implements ProductSpuApi {

    @Resource
    private ProductSpuService spuService;

    @Override
    public List<ProductSpuRespDTO> getSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.getSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public List<ProductSpuRespDTO> validateSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.validateSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public ProductSpuRespDTO getSpu(Long id) {
        ProductSpuDO spu = spuService.getSpu(id);
        return BeanUtils.toBean(spu, ProductSpuRespDTO.class);
    }

}
