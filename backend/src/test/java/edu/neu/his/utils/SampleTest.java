package edu.neu.his.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Consumer;

public class SampleTest {
    @Test
    public void cellToStringTest() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("non_drug_item.xlsx");
        Workbook wb = new XSSFWorkbook(inputStream);
        rowIterate(wb, true, r -> {
            System.out.println(r.getCell(1).toString());
        });
    }

    private void rowIterate(Workbook wb, boolean header, Consumer<Row> consumer) {
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            int rowNums = sheet.getLastRowNum();
            int colNums = sheet.getPhysicalNumberOfRows();
            for (int i = header ? 1 : 0; i <= rowNums; i++) {
                Row row = sheet.getRow(i);
                consumer.accept(row);
            }
        }
    }

    @Test
    public void testJsonPrint() throws JsonProcessingException {
        String a = "{" +
                "  \"code\":200,\n" +
                "  \"data\":[\n" +
                "    {\n" +
                "      \"id\":1,\n" +
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
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
