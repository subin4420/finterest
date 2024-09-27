package org.finterest.achieve.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressVO {
    private int progressId;
    private int materialId;
    private String title;
    private String status;
    private Date startedAt;
    private Date completedAt;
}
