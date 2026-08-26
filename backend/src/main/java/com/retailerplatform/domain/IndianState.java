package com.retailerplatform.domain;

public enum IndianState {
    MAHARASHTRA("27"), TAMIL_NADU("33"), GUJARAT("24"),
    TELANGANA("36"), KARNATAKA("29"), DELHI("07"), UTTAR_PRADESH("09");

    private final String gstStateCode;
    IndianState(String code) { this.gstStateCode = code; }
    public String getGstStateCode() { return gstStateCode; }
}
