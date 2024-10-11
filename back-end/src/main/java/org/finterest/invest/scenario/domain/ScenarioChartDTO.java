package org.finterest.invest.scenario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenarioChartDTO {

    private String stck_bsop_date;
    private String stck_oprc;
    private String stck_clpr;
    private String stck_lwpr;
    private String stck_hgpr;

}
