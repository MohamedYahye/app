package com.register.afmelden.mohamed.afmelden;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Created by mohamed on 5-12-2014.
 */
public class bCrypt {
    private String generatedPass = null;
    public String pass;

    protected String bCrypt(String pass){
        String passwordHash = pass;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPass = sb.toString();

        }catch (NoSuchAlgorithmException no){
            no.printStackTrace();
        }

        return generatedPass;
    }


}
