package com.roca12.apolobot.util;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

  private static final String algoritmo = "AES";

  private static final String tipoCifrado = "AES/GCM/NoPadding";

  public static String encrypt(String llave, String iv, String texto) {
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(tipoCifrado);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      e.printStackTrace();
    }

    SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), algoritmo);
    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes());
    try {
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }

    byte[] encrypted = null;
    try {
      encrypted = cipher.doFinal(texto.getBytes());
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }

    return new String(encodeBase64(encrypted));
  }

  public static String decrypt(String llave, String iv, String encrypted) {
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(tipoCifrado);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      e.printStackTrace();
    }

    SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), algoritmo);
    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes());
    byte[] enc = decodeBase64(encrypted);
    try {
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {

      e.printStackTrace();
    }

    byte[] decrypted = null;
    try {
      decrypted = cipher.doFinal(enc);
    } catch (IllegalBlockSizeException | BadPaddingException e) {

      e.printStackTrace();
    }

    return new String(decrypted);
  }

  //	public static void main(String[] args) {
  //		String text = "hola mundo";
  //		String iv = "holamundohfhfhtf";
  //		String key = "holamundohfhfhtf";
  //		System.out.println(iv.getBytes().length);
  //		String encoded = encrypt(key, iv, text);
  //		System.out.println(encoded);
  //	}
}
