package edu.neu.his.controller.registeredTollCollectorController;

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
public class ChargeAndRefundControllerTest {
    private Logger logger = LoggerFactory.getLogger(ChargeAndRefundControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void info()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/outpatientCharge/getChargeItems")
                .param("medical_record_id","2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void registrationByRecordId()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/outpatientCharge/registrationByRecordId")
                .param("medical_record_id","2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void refund() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientCharge/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 2,\n" +
                        "      \"charges_id_list\": [1,2],\n" +
                        "      \"_uid\": 10002\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void collect() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientCharge/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 2,\n" +
                        "      \"charges_id_list\": [2],\n" +
                        "      \"should_pay\" : 28.24,\n" +
                        "      \"truely_pay\" : 100,\n" +
                        "      \"retail_fee\" : 71.76,\n" +
                        "      \"_uid\": 10002\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void historyInfo() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/outpatientCharge/getHistoryChargeItems")
                .contentType(MediaType.APPLICATION_JSON)
                .param("medical_record_id","1")
                .param("start_time","2018-06-09 14:48:00")
                .param("end_time","2019-06-11 08:00:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
