package com.hiveview.base.common.fastdfs;

import com.hiveview.base.common.properties.Global;
import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.domain.MetaData;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * fastdfs 上传删除文件
 */
public class FastDFSClientNew {
    private static final Logger logger= LoggerFactory.getLogger(FastDFSClientNew.class);

    private static final String TRACKER_SERVER="tracker_server";

    private static FastFileStorageClient storageClient=null;

    static {
        try {
            FdfsConnectionPool pool = new FdfsConnectionPool();
            List<String> trackers = new ArrayList<>();
            trackers.add(Global.getConfig(TRACKER_SERVER));

            TrackerConnectionManager tcm = new TrackerConnectionManager(pool, trackers);
            TrackerClient trackerClient = new DefaultTrackerClient(tcm);

            ConnectionManager cm = new ConnectionManager(pool);
            storageClient = new DefaultFastFileStorageClient(trackerClient, cm);
        }catch (Exception e){
            logger.error("fast dfs client init error",e);
        }
    }

    public static StorePath uploadFile(InputStream input, long fileSize, String fileExtName, Set<MetaData> metaDataSet){
        return storageClient.uploadFile(input,fileSize,fileExtName,metaDataSet);
    }

    public static void deleteFile(String path){
        storageClient.deleteFile(path);
    }
}
