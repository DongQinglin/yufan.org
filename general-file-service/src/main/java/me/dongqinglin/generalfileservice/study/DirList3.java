package me.dongqinglin.generalfileservice.study;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList3 {
    // output:
    // .idea
    // auto
    // extra
    // general-file-service
    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        String filterStr = "a";
        list = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern pattern = Pattern.compile(filterStr);
                return pattern.matcher(name).find();
            }
        });
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirName : list ) {
            System.out.println(dirName);
        }
    }
}
