package com.hiveview.base.common.fastdfs;

import lombok.Data;

/**
 * Created by mikejiang on 2017/4/25.
 */
@Data
public class FastDFSFile {
    private static final long serialVersionUID = 1L;

    private byte[] content;
    private String filename;
    private String ext;
    private String length;
    private String author;
    private String md5;
}
