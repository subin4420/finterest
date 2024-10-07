package org.finterest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Slf4j  // Lombok의 Slf4j 어노테이션@sIf4j
@Component
public class UploadPathConfig {

    @Value("${upload.path.unix}")
    private String unixPath;

    @Value("${upload.path.windows}")
    private String windowsPath;

    public UploadPathConfig() {
        logUploadPaths();  // 생성자에서 경로 확인 로그 출력
    }

    private void logUploadPaths() {
        log.info("Upload Unix Path: " + unixPath);
        log.info("Upload Windows Path: " + windowsPath);
    }

    public String getUploadPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return windowsPath;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return unixPath;
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
    }
}


