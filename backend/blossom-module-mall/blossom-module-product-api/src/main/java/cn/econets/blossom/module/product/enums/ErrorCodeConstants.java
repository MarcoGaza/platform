package cn.econets.blossom.module.product.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Product Error code enumeration class
 *
 * product System，Use 1-008-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== Product category related 1-008-001-000 ============
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(1_008_001_000, "Product category does not exist");
    ErrorCode CATEGORY_PARENT_NOT_EXISTS = new ErrorCode(1_008_001_001, "The parent category does not exist");
    ErrorCode CATEGORY_PARENT_NOT_FIRST_LEVEL = new ErrorCode(1_008_001_002, "The parent category cannot be a secondary category");
    ErrorCode CATEGORY_EXISTS_CHILDREN = new ErrorCode(1_008_001_003, "Subcategories exist，Cannot delete");
    ErrorCode CATEGORY_DISABLED = new ErrorCode(1_008_001_004, "Product Categories({})Disabled，Unable to use");
    ErrorCode CATEGORY_HAVE_BIND_SPU = new ErrorCode(1_008_001_005, "There are products under this category，Cannot delete");

    // ========== Product brand related number 1-008-002-000 ==========
    ErrorCode BRAND_NOT_EXISTS = new ErrorCode(1_008_002_000, "Brand does not exist");
    ErrorCode BRAND_DISABLED = new ErrorCode(1_008_002_001, "Brand disabled");
    ErrorCode BRAND_NAME_EXISTS = new ErrorCode(1_008_002_002, "Brand name already exists");

    // ========== Product attribute item 1-008-003-000 ==========
    ErrorCode PROPERTY_NOT_EXISTS = new ErrorCode(1_008_003_000, "The property item does not exist");
    ErrorCode PROPERTY_EXISTS = new ErrorCode(1_008_003_001, "The property name already exists");
    ErrorCode PROPERTY_DELETE_FAIL_VALUE_EXISTS = new ErrorCode(1_008_003_002, "The attribute value exists under the attribute item，Cannot delete");

    // ========== Product attribute value 1-008-004-000 ==========
    ErrorCode PROPERTY_VALUE_NOT_EXISTS = new ErrorCode(1_008_004_000, "The property value does not exist");
    ErrorCode PROPERTY_VALUE_EXISTS = new ErrorCode(1_008_004_001, "The property value with this name already exists");

    // ========== Products SPU 1-008-005-000 ==========
    ErrorCode SPU_NOT_EXISTS = new ErrorCode(1_008_005_000, "Products SPU Does not exist");
    ErrorCode SPU_SAVE_FAIL_CATEGORY_LEVEL_ERROR = new ErrorCode(1_008_005_001, "Incorrect product classification，Reason：Must use the second level product category and below");
    ErrorCode SPU_SAVE_FAIL_COUPON_TEMPLATE_NOT_EXISTS = new ErrorCode(1_008_005_002, "Products SPU Save failed，Reason：Coupon does not exist");
    ErrorCode SPU_NOT_ENABLE = new ErrorCode(1_008_005_003, "Products SPU【{}】Not in the listing status");
    ErrorCode SPU_NOT_RECYCLE = new ErrorCode(1_008_005_004, "Products SPU Not in the recycle bin state");

    // ========== Products SKU 1-008-006-000 ==========
    ErrorCode SKU_NOT_EXISTS = new ErrorCode(1_008_006_000, "Products SKU Does not exist");
    ErrorCode SKU_PROPERTIES_DUPLICATED = new ErrorCode(1_008_006_001, "Products SKU The attribute combination of is repeated");
    ErrorCode SPU_ATTR_NUMBERS_MUST_BE_EQUALS = new ErrorCode(1_008_006_002, "One SPU Each of the following SKU，The attributes must be consistent");
    ErrorCode SPU_SKU_NOT_DUPLICATE = new ErrorCode(1_008_006_003, "One SPU Each of the following SKU，Must not repeat");
    ErrorCode SKU_STOCK_NOT_ENOUGH = new ErrorCode(1_008_006_004, "Products SKU Insufficient inventory");

    // ========== Products Evaluation 1-008-007-000 ==========
    ErrorCode COMMENT_NOT_EXISTS = new ErrorCode(1_008_007_000, "Product review does not exist");
    ErrorCode COMMENT_ORDER_EXISTS = new ErrorCode(1_008_007_001, "The product review for the order already exists");

    // ========== Products Collection 1-008-008-000 ==========
    ErrorCode FAVORITE_EXISTS = new ErrorCode(1_008_008_000, "This product has been collected");
    ErrorCode FAVORITE_NOT_EXISTS = new ErrorCode(1_008_008_001, "Product collection does not exist");

}
