package criptografia;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */

public class ROT47 {

    String _mensagem_original = "";
    String _mensagem_cifrada = "";
    private String _alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int _chave = 47;
    
    public  String getCifra(String textoClaro) {
       _mensagem_original = textoClaro;
        int novo_valor_ascii;
        int valor_ascii;
        _mensagem_cifrada = "";
        //percorra o meu texto claro
        for (int i = 0; i < this._mensagem_original.length(); i++) {
            valor_ascii = (int) this._mensagem_original.charAt(i);
            if (valor_ascii >= 33 && valor_ascii <= 126) {
                valor_ascii -= 33;
                novo_valor_ascii = (valor_ascii + this._chave) % 94;
                _mensagem_cifrada += (char) (novo_valor_ascii + 33);
            } else {
                _mensagem_cifrada += this._mensagem_original.charAt(i);
            }
        }
        return _mensagem_cifrada;
        
        
    }

    public String getDecifraROT47(String mensagem_cifrada) {
        int novo_valor_ascii;
        int valor_ascii;
        String mensagem_decifrada = "";
        //percorra o meu texto claro
        for (int i = 0; i < mensagem_cifrada.length(); i++) {
            valor_ascii = (int) mensagem_cifrada.charAt(i);
            if (valor_ascii >= 33 && valor_ascii <= 126) {
                valor_ascii -= 33;
                novo_valor_ascii = (valor_ascii + this._chave) % 94;
                mensagem_decifrada += (char) (novo_valor_ascii - 33);
            } else {
                mensagem_decifrada += mensagem_cifrada.charAt(i);
            }
        }
        return mensagem_decifrada;
    }
}
