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

/**
 * Utility class for encrypting and decrypting text using AES encryption. This class provides
 * methods to encrypt and decrypt strings using AES/GCM/NoPadding cipher transformation with Base64
 * encoding for the encrypted output.
 *
 * @author roca12
 * @version 2025-I
 */
public class Encryptor {

  /** The encryption algorithm used (AES) */
  private static final String algoritmo = "AES";

  /** The cipher transformation specification (AES/GCM/NoPadding) */
  private static final String tipoCifrado = "AES/GCM/NoPadding";

  /**
   * Encrypts a text string using AES encryption.
   *
   * @param llave The encryption key
   * @param iv The initialization vector for GCM mode
   * @param texto The plain text to encrypt
   * @return The Base64-encoded encrypted string
   */
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

  /**
   * Decrypts a Base64-encoded encrypted string.
   *
   * @param llave The encryption key (must be the same as used for encryption)
   * @param iv The initialization vector (must be the same as used for encryption)
   * @param encrypted The Base64-encoded encrypted string
   * @return The decrypted plain text
   */
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
