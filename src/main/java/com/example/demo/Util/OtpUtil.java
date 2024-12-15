package com.example.demo.Util;
import java.util.Random;

public class OtpUtil {
    public String generateOtp(){
        return String.format("%06d",new Random().nextInt(999999));
    }
}
