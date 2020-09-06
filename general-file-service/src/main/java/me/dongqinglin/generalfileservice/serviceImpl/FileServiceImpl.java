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
/**
 * This class is the  of file service. File service refers to file operations such as creating new folders, moving and copying.
 * This class uses member methods to define different behaviors, and strictly defines the parameters of each method.
 * Some parameters have been optimized, so that these illegal parameters can be corrected and the fault tolerance rate is increased.
 * This type of file operation includes, folder listing, new folder, new file, edit file, preview file, rename file or folder,
 * delete file or folder, copy file or folder, move file or folder, compress File or folder, unzip the file or folder, upload the file to the specified folder.
 * It should be noted that the compression format currently only supports .zip files,
 * corresponding to `dir`, `newFolder`, `newFile`, `editFile`, `view`, `rename`, `copy`, `cut`, `zip` , `unzip`, `upload`.
 *
 * 本类是文件服务的实现类，文件服务指新建文件夹、移动和复制等文件操作。本类使用了成员方法来定义不同的行为，并严格定义了各个方法的参数，
 * 部分参数做了优化处理，使得这部分非法的参数得以校正，增加容错率。
 * 本类的文件操作包括，文件夹列举，新建文件夹，新建文件，编辑文件，预览文件，重命名文件或文件夹，删除文件或文件夹，复制文件或文件夹，移动文件或文件夹，
 * 压缩文件或文件夹，解压缩文件或文件夹，上传文件到指定文件夹。
 * 需要注意的是压缩格式目前只支持.zip文件。
 * 分别对应`dir`，`newFolder`，`newFile`，`editFile`，`view`，`rename`，`copy`，`cut`，`zip`，`unzip`，`upload`。
 *
 * All input parameters are filtered by the parameter filter, which solves the problem of parameter null values and avoids some null pointer exceptions.
 * After the parameter filter finds a parameter with a null value, it returns as a FileMessage class object,which will return illegal parameters and
 * provide information.  For some methods that cannot return the FileMessage object due to the return value, a compensation measure is used to
 * print the error to the console. In the future, it may be replaced with log service.
 *
 * 所有输入的参数都经过了参数过滤器的过滤，这解决了参数的空值问题，避免了一部分空指针异常。
 * 参数过滤器发现参数空值后，返回为FileMessage类对象，这个对象会返回非法参数和提信息。
 * 有部分由于返回值原因无法返回FileMessage对象的方法，则使用了一种补偿措施，将错误打印至控制台。
 * 在未来，可能会被替换成日志服务。
 *
 */

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

    /**
     * The enumeration method is a common method for file services. This method uses recursion to enumerate all the nesting levels of the file system and
     * assigns data to an object of the Directory class, which can store data through a nested data structure.
     * This method filters out the case where the parameter is empty, the folder does not exist, not the folder but the file, and
     * the above three cases will be null, indicating that the enumeration fails.
     * After filtering and operation, it returns the local variable result, which is an object of the Dieactory class.
     *
     * 列举方法是文件服务的常见方法，本方法使用递归将文件系统的嵌套层级全部列举，将数据赋值给Directory类的对象，该对象可以通过嵌套数据结构保存数据。
     * 本方法过滤了参数空值，文件夹不存在，不是文件夹而是文件的情况，上面三种情况都放会null，表示列举失败。
     * 经过过滤和操作最终返回局部变量result，该局部变量是Dieactory类的一个对象。
     * @param folderPath
     * @return
     */
    @Override
    public Directory dir(String folderPath) {
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

    /**
     * The new folder method is a common method for file services. This method uses the `mkdirs()` method of the File class to create a folder.
     * This method filters the parameter null value, the folder already exists, the above parameter null value will return the FileServiceMessage object,
     * which contains the error type and prompt information. If the folder already exists, it will return the FileServiceMessage object, but it will return the success type.
     * This is because the folder exists, so there is no need to create a folder. And even if the method does not create a folder,
     * the folder already exists and has been implemented. For the purpose of this method, success information can be returned.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 新建文件夹方法是文件服务的常见方法，本方法使用File类的`mkdirs()`方法创建文件夹。
     * 本方法过滤了参数空值，文件夹已存在，上述参数空值会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。而文件夹已存在则会返回FileServiceMessage对象，但是会返回成功类型，这是由于文件夹存在，那么就没有必要创建文件夹，而且即使本方法没有创建文件夹，该文件夹也已经存在，已经实现了本方法的目的，因此可以返回成功信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param folderPath
     * @return
     */
    @Override
    public Message newFolder(String folderPath) {
        if (folderPath == null || folderPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        File pathFile=new File(folderPath);
        if (pathFile.exists()) { return new FileServiceMessage("文件夹已存在，不需要创建", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());}

        try {
            pathFile.mkdirs();
        } catch (Exception e) {
            return new FileServiceMessage("新建文件夹失败，可能是I/O流占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new FileServiceMessage("新建文件夹成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    /**
     * The new file method is a common method of file service. This method uses the `write()` method of the FileOutputStream class to create a file.
     * This method filters out the parameter null value and the folder does not exist, the above parameter null value will return the FileServiceMessage object,
     * which contains the error type and prompt information. If the folder does not exist, the new folder operation will be executed.
     * This is because the existence of the folder will affect the I/O execution operation. In order to improve the fault tolerance, the method will create a new file folder.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     * It is worth mentioning that the file uses UTF-8 encoding.
     *
     * 新建文件方法是文件服务的常见方法，本方法使用FileOutputStream类的`write()`方法创建文件。
     * 本方法过滤了参数空值，文件夹不存在，上述参数空值会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 而文件夹不存在则会执行新建文件夹操作，这是由于文件夹的存在于否会影响I/O的执行操作，为了提高容错率，因此本方法将新建文件的文件夹。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * 值得一提的是，文件使用了UTF-8编码。
     * @param filePath
     * @param fileData
     * @return
     */
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

    /**
     * The file modification method is a common method for file services, and this method is equivalent to the new file method in this class.
     *
     * 修改文件方法是文件服务的常见方法，本方法与本类中的新建文件方法等同。
     * @param filePath
     * @param fileData
     * @return
     */
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

    /**
     * The file preview method is a common method of file service. This method uses the InputStreamReader class to
     * encapsulate the FileInputStream class and then reads the file in a character stream and stitches it.
     * This method filters the parameter null value, the file does not exist, the above parameter null value and
     * the file does not exist will return the FileServiceMessage object, which contains the error type and prompt information.
     * After filtering and operation, the FileServiceMessage object is finally returned,
     * which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 预览文件方法是文件服务的常见方法，本方法使用InputStreamReader类封装FileInputStream类后按字符流读出并拼接的方式来读取文件。
     * 本方法过滤了参数空值，文件不存在，上述参数空值和文件不存在都会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param filePath
     * @return
     */
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
                    return new FileServiceMessage("预览文件失败，可能是I/O流占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
                }
            }
        }
        return new FileServiceMessage(sb.toString(), FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    /**
     * The rename method is a common method of file services. This method uses the `renameTo` method of the File class to rename.
     * This method filters out the parameter null value, the file which is waiting for renaming does not exist, and the renamed file already exists,
     * the above three cases will all return the FileServiceMessage object, which contains the error type and prompt information.
     * After filtering and operation, the FileServiceMessage object is finally returned,
     * which returns the success type and prompt information. I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 重命名方法是文件服务的常见方法，本方法使用File类的`renameTo`方法来重命名。
     * 本方法过滤了参数空值，代更名文件不存在，以及更名后的文件已存在，上述三种情况都会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param originSourceName
     * @param newSourceName
     * @return
     */
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

    /**
     * The delete method is a common method of file services. This method uses the `delete` method of the File class to delete,
     * and uses recursively to delete all contents in the directory.
     * This method filters the parameter null value, the file does not exist, the above two cases will return the FileServiceMessage object,
     * which contains the error type and prompt information.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 删除方法是文件服务的常见方法，本方法使用File类的`delete`方法来进行删除，并使用了递归删除目录中的所有内容。
     * 本方法过滤了参数空值，文件不存在，上述两种情况都会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param sourcePath
     * @return
     */
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

    /**
     * The copy method is a common method of file services. This method uses the `copy` method of the Files tool class to copy,
     * and uses recursive copying of all files and folders in the folder, and ensures that if the folder needs to be copied,
     * the file The folder will be copied together.
     * This method filters the parameter null value, the file does not exist, the above two cases will return the FileServiceMessage object,
     * which contains the error type and prompt information.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 复制方法是文件服务的常见方法，本方法使用Files工具类的`copy`方法来进行复制，并使用了递归复制文件夹内所有文件和文件夹，
     * 并保证如果需要复制的是文件夹，该文件夹会一并复制。
     * 本方法过滤了参数空值，文件不存在，上述两种情况都会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。
     * I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param sourcePath
     * @param targetPath
     * @return
     */
    @Override
    public Message copy(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        System.out.println(sourcePath +"|" +targetPath);
        File file = new File(sourcePath);
        File newfile = new File(targetPath);
        // System.out.println(file.isDirectory() + "|" + file.isFile());
        // System.out.println(newfile.isDirectory() + "|" + newfile.isFile());
        if(!file.exists()) {return new FileServiceMessage("文件或文件夹不存在", FileServiceMessage.FileStatusEnum.NOT_EXIST.ordinal());}
        Message result = null;
        if(file.isDirectory()){
            // System.out.println( "目录目录");
            if (!targetPath.endsWith(File.separator)) {
                targetPath = targetPath + File.separator + file.getName();
                newfile = new File(targetPath);
                newfile.mkdirs();
            }

            if (!copyFileSync(file, newfile))
                return new FileServiceMessage("复制失败，可能是I/O流被占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());

        }else if(file.isFile()){
            // System.out.println( "文件文件");
            String fileType = file.getName().substring(file.getName().indexOf("."));
            System.out.println(fileType);
            if(!targetPath.endsWith(fileType)){
                if (!targetPath.endsWith(File.separator)) targetPath = targetPath + File.separator;
                newfile = new File(targetPath + file.getName());
            }
            // System.out.println(file.getAbsoluteFile() + "|" + newfile.getAbsolutePath());
            if (!copyFileSync(file, newfile))
                return new FileServiceMessage("复制失败，可能是I/O流被占用", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }

        return new Message("复制成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }


    private boolean copyFileSync(File file, File newfile) {
        try {
            if(file.isFile()){
                Files.copy(file.toPath(), newfile.toPath());
            } else if (file.isDirectory()) {
                newfile.mkdirs();
                File[] files = file.listFiles();
                for (File tempFile: files) {
                    File tempTargetFile = new File(newfile.getAbsolutePath() + File.separator + tempFile.getName());
                    System.out.println(tempFile.getAbsoluteFile() + "|" + tempTargetFile.getAbsolutePath());
                    copyFileSync(tempFile, tempTargetFile);
                }
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
     * The move method is a common method of file service, and this method is similar to the copy method.
     *
     * 移动方法是文件服务的常见方法，本方法和复制方法类似。
     * @param sourcePath
     * @param targetPath
     * @return
     */
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

    /**
     * Compression method is a common method of file service, this method uses ZipOutput
     * The Stream class, the ZipEntry class, and the FileInputStream class work together to compress,
     * and all files and folders in the folder are compressed recursively.
     * Note that when compressing a folder, the folder itself will not be compressed.
     * This method filters out the case of parameter null values, and returns a FileServiceMessage object,
     * which contains error types and prompt messages.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 压缩方法是文件服务的常见方法，本方法使用ZipOutput
     * Stream类、ZipEntry类和FileInputStream类共同完成来进行压缩，并使用了递归压缩文件夹内所有文件和文件夹。
     * 需要注意的是压缩文件夹时，文件夹本身并不会被压缩。
     * 本方法过滤了参数空值的情况，会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。
     * I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     */
    @Override
    public Message zip(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (targetPath == null || targetPath.trim().equals("")) { return new FileServiceMessage("再检查一遍参数", FileServiceMessage.FileStatusEnum.ILLEGAL_PARAMETER.ordinal());  }
        if (!zipFiles(sourcePath, targetPath)) {
            return new Message("压缩失败, 可能是I/O流占用或打印的语句", FileServiceMessage.FileStatusEnum.ERROR.ordinal());
        }
        return new Message("压缩成功", FileServiceMessage.FileStatusEnum.SUCCESS.ordinal());
    }

    /**
     * The decompression method is a common method of file service. This method uses the ZipFile class,
     * BufferedInputStream class, ZipEntry class and FileOutputStream class work together to perform decompression.
     * This method filters out the case of parameter null values, and returns a FileServiceMessage object,
     * which contains error types and prompt messages.
     * After filtering and operation, the FileServiceMessage object is finally returned, which returns the success type and prompt information.
     * I/O stream errors will return the error type and prompt information of the FileServiceMessage object.
     *
     * 解压缩方法是文件服务的常见方法，本方法使用ZipFile类、
     * BufferedInputStream类、ZipEntry类和FileOutputStream类共同完成来进行解压缩。
     * 本方法过滤了参数空值的情况，会返回FileServiceMessage对象，该对象内部是错误类型和提示信息。
     * 经过过滤和操作最终返回FileServiceMessage对象，该对象返回了成功类型和提示信息。I/O流错误则会返回FileServiceMessage对象的错误类型和提示信息。
     * @param sourcePath
     * @param targetPath
     * @return
     */
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
