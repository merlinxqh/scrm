package com.hiveview.pms.api;

import com.hiveview.common.api.CrudApiService;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.pms.dto.BasicDataDto;

/**
 * Created by leo on 2018/1/15.
 */
public interface BasicDataApiService extends CrudApiService<BasicDataDto> {

    void modifyData(ModifyCommonDto dto);
}
