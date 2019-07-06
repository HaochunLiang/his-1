package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseTemplateControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DailyCheckControllerTest {

    private Logger logger = LoggerFactory.getLogger(DailyCheckControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void init() {
    }

    @Test
    public void getReport() {
    }

    @Test
    public void getRegistrationFee() {
    }

    @Test
    public void getAllClassifitation() {
    }

    @Test
    public void getClassifitationFee() {
    }

    @Test
    public void getBillRecord() {
    }

    @Test
    public void confirmCheck() {
    }

    @Test
    public void history() {
    }

    @Test
    public void getDepartmentCheck() {
    }

    @Test
    public void getUserCheck() {
    }
}