package me.dongqinglin.auto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static void writeFile(String filePath, String fileData)  {
        if (filePath == null || fileData == null) return;
//        System.out.println(filePath);
        try {
            File file=new File(filePath);
            String fileFolderStr = null;
            try {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('\\'));
            } catch (Exception e) {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('/'));
            }
            makeDir(fileFolderStr);
            if(!file.exists()){ file.createNewFile();}
            FileOutputStream out=new FileOutputStream(file,true);
            StringBuffer sb=new StringBuffer(fileData);
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDir(String path) {
        try {
            File dir=new File(path);
            if(!dir.exists()){
                dir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> dirAll(String path) {
        if(path == null) return null;
        ArrayList<String> allOfFile = new ArrayList<String>();
        File pathFile = new File(path);
        if (pathFile.exists()) {
            try {
                File files[] = pathFile.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        dirAll(file.getAbsolutePath());
                    } else {
                        allOfFile.add(file.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allOfFile;
    }

    private static void zipFile(File sourceFilePath, ZipOutputStream out, String basedir) {
        if (!sourceFilePath.exists()) return;
        byte[] buf = new byte[1024];
        FileInputStream in = null;
        try {
            int len;
            in = new FileInputStream(sourceFilePath);
            out.putNextEntry(new ZipEntry(basedir + sourceFilePath.getName()));
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.closeEntry();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void compress(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) return;
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                /* 递归 */
                compress(files[i], out, basedir + file.getName() + "/");
            }
        } else {
            zipFile(file, out, basedir);
        }
    }

    public static void zipFiles(String sourceFilePath,String targetFilePath) {
        if(sourceFilePath == null || targetFilePath == null) return;
        ZipOutputStream out = null;
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath); // .. zip
        try {
            out = new ZipOutputStream(new FileOutputStream(targetFile));
            if(sourceFile.isFile()){
                zipFile(sourceFile, out, "");
            } else{
                File[] list = sourceFile.listFiles();
                for (int i = 0; i < list.length; i++) {
                    compress(list[i], out, "");
                }
            }
            System.out.println("压缩完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        boolean result = false;
        if(!file.exists()) {System.out.println("不存在此文件"); return result;}
        if(!file.isFile()) {System.out.println("期待是文件，而不是文件夹"); return result;}
        file.delete();
        result = true;
        return result;
    }

    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) dir = dir + File.separator;
        File dirFile = new File(dir);
        boolean result = false;
        if(!dirFile.exists()) {System.out.println("不存在此文件夹"); return result;}
        if(!dirFile.isDirectory()) {System.out.println("期待是文件夹，而不是文件"); return result;}
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.isFile()) file.delete();
            if(file.isDirectory()) deleteDirectory(file.getAbsolutePath());
        }
        dirFile.delete();
        result = true;
        return  result;
    }



//    public static void main(String[] args) {
//        String filePath = "D:\\uploadFilePath\\tempFile";
////        String targetPath = "D:\\1.zip";
////        String fileData = "haha";
////        writeFile(filePath, fileData);
////        zipFiles(filePath,targetPath);
////        System.out.println();
//        //创建Timer
//        final Timer timer = new Timer();
//        //设定定时任务
//        timer.schedule(new TimerTask() {
//            //定时任务执行方法
//            @Override
//            public void run() {
//                deleteDirectory(filePath);
//                System.out.println("success");
//                System.exit(0);
//            }
//        }, 5000);
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(i);
//        }
//
//    }
}
