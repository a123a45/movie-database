package com.movie.database.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.movie.database.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Tag(name = "文件上传")
@RestController
public class FileController {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Operation(summary = "上传图片")
    @PostMapping("/file/upload")
    public Result<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String suffix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
        String newName = IdUtil.simpleUUID() + suffix;
        File dir = new File(uploadDir + "/posters");
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, newName);
        file.transferTo(dest);
        return Result.ok("/uploads/posters/" + newName);
    }

    @Operation(summary = "上传海报（自动裁剪为 600×900）")
    @PostMapping("/file/upload/poster")
    public Result<?> uploadPoster(@RequestParam("file") MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String suffix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
        String newName = IdUtil.simpleUUID() + ".jpg";
        File dir = new File(uploadDir + "/posters");
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, newName);

        // Read source image
        BufferedImage src = ImageIO.read(file.getInputStream());
        if (src == null) {
            return Result.error("无法解析图片文件");
        }

        int srcW = src.getWidth();
        int srcH = src.getHeight();
        int targetW = 600;
        int targetH = 900;

        // Crop to 2:3 aspect ratio centered, then scale to 600×900
        BufferedImage cropped = cropCenter(src, targetW, targetH);
        ImageIO.write(cropped, "jpg", dest);

        return Result.ok("/uploads/posters/" + newName);
    }

    /**
     * Center-crop the source image to match the target aspect ratio,
     * then scale to target dimensions.
     */
    private BufferedImage cropCenter(BufferedImage src, int targetW, int targetH) {
        int srcW = src.getWidth();
        int srcH = src.getHeight();
        double targetRatio = (double) targetW / targetH;
        double srcRatio = (double) srcW / srcH;

        int cropW, cropH, x, y;
        if (srcRatio > targetRatio) {
            // Source is wider — crop width
            cropH = srcH;
            cropW = (int) (srcH * targetRatio);
            x = (srcW - cropW) / 2;
            y = 0;
        } else {
            // Source is taller — crop height
            cropW = srcW;
            cropH = (int) (srcW / targetRatio);
            x = 0;
            y = (srcH - cropH) / 2;
        }

        BufferedImage cropped = src.getSubimage(x, y, cropW, cropH);

        // Scale to exact target size
        BufferedImage scaled = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(cropped, 0, 0, targetW, targetH, null);
        g.dispose();

        return scaled;
    }
}
