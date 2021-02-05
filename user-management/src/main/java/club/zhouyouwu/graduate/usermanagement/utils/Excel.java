package club.zhouyouwu.graduate.usermanagement.utils;

import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class Excel {
    public static XSSFWorkbook getExcel(MultipartFile excel) throws IOException{

        log.info("接收文件...");
        File file = File.createTempFile("temp", null);
        excel.transferTo(file);
        file.deleteOnExit();

        InputStream inputStream = new FileInputStream(file);
        return new XSSFWorkbook(inputStream);
    }
}
