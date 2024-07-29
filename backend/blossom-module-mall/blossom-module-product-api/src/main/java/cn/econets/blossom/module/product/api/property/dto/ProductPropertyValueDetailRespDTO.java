package cn.econets.blossom.module.product.api.property.dto;

import lombok.Data;

/**
 * Details of product attributes Response DTO
 *
 */
@Data
public class ProductPropertyValueDetailRespDTO {

    /**
     * Property number
     */
    private Long propertyId;

    /**
     * Name of the property
     */
    private String propertyName;

    /**
     * The number of the property value
     */
    private Long valueId;

    /**
     * Name of the property value
     */
    private String valueName;

}
