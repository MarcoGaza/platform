/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1 PostgreSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 140002
 Source Host           : 127.0.0.1:5432
 Source Catalog        : econets-vue
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140002
 File Encoding         : 65001

 Date: 28/07/2022 23:48:10
*/


-- ----------------------------
-- Sequence structure for bpm_form_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_form_seq";
CREATE SEQUENCE "bpm_form_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_oa_leave_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_oa_leave_seq";
CREATE SEQUENCE "bpm_oa_leave_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_process_definition_ext_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_process_definition_ext_seq";
CREATE SEQUENCE "bpm_process_definition_ext_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_process_instance_ext_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_process_instance_ext_seq";
CREATE SEQUENCE "bpm_process_instance_ext_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_task_assign_rule_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_task_assign_rule_seq";
CREATE SEQUENCE "bpm_task_assign_rule_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_task_ext_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_task_ext_seq";
CREATE SEQUENCE "bpm_task_ext_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for bpm_user_group_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bpm_user_group_seq";
CREATE SEQUENCE "bpm_user_group_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_api_access_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_api_access_log_seq";
CREATE SEQUENCE "infra_api_access_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_api_error_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_api_error_log_seq";
CREATE SEQUENCE "infra_api_error_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_codegen_column_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_codegen_column_seq";
CREATE SEQUENCE "infra_codegen_column_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_codegen_table_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_codegen_table_seq";
CREATE SEQUENCE "infra_codegen_table_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_config_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_config_seq";
CREATE SEQUENCE "infra_config_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_data_source_config_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_data_source_config_seq";
CREATE SEQUENCE "infra_data_source_config_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_file_config_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_file_config_seq";
CREATE SEQUENCE "infra_file_config_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_file_content_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_file_content_seq";
CREATE SEQUENCE "infra_file_content_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_file_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_file_seq";
CREATE SEQUENCE "infra_file_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_job_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_job_log_seq";
CREATE SEQUENCE "infra_job_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_job_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_job_seq";
CREATE SEQUENCE "infra_job_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for infra_test_demo_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "infra_test_demo_seq";
CREATE SEQUENCE "infra_test_demo_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for member_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "member_user_seq";
CREATE SEQUENCE "member_user_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_app_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_app_seq";
CREATE SEQUENCE "pay_app_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_channel_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_channel_seq";
CREATE SEQUENCE "pay_channel_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_merchant_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_merchant_seq";
CREATE SEQUENCE "pay_merchant_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_notify_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_notify_log_seq";
CREATE SEQUENCE "pay_notify_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_notify_task_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_notify_task_seq";
CREATE SEQUENCE "pay_notify_task_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_order_extension_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_order_extension_seq";
CREATE SEQUENCE "pay_order_extension_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_order_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_order_seq";
CREATE SEQUENCE "pay_order_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for pay_refund_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "pay_refund_seq";
CREATE SEQUENCE "pay_refund_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_dept_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_dept_seq";
CREATE SEQUENCE "system_dept_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_dict_data_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_dict_data_seq";
CREATE SEQUENCE "system_dict_data_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_dict_type_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_dict_type_seq";
CREATE SEQUENCE "system_dict_type_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_error_code_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_error_code_seq";
CREATE SEQUENCE "system_error_code_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_login_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_login_log_seq";
CREATE SEQUENCE "system_login_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_menu_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_menu_seq";
CREATE SEQUENCE "system_menu_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_notice_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_notice_seq";
CREATE SEQUENCE "system_notice_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_oauth2_access_token_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_oauth2_access_token_seq";
CREATE SEQUENCE "system_oauth2_access_token_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for system_oauth2_approve_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_oauth2_approve_seq";
CREATE SEQUENCE "system_oauth2_approve_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for system_oauth2_client_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_oauth2_client_seq";
CREATE SEQUENCE "system_oauth2_client_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for system_oauth2_code_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_oauth2_code_seq";
CREATE SEQUENCE "system_oauth2_code_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for system_oauth2_refresh_token_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_oauth2_refresh_token_seq";
CREATE SEQUENCE "system_oauth2_refresh_token_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for system_operate_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_operate_log_seq";
CREATE SEQUENCE "system_operate_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_post_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_post_seq";
CREATE SEQUENCE "system_post_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_role_menu_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_role_menu_seq";
CREATE SEQUENCE "system_role_menu_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_role_seq";
CREATE SEQUENCE "system_role_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_sensitive_word_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_sensitive_word_seq";
CREATE SEQUENCE "system_sensitive_word_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_sms_channel_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_sms_channel_seq";
CREATE SEQUENCE "system_sms_channel_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_sms_code_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_sms_code_seq";
CREATE SEQUENCE "system_sms_code_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_sms_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_sms_log_seq";
CREATE SEQUENCE "system_sms_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_sms_template_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_sms_template_seq";
CREATE SEQUENCE "system_sms_template_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_social_user_bind_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_social_user_bind_seq";
CREATE SEQUENCE "system_social_user_bind_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_social_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_social_user_seq";
CREATE SEQUENCE "system_social_user_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_tenant_package_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_tenant_package_seq";
CREATE SEQUENCE "system_tenant_package_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_tenant_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_tenant_seq";
CREATE SEQUENCE "system_tenant_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_user_post_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_user_post_seq";
CREATE SEQUENCE "system_user_post_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_user_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_user_role_seq";
CREATE SEQUENCE "system_user_role_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_user_seq";
CREATE SEQUENCE "system_user_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_mail_account_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_mail_account_seq";
CREATE SEQUENCE "system_mail_account_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_mail_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_mail_log_seq";
CREATE SEQUENCE "system_mail_log_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_mail_template_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_mail_template_seq";
CREATE SEQUENCE "system_mail_template_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_notify_message_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_notify_message_seq";
CREATE SEQUENCE "system_notify_message_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_notify_template_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_notify_template_seq";
CREATE SEQUENCE "system_notify_template_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for system_user_session_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "system_user_session_seq";
CREATE SEQUENCE "system_user_session_seq"
    INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Table structure for bpm_form
