package club.zhouyouwu.graduate.usermanagement.utils;

import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Slf4j
public class ImageUtil {

    public static final String EXTENSION_ICO = "ico";

    public static BufferedImage getImageFromFile(InputStream is, String extension) throws IOException {
        log.debug("Current File type is : [{}]", extension);

//        if (EXTENSION_ICO.equals(extension)) {
//            try {
//                return ICODecoder.read(is).get(0);
//            } catch (IOException e) {
//                throw new ImageFormatException("文件已损坏", e);
//            }
//        } else {
        return ImageIO.read(is);
        //}
    }

    @NonNull
    public static ImageReader getImageReaderFromFile(InputStream is, String formatName) throws IOException {
        try {
            Iterator<ImageReader> readerIterator = ImageIO.getImageReadersByFormatName(formatName);
            ImageReader reader = readerIterator.next();
            ImageInputStream stream = ImageIO.createImageInputStream(is);
            ImageIO.getImageReadersByFormatName(formatName);
            reader.setInput(stream, true);
            return reader;
        } catch (Exception e) {
            throw new IOException("读取图片文件失败", e);
        }
    }

    public static void storeImage(MultipartFile photo, String fileName) throws IOException {
        try{
            File file = new File(ConstantInfo.IMAGE_PATH + fileName);
            if(file.exists()){
                throw new IOException("此文件已存在，请检查文件名生成"+fileName);
            }
            photo.transferTo(file);
        }catch (IOException e){
            throw new IOException("存储文件失败", e);
        }
    }
}

