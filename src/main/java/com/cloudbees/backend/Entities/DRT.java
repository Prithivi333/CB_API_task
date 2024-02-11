package com.cloudbees.backend.Entities;

public enum DRT {
    DISCOUNT("discount"),TAX("tax");

    private final String drt;
    DRT(String drt){
        this.drt=drt;
    }

    public String getCode(){
        return drt;
    }
//    public static DRT fromValue(String value) {
//        for(DRT drt:DRT.values()){
//            if(drt.getCode().equalsIgnoreCase(value)){
//                return drt;
//            }
//        }
//        try {
//            throw new Exception("Invalid value passed for Discount or Tax addition");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
