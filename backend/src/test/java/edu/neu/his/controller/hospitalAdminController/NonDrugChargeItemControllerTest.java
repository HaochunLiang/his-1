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
public class NonDrugChargeItemControllerTest {
    private Logger logger = LoggerFactory.getLogger(NonDrugChargeItemControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void getAllSettlementCategory() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/nonDrugChargeItemManagement/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void insertNonDrugCharge() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/nonDrugChargeItemManagement/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\": \"A1010.10.122\",\n" +
                        "      \"pinyin\": \"DQJ2\",\n" +
                        "      \"format\": \"次\",\n" +
                        "      \"name\": \"小抢救2\",\n" +
                        "      \"fee\": 100.0,\n" +
                        "      \"expense_classification_id\": 20,\n" +
                        "      \"department_id\": 1,\n" +
                        "      \"expense_classification_name\": \"门诊手术费\",\n" +
                        "      \"department_name\": \"神经科\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void updateNonDrugCharge() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/nonDrugChargeItemManagement/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\": \"101\",\n" +
                        "      \"pinyin\": \"DQJ2\",\n" +
                        "      \"format\": \"次\",\n" +
                        "      \"name\": \"小抢救2\",\n" +
                        "      \"fee\": 100.0,\n" +
                        "      \"expense_classification_id\": 20,\n" +
                        "      \"department_id\": 1,\n" +
                        "      \"expense_classification_name\": \"门诊手术费\",\n" +
                        "      \"department_name\": \"神经科\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void deleteNonDrugCharge() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/nonDrugChargeItemManagement/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"data\":[\"1\"]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


}
