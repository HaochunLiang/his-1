package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordTemplateControllerTest {
    private Logger logger = LoggerFactory.getLogger(MedicalRecordTemplateControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void list()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecordTemplate/list")
                .content(" {\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }
    @Test
    public void detail()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecordTemplate/detail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"id\": 4\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void create()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecordTemplate/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"title\":\"神经病模板\",\n" +
                        "      \"type\":1,\n" +
                        "      \"chief_complaint\":\"主诉\",\n" +
                        "      \"current_medical_history\":\"无现病史\",\n" +
                        "      \"current_treatment_situation\":\"现病治疗情况\",\n" +
                        "      \"past_history\":\"无既往史\",\n" +
                        "      \"allergy_history\":\"无过敏史\",\n" +
                        "      \"physical_examination\":\"正常\",\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void update()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecordTemplate/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"id\":2,\n" +
                        "      \"title\":\"神经病模板\",\n" +
                        "      \"type\":1,\n" +
                        "      \"chief_complaint\":\"主诉2\",\n" +
                        "      \"current_medical_history\":\"无现病史\",\n" +
                        "      \"current_treatment_situation\":\"现病治疗情况\",\n" +
                        "      \"past_history\":\"无既往史\",\n" +
                        "      \"allergy_history\":\"无过敏史\",\n" +
                        "      \"physical_examination\":\"正常\",\n" +
                        "      \"department_id\": 1,\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void delete()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecordTemplate/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"idArr\":[2],\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

}
