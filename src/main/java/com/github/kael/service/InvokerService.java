package com.github.kael.service;

import cn.hutool.core.io.FileUtil;
import com.github.kael.config.FreemarkerConfiguration;
import com.google.common.collect.Maps;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.github.kael.common.StringCommon.MAP;

/**
 * create in 2022/11/8 21:16
 *
 * @author shishaodong
 * @version 0.0.1
 */
@Slf4j
@Service
public final class InvokerService {

    private final Project project;

    public InvokerService(Project project) {
        this.project = project;
    }


    public void createFiles(AnActionEvent e) {
        tryCreateReadme();
        tryCreateDomain(e);
    }


    public void tryCreateReadme() {
        String readme = project.getBasePath() + File.separator + "README.adoc";
        if (!FileUtil.exist(readme)) {
            doWriteFile(readme, "readme.ftl", readmeData());
        }
    }


    public void tryCreateDomain(AnActionEvent e) {
        MAP.forEach((k, v) -> {
            VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
            assert virtualFile != null;
            String filePath = virtualFile.getPath() + File.separator + k + File.separator + "package-info.java";
            doWriteFile(filePath, v, domainData(virtualFile, k));
        });
    }

    private void doWriteFile(String filePath, String templateName, Map<String, String> data) {
        try {
            Template template = FreemarkerConfiguration.getClassPathTemplate(templateName);
            StringWriter stringWriter = new StringWriter();
            template.process(data, stringWriter);
            FileUtil.writeString(stringWriter.toString(), new File(filePath), StandardCharsets.UTF_8);
            LocalFileSystem.getInstance().refreshAndFindFileByPath(filePath);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private static @NotNull Map<String, String> readmeData() {
        return Maps.newHashMap();
    }

    private static @NotNull Map<String, String> domainData(VirtualFile virtualFile, String dir) {
        Map<String, String> map = Maps.newHashMap();
        String replace = StringUtils.replace(dir, "/", ".");
        map.put("reference", getPackageReference(virtualFile) + "." + replace);
        return map;
    }

    public static String getPackageReference(@NotNull VirtualFile virtualFile) {
        String data = StringUtils.substringAfter(virtualFile.getPath(), "src/main/java/");
        return StringUtils.replace(data, "/", ".");
    }

}
