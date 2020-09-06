package me.dongqinglin.generalfileservice.study;

import me.dongqinglin.generalfileservice.bean.Directory;

import java.io.File;
import java.util.ArrayList;

public class DiretoryTest {
    public static Directory dir(String folderPath) {
        if (folderPath == null || folderPath.trim().equals("")) {
            System.out.println("参数非法");return null; }
        File pathFile = new File(folderPath);
        if (!pathFile.exists()) {
            System.out.println("文件不存在");return null; }
        if (pathFile.isFile()) {
            System.out.println("期待是文件夹，而不是文件");return null; }

        Directory result = new Directory();
        ArrayList<String> allOfFile = new ArrayList<String>();
        result.setDiretoryName(pathFile.getName());

        try {
            File files[] = pathFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    Directory chirdResult = dir(file.getAbsolutePath());
                    result.addtDirectory(chirdResult);
                } else {
                    result.addFile(file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        Directory dir = dir("D:\\设计-ppt\\练习");
        System.out.println(dir);
    }
}
