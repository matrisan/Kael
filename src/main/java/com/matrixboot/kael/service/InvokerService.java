package com.matrixboot.kael.service;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Maps;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.matrixboot.kael.config.FreemarkerConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static com.matrixboot.kael.common.StringCommon.STRING_MAP;

/**
 * create in 2022/11/8 21:16
 *
 * @author shishaodong
 * @version 0.0.1
 */
@Slf4j
@Service(Service.Level.PROJECT)
public final class InvokerService {

    private final Project project;

    public InvokerService(Project project) {
        this.project = project;
    }

    public void createFiles(AnActionEvent e) {
        tryCreateReadme(e);
        tryCreateDomain(e);
    }

    public void tryCreateReadme(AnActionEvent e) {
        String readme = project.getBasePath() + File.separator + "README.adoc";
        if (!FileUtil.exist(readme)) {
            doWriteFile(readme, "readme.ftl", readmeData(e));
        }
    }

    public void tryCreateDomain(AnActionEvent e) {
        STRING_MAP.forEach((path, template) -> {
            VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
            assert virtualFile != null;
            String filePath = virtualFile.getPath() + File.separator + path + File.separator + "package-info.java";
            doWriteFile(filePath, template, domainData(e, virtualFile, path));
        });
    }

    private void doWriteFile(String filePath, String templateName, Map<String, String> data) {
        Template template = FreemarkerConfiguration.getClassPathTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        try {
            template.process(data, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        FileUtil.writeString(stringWriter.toString(), new File(filePath), StandardCharsets.UTF_8);
        LocalFileSystem.getInstance().refreshAndFindFileByPath(filePath);
    }

    private @NotNull Map<String, String> readmeData(@NotNull AnActionEvent e) {
        Map<String, String> data = Maps.newHashMap();
        data.put("projectName", Objects.isNull(e.getProject()) ? "" : getProjectName(e.getProject().getBasePath()));
        data.put("version", Objects.isNull(e.getProject()) ? "" : getVersion(e.getProject().getBasePath()));
        data.put("systemUsername", System.getProperty("user.name"));
        return data;
    }

    private @NotNull Map<String, String> domainData(@NotNull AnActionEvent e, VirtualFile virtualFile, String dir) {
        Map<String, String> map = Maps.newHashMap();
        String replace = StringUtils.replace(dir, "/", ".");
        map.put("reference", getPackageReference(virtualFile) + "." + replace);
        map.put("version", Objects.isNull(e.getProject()) ? "" : getVersion(e.getProject().getBasePath()));
        map.put("systemUsername", System.getProperty("user.name"));
        return map;
    }

    public String getPackageReference(@NotNull VirtualFile virtualFile) {
        String data = StringUtils.substringAfter(virtualFile.getPath(), "src/main/java/");
        return StringUtils.replace(data, "/", ".");
    }

    private Model model;

    private String getProjectName(String path) {
        initModel(path);
        return model.getName();
    }

    private String getVersion(String path) {
        initModel(path);
        return model.getVersion();
    }

    private void initModel(String path) {
        if (Objects.isNull(model)) {
            extracted(path + File.separator + "pom.xml");
        }
    }

    private void extracted(String pomPath) {
        if (FileUtil.exist(pomPath)) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(pomPath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            MavenXpp3Reader reader = new MavenXpp3Reader();
            try {
                model = reader.read(fis);
            } catch (IOException | XmlPullParserException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
