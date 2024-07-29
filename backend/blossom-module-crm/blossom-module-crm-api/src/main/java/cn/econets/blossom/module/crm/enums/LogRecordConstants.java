package cn.econets.blossom.module.crm.enums;

/**
 * CRM Operation log enumeration
 * Purpose：Unified management，Also reduced Service Various“Complex”String
 *
 */
public interface LogRecordConstants {

    // ======================= CRM_LEADS Clues =======================

    String CRM_LEADS_TYPE = "CRM Clues";
    String CRM_LEADS_CREATE_SUB_TYPE = "Create clue";
    String CRM_LEADS_CREATE_SUCCESS = "Created a clue{{#clue.name}}";
    String CRM_LEADS_UPDATE_SUB_TYPE = "Update clues";
    String CRM_LEADS_UPDATE_SUCCESS = "Updated the clues【{{#clueName}}】: {_DIFF{#updateReq}}";
    String CRM_LEADS_DELETE_SUB_TYPE = "Delete clues";
    String CRM_LEADS_DELETE_SUCCESS = "Clue deleted【{{#clueName}}】";
    String CRM_LEADS_TRANSFER_SUB_TYPE = "Transfer clues";
    String CRM_LEADS_TRANSFER_SUCCESS = "The clues【{{#clue.name}}】The person in charge from【{getAdminUserById{#clue.ownerUserId}}】Changed to【{getAdminUserById{#reqVO.newOwnerUserId}}】";
    String CRM_LEADS_TRANSLATE_SUB_TYPE = "Convert leads into customers";
    String CRM_LEADS_TRANSLATE_SUCCESS = "The clues【{{#clue.name}}】Converted into customers";

    // ======================= CRM_CUSTOMER Customer =======================

    String CRM_CUSTOMER_TYPE = "CRM Customer";
    String CRM_CUSTOMER_CREATE_SUB_TYPE = "Create a customer";
    String CRM_CUSTOMER_CREATE_SUCCESS = "Created a customer{{#customer.name}}";
    String CRM_CUSTOMER_UPDATE_SUB_TYPE = "Update Customer";
    String CRM_CUSTOMER_UPDATE_SUCCESS = "Updated the customer【{{#customerName}}】: {_DIFF{#updateReqVO}}";
    String CRM_CUSTOMER_DELETE_SUB_TYPE = "Delete customer";
    String CRM_CUSTOMER_DELETE_SUCCESS = "Deleted the customer【{{#customerName}}】";
    String CRM_CUSTOMER_TRANSFER_SUB_TYPE = "Transfer Customer";
    String CRM_CUSTOMER_TRANSFER_SUCCESS = "Will the customer【{{#customer.name}}】The person in charge from【{getAdminUserById{#customer.ownerUserId}}】Changed to【{getAdminUserById{#reqVO.newOwnerUserId}}】";
    String CRM_CUSTOMER_LOCK_SUB_TYPE = "{{#customer.lockStatus ? 'Unlock customer' : 'Lock the customer'}}";
    String CRM_CUSTOMER_LOCK_SUCCESS = "{{#customer.lockStatus ? 'Will the customer【' + #customer.name + '】Unlock' : 'Will the customer【' + #customer.name + '】Lock'}}";
    String CRM_CUSTOMER_POOL_SUB_TYPE = "Customers are placed in the high seas";
    String CRM_CUSTOMER_POOL_SUCCESS = "Will the customer【{{#customerName}}】Put into the high seas";
    String CRM_CUSTOMER_RECEIVE_SUB_TYPE = "{{#ownerUserName != null ? 'Assign customers' : 'Receive customer'}}";
    String CRM_CUSTOMER_RECEIVE_SUCCESS = "{{#ownerUserName != null ? 'Will the customer【' + #customer.name + '】Assigned to【' + #ownerUserName + '】' : 'Get the customer【' + #customer.name + '】'}}";
    String CRM_CUSTOMER_IMPORT_SUB_TYPE = "{{#isUpdate ? 'Import and update customers' : 'Import customers'}}";
    String CRM_CUSTOMER_IMPORT_SUCCESS = "{{#isUpdate ? 'Imported and updated customers【'+ #customer.name +'】' : 'Imported customers【'+ #customer.name +'】'}}";

    // ======================= CRM_CUSTOMER_LIMIT_CONFIG Customer restriction configuration =======================

