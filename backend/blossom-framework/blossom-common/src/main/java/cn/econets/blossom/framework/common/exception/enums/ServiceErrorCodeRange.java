package cn.econets.blossom.framework.common.exception.enums;

/**
 * Error code range for business exceptions，Solved：Solve the error code definitions of each module，Avoid duplication，This is just a statement and will not be used in practice
 *
 * Total 10 position，Divided into four sections
 *
 * The first paragraph，1 position，Type
 *      1 - Business level exception
 *      x - Reserved
 * Second paragraph，3 position，System Type
 *      001 - User System
 *      002 - Commodity System
 *      003 - Order System
 *      004 - Payment System
 *      005 - Coupon System
 *      ... - ...
 * The third paragraph，3 position，Module
 *      No restrictions。
 *      General advice，In each system，There may be multiple modules，You can do segmentation again。Take the user system as an example：
 *          001 - OAuth2 Module
 *          002 - User Module
 *          003 - MobileCode Module
 * The fourth paragraph，3 position，Error code
 *       No restrictions on rules。
 *       General advice，Each module increments automatically。
 *
 */
public class ServiceErrorCodeRange {

    // Module infra Error code range [1-001-000-000 ~ 1-002-000-000)
    // Module system Error code range [1-002-000-000 ~ 1-003-000-000)
    // Module report Error code range [1-003-000-000 ~ 1-004-000-000)
    // Module member Error code range [1-004-000-000 ~ 1-005-000-000)
    // Module mp Error code range [1-006-000-000 ~ 1-007-000-000)
    // Module pay Error code range [1-007-000-000 ~ 1-008-000-000)
    // Module bpm Error code range [1-009-000-000 ~ 1-010-000-000)

    // Module product Error code range [1-008-000-000 ~ 1-009-000-000)
    // Module trade Error code range [1-011-000-000 ~ 1-012-000-000)
    // Module promotion Error code range [1-013-000-000 ~ 1-014-000-000)

    // Module crm Error code range [1-020-000-000 ~ 1-021-000-000)

}
