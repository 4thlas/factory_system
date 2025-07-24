package com.athlas.factory_system;

import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.services.FacilityService;
import com.athlas.factory_system.services.EmployeeService;
import com.athlas.factory_system.services.ProductsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FactorySystemApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FactorySystemApplication.class, args);

        FacilityService facilityService = context.getBean(FacilityService.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        ProductsService productsService = context.getBean(ProductsService.class);
        FacilityRepository facilityRepository = context.getBean(FacilityRepository.class);


        //TEST

        long facilityCount = facilityRepository.count();

        for (long i = 1; i <= facilityCount; i++)
        {
            System.out.println(employeeService.getFacilityWorkers((int) i));
        }

        employeeService.removeWorker(7);

        System.out.println("\n -------- \n");

        for (long i = 1; i <= facilityCount; i++)
        {
            System.out.println(employeeService.getFacilityWorkers((int) i));
        }

    }

}
