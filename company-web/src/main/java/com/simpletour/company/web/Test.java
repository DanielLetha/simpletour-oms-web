package com.simpletour.company.web;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by Mario on 2016/4/12.
 */
public class Test {
    public static void main(String[] args) {
        String password = new Md5Hash(123456 + "","9ef455").toBase64();
        System.out.println(password);
    }
}
