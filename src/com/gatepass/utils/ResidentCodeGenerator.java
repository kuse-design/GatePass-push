package com.gatepass.utils;

import com.gatepass.data.repositories.ResidentRepository;

public class ResidentCodeGenerator {

    private static final String PREFIX = "KUSE-RES-";

    public static String generate(ResidentRepository residentRepository) {
        long count = residentRepository.count() + 1;
        return PREFIX + String.format("%03d", count);
    }
}
