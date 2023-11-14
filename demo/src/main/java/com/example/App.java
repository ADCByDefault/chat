package com.example;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HashMap <String, Integer> lista = new HashMap();
        lista.put("nome 1", null);
        lista.put("nome 2", null);
        lista.put("nome 3", null);
        String s = String.join("\n", lista.keySet());
        System.out.println(s);
    }
}
