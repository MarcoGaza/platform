<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.7.17-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Vue-3.2-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/github/license/ximuV/econets-vue"/>
</p>

## Newbies must read

Test Account:admin
Test password:123456

## Platform Introduction

Rapid Development Platform，All open source，Individuals and enterprises can 100% Free to use。

* Java Backend：`master` Branch is JDK 8 + Spring Boot 2.7.17，`2.x` Branch is JDK21 + Spring Boot 3.2.0
* Manage the backend computer：Vue3 Provide `econets-ui-admin-vue3`Version
* Backend adoption Spring Boot Multi-module architecture、MySQL + MyBatis Plus、Redis + Redisson
* Database available MySQL、Oracle、PostgreSQL、SQL Server、MariaDB、Domestic DAMO DM、TiDB Wait
* Message queue available Event、Redis、RabbitMQ、Kafka、RocketMQ Wait
* Authorization Use Spring Security & Token & Redis，Support multiple terminals、Multiple user authentication systems，Support SSO Single Sign-On
* Support loading dynamic permission menu，Button level permission control，Redis Cache improves performance
* Support SaaS Multi-tenancy，You can customize the permissions of each tenant，Provide transparent multi-tenant underlying encapsulation
* Workflow usage Flowable，Support dynamic forms、Online design process、Countersign / or sign、Multiple task allocation methods
* High-efficiency development，Use the code generator to generate with one click Java、Vue Front-end and back-end code、SQL Script、Interface Documentation，Support single table、Tree table、Master and Sub-Table
* Real-time communication，Adopt Spring WebSocket Realization，Built-in Token Identity verification，Support WebSocket Cluster
* Integrate WeChat applet、WeChat public account、Enterprise WeChat、DingTalk and other third-party logins，Integrate Alipay、WeChat and other payment and refund
* Integrate with Alibaba Cloud、Tencent Cloud and other SMS channels，Integration MinIO、Alibaba Cloud、Tencent Cloud、Qiniu Cloud and other cloud storage services
* Integrated report designer、Large Screen Designer，Generate cool reports and large screens by dragging and dropping

### Backend Project

