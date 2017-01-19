package com.util;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Mckay on 2017/1/19.
 */
public class FileUploadUtil {

    private static final Logger logger = Logger.getLogger(FileUploadUtil.class);
    // 限制的文件类型
    private static final String[] fileType = { "jpg", "png", "bmp", "gif",
            "jpeg", "JPG", "PNG", "BMP", "GIF", "JPEG","xls","doc","txt"   };

    // 限制的文件大小,默认10M,-1就是无限制大小
    private long FILESIZE = 10 * 1024 * 1024;

    /**
     * 得到文件类型
     *
     * @param file
     * @return string
     */
    public String getFileType(MultipartFile file) {
        return file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    /**
     * 检查文件类型
     *
     *
     * @param file
     *            传入的文件
     * @return 是否为指定类型(true)
     */
    public boolean validateFileType(MultipartFile file) {
        if (fileType == null) {
            return true;
        }
        for (int i = 0, len = fileType.length; i < len; i++) {
            if (fileType[i].equals(getFileType(file))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归的根据路径创建文件夹
     *
     * @param url
     * @return
     * @throws Exception
     */

    public boolean createFolder(String url) throws Exception {
        boolean boo = true;
        File file = new File(url);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                boo = false;
                throw new Exception("文件夹创建失败!");
            }
        }
        return boo;
    }

    /**
     * 上传文件
     *
     * @param file
     * @param filePath
     *            文件保存路径
     * @param targetFileName
     *            文件保存名字(不含后缀名)
     * @return
     * @throws Exception
     */
    public Boolean uploadFile(MultipartFile file, String filePath,
                              String targetFileName) throws Exception {
        boolean flag = true;
        if (null == file) {
            return false;
        }

        if (!validateFileType(file)) {
            logger.info("文件类型校验失败！");
            return false;
        }

        if (file.getSize() > FILESIZE) {
            logger.info("文件大小超限！");
            return false;
        }

        if (!createFolder(filePath)) {
            logger.info(filePath + "创建失败！");
            return false;
        }

        try {
            File targetFile = new File(filePath, targetFileName + "."
                    + getFileType(file));
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
        }

        return flag;
    }

    public void delOldFile(String oldPath) {
        File file = new File(oldPath);
        file.deleteOnExit();
    }
}
