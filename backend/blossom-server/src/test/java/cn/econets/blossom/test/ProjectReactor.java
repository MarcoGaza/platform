package cn.econets.blossom.test;

import cn.econets.blossom.framework.common.util.collection.SetUtils;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static java.io.File.separator;

/**
 * Project Modifier，One-click replacement Maven of groupId、artifactId，Project package Wait
 * <p>
 * Through modification groupIdNew、artifactIdNew、projectBaseDirNew Three variables
 *
 */
@Slf4j
public class ProjectReactor {

    private static final String GROUP_ID = "cn.econets.boot";
    private static final String ARTIFACT_ID = "blossom";
    private static final String PACKAGE_NAME = "cn.econets.blossom";
    private static final String TITLE = "Management System";

    /**
     * Whitelist file，Do not rewrite，Avoid problems
     */
    private static final Set<String> WHITE_FILE_TYPES = SetUtils.asSet("gif", "jpg", "svg", "png", // Picture
            "eot", "woff2", "ttf", "woff"); // Font

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String projectBaseDir = getProjectBaseDir();
        log.info("[main][The original project Lu Jin changed its address ({})]", projectBaseDir);

        // ========== Configuration，You need to modify it manually ==========
        String groupIdNew = "cn.star.boot";
        String artifactIdNew = "star";
        String packageNameNew = "cn.start.pp";
        String titleNew = "Management System";
        String projectBaseDirNew = projectBaseDir + "-new"; // After one-click name change，“New”The directory where the project is located
        log.info("[main][Detect new project directory ({})Does it exist?]", projectBaseDirNew);
        if (FileUtil.exist(projectBaseDirNew)) {
            log.error("[main][New project directory detection ({})Already exists，Please change to the new directory！Program exit]", projectBaseDirNew);
            return;
        }
        // If the new directory exists PACKAGE_NAME，ARTIFACT_ID Etc. Keywords，The path will be replaced，The generated file is not in the expected directory
        if (StrUtil.containsAny(projectBaseDirNew, PACKAGE_NAME, ARTIFACT_ID, StrUtil.upperFirst(ARTIFACT_ID))) {
            log.error("[main][New project directory `projectBaseDirNew` Detection ({}) Conflicting names exist「{}」Or「{}」，Please change to the new directory！Program exit]",
                    projectBaseDirNew, PACKAGE_NAME, ARTIFACT_ID);
            return;
        }
        log.info("[main][Completed new project directory detection，New project path address ({})]", projectBaseDirNew);
        // Get the files to be copied
        log.info("[main][Start getting the files that need to be rewritten，Expected need 10-20 seconds]");
        Collection<File> files = listFiles(projectBaseDir);
        log.info("[main][Number of files that need to be rewritten：{}，Expected need 15-30 seconds]", files.size());
        // Write to file
        files.forEach(file -> {
            // If it is a whitelisted file type，Do not rewrite，Direct copy
            String fileType = getFileType(file);
            if (WHITE_FILE_TYPES.contains(fileType)) {
                copyFile(file, projectBaseDir, projectBaseDirNew, packageNameNew, artifactIdNew);
                return;
            }
            // If the file type is not in the whitelist，Rewrite content，In generating files
            String content = replaceFileContent(file, groupIdNew, artifactIdNew, packageNameNew, titleNew);
            writeFile(file, content, projectBaseDir, projectBaseDirNew, packageNameNew, artifactIdNew);
        });
        log.info("[main][Rewrite completed]Total time：{} seconds", (System.currentTimeMillis() - start) / 1000);
    }

    private static String getProjectBaseDir() {
        String baseDir = System.getProperty("user.dir");
        if (StrUtil.isEmpty(baseDir)) {
            throw new NullPointerException("The project base path does not exist");
        }
        return baseDir;
    }

    private static Collection<File> listFiles(String projectBaseDir) {
        Collection<File> files = FileUtil.loopFiles(projectBaseDir);
        // Remove IDEA、Git Own files、Node Compiled file
        files = files.stream()
                .filter(file -> !file.getPath().contains(separator + "target" + separator)
                        && !file.getPath().contains(separator + "node_modules" + separator)
                        && !file.getPath().contains(separator + ".idea" + separator)
                        && !file.getPath().contains(separator + ".git" + separator)
                        && !file.getPath().contains(separator + "dist" + separator)
                        && !file.getPath().contains(".iml")
                        && !file.getPath().contains(".html.gz"))
                .collect(Collectors.toList());
        return files;
    }

    private static String replaceFileContent(File file, String groupIdNew,
                                             String artifactIdNew, String packageNameNew,
                                             String titleNew) {
        String content = FileUtil.readString(file, StandardCharsets.UTF_8);
        // If it is a whitelisted file type，Do not rewrite
        String fileType = getFileType(file);
        if (WHITE_FILE_TYPES.contains(fileType)) {
            return content;
        }
        // All executable file contents are rewritten
        return content.replaceAll(GROUP_ID, groupIdNew)
                .replaceAll(PACKAGE_NAME, packageNameNew)
                .replaceAll(ARTIFACT_ID, artifactIdNew) // Must be replaced last，Because ARTIFACT_ID Too short！
                .replaceAll(StrUtil.upperFirst(ARTIFACT_ID), StrUtil.upperFirst(artifactIdNew))
                .replaceAll(TITLE, titleNew);
    }

    private static void writeFile(File file, String fileContent, String projectBaseDir,
                                  String projectBaseDirNew, String packageNameNew, String artifactIdNew) {
        String newPath = buildNewFilePath(file, projectBaseDir, projectBaseDirNew, packageNameNew, artifactIdNew);
        FileUtil.writeUtf8String(fileContent, newPath);
    }

    private static void copyFile(File file, String projectBaseDir,
                                 String projectBaseDirNew, String packageNameNew, String artifactIdNew) {
        String newPath = buildNewFilePath(file, projectBaseDir, projectBaseDirNew, packageNameNew, artifactIdNew);
        FileUtil.copyFile(file, new File(newPath));
    }

    private static String buildNewFilePath(File file, String projectBaseDir,
                                           String projectBaseDirNew, String packageNameNew, String artifactIdNew) {
        return file.getPath().replace(projectBaseDir, projectBaseDirNew) // New Directory
                .replace(PACKAGE_NAME.replaceAll("\\.", Matcher.quoteReplacement(separator)),
                        packageNameNew.replaceAll("\\.", Matcher.quoteReplacement(separator)))
                .replace(ARTIFACT_ID, artifactIdNew) //
                .replaceAll(StrUtil.upperFirst(ARTIFACT_ID), StrUtil.upperFirst(artifactIdNew));
    }

    private static String getFileType(File file) {
        return file.length() > 0 ? FileTypeUtil.getType(file) : "";
    }

}
