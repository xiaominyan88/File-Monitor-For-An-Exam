package org.examples.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.examples.service.ListenerService;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {

    private ListenerService listenerService;

    public FileListener(ListenerService listenerService) {
        this.listenerService = listenerService;
    }


    @Override
    public void onFileCreate(File file) {

        try {
            listenerService.doSomething(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFileChange(File file) {

        System.out.println("文件创建修改！");

        try {
            listenerService.doSomething(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 文件创建删除
    @Override
    public void onFileDelete(File file) {
    }

    // 目录创建
    @Override
    public void onDirectoryCreate(File directory) {
    }

    // 目录修改
    @Override
    public void onDirectoryChange(File directory) {
    }

    // 目录删除
    @Override
    public void onDirectoryDelete(File directory) {
    }


    // 轮询开始
    @Override
    public void onStart(FileAlterationObserver observer) {
    }

    // 轮询结束
    @Override
    public void onStop(FileAlterationObserver observer) {
    }
}
