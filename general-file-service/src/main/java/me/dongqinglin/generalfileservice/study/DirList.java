package me.dongqinglin.generalfileservice.study;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

class DirFilter implements FilenameFilter {
    private Pattern pattern;

    public DirFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean accept(File dir, String name) {
        // 全匹配
        // return pattern.matcher(name).matches();
        // 子串
        return pattern.matcher(name).find();
        // 开始
        // return pattern.matcher(name).lookingAt();
    }
}


public class DirList {

    // output:
    // .idea
    // auto
    // extra
    // general-file-service
    public static void main(String[] args) {
        File path = new File(".");
        String[] list;
        String filterStr = "a";
        list = path.list(new DirFilter(filterStr));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirName : list ) {
            System.out.println(dirName);
        }
    }
}
