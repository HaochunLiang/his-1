package edu.neu.his.bean.diagnosis;

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

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordDiagnoseTemplateControllerTest {
    private Logger logger = LoggerFactory.getLogger(MedicalRecordDiagnoseTemplateControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void list() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseTemplate/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"_uid\": 10005\n" +
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
    public void create()  throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseTemplate/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"title\":\"XX模板12\",\n" +
                        "      \"type\":1,\n" +
                        "      \"_uid\": 10004,\n" +
                        "      \"diagnose\":{\n" +
                        "        \"western_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":1,\n" +
                        "            \"disease_name\":\"疾病\",\n" +
                        "            \"disease_code\":\"1\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":true\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"chinese_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":3,\n" +
                        "            \"disease_name\":\"气虚\",\n" +
                        "            \"disease_code\":\"ZY23873\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"气血逆行，印堂发黑\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
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
    public void update()  throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseTemplate/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\":1,\n" +
                        "      \"title\":\"XX模板4\",\n" +
                        "      \"type\":1,\n" +
                        "      \"_uid\": 10004,\n" +
                        "      \"diagnose\":{\n" +
                        "        \"western_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":1,\n" +
                        "            \"disease_name\":\"神经病\",\n" +
                        "            \"disease_code\":\"XY82873\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":true\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"chinese_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":3,\n" +
                        "            \"disease_name\":\"气虚\",\n" +
                        "            \"disease_code\":\"ZY23873\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"气血逆行，印堂发黑\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
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
    public void delete()  throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseTemplate/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"idArr\":[2,3,1]\n" +
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
    public void detail()  throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnoseTemplate/detail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"id\": 12\n" +
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