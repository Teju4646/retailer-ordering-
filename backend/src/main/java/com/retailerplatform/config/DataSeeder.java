package com.retailerplatform.config;

import com.retailerplatform.domain.*;
import com.retailerplatform.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RetailerRepository retailerRepo;
    private final FranchiseRepository franchiseRepo;
    private final ProductRepository productRepo;
    private final RetailerFranchiseMappingRepository mappingRepo;

    public DataSeeder(RetailerRepository r, FranchiseRepository f,
                       ProductRepository p, RetailerFranchiseMappingRepository m) {
        this.retailerRepo = r; this.franchiseRepo = f;
        this.productRepo = p; this.mappingRepo = m;
    }

    @Override
    public void run(String... args) {
        var pune = franchiseRepo.save(franchise("Pune West Regional", IndianState.MAHARASHTRA));
        var chennai = franchiseRepo.save(franchise("Chennai South Regional", IndianState.TAMIL_NADU));

        var sharma = retailerRepo.save(retailer("Sharma Hardware Co.", "Pune", IndianState.MAHARASHTRA));
        var iyer = retailerRepo.save(retailer("Iyer Supply House", "Chennai", IndianState.TAMIL_NADU));
        var patel = retailerRepo.save(retailer("Patel Tools Retail", "Ahmedabad", IndianState.GUJARAT));

        mappingRepo.save(mapping(sharma.getId(), pune.getId()));
        mappingRepo.save(mapping(iyer.getId(), chennai.getId()));
        mappingRepo.save(mapping(patel.getId(), pune.getId()));

        productRepo.save(product("Cordless drill 18V", "SKU-4471", "8467", 6499, 18.0));
        productRepo.save(product("Safety helmet, yellow", "SKU-2290", "6506", 450, 12.0));
        productRepo.save(product("Steel measuring tape 5m", "SKU-1183", "9017", 220, 18.0));
    }

    private Franchise franchise(String name, IndianState state) {
        Franchise f = new Franchise(); f.setName(name); f.setRegion(name); f.setState(state);
        return f;
    }
    private Retailer retailer(String name, String city, IndianState state) {
        Retailer r = new Retailer(); r.setName(name); r.setCity(city); r.setState(state);
        return r;
    }
    private Product product(String name, String sku, String hsn, double price, double gst) {
        Product p = new Product(); p.setName(name); p.setSku(sku);
        p.setHsnCode(hsn); p.setUnitPrice(price); p.setGstRatePct(gst);
        return p;
    }
    private RetailerFranchiseMapping mapping(String retailerId, String franchiseId) {
        RetailerFranchiseMapping m = new RetailerFranchiseMapping();
        m.setRetailerId(retailerId); m.setFranchiseId(franchiseId);
        return m;
    }
}
