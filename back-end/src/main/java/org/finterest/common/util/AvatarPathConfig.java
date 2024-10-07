package org.finterest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AvatarPathConfig {

    @Value("${avatar.path.unix}")
    private String unixPath;

    @Value("${avatar.path.windows}")
    private String windowsPath;

    public AvatarPathConfig() {
        logUploadPaths();  // 생성자에서 경로 확인 로그 출력
    }

    private void logUploadPaths() {
        log.info("Avatar Unix Path: " + unixPath);
        log.info("Avatar Windows Path: " + windowsPath);
    }

    public String getAvatarPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return windowsPath;  // Windows 경로
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return unixPath;  // Unix 또는 macOS 경로
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
    }
}
