package org.examples.main;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.examples.factory.FileListenerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FileListenerRunner implements CommandLineRunner {

    @Autowired
    private FileListenerFactory fileListenerFactory;

    @Override
    public void run(String... args) throws Exception {


        FileAlterationMonitor fileAlterationMonitor = fileListenerFactory.getMonitor();
        try {

            fileAlterationMonitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
