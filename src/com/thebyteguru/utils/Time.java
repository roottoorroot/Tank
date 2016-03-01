
package com.thebyteguru.utils;

public class Time {
    public static final long SECOND = 1000000000l; // Колличество наносекунд в секунде. Да, нам понадобятся наносекунды;
    
    public static long get(){
        return System.nanoTime(); //Единственное, что делаем, возвращаем текущее время в наносекундах;
    }
}
