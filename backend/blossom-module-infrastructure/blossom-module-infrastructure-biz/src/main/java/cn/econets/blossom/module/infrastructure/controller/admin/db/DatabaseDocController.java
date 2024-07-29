package cn.econets.blossom.module.infrastructure.controller.admin.db;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Tag(name = "Management Backend - Database Document")
@RestController
@RequestMapping("/infra/db-doc")
public class DatabaseDocController {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    private static final String FILE_OUTPUT_DIR = System.getProperty("java.io.tmpdir") + File.separator
            + "db-doc";
    private static final String DOC_FILE_NAME = "Database Document";
    private static final String DOC_VERSION = "1.0.0";
    private static final String DOC_DESCRIPTION = "Document Description";

    @GetMapping("/export-html")
    @Operation(summary = "Export html Formatted data document")
    @Parameter(name = "deleteFile", description = "Whether to delete the database documents generated locally on the server", example = "true")
    public void exportHtml(@RequestParam(defaultValue = "true") Boolean deleteFile,
                           HttpServletResponse response) throws IOException {
        doExportFile(EngineFileType.HTML, deleteFile, response);
    }

    @GetMapping("/export-word")
    @Operation(summary = "Export word Formatted data document")
    @Parameter(name = "deleteFile", description = "Whether to delete the database documents generated locally on the server", example = "true")
    public void exportWord(@RequestParam(defaultValue = "true") Boolean deleteFile,
                           HttpServletResponse response) throws IOException {
        doExportFile(EngineFileType.WORD, deleteFile, response);
    }

    @GetMapping("/export-markdown")
    @Operation(summary = "Export markdown Formatted data document")
    @Parameter(name = "deleteFile", description = "Whether to delete the database documents generated locally on the server", example = "true")
    public void exportMarkdown(@RequestParam(defaultValue = "true") Boolean deleteFile,
                               HttpServletResponse response) throws IOException {
        doExportFile(EngineFileType.MD, deleteFile, response);
    }

    private void doExportFile(EngineFileType fileOutputType, Boolean deleteFile,
                              HttpServletResponse response) throws IOException {
        String docFileName = DOC_FILE_NAME + "_" + IdUtil.fastSimpleUUID();
        String filePath = doExportFile(fileOutputType, docFileName);
        String downloadFileName = DOC_FILE_NAME + fileOutputType.getFileSuffix(); //File name after downloading
        try {
            // Read，Return
            ServletUtils.writeAttachment(response, downloadFileName, FileUtil.readBytes(filePath));
        } finally {
            handleDeleteFile(deleteFile, filePath);
        }
    }

    /**
     * Output file，Return the file path
     *
     * @param fileOutputType File type
     * @param fileName       File name, No need ".docx" etc. file suffixes
     * @return The path where the generated file is located
     */
    private String doExportFile(EngineFileType fileOutputType, String fileName) {
        try (HikariDataSource dataSource = buildDataSource()) {
            // Create screw Configuration
            Configuration config = Configuration.builder()
                    .version(DOC_VERSION)  // Version
                    .description(DOC_DESCRIPTION) // Description
                    .dataSource(dataSource) // Data Source
                    .engineConfig(buildEngineConfig(fileOutputType, fileName)) // Engine Configuration
                    .produceConfig(buildProcessConfig()) // Processing Configuration
                    .build();

            // Execute screw，Generate database documentation
            new DocumentationExecute(config).execute();

            return FILE_OUTPUT_DIR + File.separator + fileName + fileOutputType.getFileSuffix();
        }
    }

    private void handleDeleteFile(Boolean deleteFile, String filePath) {
        if (!deleteFile) {
            return;
        }
        FileUtil.del(filePath);
    }

    /**
     * Create data source
     */
    // TODO screw Not supported yet druid，awkward
    private HikariDataSource buildDataSource() {
        // Get DataSource Data Source，Currently only supports the first one
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        // Create HikariConfig Configuration class
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperty.getUrl());
        hikariConfig.setUsername(dataSourceProperty.getUsername());
        hikariConfig.setPassword(dataSourceProperty.getPassword());
        hikariConfig.addDataSourceProperty("useInformationSchema", "true"); // Settings can be obtained tables remarks Information
        // Create data source
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Create screw Engine configuration
     */
    private static EngineConfig buildEngineConfig(EngineFileType fileOutputType, String docFileName) {
        return EngineConfig.builder()
                .fileOutputDir(FILE_OUTPUT_DIR) // Generate file path
                .openOutputDir(false) // Open directory
                .fileType(fileOutputType) // File type
                .produceType(EngineTemplateType.velocity) // File Type
                .fileName(docFileName) // Custom file name
                .build();
    }

    /**
     * Create screw Processing configuration，Generally can be ignored
     * Specify generation logic、When the specified table exists、Specify table prefix、When specifying a table suffix，The specified table will be generated，Other tables are not generated、And skip ignoring table configuration
     */
    private static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                .ignoreTablePrefix(Arrays.asList("QRTZ_", "ACT_", "FLW_")) // Ignore table prefix
                .build();
    }

}
