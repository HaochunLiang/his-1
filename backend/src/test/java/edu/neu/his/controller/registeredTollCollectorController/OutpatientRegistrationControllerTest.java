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
public class OutpatientRegistrationControllerTest {
    private Logger logger = LoggerFactory.getLogger(OutpatientRegistrationControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void init() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/outpatientRegistration/init")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void syncDoctorList() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientRegistration/syncDoctorList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"department_id\":1,\n" +
                        "      \"registration_level_id\":1\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void calulateFee() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientRegistration/calculateFee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"address\": \"undefined\",\n" +
                        "      \"age\": 17,\n" +
                        "      \"birthday\": \"2002-05-29 10:54:31\",\n" +
                        "      \"consultation_date\": \"2019-05-29 10:54:41\",\n" +
                        "      \"department_id\": 2,\n" +
                        "      \"gender\": \"female\",\n" +
                        "      \"has_record_book\": 1 ,\n" +
                        "      \"medical_category\": \"default\",\n" +
                        "      \"medical_certificate_number\": \"222222222222222222\",\n" +
                        "      \"medical_certificate_number_type\": \"id\",\n" +
                        "      \"medical_insurance_diagnosis\": \"哈哈哈哈\",\n" +
                        "      \"name\": \"李先生\",\n" +
                        "      \"outpatient_doctor_id\": 10 ,\n" +
                        "      \"registration_level_id\": 1,\n" +
                        "      \"registration_source\": \"local\",\n" +
                        "      \"settlement_category_id\": 2\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void confirm() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientRegistration/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"address\" : \"沈阳市东北大学\",\n" +
                        "      \"age\" : 17,\n" +
                        "      \"birthday\" : \"1998.03.23\",\n" +
                        "      \"consultation_date\": \"2019.10.2\",\n" +
                        "      \"department_id\": 3,\n" +
                        "      \"gender\": \"男\",\n" +
                        "      \"has_record_book\": 1,\n" +
                        "      \"medical_category\": \"不知道什么意思\",\n" +
                        "      \"medical_certificate_number\": \"371625223284621134\",\n" +
                        "      \"medical_certificate_number_type\": \"id\",\n" +
                        "      \"medical_insurance_diagnosis\": \"不知道什么意思\",\n" +
                        "      \"patient_name\": \"王蓓蕾\",\n" +
                        "      \"outpatient_doctor_id\"  : 3,\n" +
                        "      \"settlement_category_id\": 2,\n" +
                        "      \"registration_source\": \"app挂号\",\n" +
                        "      \"registration_level_id\": 1,\n" +
                        "      \"should_pay\" : 28.24,\n" +
                        "      \"truely_pay\" : 100,\n" +
                        "      \"_uid\": 10002,\n" +
                        "      \"retail_fee\" : 71.76\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void withdrawNumber() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/outpatientRegistration/withdrawNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"medical_record_id\" : 10000010,\n" +
                        "      \"_uid\": 10002\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
