I see. We use spring boot which has built-in server named tomcat. So we can give a static folder to save the files you want to download or upload. We can define a variable named personal-files or other name you like in your application.propertities. Like it:

`personal-file-server.filepath= D:/uploadFilePath/`

Note: 
On linux, we don't have the disk part. So you can update the path string.

And we need to config the server to make our server to know the path is a static path. We use a class implement the WebMvcConfigurer interface to complete it. Like it:

```
@EnableWebMvc
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	@Value("${personal-file-server.filepath}")
    	private String filepath;

	public WebConfigurer() { super(); }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/META-INF/resources/")
			.addResourceLocations("classpath:/resources/")
			.addResourceLocations("classpath:/static/")
			.addResourceLocations("classpath:/public/")
			.addResourceLocations("file:" + filepath);
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
```

When you want to upload the files, We use the class named MultipartFile to accept them, like it: 

```
@RestController
@RequestMapping("api/admin/tbAdmInfo")
public class TbAdmInfoController {
    @Value("${personal-file-server.filepath}")
    private String uploadFilePath;

    @Autowired
    private TbAdmInfoService tbAdmInfoService;

    @PostMapping("fileUpload/{id}")
    public void uploadFile(@PathVariable int id, @RequestParam("files") MultipartFile[] files){
        if(files.length == 0 ) System.out.println("没有文件");
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                System.out.println(fileName);
                String fileFolder = uploadFilePath + "tbAdmInfo/" + id + "/";
                FileUtil.makeDir(fileFolder);
                file.transferTo(new File(fileFolder + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String targetFolder = uploadFilePath + "tbAdmInfo/zip/";
        FileUtil.makeDir(targetFolder);
        String targetPath = uploadFilePath + "tbAdmInfo/zip/" + id + ".zip";
        String fileFolder = uploadFilePath + "tbAdmInfo/" + id + "/";
        FileUtil.zipFiles(fileFolder,targetPath);
    }

    @GetMapping("fileDownload/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable int id){
        String[] result = new String[1];
        String targetPath = "tbAdmInfo/zip/" + id + ".zip";
        result[0] = targetPath;
        return ResponseEntity.ok(result);
    }

    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException{
        String fileName = "数据表名";
        List<TbAdmInfo> all = tbAdmInfoService.findAll();
        String[] th = {"id", "orgId", "addr", "areaCode"};
        HSSFWorkbook excel = ExcelUtil.createExcel(fileName, th);
        HSSFSheet sheet = excel.getSheetAt(0);
        int rowNum = 1;
        for (TbAdmInfo tbAdmInfo: all) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(tbAdmInfo.getId());
            row.createCell(1).setCellValue(tbAdmInfo.getOrgId());
            row.createCell(2).setCellValue(tbAdmInfo.getsAddr());
            row.createCell(3).setCellValue(tbAdmInfo.getsAreaCode());
            rowNum++;
        }

         ExcelUtil.setBrowser(response, excel, fileName);
    }
}
```





Note: That must use the POST method and use the `@RequestParam` to accept the param which is a file.
If you want to download the file, you will get the url of file first. And if you want to operate the files, you will need the class FileUtil. Like it: 

```
public class FileUtil {

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
//        String targetPath = "D:\\1.zip";
//        String fileData = "haha";
//        writeFile(filePath, fileData);
//        zipFiles(filePath,targetPath);
//        System.out.println();
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
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

```

And the excel will need the ExcelUtil. Like it: 

```
public class ExcelUtil {

    public static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control","no-cache");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HSSFWorkbook createExcel(String fileName, String[] th){
            HSSFWorkbook workbook = new HSSFWorkbook();  //得到Excel工作簿对象
            HSSFSheet sheet1 = workbook.createSheet(fileName);   //得到Excel工作表对象
            setTitle(workbook, sheet1, th);
            return workbook;
    }

    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] th) {
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for (int i = 0; i < th.length; i++) {
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            sheet.setColumnWidth(2, 20*256);
            HSSFCell cell;
            cell = row.createCell(i);
            cell.setCellValue(th[i]);
            cell.setCellStyle(style);
        }
    }
	// don't use
    public static List<Object[]> importExcel(String fileName) {
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[((Row) row).getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (cell.getCellType().equals(CellType.NUMERIC)) {
                        objects[index] = (int) cell.getNumericCellValue();
                    }
                    if (cell.getCellType().equals(CellType.STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (cell.getCellType().equals(CellType.BOOLEAN)) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (cell.getCellType().equals(CellType.ERROR)) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
```

It' s all. Hope it will help you.
