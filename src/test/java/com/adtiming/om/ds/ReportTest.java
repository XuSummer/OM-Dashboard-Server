package com.adtiming.om.ds;

import com.adtiming.om.ds.dto.ReportConditionDTO;
import com.adtiming.om.ds.service.FieldNameService;
import com.adtiming.om.ds.service.ReportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ruandianbo on 20-2-18.
 */
public class ReportTest extends BaseCommonTests {

    @Autowired
    FieldNameService fieldNameService;

    @Autowired
    ReportService reportService;

    @Test
    public void testGetLtvData() throws Exception {
        MultiValueMap<String, String> ps = new LinkedMultiValueMap<>();
        List<String> dateBegin = new ArrayList<>();
        dateBegin.add("2020-05-14");
        ps.put("dateBegin",dateBegin);
        List<String> dateEnd = new ArrayList<>();
        dateEnd.add("2020-05-21");
        ps.put("dateEnd",dateEnd);
        ps.put("granularity", Arrays.asList("day"));
        ps.put("dimensions", Arrays.asList("retention_day", "base_date"));
        this.doPost("/report/ltv?ps=queryPaymentType=IAA&dimensions=retention_day&dimensions=base_date&dimensions=retention_day&dateBegin=2020-10-20&dateEnd=2020-11-02&granularity=day", ps);
    }

    @Test
    public void testGetDashboardHeadRevenues() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/report/dashboard/head/revenues")
                .param("pubAppId", "119"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetDashboardRegionRevenues() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/report/dashboard/regions/revenues")
                .param("pubAppId", "119"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetReportList() throws Exception {
        fieldNameService.initIdName();
        ReportConditionDTO reportConditionDTO = new ReportConditionDTO();
        reportConditionDTO.setDateBegin("2020-02-20");
        reportConditionDTO.setDateEnd("2020-03-03");
        Integer[] publisherIds = new Integer[]{10, 1};
        reportConditionDTO.setPublisherId(publisherIds);
        String[] dimensions = new String[]{"day", "publisherId", "placementId", "instanceId", "pubAppId"};
        String[] types = new String[]{"dau","lr","api"};
        reportConditionDTO.setDimension(dimensions);
        reportConditionDTO.setType(types);
        this.doPost("/report/list", reportConditionDTO);
    }

    @Test
    public void testGetAdNetworkSummary() throws Exception {
        ReportConditionDTO reportConditionDTO = new ReportConditionDTO();
        reportConditionDTO.setDateBegin("2020-02-17");
        reportConditionDTO.setDateEnd("2020-02-24");
        Integer[] publisherIds = new Integer[]{10};
        reportConditionDTO.setPublisherId(publisherIds);
        String[] dimensions = new String[]{"publisherId", "day"};
        reportConditionDTO.setDimension(dimensions);
        this.doPost("/report/adnetwork/list", reportConditionDTO);
    }

    @Test
    public void testGetDauSummary() throws Exception {
        ReportConditionDTO reportConditionDTO = new ReportConditionDTO();
        reportConditionDTO.setDateBegin("2020-12-01");
        reportConditionDTO.setDateEnd("2020-12-02");
        String[] dimensions = new String[]{"day","adnId"};
//        dimensions = new String[]{"day","instanceId"};
//        dimensions = new String[]{"day","placementId"};
//        dimensions = new String[]{"day","placementId", "adnId"};
        reportConditionDTO.setDimension(dimensions);
        Set<String> dimensionSet = new HashSet<>();
        if (reportConditionDTO.getDimension() != null && reportConditionDTO.getDimension().length > 0) {
            Collections.addAll(dimensionSet, reportConditionDTO.getDimension());
        }
        reportConditionDTO.setDimensionSet(dimensionSet);
        this.reportService.getDeuReport(reportConditionDTO);
    }

    @Test
    public void testGetLRSummary() throws Exception {
        ReportConditionDTO reportConditionDTO = new ReportConditionDTO();
        reportConditionDTO.setDateBegin("2020-02-20");
        reportConditionDTO.setDateEnd("2020-02-20");
        Integer[] publisherIds = new Integer[]{0};
        reportConditionDTO.setPublisherId(publisherIds);
        String[] dimensions = new String[]{"day","publisherId"};
        reportConditionDTO.setDimension(dimensions);
        this.doPost("/report/lr/list", reportConditionDTO);
    }
}
