package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.pms.dao.SubSystemMapper;
import com.hiveview.pms.entity.sys.SubSystem;
import com.hiveview.pms.service.SubSystemService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("subSystemService")
public class SubSystemServiceImpl extends BaseCrudServiceImpl<SubSystem> implements SubSystemService {

    @Resource
    private SubSystemMapper subSystemMapper;

    @Override
    public CrudMapper init() {
        return subSystemMapper;
    }
}