    String CRM_CUSTOMER_LIMIT_CONFIG_TYPE = "CRM Customer restriction configuration";
    String CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUB_TYPE = "Create customer restriction configuration";
    String CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUCCESS = "Created【{{#limitType}}】Customer restriction configuration of type";
    String CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUB_TYPE = "Update customer restriction configuration";
    String CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUCCESS = "Updated customer restriction configuration: {_DIFF{#updateReqVO}}";
    String CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUB_TYPE = "Delete customer restriction configuration";
    String CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUCCESS = "Deleted【{{#limitType}}】Customer restriction configuration of type";

    // ======================= CRM_CUSTOMER_POOL_CONFIG Customer High Seas Rules =======================

    String CRM_CUSTOMER_POOL_CONFIG_TYPE = "CRM Customer High Seas Rules";
    String CRM_CUSTOMER_POOL_CONFIG_SUB_TYPE = "{{#isPoolConfigUpdate ? 'Update customer high seas rules' : 'Create customer high seas rules'}}";
    String CRM_CUSTOMER_POOL_CONFIG_SUCCESS = "{{#isPoolConfigUpdate ? 'Updated the client's high seas rules' : 'Created customer high seas rules'}}";

    // ======================= CRM_CONTACT Contact Person =======================

    String CRM_CONTACT_TYPE = "CRM Contact Person";
    String CRM_CONTACT_CREATE_SUB_TYPE = "Create contact";
    String CRM_CONTACT_CREATE_SUCCESS = "Created a contact{{#contact.name}}";
    String CRM_CONTACT_UPDATE_SUB_TYPE = "Update Contact";
    String CRM_CONTACT_UPDATE_SUCCESS = "Updated contacts【{{#contactName}}】: {_DIFF{#updateReqVO}}";
    String CRM_CONTACT_DELETE_SUB_TYPE = "Delete contact";
    String CRM_CONTACT_DELETE_SUCCESS = "Deleted the contact【{{#contactName}}】";
    String CRM_CONTACT_TRANSFER_SUB_TYPE = "Transfer contacts";
    String CRM_CONTACT_TRANSFER_SUCCESS = "Add contact【{{#contact.name}}】The person in charge from【{getAdminUserById{#contact.ownerUserId}}】Changed to【{getAdminUserById{#reqVO.newOwnerUserId}}】";

    // ======================= CRM_BUSINESS Business Opportunities =======================

    String CRM_BUSINESS_TYPE = "CRM Business Opportunities";
    String CRM_BUSINESS_CREATE_SUB_TYPE = "Create business opportunity";
    String CRM_BUSINESS_CREATE_SUCCESS = "Created a business opportunity{{#business.name}}";
    String CRM_BUSINESS_UPDATE_SUB_TYPE = "Update business opportunities";
    String CRM_BUSINESS_UPDATE_SUCCESS = "Updated business opportunities【{{#businessName}}】: {_DIFF{#updateReqVO}}";
    String CRM_BUSINESS_DELETE_SUB_TYPE = "Delete business opportunity";
    String CRM_BUSINESS_DELETE_SUCCESS = "Business opportunity deleted【{{#businessName}}】";
    String CRM_BUSINESS_TRANSFER_SUB_TYPE = "Transfer business opportunities";
    String CRM_BUSINESS_TRANSFER_SUCCESS = "Business Opportunities【{{#business.name}}】The person in charge from【{getAdminUserById{#business.ownerUserId}}】Changed to【{getAdminUserById{#reqVO.newOwnerUserId}}】";

    // ======================= CRM_CONTRACT Contract =======================

    String CRM_CONTRACT_TYPE = "CRM Contract";
    String CRM_CONTRACT_CREATE_SUB_TYPE = "Create a contract";
    String CRM_CONTRACT_CREATE_SUCCESS = "Contract created{{#contract.name}}";
    String CRM_CONTRACT_UPDATE_SUB_TYPE = "Update Contract";
    String CRM_CONTRACT_UPDATE_SUCCESS = "Updated the contract【{{#contractName}}】: {_DIFF{#updateReqVO}}";
    String CRM_CONTRACT_DELETE_SUB_TYPE = "Delete contract";
    String CRM_CONTRACT_DELETE_SUCCESS = "Deleted the contract【{{#contractName}}】";
    String CRM_CONTRACT_TRANSFER_SUB_TYPE = "Transfer Contract";
    String CRM_CONTRACT_TRANSFER_SUCCESS = "Contract【{{#contract.name}}】The person in charge from【{getAdminUserById{#contract.ownerUserId}}】Changed to【{getAdminUserById{#reqVO.newOwnerUserId}}】";
    String CRM_CONTRACT_SUBMIT_SUB_TYPE = "Submit contract for approval";
    String CRM_CONTRACT_SUBMIT_SUCCESS = "Submit contract【{{#contractName}}】Approval successful";

