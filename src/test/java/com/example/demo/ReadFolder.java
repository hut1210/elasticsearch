package com.example.demo;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * @author hut
 * @date 2022/9/13 4:26 下午
 */
public class ReadFolder {
    private static String filepath = "/Users/huteng/Desktop/测试";
    public static void main(String[] args) {

    }
    public static void test1(){
        BufferedReader bufReader = null;
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("Not folder");
            } else if (file.isDirectory()) {
                System.out.println("Be folder");

                String str;
                int lineNo;

                String[] filelist = file.list();

                for (int i = 0; i < filelist.length; i++) {
                    String path = filepath + "/" + filelist[i];
                    File readfile = new File(path);
                    String absolutePath = readfile.getAbsolutePath();
                    String fileName = readfile.getName();
                    System.out.println(fileName);
                    //Read each file
                    bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
                    lineNo=0;

                    while((str=bufReader.readLine())!=null) {
                        ++lineNo;
                        if(str.endsWith("*END*")) {
                            System.out.println(fileName+"(*END*)-->"+lineNo);
                        }

                        if("".equals(str)) {
                            System.out.println(fileName+"(空行)-->"+lineNo);
                        }
                        if(str.startsWith("J")){
                            System.out.println(fileName+"(J)-->"+lineNo+"------>"+str);
                        }
                    }
                    String buffer = "";
                    FileInputStream is = new FileInputStream(path);
                    OutputStream outputStream = null;
                    try {

                        if (path.endsWith(".doc")) {

                            WordExtractor ex = new WordExtractor(is);
                            buffer = ex.getText();
                            System.out.println(buffer);

                            String outPath = filepath + "/new" + filelist[i];
                            outputStream = new FileOutputStream(outPath);
                            XWPFDocument document = new XWPFDocument();
                            document.write(outputStream);

                        } else if (path.endsWith("docx")) {
                            OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                            buffer = extractor.getText();
                            System.out.println(buffer);
                            opcPackage.close();
                        } else {
                            System.out.println("文件不是word文件");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("读取word文件失败"+e.getMessage());
                    }finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
                System.out.println("All finished");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*try {
                bufReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/


        }
    }

    public static void test2(){
        File file = new File(filepath);
        if (!file.isDirectory()) {
            System.out.println("Not folder");
        } else if (file.isDirectory()) {
            System.out.println("Be folder");
            String[] fileList = file.list();

            for (int i = 0; i < fileList.length; i++) {
                String path = filepath + "/" + fileList[i];
                File readFile = new File(path);
                String absolutePath = readFile.getAbsolutePath();
                String fileName = readFile.getName();
                System.out.println(fileName);
            }
        }
    }
}
