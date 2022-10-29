package com.mia.apa;

import java.util.Random;

public class Invoice {
    static Random random;

    public static int invoiceNumber(){
        random = new Random();
        return random.nextInt(100000);
    }
}
