package edu.neu.his.controller.outpatientMedicalRecordController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MedicalRecordControllerTest {
    private Logger logger = LoggerFactory.getLogger(MedicalRecordControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void getPatientList()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
            post("/medicalRecord/getPatientList")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(" {\n" +
                            "      \"_uid\":1" +
                            "\n" +
                            "    }")

                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void registrationInfo() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/registrationInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_certificate_number\" : \"371625223284621134\",\n" +
                        "      \"type\":\"id\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void createMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/getMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 1\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void recordHistory() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/allHistoryMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"type\":\"id\",\n" +
                        "      \"medical_certificate_number\":\"371625223284621134\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse(); // 请求返回的结果
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void saveMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/saveMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "      \"id\":3,\n" +
                        "      \"status\":\"诊毕\",\n" +
                        "      \"chief_complaint\":\"主诉\",\n" +
                        "      \"current_medical_history\":\"无现病史\",\n" +
                        "      \"current_treatment_situation\":\"现病治疗情况\",\n" +
                        "      \"past_history\":\"无既往史\",\n" +
                        "      \"allergy_history\":\"无过敏史\",\n" +
                        "      \"physical_examination\":\"正常\",\n" +
                        "      \"create_time\":\"2019-08-08\",\n" +
                        "      \"diagnose\":{\n" +
                        "        \"western_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":1,\n" +
                        "            \"disease_name\":\"神经病\",\n" +
                        "            \"disease_code\":\"XY82873\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":true\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"disease_id\":2,\n" +
                        "            \"disease_name\":\"癫痫\",\n" +
                        "            \"disease_code\":\"XY82823\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":true\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"chinese_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":3,\n" +
                        "            \"disease_name\":\"气虚\",\n" +
                        "            \"disease_code\":\"ZY23873\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"气血逆行，印堂发黑\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":false\n" +
                        "          },{\n" +
                        "            \"disease_id\":4,\n" +
                        "            \"disease_name\":\"肾虚\",\n" +
                        "            \"disease_code\":\"ZY82343\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"天庭饱满，地阁方圆\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":false\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "\n" +
                        "    }\n" +
                        "  }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void updateMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/updateMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "      \"id\":3,\n" +
                        "      \"chief_complaint\":\"主诉2\",\n" +
                        "      \"current_medical_history\":\"无现病史\",\n" +
                        "      \"current_treatment_situation\":\"现病治疗情况\",\n" +
                        "      \"past_history\":\"无既往史\",\n" +
                        "      \"allergy_history\":\"无过敏史\",\n" +
                        "      \"physical_examination\":\"正常\",\n" +
                        "      \"create_time\":\"2019-08-08\",\n" +
                        "      \"diagnose\":{\n" +
                        "        \"western_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":1,\n" +
                        "            \"disease_name\":\"神经病\",\n" +
                        "            \"disease_code\":\"XY82873\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":true\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"disease_id\":2,\n" +
                        "            \"disease_name\":\"癫痫\",\n" +
                        "            \"disease_code\":\"XY82823\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":true\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"chinese_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":3,\n" +
                        "            \"disease_name\":\"气虚\",\n" +
                        "            \"disease_code\":\"ZY23873\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"气血逆行，印堂发黑\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":false\n" +
                        "          },{\n" +
                        "            \"disease_id\":4,\n" +
                        "            \"disease_name\":\"肾虚\",\n" +
                        "            \"disease_code\":\"ZY82343\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"天庭饱满，地阁方圆\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":false\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "\n" +
                        "    }\n" +
                        "  }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void confirmMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/confirmMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "      \"id\":2,\n" +
                        "      \"chief_complaint\":\"主诉4\",\n" +
                        "      \"current_medical_history\":\"无现病史\",\n" +
                        "      \"current_treatment_situation\":\"现病治疗情况\",\n" +
                        "      \"past_history\":\"无既往史\",\n" +
                        "      \"allergy_history\":\"无过敏史\",\n" +
                        "      \"physical_examination\":\"正常\",\n" +
                        "      \"create_time\":\"2019-08-08\",\n" +
                        "      \"diagnose\":{\n" +
                        "        \"western_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":1,\n" +
                        "            \"disease_name\":\"神经病\",\n" +
                        "            \"disease_code\":\"XY82873\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":true\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"disease_id\":2,\n" +
                        "            \"disease_name\":\"癫痫\",\n" +
                        "            \"disease_code\":\"XY82823\",\n" +
                        "            \"diagnose_type\":\"西医\",\n" +
                        "            \"syndrome_differentiation\":\"\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":true\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"chinese_diagnose\":[\n" +
                        "          {\n" +
                        "            \"disease_id\":3,\n" +
                        "            \"disease_name\":\"气虚\",\n" +
                        "            \"disease_code\":\"ZY23873\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"气血逆行，印堂发黑\",\n" +
                        "            \"main_symptom\":false,\n" +
                        "            \"suspect\":false\n" +
                        "          },{\n" +
                        "            \"disease_id\":4,\n" +
                        "            \"disease_name\":\"肾虚\",\n" +
                        "            \"disease_code\":\"ZY82343\",\n" +
                        "            \"diagnose_type\":\"中医\",\n" +
                        "            \"syndrome_differentiation\":\"天庭饱满，地阁方圆\",\n" +
                        "            \"main_symptom\":true,\n" +
                        "            \"suspect\":false\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "\n" +
                        "    }\n" +
                        "  }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

}
