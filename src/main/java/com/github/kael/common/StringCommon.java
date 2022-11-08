package com.github.kael.common;

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

    public static final Map<String, String> MAP = Maps.newHashMap();

    private static final String DOMAIN = "domain";

    static {
        MAP.put("application" + File.separator + "service", "package-application-service.ftl");
        MAP.put("application" + File.separator + "task", "package-application-task.ftl");
        MAP.put(DOMAIN + File.separator + "entity", "package-domain-entity.ftl");
        MAP.put(DOMAIN + File.separator + "repository", "package-domain-repository.ftl");
        MAP.put(DOMAIN + File.separator + "service", "package-domain-service.ftl");
        MAP.put("infrastructure" + File.separator + "common", "package-infrastructure-common.ftl");
        MAP.put("infrastructure" + File.separator + "config", "package-infrastructure-config.ftl");
        MAP.put("interfaces" + File.separator + "assemble", "package-interfaces-assemble.ftl");
        MAP.put("interfaces" + File.separator + "facade", "package-interfaces-facade.ftl");
    }
}
