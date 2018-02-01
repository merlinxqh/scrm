package com.hiveview.admin.controller;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.base.common.fastdfs.FastDFSFile;
import com.hiveview.base.common.fastdfs.FileUploadUtils;
import com.hiveview.base.util.response.RespMsg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by leo on 2018/1/31.
 * 文件上传
 */
@Controller
@RequestMapping(value = "/file")
public class UploadController extends BaseController{

    @RequestMapping(value="toUpload",method = RequestMethod.GET)
    public ModelAndView toUpload(ModelMap model){
        model.addAllAttributes(getRequestMap());
        return new ModelAndView("upload/file_upload");
    }

    /**
     * 上传文件
     * @return
     *  method = { org.springframework.web.bind.annotation.RequestMethod.POST }
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public RespMsg<?> upload(@RequestParam("file")MultipartFile file) {
        try {
            FastDFSFile fsfile = FileUploadUtils.uploadFile(file);
            return RespMsg.successResp(fsfile);
        } catch (Exception e) {
            logger.error("",e);
            return RespMsg.failResp(e.getMessage());
        }
    }
}
