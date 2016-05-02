package criptografia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
 import javax.crypto.KeyGenerator; 
 import javax.crypto.SecretKey; 
import sun.misc.BASE64Encoder;
 

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918

 * @version 5.0
 * 
 */



 public class EncriptaDecriptaDES_v2 {
	 
    static KeyGenerator keygenerator;// = KeyGenerator.getInstance("DES");
    static SecretKey chaveDES = null;
    static Cipher cifraDESCBC; // Cria a cifra
    static Cipher cifraDESECB; // Cria a cifra
    
    
    static String Data = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
    
    /**
     *  Metodo para criptografar o texto utilizando o processo DES
     * @param texto  - texto a ser encriptado
     * @param chave  Chave a ser utilizada pra realizar a criptografia
     *  @return array de bytes com o texto criptografado.
     */
     public static byte[] DESPaddingECB(String texto,SecretKey chave){
        byte[] textoEncriptado = null;
             try {

                 cifraDESECB = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação
                 cifraDESECB.init(Cipher.ENCRYPT_MODE, chave); // Texto puro
                 byte[] asercriptografado = texto.getBytes();
                 //System.out.println("Texto [Formato de Byte] : " + asercriptografado);
                // System.out.println("Texto Puro : " + new String(asercriptografado)); // Texto encriptado
                 textoEncriptado = cifraDESECB.doFinal(asercriptografado);
                 System.out.println("Texto Claro : " + texto);
                 System.out.println("Texto Encriptado : " + textoEncriptado);                
             } catch (Exception ex) {
                 System.out.println(Data + "[Exception]encriptar : " + ex.getMessage());   
             }
          return textoEncriptado;               
    }     

    /**
     *  Metodo para criptografar o texto utilizando o processo DES
     * @param texto  - texto a ser encriptado
     * @param chave  Chave a ser utilizada pra realizar a criptografia
     *  @return array de bytes com o texto criptografado.
     */
     public static byte[] DESPaddingCBC(String texto,SecretKey chave){
        byte[] textoEncriptado = null;
        String strCipherText = new String();
             try {
                 cifraDESCBC = Cipher.getInstance("DES/CBC/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação
                 cifraDESCBC.init(Cipher.ENCRYPT_MODE, chave); // Texto puro
                 byte[] asercriptografado = texto.getBytes();
                 //System.out.println("Texto [Formato de Byte] : " + asercriptografado);
                // System.out.println("Texto Puro : " + new String(asercriptografado)); // Texto encriptado
                 textoEncriptado = cifraDESCBC.doFinal(asercriptografado);
                 strCipherText = new BASE64Encoder().encode(textoEncriptado);
                 System.out.println("Texto Claro : " + texto);
                 System.out.println("Texto Encriptado : " + textoEncriptado);                
             } catch (Exception ex) {
                 System.out.println(Data + "[Exception]encriptar : " + ex.getMessage());   
             }
          return textoEncriptado;               
    }   
     
     
     
     /**
      * Metodo para descriptografar usando o processo DES
      * @param textoEncriptado array de bytes encriptado
      * @param chave chave de descriptografia
      * @return texto descriptografado
      */
    public static String DESPaddingECBDecrypter(byte[] textoEncriptado,SecretKey chave){
          String descriptografado = "";
             try {
                 //keygenerator = KeyGenerator.getInstance("DES");                 
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação                 
                 //System.out.println("Texto Encriptografado : " + new String(textoEncriptado)); // Texto encriptado
                 cifraDES.init(Cipher.DECRYPT_MODE, chave); // Decriptografa o texto
                 byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado); 
                 descriptografado = new String(textoDecriptografado);
                 //System.out.println("Texto Decriptografado : " + descriptografado);
             } catch (Exception ex) {
                 System.out.println(Data + "[Exception]descriptar : " + ex.getMessage());   
             }
                   return   descriptografado;   
    }
    
     /**
      * Metodo para descriptografar usando o processo DES
      * @param textoEncriptado array de bytes encriptado
      * @param chave chave de descriptografia
      * @return texto descriptografado
      */
    public static String DESPaddingCBCDecrypter(byte[] textoEncriptado,SecretKey chave){
          String descriptografado = "";
             try {

                 cifraDESCBC = Cipher.getInstance("DES/CBC/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação                 
                 //System.out.println("Texto Encriptografado : " + new String(textoEncriptado)); // Texto encriptado
                 cifraDESCBC.init(Cipher.DECRYPT_MODE, chave,cifraDESCBC.getParameters()); // Decriptografa o texto
                 byte[] textoDecriptografado = cifraDESCBC.doFinal(textoEncriptado); 
                 descriptografado = new String(textoDecriptografado);
                 //System.out.println("Texto Decriptografado : " + descriptografado);
             } catch (Exception ex) {
                 System.out.println(Data + "[Exception]descriptar : " + ex.getMessage());   
             }
                   return   descriptografado;   
    }
    
    
    
    
    /**
     * Metodo antigo de criptografia
     * @param texto texto a ser criptografado
     * @return array de bytes
     */
    public static byte[] encriptarOLD(String texto){
        byte[] textoEncriptado = null;
             try {
                 keygenerator = KeyGenerator.getInstance("DES");   
                 chaveDES = keygenerator.generateKey();
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação
                 cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES); // Texto puro
                 byte[] asercriptografado = texto.getBytes();
                 //System.out.println("Texto [Formato de Byte] : " + asercriptografado);
                // System.out.println("Texto Puro : " + new String(asercriptografado)); // Texto encriptado
                 textoEncriptado = cifraDES.doFinal(asercriptografado);
                 System.out.println("Texto Encriptado : " + textoEncriptado);                
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_v2.class.getName()).log(Level.SEVERE, null, ex);
             }
          return textoEncriptado;               
    }  
    
    /**
     * Metodo antigo de descriptografia
     * @param textoEncriptado texto a ser encriptador
     * @return string contendo o texto criptografado
     */
    public static String descriptarOLD(byte[] textoEncriptado){
          String descriptografado = "";
             try {
                 //keygenerator = KeyGenerator.getInstance("DES");                 
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação                 
                 //System.out.println("Texto Encriptografado : " + new String(textoEncriptado)); // Texto encriptado
                 cifraDES.init(Cipher.DECRYPT_MODE, chaveDES); // Decriptografa o texto
                 byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado); 
                 descriptografado = new String(textoDecriptografado);
                 //System.out.println("Texto Decriptografado : " + descriptografado);
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_v2.class.getName()).log(Level.SEVERE, null, ex);
             }
                   return   descriptografado;   
    }

   
    
}
