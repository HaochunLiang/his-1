package edu.neu.his.utils;

import edu.neu.his.auto.DiseaseClassificationMapper;
import edu.neu.his.bean.department.Department;
import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseClassification;
import edu.neu.his.bean.disease.DiseaseMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.nondrug.NonDrugChargeItemMapper;
import edu.neu.his.util.ExcelImportation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelImportationImportUtilsTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    @Rollback
    public void excelTest(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("医院科室大类对照表.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, Department.class, departmentMapper);
        excel.setColumnFields("id", "classification_id", "pinyin", "name", "type");
        ((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

    @Test
    public void departmentImport(){
        excelTest();
    }

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Test
    public void nonDrugImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("non_drug_item.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, NonDrugChargeItem.class, nonDrugChargeItemMapper);
        excel.setColumnFields("id", "code", "name", "format", "fee", "expense_classification_id", "department_id", "pinyin");
        //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.skipLine(1);
        excel.exec();
    }

    @Autowired
    DiseaseMapper diseaseMapper;

    @Autowired
    DiseaseClassificationMapper diseaseClassificationMapper;

    @Test
    public void diseaseClassificationImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("disease_classification.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, DiseaseClassification.class, diseaseClassificationMapper);
        excel.skipLine(1);
        excel.setColumnFields("id", null, "name", null, null);
        //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

    @Test
    public void diseaseImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("disease.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, Disease.class, diseaseMapper);
        excel.skipLine(2);
        excel.setColumnFields("id", "pinyin", "name", "code", "classification_id");
        //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

    @Autowired
    DrugService drugService;

    @Test
    public void drugImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("drug.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, Drug.class, drugService);
        excel.skipLine(2);
        excel.setColumnFields("id", "code", "name", "format", "unit", "manufacturer", "dosage_form", "type", "price", "pinyin", "stock");
        ((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("stock", s->100);
        excel.exec();
    }

}
