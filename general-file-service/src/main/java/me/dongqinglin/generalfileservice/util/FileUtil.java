package me.dongqinglin.generalfileservice.util;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

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

    public static void readFileByBytes(File file) {

        InputStream in = null;
        try {

            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            in = new FileInputStream(file);
            // System.out.println("当前字节输入流中的字节数为:" + in.available());
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void readFileByChars(File file) {
        Reader reader = null;
        try {
            char[] tempchars = new char[1024];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            while ((charread = reader.read(tempchars)) != -1) {
                // ignore char \r
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {

                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }




    public static void writeFile(String filePath, String fileData)  {
        if (filePath == null || fileData == null) return;
        // System.out.println(filePath);
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

    public static boolean delete(String path){
        File file = new File(path);
        boolean result = false;
        if(!file.exists()) {System.out.println("不存在此文件或文件夹"); return result;}
        if(file.isFile()) { return deleteFile(path);}
        else if(file.isDirectory()) {return deleteDirectory(path);}
        else { return result;}
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
        // If dir does not end with a file separator, the file separator is automatically added
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
            System.out.println("Zip files complete.");
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

    private static void zipFile(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return;
        byte[] buf = new byte[1024];
        FileInputStream in = null;
        try {
            int len;
            in = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(folderStr + file.getName()));
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.closeEntry();
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void compress(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return;
        // Determine whether it is a directory or a file\
        if (file.isDirectory()) {
            folderStr = file.getName();
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                compress(files[i], out, folderStr + "/");
            }
        } else {
            zipFile(file, out, folderStr);
        }
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
