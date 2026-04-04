package com.gatepass.utils;

import com.gatepass.data.repositories.GatePassRepository;
import java.util.Random;

public class RandomCodeGenerator {

    private static final String ESTATE_SIGNATURE = "KUSE-";

    public static String generateCode(GatePassRepository gatePassRepository) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        while (true) {
            StringBuilder code = new StringBuilder(ESTATE_SIGNATURE);

            for (int count = 0; count < 6; count++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }

            String generatedCode = code.toString();

            if (gatePassRepository.findByCode(generatedCode) == null) {
                return generatedCode;
            }
        }
    }
}
















































//package com.gatepass.utils;
//
//import com.gatepass.data.repositories.GatePassRepository;
//
//import java.util.Random;
//
//public class RandomCodeGenerator {
//    private static final String ESTATE_SIGNATURE = "KUSE-";
//    private static GatePassRepository repository;
//
//    public static  String generateCode(GatePassRepository gatePassRepository) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        Random random = new Random();
//
//        while (true) {
//            StringBuilder code = new StringBuilder(ESTATE_SIGNATURE);
//
//            for (int count = 0; count < 6; count++) {
//                int index = random.nextInt(characters.length());
//                code.append(characters.charAt(index));
//            }
//
//            String generatedCode = code.toString();
//            if(repository.findByCode(generatedCode) == null){
//                return generatedCode;
//            }
//        }
//    }
//}
//
