package cn.econets.blossom.module.infrastructure.service.codegen.inner;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.framework.common.util.string.StrUtils;
import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenColumnDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenTableDO;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenFrontTypeEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenSceneEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenTemplateTypeEnum;
import cn.econets.blossom.module.infrastructure.framework.codegen.config.CodegenProperties;
import cn.hutool.system.SystemUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static cn.hutool.core.map.MapUtil.getStr;
import static cn.hutool.core.text.CharSequenceUtil.*;

/**
 * Code generation engine，Used to generate specific code
 * Currently based on {@link org.apache.velocity.app.Velocity} Template engine implementation
 *
 * Taking into account Java There are many template engine frameworks，Freemarker、Velocity、Thymeleaf Wait，So we adopt hutool Packaged {@link cn.hutool.extra.template.Template} Abstract
 *
 *
 */
@Component
public class CodegenEngine {

    /**
     * Backend template configuration
     *
     * key：Template in resources Address
     * value：Generated path
     */
    private static final Map<String, String> SERVER_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>()) // Orderly
            // Java module-biz Main
            .put(javaTemplatePath("controller/vo/pageReqVO"), javaModuleImplVOFilePath("PageReqVO"))
            .put(javaTemplatePath("controller/vo/listReqVO"), javaModuleImplVOFilePath("ListReqVO"))
            .put(javaTemplatePath("controller/vo/respVO"), javaModuleImplVOFilePath("RespVO"))
            .put(javaTemplatePath("controller/vo/saveReqVO"), javaModuleImplVOFilePath("SaveReqVO"))
            .put(javaTemplatePath("controller/controller"), javaModuleImplControllerFilePath())
            .put(javaTemplatePath("dal/do"),
                    javaModuleImplMainFilePath("dal/dataobject/${table.businessName}/${table.className}DO"))
            .put(javaTemplatePath("dal/do_sub"), // Special：Master and sub-table exclusive logic
                    javaModuleImplMainFilePath("dal/dataobject/${table.businessName}/${subTable.className}DO"))
            .put(javaTemplatePath("dal/mapper"),
                    javaModuleImplMainFilePath("dal/mysql/${table.businessName}/${table.className}Mapper"))
            .put(javaTemplatePath("dal/mapper_sub"), // Special：Master and sub-table exclusive logic
                    javaModuleImplMainFilePath("dal/mysql/${table.businessName}/${subTable.className}Mapper"))
            .put(javaTemplatePath("dal/mapper.xml"), mapperXmlFilePath())
            .put(javaTemplatePath("service/serviceImpl"),
                    javaModuleImplMainFilePath("service/${table.businessName}/${table.className}ServiceImpl"))
            .put(javaTemplatePath("service/service"),
                    javaModuleImplMainFilePath("service/${table.businessName}/${table.className}Service"))
            // Java module-biz Test
            .put(javaTemplatePath("test/serviceTest"),
                    javaModuleImplTestFilePath("service/${table.businessName}/${table.className}ServiceImplTest"))
            // Java module-api Main
            .put(javaTemplatePath("enums/errorcode"), javaModuleApiMainFilePath("enums/ErrorCodeConstants_Manual operation"))
            // SQL
            .put("codegen/sql/sql.vm", "sql/sql.sql")
            .put("codegen/sql/h2.vm", "sql/h2.sql")
            .build();

    /**
     * Backend configuration template
     *
     * key1：UI Template type {@link CodegenFrontTypeEnum#getType()}
     * key2：Template in resources Address
     * value：Generated path
     */
    private static final Table<Integer, String, String> FRONT_TEMPLATES = ImmutableTable.<Integer, String, String>builder()
            // Vue2 Standard template
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/index.vue"),
                    vueFilePath("views/${table.moduleName}/${table.businessName}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("api/api.js"),
                    vueFilePath("api/${table.moduleName}/${table.businessName}/index.js"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/form.vue"),
                    vueFilePath("views/${table.moduleName}/${table.businessName}/${simpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/components/form_sub_normal.vue"),  // Special：Master and sub-table exclusive logic
                    vueFilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/components/form_sub_inner.vue"),  // Special：Master and sub-table exclusive logic
                    vueFilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/components/form_sub_erp.vue"),  // Special：Master and sub-table exclusive logic
                    vueFilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/components/list_sub_inner.vue"),  // Special：Master and sub-table exclusive logic
                    vueFilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}List.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/components/list_sub_erp.vue"),  // Special：Master and sub-table exclusive logic
                    vueFilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}List.vue"))
            // Vue3 Standard template
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/${simpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/components/form_sub_normal.vue"),  // Special：Master and sub-table exclusive logic
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/components/form_sub_inner.vue"),  // Special：Master and sub-table exclusive logic
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/components/form_sub_erp.vue"),  // Special：Master and sub-table exclusive logic
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/components/list_sub_inner.vue"),  // Special：Master and sub-table exclusive logic
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}List.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/components/list_sub_erp.vue"),  // Special：Master and sub-table exclusive logic
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/components/${subSimpleClassName}List.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${table.businessName}/index.ts"))
            // Vue3 Schema Template
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/data.ts"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/${classNameVar}.data.ts"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/${simpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${table.businessName}/index.ts"))
            // Vue3 vben Template
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/data.ts"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/${classNameVar}.data.ts"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${table.businessName}/${simpleClassName}Modal.vue"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${table.businessName}/index.ts"))
            .build();

    @Resource
    private CodegenProperties codegenProperties;

    /**
     * Whether to use jakarta Package，Used to solve Spring Boot 2.X Japanese 3.X Compatibility issues
     *
     * true  - Use jakarta.validation.constraints.*
     * false - Use javax.validation.constraints.*
     */
    @Setter // Reasons for allowing settings，Because the single test needs to be changed manually
    private Boolean jakartaEnable;

    /**
     * Template Engine，By hutool Realization
     */
    private final TemplateEngine templateEngine;
    /**
     * Global general variable mapping
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();

    public CodegenEngine() {
        // Initialization TemplateEngine Properties
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        this.templateEngine = new VelocityEngine(config);
        // Settings javaxEnable，According to whether it is used JDK17 To judge
        this.jakartaEnable = SystemUtil.getJavaInfo().isJavaVersionAtLeast(1700); // 17.00 * 100
    }

    @PostConstruct
    @VisibleForTesting
    void initGlobalBindingMap() {
        // Global Configuration
        globalBindingMap.put("basePackage", codegenProperties.getBasePackage());
        globalBindingMap.put("baseFrameworkPackage", codegenProperties.getBasePackage()
                + '.' + "framework"); // Used to obtain the test class later package Address
        globalBindingMap.put("jakartaPackage", jakartaEnable ? "jakarta" : "javax");
        // Global Java Bean
        globalBindingMap.put("CommonResultClassName", CommonResult.class.getName());
        globalBindingMap.put("PageResultClassName", PageResult.class.getName());
        // VO Class，Unique fields
        globalBindingMap.put("PageParamClassName", PageParam.class.getName());
        globalBindingMap.put("DictFormatClassName", DictFormat.class.getName());
        // DO Class，Unique fields
        globalBindingMap.put("BaseDOClassName", BaseDO.class.getName());
        globalBindingMap.put("baseDOFields", CodegenBuilder.BASE_DO_FIELDS);
        globalBindingMap.put("QueryWrapperClassName", LambdaQueryWrapperX.class.getName());
        globalBindingMap.put("BaseMapperClassName", BaseMapperX.class.getName());
        // Util Tools
        globalBindingMap.put("ServiceExceptionUtilClassName", ServiceExceptionUtil.class.getName());
        globalBindingMap.put("DateUtilsClassName", DateUtils.class.getName());
        globalBindingMap.put("ExcelUtilsClassName", ExcelUtils.class.getName());
        globalBindingMap.put("LocalDateTimeUtilsClassName", LocalDateTimeUtils.class.getName());
        globalBindingMap.put("ObjectUtilsClassName", ObjectUtils.class.getName());
        globalBindingMap.put("DictConvertClassName", DictConvert.class.getName());
        globalBindingMap.put("OperateLogClassName", OperateLog.class.getName());
        globalBindingMap.put("OperateTypeEnumClassName", OperateTypeEnum.class.getName());
        globalBindingMap.put("BeanUtils", BeanUtils.class.getName());
    }

    /**
     * Generate code
     *
     * @param table Table definition
     * @param columns table Field definition array
     * @param subTables Subtable array，Used only when it is a master-subtable
     * @param subColumnsList subTables Field definition array
     * @return Generated code，key is the path，value is the corresponding code
     */
    public Map<String, String> execute(CodegenTableDO table, List<CodegenColumnDO> columns,
                                       List<CodegenTableDO> subTables, List<List<CodegenColumnDO>> subColumnsList) {
        // 1.1 Initialization bindMap Context
        Map<String, Object> bindingMap = initBindingMap(table, columns, subTables, subColumnsList);
        // 1.2 Get template
        Map<String, String> templates = getTemplates(table.getFrontType());

        // 2. Execute Generate
        Map<String, String> result = Maps.newLinkedHashMapWithExpectedSize(templates.size()); // Orderly
        templates.forEach((vmPath, filePath) -> {
            // 2.1 Special：Master and sub-table exclusive logic
            if (isSubTemplate(vmPath)) {
                generateSubCode(table, subTables, result, vmPath, filePath, bindingMap);
                return;
                // 2.2 Special：Tree table exclusive logic
            } else if (isPageReqVOTemplate(vmPath)) {
                // Reduce redundant class generation，For example PageVO.java Class
                if (CodegenTemplateTypeEnum.isTree(table.getTemplateType())) {
                    return;
                }
            } else if (isListReqVOTemplate(vmPath)) {
                // Reduce redundant class generation，For example ListVO.java Class
                if (!CodegenTemplateTypeEnum.isTree(table.getTemplateType())) {
                    return;
                }
            }
            // 2.3 Generate by default
            generateCode(result, vmPath, filePath, bindingMap);
        });
        return result;
    }

    private void generateCode(Map<String, String> result, String vmPath,
                              String filePath, Map<String, Object> bindingMap) {
        filePath = formatFilePath(filePath, bindingMap);
        String content = templateEngine.getTemplate(vmPath).render(bindingMap);
        // Format code
        content = prettyCode(content);
        result.put(filePath, content);
    }

    private void generateSubCode(CodegenTableDO table, List<CodegenTableDO> subTables,
                                 Map<String, String> result, String vmPath,
                                 String filePath, Map<String, Object> bindingMap) {
        // No subtable，So it is not generated
        if (CollUtil.isEmpty(subTables)) {
            return;
        }
        // Pattern matching of master and child tables。Purpose：Filter out personalized templates
        if (vmPath.contains("_normal")
                && ObjectUtil.notEqual(table.getTemplateType(), CodegenTemplateTypeEnum.MASTER_NORMAL.getType())) {
            return;
        }
        if (vmPath.contains("_erp")
                && ObjectUtil.notEqual(table.getTemplateType(), CodegenTemplateTypeEnum.MASTER_ERP.getType())) {
            return;
        }
        if (vmPath.contains("_inner")
                && ObjectUtil.notEqual(table.getTemplateType(), CodegenTemplateTypeEnum.MASTER_INNER.getType())) {
            return;
        }

        // Generate one by one
        for (int i = 0; i < subTables.size(); i++) {
            bindingMap.put("subIndex", i);
            generateCode(result, vmPath, filePath, bindingMap);
        }
        bindingMap.remove("subIndex");
    }

    /**
     * Format the generated code
     *
     * Because try to make vm Simple template，So the unified processing is in this method。
     * If not processed，Vue of Pretty Format verification may report an error
     *
     * @param content Code before formatting
     * @return Formatted code
     */
    private String prettyCode(String content) {
        // Vue Interface：Remove redundant fields , Comma，Solve the front-end problem Pretty Error in code format check
        content = content.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");
        // Vue Interface：Remove the extra ones dateFormatter，Only one case，Indicates that it is not used
        if (StrUtil.count(content, "dateFormatter") == 1) {
            content = StrUtils.removeLineContains(content, "dateFormatter");
        }
        // Vue2 Interface：Correction $refs
        if (StrUtil.count(content, "this.refs") >= 1) {
            content = content.replace("this.refs", "this.$refs");
        }
        // Vue Interface：Remove the extra ones dict Related，Only one case，Indicates that it is not used
        if (StrUtil.count(content, "getIntDictOptions") == 1) {
            content = content.replace("getIntDictOptions, ", "");
        }
        if (StrUtil.count(content, "getStrDictOptions") == 1) {
            content = content.replace("getStrDictOptions, ", "");
        }
        if (StrUtil.count(content, "getBoolDictOptions") == 1) {
            content = content.replace("getBoolDictOptions, ", "");
        }
        if (StrUtil.count(content, "DICT_TYPE.") == 0) {
            content = StrUtils.removeLineContains(content, "DICT_TYPE");
        }
        return content;
    }

    private Map<String, Object> initBindingMap(CodegenTableDO table, List<CodegenColumnDO> columns,
                                               List<CodegenTableDO> subTables, List<List<CodegenColumnDO>> subColumnsList) {
        // Create bindingMap
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);
        bindingMap.put("table", table);
        bindingMap.put("columns", columns);
        bindingMap.put("primaryColumn", CollectionUtils.findFirst(columns, CodegenColumnDO::getPrimaryKey)); // Primary key field
        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf(table.getScene()));

        // className Related
        // Remove the specified prefix，Will TestDictType Convert to DictType. Because in create After waiting for the method，No need to bring Test Prefix
        String simpleClassName = removePrefix(table.getClassName(), upperFirst(table.getModuleName()));
        bindingMap.put("simpleClassName", simpleClassName);
        bindingMap.put("simpleClassName_underlineCase", toUnderlineCase(simpleClassName)); // Will DictType Convert to dict_type
        bindingMap.put("classNameVar", lowerFirst(simpleClassName)); // Will DictType Convert to dictType，For variables
        // Will DictType Convert to dict-type
        String simpleClassNameStrikeCase = toSymbolCase(simpleClassName, '-');
        bindingMap.put("simpleClassName_strikeCase", simpleClassNameStrikeCase);
        // permission Prefix
        bindingMap.put("permissionPrefix", table.getModuleName() + ":" + simpleClassNameStrikeCase);

        // Special：Tree table exclusive logic
        if (CodegenTemplateTypeEnum.isTree(table.getTemplateType())) {
            CodegenColumnDO treeParentColumn = CollUtil.findOne(columns,
                    column -> Objects.equals(column.getId(), table.getTreeParentColumnId()));
            bindingMap.put("treeParentColumn", treeParentColumn);
            bindingMap.put("treeParentColumn_javaField_underlineCase", toUnderlineCase(treeParentColumn.getJavaField()));
            CodegenColumnDO treeNameColumn = CollUtil.findOne(columns,
                    column -> Objects.equals(column.getId(), table.getTreeNameColumnId()));
            bindingMap.put("treeNameColumn", treeNameColumn);
            bindingMap.put("treeNameColumn_javaField_underlineCase", toUnderlineCase(treeNameColumn.getJavaField()));
        }

        // Special：Master and sub-table exclusive logic
        if (CollUtil.isNotEmpty(subTables)) {
            // Create bindingMap
            bindingMap.put("subTables", subTables);
            bindingMap.put("subColumnsList", subColumnsList);
            List<CodegenColumnDO> subPrimaryColumns = new ArrayList<>();
            List<CodegenColumnDO> subJoinColumns = new ArrayList<>();
            List<String> subJoinColumnStrikeCases = new ArrayList<>();
            List<String> subSimpleClassNames = new ArrayList<>();
            List<String> subClassNameVars = new ArrayList<>();
            List<String> simpleClassNameUnderlineCases = new ArrayList<>();
            List<String> subSimpleClassNameStrikeCases = new ArrayList<>();
            for (int i = 0; i < subTables.size(); i++) {
                CodegenTableDO subTable = subTables.get(i);
                List<CodegenColumnDO> subColumns = subColumnsList.get(i);
                subPrimaryColumns.add(CollectionUtils.findFirst(subColumns, CodegenColumnDO::getPrimaryKey)); //
                CodegenColumnDO subColumn = CollectionUtils.findFirst(subColumns, // Related fields
                        column -> Objects.equals(column.getId(), subTable.getSubJoinColumnId()));
                subJoinColumns.add(subColumn);
                subJoinColumnStrikeCases.add(toSymbolCase(subColumn.getJavaField(), '-')); // Will DictType Convert to dict-type
                // className Related
                String subSimpleClassName = removePrefix(subTable.getClassName(), upperFirst(subTable.getModuleName()));
                subSimpleClassNames.add(subSimpleClassName);
                simpleClassNameUnderlineCases.add(toUnderlineCase(subSimpleClassName)); // Will DictType Convert to dict_type
                subClassNameVars.add(lowerFirst(subSimpleClassName)); // Will DictType Convert to dictType，For variables
                subSimpleClassNameStrikeCases.add(toSymbolCase(subSimpleClassName, '-')); // Will DictType Convert to dict-type
            }
            bindingMap.put("subPrimaryColumns", subPrimaryColumns);
            bindingMap.put("subJoinColumns", subJoinColumns);
            bindingMap.put("subJoinColumn_strikeCases", subJoinColumnStrikeCases);
            bindingMap.put("subSimpleClassNames", subSimpleClassNames);
            bindingMap.put("simpleClassNameUnderlineCases", simpleClassNameUnderlineCases);
            bindingMap.put("subClassNameVars", subClassNameVars);
            bindingMap.put("subSimpleClassName_strikeCases", subSimpleClassNameStrikeCases);
        }
        return bindingMap;
    }

    private Map<String, String> getTemplates(Integer frontType) {
        Map<String, String> templates = new LinkedHashMap<>();
        templates.putAll(SERVER_TEMPLATES);
        templates.putAll(FRONT_TEMPLATES.row(frontType));
        return templates;
    }

    @SuppressWarnings("unchecked")
    private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));
        filePath = StrUtil.replace(filePath, "${simpleClassName}",
                getStr(bindingMap, "simpleClassName"));
        // sceneEnum Fields included
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        // table Included fields
        CodegenTableDO table = (CodegenTableDO) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${table.moduleName}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.getBusinessName());
        filePath = StrUtil.replace(filePath, "${table.className}", table.getClassName());
        // Special：Master and sub-table exclusive logic
        Integer subIndex = (Integer) bindingMap.get("subIndex");
        if (subIndex != null) {
            CodegenTableDO subTable = ((List<CodegenTableDO>) bindingMap.get("subTables")).get(subIndex);
            filePath = StrUtil.replace(filePath, "${subTable.moduleName}", subTable.getModuleName());
            filePath = StrUtil.replace(filePath, "${subTable.businessName}", subTable.getBusinessName());
            filePath = StrUtil.replace(filePath, "${subTable.className}", subTable.getClassName());
            filePath = StrUtil.replace(filePath, "${subSimpleClassName}",
                    ((List<String>) bindingMap.get("subSimpleClassNames")).get(subIndex));
        }
        return filePath;
    }

    private static String javaTemplatePath(String path) {
        return "codegen/java/" + path + ".vm";
    }

    private static String javaModuleImplVOFilePath(String path) {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "vo/${sceneEnum.prefixClass}${table.className}" + path, "biz", "main");
    }

    private static String javaModuleImplControllerFilePath() {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "${sceneEnum.prefixClass}${table.className}Controller", "biz", "main");
    }

    private static String javaModuleImplMainFilePath(String path) {
        return javaModuleFilePath(path, "biz", "main");
    }

    private static String javaModuleApiMainFilePath(String path) {
        return javaModuleFilePath(path, "api", "main");
    }

    private static String javaModuleImplTestFilePath(String path) {
        return javaModuleFilePath(path, "biz", "test");
    }

    private static String javaModuleFilePath(String path, String module, String src) {
        return "blossom-module-${table.moduleName}/" + // Top-level module
                "blossom-module-${table.moduleName}-" + module + "/" + // Submodule
                "src/" + src + "/java/${basePackage}/module/${table.moduleName}/" + path + ".java";
    }

    private static String mapperXmlFilePath() {
        return "blossom-module-${table.moduleName}/" + // Top-level module
                "blossom-module-${table.moduleName}-biz/" + // Submodule
                "src/main/resources/mapper/${table.businessName}/${table.className}Mapper.xml";
    }

    private static String vueTemplatePath(String path) {
        return "codegen/vue/" + path + ".vm";
    }

    private static String vueFilePath(String path) {
        return "blossom-ui-${sceneEnum.basePackage}-vue2/" + // Top-level directory
                "src/" + path;
    }

    private static String vue3TemplatePath(String path) {
        return "codegen/vue3/" + path + ".vm";
    }

    private static String vue3FilePath(String path) {
        return "blossom-ui-${sceneEnum.basePackage}-vue3/" + // Top-level directory
                "src/" + path;
    }

    private static String vue3SchemaTemplatePath(String path) {
        return "codegen/vue3_schema/" + path + ".vm";
    }

    private static String vue3VbenTemplatePath(String path) {
        return "codegen/vue3_vben/" + path + ".vm";
    }

    private static boolean isSubTemplate(String path) {
        return path.contains("_sub");
    }

    private static boolean isPageReqVOTemplate(String path) {
        return path.contains("pageReqVO");
    }

    private static boolean isListReqVOTemplate(String path) {
        return path.contains("listReqVO");
    }

}

