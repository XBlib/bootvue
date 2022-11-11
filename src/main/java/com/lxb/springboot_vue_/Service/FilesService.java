package com.lxb.springboot_vue_.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.Files;

import java.util.List;


/**
 * @author XBlib
 * @version 1.0
 */
public interface FilesService extends IService<Files> {
    Files getFileByMd5(String md5);

    Result delFiles(List<Integer> ids);
}