-- ----------------------------
DROP TABLE IF EXISTS "bpm_form";
CREATE TABLE "bpm_form"
(
    "id"          int8                                         NOT NULL,
    "name"        varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "status"      int2                                         NOT NULL,
    "conf"        varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
    "fields"      varchar(5000) COLLATE "pg_catalog"."default" NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0,
    "tenant_id"   int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_form"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_form"."name" IS 'Form Name';
COMMENT
ON COLUMN "bpm_form"."status" IS 'Open status';
COMMENT
ON COLUMN "bpm_form"."conf" IS 'Form configuration';
COMMENT
ON COLUMN "bpm_form"."fields" IS 'Array of form items';
COMMENT
ON COLUMN "bpm_form"."remark" IS 'Remarks';
COMMENT
ON COLUMN "bpm_form"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_form"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_form"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_form"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_form"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_form"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_form" IS 'Workflow form definition';

-- ----------------------------
-- Records of bpm_form
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_oa_leave
-- ----------------------------
DROP TABLE IF EXISTS "bpm_oa_leave";
CREATE TABLE "bpm_oa_leave"
(
    "id"                  int8                                        NOT NULL,
    "user_id"             int8                                        NOT NULL,
    "type"                int2                                        NOT NULL,
    "reason"              varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "start_time"          timestamp(6)                                NOT NULL,
    "end_time"            timestamp(6)                                NOT NULL,
    "day"                 int2                                        NOT NULL,
    "result"              int2                                        NOT NULL,
    "process_instance_id" varchar(64) COLLATE "pg_catalog"."default",
    "creator"             varchar(64) COLLATE "pg_catalog"."default"           DEFAULT '':: character varying,
    "create_time"         timestamp(6)                                NOT NULL,
    "updater"             varchar(64) COLLATE "pg_catalog"."default"           DEFAULT '':: character varying,
    "update_time"         timestamp(6)                                NOT NULL,
    "deleted"             int2                                        NOT NULL DEFAULT 0,
    "tenant_id"           int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_oa_leave"."id" IS 'Leave form primary key';
COMMENT
ON COLUMN "bpm_oa_leave"."user_id" IS 'Applicant's user number';
COMMENT
ON COLUMN "bpm_oa_leave"."type" IS 'Leave type';
COMMENT
ON COLUMN "bpm_oa_leave"."reason" IS 'Reason for leave';
COMMENT
ON COLUMN "bpm_oa_leave"."start_time" IS 'Start time';
COMMENT
ON COLUMN "bpm_oa_leave"."end_time" IS 'End time';
COMMENT
ON COLUMN "bpm_oa_leave"."day" IS 'Number of days of leave';
COMMENT
ON COLUMN "bpm_oa_leave"."result" IS 'Leave result';
COMMENT
ON COLUMN "bpm_oa_leave"."process_instance_id" IS 'Process instance number';
COMMENT
ON COLUMN "bpm_oa_leave"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_oa_leave"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_oa_leave"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_oa_leave"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_oa_leave"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_oa_leave"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_oa_leave" IS 'OA Leave Application Form';

-- ----------------------------
-- Records of bpm_oa_leave
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_process_definition_ext
-- ----------------------------
DROP TABLE IF EXISTS "bpm_process_definition_ext";
CREATE TABLE "bpm_process_definition_ext"
(
    "id"                      int8                                       NOT NULL,
    "process_definition_id"   varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "model_id"                varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "description"             varchar(255) COLLATE "pg_catalog"."default",
    "form_type"               int2                                       NOT NULL,
    "form_id"                 int8,
    "form_conf"               varchar(1000) COLLATE "pg_catalog"."default",
    "form_fields"             varchar(5000) COLLATE "pg_catalog"."default",
    "form_custom_create_path" varchar(255) COLLATE "pg_catalog"."default",
    "form_custom_view_path"   varchar(255) COLLATE "pg_catalog"."default",
    "creator"                 varchar(64) COLLATE "pg_catalog"."default",
    "create_time"             timestamp(6)                               NOT NULL,
    "updater"                 varchar(64) COLLATE "pg_catalog"."default",
    "update_time"             timestamp(6)                               NOT NULL,
    "deleted"                 int2                                       NOT NULL DEFAULT 0,
    "tenant_id"               int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_process_definition_ext"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_process_definition_ext"."process_definition_id" IS 'Process definition number';
COMMENT
ON COLUMN "bpm_process_definition_ext"."model_id" IS 'Process model number';
COMMENT
ON COLUMN "bpm_process_definition_ext"."description" IS 'Description';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_type" IS 'Form type';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_id" IS 'Form number';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_conf" IS 'Form configuration';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_fields" IS 'Array of form items';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_custom_create_path" IS 'Customize the submission path of the form';
COMMENT
ON COLUMN "bpm_process_definition_ext"."form_custom_view_path" IS 'Customize the viewing path of the form';
COMMENT
ON COLUMN "bpm_process_definition_ext"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_process_definition_ext"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_process_definition_ext"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_process_definition_ext"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_process_definition_ext"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_process_definition_ext"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_process_definition_ext" IS 'Bpm Extension table of process definition
';

-- ----------------------------
-- Records of bpm_process_definition_ext
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_process_instance_ext
-- ----------------------------
DROP TABLE IF EXISTS "bpm_process_instance_ext";
CREATE TABLE "bpm_process_instance_ext"
(
    "id"                    int8                                       NOT NULL,
    "start_user_id"         int8                                       NOT NULL,
    "name"                  varchar(64) COLLATE "pg_catalog"."default",
    "process_instance_id"   varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "process_definition_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "category"              varchar(64) COLLATE "pg_catalog"."default",
    "status"                int2                                       NOT NULL,
    "result"                int2                                       NOT NULL,
    "end_time"              timestamp(6),
    "form_variables"        varchar(5000) COLLATE "pg_catalog"."default",
    "creator"               varchar(64) COLLATE "pg_catalog"."default",
    "create_time"           timestamp(6)                               NOT NULL,
    "updater"               varchar(64) COLLATE "pg_catalog"."default",
    "update_time"           timestamp(6)                               NOT NULL,
    "deleted"               int2                                       NOT NULL DEFAULT 0,
    "tenant_id"             int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_process_instance_ext"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_process_instance_ext"."start_user_id" IS 'The user ID that initiated the process';
COMMENT
ON COLUMN "bpm_process_instance_ext"."name" IS 'The name of the process instance';
COMMENT
ON COLUMN "bpm_process_instance_ext"."process_instance_id" IS 'The number of the process instance';
COMMENT
ON COLUMN "bpm_process_instance_ext"."process_definition_id" IS 'Process definition number';
COMMENT
ON COLUMN "bpm_process_instance_ext"."category" IS 'Process Classification';
COMMENT
ON COLUMN "bpm_process_instance_ext"."status" IS 'The status of the process instance';
COMMENT
ON COLUMN "bpm_process_instance_ext"."result" IS 'Result of process instance';
COMMENT
ON COLUMN "bpm_process_instance_ext"."end_time" IS 'End time';
COMMENT
ON COLUMN "bpm_process_instance_ext"."form_variables" IS 'Form value';
COMMENT
ON COLUMN "bpm_process_instance_ext"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_process_instance_ext"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_process_instance_ext"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_process_instance_ext"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_process_instance_ext"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_process_instance_ext"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_process_instance_ext" IS 'Extension of workflow process instance';

-- ----------------------------
-- Records of bpm_process_instance_ext
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_task_assign_rule
-- ----------------------------
DROP TABLE IF EXISTS "bpm_task_assign_rule";
CREATE TABLE "bpm_task_assign_rule"
(
    "id"                    int8                                         NOT NULL,
    "model_id"              varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "process_definition_id" varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "task_definition_key"   varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "type"                  int2                                         NOT NULL,
    "options"               varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"               varchar(64) COLLATE "pg_catalog"."default",
    "create_time"           timestamp(6)                                 NOT NULL,
    "updater"               varchar(64) COLLATE "pg_catalog"."default",
    "update_time"           timestamp(6)                                 NOT NULL,
    "deleted"               int2                                         NOT NULL DEFAULT 0,
    "tenant_id"             int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_task_assign_rule"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_task_assign_rule"."model_id" IS 'Process model number';
COMMENT
ON COLUMN "bpm_task_assign_rule"."process_definition_id" IS 'Process definition number';
COMMENT
ON COLUMN "bpm_task_assign_rule"."task_definition_key" IS 'Process task definition key';
COMMENT
ON COLUMN "bpm_task_assign_rule"."type" IS 'Rule Type';
COMMENT
ON COLUMN "bpm_task_assign_rule"."options" IS 'Rule value，JSON Array';
COMMENT
ON COLUMN "bpm_task_assign_rule"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_task_assign_rule"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_task_assign_rule"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_task_assign_rule"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_task_assign_rule"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_task_assign_rule"."tenant_id" IS 'Tenant number';
COMMENT
ON TABLE "bpm_task_assign_rule" IS 'Bpm Task rules table';

-- ----------------------------
-- Records of bpm_task_assign_rule
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_task_ext
-- ----------------------------
DROP TABLE IF EXISTS "bpm_task_ext";
CREATE TABLE "bpm_task_ext"
(
    "id"                    int8                                       NOT NULL,
    "assignee_user_id"      int8,
    "name"                  varchar(64) COLLATE "pg_catalog"."default",
    "task_id"               varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "result"                int2                                       NOT NULL,
    "reason"                varchar(255) COLLATE "pg_catalog"."default",
    "end_time"              timestamp(6),
    "process_instance_id"   varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "process_definition_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"               varchar(64) COLLATE "pg_catalog"."default",
    "create_time"           timestamp(6)                               NOT NULL,
    "updater"               varchar(64) COLLATE "pg_catalog"."default",
    "update_time"           timestamp(6)                               NOT NULL,
    "deleted"               int2                                       NOT NULL DEFAULT 0,
    "tenant_id"             int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_task_ext"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_task_ext"."assignee_user_id" IS 'Task approver';
COMMENT
ON COLUMN "bpm_task_ext"."name" IS 'Task name';
COMMENT
ON COLUMN "bpm_task_ext"."task_id" IS 'Task number';
COMMENT
ON COLUMN "bpm_task_ext"."result" IS 'The result of the task';
COMMENT
ON COLUMN "bpm_task_ext"."reason" IS 'Approval suggestion';
COMMENT
ON COLUMN "bpm_task_ext"."end_time" IS 'The end time of the task';
COMMENT
ON COLUMN "bpm_task_ext"."process_instance_id" IS 'Process instance number';
COMMENT
ON COLUMN "bpm_task_ext"."process_definition_id" IS 'Process definition number';
COMMENT
ON COLUMN "bpm_task_ext"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_task_ext"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_task_ext"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_task_ext"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_task_ext"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_task_ext"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_task_ext" IS 'Extension table of workflow process tasks';

-- ----------------------------
-- Records of bpm_task_ext
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for bpm_user_group
-- ----------------------------
DROP TABLE IF EXISTS "bpm_user_group";
CREATE TABLE "bpm_user_group"
(
    "id"              int8                                         NOT NULL,
    "name"            varchar(30) COLLATE "pg_catalog"."default"   NOT NULL,
    "description"     varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "member_user_ids" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "status"          int2                                         NOT NULL,
    "creator"         varchar(64) COLLATE "pg_catalog"."default",
    "create_time"     timestamp(6)                                 NOT NULL,
    "updater"         varchar(64) COLLATE "pg_catalog"."default",
    "update_time"     timestamp(6)                                 NOT NULL,
    "deleted"         int2                                         NOT NULL DEFAULT 0,
    "tenant_id"       int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "bpm_user_group"."id" IS 'Number';
COMMENT
ON COLUMN "bpm_user_group"."name" IS 'Group name';
COMMENT
ON COLUMN "bpm_user_group"."description" IS 'Description';
COMMENT
ON COLUMN "bpm_user_group"."member_user_ids" IS 'Member number array';
COMMENT
ON COLUMN "bpm_user_group"."status" IS 'Status（0Normal 1Disable）';
COMMENT
ON COLUMN "bpm_user_group"."creator" IS 'Creator';
COMMENT
ON COLUMN "bpm_user_group"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "bpm_user_group"."updater" IS 'Updater';
COMMENT
ON COLUMN "bpm_user_group"."update_time" IS 'Update time';
COMMENT
ON COLUMN "bpm_user_group"."deleted" IS 'Delete';
COMMENT
ON COLUMN "bpm_user_group"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "bpm_user_group" IS 'User Group';

-- ----------------------------
-- Records of bpm_user_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dual
-- ----------------------------
DROP TABLE IF EXISTS "dual";
CREATE TABLE "dual"
(

)
;

-- ----------------------------
-- Records of dual
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS "infra_api_access_log";
CREATE TABLE "infra_api_access_log"
(
    "id"               int8                                         NOT NULL,
    "trace_id"         varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "user_id"          int8                                         NOT NULL DEFAULT 0,
    "user_type"        int2                                         NOT NULL DEFAULT 0,
    "application_name" varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "request_method"   varchar(16) COLLATE "pg_catalog"."default"   NOT NULL,
    "request_url"      varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "request_params"   varchar(8000) COLLATE "pg_catalog"."default" NOT NULL,
    "user_ip"          varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "user_agent"       varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "begin_time"       timestamp(6)                                 NOT NULL,
    "end_time"         timestamp(6)                                 NOT NULL,
    "duration"         int4                                         NOT NULL,
    "result_code"      int4                                         NOT NULL,
    "result_msg"       varchar(512) COLLATE "pg_catalog"."default",
    "creator"          varchar(64) COLLATE "pg_catalog"."default",
    "create_time"      timestamp(6)                                 NOT NULL,
    "updater"          varchar(64) COLLATE "pg_catalog"."default",
    "update_time"      timestamp(6)                                 NOT NULL,
    "deleted"          int2                                         NOT NULL DEFAULT 0,
    "tenant_id"        int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_api_access_log"."id" IS 'Log primary key';
COMMENT
ON COLUMN "infra_api_access_log"."trace_id" IS 'Link tracking number';
COMMENT
ON COLUMN "infra_api_access_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "infra_api_access_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "infra_api_access_log"."application_name" IS 'Application name';
COMMENT
ON COLUMN "infra_api_access_log"."request_method" IS 'Request method name';
COMMENT
ON COLUMN "infra_api_access_log"."request_url" IS 'Request address';
COMMENT
ON COLUMN "infra_api_access_log"."request_params" IS 'Request parameters';
COMMENT
ON COLUMN "infra_api_access_log"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "infra_api_access_log"."user_agent" IS 'Browser UA';
COMMENT
ON COLUMN "infra_api_access_log"."begin_time" IS 'Start request time';
COMMENT
ON COLUMN "infra_api_access_log"."end_time" IS 'End request time';
COMMENT
ON COLUMN "infra_api_access_log"."duration" IS 'Execution duration';
COMMENT
ON COLUMN "infra_api_access_log"."result_code" IS 'Result code';
COMMENT
ON COLUMN "infra_api_access_log"."result_msg" IS 'Result prompt';
COMMENT
ON COLUMN "infra_api_access_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_api_access_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_api_access_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_api_access_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_api_access_log"."deleted" IS 'Delete';
COMMENT
ON COLUMN "infra_api_access_log"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "infra_api_access_log" IS 'API Access log table';

-- ----------------------------
-- Records of infra_api_access_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS "infra_api_error_log";
CREATE TABLE "infra_api_error_log"
(
    "id"                           int4                                         NOT NULL,
    "trace_id"                     varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "user_id"                      int4                                         NOT NULL DEFAULT 0,
    "user_type"                    int2                                         NOT NULL DEFAULT 0,
    "application_name"             varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "request_method"               varchar(16) COLLATE "pg_catalog"."default"   NOT NULL,
    "request_url"                  varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "request_params"               varchar(8000) COLLATE "pg_catalog"."default" NOT NULL,
    "user_ip"                      varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "user_agent"                   varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "exception_time"               timestamp(6)                                 NOT NULL,
    "exception_name"               varchar(128) COLLATE "pg_catalog"."default"  NOT NULL,
    "exception_message"            text COLLATE "pg_catalog"."default"          NOT NULL,
    "exception_root_cause_message" text COLLATE "pg_catalog"."default"          NOT NULL,
    "exception_stack_trace"        text COLLATE "pg_catalog"."default"          NOT NULL,
    "exception_class_name"         varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "exception_file_name"          varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "exception_method_name"        varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "exception_line_number"        int4                                         NOT NULL,
    "process_status"               int2                                         NOT NULL,
    "process_time"                 timestamp(6),
    "process_user_id"              int4,
    "creator"                      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"                  timestamp(6)                                 NOT NULL,
    "updater"                      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"                  timestamp(6)                                 NOT NULL,
    "deleted"                      int2                                         NOT NULL DEFAULT 0,
    "tenant_id"                    int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_api_error_log"."id" IS 'Number';
COMMENT
ON COLUMN "infra_api_error_log"."trace_id" IS 'Link tracking number
     *
     * Generally speaking，Tracking number by link，You can access the log，Error log，Link tracking log，logger Print logs, etc.，Combined together，To debug。';
COMMENT
ON COLUMN "infra_api_error_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "infra_api_error_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "infra_api_error_log"."application_name" IS 'Application name
     *
     * Currently reading spring.application.name';
COMMENT
ON COLUMN "infra_api_error_log"."request_method" IS 'Request method name';
COMMENT
ON COLUMN "infra_api_error_log"."request_url" IS 'Request address';
COMMENT
ON COLUMN "infra_api_error_log"."request_params" IS 'Request parameters';
COMMENT
ON COLUMN "infra_api_error_log"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "infra_api_error_log"."user_agent" IS 'Browser UA';
COMMENT
ON COLUMN "infra_api_error_log"."exception_time" IS 'Abnormality time';
COMMENT
ON COLUMN "infra_api_error_log"."exception_name" IS 'Exception name
     *
     * {@link Throwable#getClass()} Full class name';
COMMENT
ON COLUMN "infra_api_error_log"."exception_message" IS 'Message caused by exception
     *
     * {@link cn.econets.common.framework.util.ExceptionUtil#getMessage(Throwable)}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_root_cause_message" IS 'Root message caused by exception
     *
     * {@link cn.econets.common.framework.util.ExceptionUtil#getRootCauseMessage(Throwable)}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_stack_trace" IS 'Exception stack trace
     *
     * {@link cn.econets.common.framework.util.ExceptionUtil#getServiceException(Exception)}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_class_name" IS 'The full name of the class where the exception occurred
     *
     * {@link StackTraceElement#getClassName()}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_file_name" IS 'Class file where the exception occurred
     *
     * {@link StackTraceElement#getFileName()}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_method_name" IS 'The name of the method where the exception occurred
     *
     * {@link StackTraceElement#getMethodName()}';
COMMENT
ON COLUMN "infra_api_error_log"."exception_line_number" IS 'The line where the exception occurred
     *
     * {@link StackTraceElement#getLineNumber()}';
COMMENT
ON COLUMN "infra_api_error_log"."process_status" IS 'Processing status';
COMMENT
ON COLUMN "infra_api_error_log"."process_time" IS 'Processing time';
COMMENT
ON COLUMN "infra_api_error_log"."process_user_id" IS 'Processing user number';
COMMENT
ON COLUMN "infra_api_error_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_api_error_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_api_error_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_api_error_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_api_error_log"."deleted" IS 'Delete';
COMMENT
ON COLUMN "infra_api_error_log"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "infra_api_error_log" IS 'System abnormality log';

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS "infra_codegen_column";
CREATE TABLE "infra_codegen_column"
(
    "id"                       int8                                        NOT NULL,
    "table_id"                 int8                                        NOT NULL,
    "column_name"              varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "data_type"                varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "column_comment"           varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
    "nullable"                 bool                                        NOT NULL,
    "primary_key"              bool                                        NOT NULL,
    "auto_increment"           bool                                        NOT NULL,
    "ordinal_position"         int4                                        NOT NULL,
    "java_type"                varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "java_field"               varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "dict_type"                varchar(200) COLLATE "pg_catalog"."default",
    "example"                  varchar(255) COLLATE "pg_catalog"."default",
    "create_operation"         bool                                        NOT NULL,
    "update_operation"         bool                                        NOT NULL,
    "list_operation"           bool                                        NOT NULL,
    "list_operation_condition" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "list_operation_result"    bool                                        NOT NULL,
    "html_type"                varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "creator"                  varchar(64) COLLATE "pg_catalog"."default",
    "create_time"              timestamp(6)                                NOT NULL,
    "updater"                  varchar(64) COLLATE "pg_catalog"."default",
    "update_time"              timestamp(6)                                NOT NULL,
    "deleted"                  int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_codegen_column"."id" IS 'Number';
COMMENT
ON COLUMN "infra_codegen_column"."table_id" IS 'Table number';
COMMENT
ON COLUMN "infra_codegen_column"."column_name" IS 'Field name';
COMMENT
ON COLUMN "infra_codegen_column"."data_type" IS 'Field Type';
COMMENT
ON COLUMN "infra_codegen_column"."column_comment" IS 'Field description';
COMMENT
ON COLUMN "infra_codegen_column"."nullable" IS 'Is it allowed to be empty?';
COMMENT
ON COLUMN "infra_codegen_column"."primary_key" IS 'Is it the primary key?';
COMMENT
ON COLUMN "infra_codegen_column"."auto_increment" IS 'Whether to auto-increment';
COMMENT
ON COLUMN "infra_codegen_column"."ordinal_position" IS 'Sort';
COMMENT
ON COLUMN "infra_codegen_column"."java_type" IS 'Java Attribute type';
COMMENT
ON COLUMN "infra_codegen_column"."java_field" IS 'Java Attribute name';
COMMENT
ON COLUMN "infra_codegen_column"."dict_type" IS 'Dictionary type';
COMMENT
ON COLUMN "infra_codegen_column"."example" IS 'Data Example';
COMMENT
ON COLUMN "infra_codegen_column"."create_operation" IS 'Whether Create Create operation fields';
COMMENT
ON COLUMN "infra_codegen_column"."update_operation" IS 'Whether Update Update operation fields';
COMMENT
ON COLUMN "infra_codegen_column"."list_operation" IS 'Whether List Fields for query operations';
COMMENT
ON COLUMN "infra_codegen_column"."list_operation_condition" IS 'List Condition type of query operation';
COMMENT
ON COLUMN "infra_codegen_column"."list_operation_result" IS 'Whether List The return fields of the query operation';
COMMENT
ON COLUMN "infra_codegen_column"."html_type" IS 'Display type';
COMMENT
ON COLUMN "infra_codegen_column"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_codegen_column"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_codegen_column"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_codegen_column"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_codegen_column"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_codegen_column" IS 'Code generation table field definition';

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS "infra_codegen_table";
CREATE TABLE "infra_codegen_table"
(
    "id"                    int8                                        NOT NULL,
    "data_source_config_id" int8                                        NOT NULL,
    "scene"                 int2                                        NOT NULL,
    "table_name"            varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "table_comment"         varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
    "remark"                varchar(500) COLLATE "pg_catalog"."default",
    "module_name"           varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "business_name"         varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "class_name"            varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "class_comment"         varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "author"                varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "template_type"         int2                                        NOT NULL,
    "parent_menu_id"        int8,
    "creator"               varchar(64) COLLATE "pg_catalog"."default",
    "create_time"           timestamp(6)                                NOT NULL,
    "updater"               varchar(64) COLLATE "pg_catalog"."default",
    "update_time"           timestamp(6)                                NOT NULL,
    "deleted"               int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_codegen_table"."id" IS 'Number';
COMMENT
ON COLUMN "infra_codegen_table"."data_source_config_id" IS 'Data source configuration number';
COMMENT
ON COLUMN "infra_codegen_table"."scene" IS 'Generate scene';
COMMENT
ON COLUMN "infra_codegen_table"."table_name" IS 'Table name';
COMMENT
ON COLUMN "infra_codegen_table"."table_comment" IS 'Table description';
COMMENT
ON COLUMN "infra_codegen_table"."remark" IS 'Remarks';
COMMENT
ON COLUMN "infra_codegen_table"."module_name" IS 'Module name';
COMMENT
ON COLUMN "infra_codegen_table"."business_name" IS 'Business Name';
COMMENT
ON COLUMN "infra_codegen_table"."class_name" IS 'Class name';
COMMENT
ON COLUMN "infra_codegen_table"."class_comment" IS 'Class description';
COMMENT
ON COLUMN "infra_codegen_table"."author" IS 'Author';
COMMENT
ON COLUMN "infra_codegen_table"."template_type" IS 'Template type';
COMMENT
ON COLUMN "infra_codegen_table"."parent_menu_id" IS 'Parent menu number';
COMMENT
ON COLUMN "infra_codegen_table"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_codegen_table"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_codegen_table"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_codegen_table"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_codegen_table"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_codegen_table" IS 'Code generation table definition';

ALTER TABLE infra_codegen_table
    ADD front_type int4 NOT NULL;
COMMENT
ON COLUMN  infra_codegen_table.front_type IS 'Front-end type';

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS "infra_config";
CREATE TABLE "infra_config"
(
    "id"          int4                                        NOT NULL,
    "category"    varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "type"        int2                                        NOT NULL,
    "name"        varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "config_key"  varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "value"       varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
    "visible"     varchar(5) COLLATE "pg_catalog"."default"   NOT NULL,
    "remark"      varchar(500) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_config"."id" IS 'Parameter primary key';
COMMENT
ON COLUMN "infra_config"."category" IS 'Parameter grouping';
COMMENT
ON COLUMN "infra_config"."type" IS 'Parameter type';
COMMENT
ON COLUMN "infra_config"."name" IS 'Parameter name';
COMMENT
ON COLUMN "infra_config"."config_key" IS 'Parameter key name';
COMMENT
ON COLUMN "infra_config"."value" IS 'Parameter key value';
COMMENT
ON COLUMN "infra_config"."visible" IS 'Is it visible?';
COMMENT
ON COLUMN "infra_config"."remark" IS 'Remarks';
COMMENT
ON COLUMN "infra_config"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_config"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_config"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_config"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_config"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_config" IS 'Parameter configuration table';

-- ----------------------------
-- Records of infra_config
-- ----------------------------
BEGIN;
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (1, 'ui', 1, 'Main frame page-Default skin style name', 'sys.index.skinName', 'skin-blue', '0',
        'Blue skin-blue、Green skin-green、Purple skin-purple、Red skin-red、Yellow skin-yellow', 'admin',
        '2021-01-05 17:03:48', '1', '2022-03-26 23:10:31', 0);
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (2, 'biz', 1, 'User Management-Account initial password', 'sys.user.init-password', '123456', '0', 'Initialize password 123456', 'admin',
        '2021-01-05 17:03:48', '1', '2022-03-20 02:25:51', 0);
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (3, 'ui', 1, 'Main frame page-Sidebar theme', 'sys.index.sideTheme', 'theme-dark', '0',
        'Dark themetheme-dark，Light themetheme-light', 'admin', '2021-01-05 17:03:48', '', '2021-01-19 03:05:21', 0);
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (4, '1', 2, 'xxx', 'demo.test', '10', '0', '5', '', '2021-01-19 03:10:26', '', '2021-01-20 09:25:55', 0);
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (5, 'xxx', 2, 'xxx', 'xxx', 'xxx', '1', 'xxx', '', '2021-02-09 20:06:47', '', '2021-02-09 20:06:47', 0);
INSERT INTO "infra_config" ("id", "category", "type", "name", "config_key", "value", "visible", "remark", "creator",
                            "create_time", "updater", "update_time", "deleted")
VALUES (6, 'biz', 2, 'Login verification code switch', 'blossom.captcha.enable', 'true', '1', NULL, '1', '2022-02-17 00:03:11', '1',
        '2022-04-04 12:51:40', 0);
COMMIT;

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS "infra_data_source_config";
CREATE TABLE "infra_data_source_config"
(
    "id"          int8                                         NOT NULL,
    "name"        varchar(100) COLLATE "pg_catalog"."default"  NOT NULL,
    "url"         varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "username"    varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"    varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_data_source_config"."id" IS 'Primary key number';
COMMENT
ON COLUMN "infra_data_source_config"."name" IS 'Parameter name';
COMMENT
ON COLUMN "infra_data_source_config"."url" IS 'Data source connection';
COMMENT
ON COLUMN "infra_data_source_config"."username" IS 'Username';
COMMENT
ON COLUMN "infra_data_source_config"."password" IS 'Password';
COMMENT
ON COLUMN "infra_data_source_config"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_data_source_config"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_data_source_config"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_data_source_config"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_data_source_config"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_data_source_config" IS 'Data source configuration table';

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS "infra_file";
CREATE TABLE "infra_file"
(
    "id"          int8                                         NOT NULL DEFAULT 0,
    "config_id"   int8,
    "path"        varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "url"         varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "type"        varchar(127) COLLATE "pg_catalog"."default",
    "size"        int4                                         NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0,
    "name"        varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "infra_file"."id" IS 'File number';
COMMENT
ON COLUMN "infra_file"."config_id" IS 'Configuration number';
COMMENT
ON COLUMN "infra_file"."path" IS 'File path';
COMMENT
ON COLUMN "infra_file"."url" IS 'File URL';
COMMENT
ON COLUMN "infra_file"."type" IS 'File type';
COMMENT
ON COLUMN "infra_file"."size" IS 'File size';
COMMENT
ON COLUMN "infra_file"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_file"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_file"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_file"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_file"."deleted" IS 'Delete';
COMMENT
ON COLUMN "infra_file"."name" IS 'File name';
COMMENT
ON TABLE "infra_file" IS 'File List';

-- ----------------------------
-- Records of infra_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS "infra_file_config";
CREATE TABLE "infra_file_config"
(
    "id"          int8                                         NOT NULL,
    "name"        varchar(63) COLLATE "pg_catalog"."default"   NOT NULL,
    "storage"     int2                                         NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "master"      bool                                         NOT NULL,
    "config"      varchar(4096) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_file_config"."id" IS 'Number';
COMMENT
ON COLUMN "infra_file_config"."name" IS 'Configuration name';
COMMENT
ON COLUMN "infra_file_config"."storage" IS 'Memory';
COMMENT
ON COLUMN "infra_file_config"."remark" IS 'Remarks';
COMMENT
ON COLUMN "infra_file_config"."master" IS 'Whether to be the main configuration';
COMMENT
ON COLUMN "infra_file_config"."config" IS 'Storage Configuration';
COMMENT
ON COLUMN "infra_file_config"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_file_config"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_file_config"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_file_config"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_file_config"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_file_config" IS 'File configuration table';

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
BEGIN;
INSERT INTO "infra_file_config" ("id", "name", "storage", "remark", "master", "config", "creator", "create_time",
                                 "updater", "update_time", "deleted")
VALUES (4, 'Database', 1, 'I am a database', 'f',
        '{"@class":"cn.econets.blossom.framework.file.core.client.db.DBFileClientConfig","domain":"http://127.0.0.1:48080"}',
        '1', '2022-03-15 23:56:24', '1', '2022-03-26 21:39:26', 0);
INSERT INTO "infra_file_config" ("id", "name", "storage", "remark", "master", "config", "creator", "create_time",
                                 "updater", "update_time", "deleted")
VALUES (5, 'Local Disk', 10, 'Test local storage', 'f',
        '{"@class":"cn.econets.blossom.framework.file.core.client.local.LocalFileClientConfig","basePath":"/Users/econets/file_test","domain":"http://127.0.0.1:48080"}',
        '1', '2022-03-15 23:57:00', '1', '2022-03-26 21:39:26', 0);
INSERT INTO "infra_file_config" ("id", "name", "storage", "remark", "master", "config", "creator", "create_time",
                                 "updater", "update_time", "deleted")
VALUES (11, 'S3 - Qiniu Cloud', 20, NULL, 't',
        '{"@class":"cn.econets.blossom.framework.file.core.client.s3.S3FileClientConfig","endpoint":"s3-cn-south-1.qiniucs.com","domain":"http://test.blossom.econets.cn","bucket":"econets-vue","accessKey":"b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8","accessSecret":"kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP"}',
        '1', '2022-03-19 18:00:03', '1', '2022-05-26 00:03:47.17', 0);
COMMIT;

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS "infra_file_content";
CREATE TABLE "infra_file_content"
(
    "id"          int8                                        NOT NULL,
    "config_id"   int8                                        NOT NULL,
    "path"        varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
    "content"     bytea                                       NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_file_content"."id" IS 'Number';
COMMENT
ON COLUMN "infra_file_content"."config_id" IS 'Configuration number';
COMMENT
ON COLUMN "infra_file_content"."path" IS 'File path';
COMMENT
ON COLUMN "infra_file_content"."content" IS 'File contents';
COMMENT
ON COLUMN "infra_file_content"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_file_content"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_file_content"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_file_content"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_file_content"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_file_content" IS 'File List';

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS "infra_job";
CREATE TABLE "infra_job"
(
    "id"              int8                                       NOT NULL,
    "name"            varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "status"          int2                                       NOT NULL,
    "handler_name"    varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "handler_param"   varchar(255) COLLATE "pg_catalog"."default",
    "cron_expression" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "retry_count"     int4                                       NOT NULL,
    "retry_interval"  int4                                       NOT NULL,
    "monitor_timeout" int4                                       NOT NULL,
    "creator"         varchar(64) COLLATE "pg_catalog"."default",
    "create_time"     timestamp(6)                               NOT NULL,
    "updater"         varchar(64) COLLATE "pg_catalog"."default",
    "update_time"     timestamp(6)                               NOT NULL,
    "deleted"         int2                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_job"."id" IS 'Task number';
COMMENT
ON COLUMN "infra_job"."name" IS 'Task Name';
COMMENT
ON COLUMN "infra_job"."status" IS 'Task Status';
COMMENT
ON COLUMN "infra_job"."handler_name" IS 'Processor name';
COMMENT
ON COLUMN "infra_job"."handler_param" IS 'Processor parameters';
COMMENT
ON COLUMN "infra_job"."cron_expression" IS 'CRON Expression';
COMMENT
ON COLUMN "infra_job"."retry_count" IS 'Number of retries';
COMMENT
ON COLUMN "infra_job"."retry_interval" IS 'Retry interval';
COMMENT
ON COLUMN "infra_job"."monitor_timeout" IS 'Monitoring timeout';
COMMENT
ON COLUMN "infra_job"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_job"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_job"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_job"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_job"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_job" IS 'Scheduled Task List';

-- ----------------------------
-- Records of infra_job
-- ----------------------------
BEGIN;
INSERT INTO "infra_job" ("id", "name", "status", "handler_name", "handler_param", "cron_expression", "retry_count",
                         "retry_interval", "monitor_timeout", "creator", "create_time", "updater", "update_time",
                         "deleted")
VALUES (5, 'Payment Notice Job', 2, 'payNotifyJob', NULL, '* * * * * ?', 0, 0, 0, '1', '2021-10-27 08:34:42', '1',
        '2022-04-03 20:35:25', 0);
COMMIT;

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS "infra_job_log";
CREATE TABLE "infra_job_log"
(
    "id"            int8                                       NOT NULL,
    "job_id"        int8                                       NOT NULL,
    "handler_name"  varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "handler_param" varchar(255) COLLATE "pg_catalog"."default",
    "execute_index" int2                                       NOT NULL,
    "begin_time"    timestamp(6)                               NOT NULL,
    "end_time"      timestamp(6),
    "duration"      int4,
    "status"        int2                                       NOT NULL,
    "result"        varchar(4000) COLLATE "pg_catalog"."default",
    "creator"       varchar(64) COLLATE "pg_catalog"."default",
    "create_time"   timestamp(6)                               NOT NULL,
    "updater"       varchar(64) COLLATE "pg_catalog"."default",
    "update_time"   timestamp(6)                               NOT NULL,
    "deleted"       int2                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_job_log"."id" IS 'Log number';
COMMENT
ON COLUMN "infra_job_log"."job_id" IS 'Task number';
COMMENT
ON COLUMN "infra_job_log"."handler_name" IS 'Processor name';
COMMENT
ON COLUMN "infra_job_log"."handler_param" IS 'Processor parameters';
COMMENT
ON COLUMN "infra_job_log"."execute_index" IS 'Number of executions';
COMMENT
ON COLUMN "infra_job_log"."begin_time" IS 'Start execution time';
COMMENT
ON COLUMN "infra_job_log"."end_time" IS 'End execution time';
COMMENT
ON COLUMN "infra_job_log"."duration" IS 'Execution duration';
COMMENT
ON COLUMN "infra_job_log"."status" IS 'Task Status';
COMMENT
ON COLUMN "infra_job_log"."result" IS 'Result data';
COMMENT
ON COLUMN "infra_job_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_job_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_job_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_job_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_job_log"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_job_log" IS 'Scheduled task log table';

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_test_demo
-- ----------------------------
DROP TABLE IF EXISTS "infra_test_demo";
CREATE TABLE "infra_test_demo"
(
    "id"          int8                                        NOT NULL,
    "name"        varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "status"      int2                                        NOT NULL,
    "type"        int2                                        NOT NULL,
    "category"    int2                                        NOT NULL,
    "remark"      varchar(500) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "infra_test_demo"."id" IS 'Number';
COMMENT
ON COLUMN "infra_test_demo"."name" IS 'Name';
COMMENT
ON COLUMN "infra_test_demo"."status" IS 'Status';
COMMENT
ON COLUMN "infra_test_demo"."type" IS 'Type';
COMMENT
ON COLUMN "infra_test_demo"."category" IS 'Classification';
COMMENT
ON COLUMN "infra_test_demo"."remark" IS 'Remarks';
COMMENT
ON COLUMN "infra_test_demo"."creator" IS 'Creator';
COMMENT
ON COLUMN "infra_test_demo"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "infra_test_demo"."updater" IS 'Updater';
COMMENT
ON COLUMN "infra_test_demo"."update_time" IS 'Update time';
COMMENT
ON COLUMN "infra_test_demo"."deleted" IS 'Delete';
COMMENT
ON TABLE "infra_test_demo" IS 'Dictionary type table';

-- ----------------------------
-- Records of infra_test_demo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for member_user
-- ----------------------------
DROP TABLE IF EXISTS "member_user";
CREATE TABLE "member_user"
(
    "id"          int8                                        NOT NULL,
    "nickname"    varchar(30) COLLATE "pg_catalog"."default"  NOT NULL DEFAULT '',
    "avatar"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '',
    "status"      int2                                        NOT NULL,
    "mobile"      varchar(11) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"    varchar(100) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '',
    "register_ip" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "login_ip"    varchar(50) COLLATE "pg_catalog"."default"           DEFAULT '',
    "login_date"  timestamp(6),
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0,
    "tenant_id"   int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "member_user"."id" IS 'Number';
COMMENT
ON COLUMN "member_user"."nickname" IS 'User Nickname';
COMMENT
ON COLUMN "member_user"."avatar" IS 'Avatar';
COMMENT
ON COLUMN "member_user"."status" IS 'Status';
COMMENT
ON COLUMN "member_user"."mobile" IS 'Mobile phone number';
COMMENT
ON COLUMN "member_user"."password" IS 'Password';
COMMENT
ON COLUMN "member_user"."register_ip" IS 'Register IP';
COMMENT
ON COLUMN "member_user"."login_ip" IS 'Last loginIP';
COMMENT
ON COLUMN "member_user"."login_date" IS 'Last login time';
COMMENT
ON COLUMN "member_user"."creator" IS 'Creator';
COMMENT
ON COLUMN "member_user"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "member_user"."updater" IS 'Updater';
COMMENT
ON COLUMN "member_user"."update_time" IS 'Update time';
COMMENT
ON COLUMN "member_user"."deleted" IS 'Delete';
COMMENT
ON COLUMN "member_user"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "member_user" IS 'User';

-- ----------------------------
-- Records of member_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_app
-- ----------------------------
DROP TABLE IF EXISTS "pay_app";
CREATE TABLE "pay_app"
(
    "id"                int8                                         NOT NULL,
    "name"              varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "status"            int2                                         NOT NULL,
    "remark"            varchar(255) COLLATE "pg_catalog"."default",
    "pay_notify_url"    varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "refund_notify_url" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "merchant_id"       int8                                         NOT NULL,
    "creator"           varchar(64) COLLATE "pg_catalog"."default",
    "create_time"       timestamp(6)                                 NOT NULL,
    "updater"           varchar(64) COLLATE "pg_catalog"."default",
    "update_time"       timestamp(6)                                 NOT NULL,
    "deleted"           int2                                         NOT NULL DEFAULT 0,
    "tenant_id"         int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_app"."id" IS 'Application number';
COMMENT
ON COLUMN "pay_app"."name" IS 'Application name';
COMMENT
ON COLUMN "pay_app"."status" IS 'Open status';
COMMENT
ON COLUMN "pay_app"."remark" IS 'Remarks';
COMMENT
ON COLUMN "pay_app"."pay_notify_url" IS 'Payment result callback address';
COMMENT
ON COLUMN "pay_app"."refund_notify_url" IS 'Refund result callback address';
COMMENT
ON COLUMN "pay_app"."merchant_id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_app"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_app"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_app"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_app"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_app"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_app"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_app" IS 'Payment application information';

-- ----------------------------
-- Records of pay_app
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_channel
-- ----------------------------
DROP TABLE IF EXISTS "pay_channel";
CREATE TABLE "pay_channel"
(
    "id"          int8                                         NOT NULL,
    "code"        varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "status"      int2                                         NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "fee_rate"    float8                                       NOT NULL,
    "merchant_id" int8                                         NOT NULL,
    "app_id"      int8                                         NOT NULL,
    "config"      varchar(4096) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0,
    "tenant_id"   int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_channel"."id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_channel"."code" IS 'Channel Code';
COMMENT
ON COLUMN "pay_channel"."status" IS 'Open status';
COMMENT
ON COLUMN "pay_channel"."remark" IS 'Remarks';
COMMENT
ON COLUMN "pay_channel"."fee_rate" IS 'Channel Rate，Unit：Percentage';
COMMENT
ON COLUMN "pay_channel"."merchant_id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_channel"."app_id" IS 'Application number';
COMMENT
ON COLUMN "pay_channel"."config" IS 'Payment channel configuration';
COMMENT
ON COLUMN "pay_channel"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_channel"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_channel"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_channel"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_channel"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_channel"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_channel" IS 'Payment channel
';

-- ----------------------------
-- Records of pay_channel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_merchant
-- ----------------------------
DROP TABLE IF EXISTS "pay_merchant";
CREATE TABLE "pay_merchant"
(
    "id"          int8                                       NOT NULL,
    "no"          varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "short_name"  varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "status"      int2                                       NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                               NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                               NOT NULL,
    "deleted"     int2                                       NOT NULL DEFAULT 0,
    "tenant_id"   int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_merchant"."id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_merchant"."no" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_merchant"."name" IS 'Merchant Full Name';
COMMENT
ON COLUMN "pay_merchant"."short_name" IS 'Merchant's short name';
COMMENT
ON COLUMN "pay_merchant"."status" IS 'Open status';
COMMENT
ON COLUMN "pay_merchant"."remark" IS 'Remarks';
COMMENT
ON COLUMN "pay_merchant"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_merchant"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_merchant"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_merchant"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_merchant"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_merchant"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_merchant" IS 'Payment merchant information';

-- ----------------------------
-- Records of pay_merchant
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_notify_log
-- ----------------------------
DROP TABLE IF EXISTS "pay_notify_log";
CREATE TABLE "pay_notify_log"
(
    "id"           int8                                         NOT NULL,
    "task_id"      int8                                         NOT NULL,
    "notify_times" int2                                         NOT NULL,
    "response"     varchar(2048) COLLATE "pg_catalog"."default" NOT NULL,
    "status"       int2                                         NOT NULL,
    "creator"      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"  timestamp(6)                                 NOT NULL,
    "updater"      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)                                 NOT NULL,
    "deleted"      int2                                         NOT NULL DEFAULT 0,
    "tenant_id"    int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_notify_log"."id" IS 'Log number';
COMMENT
ON COLUMN "pay_notify_log"."task_id" IS 'Notification task number';
COMMENT
ON COLUMN "pay_notify_log"."notify_times" IS 'Number of times I was notified';
COMMENT
ON COLUMN "pay_notify_log"."response" IS 'Request parameters';
COMMENT
ON COLUMN "pay_notify_log"."status" IS 'Notification status';
COMMENT
ON COLUMN "pay_notify_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_notify_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_notify_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_notify_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_notify_log"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_notify_log"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_notify_log" IS 'Payment Notice App Log';

-- ----------------------------
-- Records of pay_notify_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_notify_task
-- ----------------------------
DROP TABLE IF EXISTS "pay_notify_task";
CREATE TABLE "pay_notify_task"
(
    "id"                int8                                         NOT NULL,
    "merchant_id"       int8                                         NOT NULL,
    "app_id"            int8                                         NOT NULL,
    "type"              int2                                         NOT NULL,
    "data_id"           int8                                         NOT NULL,
    "status"            int2                                         NOT NULL,
    "merchant_order_id" varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "next_notify_time"  timestamp(6)                                 NOT NULL,
    "last_execute_time" timestamp(6)                                 NOT NULL,
    "notify_times"      int2                                         NOT NULL,
    "max_notify_times"  int2                                         NOT NULL,
    "notify_url"        varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"           varchar(64) COLLATE "pg_catalog"."default",
    "create_time"       timestamp(6)                                 NOT NULL,
    "updater"           varchar(64) COLLATE "pg_catalog"."default",
    "update_time"       timestamp(6)                                 NOT NULL,
    "deleted"           int2                                         NOT NULL DEFAULT 0,
    "tenant_id"         int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_notify_task"."id" IS 'Task number';
COMMENT
ON COLUMN "pay_notify_task"."merchant_id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_notify_task"."app_id" IS 'Application number';
COMMENT
ON COLUMN "pay_notify_task"."type" IS 'Notification type';
COMMENT
ON COLUMN "pay_notify_task"."data_id" IS 'Data number';
COMMENT
ON COLUMN "pay_notify_task"."status" IS 'Notification status';
COMMENT
ON COLUMN "pay_notify_task"."merchant_order_id" IS 'Merchant order number';
COMMENT
ON COLUMN "pay_notify_task"."next_notify_time" IS 'Next notification time';
COMMENT
ON COLUMN "pay_notify_task"."last_execute_time" IS 'Last execution time';
COMMENT
ON COLUMN "pay_notify_task"."notify_times" IS 'Current notification count';
COMMENT
ON COLUMN "pay_notify_task"."max_notify_times" IS 'Maximum number of notifications';
COMMENT
ON COLUMN "pay_notify_task"."notify_url" IS 'Asynchronous notification address';
COMMENT
ON COLUMN "pay_notify_task"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_notify_task"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_notify_task"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_notify_task"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_notify_task"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_notify_task"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_notify_task" IS 'Merchant Payment、Notification of refund, etc.
';

-- ----------------------------
-- Records of pay_notify_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS "pay_order";
CREATE TABLE "pay_order"
(
    "id"                   int8                                         NOT NULL,
    "merchant_id"          int8                                         NOT NULL,
    "app_id"               int8                                         NOT NULL,
    "channel_id"           int8,
    "channel_code"         varchar(32) COLLATE "pg_catalog"."default",
    "merchant_order_id"    varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "subject"              varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "body"                 varchar(128) COLLATE "pg_catalog"."default"  NOT NULL,
    "notify_url"           varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "notify_status"        int2                                         NOT NULL,
    "amount"               int8                                         NOT NULL,
    "channel_fee_rate"     float8,
    "channel_fee_amount"   int8,
    "status"               int2                                         NOT NULL,
    "user_ip"              varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "expire_time"          timestamp(6)                                 NOT NULL,
    "success_time"         timestamp(6),
    "notify_time"          timestamp(6),
    "success_extension_id" int8,
    "refund_status"        int2                                         NOT NULL,
    "refund_times"         int2                                         NOT NULL,
    "refund_amount"        int8                                         NOT NULL,
    "channel_user_id"      varchar(255) COLLATE "pg_catalog"."default",
    "channel_order_no"     varchar(64) COLLATE "pg_catalog"."default",
    "creator"              varchar(64) COLLATE "pg_catalog"."default",
    "create_time"          timestamp(6)                                 NOT NULL,
    "updater"              varchar(64) COLLATE "pg_catalog"."default",
    "update_time"          timestamp(6)                                 NOT NULL,
    "deleted"              int2                                         NOT NULL DEFAULT 0,
    "tenant_id"            int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_order"."id" IS 'Payment order number';
COMMENT
ON COLUMN "pay_order"."merchant_id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_order"."app_id" IS 'Application number';
COMMENT
ON COLUMN "pay_order"."channel_id" IS 'Channel Number';
COMMENT
ON COLUMN "pay_order"."channel_code" IS 'Channel Code';
COMMENT
ON COLUMN "pay_order"."merchant_order_id" IS 'Merchant order number';
COMMENT
ON COLUMN "pay_order"."subject" IS 'Product Title';
COMMENT
ON COLUMN "pay_order"."body" IS 'Product Description';
COMMENT
ON COLUMN "pay_order"."notify_url" IS 'Asynchronous notification address';
COMMENT
ON COLUMN "pay_order"."notify_status" IS 'Notify the merchant of the callback status of the payment result';
COMMENT
ON COLUMN "pay_order"."amount" IS 'Payment amount，Unit：Points';
COMMENT
ON COLUMN "pay_order"."channel_fee_rate" IS 'Channel Fee，Unit：Percentage';
COMMENT
ON COLUMN "pay_order"."channel_fee_amount" IS 'Channel fee amount，Unit：Points';
COMMENT
ON COLUMN "pay_order"."status" IS 'Payment Status';
COMMENT
ON COLUMN "pay_order"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "pay_order"."expire_time" IS 'Order expiration time';
COMMENT
ON COLUMN "pay_order"."success_time" IS 'Time when order payment is successful';
COMMENT
ON COLUMN "pay_order"."notify_time" IS 'Order payment notification time';
COMMENT
ON COLUMN "pay_order"."success_extension_id" IS 'The order extension number of successful payment';
COMMENT
ON COLUMN "pay_order"."refund_status" IS 'Refund Status';
COMMENT
ON COLUMN "pay_order"."refund_times" IS 'Number of refunds';
COMMENT
ON COLUMN "pay_order"."refund_amount" IS 'Total refund amount，Unit：Points';
COMMENT
ON COLUMN "pay_order"."channel_user_id" IS 'Channel User Number';
COMMENT
ON COLUMN "pay_order"."channel_order_no" IS 'Channel order number';
COMMENT
ON COLUMN "pay_order"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_order"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_order"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_order"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_order"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_order"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_order" IS 'Payment order
';

-- ----------------------------
-- Records of pay_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_order_extension
-- ----------------------------
DROP TABLE IF EXISTS "pay_order_extension";
CREATE TABLE "pay_order_extension"
(
    "id"                  int8                                       NOT NULL,
    "no"                  varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "order_id"            int8                                       NOT NULL,
    "channel_id"          int8                                       NOT NULL,
    "channel_code"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "user_ip"             varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "status"              int2                                       NOT NULL,
    "channel_extras"      varchar(256) COLLATE "pg_catalog"."default",
    "channel_notify_data" varchar(1024) COLLATE "pg_catalog"."default",
    "creator"             varchar(64) COLLATE "pg_catalog"."default",
    "create_time"         timestamp(6)                               NOT NULL,
    "updater"             varchar(64) COLLATE "pg_catalog"."default",
    "update_time"         timestamp(6)                               NOT NULL,
    "deleted"             int2                                       NOT NULL DEFAULT 0,
    "tenant_id"           int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_order_extension"."id" IS 'Payment order number';
COMMENT
ON COLUMN "pay_order_extension"."no" IS 'Payment order number';
COMMENT
ON COLUMN "pay_order_extension"."order_id" IS 'Payment order number';
COMMENT
ON COLUMN "pay_order_extension"."channel_id" IS 'Channel Number';
COMMENT
ON COLUMN "pay_order_extension"."channel_code" IS 'Channel Code';
COMMENT
ON COLUMN "pay_order_extension"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "pay_order_extension"."status" IS 'Payment Status';
COMMENT
ON COLUMN "pay_order_extension"."channel_extras" IS 'Additional parameters for payment channels';
COMMENT
ON COLUMN "pay_order_extension"."channel_notify_data" IS 'Content of payment channel asynchronous notification';
COMMENT
ON COLUMN "pay_order_extension"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_order_extension"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_order_extension"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_order_extension"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_order_extension"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_order_extension"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_order_extension" IS 'Payment order
';

-- ----------------------------
-- Records of pay_order_extension
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pay_refund
-- ----------------------------
DROP TABLE IF EXISTS "pay_refund";
CREATE TABLE "pay_refund"
(
    "id"                 int8                                         NOT NULL,
    "merchant_id"        int8                                         NOT NULL,
    "app_id"             int8                                         NOT NULL,
    "channel_id"         int8                                         NOT NULL,
    "channel_code"       varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "order_id"           int8                                         NOT NULL,
    "trade_no"           varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "merchant_order_id"  varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "merchant_refund_no" varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "notify_url"         varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "notify_status"      int2                                         NOT NULL,
    "status"             int2                                         NOT NULL,
    "type"               int2                                         NOT NULL,
    "pay_amount"         int8                                         NOT NULL,
    "refund_amount"      int8                                         NOT NULL,
    "reason"             varchar(256) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_ip"            varchar(50) COLLATE "pg_catalog"."default",
    "channel_order_no"   varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "channel_refund_no"  varchar(64) COLLATE "pg_catalog"."default",
    "channel_error_code" varchar(128) COLLATE "pg_catalog"."default",
    "channel_error_msg"  varchar(256) COLLATE "pg_catalog"."default",
    "channel_extras"     varchar(1024) COLLATE "pg_catalog"."default",
    "expire_time"        timestamp(6),
    "success_time"       timestamp(6),
    "notify_time"        timestamp(6),
    "creator"            varchar(64) COLLATE "pg_catalog"."default",
    "create_time"        timestamp(6)                                 NOT NULL,
    "updater"            varchar(64) COLLATE "pg_catalog"."default",
    "update_time"        timestamp(6)                                 NOT NULL,
    "deleted"            int2                                         NOT NULL DEFAULT 0,
    "tenant_id"          int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "pay_refund"."id" IS 'Payment refund number';
COMMENT
ON COLUMN "pay_refund"."merchant_id" IS 'Merchant Number';
COMMENT
ON COLUMN "pay_refund"."app_id" IS 'Application number';
COMMENT
ON COLUMN "pay_refund"."channel_id" IS 'Channel Number';
COMMENT
ON COLUMN "pay_refund"."channel_code" IS 'Channel Code';
COMMENT
ON COLUMN "pay_refund"."order_id" IS 'Payment order number pay_order Tableid';
COMMENT
ON COLUMN "pay_refund"."trade_no" IS 'Transaction order number pay_extension Tableno Field';
COMMENT
ON COLUMN "pay_refund"."merchant_order_id" IS 'Merchant order number（Merchant system generated）';
COMMENT
ON COLUMN "pay_refund"."merchant_refund_no" IS 'Merchant refund order number（Merchant system generated）';
COMMENT
ON COLUMN "pay_refund"."notify_url" IS 'Asynchronous notification of merchant address';
COMMENT
ON COLUMN "pay_refund"."notify_status" IS 'Notify the merchant of the callback status of the refund result';
COMMENT
ON COLUMN "pay_refund"."status" IS 'Refund Status';
COMMENT
ON COLUMN "pay_refund"."type" IS 'Refund Type(Partial Refund，Full refund)';
COMMENT
ON COLUMN "pay_refund"."pay_amount" IS 'Payment amount,Unit points';
COMMENT
ON COLUMN "pay_refund"."refund_amount" IS 'Refund amount,Unit points';
COMMENT
ON COLUMN "pay_refund"."reason" IS 'Refund reason';
COMMENT
ON COLUMN "pay_refund"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "pay_refund"."channel_order_no" IS 'Channel order number，pay_order Inchannel_order_no Corresponding';
COMMENT
ON COLUMN "pay_refund"."channel_refund_no" IS 'Channel refund order number，Channel return';
COMMENT
ON COLUMN "pay_refund"."channel_error_code" IS 'When channel call reports an error，Error code';
COMMENT
ON COLUMN "pay_refund"."channel_error_msg" IS 'Channel call error，Error message';
COMMENT
ON COLUMN "pay_refund"."channel_extras" IS 'Additional parameters for payment channels';
COMMENT
ON COLUMN "pay_refund"."expire_time" IS 'Refund expiration date';
COMMENT
ON COLUMN "pay_refund"."success_time" IS 'Refund success time';
COMMENT
ON COLUMN "pay_refund"."notify_time" IS 'Refund notification time';
COMMENT
ON COLUMN "pay_refund"."creator" IS 'Creator';
COMMENT
ON COLUMN "pay_refund"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "pay_refund"."updater" IS 'Updater';
COMMENT
ON COLUMN "pay_refund"."update_time" IS 'Update time';
COMMENT
ON COLUMN "pay_refund"."deleted" IS 'Delete';
COMMENT
ON COLUMN "pay_refund"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "pay_refund" IS 'Refund order';

-- ----------------------------
-- Records of pay_refund
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_blob_triggers";
CREATE TABLE "qrtz_blob_triggers"
(
    "sched_name"    varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_name"  varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "blob_data"     bytea
)
;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_calendars";
CREATE TABLE "qrtz_calendars"
(
    "sched_name"    varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "calendar_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "calendar"      bytea                                       NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_cron_triggers";
CREATE TABLE "qrtz_cron_triggers"
(
    "sched_name"      varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_name"    varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group"   varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "cron_expression" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "time_zone_id"    varchar(80) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_fired_triggers";
CREATE TABLE "qrtz_fired_triggers"
(
    "sched_name"        varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "entry_id"          varchar(95) COLLATE "pg_catalog"."default"  NOT NULL,
    "trigger_name"      varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group"     varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "instance_name"     varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "fired_time"        int8                                        NOT NULL,
    "sched_time"        int8                                        NOT NULL,
    "priority"          int4                                        NOT NULL,
    "state"             varchar(16) COLLATE "pg_catalog"."default"  NOT NULL,
    "job_name"          varchar(200) COLLATE "pg_catalog"."default",
    "job_group"         varchar(200) COLLATE "pg_catalog"."default",
    "is_nonconcurrent"  bool,
    "requests_recovery" bool
)
;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_job_details";
CREATE TABLE "qrtz_job_details"
(
    "sched_name"        varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "job_name"          varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "job_group"         varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "description"       varchar(250) COLLATE "pg_catalog"."default",
    "job_class_name"    varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
    "is_durable"        bool                                        NOT NULL,
    "is_nonconcurrent"  bool                                        NOT NULL,
    "is_update_data"    bool                                        NOT NULL,
    "requests_recovery" bool                                        NOT NULL,
    "job_data"          bytea
)
;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_job_details" ("sched_name", "job_name", "job_group", "description", "job_class_name", "is_durable",
                                "is_nonconcurrent", "is_update_data", "requests_recovery", "job_data")
VALUES ('schedulerName', 'userSessionTimeoutJob', 'DEFAULT', NULL,
        'cn.econets.blossom.framework.quartz.core.handler.JobHandlerInvoker', 'f', 't', 't', 'f',
         '\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\002t\\000\\006JOB_IDsr\\000\\016java.lang.Long;\\213\\344\\220\\314\\217#\\337\\002\\000\\001J\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\000\\000\\000\\000\\002t\\000\\020JOB_HANDLER_NAMEt\\000\\025userSessionTimeoutJobx\\000');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_locks";
CREATE TABLE "qrtz_locks"
(
    "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "lock_name"  varchar(40) COLLATE "pg_catalog"."default"  NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_locks" ("sched_name", "lock_name")
VALUES ('schedulerName', 'TRIGGER_ACCESS');
INSERT INTO "qrtz_locks" ("sched_name", "lock_name")
VALUES ('schedulerName', 'STATE_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_paused_trigger_grps";
CREATE TABLE "qrtz_paused_trigger_grps"
(
    "sched_name"    varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_scheduler_state";
CREATE TABLE "qrtz_scheduler_state"
(
    "sched_name"        varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "instance_name"     varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "last_checkin_time" int8                                        NOT NULL,
    "checkin_interval"  int8                                        NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_scheduler_state" ("sched_name", "instance_name", "last_checkin_time", "checkin_interval")
VALUES ('schedulerName', 'econets.local1651328569660', 1651328650075, 15000);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_simple_triggers";
CREATE TABLE "qrtz_simple_triggers"
(
    "sched_name"      varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_name"    varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group"   varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "repeat_count"    int8                                        NOT NULL,
    "repeat_interval" int8                                        NOT NULL,
    "times_triggered" int8                                        NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_simprop_triggers";
CREATE TABLE "qrtz_simprop_triggers"
(
    "sched_name"    varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_name"  varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "str_prop_1"    varchar(512) COLLATE "pg_catalog"."default",
    "str_prop_2"    varchar(512) COLLATE "pg_catalog"."default",
    "str_prop_3"    varchar(512) COLLATE "pg_catalog"."default",
    "int_prop_1"    int4,
    "int_prop_2"    int4,
    "long_prop_1"   int8,
    "long_prop_2"   int8,
    "dec_prop_1"    numeric(13, 4),
    "dec_prop_2"    numeric(13, 4),
    "bool_prop_1"   bool,
    "bool_prop_2"   bool
)
;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_triggers";
CREATE TABLE "qrtz_triggers"
(
    "sched_name"     varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_name"   varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "trigger_group"  varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "job_name"       varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "job_group"      varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "description"    varchar(250) COLLATE "pg_catalog"."default",
    "next_fire_time" int8,
    "prev_fire_time" int8,
    "priority"       int4,
    "trigger_state"  varchar(16) COLLATE "pg_catalog"."default"  NOT NULL,
    "trigger_type"   varchar(8) COLLATE "pg_catalog"."default"   NOT NULL,
    "start_time"     int8                                        NOT NULL,
    "end_time"       int8,
    "calendar_name"  varchar(200) COLLATE "pg_catalog"."default",
    "misfire_instr"  int2,
    "job_data"       bytea
)
;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group", "job_name", "job_group", "description",
                             "next_fire_time", "prev_fire_time", "priority", "trigger_state", "trigger_type",
                             "start_time", "end_time", "calendar_name", "misfire_instr", "job_data")
VALUES ('schedulerName', 'userSessionTimeoutJob', 'DEFAULT', 'userSessionTimeoutJob', 'DEFAULT', NULL, 1651328700000,
        1651328640000, 5, 'WAITING', 'CRON', 1651328526000, 0, NULL, 0,
         '\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\003t\\000\\021JOB_HANDLER_PARAMpt\\000\\022JOB_RETRY_INTERVALsr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\007\\320t\\000\\017JOB_RETRY_COUNTsq\\000~\\000\\011\\000\\000\\000\\003x\\000');
COMMIT;

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS "system_dept";
CREATE TABLE "system_dept"
(
    "id"             int8                                       NOT NULL,
    "name"           varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"      int8                                       NOT NULL,
    "sort"           int4                                       NOT NULL,
    "leader_user_id" int8,
    "phone"          varchar(11) COLLATE "pg_catalog"."default",
    "email"          varchar(50) COLLATE "pg_catalog"."default",
    "status"         int2                                       NOT NULL,
    "creator"        varchar(64) COLLATE "pg_catalog"."default",
    "create_time"    timestamp(6)                               NOT NULL,
    "updater"        varchar(64) COLLATE "pg_catalog"."default",
    "update_time"    timestamp(6)                               NOT NULL,
    "deleted"        int2                                       NOT NULL DEFAULT 0,
    "tenant_id"      int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_dept"."id" IS 'Departmentid';
COMMENT
ON COLUMN "system_dept"."name" IS 'Department Name';
COMMENT
ON COLUMN "system_dept"."parent_id" IS 'Parent Departmentid';
COMMENT
ON COLUMN "system_dept"."sort" IS 'Display order';
COMMENT
ON COLUMN "system_dept"."leader_user_id" IS 'Person in charge';
COMMENT
ON COLUMN "system_dept"."phone" IS 'Contact number';
COMMENT
ON COLUMN "system_dept"."email" IS 'Mailbox';
COMMENT
ON COLUMN "system_dept"."status" IS 'Department Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_dept"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_dept"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_dept"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_dept"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_dept"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_dept"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_dept" IS 'Department Table';

-- ----------------------------
-- Records of system_dept
-- ----------------------------
BEGIN;
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (100, 'Source code', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-14 01:04:05', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (101, 'Shenzhen Head Office', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1',
        '2022-02-22 19:47:48', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (102, 'Changsha Branch', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:40', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (103, 'R&D Department', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-14 01:04:14', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (104, 'Marketing Department', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:38', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (105, 'Testing Department', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:37', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (106, 'Finance Department', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103',
        '2022-01-15 21:32:22', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (107, 'Operation and Maintenance Department', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:33', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (108, 'Marketing Department', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1',
        '2022-02-16 08:35:45', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (109, 'Finance Department', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '',
        '2021-12-15 05:01:29', 0, 1);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (110, 'New Department', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', 0, 121);
INSERT INTO "system_dept" ("id", "name", "parent_id", "sort", "leader_user_id", "phone", "email", "status", "creator",
                           "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (111, 'Top Department', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', 0, 122);
COMMIT;

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "system_dict_data";
CREATE TABLE "system_dict_data"
(
    "id"          int8                                        NOT NULL,
    "sort"        int4                                        NOT NULL,
    "label"       varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "value"       varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "dict_type"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "status"      int2                                        NOT NULL,
    "color_type"  varchar(100) COLLATE "pg_catalog"."default",
    "css_class"   varchar(100) COLLATE "pg_catalog"."default",
    "remark"      varchar(500) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_dict_data"."id" IS 'Dictionary encoding';
COMMENT
ON COLUMN "system_dict_data"."sort" IS 'Dictionary sort';
COMMENT
ON COLUMN "system_dict_data"."label" IS 'Dictionary tags';
COMMENT
ON COLUMN "system_dict_data"."value" IS 'Dictionary key value';
COMMENT
ON COLUMN "system_dict_data"."dict_type" IS 'Dictionary type';
COMMENT
ON COLUMN "system_dict_data"."status" IS 'Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_dict_data"."color_type" IS 'Color type';
COMMENT
ON COLUMN "system_dict_data"."css_class" IS 'css Style';
COMMENT
ON COLUMN "system_dict_data"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_dict_data"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_dict_data"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_dict_data"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_dict_data"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_dict_data"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_dict_data" IS 'Dictionary data table';

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
BEGIN;
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1, 1, 'Male', '1', 'system_user_sex', 0, 'default', 'A', 'Gender: Male', 'admin', '2021-01-05 17:03:48', '1',
        '2022-03-29 00:14:39', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (2, 2, 'Female', '2', 'system_user_sex', 1, 'success', '', 'Gender Female', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 01:30:51', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (8, 1, 'Normal', '1', 'infra_job_status', 0, 'success', '', 'Normal state', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:33:38', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (9, 2, 'Pause', '2', 'infra_job_status', 0, 'danger', '', 'Disabled status', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 19:33:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (12, 1, 'Built-in system', '1', 'infra_config_type', 0, 'danger', '', 'Parameter type - Built-in system', 'admin',
        '2021-01-05 17:03:48', '1', '2022-02-16 19:06:02', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (13, 2, 'Custom', '2', 'infra_config_type', 0, 'primary', '', 'Parameter type - Custom', 'admin',
        '2021-01-05 17:03:48', '1', '2022-02-16 19:06:07', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (14, 1, 'Notice', '1', 'system_notice_type', 0, 'success', '', 'Notice', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:05:57', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (15, 2, 'Announcement', '2', 'system_notice_type', 0, 'info', '', 'Announcement', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:06:01', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (16, 0, 'Others', '0', 'system_operate_type', 0, 'default', '', 'Other operations', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:32:46', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (17, 1, 'Query', '1', 'system_operate_type', 0, 'info', '', 'Query operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:16', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (18, 2, 'Newly added', '2', 'system_operate_type', 0, 'primary', '', 'Add new operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:13', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (19, 3, 'Modify', '3', 'system_operate_type', 0, 'warning', '', 'Modify operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:22', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (20, 4, 'Delete', '4', 'system_operate_type', 0, 'danger', '', 'Delete operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:27', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (22, 5, 'Export', '5', 'system_operate_type', 0, 'default', '', 'Export operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:32', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (23, 6, 'Import', '6', 'system_operate_type', 0, 'default', '', 'Import operation', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 09:33:35', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (27, 1, 'Open', '0', 'common_status', 0, 'primary', '', 'Open status', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 08:00:39', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (28, 2, 'Close', '1', 'common_status', 0, 'info', '', 'Closed state', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 08:00:44', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (29, 1, 'Directory', '1', 'system_menu_type', 0, '', '', 'Directory', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (30, 2, 'Menu', '2', 'system_menu_type', 0, '', '', 'Menu', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:41', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (31, 3, 'Button', '3', 'system_menu_type', 0, '', '', 'Button', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:43:39', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (32, 1, 'Built-in', '1', 'system_role_type', 0, 'danger', '', 'Built-in roles', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:02:08', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (33, 2, 'Custom', '2', 'system_role_type', 0, 'primary', '', 'Custom role', 'admin', '2021-01-05 17:03:48', '1',
        '2022-02-16 13:02:12', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (34, 1, 'All data permissions', '1', 'system_data_scope', 0, '', '', 'All data permissions', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-01 16:47:17', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (35, 2, 'Specify department data permissions', '2', 'system_data_scope', 0, '', '', 'Specify department data permissions', 'admin',
        '2021-01-05 17:03:48', '', '2022-02-01 16:47:18', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (36, 3, 'Data permissions for this department', '3', 'system_data_scope', 0, '', '', 'Data permissions for this department', 'admin', '2021-01-05 17:03:48',
        '', '2022-02-01 16:47:16', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (37, 4, 'Data permissions for this department and below', '4', 'system_data_scope', 0, '', '', 'Data permissions for this department and below', 'admin',
        '2021-01-05 17:03:48', '', '2022-02-01 16:47:21', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (38, 5, 'Only personal data permissions', '5', 'system_data_scope', 0, '', '', 'Only personal data permissions', 'admin', '2021-01-05 17:03:48',
        '', '2022-02-01 16:47:23', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (39, 0, 'Success', '0', 'system_login_result', 0, 'success', '', 'Login result - Success', '', '2021-01-18 06:17:36', '1',
        '2022-02-16 13:23:49', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (40, 10, 'The account or password is incorrect', '10', 'system_login_result', 0, 'primary', '', 'Login result - The account or password is incorrect', '',
        '2021-01-18 06:17:54', '1', '2022-02-16 13:24:27', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (41, 20, 'User is disabled', '20', 'system_login_result', 0, 'warning', '', 'Login result - User is disabled', '',
        '2021-01-18 06:17:54', '1', '2022-02-16 13:23:57', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (42, 30, 'Verification code does not exist', '30', 'system_login_result', 0, 'info', '', 'Login result - Verification code does not exist', '',
        '2021-01-18 06:17:54', '1', '2022-02-16 13:24:07', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (43, 31, 'Incorrect verification code', '31', 'system_login_result', 0, 'info', '', 'Login result - Incorrect verification code', '',
        '2021-01-18 06:17:54', '1', '2022-02-16 13:24:11', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (44, 100, 'Unknown exception', '100', 'system_login_result', 0, 'danger', '', 'Login result - Unknown exception', '',
        '2021-01-18 06:17:54', '1', '2022-02-16 13:24:23', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (45, 1, 'Yes', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean Whether type - Yes', '',
        '2021-01-19 03:20:55', '1', '2022-03-15 23:01:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (46, 1, 'No', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean Whether type - No', '', '2021-01-19 03:20:55',
        '1', '2022-03-15 23:09:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (47, 1, 'Never time out', '1', 'infra_redis_timeout_type', 0, 'primary', '', 'Redis When timeout is not set', '',
        '2021-01-26 00:53:17', '1', '2022-02-16 19:03:35', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (48, 1, 'Dynamic timeout', '2', 'infra_redis_timeout_type', 0, 'info', '', 'Dynamically pass in the timeout in the program，Cannot be fixed', '',
        '2021-01-26 00:55:00', '1', '2022-02-16 19:03:41', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (49, 3, 'Fixed timeout', '3', 'infra_redis_timeout_type', 0, 'success', '', 'Redis Expiration time is set', '',
        '2021-01-26 00:55:26', '1', '2022-02-16 19:03:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (50, 1, 'Single table（Add, delete, modify, check）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '',
        '2022-03-10 16:33:15', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (51, 2, 'Tree table（Add, delete, modify, check）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '',
        '2022-03-10 16:33:19', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (53, 0, 'Initializing', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1',
        '2022-02-16 19:33:29', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (57, 0, 'Running', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1',
        '2022-02-16 19:07:48', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (58, 1, 'Success', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1',
        '2022-02-16 19:07:52', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (59, 2, 'Failed', '2', 'infra_job_log_status', 0, 'warning', '', 'Failed', '', '2021-02-08 10:07:38', '1',
        '2022-02-16 19:07:56', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (60, 1, 'Member', '1', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2022-02-16 10:22:19',
        0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (61, 2, 'Administrator', '2', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1',
        '2022-02-16 10:22:22', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (62, 0, 'Unprocessed', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19',
        '1', '2022-02-16 20:14:17', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (63, 1, 'Processed', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26',
        '1', '2022-02-16 20:14:08', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (64, 2, 'Ignored', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34',
        '1', '2022-02-16 20:14:14', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (66, 2, 'Alibaba Cloud', 'ALIYUN', 'system_sms_channel_code', 0, 'primary', '', NULL, '1', '2021-04-05 01:05:26', '1',
        '2022-02-16 10:09:52', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (67, 1, 'Verification code', '1', 'system_sms_template_type', 0, 'warning', '', NULL, '1', '2021-04-05 21:50:57', '1',
        '2022-02-16 12:48:30', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (68, 2, 'Notice', '2', 'system_sms_template_type', 0, 'primary', '', NULL, '1', '2021-04-05 21:51:08', '1',
        '2022-02-16 12:48:27', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (69, 0, 'Marketing', '3', 'system_sms_template_type', 0, 'danger', '', NULL, '1', '2021-04-05 21:51:15', '1',
        '2022-02-16 12:48:22', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (70, 0, 'Initialization', '0', 'system_sms_send_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:18:33', '1',
        '2022-02-16 10:26:07', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (71, 1, 'Sent successfully', '10', 'system_sms_send_status', 0, 'success', '', NULL, '1', '2021-04-11 20:18:43', '1',
        '2022-02-16 10:25:56', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (72, 2, 'Sending failed', '20', 'system_sms_send_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:18:49', '1',
        '2022-02-16 10:26:03', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (73, 3, 'Do not send', '30', 'system_sms_send_status', 0, 'info', '', NULL, '1', '2021-04-11 20:19:44', '1',
        '2022-02-16 10:26:10', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (74, 0, 'Waiting for results', '0', 'system_sms_receive_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:27:43', '1',
        '2022-02-16 10:28:24', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (75, 1, 'Received successfully', '10', 'system_sms_receive_status', 0, 'success', '', NULL, '1', '2021-04-11 20:29:25', '1',
        '2022-02-16 10:28:28', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (76, 2, 'Receiving failed', '20', 'system_sms_receive_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:29:31', '1',
        '2022-02-16 10:28:32', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (77, 0, 'Debug(DingTalk)', 'DEBUG_DING_TALK', 'system_sms_channel_code', 0, 'info', '', NULL, '1',
        '2021-04-13 00:20:37', '1', '2022-02-16 10:10:00', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (78, 1, 'Automatically generated', '1', 'system_error_code_type', 0, 'warning', '', NULL, '1', '2021-04-21 00:06:48', '1',
        '2022-02-16 13:57:20', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (79, 2, 'Manual Edit', '2', 'system_error_code_type', 0, 'primary', '', NULL, '1', '2021-04-21 00:07:14', '1',
        '2022-02-16 13:57:24', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (80, 100, 'Account login', '100', 'system_login_type', 0, 'primary', '', 'Account login', '1', '2021-10-06 00:52:02', '1',
        '2022-02-16 13:11:34', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (81, 101, 'Social login', '101', 'system_login_type', 0, 'info', '', 'Social login', '1', '2021-10-06 00:52:17', '1',
        '2022-02-16 13:11:40', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (83, 200, 'Actively log out', '200', 'system_login_type', 0, 'primary', '', 'Actively log out', '1', '2021-10-06 00:52:58', '1',
        '2022-02-16 13:11:49', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (85, 202, 'Force logout', '202', 'system_login_type', 0, 'danger', '', 'Force exit', '1', '2021-10-06 00:53:41', '1',
        '2022-02-16 13:11:57', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (86, 0, 'Sick leave', '1', 'bpm_oa_leave_type', 0, 'primary', '', NULL, '1', '2021-09-21 22:35:28', '1',
        '2022-02-16 10:00:41', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (87, 1, 'Personal leave', '2', 'bpm_oa_leave_type', 0, 'info', '', NULL, '1', '2021-09-21 22:36:11', '1',
        '2022-02-16 10:00:49', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (88, 2, 'Marriage leave', '3', 'bpm_oa_leave_type', 0, 'warning', '', NULL, '1', '2021-09-21 22:36:38', '1',
        '2022-02-16 10:00:53', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (98, 1, 'v2', 'v2', 'pay_channel_wechat_version', 0, '', '', 'v2Version', '1', '2021-11-08 17:00:58', '1',
        '2021-11-08 17:00:58', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (99, 2, 'v3', 'v3', 'pay_channel_wechat_version', 0, '', '', 'v3Version', '1', '2021-11-08 17:01:07', '1',
        '2021-11-08 17:01:07', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (108, 1, 'RSA2', 'RSA2', 'pay_channel_alipay_sign_type', 0, '', '', 'RSA2', '1', '2021-11-18 15:39:29', '1',
        '2021-11-18 15:39:29', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (109, 1, 'Public key mode', '1', 'pay_channel_alipay_mode', 0, '', '', 'Public key mode：privateKey + alipayPublicKey', '1',
        '2021-11-18 15:45:23', '1', '2021-11-18 15:45:23', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (110, 2, 'Certificate Mode', '2', 'pay_channel_alipay_mode', 0, '', '',
        'Certificate Mode：appCertContent + alipayPublicCertContent + rootCertContent', '1', '2021-11-18 15:45:40', '1',
        '2021-11-18 15:45:40', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (111, 1, 'Online', 'https://openapi.alipay.com/gateway.do', 'pay_channel_alipay_server_type', 0, '', '',
        'Gateway address - Online', '1', '2021-11-18 16:59:32', '1', '2021-11-21 17:37:29', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (112, 2, 'Sandbox', 'https://openapi.alipaydev.com/gateway.do', 'pay_channel_alipay_server_type', 0, '', '',
        'Gateway address - Sandbox', '1', '2021-11-18 16:59:48', '1', '2021-11-21 17:37:39', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (113, 1, 'WeChat JSAPI Payment', 'wx_pub', 'pay_channel_code_type', 0, '', '', 'WeChat JSAPI（Public Account） Payment', '1',
        '2021-12-03 10:40:24', '1', '2021-12-04 16:41:00', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (114, 2, 'WeChat Mini Program Payment', 'wx_lite', 'pay_channel_code_type', 0, '', '', 'WeChat Mini Program Payment', '1',
        '2021-12-03 10:41:06', '1', '2021-12-03 10:41:06', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (115, 3, 'WeChat App Payment', 'wx_app', 'pay_channel_code_type', 0, '', '', 'WeChat App Payment', '1',
        '2021-12-03 10:41:20', '1', '2021-12-03 10:41:20', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (116, 4, 'Alipay PC Website payment', 'alipay_pc', 'pay_channel_code_type', 0, '', '', 'Alipay PC Website payment', '1',
        '2021-12-03 10:42:09', '1', '2021-12-03 10:42:09', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (117, 5, 'Alipay Wap Website payment', 'alipay_wap', 'pay_channel_code_type', 0, '', '', 'Alipay Wap Website payment', '1',
        '2021-12-03 10:42:26', '1', '2021-12-03 10:42:26', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (118, 6, 'AlipayApp Payment', 'alipay_app', 'pay_channel_code_type', 0, '', '', 'AlipayApp Payment', '1',
        '2021-12-03 10:42:55', '1', '2021-12-03 10:42:55', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (119, 7, 'Alipay scan code payment', 'alipay_qr', 'pay_channel_code_type', 0, '', '', 'Alipay scan code payment', '1',
        '2021-12-03 10:43:10', '1', '2021-12-03 10:43:10', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (120, 1, 'Notification successful', '10', 'pay_order_notify_status', 0, 'success', '', 'Notification successful', '1', '2021-12-03 11:02:41',
        '1', '2022-02-16 13:59:13', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (121, 2, 'Notification failed', '20', 'pay_order_notify_status', 0, 'danger', '', 'Notification failed', '1', '2021-12-03 11:02:59',
        '1', '2022-02-16 13:59:17', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (122, 3, 'Not notified', '0', 'pay_order_notify_status', 0, 'info', '', 'Not notified', '1', '2021-12-03 11:03:10', '1',
        '2022-02-16 13:59:23', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (123, 1, 'Payment successful', '10', 'pay_order_status', 0, 'success', '', 'Payment successful', '1', '2021-12-03 11:18:29', '1',
        '2022-02-16 15:24:25', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (124, 2, 'Payment closed', '20', 'pay_order_status', 0, 'danger', '', 'Payment closed', '1', '2021-12-03 11:18:42', '1',
        '2022-02-16 15:24:31', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (125, 3, 'Not paid', '0', 'pay_order_status', 0, 'info', '', 'Not paid', '1', '2021-12-03 11:18:18', '1',
        '2022-02-16 15:24:35', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (126, 1, 'No refund', '0', 'pay_order_refund_status', 0, '', '', 'No refund', '1', '2021-12-03 11:30:35', '1',
        '2021-12-03 11:34:05', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (127, 2, 'Partial Refund', '10', 'pay_order_refund_status', 0, '', '', 'Partial Refund', '1', '2021-12-03 11:30:44', '1',
        '2021-12-03 11:34:10', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (128, 3, 'Full refund', '20', 'pay_order_refund_status', 0, '', '', 'Full refund', '1', '2021-12-03 11:30:52', '1',
        '2021-12-03 11:34:14', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1117, 1, 'Refund order generation', '0', 'pay_refund_order_status', 0, 'primary', '', 'Refund order generation', '1',
        '2021-12-10 16:44:44', '1', '2022-02-16 14:05:24', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1118, 2, 'Refund successful', '1', 'pay_refund_order_status', 0, 'success', '', 'Refund successful', '1', '2021-12-10 16:44:59',
        '1', '2022-02-16 14:05:28', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1119, 3, 'Refund failed', '2', 'pay_refund_order_status', 0, 'danger', '', 'Refund failed', '1', '2021-12-10 16:45:10',
        '1', '2022-02-16 14:05:34', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1124, 8, 'Refund closed', '99', 'pay_refund_order_status', 0, 'info', '', 'Refund closed', '1', '2021-12-10 16:46:26',
        '1', '2022-02-16 14:05:40', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1125, 0, 'Default', '1', 'bpm_model_category', 0, 'primary', '', 'Process Classification - Default', '1', '2022-01-02 08:41:11',
        '1', '2022-02-16 20:01:42', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1126, 0, 'OA', '2', 'bpm_model_category', 0, 'success', '', 'Process Classification - OA', '1', '2022-01-02 08:41:22', '1',
        '2022-02-16 20:01:50', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1127, 0, 'In progress', '1', 'bpm_process_instance_status', 0, 'primary', '', 'The status of the process instance - In progress', '1',
        '2022-01-07 23:47:22', '1', '2022-02-16 20:07:49', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1128, 2, 'Completed', '2', 'bpm_process_instance_status', 0, 'success', '', 'The status of the process instance - Completed', '1',
        '2022-01-07 23:47:49', '1', '2022-02-16 20:07:54', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1129, 1, 'Processing', '1', 'bpm_process_instance_result', 0, 'primary', '', 'Results of process instance - Processing', '1',
        '2022-01-07 23:48:32', '1', '2022-02-16 09:53:26', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1130, 2, 'Passed', '2', 'bpm_process_instance_result', 0, 'success', '', 'Results of process instance - Passed', '1',
        '2022-01-07 23:48:45', '1', '2022-02-16 09:53:31', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1131, 3, 'Not passed', '3', 'bpm_process_instance_result', 0, 'danger', '', 'Results of process instance - Not passed', '1',
        '2022-01-07 23:48:55', '1', '2022-02-16 09:53:38', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1132, 4, 'Cancelled', '4', 'bpm_process_instance_result', 0, 'info', '', 'Results of process instance - Cancel', '1',
        '2022-01-07 23:49:06', '1', '2022-02-16 09:53:42', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1133, 10, 'Process Form', '10', 'bpm_model_form_type', 0, '', '', 'Process form type - Process Form', '103',
        '2022-01-11 23:51:30', '103', '2022-01-11 23:51:30', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1134, 20, 'Business Form', '20', 'bpm_model_form_type', 0, '', '', 'Process form type - Business Form', '103',
        '2022-01-11 23:51:47', '103', '2022-01-11 23:51:47', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1135, 10, 'Role', '10', 'bpm_task_assign_rule_type', 0, 'info', '', 'Task assignment rule type - Role', '103',
        '2022-01-12 23:21:22', '1', '2022-02-16 20:06:14', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1136, 20, 'Members of the department', '20', 'bpm_task_assign_rule_type', 0, 'primary', '', 'Task assignment rule type - Members of the department',
        '103', '2022-01-12 23:21:47', '1', '2022-02-16 20:05:28', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1137, 21, 'Head of department', '21', 'bpm_task_assign_rule_type', 0, 'primary', '',
        'Task assignment rule type - Head of department', '103', '2022-01-12 23:33:36', '1', '2022-02-16 20:05:31', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1138, 30, 'User', '30', 'bpm_task_assign_rule_type', 0, 'info', '', 'Task assignment rule type - User', '103',
        '2022-01-12 23:34:02', '1', '2022-02-16 20:05:50', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1139, 40, 'User Group', '40', 'bpm_task_assign_rule_type', 0, 'warning', '', 'Task assignment rule type - User Group', '103',
        '2022-01-12 23:34:21', '1', '2022-02-16 20:05:57', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1140, 50, 'Custom script', '50', 'bpm_task_assign_rule_type', 0, 'danger', '', 'Task assignment rule type - Custom script',
        '103', '2022-01-12 23:34:43', '1', '2022-02-16 20:06:01', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1141, 22, 'Position', '22', 'bpm_task_assign_rule_type', 0, 'success', '', 'Task assignment rule type - Position', '103',
        '2022-01-14 18:41:55', '1', '2022-02-16 20:05:39', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1142, 10, 'Process initiator', '10', 'bpm_task_assign_script', 0, '', '', 'Task assignment custom script - Process initiator', '103',
        '2022-01-15 00:10:57', '103', '2022-01-15 21:24:10', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1143, 20, 'First-level leader of the process initiator', '20', 'bpm_task_assign_script', 0, '', '',
        'Task assignment custom script - First-level leader of the process initiator', '103', '2022-01-15 21:24:31', '103', '2022-01-15 21:24:31', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1144, 21, 'Second-level leader of the process initiator', '21', 'bpm_task_assign_script', 0, '', '',
        'Task assignment custom script - Second-level leader of the process initiator', '103', '2022-01-15 21:24:46', '103', '2022-01-15 21:24:57', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1145, 1, 'Management Backend', '1', 'infra_codegen_scene', 0, '', '', 'Code-generated scene enumeration - Management Backend', '1',
        '2022-02-02 13:15:06', '1', '2022-03-10 16:32:59', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1146, 2, 'User APP', '2', 'infra_codegen_scene', 0, '', '', 'Code-generated scene enumeration - User APP', '1',
        '2022-02-02 13:15:19', '1', '2022-03-10 16:33:03', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1147, 0, 'No refund', '0', 'pay_refund_order_type', 0, 'info', '', 'Refund type - No refund', '1', '2022-02-16 14:09:01',
        '1', '2022-02-16 14:09:01', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1148, 10, 'Partial Refund', '10', 'pay_refund_order_type', 0, 'success', '', 'Refund type - Partial Refund', '1',
        '2022-02-16 14:09:25', '1', '2022-02-16 14:11:38', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1149, 20, 'Full refund', '20', 'pay_refund_order_type', 0, 'warning', '', 'Refund type - Full refund', '1',
        '2022-02-16 14:11:33', '1', '2022-02-16 14:11:33', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1150, 1, 'Database', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1',
        '2022-03-15 00:25:28', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1151, 10, 'Local Disk', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1',
        '2022-03-15 00:25:56', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1152, 11, 'FTP Server', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1',
        '2022-03-15 00:26:10', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1153, 12, 'SFTP Server', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1',
        '2022-03-15 00:26:22', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1154, 20, 'S3 Object Storage', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1',
        '2022-03-15 00:26:45', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1155, 103, 'SMS login', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1',
        '2022-05-09 23:58:09', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1156, 1, 'password', 'password', 'system_oauth2_grant_type', 0, 'default', '', 'Password Mode', '1',
        '2022-05-12 00:22:05', '1', '2022-05-11 16:26:01', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1157, 2, 'authorization_code', 'authorization_code', 'system_oauth2_grant_type', 0, 'primary', '', 'Authorization code mode',
        '1', '2022-05-12 00:22:59', '1', '2022-05-11 16:26:02', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1158, 3, 'implicit', 'implicit', 'system_oauth2_grant_type', 0, 'success', '', 'Simplified mode', '1',
        '2022-05-12 00:23:40', '1', '2022-05-11 16:26:05', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1159, 4, 'client_credentials', 'client_credentials', 'system_oauth2_grant_type', 0, 'default', '', 'Client Mode',
        '1', '2022-05-12 00:23:51', '1', '2022-05-11 16:26:08', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1160, 5, 'refresh_token', 'refresh_token', 'system_oauth2_grant_type', 0, 'info', '', 'Refresh Mode', '1',
        '2022-05-12 00:24:02', '1', '2022-05-11 16:26:11', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1161, 4, 'Vue 3 Vben', '30', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-06-14 15:24:37.447', '1',
        '2023-06-14 15:24:37.447', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1162, 3, 'Vue 3 Schema', '21', 'infra_codegen_front_type', 0, '', '', 'Vue 3 Element Plus Schema', '1',
        '2023-06-14 15:24:18.714', '1', '2023-06-14 15:36:40.317', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1163, 2, 'Vue 3', '20', 'infra_codegen_front_type', 0, '', '', 'Vue 3 Element Plus', '1',
        '2023-06-14 15:24:05.654', '1', '2023-06-14 15:24:05.654', 0);
INSERT INTO "system_dict_data" ("id", "sort", "label", "value", "dict_type", "status", "color_type", "css_class",
                                "remark", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (1164, 1, 'Vue 2', '10', 'infra_codegen_front_type', 0, '', '', 'Vue 2', '1', '2023-06-14 15:23:12.211', '1',
        '2023-06-14 15:23:57.816', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "system_dict_type";
CREATE TABLE "system_dict_type"
(
    "id"           int8                                        NOT NULL,
    "name"         varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "type"         varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "status"       int2                                        NOT NULL,
    "remark"       varchar(500) COLLATE "pg_catalog"."default",
    "creator"      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"  timestamp(6)                                NOT NULL,
    "updater"      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)                                NOT NULL,
    "deleted_time" timestamp(6),
    "deleted"      int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_dict_type"."id" IS 'Dictionary primary key';
COMMENT
ON COLUMN "system_dict_type"."name" IS 'Dictionary name';
COMMENT
ON COLUMN "system_dict_type"."type" IS 'Dictionary type';
COMMENT
ON COLUMN "system_dict_type"."status" IS 'Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_dict_type"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_dict_type"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_dict_type"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_dict_type"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_dict_type"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_dict_type"."deleted_time" IS 'Delete time';
COMMENT
ON COLUMN "system_dict_type"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_dict_type" IS 'Dictionary type table';

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
BEGIN;
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (1, 'User gender', 'system_user_sex', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:30:31', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (6, 'Parameter type', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (7, 'Notification type', 'system_notice_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:35:26', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (9, 'Operation type', 'system_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:21', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (10, 'System Status', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (11, 'Boolean Whether type', 'infra_boolean_string', 0, 'boolean Transfer whether', '', '2021-01-19 03:20:08', '',
        '2022-02-01 16:37:10', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (104, 'Login result', 'system_login_result', 0, 'Login result', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (105, 'Redis Timeout type', 'infra_redis_timeout_type', 0, 'RedisKeyDefine.TimeoutTypeEnum', '',
        '2021-01-26 00:52:50', '', '2022-02-01 16:50:29', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (106, 'Code generation template type', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '',
        '2022-03-10 16:33:42', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (107, 'Scheduled task status', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (108, 'Scheduled task log status', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43',
        0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (109, 'User Type', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (110, 'API Processing status of abnormal data', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '',
        '2022-02-01 16:50:53', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (111, 'SMS channel code', 'system_sms_channel_code', 0, NULL, '1', '2021-04-05 01:04:50', '1', '2022-02-16 02:09:08',
        0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (112, 'SMS template type', 'system_sms_template_type', 0, NULL, '1', '2021-04-05 21:50:43', '1',
        '2022-02-01 16:35:06', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (113, 'SMS sending status', 'system_sms_send_status', 0, NULL, '1', '2021-04-11 20:18:03', '1', '2022-02-01 16:35:09',
        0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (114, 'SMS receiving status', 'system_sms_receive_status', 0, NULL, '1', '2021-04-11 20:27:14', '1',
        '2022-02-01 16:35:14', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (115, 'Error code type', 'system_error_code_type', 0, NULL, '1', '2021-04-21 00:06:30', '1', '2022-02-01 16:36:49',
        0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (116, 'Login log type', 'system_login_type', 0, 'Login log type', '1', '2021-10-06 00:50:46', '1',
        '2022-02-01 16:35:56', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (117, 'OA Leave Type', 'bpm_oa_leave_type', 0, NULL, '1', '2021-09-21 22:34:33', '1', '2022-01-22 10:41:37', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (122, 'WeChat version of payment channel', 'pay_channel_wechat_version', 0, 'WeChat version of payment channel', '1', '2021-11-08 17:00:26', '1',
        '2021-11-08 17:00:26', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (127, 'Payment channel Alipay algorithm type', 'pay_channel_alipay_sign_type', 0, 'Payment channel Alipay algorithm type', '1',
        '2021-11-18 15:39:09', '1', '2021-11-18 15:39:09', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (128, 'Payment channel Alipay public key type', 'pay_channel_alipay_mode', 0, 'Payment channel Alipay public key type', '1',
        '2021-11-18 15:44:28', '1', '2021-11-18 15:44:28', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (129, 'Alipay gateway address', 'pay_channel_alipay_server_type', 0, 'Alipay gateway address', '1', '2021-11-18 16:58:55', '1',
        '2021-11-18 17:01:34', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (130, 'Payment channel encoding type', 'pay_channel_code_type', 0, 'Payment channel code', '1', '2021-12-03 10:35:08', '1',
        '2021-12-03 10:35:08', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (131, 'Payment order callback status', 'pay_order_notify_status', 0, 'Payment order callback status', '1', '2021-12-03 10:53:29', '1',
        '2021-12-03 10:53:29', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (132, 'Payment order status', 'pay_order_status', 0, 'Payment order status', '1', '2021-12-03 11:17:50', '1',
        '2021-12-03 11:17:50', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (133, 'Payment order refund status', 'pay_order_refund_status', 0, 'Payment order refund status', '1', '2021-12-03 11:27:31', '1',
        '2021-12-03 11:27:31', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (134, 'Refund order status', 'pay_refund_order_status', 0, 'Refund order status', '1', '2021-12-10 16:42:50', '1',
        '2021-12-10 16:42:50', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (135, 'Refund order category', 'pay_refund_order_type', 0, 'Refund order category', '1', '2021-12-10 17:14:53', '1',
        '2021-12-10 17:14:53', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (138, 'Process Classification', 'bpm_model_category', 0, 'Process Classification', '1', '2022-01-02 08:40:45', '1', '2022-01-02 08:40:45',
        0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (139, 'The status of the process instance', 'bpm_process_instance_status', 0, 'The status of the process instance', '1', '2022-01-07 23:46:42', '1',
        '2022-01-07 23:46:42', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (140, 'Results of process instance', 'bpm_process_instance_result', 0, 'Results of process instance', '1', '2022-01-07 23:48:10', '1',
        '2022-01-07 23:48:10', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (141, 'Process form type', 'bpm_model_form_type', 0, 'Process form type', '103', '2022-01-11 23:50:45', '103',
        '2022-01-11 23:50:45', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (142, 'Task assignment rule type', 'bpm_task_assign_rule_type', 0, 'Task assignment rule type', '103', '2022-01-12 23:21:04',
        '103', '2022-01-12 15:46:10', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (143, 'Task assignment custom script', 'bpm_task_assign_script', 0, 'Task assignment custom script', '103', '2022-01-15 00:10:35',
        '103', '2022-01-15 00:10:35', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (144, 'Code-generated scene enumeration', 'infra_codegen_scene', 0, 'Code-generated scene enumeration', '1', '2022-02-02 13:14:45', '1',
        '2022-03-10 16:33:46', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (145, 'Role type', 'system_role_type', 0, 'Role type', '1', '2022-02-16 13:01:46', '1', '2022-02-16 13:01:46', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (146, 'File Storage', 'infra_file_storage', 0, 'File Storage', '1', '2022-03-15 00:24:38', '1',
        '2022-03-15 00:24:38', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (147, 'OAuth 2.0 Authorization type', 'system_oauth2_grant_type', 0, 'OAuth 2.0 Authorization type（Mode）', '1',
        '2022-05-12 00:20:52', '1', '2022-05-11 16:25:49', 0);
INSERT INTO "system_dict_type" ("id", "name", "type", "status", "remark", "creator", "create_time", "updater",
                                "update_time", "deleted")
VALUES (148, 'Generate front-end code type', 'infra_codegen_front_type', 0, 'Generate front-end code type', '1', '2023-6-14 16:07:35', '1',
        '2023-6-14 16:07:39', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_error_code
-- ----------------------------
DROP TABLE IF EXISTS "system_error_code";
CREATE TABLE "system_error_code"
(
    "id"               int8                                        NOT NULL,
    "type"             int2                                        NOT NULL,
    "application_name" varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "code"             int4                                        NOT NULL,
    "message"          varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
    "memo"             varchar(512) COLLATE "pg_catalog"."default",
    "creator"          varchar(64) COLLATE "pg_catalog"."default",
    "create_time"      timestamp(6)                                NOT NULL,
    "updater"          varchar(64) COLLATE "pg_catalog"."default",
    "update_time"      timestamp(6)                                NOT NULL,
    "deleted"          int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_error_code"."id" IS 'Error code number';
COMMENT
ON COLUMN "system_error_code"."type" IS 'Error code type';
COMMENT
ON COLUMN "system_error_code"."application_name" IS 'Application name';
COMMENT
ON COLUMN "system_error_code"."code" IS 'Error code';
COMMENT
ON COLUMN "system_error_code"."message" IS 'Error code error prompt';
COMMENT
ON COLUMN "system_error_code"."memo" IS 'Remarks';
COMMENT
ON COLUMN "system_error_code"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_error_code"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_error_code"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_error_code"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_error_code"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_error_code" IS 'Error code table';

-- ----------------------------
-- Records of system_error_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS "system_login_log";
CREATE TABLE "system_login_log"
(
    "id"          int8                                        NOT NULL,
    "log_type"    int8                                        NOT NULL,
    "trace_id"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_id"     int8                                        NOT NULL DEFAULT 0,
    "user_type"   int2                                        NOT NULL,
    "username"    varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "result"      int2                                        NOT NULL,
    "user_ip"     varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_agent"  varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0,
    "tenant_id"   int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_login_log"."id" IS 'VisitID';
COMMENT
ON COLUMN "system_login_log"."log_type" IS 'Log type';
COMMENT
ON COLUMN "system_login_log"."trace_id" IS 'Link tracking number';
COMMENT
ON COLUMN "system_login_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_login_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_login_log"."username" IS 'User Account';
COMMENT
ON COLUMN "system_login_log"."result" IS 'Login result';
COMMENT
ON COLUMN "system_login_log"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "system_login_log"."user_agent" IS 'Browser UA';
COMMENT
ON COLUMN "system_login_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_login_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_login_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_login_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_login_log"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_login_log"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_login_log" IS 'System access records';

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS "system_menu";
CREATE TABLE "system_menu"
(
    "id"             int8                                        NOT NULL DEFAULT nextval('system_menu_seq'::regclass),
    "name"           varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "permission"     varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "type"           int2                                        NOT NULL,
    "sort"           int4                                        NOT NULL,
    "parent_id"      int8                                        NOT NULL,
    "path"           varchar(200) COLLATE "pg_catalog"."default",
    "icon"           varchar(100) COLLATE "pg_catalog"."default",
    "component"      varchar(255) COLLATE "pg_catalog"."default",
    "status"         int2                                        NOT NULL,
    "visible"        bool                                        NOT NULL DEFAULT true,
    "keep_alive"     bool                                        NOT NULL DEFAULT false,
    "creator"        varchar(64) COLLATE "pg_catalog"."default",
    "create_time"    timestamp(6)                                NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    "updater"        varchar(64) COLLATE "pg_catalog"."default",
    "update_time"    timestamp(6)                                NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    "deleted"        int2                                        NOT NULL DEFAULT 0,
    "component_name" varchar(255) COLLATE "pg_catalog"."default",
    "always_show"    bool                                        NOT NULL DEFAULT false
)
;
COMMENT
ON COLUMN "system_menu"."id" IS 'MenuID';
COMMENT
ON COLUMN "system_menu"."name" IS 'Menu name';
COMMENT
ON COLUMN "system_menu"."permission" IS 'Permission flag';
COMMENT
ON COLUMN "system_menu"."type" IS 'Menu Type';
COMMENT
ON COLUMN "system_menu"."sort" IS 'Display order';
COMMENT
ON COLUMN "system_menu"."parent_id" IS 'Parent MenuID';
COMMENT
ON COLUMN "system_menu"."path" IS 'Routing address';
COMMENT
ON COLUMN "system_menu"."icon" IS 'Menu icon';
COMMENT
ON COLUMN "system_menu"."component" IS 'Component Path';
COMMENT
ON COLUMN "system_menu"."status" IS 'Menu Status';
COMMENT
ON COLUMN "system_menu"."visible" IS 'Is it visible?';
COMMENT
ON COLUMN "system_menu"."keep_alive" IS 'Whether to cache';
COMMENT
ON COLUMN "system_menu"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_menu"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_menu"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_menu"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_menu"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_menu"."component_name" IS 'Component name';
COMMENT
ON COLUMN "system_menu"."always_show" IS 'Whether to always display';
COMMENT
ON TABLE "system_menu" IS 'Menu Permission Table';

ALTER TABLE system_menu
    ALTER COLUMN permission SET DEFAULT '';
-- ----------------------------
-- Records of system_menu
-- ----------------------------
BEGIN;
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1, 'System Management', '', 1, 10, 0, '/system', 'system', NULL, 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-05-13 01:02:57.073', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (2, 'Infrastructure', '', 1, 20, 0, '/infra', 'monitor', NULL, 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (5, 'OA Example', '', 1, 40, 1185, 'oa', 'people', NULL, 0, 't', 't', 'admin', '2021-09-20 16:26:19', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (100, 'User Management', 'system:user:list', 2, 1, 1, 'user', 'user', 'system/user/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', 0, 'User', '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (101, 'Role Management', '', 2, 2, 1, 'role', 'peoples', 'system/role/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (102, 'Menu Management', '', 2, 3, 1, 'menu', 'tree-table', 'system/menu/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (103, 'Department Management', '', 2, 4, 1, 'dept', 'tree', 'system/dept/index', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (104, 'Position Management', '', 2, 5, 1, 'post', 'post', 'system/post/index', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (105, 'Dictionary Management', '', 2, 6, 1, 'dict', 'dict', 'system/dict/index', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (106, 'Configuration Management', '', 2, 6, 2, 'config', 'edit', 'infra/config/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (107, 'Notices and Announcements', '', 2, 8, 1, 'notice', 'message', 'system/notice/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (108, 'Audit log', '', 1, 9, 1, 'log', 'log', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (109, 'Token Management', '', 2, 2, 1261, 'token', 'online', 'system/oauth2/token/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-11 23:31:42', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (110, 'Scheduled tasks', '', 2, 12, 2, 'job', 'job', 'infra/job/index', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (111, 'MySQL Monitoring', '', 2, 9, 2, 'druid', 'druid', 'infra/druid/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (112, 'Java Monitoring', '', 2, 11, 2, 'admin-server', 'server', 'infra/server/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (113, 'Redis Monitoring', '', 2, 10, 2, 'redis', 'redis', 'infra/redis/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (114, 'Form construction', 'infra:build:list', 2, 2, 2, 'build', 'build', 'infra/build/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (115, 'Code Generation', 'infra:codegen:query', 2, 1, 2, 'codegen', 'code', 'infra/codegen/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (116, 'System interface', 'infra:swagger:list', 2, 3, 2, 'swagger', 'swagger', 'infra/swagger/index', 0, 't', 't',
        'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (500, 'Operation log', '', 2, 1, 108, 'operate-log', 'form', 'system/operatelog/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (501, 'Login log', '', 2, 2, 108, 'login-log', 'logininfor', 'system/loginlog/index', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1001, 'User Query', 'system:user:query', 3, 1, 100, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1002, 'New User', 'system:user:create', 3, 2, 100, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1003, 'User modification', 'system:user:update', 3, 3, 100, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1004, 'User deletion', 'system:user:delete', 3, 4, 100, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1005, 'User export', 'system:user:export', 3, 5, 100, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1006, 'User Import', 'system:user:import', 3, 6, 100, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1007, 'Reset password', 'system:user:update-password', 3, 7, 100, '', '', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1008, 'Role Query', 'system:role:query', 3, 1, 101, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1009, 'Add new characters', 'system:role:create', 3, 2, 101, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1010, 'Role modification', 'system:role:update', 3, 3, 101, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1011, 'Role deletion', 'system:role:delete', 3, 4, 101, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1012, 'Role export', 'system:role:export', 3, 5, 101, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1013, 'Menu Query', 'system:menu:query', 3, 1, 102, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1014, 'Menu Addition', 'system:menu:create', 3, 2, 102, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1015, 'Menu modification', 'system:menu:update', 3, 3, 102, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1016, 'Menu Delete', 'system:menu:delete', 3, 4, 102, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1017, 'Department Query', 'system:dept:query', 3, 1, 103, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1018, 'Newly added department', 'system:dept:create', 3, 2, 103, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1019, 'Department modification', 'system:dept:update', 3, 3, 103, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1020, 'Department Delete', 'system:dept:delete', 3, 4, 103, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1021, 'Job Query', 'system:post:query', 3, 1, 104, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1022, 'Newly added positions', 'system:post:create', 3, 2, 104, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1023, 'Position modification', 'system:post:update', 3, 3, 104, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1024, 'Position deletion', 'system:post:delete', 3, 4, 104, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1025, 'Position Export', 'system:post:export', 3, 5, 104, '', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1026, 'Dictionary search', 'system:dict:query', 3, 1, 105, '#', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1027, 'Dictionary addition', 'system:dict:create', 3, 2, 105, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1028, 'Dictionary modification', 'system:dict:update', 3, 3, 105, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1029, 'Dictionary deletion', 'system:dict:delete', 3, 4, 105, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1030, 'Dictionary export', 'system:dict:export', 3, 5, 105, '#', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1031, 'Configuration query', 'infra:config:query', 3, 1, 106, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1032, 'Configuration added', 'infra:config:create', 3, 2, 106, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1033, 'Configuration modification', 'infra:config:update', 3, 3, 106, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1034, 'Configuration deletion', 'infra:config:delete', 3, 4, 106, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1035, 'Configuration export', 'infra:config:export', 3, 5, 106, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1036, 'Announcement Query', 'system:notice:query', 3, 1, 107, '#', '#', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1037, 'Announcement added', 'system:notice:create', 3, 2, 107, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1038, 'Announcement modification', 'system:notice:update', 3, 3, 107, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1039, 'Announcement deleted', 'system:notice:delete', 3, 4, 107, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1040, 'Operation query', 'system:operate-log:query', 3, 1, 500, '', '', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1042, 'Log Export', 'system:operate-log:export', 3, 2, 500, '', '', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1043, 'Login query', 'system:login-log:query', 3, 1, 501, '#', '#', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1045, 'Log Export', 'system:login-log:export', 3, 3, 501, '#', '#', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1046, 'Token List', 'system:oauth2-token:page', 3, 1, 109, '', '', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-09 23:54:42', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1048, 'Token deletion', 'system:oauth2-token:delete', 3, 2, 109, '', '', '', 0, 't', 't', 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-09 23:54:53', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1050, 'Newly added tasks', 'infra:job:create', 3, 2, 110, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1051, 'Task modification', 'infra:job:update', 3, 3, 110, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1052, 'Task deletion', 'infra:job:delete', 3, 4, 110, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1053, 'Status modification', 'infra:job:update', 3, 5, 110, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1054, 'Task Export', 'infra:job:export', 3, 7, 110, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1056, 'Generate Modifications', 'infra:codegen:update', 3, 2, 115, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1057, 'Generate Delete', 'infra:codegen:delete', 3, 3, 115, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1058, 'Import code', 'infra:codegen:create', 3, 2, 115, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1059, 'Preview code', 'infra:codegen:preview', 3, 4, 115, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1060, 'Generate code', 'infra:codegen:download', 3, 5, 115, '', '', '', 0, 't', 't', 'admin', '2021-01-05 17:03:48',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1063, 'Set role menu permissions', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', 0, 't', 't', '',
        '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1064, 'Set role data permissions', 'system:permission:assign-role-data-scope', 3, 7, 101, '', '', '', 0, 't', 't', '',
        '2021-01-06 17:56:31', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1065, 'Set user role', 'system:permission:assign-user-role', 3, 8, 101, '', '', '', 0, 't', 't', '',
        '2021-01-07 10:23:28', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1066, 'Get Redis Monitoring information', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', 0, 't', 't', '',
        '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1067, 'Get Redis Key List', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', 0, 't', 't', '',
        '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1070, 'Code generation example', 'infra:test-demo:query', 2, 1, 2, 'test-demo', 'validCode', 'infra/testDemo/index', 0,
        't', 't', '', '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1071, 'Test sample table creation', 'infra:test-demo:create', 3, 1, 1070, '', '', '', 0, 't', 't', '',
        '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1072, 'Test sample table update', 'infra:test-demo:update', 3, 2, 1070, '', '', '', 0, 't', 't', '',
        '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1073, 'Test sample table deletion', 'infra:test-demo:delete', 3, 3, 1070, '', '', '', 0, 't', 't', '',
        '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1074, 'Export test sample table', 'infra:test-demo:export', 3, 4, 1070, '', '', '', 0, 't', 't', '',
        '2021-02-06 12:42:49', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1075, 'Task Trigger', 'infra:job:trigger', 3, 8, 110, '', '', '', 0, 't', 't', '', '2021-02-07 13:03:10', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1076, 'Database Document', '', 2, 4, 2, 'db-doc', 'table', 'infra/dbDoc/index', 0, 't', 't', '', '2021-02-08 01:41:47',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1077, 'Monitoring platform', '', 2, 13, 2, 'skywalking', 'eye-open', 'infra/skywalking/index', 0, 't', 't', '',
        '2021-02-08 20:41:31', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1078, 'Access log', '', 2, 1, 1083, 'api-access-log', 'log', 'infra/apiAccessLog/index', 0, 't', 't', '',
        '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1082, 'Log Export', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', 0, 't', 't', '', '2021-02-26 01:32:59',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1083, 'API Log', '', 2, 8, 2, 'log', 'log', NULL, 0, 't', 't', '', '2021-02-26 02:18:24', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1084, 'Error log', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'log', 'infra/apiErrorLog/index', 0,
        't', 't', '', '2021-02-26 07:53:20', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1085, 'Log processing', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', 0, 't', 't', '',
        '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1086, 'Log Export', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', 0, 't', 't', '', '2021-02-26 07:53:20',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1087, 'Task Query', 'infra:job:query', 3, 1, 110, '', '', '', 0, 't', 't', '1', '2021-03-10 01:26:19', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1088, 'Log query', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', 0, 't', 't', '1', '2021-03-10 01:28:04',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1089, 'Log query', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', 0, 't', 't', '1', '2021-03-10 01:29:09',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1090, 'File list', '', 2, 5, 1243, 'file', 'upload', 'infra/file/index', 0, 't', 't', '', '2021-03-12 20:16:20',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1091, 'File Query', 'infra:file:query', 3, 1, 1090, '', '', '', 0, 't', 't', '', '2021-03-12 20:16:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1092, 'File deletion', 'infra:file:delete', 3, 4, 1090, '', '', '', 0, 't', 't', '', '2021-03-12 20:16:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1093, 'SMS management', '', 1, 11, 1, 'sms', 'validCode', NULL, 0, 't', 't', '1', '2021-04-05 01:10:16', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1094, 'SMS channel', '', 2, 0, 1093, 'sms-channel', 'phone', 'system/sms/channel', 0, 't', 't', '',
        '2021-04-01 11:07:15', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1095, 'SMS channel query', 'system:sms-channel:query', 3, 1, 1094, '', '', '', 0, 't', 't', '',
        '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1096, 'SMS channel creation', 'system:sms-channel:create', 3, 2, 1094, '', '', '', 0, 't', 't', '',
        '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1097, 'SMS channel update', 'system:sms-channel:update', 3, 3, 1094, '', '', '', 0, 't', 't', '',
        '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1098, 'SMS channel deletion', 'system:sms-channel:delete', 3, 4, 1094, '', '', '', 0, 't', 't', '',
        '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1100, 'SMS template', '', 2, 1, 1093, 'sms-template', 'phone', 'system/sms/template', 0, 't', 't', '',
        '2021-04-01 17:35:17', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1101, 'SMS template query', 'system:sms-template:query', 3, 1, 1100, '', '', '', 0, 't', 't', '',
        '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1102, 'Create SMS template', 'system:sms-template:create', 3, 2, 1100, '', '', '', 0, 't', 't', '',
        '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1103, 'SMS template update', 'system:sms-template:update', 3, 3, 1100, '', '', '', 0, 't', 't', '',
        '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1104, 'SMS template deletion', 'system:sms-template:delete', 3, 4, 1100, '', '', '', 0, 't', 't', '',
        '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1105, 'SMS template export', 'system:sms-template:export', 3, 5, 1100, '', '', '', 0, 't', 't', '',
        '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1106, 'Send a test SMS', 'system:sms-template:send-sms', 3, 6, 1100, '', '', '', 0, 't', 't', '1',
        '2021-04-11 00:26:40', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1107, 'SMS log', '', 2, 2, 1093, 'sms-log', 'phone', 'system/sms/log', 0, 't', 't', '', '2021-04-11 08:37:05',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1108, 'SMS log query', 'system:sms-log:query', 3, 1, 1107, '', '', '', 0, 't', 't', '', '2021-04-11 08:37:05',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1109, 'SMS log export', 'system:sms-log:export', 3, 5, 1107, '', '', '', 0, 't', 't', '', '2021-04-11 08:37:05',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1110, 'Error code management', '', 2, 12, 1, 'error-code', 'code', 'system/errorCode/index', 0, 't', 't', '',
        '2021-04-13 21:46:42', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1111, 'Error code query', 'system:error-code:query', 3, 1, 1110, '', '', '', 0, 't', 't', '', '2021-04-13 21:46:42',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1112, 'Error code creation', 'system:error-code:create', 3, 2, 1110, '', '', '', 0, 't', 't', '', '2021-04-13 21:46:42',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1113, 'Error code update', 'system:error-code:update', 3, 3, 1110, '', '', '', 0, 't', 't', '', '2021-04-13 21:46:42',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1114, 'Error code deleted', 'system:error-code:delete', 3, 4, 1110, '', '', '', 0, 't', 't', '', '2021-04-13 21:46:42',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1115, 'Error code export', 'system:error-code:export', 3, 5, 1110, '', '', '', 0, 't', 't', '', '2021-04-13 21:46:42',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1117, 'Payment Management', '', 1, 11, 0, '/pay', 'money', NULL, 0, 't', 't', '1', '2021-12-25 16:43:41', '1',
        '2022-05-13 01:02:25.244', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1118, 'Leave Query', '', 2, 0, 5, 'leave', 'user', 'bpm/oa/leave/index', 0, 't', 't', '', '2021-09-20 08:51:03',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1119, 'Leave Application Query', 'bpm:oa-leave:query', 3, 1, 1118, '', '', '', 0, 't', 't', '', '2021-09-20 08:51:03', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1120, 'Leave application creation', 'bpm:oa-leave:create', 3, 2, 1118, '', '', '', 0, 't', 't', '', '2021-09-20 08:51:03',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1126, 'Application Information', '', 2, 1, 1117, 'app', 'table', 'pay/app/index', 0, 't', 't', '', '2021-11-10 01:13:30', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1127, 'Payment application information query', 'pay:app:query', 3, 1, 1126, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:31', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1128, 'Payment application information creation', 'pay:app:create', 3, 2, 1126, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:31', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1129, 'Payment application information update', 'pay:app:update', 3, 3, 1126, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:31', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1130, 'Payment application information deletion', 'pay:app:delete', 3, 4, 1126, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:31', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1131, 'Payment application information export', 'pay:app:export', 3, 5, 1126, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:31', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1132, 'Secret key analysis', 'pay:channel:parsing', 3, 6, 1129, '', '', '', 0, 't', 't', '1', '2021-11-08 15:15:47', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1133, 'Payment merchant information query', 'pay:merchant:query', 3, 1, 1132, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:41',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1134, 'Creating payment merchant information', 'pay:merchant:create', 3, 2, 1132, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:41',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1135, 'Payment merchant information update', 'pay:merchant:update', 3, 3, 1132, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:41',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1136, 'Payment merchant information deletion', 'pay:merchant:delete', 3, 4, 1132, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:41',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1137, 'Export payment merchant information', 'pay:merchant:export', 3, 5, 1132, '', '', '', 0, 't', 't', '', '2021-11-10 01:13:41',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1138, 'Tenant List', '', 2, 0, 1224, 'list', 'peoples', 'system/tenant/index', 0, 't', 't', '',
        '2021-12-14 12:31:43', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1139, 'Tenant Query', 'system:tenant:query', 3, 1, 1138, '', '', '', 0, 't', 't', '', '2021-12-14 12:31:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1140, 'Tenant creation', 'system:tenant:create', 3, 2, 1138, '', '', '', 0, 't', 't', '', '2021-12-14 12:31:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1141, 'Tenant Update', 'system:tenant:update', 3, 3, 1138, '', '', '', 0, 't', 't', '', '2021-12-14 12:31:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1142, 'Tenant deletion', 'system:tenant:delete', 3, 4, 1138, '', '', '', 0, 't', 't', '', '2021-12-14 12:31:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1143, 'Tenant export', 'system:tenant:export', 3, 5, 1138, '', '', '', 0, 't', 't', '', '2021-12-14 12:31:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1150, 'Secret key analysis', '', 3, 6, 1129, '', '', '', 0, 't', 't', '1', '2021-11-08 15:15:47', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1161, 'Refund order', '', 2, 3, 1117, 'refund', 'order', 'pay/refund/index', 0, 't', 't', '', '2021-12-25 08:29:07',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1162, 'Refund order inquiry', 'pay:refund:query', 3, 1, 1161, '', '', '', 0, 't', 't', '', '2021-12-25 08:29:07', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1163, 'Refund order creation', 'pay:refund:create', 3, 2, 1161, '', '', '', 0, 't', 't', '', '2021-12-25 08:29:07', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1164, 'Refund order update', 'pay:refund:update', 3, 3, 1161, '', '', '', 0, 't', 't', '', '2021-12-25 08:29:07', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1165, 'Refund order deletion', 'pay:refund:delete', 3, 4, 1161, '', '', '', 0, 't', 't', '', '2021-12-25 08:29:07', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1166, 'Refund order export', 'pay:refund:export', 3, 5, 1161, '', '', '', 0, 't', 't', '', '2021-12-25 08:29:07', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1173, 'Payment order', '', 2, 2, 1117, 'order', 'pay', 'pay/order/index', 0, 't', 't', '', '2021-12-25 08:49:43',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1174, 'Payment Order Query', 'pay:order:query', 3, 1, 1173, '', '', '', 0, 't', 't', '', '2021-12-25 08:49:43', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1175, 'Payment order creation', 'pay:order:create', 3, 2, 1173, '', '', '', 0, 't', 't', '', '2021-12-25 08:49:43', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1176, 'Payment order update', 'pay:order:update', 3, 3, 1173, '', '', '', 0, 't', 't', '', '2021-12-25 08:49:43', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1177, 'Payment order deletion', 'pay:order:delete', 3, 4, 1173, '', '', '', 0, 't', 't', '', '2021-12-25 08:49:43', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1178, 'Payment order export', 'pay:order:export', 3, 5, 1173, '', '', '', 0, 't', 't', '', '2021-12-25 08:49:43', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1179, 'Merchant Information', '', 2, 0, 1117, 'merchant', 'merchant', 'pay/merchant/index', 0, 't', 't', '',
        '2021-12-25 09:01:44', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1180, 'Payment merchant information query', 'pay:merchant:query', 3, 1, 1179, '', '', '', 0, 't', 't', '', '2021-12-25 09:01:44',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1181, 'Creating payment merchant information', 'pay:merchant:create', 3, 2, 1179, '', '', '', 0, 't', 't', '', '2021-12-25 09:01:44',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1182, 'Payment merchant information update', 'pay:merchant:update', 3, 3, 1179, '', '', '', 0, 't', 't', '', '2021-12-25 09:01:44',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1183, 'Payment merchant information deletion', '', 3, 4, 1179, '', '', '', 0, 't', 't', '', '2021-12-25 09:01:44', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1184, 'Payment merchant information export', 'pay:merchant:export', 3, 5, 1179, '', '', '', 0, 't', 't', '', '2021-12-25 09:01:44',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1185, 'Workflow', '', 1, 50, 0, '/bpm', 'tool', NULL, 0, 't', 't', '1', '2021-12-30 20:26:36', '103',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1186, 'Process Management', '', 1, 10, 1185, 'manager', 'nested', NULL, 0, 't', 't', '1', '2021-12-30 20:28:30', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1187, 'Process Form', '', 2, 0, 1186, 'form', 'form', 'bpm/form/index', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1188, 'Form query', 'bpm:form:query', 3, 1, 1187, '', '', '', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1189, 'Form creation', 'bpm:form:create', 3, 2, 1187, '', '', '', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1190, 'Form update', 'bpm:form:update', 3, 3, 1187, '', '', '', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1191, 'Form Delete', 'bpm:form:delete', 3, 4, 1187, '', '', '', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1192, 'Form export', 'bpm:form:export', 3, 5, 1187, '', '', '', 0, 't', 't', '', '2021-12-30 12:38:22', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1193, 'Process Model', '', 2, 5, 1186, 'model', 'guide', 'bpm/model/index', 0, 't', 't', '1', '2021-12-31 23:24:58',
        '103', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1194, 'Model Query', 'bpm:model:query', 3, 1, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:01:10', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1195, 'Model creation', 'bpm:model:create', 3, 2, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:01:24', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1196, 'Model import', 'bpm:model:import', 3, 3, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:01:35', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1197, 'Model update', 'bpm:model:update', 3, 4, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:02:28', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1198, 'Model deletion', 'bpm:model:delete', 3, 5, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:02:43', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1199, 'Model Release', 'bpm:model:deploy', 3, 6, 1193, '', '', '', 0, 't', 't', '1', '2022-01-03 19:03:24', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1200, 'Task Management', '', 1, 20, 1185, 'task', 'cascader', NULL, 0, 't', 't', '1', '2022-01-07 23:51:48', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1201, 'My process', '', 2, 0, 1200, 'my', 'people', 'bpm/processInstance/index', 0, 't', 't', '',
        '2022-01-07 15:53:44', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1202, 'Query of process instance', 'bpm:process-instance:query', 3, 1, 1201, '', '', '', 0, 't', 't', '',
        '2022-01-07 15:53:44', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1207, 'To-do tasks', '', 2, 10, 1200, 'todo', 'eye-open', 'bpm/task/todo', 0, 't', 't', '1', '2022-01-08 10:33:37',
        '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1208, 'Task completed', '', 2, 20, 1200, 'done', 'eye', 'bpm/task/done', 0, 't', 't', '1', '2022-01-08 10:34:13', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1209, 'User Grouping', '', 2, 2, 1186, 'user-group', 'people', 'bpm/group/index', 0, 't', 't', '',
        '2022-01-14 02:14:20', '103', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1210, 'User Group Query', 'bpm:user-group:query', 3, 1, 1209, '', '', '', 0, 't', 't', '', '2022-01-14 02:14:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1211, 'User group creation', 'bpm:user-group:create', 3, 2, 1209, '', '', '', 0, 't', 't', '', '2022-01-14 02:14:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1212, 'User Group Update', 'bpm:user-group:update', 3, 3, 1209, '', '', '', 0, 't', 't', '', '2022-01-14 02:14:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1213, 'User group deletion', 'bpm:user-group:delete', 3, 4, 1209, '', '', '', 0, 't', 't', '', '2022-01-14 02:14:20', '',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1215, 'Process definition query', 'bpm:process-definition:query', 3, 10, 1193, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:21:43', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1216, 'Process task allocation rule query', 'bpm:task-assign-rule:query', 3, 20, 1193, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:26:53', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1217, 'Creating process task allocation rules', 'bpm:task-assign-rule:create', 3, 21, 1193, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:28:15', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1218, 'Update of process task allocation rules', 'bpm:task-assign-rule:update', 3, 22, 1193, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:28:41', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1219, 'Creation of process instance', 'bpm:process-instance:create', 3, 2, 1201, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:36:15', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1220, 'Cancellation of process instance', 'bpm:process-instance:cancel', 3, 3, 1201, '', '', '', 0, 't', 't', '1',
        '2022-01-23 00:36:33', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1221, 'Query of process tasks', 'bpm:task:query', 3, 1, 1207, '', '', '', 0, 't', 't', '1', '2022-01-23 00:38:52', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1222, 'Update of process tasks', 'bpm:task:update', 3, 2, 1207, '', '', '', 0, 't', 't', '1', '2022-01-23 00:39:24', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1224, 'Tenant Management', '', 2, 0, 1, 'tenant', 'peoples', NULL, 0, 't', 't', '1', '2022-02-20 01:41:13', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1225, 'Tenant Package', '', 2, 0, 1224, 'package', 'eye', 'system/tenantPackage/index', 0, 't', 't', '',
        '2022-02-19 17:44:06', '1', '2022-04-21 01:21:25', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1226, 'Tenant Package Query', 'system:tenant-package:query', 3, 1, 1225, '', '', '', 0, 't', 't', '',
        '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1227, 'Tenant package creation', 'system:tenant-package:create', 3, 2, 1225, '', '', '', 0, 't', 't', '',
        '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1228, 'Tenant package update', 'system:tenant-package:update', 3, 3, 1225, '', '', '', 0, 't', 't', '',
        '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1229, 'Tenant package deletion', 'system:tenant-package:delete', 3, 4, 1225, '', '', '', 0, 't', 't', '',
        '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1237, 'File configuration', '', 2, 0, 1243, 'file-config', 'config', 'infra/fileConfig/index', 0, 't', 't', '',
        '2022-03-15 14:35:28', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1238, 'File configuration query', 'infra:file-config:query', 3, 1, 1237, '', '', '', 0, 't', 't', '', '2022-03-15 14:35:28',
        '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1239, 'File configuration creation', 'infra:file-config:create', 3, 2, 1237, '', '', '', 0, 't', 't', '',
        '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1240, 'File configuration update', 'infra:file-config:update', 3, 3, 1237, '', '', '', 0, 't', 't', '',
        '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1241, 'File configuration deletion', 'infra:file-config:delete', 3, 4, 1237, '', '', '', 0, 't', 't', '',
        '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1242, 'File configuration export', 'infra:file-config:export', 3, 5, 1237, '', '', '', 0, 't', 't', '',
        '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1243, 'File Management', '', 2, 5, 2, 'file', 'download', NULL, 0, 't', 't', '1', '2022-03-16 23:47:40', '1',
        '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1247, 'Sensitive word management', '', 2, 13, 1, 'sensitive-word', 'education', 'system/sensitiveWord/index', 0, 't', 't', '',
        '2022-04-07 16:55:03', '1', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1248, 'Sensitive word search', 'system:sensitive-word:query', 3, 1, 1247, '', '', '', 0, 't', 't', '',
        '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1249, 'Creating sensitive words', 'system:sensitive-word:create', 3, 2, 1247, '', '', '', 0, 't', 't', '',
        '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1250, 'Sensitive word update', 'system:sensitive-word:update', 3, 3, 1247, '', '', '', 0, 't', 't', '',
        '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1251, 'Sensitive words deleted', 'system:sensitive-word:delete', 3, 4, 1247, '', '', '', 0, 't', 't', '',
        '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1252, 'Sensitive word export', 'system:sensitive-word:export', 3, 5, 1247, '', '', '', 0, 't', 't', '',
        '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1254, 'Author's dynamics', '', 1, 0, 0, 'https://www.econets.cn', 'people', NULL, 0, 't', 't', '1',
        '2022-04-23 01:03:15', '1', '2022-04-23 01:03:15', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1255, 'Data source configuration', '', 2, 1, 2, 'data-source-config', 'rate', 'infra/dataSourceConfig/index', 0, 't', 't', '',
        '2022-04-27 14:37:32', '1', '2022-04-27 22:42:06', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1256, 'Data source configuration query', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', 0, 't', 't', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1257, 'Data source configuration creation', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', 0, 't', 't', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1258, 'Data source configuration update', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', 0, 't', 't', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1259, 'Data source configuration deletion', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', 0, 't', 't', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1260, 'Data source configuration export', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', 0, 't', 't', '',
        '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1261, 'OAuth 2.0', '', 1, 10, 1, 'oauth2', 'people', NULL, 0, 't', 't', '1', '2022-05-09 23:38:17', '1',
        '2022-05-11 23:51:46', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1263, 'Application Management', '', 2, 0, 1261, 'oauth2/application', 'tool', 'system/oauth2/client/index', 0, 't', 't', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 23:31:36', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1264, 'Client query', 'system:oauth2-client:query', 3, 1, 1263, '', '', '', 0, 't', 't', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 00:31:06', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1265, 'Client creation', 'system:oauth2-client:create', 3, 2, 1263, '', '', '', 0, 't', 't', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 00:31:23', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1266, 'Client Update', 'system:oauth2-client:update', 3, 3, 1263, '', '', '', 0, 't', 't', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 00:31:28', 0, NULL, '1');
INSERT INTO "system_menu" ("id", "name", "permission", "type", "sort", "parent_id", "path", "icon", "component",
                           "status", "visible", "keep_alive", "creator", "create_time", "updater", "update_time",
                           "deleted", "component_name", "always_show")
VALUES (1267, 'Client deletion', 'system:oauth2-client:delete', 3, 4, 1263, '', '', '', 0, 't', 't', '',
        '2022-05-10 16:26:33', '1', '2022-05-11 00:31:33', 0, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS "system_notice";
CREATE TABLE "system_notice"
(
    "id"          int8                                       NOT NULL,
    "title"       varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "content"     text COLLATE "pg_catalog"."default"        NOT NULL,
    "type"        int2                                       NOT NULL,
    "status"      int2                                       NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                               NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                               NOT NULL,
    "deleted"     int2                                       NOT NULL DEFAULT 0,
    "tenant_id"   int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_notice"."id" IS 'AnnouncementID';
COMMENT
ON COLUMN "system_notice"."title" IS 'Announcement Title';
COMMENT
ON COLUMN "system_notice"."content" IS 'Announcement content';
COMMENT
ON COLUMN "system_notice"."type" IS 'Announcement Type（1Notice 2Announcement）';
COMMENT
ON COLUMN "system_notice"."status" IS 'Announcement Status（0Normal 1Close）';
COMMENT
ON COLUMN "system_notice"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_notice"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_notice"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_notice"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_notice"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_notice"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_notice" IS 'Notice table';

-- ----------------------------
-- Records of system_notice
-- ----------------------------
BEGIN;
INSERT INTO "system_notice" ("id", "title", "content", "type", "status", "creator", "create_time", "updater",
                             "update_time", "deleted", "tenant_id")
VALUES (1, 'The public of Yudao', '<p>New version content133</p>', 1, 0, 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 21:00:20', 0,
        1);
INSERT INTO "system_notice" ("id", "title", "content", "type", "status", "creator", "create_time", "updater",
                             "update_time", "deleted", "tenant_id")
VALUES (2, 'Maintenance Notice：2018-07-01 If the system is maintained at midnight',
        '<p><img src="http://test.blossom.econets.cn/b7cb3cf49b4b3258bf7309a09dd2f4e5.jpg">Maintenance content</p>', 2, 1, 'admin',
        '2021-01-05 17:03:48', '1', '2022-05-11 12:34:24', 0, 1);
INSERT INTO "system_notice" ("id", "title", "content", "type", "status", "creator", "create_time", "updater",
                             "update_time", "deleted", "tenant_id")
VALUES (4, 'I am a test title', '<p>Hahahaha123</p>', 1, 0, '110', '2022-02-22 01:01:25', '110', '2022-02-22 01:01:46', 0,
        121);
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS "system_oauth2_access_token";
CREATE TABLE "system_oauth2_access_token"
(
    "id"            int8                                        NOT NULL,
    "user_id"       int8                                        NOT NULL,
    "access_token"  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "refresh_token" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_type"     int2                                        NOT NULL,
    "client_id"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "expires_time"  timestamp(6)                                NOT NULL,
    "creator"       varchar(64) COLLATE "pg_catalog"."default",
    "create_time"   timestamp(6)                                NOT NULL,
    "updater"       varchar(64) COLLATE "pg_catalog"."default",
    "update_time"   timestamp(6)                                NOT NULL,
    "deleted"       int2                                        NOT NULL DEFAULT 0,
    "tenant_id"     int8                                        NOT NULL DEFAULT 0,
    "scopes"        varchar(255) COLLATE "pg_catalog"."default"          DEFAULT '':: character varying
)
;
COMMENT
ON COLUMN "system_oauth2_access_token"."id" IS 'Number';
COMMENT
ON COLUMN "system_oauth2_access_token"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_oauth2_access_token"."access_token" IS 'Access Token';
COMMENT
ON COLUMN "system_oauth2_access_token"."refresh_token" IS 'Refresh Token';
COMMENT
ON COLUMN "system_oauth2_access_token"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_oauth2_access_token"."client_id" IS 'Client ID';
COMMENT
ON COLUMN "system_oauth2_access_token"."expires_time" IS 'Expiration time';
COMMENT
ON COLUMN "system_oauth2_access_token"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_oauth2_access_token"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_oauth2_access_token"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_oauth2_access_token"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_oauth2_access_token"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_oauth2_access_token"."tenant_id" IS 'Tenant Number';
COMMENT
ON COLUMN "system_oauth2_access_token"."scopes" IS 'Authorization scope';
COMMENT
ON TABLE "system_oauth2_access_token" IS 'Refresh Token';

-- ----------------------------
-- Records of system_oauth2_access_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_approve
-- ----------------------------
DROP TABLE IF EXISTS "system_oauth2_approve";
CREATE TABLE "system_oauth2_approve"
(
    "id"           int8                                        NOT NULL,
    "user_id"      int8                                        NOT NULL,
    "user_type"    int2                                        NOT NULL,
    "client_id"    varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "scope"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "approved"     bool                                        NOT NULL,
    "expires_time" timestamp(6)                                NOT NULL,
    "creator"      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"  timestamp(6)                                NOT NULL,
    "updater"      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)                                NOT NULL,
    "deleted"      int2                                        NOT NULL DEFAULT 0,
    "tenant_id"    int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_oauth2_approve"."id" IS 'Number';
COMMENT
ON COLUMN "system_oauth2_approve"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_oauth2_approve"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_oauth2_approve"."client_id" IS 'Client ID';
COMMENT
ON COLUMN "system_oauth2_approve"."scope" IS 'Authorization scope';
COMMENT
ON COLUMN "system_oauth2_approve"."approved" IS 'Do you accept?';
COMMENT
ON COLUMN "system_oauth2_approve"."expires_time" IS 'Expiration time';
COMMENT
ON COLUMN "system_oauth2_approve"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_oauth2_approve"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_oauth2_approve"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_oauth2_approve"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_oauth2_approve"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_oauth2_approve"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_oauth2_approve" IS 'OAuth2 Approval Form';

-- ----------------------------
-- Records of system_oauth2_approve
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS "system_oauth2_client";
CREATE TABLE "system_oauth2_client"
(
    "id"                             int8                                        NOT NULL,
    "client_id"                      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "secret"                         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "name"                           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "logo"                           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "description"                    varchar(255) COLLATE "pg_catalog"."default",
    "status"                         int2                                        NOT NULL,
    "access_token_validity_seconds"  int4                                        NOT NULL,
    "refresh_token_validity_seconds" int4                                        NOT NULL,
    "redirect_uris"                  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "authorized_grant_types"         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "scopes"                         varchar(255) COLLATE "pg_catalog"."default",
    "authorities"                    varchar(255) COLLATE "pg_catalog"."default",
    "resource_ids"                   varchar(255) COLLATE "pg_catalog"."default",
    "additional_information"         varchar(4096) COLLATE "pg_catalog"."default",
    "creator"                        varchar(64) COLLATE "pg_catalog"."default",
    "create_time"                    timestamp(6)                                NOT NULL,
    "updater"                        varchar(64) COLLATE "pg_catalog"."default",
    "update_time"                    timestamp(6)                                NOT NULL,
    "deleted"                        int2                                        NOT NULL DEFAULT 0,
    "auto_approve_scopes"            varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "system_oauth2_client"."id" IS 'Number';
COMMENT
ON COLUMN "system_oauth2_client"."client_id" IS 'Client ID';
COMMENT
ON COLUMN "system_oauth2_client"."secret" IS 'Client Key';
COMMENT
ON COLUMN "system_oauth2_client"."name" IS 'Application name';
COMMENT
ON COLUMN "system_oauth2_client"."logo" IS 'Application Icon';
COMMENT
ON COLUMN "system_oauth2_client"."description" IS 'Application Description';
COMMENT
ON COLUMN "system_oauth2_client"."status" IS 'Status';
COMMENT
ON COLUMN "system_oauth2_client"."access_token_validity_seconds" IS 'Access token validity period';
COMMENT
ON COLUMN "system_oauth2_client"."refresh_token_validity_seconds" IS 'Validity period of refresh token';
COMMENT
ON COLUMN "system_oauth2_client"."redirect_uris" IS 'Redirectable URI Address';
COMMENT
ON COLUMN "system_oauth2_client"."authorized_grant_types" IS 'Authorization type';
COMMENT
ON COLUMN "system_oauth2_client"."scopes" IS 'Authorization scope';
COMMENT
ON COLUMN "system_oauth2_client"."authorities" IS 'Permissions';
COMMENT
ON COLUMN "system_oauth2_client"."resource_ids" IS 'Resources';
COMMENT
ON COLUMN "system_oauth2_client"."additional_information" IS 'Additional information';
COMMENT
ON COLUMN "system_oauth2_client"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_oauth2_client"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_oauth2_client"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_oauth2_client"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_oauth2_client"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_oauth2_client"."auto_approve_scopes" IS 'Automatically approved authorization scope';
COMMENT
ON TABLE "system_oauth2_client" IS 'OAuth2 Client Table';

-- ----------------------------
-- Records of system_oauth2_client
-- ----------------------------
BEGIN;
INSERT INTO "system_oauth2_client" ("id", "client_id", "secret", "name", "logo", "description", "status",
                                    "access_token_validity_seconds", "refresh_token_validity_seconds", "redirect_uris",
                                    "authorized_grant_types", "scopes", "authorities", "resource_ids",
                                    "additional_information", "creator", "create_time", "updater", "update_time",
                                    "deleted", "auto_approve_scopes")
VALUES (1, 'default', 'admin123', 'Source code', 'http://test.blossom.econets.cn/a5e2e244368878a366b516805a4aabf1.png',
        'I am describing', 0, 180, 8640, '["https://www.econets.cn","https://doc.econets.cn"]',
        '["password","authorization_code","implicit","refresh_token"]', '["user.read","user.write"]',
        '["system:user:query"]', '[]', '{}', '1', '2022-05-11 21:47:12', '1', '2022-05-12 01:00:20', 0, NULL);
INSERT INTO "system_oauth2_client" ("id", "client_id", "secret", "name", "logo", "description", "status",
                                    "access_token_validity_seconds", "refresh_token_validity_seconds", "redirect_uris",
                                    "authorized_grant_types", "scopes", "authorities", "resource_ids",
                                    "additional_information", "creator", "create_time", "updater", "update_time",
                                    "deleted", "auto_approve_scopes")
VALUES (40, 'test', 'test2', 'biubiu', 'http://test.blossom.econets.cn/277a899d573723f1fcdfb57340f00379.png', NULL, 0,
        1800, 43200, '["https://www.econets.cn"]', '["password","authorization_code","implicit"]', '[]', '[]', '[]',
        '{}', '1', '2022-05-12 00:28:20', '1', '2022-05-25 23:45:33.005', 0, '[]');
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_code
-- ----------------------------
DROP TABLE IF EXISTS "system_oauth2_code";
CREATE TABLE "system_oauth2_code"
(
    "id"           int8                                        NOT NULL,
    "user_id"      int8                                        NOT NULL,
    "user_type"    int2                                        NOT NULL,
    "code"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "client_id"    varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "scopes"       varchar(255) COLLATE "pg_catalog"."default",
    "expires_time" timestamp(6)                                NOT NULL,
    "redirect_uri" varchar(255) COLLATE "pg_catalog"."default",
    "state"        varchar(255) COLLATE "pg_catalog"."default",
    "creator"      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"  timestamp(6)                                NOT NULL,
    "updater"      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)                                NOT NULL,
    "deleted"      int2                                        NOT NULL DEFAULT 0,
    "tenant_id"    int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_oauth2_code"."id" IS 'Number';
COMMENT
ON COLUMN "system_oauth2_code"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_oauth2_code"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_oauth2_code"."code" IS 'Authorization code';
COMMENT
ON COLUMN "system_oauth2_code"."client_id" IS 'Client ID';
COMMENT
ON COLUMN "system_oauth2_code"."scopes" IS 'Authorization scope';
COMMENT
ON COLUMN "system_oauth2_code"."expires_time" IS 'Expiration time';
COMMENT
ON COLUMN "system_oauth2_code"."redirect_uri" IS 'Redirectable URI Address';
COMMENT
ON COLUMN "system_oauth2_code"."state" IS 'Status';
COMMENT
ON COLUMN "system_oauth2_code"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_oauth2_code"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_oauth2_code"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_oauth2_code"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_oauth2_code"."deleted" IS 'Do you want to delete?';
COMMENT
ON COLUMN "system_oauth2_code"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_oauth2_code" IS 'OAuth2 Authorization code table';

-- ----------------------------
-- Records of system_oauth2_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS "system_oauth2_refresh_token";
CREATE TABLE "system_oauth2_refresh_token"
(
    "id"            int8                                        NOT NULL,
    "user_id"       int8                                        NOT NULL,
    "refresh_token" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_type"     int2                                        NOT NULL,
    "client_id"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "expires_time"  timestamp(6)                                NOT NULL,
    "creator"       varchar(64) COLLATE "pg_catalog"."default",
    "create_time"   timestamp(6)                                NOT NULL,
    "updater"       varchar(64) COLLATE "pg_catalog"."default",
    "update_time"   timestamp(6)                                NOT NULL,
    "deleted"       int2                                        NOT NULL DEFAULT 0,
    "tenant_id"     int8                                        NOT NULL DEFAULT 0,
    "scopes"        varchar(255) COLLATE "pg_catalog"."default"          DEFAULT '':: character varying
)
;
COMMENT
ON COLUMN "system_oauth2_refresh_token"."id" IS 'Number';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."refresh_token" IS 'Refresh Token';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."client_id" IS 'Client ID';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."expires_time" IS 'Expiration time';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."tenant_id" IS 'Tenant Number';
COMMENT
ON COLUMN "system_oauth2_refresh_token"."scopes" IS 'Authorization scope';
COMMENT
ON TABLE "system_oauth2_refresh_token" IS 'Refresh Token';

-- ----------------------------
-- Records of system_oauth2_refresh_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS "system_operate_log";
CREATE TABLE "system_operate_log"
(
    "id"               int8                                         NOT NULL,
    "trace_id"         varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "user_id"          int8                                         NOT NULL,
    "user_type"        int2                                         NOT NULL,
    "module"           varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "name"             varchar(50) COLLATE "pg_catalog"."default"   NOT NULL,
    "type"             int8                                         NOT NULL,
    "content"          varchar(2000) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '':: character varying,
    "exts"             varchar(512) COLLATE "pg_catalog"."default"  NOT NULL DEFAULT '':: character varying,
    "request_method"   varchar(16) COLLATE "pg_catalog"."default",
    "request_url"      varchar(255) COLLATE "pg_catalog"."default",
    "user_ip"          varchar(50) COLLATE "pg_catalog"."default",
    "user_agent"       varchar(200) COLLATE "pg_catalog"."default",
    "java_method"      varchar(512) COLLATE "pg_catalog"."default"  NOT NULL,
    "java_method_args" varchar(8000) COLLATE "pg_catalog"."default",
    "start_time"       timestamp(6)                                 NOT NULL,
    "duration"         int4                                         NOT NULL,
    "result_code"      int4                                         NOT NULL,
    "result_msg"       varchar(512) COLLATE "pg_catalog"."default",
    "result_data"      varchar(4000) COLLATE "pg_catalog"."default",
    "creator"          varchar(64) COLLATE "pg_catalog"."default",
    "create_time"      timestamp(6)                                 NOT NULL,
    "updater"          varchar(64) COLLATE "pg_catalog"."default",
    "update_time"      timestamp(6)                                 NOT NULL,
    "deleted"          int2                                         NOT NULL DEFAULT 0,
    "tenant_id"        int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_operate_log"."id" IS 'Log primary key';
COMMENT
ON COLUMN "system_operate_log"."trace_id" IS 'Link tracking number';
COMMENT
ON COLUMN "system_operate_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_operate_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_operate_log"."module" IS 'Module title';
COMMENT
ON COLUMN "system_operate_log"."name" IS 'Operation name';
COMMENT
ON COLUMN "system_operate_log"."type" IS 'Operation Category';
COMMENT
ON COLUMN "system_operate_log"."content" IS 'Operation content';
COMMENT
ON COLUMN "system_operate_log"."exts" IS 'Extended fields';
COMMENT
ON COLUMN "system_operate_log"."request_method" IS 'Request method name';
COMMENT
ON COLUMN "system_operate_log"."request_url" IS 'Request address';
COMMENT
ON COLUMN "system_operate_log"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "system_operate_log"."user_agent" IS 'Browser UA';
COMMENT
ON COLUMN "system_operate_log"."java_method" IS 'Java Method name';
COMMENT
ON COLUMN "system_operate_log"."java_method_args" IS 'Java Method parameters';
COMMENT
ON COLUMN "system_operate_log"."start_time" IS 'Operation time';
COMMENT
ON COLUMN "system_operate_log"."duration" IS 'Execution duration';
COMMENT
ON COLUMN "system_operate_log"."result_code" IS 'Result code';
COMMENT
ON COLUMN "system_operate_log"."result_msg" IS 'Result prompt';
COMMENT
ON COLUMN "system_operate_log"."result_data" IS 'Result data';
COMMENT
ON COLUMN "system_operate_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_operate_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_operate_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_operate_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_operate_log"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_operate_log"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_operate_log" IS 'Operation log record';

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS "system_post";
CREATE TABLE "system_post"
(
    "id"          int8                                       NOT NULL DEFAULT 0,
    "code"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"        varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "sort"        int4                                       NOT NULL,
    "status"      int2                                       NOT NULL,
    "remark"      varchar(500) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                               NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                               NOT NULL,
    "deleted"     int2                                       NOT NULL DEFAULT 0,
    "tenant_id"   int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_post"."id" IS 'PositionID';
COMMENT
ON COLUMN "system_post"."code" IS 'Position code';
COMMENT
ON COLUMN "system_post"."name" IS 'Position Name';
COMMENT
ON COLUMN "system_post"."sort" IS 'Display order';
COMMENT
ON COLUMN "system_post"."status" IS 'Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_post"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_post"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_post"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_post"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_post"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_post"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_post"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_post" IS 'Position Information Table';

-- ----------------------------
-- Records of system_post
-- ----------------------------
BEGIN;
INSERT INTO "system_post" ("id", "code", "name", "sort", "status", "remark", "creator", "create_time", "updater",
                           "update_time", "deleted", "tenant_id")
VALUES (1, 'ceo', 'Chairman', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2022-04-19 16:53:39', 0, 1);
INSERT INTO "system_post" ("id", "code", "name", "sort", "status", "remark", "creator", "create_time", "updater",
                           "update_time", "deleted", "tenant_id")
VALUES (2, 'se', 'Project Manager', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', 0, 1);
INSERT INTO "system_post" ("id", "code", "name", "sort", "status", "remark", "creator", "create_time", "updater",
                           "update_time", "deleted", "tenant_id")
VALUES (4, 'user', 'Ordinary employee', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 22:46:35', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS "system_role";
CREATE TABLE "system_role"
(
    "id"                  int8                                        NOT NULL,
    "name"                varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "code"                varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "sort"                int4                                        NOT NULL,
    "data_scope"          int2                                        NOT NULL,
    "data_scope_dept_ids" varchar(500) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '',
    "status"              int2                                        NOT NULL,
    "type"                int2                                        NOT NULL,
    "remark"              varchar(500) COLLATE "pg_catalog"."default",
    "creator"             varchar(64) COLLATE "pg_catalog"."default",
    "create_time"         timestamp(6)                                NOT NULL,
    "updater"             varchar(64) COLLATE "pg_catalog"."default",
    "update_time"         timestamp(6)                                NOT NULL,
    "deleted"             int2                                        NOT NULL DEFAULT 0,
    "tenant_id"           int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_role"."id" IS 'RoleID';
COMMENT
ON COLUMN "system_role"."name" IS 'Role name';
COMMENT
ON COLUMN "system_role"."code" IS 'Role permission string';
COMMENT
ON COLUMN "system_role"."sort" IS 'Display order';
COMMENT
ON COLUMN "system_role"."data_scope" IS 'Data Range（1：All data permissions 2：Customize data permissions 3：Data permissions for this department 4：Data permissions for this department and below）';
COMMENT
ON COLUMN "system_role"."data_scope_dept_ids" IS 'Data Range(Specify department array)';
COMMENT
ON COLUMN "system_role"."status" IS 'Character Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_role"."type" IS 'Role type';
COMMENT
ON COLUMN "system_role"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_role"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_role"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_role"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_role"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_role"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_role"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_role" IS 'Role Information Table';

-- ----------------------------
-- Records of system_role
-- ----------------------------
BEGIN;
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (1, 'Super Administrator', 'super_admin', 1, 1, '', 0, 1, 'Super Administrator', 'admin', '2021-01-05 17:03:48', '',
        '2022-02-22 05:08:21', 0, 1);
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (2, 'Ordinary character', 'common', 2, 2, '', 0, 1, 'Ordinary character', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20',
        0, 1);
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (101, 'Test account', 'test', 0, 1, '[]', 0, 2, '132', '', '2021-01-06 13:49:35', '1', '2022-04-01 21:37:13', 0, 1);
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (109, 'Tenant Administrator', 'tenant_admin', 0, 1, '', 0, 1, 'Automatically generated by the system', '1', '2022-02-22 00:56:14', '1',
        '2022-02-22 00:56:14', 0, 121);
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (110, 'Test role', 'test', 0, 1, '[]', 0, 2, 'Hey', '110', '2022-02-23 00:14:34', '110', '2022-02-23 13:14:58',
        0, 121);
INSERT INTO "system_role" ("id", "name", "code", "sort", "data_scope", "data_scope_dept_ids", "status", "type",
                           "remark", "creator", "create_time", "updater", "update_time", "deleted", "tenant_id")
VALUES (111, 'Tenant Administrator', 'tenant_admin', 0, 1, '', 0, 1, 'Automatically generated by the system', '1', '2022-03-07 21:37:58', '1',
        '2022-03-07 21:37:58', 0, 122);
COMMIT;

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "system_role_menu";
CREATE TABLE "system_role_menu"
(
    "id"          int8         NOT NULL,
    "role_id"     int8         NOT NULL,
    "menu_id"     int8         NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6) NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6) NOT NULL,
    "deleted"     int2         NOT NULL DEFAULT 0,
    "tenant_id"   int8         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_role_menu"."id" IS 'Self-increment number';
COMMENT
ON COLUMN "system_role_menu"."role_id" IS 'RoleID';
COMMENT
ON COLUMN "system_role_menu"."menu_id" IS 'MenuID';
COMMENT
ON COLUMN "system_role_menu"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_role_menu"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_role_menu"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_role_menu"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_role_menu"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_role_menu"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_role_menu" IS 'Role and menu association table';

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
BEGIN;
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (263, 109, 1, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (434, 2, 1, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (454, 2, 1093, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (455, 2, 1094, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (460, 2, 1100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (467, 2, 1107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (470, 2, 1110, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (476, 2, 1117, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (477, 2, 100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (478, 2, 101, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (479, 2, 102, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (480, 2, 1126, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (481, 2, 103, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (483, 2, 104, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (485, 2, 105, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (488, 2, 107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (490, 2, 108, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (492, 2, 109, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (498, 2, 1138, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (523, 2, 1224, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (524, 2, 1225, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (541, 2, 500, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (543, 2, 501, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (675, 2, 2, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (689, 2, 1077, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (690, 2, 1078, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (692, 2, 1083, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (693, 2, 1084, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (699, 2, 1090, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (703, 2, 106, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (704, 2, 110, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (705, 2, 111, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (706, 2, 112, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (707, 2, 113, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1296, 110, 1, '110', '2022-02-23 00:23:55', '110', '2022-02-23 00:23:55', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1486, 109, 103, '1', '2022-02-23 19:32:14', '1', '2022-02-23 19:32:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1487, 109, 104, '1', '2022-02-23 19:32:14', '1', '2022-02-23 19:32:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1489, 1, 1, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1490, 1, 2, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1494, 1, 1077, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1495, 1, 1078, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1496, 1, 1083, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1497, 1, 1084, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1498, 1, 1090, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1499, 1, 1093, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1500, 1, 1094, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1501, 1, 1100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1502, 1, 1107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1503, 1, 1110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1505, 1, 1117, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1506, 1, 100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1507, 1, 101, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1508, 1, 102, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1509, 1, 1126, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1510, 1, 103, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1511, 1, 104, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1512, 1, 105, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1513, 1, 106, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1514, 1, 107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1515, 1, 108, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1516, 1, 109, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1517, 1, 110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1518, 1, 111, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1519, 1, 112, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1520, 1, 113, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1522, 1, 1138, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1525, 1, 1224, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1526, 1, 1225, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1527, 1, 500, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1528, 1, 501, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1529, 109, 1024, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1530, 109, 1025, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1536, 109, 1017, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1537, 109, 1018, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1538, 109, 1019, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1539, 109, 1020, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1540, 109, 1021, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1541, 109, 1022, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1542, 109, 1023, '1', '2022-02-23 20:30:14', '1', '2022-02-23 20:30:14', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1576, 111, 1024, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1577, 111, 1025, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1578, 111, 1, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1584, 111, 103, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1585, 111, 104, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1587, 111, 1017, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1588, 111, 1018, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1589, 111, 1019, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1590, 111, 1020, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1591, 111, 1021, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1592, 111, 1022, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1593, 111, 1023, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1594, 109, 102, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1595, 109, 1013, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1596, 109, 1014, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1597, 109, 1015, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1598, 109, 1016, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 121);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1599, 111, 102, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1600, 111, 1013, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1601, 111, 1014, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1602, 111, 1015, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1603, 111, 1016, '1', '2022-03-19 18:39:13', '1', '2022-03-19 18:39:13', 0, 122);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1604, 101, 1216, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1605, 101, 1217, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1606, 101, 1218, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1607, 101, 1219, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1608, 101, 1220, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1609, 101, 1221, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1610, 101, 5, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1611, 101, 1222, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1612, 101, 1118, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1613, 101, 1119, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1614, 101, 1120, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1615, 101, 1185, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1616, 101, 1186, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1617, 101, 1187, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1618, 101, 1188, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1619, 101, 1189, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1620, 101, 1190, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1621, 101, 1191, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1622, 101, 1192, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1623, 101, 1193, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1624, 101, 1194, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1625, 101, 1195, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1626, 101, 1196, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1627, 101, 1197, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1628, 101, 1198, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1629, 101, 1199, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1630, 101, 1200, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1631, 101, 1201, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1632, 101, 1202, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1633, 101, 1207, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1634, 101, 1208, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1635, 101, 1209, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1636, 101, 1210, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1637, 101, 1211, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1638, 101, 1212, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1639, 101, 1213, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1640, 101, 1215, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1641, 101, 2, '1', '2022-04-01 22:21:24', '1', '2022-04-01 22:21:24', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1642, 101, 1031, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1643, 101, 1032, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1644, 101, 1033, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1645, 101, 1034, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1646, 101, 1035, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1647, 101, 1050, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1648, 101, 1051, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1649, 101, 1052, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1650, 101, 1053, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1651, 101, 1054, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1652, 101, 1056, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1653, 101, 1057, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1654, 101, 1058, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1655, 101, 1059, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1656, 101, 1060, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1657, 101, 1066, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1658, 101, 1067, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1659, 101, 1070, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1660, 101, 1071, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1661, 101, 1072, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1662, 101, 1073, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1663, 101, 1074, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1664, 101, 1075, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1665, 101, 1076, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1666, 101, 1077, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1667, 101, 1078, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1668, 101, 1082, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1669, 101, 1083, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1670, 101, 1084, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1671, 101, 1085, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1672, 101, 1086, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1673, 101, 1087, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1674, 101, 1088, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1675, 101, 1089, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1676, 101, 1090, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1677, 101, 1091, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1678, 101, 1092, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1679, 101, 1237, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1680, 101, 1238, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1681, 101, 1239, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1682, 101, 1240, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1683, 101, 1241, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1684, 101, 1242, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1685, 101, 1243, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1687, 101, 106, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1688, 101, 110, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1689, 101, 111, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1690, 101, 112, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1691, 101, 113, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1692, 101, 114, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1693, 101, 115, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
INSERT INTO "system_role_menu" ("id", "role_id", "menu_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1694, 101, 116, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for system_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS "system_sensitive_word";
CREATE TABLE "system_sensitive_word"
(
    "id"          int8                                        NOT NULL,
    "name"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "description" varchar(512) COLLATE "pg_catalog"."default",
    "tags"        varchar(255) COLLATE "pg_catalog"."default",
    "status"      int2                                        NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_sensitive_word"."id" IS 'Number';
COMMENT
ON COLUMN "system_sensitive_word"."name" IS 'Sensitive words';
COMMENT
ON COLUMN "system_sensitive_word"."description" IS 'Description';
COMMENT
ON COLUMN "system_sensitive_word"."tags" IS 'Tag array';
COMMENT
ON COLUMN "system_sensitive_word"."status" IS 'Status';
COMMENT
ON COLUMN "system_sensitive_word"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_sensitive_word"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_sensitive_word"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_sensitive_word"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_sensitive_word"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_sensitive_word" IS 'Sensitive words';

-- ----------------------------
-- Records of system_sensitive_word
-- ----------------------------
BEGIN;
INSERT INTO "system_sensitive_word" ("id", "name", "description", "tags", "status", "creator", "create_time", "updater",
                                     "update_time", "deleted")
VALUES (3, 'Potatoes', 'Okay', 'Vegetables,SMS', 0, '1', '2022-04-08 21:07:12', '1', '2022-04-09 10:28:14', 0);
INSERT INTO "system_sensitive_word" ("id", "name", "description", "tags", "status", "creator", "create_time", "updater",
                                     "update_time", "deleted")
VALUES (4, 'XXX', NULL, 'SMS', 0, '1', '2022-04-08 21:27:49', '1', '2022-04-08 21:27:49', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS "system_sms_channel";
CREATE TABLE "system_sms_channel"
(
    "id"           int8                                        NOT NULL,
    "signature"    varchar(12) COLLATE "pg_catalog"."default"  NOT NULL,
    "code"         varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "status"       int2                                        NOT NULL,
    "remark"       varchar(255) COLLATE "pg_catalog"."default",
    "api_key"      varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "api_secret"   varchar(128) COLLATE "pg_catalog"."default",
    "callback_url" varchar(255) COLLATE "pg_catalog"."default",
    "creator"      varchar(64) COLLATE "pg_catalog"."default",
    "create_time"  timestamp(6)                                NOT NULL,
    "updater"      varchar(64) COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)                                NOT NULL,
    "deleted"      int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_sms_channel"."id" IS 'Number';
COMMENT
ON COLUMN "system_sms_channel"."signature" IS 'SMS signature';
COMMENT
ON COLUMN "system_sms_channel"."code" IS 'Channel Code';
COMMENT
ON COLUMN "system_sms_channel"."status" IS 'Open status';
COMMENT
ON COLUMN "system_sms_channel"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_sms_channel"."api_key" IS 'SMS API Account';
COMMENT
ON COLUMN "system_sms_channel"."api_secret" IS 'SMS API Secret Key';
COMMENT
ON COLUMN "system_sms_channel"."callback_url" IS 'SMS sending callback URL';
COMMENT
ON COLUMN "system_sms_channel"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_sms_channel"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_sms_channel"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_sms_channel"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_sms_channel"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_sms_channel" IS 'SMS channel';

-- ----------------------------
-- Records of system_sms_channel
-- ----------------------------
BEGIN;
INSERT INTO "system_sms_channel" ("id", "signature", "code", "status", "remark", "api_key", "api_secret",
                                  "callback_url", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (2, 'Ballcat', 'ALIYUN', 0, 'La La La', 'LTAI5tCnKso2uG3kJ5gRav88', 'fGJ5SNXL7P1NHNRmJ7DJaMJGPyE55C', NULL, '',
        '2021-03-31 11:53:10', '1', '2021-04-14 00:08:37', 0);
INSERT INTO "system_sms_channel" ("id", "signature", "code", "status", "remark", "api_key", "api_secret",
                                  "callback_url", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (4, 'Test channel', 'DEBUG_DING_TALK', 0, '123', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859',
        'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2021-04-13 00:23:14', '1',
        '2022-03-27 20:29:49', 0);
INSERT INTO "system_sms_channel" ("id", "signature", "code", "status", "remark", "api_key", "api_secret",
                                  "callback_url", "creator", "create_time", "updater", "update_time", "deleted")
VALUES (6, 'Test Demo', 'DEBUG_DING_TALK', 0, NULL, '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859',
        'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2022-04-10 23:07:59', '1',
        '2022-04-10 23:07:59', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_sms_code
-- ----------------------------
DROP TABLE IF EXISTS "system_sms_code";
CREATE TABLE "system_sms_code"
(
    "id"          int8                                       NOT NULL,
    "mobile"      varchar(11) COLLATE "pg_catalog"."default" NOT NULL,
    "code"        varchar(6) COLLATE "pg_catalog"."default"  NOT NULL,
    "create_ip"   varchar(15) COLLATE "pg_catalog"."default" NOT NULL,
    "scene"       int2                                       NOT NULL,
    "today_index" int2                                       NOT NULL,
    "used"        bool                                       NOT NULL,
    "used_time"   timestamp(6),
    "used_ip"     varchar(255) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                               NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                               NOT NULL,
    "deleted"     int2                                       NOT NULL DEFAULT 0,
    "tenant_id"   int8                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_sms_code"."id" IS 'Number';
COMMENT
ON COLUMN "system_sms_code"."mobile" IS 'Mobile phone number';
COMMENT
ON COLUMN "system_sms_code"."code" IS 'Verification code';
COMMENT
ON COLUMN "system_sms_code"."create_ip" IS 'Create IP';
COMMENT
ON COLUMN "system_sms_code"."scene" IS 'Send scene';
COMMENT
ON COLUMN "system_sms_code"."today_index" IS 'The number of messages sent today';
COMMENT
ON COLUMN "system_sms_code"."used" IS 'Whether to use';
COMMENT
ON COLUMN "system_sms_code"."used_time" IS 'Usage time';
COMMENT
ON COLUMN "system_sms_code"."used_ip" IS 'Use IP';
COMMENT
ON COLUMN "system_sms_code"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_sms_code"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_sms_code"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_sms_code"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_sms_code"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_sms_code"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_sms_code" IS 'Mobile verification code';

-- ----------------------------
-- Records of system_sms_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_sms_log
-- ----------------------------
DROP TABLE IF EXISTS "system_sms_log";
CREATE TABLE "system_sms_log"
(
    "id"               int8                                        NOT NULL,
    "channel_id"       int8                                        NOT NULL,
    "channel_code"     varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "template_id"      int8                                        NOT NULL,
    "template_code"    varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "template_type"    int2                                        NOT NULL,
    "template_content" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "template_params"  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "api_template_id"  varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "mobile"           varchar(11) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_id"          int8,
    "user_type"        int2,
    "send_status"      int2                                        NOT NULL,
    "send_time"        timestamp(6),
    "send_code"        int4,
    "send_msg"         varchar(255) COLLATE "pg_catalog"."default",
    "api_send_code"    varchar(63) COLLATE "pg_catalog"."default",
    "api_send_msg"     varchar(255) COLLATE "pg_catalog"."default",
    "api_request_id"   varchar(255) COLLATE "pg_catalog"."default",
    "api_serial_no"    varchar(255) COLLATE "pg_catalog"."default",
    "receive_status"   int2                                        NOT NULL,
    "receive_time"     timestamp(6),
    "api_receive_code" varchar(63) COLLATE "pg_catalog"."default",
    "api_receive_msg"  varchar(255) COLLATE "pg_catalog"."default",
    "creator"          varchar(64) COLLATE "pg_catalog"."default",
    "create_time"      timestamp(6)                                NOT NULL,
    "updater"          varchar(64) COLLATE "pg_catalog"."default",
    "update_time"      timestamp(6)                                NOT NULL,
    "deleted"          int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_sms_log"."id" IS 'Number';
COMMENT
ON COLUMN "system_sms_log"."channel_id" IS 'SMS channel number';
COMMENT
ON COLUMN "system_sms_log"."channel_code" IS 'SMS channel code';
COMMENT
ON COLUMN "system_sms_log"."template_id" IS 'Template number';
COMMENT
ON COLUMN "system_sms_log"."template_code" IS 'Template encoding';
COMMENT
ON COLUMN "system_sms_log"."template_type" IS 'SMS type';
COMMENT
ON COLUMN "system_sms_log"."template_content" IS 'SMS content';
COMMENT
ON COLUMN "system_sms_log"."template_params" IS 'SMS parameters';
COMMENT
ON COLUMN "system_sms_log"."api_template_id" IS 'SMS API Template number';
COMMENT
ON COLUMN "system_sms_log"."mobile" IS 'Mobile phone number';
COMMENT
ON COLUMN "system_sms_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_sms_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_sms_log"."send_status" IS 'Send status';
COMMENT
ON COLUMN "system_sms_log"."send_time" IS 'Send time';
COMMENT
ON COLUMN "system_sms_log"."send_code" IS 'The encoding of the sent result';
COMMENT
ON COLUMN "system_sms_log"."send_msg" IS 'Reminder for sending results';
COMMENT
ON COLUMN "system_sms_log"."api_send_code" IS 'SMS API The encoding of the sent result';
COMMENT
ON COLUMN "system_sms_log"."api_send_msg" IS 'SMS API Send failed prompt';
COMMENT
ON COLUMN "system_sms_log"."api_request_id" IS 'SMS API Send the only request returned ID';
COMMENT
ON COLUMN "system_sms_log"."api_serial_no" IS 'SMS API Send the returned sequence number';
COMMENT
ON COLUMN "system_sms_log"."receive_status" IS 'Receiving status';
COMMENT
ON COLUMN "system_sms_log"."receive_time" IS 'Receive time';
COMMENT
ON COLUMN "system_sms_log"."api_receive_code" IS 'API The code of the received result';
COMMENT
ON COLUMN "system_sms_log"."api_receive_msg" IS 'API Description of receiving results';
COMMENT
ON COLUMN "system_sms_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_sms_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_sms_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_sms_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_sms_log"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_sms_log" IS 'SMS log';

-- ----------------------------
-- Records of system_sms_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS "system_sms_template";
CREATE TABLE "system_sms_template"
(
    "id"              int8                                        NOT NULL,
    "type"            int2                                        NOT NULL,
    "status"          int2                                        NOT NULL,
    "code"            varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"            varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "content"         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "params"          varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "remark"          varchar(255) COLLATE "pg_catalog"."default",
    "api_template_id" varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "channel_id"      int8                                        NOT NULL,
    "channel_code"    varchar(63) COLLATE "pg_catalog"."default"  NOT NULL,
    "creator"         varchar(64) COLLATE "pg_catalog"."default",
    "create_time"     timestamp(6)                                NOT NULL,
    "updater"         varchar(64) COLLATE "pg_catalog"."default",
    "update_time"     timestamp(6)                                NOT NULL,
    "deleted"         int2                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_sms_template"."id" IS 'Number';
COMMENT
ON COLUMN "system_sms_template"."type" IS 'SMS signature';
COMMENT
ON COLUMN "system_sms_template"."status" IS 'Open status';
COMMENT
ON COLUMN "system_sms_template"."code" IS 'Template encoding';
COMMENT
ON COLUMN "system_sms_template"."name" IS 'Template name';
COMMENT
ON COLUMN "system_sms_template"."content" IS 'Template content';
COMMENT
ON COLUMN "system_sms_template"."params" IS 'Parameter array';
COMMENT
ON COLUMN "system_sms_template"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_sms_template"."api_template_id" IS 'SMS API Template number';
COMMENT
ON COLUMN "system_sms_template"."channel_id" IS 'SMS channel number';
COMMENT
ON COLUMN "system_sms_template"."channel_code" IS 'SMS channel code';
COMMENT
ON COLUMN "system_sms_template"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_sms_template"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_sms_template"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_sms_template"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_sms_template"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_sms_template" IS 'SMS template';

-- ----------------------------
-- Records of system_sms_template
-- ----------------------------
BEGIN;
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (2, 1, 0, 'test_01', 'Test verification code SMS', 'Login in progress{operation}，Your verification code is{code}', '["operation","code"]',
        NULL, '4383920', 1, 'YUN_PIAN', '', '2021-03-31 10:49:38', '1', '2021-04-10 01:22:00', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (3, 1, 0, 'test_02', 'Announcement', 'Your verification code{code}，The verification code5Valid within minutes，Please do not disclose to others！', '["code"]', NULL,
        'SMS_207945135', 2, 'ALIYUN', '', '2021-03-31 11:56:30', '1', '2021-04-10 01:22:02', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (6, 3, 0, 'test-01', 'Test template', 'Hahaha {name}', '["name"]', 'fHahaha', '4383920', 1, 'YUN_PIAN', '1',
        '2021-04-10 01:07:21', '1', '2021-04-10 01:22:05', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (7, 3, 0, 'test-04', 'Test', 'Old Chicken{name}，Awesome{code}', '["name","code"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK',
        '1', '2021-04-13 00:29:53', '1', '2021-04-14 00:30:38', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (8, 1, 0, 'user-sms-login', 'Foreground user SMS login', 'Your verification code is{code}', '["code"]', NULL, '4372216', 1, 'YUN_PIAN',
        '1', '2021-10-11 08:10:00', '1', '2021-10-11 08:10:00', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (9, 2, 0, 'bpm_task_assigned', '【Workflow】The task is assigned',
        'You have received a new to-do task：{processInstanceName}-{taskName}，Applicant：{startUserNickname}，Processing links：{detailUrl}',
        '["processInstanceName","taskName","startUserNickname","detailUrl"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK',
        '1', '2022-01-21 22:31:19', '1', '2022-01-22 00:03:36', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (10, 2, 0, 'bpm_process_instance_reject', '【Workflow】The process was not passed',
        'Your process was not approved：{processInstanceName}，Reason：{reason}，View link：{detailUrl}',
        '["processInstanceName","reason","detailUrl"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1',
        '2022-01-22 00:03:31', '1', '2022-05-01 12:33:14', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (11, 2, 0, 'bpm_process_instance_approve', '【Workflow】The process was passed',
        'Your process has been approved：{processInstanceName}，View link：{detailUrl}', '["processInstanceName","detailUrl"]', NULL,
        'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:04:31', '1', '2022-03-27 20:32:21', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (12, 2, 0, 'demo', 'Demo Template', 'I'm just testing it', '[]', NULL, 'biubiubiu', 6, 'DEBUG_DING_TALK', '1',
        '2022-04-10 23:22:49', '1', '2022-04-10 23:22:49', 0);
INSERT INTO "system_sms_template" ("id", "type", "status", "code", "name", "content", "params", "remark",
                                   "api_template_id", "channel_id", "channel_code", "creator", "create_time", "updater",
                                   "update_time", "deleted")
VALUES (13, 1, 0, 'admin-sms-login', 'Backend user SMS login', 'Your verification code is{code}', '["code"]', '', '4372216', 1, 'YUN_PIAN',
        '1', '2021-10-11 08:10:00', '1', '2021-10-11 08:10:00', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_social_user
-- ----------------------------
DROP TABLE IF EXISTS "system_social_user";
CREATE TABLE "system_social_user"
(
    "id"             numeric(20, 0)                               NOT NULL,
    "type"           int2                                         NOT NULL,
    "openid"         varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "token"          varchar(256) COLLATE "pg_catalog"."default",
    "raw_token_info" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "nickname"       varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "avatar"         varchar(255) COLLATE "pg_catalog"."default",
    "raw_user_info"  varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "code"           varchar(256) COLLATE "pg_catalog"."default"  NOT NULL,
    "state"          varchar(256) COLLATE "pg_catalog"."default",
    "creator"        varchar(64) COLLATE "pg_catalog"."default",
    "create_time"    timestamp(6)                                 NOT NULL,
    "updater"        varchar(64) COLLATE "pg_catalog"."default",
    "update_time"    timestamp(6)                                 NOT NULL,
    "deleted"        int2                                         NOT NULL DEFAULT 0,
    "tenant_id"      int8                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_social_user"."id" IS 'Primary key(Self-increase strategy)';
COMMENT
ON COLUMN "system_social_user"."type" IS 'Type of social platform';
COMMENT
ON COLUMN "system_social_user"."openid" IS 'Social openid';
COMMENT
ON COLUMN "system_social_user"."token" IS 'Social token';
COMMENT
ON COLUMN "system_social_user"."raw_token_info" IS 'Original Token Data，Generally JSON Format';
COMMENT
ON COLUMN "system_social_user"."nickname" IS 'User Nickname';
COMMENT
ON COLUMN "system_social_user"."avatar" IS 'User avatar';
COMMENT
ON COLUMN "system_social_user"."raw_user_info" IS 'Original user data，Generally JSON Format';
COMMENT
ON COLUMN "system_social_user"."code" IS 'The last authentication code';
COMMENT
ON COLUMN "system_social_user"."state" IS 'The last authentication state';
COMMENT
ON COLUMN "system_social_user"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_social_user"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_social_user"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_social_user"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_social_user"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_social_user"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_social_user" IS 'Social User Table';

-- ----------------------------
-- Records of system_social_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS "system_social_user_bind";
CREATE TABLE "system_social_user_bind"
(
    "id"             numeric(20, 0) NOT NULL,
    "user_id"        int8           NOT NULL,
    "user_type"      int2           NOT NULL,
    "social_type"    int2           NOT NULL,
    "social_user_id" int8           NOT NULL,
    "creator"        varchar(64) COLLATE "pg_catalog"."default",
    "create_time"    timestamp(6)   NOT NULL,
    "updater"        varchar(64) COLLATE "pg_catalog"."default",
    "update_time"    timestamp(6)   NOT NULL,
    "deleted"        int2           NOT NULL DEFAULT 0,
    "tenant_id"      int8           NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_social_user_bind"."id" IS 'Primary key(Self-increase strategy)';
COMMENT
ON COLUMN "system_social_user_bind"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_social_user_bind"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_social_user_bind"."social_type" IS 'Type of social platform';
COMMENT
ON COLUMN "system_social_user_bind"."social_user_id" IS 'Social user ID';
COMMENT
ON COLUMN "system_social_user_bind"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_social_user_bind"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_social_user_bind"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_social_user_bind"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_social_user_bind"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_social_user_bind"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_social_user_bind" IS 'Social Binding Table';

-- ----------------------------
-- Records of system_social_user_bind
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS "system_tenant";
CREATE TABLE "system_tenant"
(
    "id"              int8                                       NOT NULL,
    "name"            varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
    "contact_user_id" int8,
    "contact_name"    varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
    "contact_mobile"  varchar(500) COLLATE "pg_catalog"."default",
    "status"          int2                                       NOT NULL,
    "website"          varchar(256) COLLATE "pg_catalog"."default",
    "package_id"      int8                                       NOT NULL,
    "expire_time"     timestamp(6)                               NOT NULL,
    "account_count"   int4                                       NOT NULL,
    "creator"         varchar(64) COLLATE "pg_catalog"."default",
    "create_time"     timestamp(6)                               NOT NULL,
    "updater"         varchar(64) COLLATE "pg_catalog"."default",
    "update_time"     timestamp(6)                               NOT NULL,
    "deleted"         int2                                       NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_tenant"."id" IS 'Tenant Number';
COMMENT
ON COLUMN "system_tenant"."name" IS 'Tenant Name';
COMMENT
ON COLUMN "system_tenant"."contact_user_id" IS 'Contact's user ID';
COMMENT
ON COLUMN "system_tenant"."contact_name" IS 'Contact Person';
COMMENT
ON COLUMN "system_tenant"."contact_mobile" IS 'Contact phone number';
COMMENT
ON COLUMN "system_tenant"."status" IS 'Tenant Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_tenant"."website" IS 'Bind domain name';
COMMENT
ON COLUMN "system_tenant"."package_id" IS 'Tenant package number';
COMMENT
ON COLUMN "system_tenant"."expire_time" IS 'Expiration time';
COMMENT
ON COLUMN "system_tenant"."account_count" IS 'Number of accounts';
COMMENT
ON COLUMN "system_tenant"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_tenant"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_tenant"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_tenant"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_tenant"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_tenant" IS 'Tenant table';

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
BEGIN;
INSERT INTO "system_tenant" ("id", "name", "contact_user_id", "contact_name", "contact_mobile", "status", "website",
                             "package_id", "expire_time", "account_count", "creator", "create_time", "updater",
                             "update_time", "deleted")
VALUES (1, 'Source code', NULL, 'econets', '17321315478', 0, 'https://www.econets.cn', 0, '2099-02-19 17:14:16', 9999, '1',
        '2021-01-05 17:03:47', '1', '2022-02-23 12:15:11', 0);
INSERT INTO "system_tenant" ("id", "name", "contact_user_id", "contact_name", "contact_mobile", "status", "website",
                             "package_id", "expire_time", "account_count", "creator", "create_time", "updater",
                             "update_time", "deleted")
VALUES (121, 'Small tenant', 110, 'Xiao Wang2', '15601691300', 0, 'http://www.econets.cn', 111, '2024-03-11 00:00:00', 20, '1',
        '2022-02-22 00:56:14', '1', '2022-03-19 18:37:20', 0);
INSERT INTO "system_tenant" ("id", "name", "contact_user_id", "contact_name", "contact_mobile", "status", "website",
                             "package_id", "expire_time", "account_count", "creator", "create_time", "updater",
                             "update_time", "deleted")
VALUES (122, 'Test tenant', 113, 'Taro Road', '15601691300', 0, 'https://www.econets.cn', 111, '2022-04-30 00:00:00', 50, '1',
        '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS "system_tenant_package";
CREATE TABLE "system_tenant_package"
(
    "id"          int8                                         NOT NULL,
    "name"        varchar(30) COLLATE "pg_catalog"."default"   NOT NULL,
    "status"      int2                                         NOT NULL,
    "remark"      varchar(256) COLLATE "pg_catalog"."default",
    "menu_ids"    varchar(2048) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_tenant_package"."id" IS 'Package number';
COMMENT
ON COLUMN "system_tenant_package"."name" IS 'Package Name';
COMMENT
ON COLUMN "system_tenant_package"."status" IS 'Tenant Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_tenant_package"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_tenant_package"."menu_ids" IS 'The associated menu number';
COMMENT
ON COLUMN "system_tenant_package"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_tenant_package"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_tenant_package"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_tenant_package"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_tenant_package"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_tenant_package" IS 'Tenant Package List';

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
BEGIN;
INSERT INTO "system_tenant_package" ("id", "name", "status", "remark", "menu_ids", "creator", "create_time", "updater",
                                     "update_time", "deleted")
VALUES (111, 'Regular Package', 0, 'Small function',
        '[1024,1025,1,102,103,104,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023]', '1', '2022-02-22 00:54:00',
        '1', '2022-03-19 18:39:13', 0);
COMMIT;

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS "system_user_post";
CREATE TABLE "system_user_post"
(
    "id"          int8         NOT NULL,
    "user_id"     int8         NOT NULL,
    "post_id"     int8         NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6) NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6) NOT NULL,
    "tenant_id"   int8         NOT NULL,
    "deleted"     int2         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_user_post"."id" IS 'id';
COMMENT
ON COLUMN "system_user_post"."user_id" IS 'UserID';
COMMENT
ON COLUMN "system_user_post"."post_id" IS 'PositionID';
COMMENT
ON COLUMN "system_user_post"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_user_post"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_user_post"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_user_post"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_user_post"."tenant_id" IS 'Tenant Number';
COMMENT
ON COLUMN "system_user_post"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_user_post" IS 'User Position Table';

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
BEGIN;
INSERT INTO "system_user_post" ("id", "user_id", "post_id", "creator", "create_time", "updater", "update_time",
                                "tenant_id", "deleted")
VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', 1, 0);
INSERT INTO "system_user_post" ("id", "user_id", "post_id", "creator", "create_time", "updater", "update_time",
                                "tenant_id", "deleted")
VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', 1, 0);
INSERT INTO "system_user_post" ("id", "user_id", "post_id", "creator", "create_time", "updater", "update_time",
                                "tenant_id", "deleted")
VALUES (114, 114, 3, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS "system_user_role";
CREATE TABLE "system_user_role"
(
    "id"          int8 NOT NULL,
    "user_id"     int8 NOT NULL,
    "role_id"     int8 NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6),
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6),
    "deleted"     int2 NOT NULL DEFAULT 0,
    "tenant_id"   int8 NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_user_role"."id" IS 'Self-increment number';
COMMENT
ON COLUMN "system_user_role"."user_id" IS 'UserID';
COMMENT
ON COLUMN "system_user_role"."role_id" IS 'RoleID';
COMMENT
ON COLUMN "system_user_role"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_user_role"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_user_role"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_user_role"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_user_role"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_user_role"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_user_role" IS 'User and role association table';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
BEGIN;
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (7, 104, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (11, 107, 106, '1', '2022-02-20 22:59:33', '1', '2022-02-20 22:59:33', 0, 118);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (12, 108, 107, '1', '2022-02-20 23:00:50', '1', '2022-02-20 23:00:50', 0, 119);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (13, 109, 108, '1', '2022-02-20 23:11:50', '1', '2022-02-20 23:11:50', 0, 120);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', 0, 121);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', 0, 121);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', 0, 122);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (17, 114, 101, '1', '2022-03-19 21:51:13', '1', '2022-03-19 21:51:13', 0, 1);
INSERT INTO "system_user_role" ("id", "user_id", "role_id", "creator", "create_time", "updater", "update_time",
                                "deleted", "tenant_id")
VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS "system_users";
CREATE TABLE "system_users"
(
    "id"          int8                                        NOT NULL,
    "username"    varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"    varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "nickname"    varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "remark"      varchar(500) COLLATE "pg_catalog"."default",
    "dept_id"     int8,
    "post_ids"    varchar(255) COLLATE "pg_catalog"."default",
    "email"       varchar(50) COLLATE "pg_catalog"."default",
    "mobile"      varchar(11) COLLATE "pg_catalog"."default",
    "sex"         int2,
    "avatar"      varchar(100) COLLATE "pg_catalog"."default",
    "status"      int2                                        NOT NULL,
    "login_ip"    varchar(50) COLLATE "pg_catalog"."default",
    "login_date"  timestamp(6),
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL DEFAULT 0,
    "tenant_id"   int8                                        NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_users"."id" IS 'UserID';
COMMENT
ON COLUMN "system_users"."username" IS 'User Account';
COMMENT
ON COLUMN "system_users"."password" IS 'Password';
COMMENT
ON COLUMN "system_users"."nickname" IS 'User Nickname';
COMMENT
ON COLUMN "system_users"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_users"."dept_id" IS 'DepartmentID';
COMMENT
ON COLUMN "system_users"."post_ids" IS 'Position number array';
COMMENT
ON COLUMN "system_users"."email" IS 'User mailbox';
COMMENT
ON COLUMN "system_users"."mobile" IS 'Mobile phone number';
COMMENT
ON COLUMN "system_users"."sex" IS 'User gender';
COMMENT
ON COLUMN "system_users"."avatar" IS 'Avatar address';
COMMENT
ON COLUMN "system_users"."status" IS 'Account Status（0Normal 1Disable）';
COMMENT
ON COLUMN "system_users"."login_ip" IS 'Last loginIP';
COMMENT
ON COLUMN "system_users"."login_date" IS 'Last login time';
COMMENT
ON COLUMN "system_users"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_users"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_users"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_users"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_users"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_users"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_users" IS 'User Information Table';

-- ----------------------------
-- Records of system_users
-- ----------------------------
BEGIN;
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (100, 'blossom', '$2a$10$11U48RhyJ5pSBYWSn12AD./ld671.ycSzJHbyrtpeoMeYiw31eo8a', 'Taro Road', 'Don't scare me', 104, '[1]',
        'blossom@econets.cn', '15601691300', 1, '', 1, '', NULL, '', '2021-01-07 09:07:17', '104', '2021-12-16 09:26:10',
        0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (103, 'yuanma', '$2a$10$wWoPT7sqriM2O1YXRL.je.GiL538OR6ZTN8aQZr9JAGdnpCH2tpYe', 'Source code', NULL, 106, NULL,
        'yuanma@econets.cn', '15601701300', 0, '', 0, '127.0.0.1', '2022-01-18 00:33:40', '', '2021-01-13 23:50:35',
        NULL, '2022-01-18 00:33:40', 0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (104, 'test', '$2a$10$e5RpuDCC0GYSt0Hvd2.CjujIXwgGct4SnXi6dVGxdgFsnqgEryk5a', 'Test number', NULL, 107, '[]',
        '111@qq.com', '15601691200', 1, '', 0, '127.0.0.1', '2022-03-19 21:46:19', '', '2021-01-21 02:13:53', NULL,
        '2022-03-19 21:46:19', 0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (107, 'admin107', '$2a$10$dYOOBKMO93v/.ReCqzyFg.o67Tqk.bbc2bhrpyBGkIw9aypCtr2pm', 'econets', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 22:59:33', '1', '2022-02-27 08:26:51', 0, 118);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (108, 'admin108', '$2a$10$y6mfvKoNYL1GXWak8nYwVOH.kCWqjactkzdoIDgiKl93WN3Ejg.Lu', 'econets', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 23:00:50', '1', '2022-02-27 08:26:53', 0, 119);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (109, 'admin109', '$2a$10$JAqvH0tEc0I7dfDVBI7zyuB4E3j.uH6daIjV53.vUS6PknFkDJkuK', 'econets', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 23:11:50', '1', '2022-02-27 08:26:56', 0, 120);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (110, 'admin110', '$2a$10$qYxoXs0ogPHgYllyEneYde9xcCW5hZgukrxeXZ9lmLhKse8TK6IwW', 'Xiao Wang', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '127.0.0.1', '2022-02-23 19:36:28', '1', '2022-02-22 00:56:14', NULL,
        '2022-02-27 08:26:59', 0, 121);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (111, 'test', '$2a$10$mExveopHUx9Q4QiLtAzhDeH3n4/QlNLzEsM4AqgxKrU.ciUZDXZCy', 'Test user', NULL, NULL, '[]', '',
        '', 0, '', 0, '', NULL, '110', '2022-02-23 13:14:33', '110', '2022-02-23 13:14:33', 0, 121);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (112, 'newobject', '$2a$10$jh5MsR.ud/gKe3mVeUp5t.nEXGDSmHyv5OYjWQwHO8wlGmMSI9Twy', 'New object', NULL, NULL, '[]',
        '', '', 0, '', 0, '', NULL, '1', '2022-02-23 19:08:03', '1', '2022-02-23 19:08:03', 0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (113, 'aoteman', '$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO', 'Taro Road', NULL, NULL, NULL, '',
        '15601691300', 0, '', 0, '127.0.0.1', '2022-03-19 18:38:51', '1', '2022-03-07 21:37:58', NULL,
        '2022-03-19 18:38:51', 0, 122);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (114, 'hrmgr', '$2a$10$TR4eybBioGRhBmDBWkqWLO6NIh3mzYa8KBKDDB5woiGYFVlRAi.fu', 'hr Little sister', NULL, NULL, '[3]',
        '', '', 0, '', 0, '127.0.0.1', '2022-03-19 22:15:43', '1', '2022-03-19 21:50:58', NULL, '2022-03-19 22:15:43',
        0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (115, 'aotemane', '$2a$10$/WCwGHu1eq0wOVDd/u8HweJ0gJCHyLS6T7ndCqI8UXZAQom1etk2e', '1', '11', 100, '[]', '', '',
        0, '', 0, '', NULL, '1', '2022-04-30 02:55:43', '1', '2022-04-30 02:55:43', 0, 1);
INSERT INTO "system_users" ("id", "username", "password", "nickname", "remark", "dept_id", "post_ids", "email",
                            "mobile", "sex", "avatar", "status", "login_ip", "login_date", "creator", "create_time",
                            "updater", "update_time", "deleted", "tenant_id")
VALUES (1, 'admin', '$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO', 'Source code', 'Administrator', 103, '[1]',
        'aoteman@126.com', '15612345678', 1, 'http://test.blossom.econets.cn/48934f2f-92d4-4250-b917-d10d2b262c6a', 0,
        '127.0.0.1', '2022-05-25 23:44:33.003', 'admin', '2021-01-05 17:03:47', NULL, '2022-05-25 23:44:33.003', 0, 1);
COMMIT;



-- ----------------------------
-- Table structure for system_mail_account
-- ----------------------------
DROP TABLE IF EXISTS "system_mail_account";
CREATE TABLE "system_mail_account"
(
    "id"          int8                                        NOT NULL,
    "mail"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "username"    varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "password"    varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "host"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "port"        int4                                        NOT NULL,
    "ssl_enable"  varchar(1) COLLATE "pg_catalog"."default"   NOT NULL,
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                NOT NULL,
    "deleted"     int2                                        NOT NULL
)
;
COMMENT
ON COLUMN "system_mail_account"."id" IS 'Primary key';
COMMENT
ON COLUMN "system_mail_account"."mail" IS 'Mailbox';
COMMENT
ON COLUMN "system_mail_account"."username" IS 'Username';
COMMENT
ON COLUMN "system_mail_account"."password" IS 'Password';
COMMENT
ON COLUMN "system_mail_account"."host" IS 'SMTP Server domain name';
COMMENT
ON COLUMN "system_mail_account"."port" IS 'SMTP Server port';
COMMENT
ON COLUMN "system_mail_account"."ssl_enable" IS 'Is it enabled? SSL';
COMMENT
ON COLUMN "system_mail_account"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_mail_account"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_mail_account"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_mail_account"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_mail_account"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_mail_account" IS 'Email Account Table';


-- ----------------------------
-- Table structure for system_mail_log
-- ----------------------------
DROP TABLE IF EXISTS "system_mail_log";
CREATE TABLE "system_mail_log"
(
    "id"                int8                                          NOT NULL,
    "user_id"           int8,
    "user_type"         int2,
    "to_mail"           varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "account_id"        int8                                          NOT NULL,
    "from_mail"         varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "template_id"       int8                                          NOT NULL,
    "template_code"     varchar(63) COLLATE "pg_catalog"."default"    NOT NULL,
    "template_nickname" varchar(255) COLLATE "pg_catalog"."default",
    "template_title"    varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "template_content"  varchar(10240) COLLATE "pg_catalog"."default" NOT NULL,
    "template_params"   varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "send_status"       int2                                          NOT NULL,
    "send_time"         timestamp(6),
    "send_message_id"   varchar(255) COLLATE "pg_catalog"."default",
    "send_exception"    varchar(4096) COLLATE "pg_catalog"."default",
    "creator"           varchar(64) COLLATE "pg_catalog"."default",
    "create_time"       timestamp(6)                                  NOT NULL,
    "updater"           varchar(64) COLLATE "pg_catalog"."default",
    "update_time"       timestamp(6)                                  NOT NULL,
    "deleted"           int2                                          NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_mail_log"."id" IS 'Number';
COMMENT
ON COLUMN "system_mail_log"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_mail_log"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_mail_log"."to_mail" IS 'Receiving email address';
COMMENT
ON COLUMN "system_mail_log"."account_id" IS 'Email account number';
COMMENT
ON COLUMN "system_mail_log"."from_mail" IS 'Send email address';
COMMENT
ON COLUMN "system_mail_log"."template_id" IS 'Template number';
COMMENT
ON COLUMN "system_mail_log"."template_code" IS 'Template encoding';
COMMENT
ON COLUMN "system_mail_log"."template_nickname" IS 'Template sender name';
COMMENT
ON COLUMN "system_mail_log"."template_title" IS 'Email title';
COMMENT
ON COLUMN "system_mail_log"."template_content" IS 'Email content';
COMMENT
ON COLUMN "system_mail_log"."template_params" IS 'Mail Parameters';
COMMENT
ON COLUMN "system_mail_log"."send_status" IS 'Send status';
COMMENT
ON COLUMN "system_mail_log"."send_time" IS 'Send time';
COMMENT
ON COLUMN "system_mail_log"."send_message_id" IS 'Send return message ID';
COMMENT
ON COLUMN "system_mail_log"."send_exception" IS 'Sending exception';
COMMENT
ON COLUMN "system_mail_log"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_mail_log"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_mail_log"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_mail_log"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_mail_log"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_mail_log" IS 'Mail log table';


-- ----------------------------
-- Table structure for system_mail_template
-- ----------------------------
DROP TABLE IF EXISTS "system_mail_template";
CREATE TABLE "system_mail_template"
(
    "id"          int8                                          NOT NULL,
    "name"        varchar(63) COLLATE "pg_catalog"."default"    NOT NULL,
    "code"        varchar(63) COLLATE "pg_catalog"."default"    NOT NULL,
    "account_id"  int8                                          NOT NULL,
    "nickname"    varchar(255) COLLATE "pg_catalog"."default",
    "title"       varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "content"     varchar(10240) COLLATE "pg_catalog"."default" NOT NULL,
    "params"      varchar(255) COLLATE "pg_catalog"."default"   NOT NULL,
    "status"      int2                                          NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                  NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                  NOT NULL,
    "deleted"     int2                                          NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_mail_template"."id" IS 'Number';
COMMENT
ON COLUMN "system_mail_template"."name" IS 'Template name';
COMMENT
ON COLUMN "system_mail_template"."code" IS 'Template encoding';
COMMENT
ON COLUMN "system_mail_template"."account_id" IS 'The email account number to send the message to';
COMMENT
ON COLUMN "system_mail_template"."nickname" IS 'Sender's name';
COMMENT
ON COLUMN "system_mail_template"."title" IS 'Template title';
COMMENT
ON COLUMN "system_mail_template"."content" IS 'Template content';
COMMENT
ON COLUMN "system_mail_template"."params" IS 'Parameter array';
COMMENT
ON COLUMN "system_mail_template"."status" IS 'Open status';
COMMENT
ON COLUMN "system_mail_template"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_mail_template"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_mail_template"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_mail_template"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_mail_template"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_mail_template"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_mail_template" IS 'Mail Template Table';



-- ----------------------------
-- Table structure for system_notify_message
-- ----------------------------
DROP TABLE IF EXISTS "system_notify_message";
CREATE TABLE "system_notify_message"
(
    "id"                int8                                         NOT NULL,
    "user_id"           int8                                         NOT NULL,
    "user_type"         int2                                         NOT NULL,
    "template_id"       int8                                         NOT NULL,
    "template_code"     varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "template_nickname" varchar(63) COLLATE "pg_catalog"."default"   NOT NULL,
    "template_content"  varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "template_type"     int4                                         NOT NULL,
    "template_params"   varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "read_status"       bool                                         NOT NULL DEFAULT false,
    "read_time"         timestamp(6),
    "creator"           varchar(64) COLLATE "pg_catalog"."default",
    "create_time"       timestamp(6)                                 NOT NULL,
    "updater"           varchar(64) COLLATE "pg_catalog"."default",
    "update_time"       timestamp(6)                                 NOT NULL,
    "deleted"           int2                                         NOT NULL DEFAULT 0,
    "tenant_id"         int8                                         NOT NULL
)
;
COMMENT
ON COLUMN "system_notify_message"."id" IS 'UserID';
COMMENT
ON COLUMN "system_notify_message"."user_id" IS 'Userid';
COMMENT
ON COLUMN "system_notify_message"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_notify_message"."template_id" IS 'Template number';
COMMENT
ON COLUMN "system_notify_message"."template_code" IS 'Template encoding';
COMMENT
ON COLUMN "system_notify_message"."template_nickname" IS 'Template sender name';
COMMENT
ON COLUMN "system_notify_message"."template_content" IS 'Template content';
COMMENT
ON COLUMN "system_notify_message"."template_type" IS 'Template type';
COMMENT
ON COLUMN "system_notify_message"."template_params" IS 'Template parameters';
COMMENT
ON COLUMN "system_notify_message"."read_status" IS 'Has it been read?';
COMMENT
ON COLUMN "system_notify_message"."read_time" IS 'Reading time';
COMMENT
ON COLUMN "system_notify_message"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_notify_message"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_notify_message"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_notify_message"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_notify_message"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_notify_message"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_notify_message" IS 'Internal message table';



-- ----------------------------
-- Table structure for system_notify_template
-- ----------------------------
DROP TABLE IF EXISTS "system_notify_template";
CREATE TABLE "system_notify_template"
(
    "id"          int8                                         NOT NULL,
    "name"        varchar(63) COLLATE "pg_catalog"."default"   NOT NULL,
    "code"        varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "nickname"    varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "content"     varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "type"        int2                                         NOT NULL,
    "params"      varchar(255) COLLATE "pg_catalog"."default",
    "status"      int2                                         NOT NULL,
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "creator"     varchar(64) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6)                                 NOT NULL,
    "updater"     varchar(64) COLLATE "pg_catalog"."default",
    "update_time" timestamp(6)                                 NOT NULL,
    "deleted"     int2                                         NOT NULL DEFAULT 0
)
;
COMMENT
ON COLUMN "system_notify_template"."id" IS 'Primary key';
COMMENT
ON COLUMN "system_notify_template"."name" IS 'Template name';
COMMENT
ON COLUMN "system_notify_template"."code" IS 'Template code';
COMMENT
ON COLUMN "system_notify_template"."nickname" IS 'Sender's name';
COMMENT
ON COLUMN "system_notify_template"."content" IS 'Template content';
COMMENT
ON COLUMN "system_notify_template"."type" IS 'Type';
COMMENT
ON COLUMN "system_notify_template"."params" IS 'Parameter array';
COMMENT
ON COLUMN "system_notify_template"."status" IS 'Status';
COMMENT
ON COLUMN "system_notify_template"."remark" IS 'Remarks';
COMMENT
ON COLUMN "system_notify_template"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_notify_template"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_notify_template"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_notify_template"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_notify_template"."deleted" IS 'Delete';
COMMENT
ON TABLE "system_notify_template" IS 'Internal letter template table';



-- ----------------------------
-- Table structure for system_user_session
-- ----------------------------
DROP TABLE IF EXISTS "system_user_session";
CREATE TABLE "system_user_session"
(
    "id"              int8                                        NOT NULL,
    "token"           varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_id"         int8                                        NOT NULL,
    "user_type"       int2                                        NOT NULL,
    "session_timeout" timestamp(6)                                NOT NULL,
    "username"        varchar(30) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_ip"         varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "user_agent"      varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"         varchar(64) COLLATE "pg_catalog"."default",
    "create_time"     timestamp(6)                                NOT NULL,
    "updater"         varchar(64) COLLATE "pg_catalog"."default",
    "update_time"     timestamp(6)                                NOT NULL,
    "deleted"         int2                                        NOT NULL DEFAULT 0,
    "tenant_id"       int8                                        NOT NULL
)
;
COMMENT
ON COLUMN "system_user_session"."id" IS 'Number';
COMMENT
ON COLUMN "system_user_session"."token" IS 'Session number';
COMMENT
ON COLUMN "system_user_session"."user_id" IS 'User Number';
COMMENT
ON COLUMN "system_user_session"."user_type" IS 'User Type';
COMMENT
ON COLUMN "system_user_session"."session_timeout" IS 'Session timeout';
COMMENT
ON COLUMN "system_user_session"."username" IS 'User Account';
COMMENT
ON COLUMN "system_user_session"."user_ip" IS 'User IP';
COMMENT
ON COLUMN "system_user_session"."user_agent" IS 'Browser UA';
COMMENT
ON COLUMN "system_user_session"."creator" IS 'Creator';
COMMENT
ON COLUMN "system_user_session"."create_time" IS 'Creation time';
COMMENT
ON COLUMN "system_user_session"."updater" IS 'Updater';
COMMENT
ON COLUMN "system_user_session"."update_time" IS 'Update time';
COMMENT
ON COLUMN "system_user_session"."deleted" IS 'Delete';
COMMENT
ON COLUMN "system_user_session"."tenant_id" IS 'Tenant Number';
COMMENT
ON TABLE "system_user_session" IS 'User online Session';





-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"bpm_oa_leave_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"bpm_task_assign_rule_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"infra_api_access_log_seq"', 537, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"infra_api_error_log_seq"', 73, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"infra_job_log_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"infra_job_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_error_code_seq"', 186, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_login_log_seq"', 23, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_oauth2_access_token_seq"', 11, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_oauth2_approve_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_oauth2_client_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_oauth2_code_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_oauth2_refresh_token_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_operate_log_seq"', 44, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"system_sms_log_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table bpm_form
-- ----------------------------
ALTER TABLE "bpm_form"
    ADD CONSTRAINT "bpm_form_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_oa_leave
-- ----------------------------
ALTER TABLE "bpm_oa_leave"
    ADD CONSTRAINT "bpm_oa_leave_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_process_definition_ext
-- ----------------------------
ALTER TABLE "bpm_process_definition_ext"
    ADD CONSTRAINT "bpm_process_definition_ext_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_process_instance_ext
-- ----------------------------
ALTER TABLE "bpm_process_instance_ext"
    ADD CONSTRAINT "bpm_process_instance_ext_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_task_assign_rule
-- ----------------------------
ALTER TABLE "bpm_task_assign_rule"
    ADD CONSTRAINT "bpm_task_assign_rule_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_task_ext
-- ----------------------------
ALTER TABLE "bpm_task_ext"
    ADD CONSTRAINT "bpm_task_ext_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table bpm_user_group
-- ----------------------------
ALTER TABLE "bpm_user_group"
    ADD CONSTRAINT "bpm_user_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_api_access_log
-- ----------------------------
ALTER TABLE "infra_api_access_log"
    ADD CONSTRAINT "infra_api_access_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_api_error_log
-- ----------------------------
ALTER TABLE "infra_api_error_log"
    ADD CONSTRAINT "infra_api_error_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_codegen_column
-- ----------------------------
ALTER TABLE "infra_codegen_column"
    ADD CONSTRAINT "infra_codegen_column_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_codegen_table
-- ----------------------------
ALTER TABLE "infra_codegen_table"
    ADD CONSTRAINT "infra_codegen_table_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_config
-- ----------------------------
ALTER TABLE "infra_config"
    ADD CONSTRAINT "infra_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_data_source_config
-- ----------------------------
ALTER TABLE "infra_data_source_config"
    ADD CONSTRAINT "infra_data_source_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_file
-- ----------------------------
ALTER TABLE "infra_file"
    ADD CONSTRAINT "infra_file_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_file_config
-- ----------------------------
ALTER TABLE "infra_file_config"
    ADD CONSTRAINT "infra_file_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_file_content
-- ----------------------------
ALTER TABLE "infra_file_content"
    ADD CONSTRAINT "infra_file_content_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_job
-- ----------------------------
ALTER TABLE "infra_job"
    ADD CONSTRAINT "infra_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_job_log
-- ----------------------------
ALTER TABLE "infra_job_log"
    ADD CONSTRAINT "infra_job_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table infra_test_demo
-- ----------------------------
ALTER TABLE "infra_test_demo"
    ADD CONSTRAINT "infra_test_demo_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table member_user
-- ----------------------------
CREATE UNIQUE INDEX "uk_mobile" ON "member_user" USING btree (
    "mobile" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
COMMENT
ON INDEX "uk_mobile" IS 'Mobile phone number';

-- ----------------------------
-- Primary Key structure for table member_user
-- ----------------------------
ALTER TABLE "member_user"
    ADD CONSTRAINT "member_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_app
-- ----------------------------
ALTER TABLE "pay_app"
    ADD CONSTRAINT "pay_app_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_channel
-- ----------------------------
ALTER TABLE "pay_channel"
    ADD CONSTRAINT "pay_channel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_merchant
-- ----------------------------
ALTER TABLE "pay_merchant"
    ADD CONSTRAINT "pay_merchant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_notify_log
-- ----------------------------
ALTER TABLE "pay_notify_log"
    ADD CONSTRAINT "pay_notify_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_notify_task
-- ----------------------------
ALTER TABLE "pay_notify_task"
    ADD CONSTRAINT "pay_notify_task_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_order
-- ----------------------------
ALTER TABLE "pay_order"
    ADD CONSTRAINT "pay_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_order_extension
-- ----------------------------
ALTER TABLE "pay_order_extension"
    ADD CONSTRAINT "pay_order_extension_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pay_refund
-- ----------------------------
ALTER TABLE "pay_refund"
    ADD CONSTRAINT "pay_refund_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "qrtz_blob_triggers"
    ADD CONSTRAINT "qrtz_blob_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_calendars
-- ----------------------------
ALTER TABLE "qrtz_calendars"
    ADD CONSTRAINT "qrtz_calendars_pkey" PRIMARY KEY ("sched_name", "calendar_name");

-- ----------------------------
-- Primary Key structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "qrtz_cron_triggers"
    ADD CONSTRAINT "qrtz_cron_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_fired_triggers
-- ----------------------------
CREATE INDEX "idx_qrtz_ft_inst_job_req_rcvry" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "instance_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "requests_recovery" "pg_catalog"."bool_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_ft_j_g" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_ft_jg" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_ft_t_g" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_ft_tg" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_ft_trig_inst_name" ON "qrtz_fired_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "instance_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table qrtz_fired_triggers
-- ----------------------------
ALTER TABLE "qrtz_fired_triggers"
    ADD CONSTRAINT "qrtz_fired_triggers_pkey" PRIMARY KEY ("sched_name", "entry_id");

-- ----------------------------
-- Indexes structure for table qrtz_job_details
-- ----------------------------
CREATE INDEX "idx_qrtz_j_grp" ON "qrtz_job_details" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_j_req_recovery" ON "qrtz_job_details" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "requests_recovery" "pg_catalog"."bool_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table qrtz_job_details
-- ----------------------------
ALTER TABLE "qrtz_job_details"
    ADD CONSTRAINT "qrtz_job_details_pkey" PRIMARY KEY ("sched_name", "job_name", "job_group");

-- ----------------------------
-- Primary Key structure for table qrtz_locks
-- ----------------------------
ALTER TABLE "qrtz_locks"
    ADD CONSTRAINT "qrtz_locks_pkey" PRIMARY KEY ("sched_name", "lock_name");

-- ----------------------------
-- Primary Key structure for table qrtz_paused_trigger_grps
-- ----------------------------
ALTER TABLE "qrtz_paused_trigger_grps"
    ADD CONSTRAINT "qrtz_paused_trigger_grps_pkey" PRIMARY KEY ("sched_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_scheduler_state
-- ----------------------------
ALTER TABLE "qrtz_scheduler_state"
    ADD CONSTRAINT "qrtz_scheduler_state_pkey" PRIMARY KEY ("sched_name", "instance_name");

-- ----------------------------
-- Primary Key structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "qrtz_simple_triggers"
    ADD CONSTRAINT "qrtz_simple_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "qrtz_simprop_triggers"
    ADD CONSTRAINT "qrtz_simprop_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_triggers
-- ----------------------------
CREATE INDEX "idx_qrtz_t_c" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "calendar_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_g" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_j" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_jg" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_n_g_state" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_n_state" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_next_fire_time" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_nft_misfire" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
    "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_nft_st" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_nft_st_misfire" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
    "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_nft_st_misfire_grp" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
    "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_qrtz_t_state" ON "qrtz_triggers" USING btree (
    "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "qrtz_triggers"
    ADD CONSTRAINT "qrtz_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table system_dept
-- ----------------------------
ALTER TABLE "system_dept"
    ADD CONSTRAINT "system_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_dict_data
-- ----------------------------
ALTER TABLE "system_dict_data"
    ADD CONSTRAINT "system_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table system_dict_type
-- ----------------------------
CREATE UNIQUE INDEX "dict_type" ON "system_dict_type" USING btree (
    "type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table system_dict_type
-- ----------------------------
ALTER TABLE "system_dict_type"
    ADD CONSTRAINT "system_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_error_code
-- ----------------------------
ALTER TABLE "system_error_code"
    ADD CONSTRAINT "system_error_code_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_login_log
-- ----------------------------
ALTER TABLE "system_login_log"
    ADD CONSTRAINT "system_login_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_menu
-- ----------------------------
ALTER TABLE "system_menu"
    ADD CONSTRAINT "system_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_notice
-- ----------------------------
ALTER TABLE "system_notice"
    ADD CONSTRAINT "system_notice_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_oauth2_access_token
-- ----------------------------
ALTER TABLE "system_oauth2_access_token"
    ADD CONSTRAINT "system_oauth2_access_token_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_oauth2_approve
-- ----------------------------
ALTER TABLE "system_oauth2_approve"
    ADD CONSTRAINT "system_oauth2_approve_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_oauth2_client
-- ----------------------------
ALTER TABLE "system_oauth2_client"
    ADD CONSTRAINT "system_oauth2_client_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_oauth2_code
-- ----------------------------
ALTER TABLE "system_oauth2_code"
    ADD CONSTRAINT "system_oauth2_code_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_oauth2_refresh_token
-- ----------------------------
ALTER TABLE "system_oauth2_refresh_token"
    ADD CONSTRAINT "system_oauth2_refresh_token_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_operate_log
-- ----------------------------
ALTER TABLE "system_operate_log"
    ADD CONSTRAINT "system_operate_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_post
-- ----------------------------
ALTER TABLE "system_post"
    ADD CONSTRAINT "system_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_role
-- ----------------------------
ALTER TABLE "system_role"
    ADD CONSTRAINT "system_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_role_menu
-- ----------------------------
ALTER TABLE "system_role_menu"
    ADD CONSTRAINT "system_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_sensitive_word
-- ----------------------------
ALTER TABLE "system_sensitive_word"
    ADD CONSTRAINT "system_sensitive_word_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_sms_channel
-- ----------------------------
ALTER TABLE "system_sms_channel"
    ADD CONSTRAINT "system_sms_channel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table system_sms_code
-- ----------------------------
CREATE INDEX "idx_mobile" ON "system_sms_code" USING btree (
    "mobile" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
COMMENT
ON INDEX "idx_mobile" IS 'Mobile phone number';

-- ----------------------------
-- Primary Key structure for table system_sms_code
-- ----------------------------
ALTER TABLE "system_sms_code"
    ADD CONSTRAINT "system_sms_code_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_sms_log
-- ----------------------------
ALTER TABLE "system_sms_log"
    ADD CONSTRAINT "system_sms_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_sms_template
-- ----------------------------
ALTER TABLE "system_sms_template"
    ADD CONSTRAINT "system_sms_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_social_user
-- ----------------------------
ALTER TABLE "system_social_user"
    ADD CONSTRAINT "system_social_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_social_user_bind
-- ----------------------------
ALTER TABLE "system_social_user_bind"
    ADD CONSTRAINT "system_social_user_bind_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_tenant
-- ----------------------------
ALTER TABLE "system_tenant"
    ADD CONSTRAINT "system_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_tenant_package
-- ----------------------------
ALTER TABLE "system_tenant_package"
    ADD CONSTRAINT "system_tenant_package_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_user_post
-- ----------------------------
ALTER TABLE "system_user_post"
    ADD CONSTRAINT "system_user_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system_user_role
-- ----------------------------
ALTER TABLE "system_user_role"
    ADD CONSTRAINT "system_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table system_users
-- ----------------------------
CREATE UNIQUE INDEX "idx_username" ON "system_users" USING btree (
    "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "update_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST,
    "tenant_id" "pg_catalog"."int8_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table system_users
-- ----------------------------
ALTER TABLE "system_users"
    ADD CONSTRAINT "system_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "qrtz_blob_triggers"
    ADD CONSTRAINT "qrtz_blob_triggers_sched_name_trigger_name_trigger_group_fkey" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "qrtz_cron_triggers"
    ADD CONSTRAINT "qrtz_cron_triggers_sched_name_trigger_name_trigger_group_fkey" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "qrtz_simple_triggers"
    ADD CONSTRAINT "qrtz_simple_triggers_sched_name_trigger_name_trigger_group_fkey" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "qrtz_simprop_triggers"
    ADD CONSTRAINT "qrtz_simprop_triggers_sched_name_trigger_name_trigger_grou_fkey" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "qrtz_triggers"
    ADD CONSTRAINT "qrtz_triggers_sched_name_job_name_job_group_fkey" FOREIGN KEY ("sched_name", "job_name", "job_group") REFERENCES "qrtz_job_details" ("sched_name", "job_name", "job_group") ON DELETE NO ACTION ON UPDATE NO ACTION;
