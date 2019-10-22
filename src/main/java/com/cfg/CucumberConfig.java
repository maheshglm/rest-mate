package com.cfg;

public class CucumberConfig {

    public static String[] getCucumberOptions(){
        return new String[]{
                "--strict",
                "--monochrome",
                "--glue",
                "glue",
                "--threads",
                "2",
                "--plugin",
                "pretty",
                "--plugin",
                "json:./testout/reports/report.json",
                "./tests/features"
        };
    }
}
