package com.example.foodtruckclient.util;

public class NumberUtils {

    private static final int DEFAULT_BINARY_LENGTH = 16;

    public static String convertIntToBinaryString(int number) {
        return convertIntToBinaryString(number, DEFAULT_BINARY_LENGTH);
    }

    public static String convertIntToBinaryString(int number, int length) {
        return String.format("%" + length + "s", Integer.toBinaryString(number)).replace(' ', '0');
    }
}
