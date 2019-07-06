package edu.neu.his.bean.drug;

import edu.neu.his.bean.exam.ExamControllerTest;
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
public class DrugDispenseControllerTest {
    private Logger logger = LoggerFactory.getLogger(DrugDispenseControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void list() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/drugDispense/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"medical_record_id\" :10002\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String a = response.getContentAsString();
        Utils.prettyPrintJSON(a);
    }

    @Test
    public void submit() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/drugDispense/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"prescription_item_id\" :[1]\n" +
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
    public void withdrawHistory() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/drugDispense/withdrawHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"prescription_item_id\" :[1]\n" +
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