    // ======================= CRM_PRODUCT Products =======================

    String CRM_PRODUCT_TYPE = "CRM Products";
    String CRM_PRODUCT_CREATE_SUB_TYPE = "Create product";
    String CRM_PRODUCT_CREATE_SUCCESS = "Created a product【{{#createReqVO.name}}】";
    String CRM_PRODUCT_UPDATE_SUB_TYPE = "Update product";
    String CRM_PRODUCT_UPDATE_SUCCESS = "Updated product【{{#updateReqVO.name}}】: {_DIFF{#updateReqVO}}";
    String CRM_PRODUCT_DELETE_SUB_TYPE = "Delete product";
    String CRM_PRODUCT_DELETE_SUCCESS = "Product deleted【{{#product.name}}】";

    // ======================= CRM_PRODUCT_CATEGORY Product Category =======================

    String CRM_PRODUCT_CATEGORY_TYPE = "CRM Product Category";
    String CRM_PRODUCT_CATEGORY_CREATE_SUB_TYPE = "Create product categories";
    String CRM_PRODUCT_CATEGORY_CREATE_SUCCESS = "Created product category【{{#createReqVO.name}}】";
    String CRM_PRODUCT_CATEGORY_UPDATE_SUB_TYPE = "Update product categories";
    String CRM_PRODUCT_CATEGORY_UPDATE_SUCCESS = "Updated product categories【{{#updateReqVO.name}}】: {_DIFF{#updateReqVO}}";
    String CRM_PRODUCT_CATEGORY_DELETE_SUB_TYPE = "Delete product category";
    String CRM_PRODUCT_CATEGORY_DELETE_SUCCESS = "Deleted product category【{{#productCategory.name}}】";

    // ======================= CRM_RECEIVABLE Payback =======================

    String CRM_RECEIVABLE_TYPE = "CRM Payback";
    String CRM_RECEIVABLE_CREATE_SUB_TYPE = "Create payment collection";
    String CRM_RECEIVABLE_CREATE_SUCCESS = "Contract created【{getContractById{#receivable.contractId}}】No.【{{#receivable.period}}】Payment due";
    String CRM_RECEIVABLE_UPDATE_SUB_TYPE = "Update payment";
    String CRM_RECEIVABLE_UPDATE_SUCCESS = "Updated the contract【{getContractById{#receivable.contractId}}】No.【{{#receivable.period}}】Payment due: {_DIFF{#updateReqVO}}";
    String CRM_RECEIVABLE_DELETE_SUB_TYPE = "Delete payment";
    String CRM_RECEIVABLE_DELETE_SUCCESS = "Deleted the contract【{getContractById{#receivable.contractId}}】No.【{{#receivable.period}}】Payment due";

    // ======================= CRM_RECEIVABLE_PLAN Payment Refund Plan =======================

    String CRM_RECEIVABLE_PLAN_TYPE = "CRM Payment Refund Plan";
    String CRM_RECEIVABLE_PLAN_CREATE_SUB_TYPE = "Create a payment plan";
    String CRM_RECEIVABLE_PLAN_CREATE_SUCCESS = "Contract created【{getContractById{#receivablePlan.contractId}}】No.【{{#receivablePlan.period}}】Repayment plan";
    String CRM_RECEIVABLE_PLAN_UPDATE_SUB_TYPE = "Update payment plan";
    String CRM_RECEIVABLE_PLAN_UPDATE_SUCCESS = "Updated the contract【{getContractById{#receivablePlan.contractId}}】No.【{{#receivablePlan.period}}】Repayment plan: {_DIFF{#updateReqVO}}";
    String CRM_RECEIVABLE_PLAN_DELETE_SUB_TYPE = "Delete payment plan";
    String CRM_RECEIVABLE_PLAN_DELETE_SUCCESS = "Deleted the contract【{getContractById{#receivablePlan.contractId}}】No.【{{#receivablePlan.period}}】Repayment plan";

}
