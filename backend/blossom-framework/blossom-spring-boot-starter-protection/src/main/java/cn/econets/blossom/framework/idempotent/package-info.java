/**
 * Idempotent component，Reference https://github.com/it4alla/idempotent Project Implementation
 * The implementation principle is，Methods with the same parameters，For a period of time，Can only be executed once。In this way，Ensure idempotence。
 *
 * Usage scenario：For example，The user quickly double-clicked a button，The button is not disabled on the front end，Resulting in sending two duplicate requests。
 *
 * Japanese it4alla/idempotent Differences in components，It is mainly reflected in two points：
 *  1. We removed it @Idempotent Annotated delKey Properties。The reason is，In essence delKey for true Time，It implements the distributed lock capability
 * At this time，We prefer to use Lock4j Component。In principle，A component provides only a single capability。
 *  2. Taking into account the universality of components，We are not like it4alla/idempotent Use the same components as above Redisson RMap Structure，Instead, use it directly Redis of String Data format。
 */
package cn.econets.blossom.framework.idempotent;
