package com.athlas.factory_system;

import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.services.FacilityService;
import com.athlas.factory_system.services.PersonelService;
import com.athlas.factory_system.services.ProductsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class FactorySystemApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FactorySystemApplication.class, args);

        FacilityService facilityService = context.getBean(FacilityService.class);

        PersonelService personelService = context.getBean(PersonelService.class);

        ProductsService productsService = context.getBean(ProductsService.class);

        FacilityRepository facilityRepository = context.getBean(FacilityRepository.class);

        //personelService.addWorker(4, "Skibidi", "manager", 1000);
        //personelService.addWorker(4, "Bob", "manager", 1000);

        try {
            personelService.assignManager(3, 4);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(facilityRepository.findById(1));
        System.out.println(facilityRepository.findById(4));

        personelService.moveWorker(3, 1);

        System.out.println(facilityRepository.findById(1));
        System.out.println(facilityRepository.findById(4));

        //personelService.removeWorker(2);

        //personelService.unassignManager(3);

        //System.out.println(facilityRepository.findById(4));



    }

}
