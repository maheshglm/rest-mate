package com.gummarajum.automation.restmate.utils;

import com.google.common.base.Strings;
import com.gummarajum.automation.restmate.ApiException;
import com.gummarajum.automation.restmate.ApiExceptionType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FileDirUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDirUtils.class);

    public static final String UTF_8 = "UTF-8";

    public String readFileToString(final String filename) {
        try {
            return FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("readFileToString():failed to read file to string [{}]", filename);
            throw new ApiException(ApiExceptionType.PROCESSING_FAILED, "readFileToString():failed to read file to string [{}]", filename);
        }
    }

    public void writeStringToFile(final String filename, final String strToWrite) {
        try {
            FileUtils.writeStringToFile(new File(filename), strToWrite, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("writeStringToFile():failed to write string to file [{}]", filename);
            throw new ApiException(ApiExceptionType.PROCESSING_FAILED, "writeStringToFile():failed to write string to file [{}]", filename);
        }
    }

    public void copyFile(File src, File dst) {
        try {
            FileUtils.copyFile(src, dst);
        } catch (IOException e) {
            LOGGER.error("failed to copy file");
            throw new ApiException(ApiExceptionType.PROCESSING_FAILED, "failed copy file");
        }
    }

    public String getClassAbsolutePath(Class class1) {
        return class1.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    public boolean verifyFileExists(final String path){
        if(Strings.isNullOrEmpty(path)){
            LOGGER.error("file path must be specified");
            throw new ApiException(ApiExceptionType.UNSATISFIED_IMPLICIT_ASSUMPTION,"file path must be specified");
        }
        final File file = new File(path);
        return file.exists() && file.isFile();
    }

    public boolean verifyDirExists(final String path){
        if(Strings.isNullOrEmpty(path)){
            LOGGER.error("file path must be specified");
            throw new ApiException(ApiExceptionType.UNSATISFIED_IMPLICIT_ASSUMPTION,"file path must be specified");
        }
        final File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public String addPrefixIfNotAbsolute(final String filename, final String prefix){
        String result = null;

        if(filename != null){
            File file = new File(filename);
            if(!file.isAbsolute()){
                result = prefix + '/' + filename;
            }else{
                result = filename;
            }
        }
        return result;
    }



}
