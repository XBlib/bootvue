package com.lxb.springboot_vue_.Service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxb.springboot_vue_.Service.FilesService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.mapper.FilesMapper;
import com.lxb.springboot_vue_.pojo.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {
    @Autowired
    private FilesMapper filesMapper;
    @Override
    public Files getFileByMd5(String md5) {
        List<Files> filesList = filesMapper.selectList(Wrappers.<Files>lambdaQuery().eq(Files::getMd5, md5));
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @Override
    public Result delFiles(List<Integer> ids) {
        List<Files> files = filesMapper.selectBatchIds(ids);
        for (Files file : files) {
            file.setIsDelete(true);
            filesMapper.updateById(file);
        }
        return Result.success();
    }
}
