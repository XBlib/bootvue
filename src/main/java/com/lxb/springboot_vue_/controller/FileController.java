package com.lxb.springboot_vue_.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxb.springboot_vue_.Service.FilesService;
import com.lxb.springboot_vue_.common.Result;
import com.lxb.springboot_vue_.pojo.Files;
import com.lxb.springboot_vue_.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author XBlib
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Autowired
    private FilesService filesService;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //获取原始文件名
        String originalFileName = file.getOriginalFilename();
        //获取文件类型
        String type = FileUtil.extName(originalFileName);
        //获取文件大小（字节）
        long size = file.getSize();
        //生成唯一表示UUID
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        //设置文存储路径
        File uploadFile = new File(fileUploadPath + fileUUID);
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否存在相同的记录
        Files dbFiles = filesService.getFileByMd5(md5);
        //定义url
        String url;
        if(dbFiles != null) {
            url = dbFiles.getUrl();
            //删除存在相同的文件
            uploadFile.delete();
        } else {
            //数据库不存在则不删除刚才上传的文件
            url = "http://localhost:9090/file/down/" + fileUUID;
        }
        //将文件信息写到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFileName);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        filesService.save(saveFile);
        return url;
    }

    /**
     * 文件下载
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/down/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件唯一标识获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //设置输出流格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
    //更新
    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        return Result.success(filesService.updateById(files));
    }

    @DeleteMapping("/del/{id}")
    public Result delFile(@PathVariable Integer id) {
        Files files = filesService.getById(id);
        files.setIsDelete(true);
        filesService.updateById(files);
        return Result.success();
    }
    //根据id批量删除
    @PostMapping("/delBatch")
    public Result delFiles(@RequestBody List<Integer> ids) {
        return filesService.delFiles(ids);
    }

    /**
     * 分页查询文件接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result fileOfPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String name
    ){
        IPage<Files> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(filesService.page(page,queryWrapper));
    }

}
