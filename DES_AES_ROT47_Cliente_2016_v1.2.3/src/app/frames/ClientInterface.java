package app.frames;

import Criptografia.EncriptaDecriptaDES_v2;
import app.Sistema.Sistema;
import app.Sistema.ChatMessage;
import app.Sistema.ChatMessage.Action;
import app.Sistema.ClienteService;
import criptografia.AES;
//import criptografia.ROT47;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918

 * @version 5.0
 * 
 */


public class ClientInterface extends javax.swing.JFrame {

    private Socket socketTeste;
    private ChatMessage message;
    private ClienteService service;
    private String nome = null;
    private String servidor = null;
    boolean isConected = false;
    static EncriptaDecriptaDES_v2 classeDES = new EncriptaDecriptaDES_v2();
    //static ROT47 rot = new ROT47();
    static AES aes = new AES();
    static Sistema sys = new Sistema();
    int tpCrypt = 0;
    Opcoes frameOpecoes;
    static KeyGenerator keygenerator;// = KeyGenerator.getInstance("DES");
    static SecretKey chaveDES = null;
    static String chaveAES = null;
    File autolog;// = new File("teslog.logSc");
    String Data = new SimpleDateFormat("[dd_MM_yyyy-HH_mm_ss]").format(new Date(System.currentTimeMillis()));
    String validaCrypt = "";
    byte[] txtbyte = null;
    
    
    public ClientInterface() {
        initComponents();

    }

