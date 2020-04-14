package com.ipicbook.fangdoc.utils;

import com.spire.doc.Document;
import com.spire.doc.documents.DocumentObjectType;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.interfaces.ICompositeObject;
import com.spire.doc.interfaces.IDocumentObject;
import org.apache.commons.lang.math.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExtractImg {
    public static List<String> parse(String path,String fileName) throws IOException {
        //加载Word文档
        Document document = new Document();
        document.loadFromFile(fileName);

        Queue nodes = new LinkedList();
        nodes.add(document);

        //创建List对象
        List images = new ArrayList();
        List<String> imgs = new ArrayList();

        //遍历文档中的子对象
        while (nodes.size() > 0) {
            ICompositeObject node = (ICompositeObject) nodes.poll();
            for (int i = 0; i < node.getChildObjects().getCount(); i++) {
                IDocumentObject child = node.getChildObjects().get(i);
                if (child instanceof ICompositeObject) {
                    nodes.add((ICompositeObject) child);

                    //获取图片并添加到List
                    if (child.getDocumentObjectType() == DocumentObjectType.Picture) {
                        DocPicture picture = (DocPicture) child;
                        images.add(picture.getImage());
                    }
                }
            }
        }

        //将图片保存为PNG格式文件
        for (int i = 0; i < images.size(); i++) {
            int mouth = new Date().getMonth() + 1;
            int date = new Date().getDate();
            File file = new File(path,String.format("%d-%d-%d-%d.png", mouth, date, i + 1, RandomUtils.nextInt(1000)));
            ImageIO.write((RenderedImage) images.get(i), "PNG", file);
            imgs.add("statics" + File.separator + "uploadFiles"+File.separator+file.getName());
        }
        return  imgs;
    }
}