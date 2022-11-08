package com.github.kael.action;

import com.github.kael.service.InvokerService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * //    private static VirtualFile createPackageDir(String packageName) {
 * //        String path = FileUtil.toSystemIndependentName(StringUtil.replace(packageName, ".", "/"))
 * //        FileUtil.createDirectory(new File(path))
 * //        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path)
 * //    }
 * create in 2022/11/8 21:01
 *
 * @author shishaodong
 * @version 0.0.1
 */
public class InvokerAction extends AnAction {

    private InvokerService invokerService = null;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (Objects.isNull(invokerService)) {
            invokerService = new InvokerService(e.getProject());
        }
        invokerService.createFiles(e);
    }
}