    private class ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            ChatMessage message = null;
            try {
                while ((message = (ChatMessage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        connected(message);
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnected();
                        socketTeste.close();
                    } else if (action.equals(Action.SEND_ONE)) {                      
                        //System.out.println("::: " + message.getText() + " :::");
                        receive(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    }
                }
            } catch (IOException ex) {
                sys.EscreveLogConsole("ClienteInterface - IOException", "Erro : " + ex.getMessage());
                //Logger.getLogger(InterfaceAntiga.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                sys.EscreveLogConsole("ClienteInterface - ClassNotFoundException", "Erro : " + ex.getMessage());
                //Logger.getLogger(InterfaceAntiga.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo para conectar o cliente no servidor
     * @param message 
     */
    private void connected(ChatMessage message) {
        
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected", "***Inicio****");
        
        if (message.getText().equals("NO")) {
            
            sys.EscreveLogConsole("ClienteInterface - Metodo Connected",  "[" + this.message.getName()+ "]" + "Dentro do if");
            sys.EscreveLogConsole("ClienteInterface - Metodo Connected",  "[" + this.message.getName()+ "]" + "Conexão não realizada!\nTente novamente com um novo nome.");
            JOptionPane.showMessageDialog(this, "Conexão não realizada!\nTente novamente com um novo nome.");
            return;
            
        }
        if(message.getTpCrypt() == 2){
            //if (!message.isIsGenaratedKey()) {
                //chaveDES = geraChaveDES();
                //message.setIsGenaratedKey(true);
                sys.EscreveLogConsole("ClienteInterface - Metodo Connected", "[" + this.message.getName() + "]" + "Chave DES gerada : " + message.getChaveDES());
                sys.EscreveLogConsole("ClienteInterface - Metodo Connected", "[" + this.message.getName() + "]" + "isIsGenaratedKey : " + message.isIsGenaratedKey());
                //message.setChaveDES(chaveDES);
                
            //}

        }

        
        this.listOnlines.setEnabled(true);
        String a = new String().valueOf(message.getTpCrypt());
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected",  "[" + this.message.getName()+ "]" + "[TpCrypt] " + a);
        
        this.message = message;
        
        a = new String().valueOf(this.message.getTpCrypt());
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected",  "[" + this.message.getName()+ "]" + "[TpCrypt] " + a);
       
        this.btnConnectar.setEnabled(false);
        this.MenuOpcoes.setEnabled(false);
        
        
        this.btnSair.setEnabled(true);
        this.txtAreaSend.setEnabled(true);
        this.txtAreaReceive.setEnabled(true);
        this.btnEnviar.setEnabled(true);
        this.btnLimpar.setEnabled(true);

        this.StatusLabel.setText("Conectado");
        
        //Inseri essa linha e a descriptografia começou a funcionar normalmente.
        chaveDES = this.message.getChaveDES();
        //************************************************
        
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected", "[Chave DES] : " + chaveDES);
        
        JOptionPane.showMessageDialog(this, "Você está conectado no chat!");
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected",  "[" + this.message.getName()+ "]" + "Você está conectado no chat!");
        sys.EscreveLogConsole("ClienteInterface - Metodo Connected", "***FIM****");
        
        isConected = true;
    }

    /**
     * Metodo responsável para desconectar o cliente do servidor
     */
    private void disconnected() {
        
        byte[] varAuxDIS = null;
        String ChaveAES = null;
        isConected = false;
        //*********Metodo Desconectar
        //ChatMessage message = new ChatMessage();
        message.setName(this.message.getName());

        this.message.setAction(Action.DISCONNECT);

        
        if(this.message.getTpCrypt() == 1){
            
            String aux = this.message.getText();
            
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]", "***Inicio****");
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]",  "[" + this.message.getName()+ "]" + "Texto : " + aux);
            
            //AES
            varAuxDIS = this.message.getBytetxt();            
            try {
                sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]",  "[" + this.message.getName()+ "]" + "Texto Descriptografado : " + aes.decrypt(varAuxDIS, ChaveAES));
            } catch (Exception ex) {
                sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]","Erro AES : " + ex.getMessage());
                
            }
            
            //Rot47
            //sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[ROT47]",  "[" + this.message.getName()+ "]" + "Texto Descriptografado : " + rot.Decrypt(aux));
            
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]",  "[" + this.message.getName()+ "]" + "***FIM***");
            
        }else if(this.message.getTpCrypt() == 2){
            
            varAuxDIS = this.message.getBytetxt();
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[DES]", "***Inicio****");
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[DES]",  "[" + this.message.getName()+ "]" + "Texto : " + varAuxDIS);
            //sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[DES]",  "[" + this.message.getName()+ "]" + "Texto Descriptografado : " + classeDES.DESPaddingECBDecrypter(aux));
            sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[DES]",  "[" + this.message.getName()+ "]" + "***FIM***");
            
        }


        this.service.send(this.message);
        this.btnConnectar.setEnabled(true);
        //this.MenuDesconcetar.setEnabled(false);
        this.listOnlines.setEnabled(false);
        this.listOnlines.setListData(new Vector());
        
        this.txtAreaSend.setText(null);
        this.txtAreaReceive.setText(null);
        this.btnSair.setEnabled(false);
        this.MenuOpcoes.setEnabled(false);
        this.txtAreaSend.setEnabled(false);
        this.txtAreaReceive.setEnabled(false);
        this.btnEnviar.setEnabled(false);
        this.btnLimpar.setEnabled(false);
        this.setTitle("Você está Offline ");
        this.StatusLabel.setText("Desconectado");

    }

    /**
     * Metodo responsavel pelo recebimento das mensagens
     * @param message 
     */
    private void receive(ChatMessage message) {
        sys.EscreveLogConsole("ClienteInterface - Metodo receive", "****INICIO***");

        String a =null;
        //message.setTpCrypt(2);
        a = new String().valueOf(message.getTpCrypt());
        
        sys.EscreveLogConsole("ClienteInterface - Metodo receive",  "[" + this.message.getName()+ "]" + "[TpCrypt] " + a);
        
        
        if (message.getTpCrypt() == 0) {

            //Sem Criptografia        
            this.txtAreaReceive.append(message.getName() + " diz : " + message.getText() + "\n");
            sys.EscreveLogConsole("ClienteInterface - Metodo receive[noCrypt]", "[" + message.getName()+ "]" + message.getText());
        } else if (message.getTpCrypt() == 1) {

            //Com Criptografia Rot47
            String txtcripto = message.getText();
            sys.EscreveLogConsole("ClienteInterface - Metodo receive[Rot47]", "[" + message.getName()+ "]" + "txtcripto : " + txtcripto);
            //String claro = rot.Decrypt(txtcripto);
            //this.txtAreaReceive.append(message.getName() + " diz : " + claro + "\n");

        } else if (message.getTpCrypt() == 2) {

            //Criptografia DES
            //byte[] encriptado = null;
            String resultado = "";
            //encriptado = classeDES.DESPaddingECB(message.getText());       
            resultado = classeDES.DESPaddingECBDecrypter(message.getBytetxt(),chaveDES);
            this.txtAreaReceive.append(message.getName() + " diz : " + resultado + "\n");
            sys.EscreveLogConsole("ClienteInterface - Metodo receive[DES]", "Resultado : " + resultado);
            sys.EscreveLogConsole("ClienteInterface - Metodo receive[DES]", "Resultado[toString] : " + resultado.toString());
//            sys.EscreveLogConsole("ClienteInterface - Metodo receive[DES]", "Teste[DES] : " +classeDES.DESPaddingECBDecrypter(message.getBytetxt()));
        }

        sys.EscreveLogConsole("ClienteInterface - Metodo receive", "****FIM***");
    }

    /**
     * Metodo responsavel para a atualizar a lista de usuarios onlines
     * @param message 
     */
    private void refreshOnlines(ChatMessage message) {
        sys.EscreveLogConsole("ClienteInterface - Metodo refreshOnlines", "***INICIO***");
        sys.EscreveLogConsole("ClienteInterface - Metodo refreshOnlines", "Usuario : " + message.getName());
        sys.EscreveLogConsole("ClienteInterface - Metodo refreshOnlines", "Usuarios : " + message.getSetOnlines().toString());

        Set<String> names = message.getSetOnlines();

        String[] array = (String[]) names.toArray(new String[names.size()]);

        this.listOnlines.setListData(array);
        this.listOnlines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOnlines.setLayoutOrientation(JList.VERTICAL);
        sys.EscreveLogConsole("ClienteInterface - Metodo refreshOnlines", "***FIM***");
    }

    /**
     * Metodo para criptografar a mensagem do usuário
     * @param i tipo de criptografia selecionado pelo usuário
     * @param txt texto a ser criptografado
     * @return 
     
    private String criptografarROT47(int i, String txt) {
        
        //Variável para armazenar o texto criptografado
        String txtcriptografado = "";
        sys.EscreveLogConsole("ClienteInterface - Metodo criptografarROT47"," Texto : " + txt);
        if (i == 1) {
            //Rot47
            sys.EscreveLogConsole("ClienteInterface - Metodo criptografarROT47[ROT47]", "[" + this.message.getName() + "]" + "Texto Claro : " + txt);
            String criptografado = rot.Encrypt(txt);
            sys.EscreveLogConsole("ClienteInterface - Metodo criptografarROT47[ROT47]", "Texto Criptografado : " + rot.Encrypt(txt));
            sys.EscreveLogConsole("ClienteInterface - Metodo criptografarROT47[ROT47]", "[" + this.message.getName() + "]" + "Texto Descriptografado : " + rot.Decrypt(criptografado));
            txtcriptografado = criptografado;
            /*
            Variavel de teste para verificar qual o texto criptografado usando ROT47
            /
            String teste = "";
           
            sys.EscreveLogConsole("ClienteInterface - Metodo criptografarROT47[ROT47]", "[" + this.message.getName() + "]" + "Teste : " + txtcriptografado);

        } else if (i == 0) {
            //Sem criptografia o texto é armaezando na variável que será retornado
            txtcriptografado = txt;
        }

        return txtcriptografado;
    }
*/
    
    private byte[] criptografarDES(int i, String txt) {
 
        sys.EscreveLogConsole("ClienteInterface - Metodo criptografar", " Texto : " + txt);

        //DES
        sys.EscreveLogConsole("ClienteInterface - Metodo criptografar[DES]", "[" + this.message.getName() + "]" + "Texto Claro : " + txt);

        sys.EscreveLogConsole("ClienteInterface - Metodo criptografar[DES]", "[" + this.message.getName() + "]" + "ChavesDES : " + chaveDES);
        
        //Momento que ocorre a encriptação do texto com base na chave DES gerada.            
        byte[] aux = classeDES.DESPaddingECB(txt, chaveDES);

        sys.EscreveLogConsole("ClienteInterface - Metodo criptografar[DES]", " aux : " + aux);       

        return aux;
    }
    
    private byte[] criptografiaAES(String txt){
        byte[] auxAES = null;
        try {
            auxAES = aes.encrypt(txt, chaveAES);
        } catch (Exception ex) {
             sys.EscreveLogConsole("ClienteInterface - Metodo Disconnected[AES]","Erro AES : " + ex.getMessage());
        }
        
        return auxAES;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReceive = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaSend = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        IPLabel = new javax.swing.JLabel();
        IP_Status = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Status_Crypto = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        RelogioStatus = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOnlines = new javax.swing.JList();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnConnectar = new javax.swing.JMenuItem();
        btnSair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuOpcoes = new javax.swing.JMenuItem();
        BotaoSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtAreaReceive.setEditable(false);
        txtAreaReceive.setColumns(20);
        txtAreaReceive.setRows(5);
        txtAreaReceive.setEnabled(false);
        jScrollPane1.setViewportView(txtAreaReceive);

        txtAreaSend.setEditable(false);
        txtAreaSend.setColumns(20);
        txtAreaSend.setRows(5);
        txtAreaSend.setEnabled(false);
        jScrollPane3.setViewportView(txtAreaSend);

        btnEnviar.setText("Enviar");
        btnEnviar.setEnabled(false);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Cancelar");
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        jToolBar1.setFloatable(false);

        jLabel1.setText("Seu ip : ");
        jToolBar1.add(jLabel1);

        IPLabel.setText("0.0.0.0                    ");
        jToolBar1.add(IPLabel);
        jToolBar1.add(IP_Status);

        jLabel4.setText("    ");
        jToolBar1.add(jLabel4);

        StatusLabel.setText(" Desconectado");
        jToolBar1.add(StatusLabel);

        jLabel5.setText("      ");
        jToolBar1.add(jLabel5);
        jToolBar1.add(Status_Crypto);

        jLabel2.setText("                                                         ");
        jToolBar1.add(jLabel2);
        jToolBar1.add(jSeparator4);

        RelogioStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        RelogioStatus.setText("00:00:00");
        jToolBar1.add(RelogioStatus);

        listOnlines.setToolTipText("");
        listOnlines.setEnabled(false);
        jScrollPane2.setViewportView(listOnlines);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(11, 11, 11)
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addGap(6, 6, 6)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Menu");

        btnConnectar.setText("Conectar");
        btnConnectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectarActionPerformed(evt);
            }
        });
        jMenu1.add(btnConnectar);

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jMenu1.add(btnSair);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opcoes");

        MenuOpcoes.setText("Opcoes");
        MenuOpcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuOpcoesActionPerformed(evt);
            }
        });
        jMenu2.add(MenuOpcoes);

        BotaoSobre.setText("Sobre");
        BotaoSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSobreActionPerformed(evt);
            }
        });
        jMenu2.add(BotaoSobre);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        
        /*
        Primeiro if valida se foi digitado alguma coisa antes de enviar para os outros clientes.
        */
        if (txtAreaSend.getText().isEmpty() || txtAreaSend.getText().equals(null)) {
            JOptionPane.showMessageDialog(null, "Não é possivel enviar mensagens em branco",
                    "Não é possivel enviar mensagens em branco", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String text = this.txtAreaSend.getText();
            String name = this.message.getName();
            String textOld = "";
            textOld = text;
            
            /*
            É criado um novo objeto do tipo ChatMessage para armazenar a mensagem a ser enviada
            o setCrypt é armazenado qual o tipo de criptografia escolido
            */
            this.message = new ChatMessage();
            this.message.setTpCrypt(tpCrypt);
            this.message.setName(name);
            
            
            /*
            Ponto de Log para exibir no console o tipo de criptografia
            */
            sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[TpCrypt]","tpCrypt : " + tpCrypt);
            sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[TpCrypt]","this.tpCrypt : " + this.message.getTpCrypt());
            
            
            //Ponto de log para exibir no console o valor da variavel isConnected.            
            sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar","[" + this.message.getName()+ "]" + "isConnected : " + isConected); 
           
            
            /*
            Validação do tipo de criptografado 
            */
            if (this.message.getTpCrypt() == 0) {
                
                //Sem Criptografia                
                 this.message.setText(textOld);
                 sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[NoCrypt]","[" + this.message.getName()+ "]" + "textOld : " + textOld);
                 sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[NoCrypt]","[" + this.message.getName()+ "]" + "txtMSG : " + this.message.getText());
                 
            } else if (this.message.getTpCrypt() == 1) {
                
                //Criptografia ROT47
                  //String criptografado = criptografarROT47(this.message.getTpCrypt(),text);                 
                  //this.message.setText(criptografado);
                  sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[ROT47]","[" + this.message.getName()+ "]" + "textOld : " + textOld);
                  sys.EscreveLogConsole("ClienteInterface - Metodo btnEnviar[ROT47]","[" + this.message.getName()+ "]" + "txtMSG : " + this.message.getText());
                  
            } else if (this.message.getTpCrypt() == 2) {

                //Criptografia DES
                //String aux = criptografarROT47(this.message.getTpCrypt(),text);
                //byte[] txtbyte = aux.getBytes();                    
                txtbyte = criptografarDES(this.message.getTpCrypt(), text);
                this.message.setBytetxt(txtbyte);
                this.message.setText("");

            }         
         
            /*            
                Validação se foi selecionado apenas um cliente para envio de mensagem
            */
            if (this.listOnlines.getSelectedIndex() > -1) {
                this.message.setNameReserved((String) this.listOnlines.getSelectedValue());
                this.message.setAction(Action.SEND_ONE);
                this.listOnlines.clearSelection();
            } else {
                this.message.setAction(Action.SEND_ALL);
            }
            
            
            this.txtAreaReceive.append("Voce disse : " + textOld + "\n");
            System.out.println("|*******************************************|");
            sys.EscreveLogConsole("[btnEnviarActionPerformed]","ChaveDES : " + chaveDES);
            sys.EscreveLogConsole("[btnEnviarActionPerformed]","[message]ChaveDES : " + this.message.getChaveDES());
            sys.EscreveLogConsole("[btnEnviarActionPerformed]","TextoClaro : " + textOld);
            sys.EscreveLogConsole("[btnEnviarActionPerformed]","Textocriptofrado : " + txtbyte);
            sys.EscreveLogConsole("[btnEnviarActionPerformed]","TextoDescriptografado : " +  classeDES.DESPaddingECBDecrypter(txtbyte, chaveDES));
            System.out.println("|*******************************************|");
            this.service.send(this.message);
            this.txtAreaSend.setText(null);
            
        }

    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        if (isConected == true) {
            if (JOptionPane.showConfirmDialog(null, "Voce ainda esta conectado ao Servidor : " + servidor + "\n"
                    + "Voce realmente gostaria de se desconectar e sair?", "Pergunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                disconnected();

            } else {
                return;
            }
        } else {

        }


    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        txtAreaSend.setText(null);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void BotaoSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSobreActionPerformed
        JOptionPane.showMessageDialog(null, "    Desenvolvido por : \n Breno Lopes de Lima - 31312514\n" +
" Vinicius Dutra Cerqueira Rocha - 315112918\n" +
" Kepler Nicolai Alves Freitas  - 31222198"
                + "\nVersao 5.3" + "\nDezembro 2015 ", "Sobre a aplicacao", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_BotaoSobreActionPerformed

    private void MenuOpcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuOpcoesActionPerformed
        if(frameOpecoes == null){
            frameOpecoes = new Opcoes();
            frameOpecoes.setVisible(true);
        }else{
            frameOpecoes.setVisible(true);
            frameOpecoes.setState(frameOpecoes.NORMAL);
        }
        tpCrypt = frameOpecoes.RetornaTPCrypt();
        
        sys.EscreveLogConsole("MenuOpcoesActionPerformed", "Valor tpCrypt : " + tpCrypt);
    }//GEN-LAST:event_MenuOpcoesActionPerformed

    private void btnConnectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectarActionPerformed

            servidor = JOptionPane.showInputDialog("Informe o Ip do servidor : ");
            nome = JOptionPane.showInputDialog("Digite seu nome : ");
            boolean aux = validaDados(servidor, nome);
            if (aux) {
                
                this.message = new ChatMessage();
                
                System.out.println("[antes do if]TpCrypt : " + tpCrypt);
                //habilitar criptografia :
                if (tpCrypt == 0) {
                    if(JOptionPane.showConfirmDialog(null, "Voce nao informou qual tipo de criptografia irá utilizar. Deseja utilizar DES?\n"
                            + "Caso não aceite a sugestão será utilizado será ROT47 como padrão.", "Seleçao de Criptografia",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        tpCrypt = 2;
                        this.message.setTpCrypt(tpCrypt);
                    }else{
                        tpCrypt = 1;
                        this.message.setTpCrypt(tpCrypt);
                    }
                }
                
                txtAreaSend.setEnabled(true);
                
                
                this.message.setAction(Action.CONNECT);
                this.message.setName(nome);
                this.message.setTpCrypt(tpCrypt);
                System.out.println("[depois do If]TpCrypt : " + tpCrypt);
                System.out.println("[this.message.getTpCrypt()]TpCrypt : " + this.message.getTpCrypt());
                this.service = new ClienteService();
                this.socketTeste = this.service.connect(servidor);
                
                new Thread(new ListenerSocket(this.socketTeste)).start();
                this.service.send(message);
                
                int StCrypt = 0;
                
                
                isConected = true;
                
                //Altera o titulo da janela exibindo o nome do usuário e o ip/endereco do servidor e status da cryptografia
                validaCrypt = (this.message.getTpCrypt() == 0) ? "Desativada" : "Ativada";
                validaCrypt = (this.message.getTpCrypt() == 1) ? "[AES]Ativada" : "No[AES]";
                validaCrypt = (this.message.getTpCrypt() == 2) ? "[DES]" : "No[DES]";
                String titulo = nome + " conectado no Servidor : " + servidor + " Criptografia : " + validaCrypt;
                this.setTitle(titulo);
                
                
                btnConnectar.setEnabled(false);
                txtAreaSend.setEditable(true);
                
            } else {
                isConected = false;
            }
            AutoLog();
            
        

    }//GEN-LAST:event_btnConnectarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
            Thread tr = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        Date data = new Date();
                        String hora = new SimpleDateFormat("kk:mm:ss").format(data);
                        RelogioStatus.setText(String.valueOf(hora));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            
                        }
                    }
                }
            });
            tr.start();
            IPLabel.setText(say());
          
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        sys.EscreveLogConsole("ClienteInterface - Metodo formWindowClosed",  "INICIO");
        if (isConected == true) {
            /*
            Validação caso o usuário esteja conectado ao servidor, e ´questionado ao usuário se realmente ele quer sair do chat
            */
            if (JOptionPane.showConfirmDialog(null, "Voce ainda esta conectado ao Servidor : " + servidor + "\n"
                    + "Voce realmente gostaria de se desconectar e sair?", "Pergunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                disconnected();
                sys.EscreveLogConsole("ClienteInterface - Metodo formWindowClosed",  "[Desconectando]FIM disconnected");
                System.exit(0);
            } else {
                return;
            }
        } else {
            sys.EscreveLogConsole("ClienteInterface - Metodo formWindowClosed",  "FIM ELSE");
            System.exit(0);
        }

        
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem BotaoSobre;
    private javax.swing.JLabel IPLabel;
    private javax.swing.JToolBar.Separator IP_Status;
    private javax.swing.JMenuItem MenuOpcoes;
    private javax.swing.JLabel RelogioStatus;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JToolBar.Separator Status_Crypto;
    private javax.swing.JMenuItem btnConnectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JMenuItem btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList listOnlines;
    private javax.swing.JTextArea txtAreaReceive;
    private javax.swing.JTextArea txtAreaSend;
    // End of variables declaration//GEN-END:variables

    /**
     * Metodo para realizar a validação dos dados inseridos pelo usuário
     * @param server ip do servidor
     * @param name nome do usuário a ser conectado
     * @return retorna verdade se somente se se os dados inseridos pelo usuário estão válidos
     */
    
    private boolean validaDados(String server, String name) {
        boolean aux = false;
        if (server == null || server.isEmpty() || server.equals("")) {

            if (JOptionPane.showConfirmDialog(null, "Voce nao digitou nenhum ip de servidor\n"
                    + "Voce gostaria de informar o ip do servidor?", "Ip do servidor",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                servidor = JOptionPane.showInputDialog("Informe o Ip do servidor : ");
                aux = true;
            } else {
                JOptionPane.showMessageDialog(null, "Voce nao informou o servidor,seu status atual eh:Desconcetado", "Mensagem",
                        JOptionPane.INFORMATION_MESSAGE);
                StatusLabel.setText("OffLine");
                //isConected = false;
                txtAreaSend.setEditable(false);
                txtAreaReceive.setEnabled(false);
                btnEnviar.setEnabled(false);
                btnLimpar.setEnabled(false);
                aux = false;
            }

        } else {
            aux = true;
        }

        if (name == null || name.isEmpty() || name.equals("")) {

            if (JOptionPane.showConfirmDialog(null, "Voce nao digitou um nome/apelido \n"
                    + "Sem essa informação não podemos continuar.", "Apelido do Usuário",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                nome = JOptionPane.showInputDialog("digite seu nome ou apelido : ");
                aux = true;
            } else {
                aux = false;
            }
        }

        
        return aux;
    }

    /**
     * Metodo para exibir na tela o ip do computador
     * @return um string contendo o ip do computador
     */
    public String say() {
        String ip = "0.0.0.0  ";
        try {
            java.net.InetAddress i = java.net.InetAddress.getLocalHost();
            ip = i.getHostAddress();
           // System.out.println(ip); // IP address only  

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    
    /**
     * Metodo responsavel para verificar qual o tipo de criptografia selecionado
     * @param vlrTpCrypt - qual o tipo de criptografia selecionado 
     */
    public void setTpCrypt(int vlrTpCrypt) {
        this.tpCrypt = vlrTpCrypt;
        sys.EscreveLogConsole("ClienteInterface - Metodo setTpCrypt","Valor de vlrTpCrypt : " + vlrTpCrypt);
        sys.EscreveLogConsole("ClienteInterface - Metodo setTpCrypt","Valor de this.vlrTpCrypt : " + this.tpCrypt);

    }
    
    /**
     * Metodo responsavel pela geracao da chave de criptografia
     * @return retorna a chave gerada
     */
    
    /*
    public SecretKey geraChaveDES() {
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            chaveDES = keygenerator.generateKey();
            this.message.setChaveDES(chaveDES);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chaveDES;
    }*/
    
    
    public void AutoLog(){
    
        try {
            autolog = new File("LogsCripto\\" + Data + "_autoLog_" + nome + ".logSc");
            sys.CriaLog(autolog);
            System.setErr(new PrintStream(autolog));
            System.setOut(new PrintStream(autolog));            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
