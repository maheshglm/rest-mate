package com.gummarajum.automation.restmate.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class WorkspaceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceUtils.class);

    private String baseDir;

    public WorkspaceUtils() {
        if ("true".equals(System.getProperty("cart.relative.path"))) {
            String absolutePath = Paths.get(".").toAbsolutePath().normalize().toString();
            baseDir = absolutePath.replaceAll("\\\\", "/");
        } else {
            baseDir = "/users";
        }
        String userSpecifiedBaseDir = System.getProperty("cart.basedir");
        if (userSpecifiedBaseDir != null && !"".equals(userSpecifiedBaseDir)) {
            baseDir = userSpecifiedBaseDir;
        }
        LOGGER.info("Basedir set as:[{}]", baseDir);
    }

    public String getBaseDir() {
        return baseDir;
    }

    public String getConfigDir() {
        return baseDir + "/config";
    }

    public String getTestDataDir() {
        return baseDir + "/tests/testdata";
    }

    public String getTestEvidenceDir() {
        return baseDir + "/testout/evidence";
    }

    public String getFeaturesDir() {
        return baseDir + "/tests/features";
    }

    public String getObjectProprDir() {
        return baseDir + "/objectprops";
    }
}
