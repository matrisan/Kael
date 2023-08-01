package com.matrixboot.kael.common;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Map;


/**
 * create in 2022/11/8 20:12
 *
 * @author shishaodong
 * @version 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringCommon {

    public static final Map<String, String> STRING_MAP = Maps.newHashMap();

    private static final String APPLICATION = "application";

    private static final String DOMAIN = "domain";

    private static final String INFRASTRUCTURE = "infrastructure";

    private static final String INTERFACES = "interfaces";

    static {
        STRING_MAP.put(APPLICATION, "package-application.ftl");
        STRING_MAP.put(APPLICATION + File.separator + "service", "package-application-service.ftl");
        STRING_MAP.put(APPLICATION + File.separator + "task", "package-application-task.ftl");

        STRING_MAP.put(DOMAIN, "package-domain.ftl");
        STRING_MAP.put(DOMAIN + File.separator + "entity", "package-domain-entity.ftl");
        STRING_MAP.put(DOMAIN + File.separator + "repository", "package-domain-repository.ftl");
        STRING_MAP.put(DOMAIN + File.separator + "service", "package-domain-service.ftl");

        STRING_MAP.put(INFRASTRUCTURE, "package-infrastructure.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common", "package-infrastructure-common.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "command", "package-infrastructure-common-command.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "constant", "package-infrastructure-common-constant.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "query", "package-infrastructure-common-query.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "result", "package-infrastructure-common-result.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "event", "package-infrastructure-common-event.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "common" + File.separator + "view", "package-infrastructure-common-view.ftl");

        STRING_MAP.put(INFRASTRUCTURE + File.separator + "config", "package-infrastructure-config.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "annotation", "package-infrastructure-annotation.ftl");

        STRING_MAP.put(INFRASTRUCTURE + File.separator + "exception", "package-infrastructure-exception.ftl");
        STRING_MAP.put(INFRASTRUCTURE + File.separator + "exception"+ File.separator + "handler", "package-infrastructure-exception-handler.ftl");

        STRING_MAP.put(INFRASTRUCTURE + File.separator + "validator", "package-infrastructure-validator.ftl");

        STRING_MAP.put(INTERFACES, "package-interfaces.ftl");
        STRING_MAP.put(INTERFACES + File.separator + "assemble", "package-interfaces-assemble.ftl");
        STRING_MAP.put(INTERFACES + File.separator + "facade", "package-interfaces-facade.ftl");
    }
}