| Project                                                              | Star                                                                                                                                                                                                                                                                                             | Introduction                          |
|-----------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| [econets-vue](https://github.com/EcoNetsTech/econets-vue)  | [![Gitee star](https://gitee.com/EcoNetsTech/econets-vue/badge/star.svg?theme=white)](https://gitee.com/EcoNetsTech/econets-vue) [![GitHub stars](https://img.shields.io/github/stars/EcoNetsTech/econets-vue.svg?style=social&label=Stars)](https://github.com/EcoNetsTech/econets-vue)       | Based on Spring Boot Multi-module architecture        |
| [econets-vue-mini](https://github.com/EcoNetsTech/econets-vue-mini)  | [![Gitee star](https://gitee.com/EcoNetsTech/econets-vue-mini/badge/star.svg?theme=white)](https://gitee.com/EcoNetsTech/econets-vue-mini) [![GitHub stars](https://img.shields.io/github/stars/EcoNetsTech/econets-vue-mini.svg?style=social&label=Stars)](https://github.com/EcoNetsTech/econets-vue-mini)       | Based on Spring Boot Multi-module architecture miniVersion        |

### Front-end project

| Project                                                                         | Star                                                                                                                                                                                                                                                                                                                     | Introduction                                     |
|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------|
| [econets-ui-admin-vue3](https://github.com/EcoNetsTech/econets-ui-admin-vue3) | [![Gitee star](https://gitee.com/EcoNetsTech/econets-ui-admin-vue3/badge/star.svg?theme=white)](https://gitee.com/EcoNetsTech/econets-ui-admin-vue3) [![GitHub stars](https://img.shields.io/github/stars/EcoNetsTech/econets-ui-admin-vue3.svg?style=social&label=Stars)](https://github.com/EcoNetsTech/econets-ui-admin-vue3)         | Based on Vue3 + element-plus Management backend implemented         |
| [econets-mall-uniapp](https://github.com/EcoNetsTech/econets-mall-uniapp) | [![Gitee star](https://gitee.com/EcoNetsTech/econets-mall-uniapp/badge/star.svg?theme=white)](https://gitee.com/EcoNetsTech/econets-mall-uniapp) [![GitHub stars](https://img.shields.io/github/stars/EcoNetsTech/econets-mall-uniapp.svg?style=social&label=Stars)](https://github.com/EcoNetsTech/econets-mall-uniapp)         | Based on uni-app The implemented mall applet           |


## Branch Description


### Simplified version

【Simplified version】Only system functions are included、Infrastructure functions，Does not include member center、Data Report、Workflow、Mall System、WeChat public account、CRM and other functions

* JDK 8 + Spring Boot 2.7.17 Version：<https://github.com/EcoNetsTech/econets-vue-mini > of `master/mini-2.x` Branch
* JDK 21 + Spring Boot 3.2.0 Version：<https://github.com/EcoNetsTech/econets-vue-mini > of `mini-3.x` Branch

### Full version

【Full version】Including system functions、Infrastructure、Member Center、Data Report、Workflow、Mall System、WeChat public account、CRM and other functions

* JDK 8 + Spring Boot 2.7.17 Version：<https://github.com/EcoNetsTech/econets-vue> of `master/2.x` Branch
* JDK 21 + Spring Boot 3.2.0 Version：<https://github.com/EcoNetsTech/econets-vue> of `3.x` Branch

The functions of the two branches are the same，You can use it with confidence！

## Open Source Agreement

**Why is this project recommended?？**

① This project adopts the ratio Apache 2.0 More relaxed [MIT License](https://github.com/EcoNetsTech/econets-vue/blob/master/LICENSE) Open Source Agreement，Individuals and enterprises can 100% Free to use，No need to keep the class author、Copyright Information。

② All code is open source，It won't be like other projects，Only open source part of the code，You cannot understand the architectural design of the entire project。

③ Clean code、Clean architecture，Follow《Alibaba Java Development Manual》Specification，Detailed code comments。

## Built-in function

The system has multiple built-in business functions，Can be used to speed up your business system：

![Functional Layering](/.image/common/econets-vue-pro-biz.png)

* System Function
* Infrastructure
* Workflow
* Payment System
* Member Center
* Data Report
* Mall System
* WeChat public account

> Friendly Tips：This project is based on econets-vue Modify，**Refactoring Optimization**Backend code，**Beautify**Front-end interface。
>
> * Additional new features，We use 🚀 Tag。
> * Re-implemented functionality，We use ⭐️ Tag。

### System Function

|     | Function    | Description                              |
|-----|-------|---------------------------------|
|     | User Management  | The user is a system operator，This function mainly completes system user configuration          |
| ⭐️  | Online users  | Active user status monitoring in the current system，Supports manual kick-off           |
|     | Role Management  | Role menu permission allocation、Set roles to divide data range permissions by organization      |
|     | Menu Management  | Configure system menu、Operation Permissions、Button permission identification, etc.，Local cache provides performance    |
|     | Department Management  | Configure system organization（Company、Department、Group），Tree structure display supports data permissions  |
|     | Position Management  | Configure system user positions                    |
| 🚀  | Tenant Management  | Configure system tenants，Support SaaS Multi-tenant function in the scenario        |
| 🚀  | Tenant Package  | Configure tenant packages，Customize each tenant's menu、Operation、Button permissions       |
|     | Dictionary Management  | Maintain some relatively fixed data that is frequently used in the system          |
| 🚀  | SMS Management  | SMS channel、Short message template、SMS log，Connecting to Alibaba Cloud、Tencent Cloud and other mainstream SMS platforms |
| 🚀  | Mail Management  | Email Account、Mail Template、Mail sending log，Support all mail platforms       |
| 🚀  | Internal message   | Message notification within the system，Provide in-site letter template、Internal message          |
| 🚀  | Operation log  | System normal operation log record and query，Integration Swagger Generate log content |
| ⭐️  | Login log  | System login log record query，Contains login exception               |
| 🚀  | Error code management | Management of all system error codes，Error prompts can be modified online，No need to restart the service     |
|     | Notices and Announcements  | System notification announcement information release maintenance                    |
| 🚀  | Sensitive words   | Configure system sensitive words，Support tag grouping                  |
| 🚀  | Application Management  | Management SSO Single sign-on application，Support multiple OAuth2 Authorization method |
| 🚀  | Regional Management  | Show provinces、City、District, town and other city information，Support IP Corresponding city      |

### Workflow

|     | Function    | Description                                     |
|-----|-------|----------------------------------------|
| 🚀  | Process Model  | Configure workflow process model，Support file import and online design flowchart，Provide 7 Task allocation rules |
| 🚀  | Process Form  | Drag form elements to generate corresponding workflow forms，Overwrite Element UI All form components |
| 🚀  | User Grouping  | Custom user grouping，Approval grouping that can be used in workflow                    |
| 🚀  | My Process  | View the workflow I initiated，Support new creation、Cancel process and other operations，Highlight flowchart、Approval Timeline    |
| 🚀  | To-do tasks  | View yourself【Not yet】Approval tasks，Supported by、Not passed、Forward、Delegation、Return and other operations    |
| 🚀  | Task completed  | View yourself【Has】Approval tasks，Return operation will be supported in the future               |
| 🚀  | OA Requesting leave | As an example of using a custom business access workflow，Just create the workflow corresponding to the request，Approval can be carried out immediately |

### Payment System

|     | Function   | Description                        |
|-----|------|---------------------------|
| 🚀  | Application Information | Configure the merchant's application information，Connect with Alipay、WeChat and other payment channels |
| 🚀  | Payment Order | View Alipay initiated by users、WeChat, etc.【Payment】Order     |
| 🚀  | Refund order | View Alipay initiated by users、WeChat, etc.【Refund】Order     |
| 🚀  | Callback notification | View payment callback service【Payment】【Refund】Notification result    |
| 🚀  | Access Example | Provide access to payment systems【Payment】【Refund】Functional Practice    |

### Infrastructure

|     | Function        | Description                                           |
|-----|-----------|----------------------------------------------|
| 🚀  | Code Generation      | Generation of front-end and back-end codes（Java、Vue、SQL、Unit Test），Support CRUD Download       |
| 🚀  | System interface      | Based on Swagger Automatically generate relevant RESTful API Interface Documentation          |
| 🚀  | Database Document     | Based on Screw Automatically generate database documents，Support export Word、HTML、MD Format      |
|     | Form Construction      | Drag form elements to generate corresponding HTML Code，Support export JSON、Vue File         |
| 🚀  | Configuration Management      | Dynamically configure common parameters for the system，Support SpringBoot Loading                 |
| ⭐️  | Scheduled tasks      | Online（Add、Modify、Delete)Task scheduling includes execution result log                     |
| 🚀  | File Service      | Supports storing files to S3（MinIO、Alibaba Cloud、Tencent Cloud、Qiniu Cloud）、Local、FTP、Database, etc.   | 
| 🚀  | WebSocket | Provide WebSocket Access Example，Support one-to-one、One-to-many sending method              | 
| 🚀  | API Log    | Include RESTful API Access log、Abnormal log in two parts，Convenient for troubleshooting API Related issues   |
|     | MySQL Monitoring  | Monitor the current system database connection pool status，Can be analyzedSQLFind out the system performance bottleneck              |
|     | Redis Monitoring  | Monitoring Redis Database usage，Used Redis Key Management           |
| 🚀  | Message Queue      | Based on Redis Implementing message queues，Stream Provide cluster consumption，Pub/Sub Provide broadcast consumption |
| 🚀  | Java Monitoring   | Based on Spring Boot Admin Realization Java Application monitoring           |
| 🚀  | Link Tracking      | Access SkyWalking Component，Realize link tracking                      |
| 🚀  | Log Center      | Access SkyWalking Component，Realize the log center                      |
| 🚀  | Distributed lock      | Based on Redis Implementing distributed locks，Satisfy concurrent scenarios                       |
| 🚀  | Idempotent component      | Based on Redis Implement idempotent components，Solve the duplicate request problem                     |
| 🚀  | Service Guarantee      | Based on Resilience4j Achieve service stability，Including current limiting、Fuse and other functions          |
| 🚀  | Log Service      | Lightweight Log Center，View the remote server log                           |
| 🚀  | Unit Test      | Based on JUnit + Mockito Implement unit testing，Ensure the correctness of the function、Code quality, etc.    |

### Data Report

|     | Function    | Description                 |
|-----|-------|--------------------|
| 🚀  | Report Designer | Support data reports、Graphical Report、Printing design, etc.  |
| 🚀  | Large Screen Designer | Drag and drop to generate a large data screen，Built-in dozens of chart components |

### WeChat public account

|     | Function     | Description                            |
|-----|--------|-------------------------------|
| 🚀  | Account Management   | Configure the WeChat public account to access，Can support multiple public accounts           |
| 🚀  | Statistics   | Count the increase and decrease of users of public accounts、Total users、News Overview、Interface analysis and other data  |
| 🚀  | Fans Management   | View already followed、List of fans who have unfollowed，Can synchronize fans、Operations such as labeling |
| 🚀  | Message Management   | View the list of messages sent by fans，Can actively reply to fans' messages         |
| 🚀  | Automatic reply   | Automatically reply to messages sent by fans，Support follow-up reply、Message reply、Keyword reply |
| 🚀  | Tag Management   | Create tags for public accounts、Query、Modify、Delete and other operations       |
| 🚀  | Menu Management   | Customize the menu of the public account，You can also synchronize the menu from the official account         |
| 🚀  | Material Management   | Manage public account pictures、Voice、Videos and other materials，Support online voice playback、Video |
| 🚀  | Draft Box  | Add commonly used graphic materials to the draft box，Can be published to the official account         |
| 🚀  | Image and text publication records | View the successfully published graphic materials，Support deletion operation    

### Mall System

![Functional diagram](/.image/mall/mall-feature.png)

![Functional diagram](/.image/mall/mall-preview.png)

_Front end based on crmeb uniapp Authorized reconstruction，Optimize code implementation_

### Member Center

|     | Function   | Description                               |
|-----|------|----------------------------------|
| 🚀  | Member Management | Member is C Consumers at the end，This function is used for searching and managing members        |
| 🚀  | Member Tag | Create tags for members、Query、Modify、Delete and other operations           |
| 🚀  | Member Level | Level of members、Manage growth value，Can be used for membership benefits such as order discounts      |
| 🚀  | Member Grouping | Group members，For user portrait、Content push and other operational means         |
| 🚀  | Points Sign-in | Give back to sign-in、Points for consumption and other behaviors，Members can use their orders to get cash back、Points redemption and other consumption methods |


## Technology Stack

### Module

| Project                                                                       | Description                 |
|--------------------------------------------------------------------------|--------------------|
| `blossom-dependencies`                                                     | Maven Dependency version management       |
| `blossom-framework`                                                        | Java Framework expansion          |
| `blossom-server`                                                           | Management Backend + User APP Server side |
| `blossom-module-system`                                                    | System functions Module Module    |
| `blossom-module-member`                                                    | Member Center Module Module    |
| `blossom-module-infrastructure`                                            | Infrastructure Module Module    |
| `blossom-module-bpm`                                                       | Workflow Module Module    |
| `blossom-module-pay`                                                       | Payment system Module Module    |
| `blossom-module-mall`                                                      | Mall system Module Module    |
| `blossom-module-mp`                                                        | WeChat public account Module Module   |
| `blossom-module-report`                                                    | Large screen report Module Module     |

### Framework

| Framework                                                                                          | Description               | Version             | Study Guide                                                           |
|---------------------------------------------------------------------------------------------|------------------|----------------|----------------------------------------------------------------|
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Application Development Framework           | 2.7.17         | [Document](https://github.com/ximuV/SpringBoot-Labs)                |
| [MySQL](https://www.mysql.com/cn/)                                                          | Database Server           | 5.7 / 8.0+     |                                                                |
| [Druid](https://github.com/alibaba/druid)                                                   | JDBC Connection pool、Monitoring Component    | 1.2.19         |                                                                |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis Enhancement Toolkit    | 3.5.3.2        |                                                                |
| [Dynamic Datasource](https://dynamic-datasource.com/)                                       | Dynamic data source            | 3.6.1          |                                                               |
| [Redis](https://redis.io/)                                                                  | key-value Database    | 5.0 / 6.0 /7.0 |                                                                |
| [Redisson](https://github.com/redisson/redisson)                                            | Redis Client        | 3.18.0         |                                                               |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC Framework           | 5.3.24         |                                                                |
| [Spring Security](https://github.com/spring-projects/spring-security)                       | Spring Security Framework      | 5.7.11         |                                                                |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator)                     | Parameter verification component           | 6.2.5          |                                                                |
| [Flowable](https://github.com/flowable/flowable-engine)                                     | Workflow Engine            | 6.8.0          |                                                                |
| [Quartz](https://github.com/quartz-scheduler)                                               | Task Scheduling Component           | 2.3.2          |                                                               |
| [Springdoc](https://springdoc.org/)                                                         | Swagger Document       | 1.6.15         |                                                               |
| [Resilience4j](https://github.com/resilience4j/resilience4j)                                | Service assurance component           | 1.7.1          |                                                               |
| [SkyWalking](https://skywalking.apache.org/)                                                | Distributed Application Tracing System        | 8.12.0         |                                                               |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)                       | Spring Boot Monitoring Platform | 2.7.10         |                                                                |
| [Jackson](https://github.com/FasterXML/jackson)                                             | JSON Tool Library         | 2.13.3         |                                                                |
| [MapStruct](https://mapstruct.org/)                                                         | Java Bean Conversion     | 1.5.5.Final    |                                                                |
| [Lombok](https://projectlombok.org/)                                                        | Eliminate verbose Java Code    | 1.18.30        |                                                                |
| [JUnit](https://junit.org/junit5/)                                                          | Java Unit testing framework      | 5.8.2          | -                                                              |
| [Mockito](https://github.com/mockito/mockito)                                               | Java Mock Framework     | 4.8.0          | -                                                              |
