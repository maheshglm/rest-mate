package com.gummarajum.automation.restmate.svc;

import com.gummarajum.automation.restmate.utils.FileDirUtils;
import com.gummarajum.automation.restmate.utils.WorkspaceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceDirSvc {

    @Autowired
    private WorkspaceUtils workspaceUtils;

    @Autowired
    private FileDirUtils fileDirUtils;

    public String normalize(final String path){
        String workspaceBaseDir = workspaceUtils.getBaseDir();
        return fileDirUtils.addPrefixIfNotAbsolute(path, workspaceBaseDir);
    }
}
