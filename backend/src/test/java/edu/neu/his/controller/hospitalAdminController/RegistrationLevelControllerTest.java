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
public class RegistrationLevelControllerTest {
    private Logger logger = LoggerFactory.getLogger(RegistrationLevelControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void listAllRegistration_level() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/registrationLevelManagement/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateRegistration_level() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/registrationLevelManagement/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\": 4,\n" +
                        "      \"name\": \"专家号\",\n" +
                        "      \"is_default\": true,\n" +
                        "      \"seq_num\": 2,\n" +
                        "      \"fee\": 20\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void insertRegistration_level() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/registrationLevelManagement/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"id\": 3,\n" +
                        "      \"name\": \"普通号\",\n" +
                        "      \"is_default\": true,\n" +
                        "      \"seq_num\": 2,\n" +
                        "      \"fee\": 10\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void deleteRegistration_level() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/registrationLevelManagement/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"data\": [1]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


}
