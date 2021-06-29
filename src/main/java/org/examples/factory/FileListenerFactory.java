package org.examples.factory;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.examples.listener.FileListener;
import org.examples.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Component
public class FileListenerFactory {

    @Value("${exam.source}")
    private String monitorDir;

    private final long interval = TimeUnit.SECONDS.toMillis(1);

    @Autowired
    private ListenerService listenerService;

    public FileAlterationMonitor getMonitor() {

        IOFileFilter directories = FileFilterUtils.and(
//                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".log"),
                FileFilterUtils.suffixFileFilter(".doc"),
                FileFilterUtils.suffixFileFilter(".xls"),
                FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        File file = new File(monitorDir);

        FileAlterationObserver observer = new FileAlterationObserver(file, filter);


        observer.addListener(new FileListener(listenerService));

        return new FileAlterationMonitor(interval, observer);
    }
}
