package me.laudukang.persistence;

import org.junit.Test;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/13
 * <p>Time: 23:32
 * <p>Version: 1.0
 */
public class SimpleTests {

    @Test
    public void toUpper() {
        System.out.println("system implementation".toUpperCase());
    }

    @Test
    public void properties() {
        Properties properties = new Properties();
        properties.setProperty("aa", "1");
        properties.setProperty("/*", "1");
        Enumeration<?> propNames = properties.propertyNames();
        while (propNames.hasMoreElements()) {
            String path = (String) propNames.nextElement();
            System.out.println("path=" + path);
            System.out.println("properties.getProperty(path)=" + properties.getProperty(path));
            //int cacheSeconds = Integer.valueOf(properties.getProperty(path));
            //System.out.println("path=" + path + "  sec=" + cacheSeconds);
        }
    }

    @Test
    public void suffix() {
//        String fileName="111;222;333";
//        String[] str=fileName.trim().split(";");
//        for (String v :
//                str) {
//            System.out.println(v);
//        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("111;").append("222;").append("3333;");
        int index = stringBuilder.lastIndexOf(";");
        System.out.println(index);
        System.out.println(stringBuilder.subSequence(0, index));
    }

    @Test
    public void test() {
        Date date = new Date();
        // date.setTime(date.getTime()+24*60*60*1000);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + 24 * 60 * 60 * 1000);

        //System.out.println(calendar.getTime().getTime() > new Date().getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date1 = df.parse("2016-03-01");
            Date date2 = new Date(date1.getTime());
            System.out.println(sdf.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void zip() throws IOException {
        // see https://opoo.org/2014/walking-the-file-tree/ for more
        String path = "D:/apache/zip/751611201@qq.com/laudukang/";
        File file = new File(path);
        // System.out.println(file.isDirectory());
//        for (File tmp :
//                file.listFiles()) {
//            System.out.println("isDirectory=" + tmp.isDirectory());
//            System.out.println(tmp.getName());
//        }
        System.out.println("==============");
//        Collection<File> fileCollection = FileUtils.listFilesAndDirs(file, FileFileFilter.FILE, DirectoryFileFilter.DIRECTORY);
//        Iterator<File> itf = fileCollection.iterator();
//        while (itf.hasNext()) {
//            File tmp = itf.next();
//            System.out.println(tmp.isDirectory());
//            if (!tmp.isDirectory())
//                System.out.println(tmp.getName());
//            System.out.println("----");
//        }


//        FluentIterable<File> fileFluentIterable = Files.fileTreeTraverser().breadthFirstTraversal(file).filter(new Predicate<File>() {
//            public boolean apply(File input) {
//                return input.isFile();
//            }
//        });
//        Iterator<File> iterator = fileFluentIterable.iterator();
//        while (iterator.hasNext()) {
//            File tmp = iterator.next();
//            System.out.println(tmp.isDirectory());
//            if (!tmp.isDirectory()) System.out.println(tmp.getName());
//        }

        final List<File> files = new ArrayList<File>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                files.add(dir.toFile());
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            java.nio.file.Files.walkFileTree(Paths.get(path), finder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File tmp : files
                ) {
            File parent = tmp.getParentFile();
            StringBuilder sb = new StringBuilder();
            while (!"laudukang".equals(tmp.getName()) && null != parent && !"laudukang".equals(parent.getName())) {
                if (parent.isDirectory())
                    sb.insert(0, parent.getName() + File.separator);
                parent = parent.getParentFile();
            }
            System.out.println("path=" + sb.toString());
            System.out.println(tmp.getAbsolutePath());
//            System.out.println(tmp.getParent());
//            System.out.println(tmp.getParentFile().getName());
//            System.out.println(tmp.getParentFile().getPath());
            System.out.println("-------------");
        }

    }

    @Test
    public void split() {
        String t = "aaaaa";
        String[] m = t.split(";");
        System.out.println(m.length);
    }

    @Test
    public void writeHTML() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("D:/apache/zip/YourFile.html"), "UTF-8");

        String html = "<a class=\"brand pull-left\" href=\"/\"> <img src=\"http://www.codejava.net/images/banner.png\" alt=\"CodeJava\"> </a>";
        BufferedWriter bufWriter = new BufferedWriter(writer);
        bufWriter.write(html);
        bufWriter.close();
    }

    class FileVisitorTest extends SimpleFileVisitor<Path> {

        private void find(Path path) {
            System.out.printf("访问-%s:%s%n", (Files.isDirectory(path) ? "目录" : "文件"), path.getFileName());
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            find(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e) {
            System.out.println(e);
            return FileVisitResult.CONTINUE;
        }
    }
}
