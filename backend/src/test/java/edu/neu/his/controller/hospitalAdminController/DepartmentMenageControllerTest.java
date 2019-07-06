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
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentMenageControllerTest {

    private Logger logger = LoggerFactory.getLogger(DepartmentMenageControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void departmentFindByName() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/findByName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"眼科\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void listAllDepartment() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/departmentManage/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateDepartment() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"id\": 1,\n" +
                        "      \"pinyin\": \"YK\",\n" +
                        "      \"name\": \"眼科1\",\n" +
                        "      \"type\": \"门诊科室\",\n" +
                        "      \"classification_id\":1\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void insertDepartment() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"id\": 1,\n" +
                        "      \"pinyin\": \"NK\",\n" +
                        "      \"name\": \"脑科\",\n" +
                        "      \"type\": \"门诊科室\",\n" +
                        "      \"classification_id\":2\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void deleteDepartment() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"data\":[2]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void batchImport() throws Exception{//?
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/import")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(" {\n" +
                        "      \"data\":[2]\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }
}