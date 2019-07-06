package edu.neu.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


//开启redis缓存
//@EnableCaching

@SpringBootApplication
public class NeuHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeuHospitalApplication.class, args);
    }

}
