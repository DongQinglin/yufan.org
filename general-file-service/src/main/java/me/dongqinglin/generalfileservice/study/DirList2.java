package me.dongqinglin.generalfileservice.study;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList2 {
    public static FilenameFilter filter(final String regex){
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).find();
            }
        };
    }
    // output:
    // .idea
    // auto
    // extra
    // general-file-service
    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        String filterStr = "a";
        list = path.list(filter(filterStr));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirName : list ) {
            System.out.println(dirName);
        }
    }
}
