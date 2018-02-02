package com.hiveview.base.common.fastdfs;

import com.luhuiguo.fastdfs.domain.MetaData;
import com.luhuiguo.fastdfs.domain.StorePath;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leo on 2017/10/24.
 */
public class FileUploadUtils {


    /**
     * 通用文件上传
     * @param file
     * @return
     * @throws IOException
     */
    public static FastDFSFile uploadFile(MultipartFile file) throws IOException {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        Set<MetaData> metaSet=new HashSet<>();
        metaSet.add(new MetaData("fileName", file.getOriginalFilename()));
        metaSet.add(new MetaData("fileLength", String.valueOf(file.getSize())));
        metaSet.add(new MetaData("fileExt", ext));
        metaSet.add(new MetaData("fileAuthor", "hiveview"));
        StorePath store=null;
        InputStream is=null;
        try {
            is=file.getInputStream();
            store= FastDFSClientNew.uploadFile(is,file.getSize(),ext,metaSet);
        }finally {
            if(null != is){
                is.close();
            }
        }
        FastDFSFile fsfile = new FastDFSFile();
        fsfile.setFilename(store.getGroup()+"/"+store.getPath());
        return fsfile;
    }

    /**
     * 通用文件上传
     * @param file
     * @return
     * @throws IOException
     */
    public static String uploadFile(File file) throws IOException {
        String ext = FilenameUtils.getExtension(file.getName());
        Set<MetaData> metaSet=new HashSet<>();
        metaSet.add(new MetaData("fileName", file.getName()));
        metaSet.add(new MetaData("fileLength", String.valueOf(file.length())));
        metaSet.add(new MetaData("fileExt", ext));
        metaSet.add(new MetaData("fileAuthor", "hiveview"));
        StorePath store=null;
        FileInputStream is=null;
        try {
            is=new FileInputStream(file);
            store = FastDFSClientNew.uploadFile(is,file.length(),ext,metaSet);
        }finally {
            if(null != is){
                is.close();
            }
        }

        return store.getGroup()+"/"+store.getPath();
    }
}
