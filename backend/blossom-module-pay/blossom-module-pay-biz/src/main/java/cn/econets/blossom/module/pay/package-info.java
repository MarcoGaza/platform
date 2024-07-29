/**
 * pay Module，We open payment business，Provide payment capability for services。
 * For example：Merchant、Application、Payment、Refunds, etc.
 *
 * 1. Controller URL：With /pay/ Beginning，Avoid and others Module Conflict
 * 2. DataObject Table name：With pay_ Beginning，Convenient to distinguish in the database
 *
 * Attention，Because Pay Modules and Trade Module，Easy to have duplicate names，So all class names are loaded Pay Prefix~
 */
package cn.econets.blossom.module.pay;
