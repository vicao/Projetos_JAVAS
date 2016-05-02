
package criptografia;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec; 
import javax.crypto.Cipher; 

public class AES { 
    static String IV = "AAAAAAAAAAAAAAAA";
    static String textopuro = "teste texto 12345678\0\0\0"; 
    static String chaveencriptacao = "0123456789abcdef";
    public static void main(String [] args) { 
        try {
            System.out.println("Texto Puro: " + textopuro);
            byte[] textoencriptado = encrypt(textopuro, chaveencriptacao);
            System.out.print("Texto Encriptado: ");
            for (int i=0; i<textoencriptado.length; i++) 
                System.out.print(new Integer(textoencriptado[i])+" ");
            System.out.println("");
            String textodecriptado = decrypt(textoencriptado, chaveencriptacao);
            System.out.println("Texto Decriptado: " + textodecriptado);
        } catch (Exception e) { e.printStackTrace();
        } 
    } 
    public static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE"); 
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8")); 
    }
    
    public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception{ 
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8"))); 
        return new String(decripta.doFinal(textoencriptado),"UTF-8");
    }
}



/*

Leia mais em: Utilizando Criptografia Simétrica em Java http://www.devmedia.com.br/utilizando-criptografia-simetrica-em-java/31170#ixzz3e5I1CrAN
O Advanced Encryption Standard (AES) é uma cifra de bloco sucessora do DES que surgiu através de um concurso promovido pelo governo dos Estados Unidos para substituir o DES. Entre as condições necessárias para a candidatura de um algoritmo foram especificadas as seguintes características obrigatórias que o algoritmo deveria possuir: divulgação aberta e pública, livre de direitos autorais, e ser de chave privada (simétricos) que suporte blocos de 128 bits e chaves de 128, 192 e 256 bits. Três anos e meio após o início do concurso, o comitê responsável chegou à escolha do vencedor: Rijndael. O nome é uma fusão de Vincent Rijmen e Joan Daemen, os dois belgas criadores do algoritmo.

A criptografia AES usa o algoritmo de criptografia Rijndael, que envolve métodos de substituição e permutação para criar dados criptografados de uma mensagem.

O AES foi oficialmente anunciado em 26 de novembro de 2001 e tornou-se um padrão em 26 de maio de 2002. O AES atualmente é dos algoritmos mais populares usados para criptografia de chave simétrica.

O AES tem como principais características segurança, desempenho, facilidade de implementação, flexibilidade e exige pouca memória, o que o torna adequado para operar em ambientes restritos como Smart cards, PDAs e telefones celulares.

Basicamente o AES opera sobre um arranjo bidimensional de bytes com 4x4 posições. Para criptografar, cada turno do AES consiste em quatro estágios: AddRoundKey onde cada byte do estado é combinado com a subchave própria do turno e cada subchave é derivada da chave principal usando-se um algoritmo de escalonamento de chaves, SubBytes que consiste de uma substituição não linear onde cada byte é substituído por outro de acordo com uma tabela de referência, ShiftRows que consiste de uma transposição onde cada fileira do estado é deslocada em um determinado número de posições, MixColumns que consiste de uma operação de mescla que opera nas colunas do estado e combina os quatro bytes de cada coluna usando uma transformação linear. Porém o último turno é diferente onde o estágio de MixColumns é substituído por um novo estágio de AddRoundKey.

Tanto em hardware quanto em software o AES bastante é rápido, sendo utilizado nos processadores da Intel. Como o AES é um algoritmo de chave simétrica, podemos utilizar a chave tanto para criptografar quanto para descriptografar a mensagem. A chave em questão será representada em 256 bits, o que significa que se alguém tentar quebrar a mensagem teria que descobrir o valor da chave de 256 bits. A tecnologia para decifrar uma chave de 256 bits em uma quantidade razoável de tempo ainda não foi inventada.

Segue na Listagem 2 um exemplo utilizando a biblioteca padrão do Java para criptografar e descriptografar um texto puro utilizando o AES.

Listagem 2. Criptografando e Descriptografando dados com AES.

  import javax.crypto.spec.SecretKeySpec;
  import javax.crypto.spec.IvParameterSpec;
   
  import javax.crypto.Cipher;
   
  public class EncriptaDecriptaAES {
         
         static String IV = "AAAAAAAAAAAAAAAA";
         static String textopuro = "teste texto 12345678\0\0\0";
         static String chaveencriptacao = "0123456789abcdef";
   
         public static void main(String [] args) {
   
               try {
                      
                      System.out.println("Texto Puro: " + textopuro);
                       
                      byte[] textoencriptado = encrypt(textopuro, chaveencriptacao);
                       
                      System.out.print("Texto Encriptado: ");
   
                      for (int i=0; i<textoencriptado.length; i++)
                             System.out.print(new Integer(textoencriptado[i])+" ");
                      
                      System.out.println("");
                       
                      String textodecriptado = decrypt(textoencriptado, chaveencriptacao);
                       
                      System.out.println("Texto Decriptado: " + textodecriptado);
                
               } catch (Exception e) {
                      e.printStackTrace();
               }
         }
   
         public static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
               Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
               SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
               encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
               return encripta.doFinal(textopuro.getBytes("UTF-8"));
         }
   
         public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception{
               Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
               SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
               decripta.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
               return new String(decripta.doFinal(textoencriptado),"UTF-8");
         }
         
  }
Executando o algoritmo acima teremos como resultado a saída abaixo:

  Texto Puro: teste texto 12345678
  Texto Encriptado: 80 20 103 61 -80 -122 -15 -45 116 -53 73 -33 55 -58 -99 25 -106 124 -117 -18 37 -127 86 112 39 11 -60 -118 -125 -66 -111 96 
  Texto Decriptado: teste texto 12345678
  
Podemos verificar a semelhança com o algoritmo anterior (DES), porém com diferenças nas configurações dos métodos.



Leia mais em: Utilizando Criptografia Simétrica em Java http://www.devmedia.com.br/utilizando-criptografia-simetrica-em-java/31170#ixzz3e5IE57Cq




*/