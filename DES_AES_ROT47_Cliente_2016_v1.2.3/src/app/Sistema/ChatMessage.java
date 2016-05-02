
package app.Sistema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */

public class ChatMessage implements Serializable {
    
    private String name;
    private String text;
    
    /*
    Variaveis usadas na Criptografia
    bytetxt -> texto criptografado em DES
    tpCrypt -> tipo de criptografia escolhido;
    chaveDES -> Chave utilizada para criptografar e descriptografar
    keygenerator -> Chave gerada aleatoriamente
    isGenaratedKey -> variavel auxiliar para validar se a chave de criptografia foi gerada.
    
    */
    private byte[] bytetxt;
    private int tpCrypt = 0;
    private SecretKey chaveDES = null;
    private String chaveAES = null;
    private KeyGenerator keygenerator = null;
    private boolean isGenaratedKey = false;
    //----------------------------------
    
    

    private String nameReserved;
    private Set<String> setOnlines = new HashSet<String>();
    private Action action;

    public boolean isIsGenaratedKey() {
        return isGenaratedKey;
    }

    public void setIsGenaratedKey(boolean isGenaratedKey) {
        this.isGenaratedKey = isGenaratedKey;
    }

    public SecretKey getChaveDES() {
        return chaveDES;
    }

    public void setChaveDES(SecretKey chaveDES) {
        this.chaveDES = chaveDES;
    }

    public KeyGenerator getKeygenerator() {
        return keygenerator;
    }

    public void setKeygenerator(KeyGenerator keygenerator) {
        this.keygenerator = keygenerator;
    }

    
    
    public int getTpCrypt() {
        return tpCrypt;
    }

    public void setTpCrypt(int tpCrypt) {
        this.tpCrypt = tpCrypt;
    }
    
    
    public String getName() {
        return name;
    }

    public byte[] getBytetxt() {
        return bytetxt;
    }

    public void setBytetxt(byte[] bytetxt) {
        this.bytetxt = bytetxt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getChaveAES() {
        return chaveAES;
    }

    public void setChaveAES(String chaveAES) {
        this.chaveAES = chaveAES;
    }    
        
    
    /*
    
    Enum contendo as acoes diponiveis,
    Connect
    Disconnect,
    SEND_ONE,
    SEND_ALL
    USERS_ONLINE
    
    */
    public enum Action {
        CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE
    }
}
