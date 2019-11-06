package com.scitc.blog.utils;

import com.scitc.blog.dto.ImageHolder;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

   private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static String addImgAddr(String dest, ImageHolder imageHolder) {
        String path = PathUtil.ImageBasePath() + dest;
        makeDirPath(path);
        logger.info("ImageUtil path: " + path);
        String imageName = getRandomFileName() + getFileExtension(imageHolder.getImageName());
        File imageAddr = new File(path,imageName);
        try {
            FileUtils.copyInputStreamToFile(imageHolder.getImage(),imageAddr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("ImageUtil dest + imageName: " + dest + imageName);
        //返回映射路径下的 相对路径 + 文件名称
        return dest + imageName;

    }


    /**
     * 创建目标路径所涉及到的目录，即 /home/work/xiangze/xxx.jpg
     * 那么 home word xiangze 则三个文件夹都得自动创建
     *
     * @param path
     */
    public static void makeDirPath(String path){
        File dirPath = new File(path);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String getRandomFileName(){
        //时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowTimeStr = sDateFormat.format(new Date());
        //随机数
        Random r = new Random();
        int rannum = r.nextInt(89999) + 10000;
        return  nowTimeStr + rannum;
    }

    /**
     * 后缀
     */

    public static String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 删除图片
     */
    public static void deleteFileOrPath(String imgPath){
        File fileOrPath = new File(PathUtil.ImageBasePath() + imgPath.substring(imgPath.indexOf("/upload")));
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] file = fileOrPath.listFiles();
                for(int i = 0; i< file.length; i++){
                    file[i].delete();
                }
            }
            //如果是文件
            fileOrPath.delete();
        }
    }


}
