package com.ipicbook.fangdoc.utils;

import com.spire.doc.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;

public class ExtractText {

    public static String parse(String fileName) throws IOException{
        Document doc = new Document();
        doc.loadFromFile(fileName);
        return doc.getText();
    }
}