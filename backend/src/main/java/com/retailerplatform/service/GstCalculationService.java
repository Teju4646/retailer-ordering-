package com.retailerplatform.service;

import com.retailerplatform.domain.Franchise;
import com.retailerplatform.domain.Retailer;
import org.springframework.stereotype.Service;

@Service
public class GstCalculationService {

    public record TaxBreakdown(
        double taxableValue, double cgst, double sgst, double igst, double total) {}

    public TaxBreakdown calculate(Retailer retailer, Franchise franchise,
                                   double lineTaxableValue, double gstRatePct) {

        boolean sameState = retailer.getState() == franchise.getState();
        double totalTax = lineTaxableValue * (gstRatePct / 100.0);

        if (sameState) {
            double half = totalTax / 2.0;
            return new TaxBreakdown(lineTaxableValue, half, half, 0.0,
                lineTaxableValue + totalTax);
        } else {
            return new TaxBreakdown(lineTaxableValue, 0.0, 0.0, totalTax,
                lineTaxableValue + totalTax);
        }
    }
}
