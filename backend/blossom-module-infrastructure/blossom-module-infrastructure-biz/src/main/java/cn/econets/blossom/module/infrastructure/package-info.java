/**
 * infrastructure Module，Mainly provides two capabilities：
 * 1. We are responsible for the operation and management of infrastructure，Supporting the general and core business of the upper layer。 For example：Management of scheduled tasks、Server information, etc.
 * 2. R&D Tools，Improve R&D efficiency and quality。 For example：Code generator、Interface documentation, etc.
 *
 * 1. Controller URL：With /infra/ Beginning，Avoid and others Module Conflict
 * 2. DataObject Table name：With infra_ Beginning，Convenient to distinguish in the database
 */
package cn.econets.blossom.module.infrastructure;
