package edu.neu.his.controller.hospitalAdminController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiagnoseDirectoryControllerTest {
    private Logger logger = LoggerFactory.getLogger(DiagnoseDirectoryControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void getAllSettlementCategory() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/diagnoseDirectoryManagement/allClassification")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void listAllDisease() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseDirectoryManagement/searchAllByClassificationId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"classification_id\":1\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void DiseaseFindByName() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseDirectoryManagement/findByName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"name\":\"阿米巴病带菌者\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void insertDisease() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseDirectoryManagement/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\":9999999,\n" +
                        "      \"name\":\"传染性单核细胞增多2\",\n" +
                        "      \"code\": \"ZZXY30\",\n" +
                        "      \"classification_id\":1,\n" +
                        "      \"pinyin\":\"CRXDHXBZDZ\",\n" +
                        "      \"custom_name\":\"传单\",\n" +
                        "      \"custom_pinyin\":\"ChuanDan\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateDisease() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseDirectoryManagement/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"raw_id\":29396,\n" +
                        "      \"id\":29396,\n" +
                        "      \"name\":\"传染性单核细胞增多症\",\n" +
                        "      \"code\":\"A1019\",\n" +
                        "      \"classification_id\":1,\n" +
                        "      \"pinyin\":\"CRXDHXBZDZ\",\n" +
                        "      \"custom_name\":\"传单\",\n" +
                        "      \"custom_pinyin\":\"ChuanDan\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteDisease() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseDirectoryManagement/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"data\":[\"29395\"]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
