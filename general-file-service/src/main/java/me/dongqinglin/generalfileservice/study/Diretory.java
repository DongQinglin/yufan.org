package me.dongqinglin.generalfileservice.study;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class Diretory {
    public static File[] local(File dir, final String regex) {
        if (dir == null ) { return null;}
        if (regex == null || regex.isEmpty()) { return null;}
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(name).find();
            }
        });
    }
    // Overload
    public static File[] local(String dir, final String regex) {
        if (dir == null || dir.isEmpty()) { return null;}
        if (regex == null || regex.isEmpty()) { return null;}
        return local(new File(dir), regex);
    }

    public static class TreeInfo implements Iterable<File> {
        public List<File> files = new ArrayList<File>();
        public List<File> dirs = new ArrayList<File>();
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        @Override
        public String toString() {
            return "TreeInfo{" +
                    "files=" + files +
                    ",\n dirs=" + dirs +
                    '}';
        }
    }

    public static TreeInfo walk(String start, String regex) {
        return recuresDirs(new File(start), regex);
    }
    public static TreeInfo walk(File start, String regex) {
        return recuresDirs(start, regex);
    }
    public static TreeInfo walk(File start) {
        return recuresDirs(start, "");
    }
    public static TreeInfo walk(String start) {
        return recuresDirs(new File(start), "");
    }

    public static TreeInfo recuresDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                result.dirs.add(item);
            }else {
                if (item.getName().matches(regex)) {
                    result.files.add(item);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(walk("."));
    }


}
