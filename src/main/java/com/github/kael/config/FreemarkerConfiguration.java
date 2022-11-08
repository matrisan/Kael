package com.github.kael.config;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * create in 2022/11/8 23:23
 *
 * @author shishaodong
 * @version 0.0.1
 */
public class FreemarkerConfiguration extends Configuration {

    private static final String ENCODING = "UTF-8";

    private static final FreemarkerConfiguration FREEMARKER = new FreemarkerConfiguration("/template");


    public static Template getClassPathTemplate(String ftl) {
        try {
            return FREEMARKER.getTemplate(ftl, ENCODING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FreemarkerConfiguration(String basePackagePath) {
        super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        setDefaultEncoding(ENCODING);
        setClassForTemplateLoading(getClass(), basePackagePath);
    }
}
