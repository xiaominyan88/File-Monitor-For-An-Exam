package org.examples.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@Component
public class ListenerService implements ListenerServiceInterface {
    @Value("${exam.destination")
    private String des_path;

    @Override
    public void doSomething(File file) throws Exception {

        StringBuilder sb = new StringBuilder();

        if(!file.exists()){
            throw new Exception("file does not exist!");
        }

        handfile(file,sb);

    }

    private void handfile(File file,StringBuilder sb) throws Exception{
        if(file.isDirectory()){
            if("A".equals(file.getName())){
                File[] files = file.listFiles();
                for(File ff : files){
                    if(ff.isDirectory()){
                        recordFileName(ff,sb);
                    }
                }
            }else{
                File[] files1 = file.listFiles();
                for(File ff1 : files1){
                    if(ff1.isDirectory()){
                        handfile(ff1,sb);
                    }
                }
            }
        }else{
            String path = file.getAbsolutePath();
            String[] strings = path.substring(path.indexOf('A') + 2).split("\\\\");
            String newFileName = strings[0] + "_" + strings[1] + "_" + strings[2] + "_" + strings[3];
            executeWrtie(path,newFileName);
        }
    }

    private void recordFileName(File file,StringBuilder sb) throws Exception{
        if(file.isDirectory()){
            String fileName = file.getName();
            sb.append(fileName).append("_");
            File[] files = file.listFiles();
            for(File ff : files){
                recordFileName(ff,sb);
            }
        }else{
            copyFile(file,sb);
        }

    }

    private void copyFile(File file,StringBuilder sb) throws Exception{
        String oldFileName = file.getName();
        String newFileName = sb.toString() + oldFileName;
        executeWrtie(file.getAbsolutePath(),newFileName);
    }

    private void executeWrtie(String sourcePath,String fileNewName) throws Exception{
        FileReader reader = new FileReader(sourcePath);
        FileWriter writer = new FileWriter(des_path + "\\" + fileNewName);
        int ch = 0;
        while((ch = reader.read()) != -1){
            writer.write(ch);
        }
        reader.close();
        writer.close();
    }
}
