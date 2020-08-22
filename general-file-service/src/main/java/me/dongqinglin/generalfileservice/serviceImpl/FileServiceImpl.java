package me.dongqinglin.generalfileservice.serviceImpl;

import me.dongqinglin.generalfileservice.bean.Directory;
import me.dongqinglin.generalfileservice.bean.FileServiceMessage;
import me.dongqinglin.generalfileservice.bean.Message;
import me.dongqinglin.generalfileservice.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


@Service
public class FileServiceImpl implements FileService {

    @Value("${personal-upload-root}")
    private String UPLOAD_ROOT;

    @Value("${personal-static-root}")
    private String STATIC_ROOT;

    @Override
    public String staticRoot() {
        return STATIC_ROOT;
    }

    @Override
    public String uploadRoot() {
        return UPLOAD_ROOT;
    }

    @Override
    public Directory dir(String folderPath) {
        if (folderPath == null || folderPath.trim().equals("")) {
            System.out.println("参数非法");return null; }
        File pathFile = new File(folderPath);
        if (pathFile.exists()) {
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
        return null;
    }


    @Override
    public Message newFolder(String folderPath) {
        if (folderPath == null || folderPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        File pathFile=new File(folderPath);
        if (pathFile.exists()) { return new FileServiceMessage("新建文件夹成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());}

        try {
            pathFile.mkdirs();
        } catch (Exception e) {
            return new FileServiceMessage("新建文件夹失败，可能是I/O流占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new FileServiceMessage("新建文件夹成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    @Override
    public Message newFile(String filePath, String fileData) {
        if (filePath == null || filePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (fileData == null || fileData.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }

        File file=new File(filePath);
        String fileFolderStr = null;
        try {
            fileFolderStr = filePath.substring(0, filePath.lastIndexOf('\\'));
        } catch (Exception e) {
            try {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('/'));
            } catch (Exception e1) {
                fileFolderStr = "";
            }
        }
        if((newFolder(fileFolderStr).getStatus() != FileServiceMessage.FileStatusEnum.SUCCESS.ordinal())){
            return newFolder(fileFolderStr);}
        if(file.exists()){
            return new FileServiceMessage("新建文件失败，因为文件已经存在", FileServiceMessage.FileStatusEnum.EXIST_FILE.ordinal());}
        try {
            file.createNewFile();
            FileOutputStream out=new FileOutputStream(file,true);
            StringBuffer sb=new StringBuffer(fileData);
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        } catch (IOException e) {
            return new FileServiceMessage("新建文件失败，可能是I/O流占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new FileServiceMessage("新建文件成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    @Override
    public Message editFile(String filePath, String fileData) {
        if (filePath == null || filePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (fileData == null || fileData.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }

        File file=new File(filePath);
        String fileFolderStr = null;
        try {
            fileFolderStr = filePath.substring(0, filePath.lastIndexOf('\\'));
        } catch (Exception e) {
            try {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('/'));
            } catch (Exception e1) {
                fileFolderStr = "";
            }
        }
        if((newFolder(fileFolderStr).getStatus() != FileServiceMessage.FileStatusEnum.SUCCESS.ordinal())){
            return newFolder(fileFolderStr);}
        if(!file.exists()){ delete(fileFolderStr);return new FileServiceMessage("要修改的文件不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        try {
            FileOutputStream out=new FileOutputStream(file,false);
            StringBuffer sb=new StringBuffer(fileData);
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        } catch (IOException e) {
            return new FileServiceMessage("修改文件失败，可能是I/O流占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new FileServiceMessage("修改文件成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }


    @Override
    public Message view(String filePath) {
        if (filePath == null || filePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal()); }
        File file = new File(filePath);

        if (!file.exists()) { return new FileServiceMessage("文件不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        Reader reader = null;
        StringBuilder sb = null;
        try {
            char[] tempchars = new char[1024];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            sb = new StringBuilder();
            while ((charread = reader.read(tempchars)) != -1) {
                // ignore char \r
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {

                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            sb.append(tempchars[i]);
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
        return new FileServiceMessage(sb.toString(), FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    @Override
    public Message rename(String originSourceName, String newSourceName) {
        if (originSourceName == null || originSourceName.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (newSourceName == null || newSourceName.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        File file = new File(originSourceName);
        File newFile = new File(newSourceName);
        if (!file.exists()) { return new FileServiceMessage("文件不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        if (newFile.exists()) { return new FileServiceMessage("文件名重复", FileServiceMessage.FileStatusEnum.EXIST_FILE.ordinal());}
        if(file.renameTo(newFile))
            return new FileServiceMessage("重命名成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());

        return new FileServiceMessage("重命名失败，可能是I/O流被占用,或者不能重命名路径内父路径", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
    }
    @Override
    public Message delete(String sourcePath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        File file = new File(sourcePath);
        boolean flag = false;
        if(!file.exists()) {return new FileServiceMessage("文件或文件夹不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        if(file.isFile()) { flag = deleteFile(sourcePath);}
        else if(file.isDirectory()) {flag =  deleteDirectory(sourcePath);}

        if (flag) {
            return new Message("删除成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
        } else {
            return new FileServiceMessage("重命名失败，可能是I/O流被占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }

    }

    @Override
    public Message copy(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        System.out.println(sourcePath +"|" +targetPath);
        File file = new File(sourcePath);
        File newfile = new File(targetPath);
        System.out.println(file.isDirectory() + "|" + file.isFile());
        System.out.println(newfile.isDirectory() + "|" + newfile.isFile());
        if(!file.exists()) {return new FileServiceMessage("文件或文件夹不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        Message result = null;
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        if(file.isDirectory()){
            System.out.println( "目录目录");
            if (!targetPath.endsWith(File.separator)) {targetPath = targetPath + File.separator + file.getName() + File.separator;
                newfile = new File(targetPath);}
            newfile.mkdirs();
            File[] files = file.listFiles();
            for (File tempFile: files) {
                File tempTargetFile = new File(targetPath + tempFile.getName());
                if (!copyFileSync(tempFile, tempTargetFile))
                    return new FileServiceMessage("复制失败，可能是I/O流被占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
            }
        }else if(file.isFile()){
            System.out.println( "文件文件");
            String fileType = file.getName().substring(file.getName().indexOf("."));
            System.out.println(fileType);
            if(!targetPath.endsWith(fileType)){
                if (!targetPath.endsWith(File.separator)) targetPath = targetPath + File.separator;
                newfile = new File(targetPath + file.getName());
            }
            System.out.println(file.getAbsoluteFile() + "|" + newfile.getAbsolutePath());
            if (!copyFileSync(file, newfile))
                return new FileServiceMessage("复制失败，可能是I/O流被占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }

        return new Message("复制成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    private boolean copyFileSync(File file, File newfile) {
        try {
            Files.copy(file.toPath(), newfile.toPath());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Message cut(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        Message copyMessage = copy(sourcePath, targetPath);
        if (copyMessage.getStatus() != FileServiceMessage.FileStatusEnum.SUCCESS.ordinal()) {
            return copyMessage;
        }else {
            Message deleteMessage = delete(sourcePath);
            if(deleteMessage.getStatus() != FileServiceMessage.FileStatusEnum.SUCCESS.ordinal()) {
                return deleteMessage;
            }
        }
        return new Message("剪切成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }


    @Override
    public Message zip(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (!zipFiles(sourcePath, targetPath)) {
            return new Message("压缩失败, 可能是I/O流占用或打印的语句", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new Message("压缩成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    @Override
    public Message unzip(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (!unzipFiles(sourcePath, targetPath)) {
            return new Message("解压缩失败, 可能是I/O流占用或打印的语句", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new Message("解压缩成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    @Override
    public Message upload(MultipartFile[] files, String targetPath) {
        if (files == null || files.length == 0) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        System.out.println(targetPath);

        if (targetPath.indexOf(".")!= -1){
            targetPath.replaceAll(".", "");}
        if (!targetPath.endsWith(File.separator)) targetPath = targetPath + File.separator;
        System.out.println(targetPath);

        new File(targetPath).mkdirs();
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                // System.out.println(fileName);
                // System.out.println(targetPath + fileName);

                file.transferTo(new File(targetPath + fileName));
            } catch (IOException e) {
                return new Message("上传失败, 可能是路径错误, 路径为" + targetPath, FileServiceMessage.FileStatusEnum.ERROR.ordinal());
            }
        }
        return new Message("上传成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }


    private boolean deleteFile(String fileName) {
        File file = new File(fileName);
        boolean result = false;
        if(!file.exists()) {System.out.println("不存在此文件"); return result;}
        if(!file.isFile()) {System.out.println("期待是文件，而不是文件夹"); return result;}
        file.delete();
        result = true;
        return result;
    }

    private boolean deleteDirectory(String dir) {
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

    private boolean unzipFiles(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) {
            System.out.println("参数sourcePath非法");return false;  }
        if (targetPath == null || targetPath.trim().equals("")) {
            System.out.println("参数targetPath非法");return false;  }
        if (!sourcePath.endsWith(".zip")) {
            sourcePath = sourcePath + ".zip";}
        File sourceFile = new File(sourcePath);
        File targetPathFile = new File(targetPath);
        if(!sourceFile.exists()){
            System.out.println("文件不存在:"+ sourcePath);return false; }
        if(!targetPathFile.isDirectory()){
            System.out.println("解压目标路径不是目录:" + targetPath);return false; }
        boolean result = false;
        try {
            ZipFile zipFile = new ZipFile(sourcePath);
            Enumeration emu = zipFile.entries();
            int i=0;
            while(emu.hasMoreElements()){
                int BUFFER = 1024;
                ZipEntry entry = (ZipEntry)emu.nextElement();
                //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(targetPath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(targetPath + entry.getName());
                //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                //而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if(parent != null && (!parent.exists())){
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);
                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean zipFiles(String sourceFilePath,String targetFilePath) {
        if(sourceFilePath == null || targetFilePath == null) return false;

        boolean result = false;
        ZipOutputStream out = null;
        File sourceFile = new File(sourceFilePath);
        if(!sourceFile.exists()) {
            System.out.println("该路径不存在"+ sourceFilePath); return false;
        }
        File targetFile = new File(targetFilePath); // .. zip
        if (!targetFilePath.endsWith(".zip")) {
            if (!targetFilePath.endsWith(File.separator)) {
                targetFilePath = targetFilePath + sourceFile.getName() + ".zip";
                targetFile = new File(targetFilePath);
            }
         }
        try {
            out = new ZipOutputStream(new FileOutputStream(targetFile));
            if(sourceFile.isFile()){
                result = zipFile(sourceFile, out, "");
            } else if(sourceFile.isDirectory()){
                File[] list = sourceFile.listFiles();
                result = true;
                for (int i = 0; i < list.length; i++) {
                    result = result && compress(list[i], out, "");
                }

            }else{
                System.out.println("该路径既不是文件，也不是文件夹，可能不存在");
                return result;
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
        return result;
    }

    private boolean zipFile(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return false;
        boolean result = false;
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
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean compress(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return false;
        boolean result = false;
        // Determine whether it is a directory or a file\
        if (file.isDirectory()) {
            folderStr = file.getName();
            File[] files = file.listFiles();
            result = true;
            for (int i = 0; i < files.length; i++) {
                result = result && compress(files[i], out, folderStr + "/");
            }
        } else {
            result = zipFile(file, out, folderStr);
        }
        return result;
    }
}
