package cn.econets.blossom.module.infrastructure.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Infra Error code enumeration class
 *
 * infra System，Use 1-001-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== Parameter configuration 1-001-000-000 ==========
    ErrorCode CONFIG_NOT_EXISTS = new ErrorCode(1_001_000_001, "Parameter configuration does not exist");
    ErrorCode CONFIG_KEY_DUPLICATE = new ErrorCode(1_001_000_002, "Parameter configuration key Repeat");
    ErrorCode CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE = new ErrorCode(1_001_000_003, "Cannot delete parameter configurations of type system built-in");
    ErrorCode CONFIG_GET_VALUE_ERROR_IF_VISIBLE = new ErrorCode(1_001_000_004, "Failed to obtain parameter configuration，Reason：Getting invisible configuration is not allowed");

    // ========== Scheduled tasks 1-001-001-000 ==========
    ErrorCode JOB_NOT_EXISTS = new ErrorCode(1_001_001_000, "The scheduled task does not exist");
    ErrorCode JOB_HANDLER_EXISTS = new ErrorCode(1_001_001_001, "The processor for the scheduled task already exists");
    ErrorCode JOB_CHANGE_STATUS_INVALID = new ErrorCode(1_001_001_002, "Only allow to change to on or off state");
    ErrorCode JOB_CHANGE_STATUS_EQUALS = new ErrorCode(1_001_001_003, "The scheduled task is already in this state，No modification required");
    ErrorCode JOB_UPDATE_ONLY_NORMAL_STATUS = new ErrorCode(1_001_001_004, "Only tasks in the enabled state，Can be modified");
    ErrorCode JOB_CRON_EXPRESSION_VALID = new ErrorCode(1_001_001_005, "CRON Incorrect expression");

    // ========== API Error log 1-001-002-000 ==========
    ErrorCode API_ERROR_LOG_NOT_FOUND = new ErrorCode(1_001_002_000, "API Error log does not exist");
    ErrorCode API_ERROR_LOG_PROCESSED = new ErrorCode(1_001_002_001, "API Error log processed");

    // ========= File related 1-001-003-000 =================
    ErrorCode FILE_PATH_EXISTS = new ErrorCode(1_001_003_000, "The file path already exists");
    ErrorCode FILE_NOT_EXISTS = new ErrorCode(1_001_003_001, "File does not exist");
    ErrorCode FILE_IS_EMPTY = new ErrorCode(1_001_003_002, "The file is empty");

    // ========== Code generator 1-001-004-000 ==========
    ErrorCode CODEGEN_TABLE_EXISTS = new ErrorCode(1_003_001_000, "Table definition already exists");
    ErrorCode CODEGEN_IMPORT_TABLE_NULL = new ErrorCode(1_003_001_001, "The imported table does not exist");
    ErrorCode CODEGEN_IMPORT_COLUMNS_NULL = new ErrorCode(1_003_001_002, "The imported field does not exist");
    ErrorCode CODEGEN_TABLE_NOT_EXISTS = new ErrorCode(1_003_001_004, "Table definition does not exist");
    ErrorCode CODEGEN_COLUMN_NOT_EXISTS = new ErrorCode(1_003_001_005, "Field does not exist");
    ErrorCode CODEGEN_SYNC_COLUMNS_NULL = new ErrorCode(1_003_001_006, "The synchronized field does not exist");
    ErrorCode CODEGEN_SYNC_NONE_CHANGE = new ErrorCode(1_003_001_007, "Synchronization failed，No changes exist");
    ErrorCode CODEGEN_TABLE_INFO_TABLE_COMMENT_IS_NULL = new ErrorCode(1_003_001_008, "The database table comment is not filled in");
    ErrorCode CODEGEN_TABLE_INFO_COLUMN_COMMENT_IS_NULL = new ErrorCode(1_003_001_009, "Database table fields({})Comments not filled in");
    ErrorCode CODEGEN_MASTER_TABLE_NOT_EXISTS = new ErrorCode(1_003_001_010, "Main table(id={})Definition does not exist，Please check");
    ErrorCode CODEGEN_SUB_COLUMN_NOT_EXISTS = new ErrorCode(1_003_001_011, "Subtable fields(id={})Does not exist，Please check");
    ErrorCode CODEGEN_MASTER_GENERATION_FAIL_NO_SUB_TABLE = new ErrorCode(1_003_001_012, "Main table code generation failed，Reason：It has no subtable");
    ErrorCode CODEGEN_MASTER_GENERATION_FAIL_NO_SUB_COLUMN = new ErrorCode(1_003_001_013, "Main table code generation failed，Reason：Its subtable({})No fields");

    // ========== File Configuration 1-001-006-000 ==========
    ErrorCode FILE_CONFIG_NOT_EXISTS = new ErrorCode(1_001_006_000, "The configuration file does not exist");
    ErrorCode FILE_CONFIG_DELETE_FAIL_MASTER = new ErrorCode(1_001_006_001, "This file configuration does not allow deletion，Reason：It is the main configuration，Deleting it will cause the file to be unable to be uploaded");

    // ========== Data source configuration 1-001-007-000 ==========
    ErrorCode DATA_SOURCE_CONFIG_NOT_EXISTS = new ErrorCode(1_001_007_000, "Data source configuration does not exist");
    ErrorCode DATA_SOURCE_CONFIG_NOT_OK = new ErrorCode(1_001_007_001, "Data source configuration is incorrect，Unable to connect");

    // ========== Data source configuration 1-001-107-000 ==========
    ErrorCode DEMO_STUDENT_NOT_EXISTS = new ErrorCode(1_001_107_000, "Student does not exist");

    // ========== Student 1-001-201-000 ==========
    ErrorCode DEMO01_CONTACT_NOT_EXISTS = new ErrorCode(1_001_201_000, "The sample contact does not exist");
    ErrorCode DEMO02_CATEGORY_NOT_EXISTS = new ErrorCode(1_001_201_001, "The example category does not exist");
    ErrorCode DEMO02_CATEGORY_EXITS_CHILDREN = new ErrorCode(1_001_201_002, "Existence exists sub-example category，Cannot delete");
    ErrorCode DEMO02_CATEGORY_PARENT_NOT_EXITS = new ErrorCode(1_001_201_003,"The parent example category does not exist");
    ErrorCode DEMO02_CATEGORY_PARENT_ERROR = new ErrorCode(1_001_201_004, "Cannot set itself as the parent example category");
    ErrorCode DEMO02_CATEGORY_NAME_DUPLICATE = new ErrorCode(1_001_201_005, "An example category with this name already exists");
    ErrorCode DEMO02_CATEGORY_PARENT_IS_CHILD = new ErrorCode(1_001_201_006, "Cannot set its own sub-example category as the parent example category");
    ErrorCode DEMO03_STUDENT_NOT_EXISTS = new ErrorCode(1_001_201_007, "Student does not exist");
    ErrorCode DEMO03_GRADE_NOT_EXISTS = new ErrorCode(1_001_201_008, "The student class does not exist");
    ErrorCode DEMO03_GRADE_EXISTS = new ErrorCode(1_001_201_009, "The student class already exists");

}
