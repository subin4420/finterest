package org.finterest.invest.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j
public class BoardAttachmentVO {
    private Long no; //
    private Long bno; // FK: Board의 no
    private String filename; // 원본 파일명
    private String path; // 서버에 저장된 파일 경로
    private String contentType; // 파일 mime-type
    private Long size; // 파일의 크기
    private Date regDate; // 등록일


    // 팩토리 메서드
    public static BoardAttachmentVO of(MultipartFile part, Long bno, String path) { // path: 업로드된 파일 경로
        return builder()
                .bno(bno)
                .filename(part.getOriginalFilename())
                .path(path)
                .contentType(part.getContentType())
                .size(part.getSize())
                .build();
    }

    public String getFileSize() {
        return UploadFiles.getFormatSize(size);
    }

}