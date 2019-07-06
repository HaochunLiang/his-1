package edu.neu.his.bean.expenseClassification;

import edu.neu.his.bean.dailyCheck.DailyCheckControllerTest;
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
public class ExpenseClassificationControllerTest {


    private Logger logger = LoggerFactory.getLogger(ExpenseClassificationControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void findAll() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/expenseClassificationManage/all")
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
    public void updateExpenseClassification() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/expenseClassificationManage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"id\":122,\n" +
                        "    \"pinyin\":\"XYF\",\n" +
                        "    \"fee_name\":\"东药费\"\n" +
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
    public void insertExpenseClassification() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/expenseClassificationManage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"id\":122,\n" +
                        "    \"pinyin\":\"BYF\",\n" +
                        "    \"fee_name\":\"北药费\"\n" +
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
    public void deleteExpenseClassification() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/expenseClassificationManage/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"idArr\":[122],\n" +
                        "    \"pinyin\":\"XYF\",\n" +
                        "    \"fee_name\":\"东药费\"\n" +
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