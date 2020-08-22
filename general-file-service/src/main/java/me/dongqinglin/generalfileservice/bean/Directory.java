package me.dongqinglin.generalfileservice.bean;

import java.util.ArrayList;
import java.util.List;

public class Directory {
    List<String> files = new ArrayList<String>();
    String diretoryName = "";
    List<Directory> directories = new ArrayList<Directory>();

    public Directory() {
    }

    public Directory(List<String> files, String diretoryName, List<Directory> directories) {
        this.files = files;
        this.diretoryName = diretoryName;
        this.directories = directories;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public String getDiretoryName() {
        return diretoryName;
    }

    public void setDiretoryName(String diretoryName) {
        this.diretoryName = diretoryName;
    }

    public Directory addFile(String file){
        if (file == null || file.trim().equals("")) {   return this;    }
        files.add(file);
        return this;
    }

    public Directory deleteFile(String file){
        if (file == null || file.trim().equals("")) {   return this;    }
        files.remove(file);
        return this;
    }

    public Directory addtDirectory(Directory directory){
        if (directory == null) {   return this;   }
        directories.add(directory);
        return this;
    }

    public Directory deleteDirectory(Directory directory){
        if (directory == null) {   return this;   }
        directories.remove(directory);
        return this;
    }


}
