package com.music.controller;

import com.music.common.Result;
import com.music.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileUtil fileUtil;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> uploadFile(
            @ApiParam("文件") @RequestParam("file") MultipartFile file,
            @ApiParam("文件类型") @RequestParam("type") String type) {
        try {
            String filePath = fileUtil.uploadFile(file, type);
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
} 