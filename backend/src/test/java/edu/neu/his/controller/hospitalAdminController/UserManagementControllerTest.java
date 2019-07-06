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
public class UserManagementControllerTest {
    private Logger logger = LoggerFactory.getLogger(UserManagementControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void selectAllUser() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/userManagement/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void addNewUser() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/userManagement/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"username\": \"admin5\",\n" +
                        "      \"password\": \"2e23e23e\",\n" +
                        "      \"real_name\": \"XUranus\",\n" +
                        "      \"department_id\": 1,\n" +
                        "      \"role_id\": 1,\n" +
                        "      \"participate_in_scheduling\": true,\n" +
                        "      \"title\": \"主任\",\n" +
                        "      \"role_name\": \"医院管理员\",\n" +
                        "      \"department_name\": \"神经科\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public  void deleteUser() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/userManagement/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"data\":[10001]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/userManagement/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"uid\":1,\n" +
                        "      \"username\": \"yu\",\n" +
                        "      \"password\": \"123456\",\n" +
                        "      \"real_name\": \"X\",\n" +
                        "      \"department_id\": 1,\n" +
                        "      \"role_id\": 1,\n" +
                        "      \"participate_in_scheduling\": true,\n" +
                        "      \"title\": \"主任\",\n" +
                        "      \"role_name\": \"医院管理员\",\n" +
                        "      \"department_name\": \"神经科\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
