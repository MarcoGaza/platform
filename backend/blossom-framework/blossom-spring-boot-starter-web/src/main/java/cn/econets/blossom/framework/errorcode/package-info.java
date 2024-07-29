/**
 * Error code ErrorCode Automatic configuration function，Provides the following functions：
 *
 * 1. Remote read：When the project starts，From system-service Service，Read from database ErrorCode Error code，Make error code configurable；
 * 2. Automatic Update：Administrators modify the database in the management backend ErrorCode Error code，Projects automatically from system-service Service loads the latest ErrorCode Error code；
 * 3. Automatically write：When the project starts，Write the local error code of the project to system-server In service，Convenient for administrators to edit in the management background；
 */
package cn.econets.blossom.framework.errorcode;
