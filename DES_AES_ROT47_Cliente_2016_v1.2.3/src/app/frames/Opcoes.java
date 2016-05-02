package app.frames;

import javax.swing.JFrame;

/**
 * @author Breno Lopes de Lima - 31312514
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 * @author Kepler Nicolai Alves Freitas  - 31222198
 * @version 5.0
 * 
 */

public class Opcoes extends javax.swing.JFrame {

    
    int aux = 0;
    ClientInterface uiprincipal = new ClientInterface();
    
    
    public Opcoes() {
        
        initComponents();
    }
    
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GroupTipoCrypt = new javax.swing.ButtonGroup();
        ChaveDESGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        BotaoSalvar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        PainelTabulado = new javax.swing.JTabbedPane();
        DESPanel = new javax.swing.JPanel();
        FieldChaveDESUser = new javax.swing.JTextField();
        FieldChaveGeradaDESserver = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        AESPanel = new javax.swing.JPanel();
        FieldChaveDESUser1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        FieldChaveGeradaDESserver1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BtnAES = new javax.swing.JRadioButton();
        BtnDES = new javax.swing.JRadioButton();
        BtnSemCrypt = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        BotaoSalvar.setText("Salvar");
        BotaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        FieldChaveDESUser.setEditable(false);

        FieldChaveGeradaDESserver.setEditable(false);

        jLabel2.setText("Informe a Chave DES  :");

        jLabel3.setText("Chave DES gerada pelo servidor :");

        jRadioButton1.setText("CBC");

        jRadioButton2.setText("ECB");

        javax.swing.GroupLayout DESPanelLayout = new javax.swing.GroupLayout(DESPanel);
        DESPanel.setLayout(DESPanelLayout);
        DESPanelLayout.setHorizontalGroup(
            DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DESPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DESPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FieldChaveDESUser, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DESPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(FieldChaveGeradaDESserver, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DESPanelLayout.createSequentialGroup()
                        .addGroup(DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DESPanelLayout.setVerticalGroup(
            DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DESPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addGroup(DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldChaveDESUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldChaveGeradaDESserver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        PainelTabulado.addTab("DES", DESPanel);

        FieldChaveDESUser1.setEditable(false);

        jLabel6.setText("Informe a Chave AES  :");

        FieldChaveGeradaDESserver1.setEditable(false);

        jLabel7.setText("Chave AES gerada pelo servidor :");

        javax.swing.GroupLayout AESPanelLayout = new javax.swing.GroupLayout(AESPanel);
        AESPanel.setLayout(AESPanelLayout);
        AESPanelLayout.setHorizontalGroup(
            AESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AESPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AESPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FieldChaveDESUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AESPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(FieldChaveGeradaDESserver1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AESPanelLayout.setVerticalGroup(
            AESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AESPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(AESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldChaveDESUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AESPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldChaveGeradaDESserver1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        PainelTabulado.addTab("AES", AESPanel);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Seleção de Criptografia");

        GroupTipoCrypt.add(BtnAES);
        BtnAES.setText("AES");
        BtnAES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAESActionPerformed(evt);
            }
        });

        GroupTipoCrypt.add(BtnDES);
        BtnDES.setText(" DES");
        BtnDES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDESActionPerformed(evt);
            }
        });

        GroupTipoCrypt.add(BtnSemCrypt);
        BtnSemCrypt.setText("Sem Criptografia");
        BtnSemCrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemCryptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(95, 95, 95))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(BtnAES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnDES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnSemCrypt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnAES)
                    .addComponent(BtnDES)
                    .addComponent(BtnSemCrypt))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelTabulado, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BotaoSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(PainelTabulado, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoSalvar)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarActionPerformed
       //if(uiprincipal != null){
           uiprincipal.setTpCrypt(RetornaTPCrypt());
           this.dispose();
      // }
           
            //this.dispose();
        
    }//GEN-LAST:event_BotaoSalvarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       PainelTabulado.setVisible(false);
        if (aux == 0) {
            BtnAES.setSelected(false);
            BtnDES.setSelected(false);
        } else if (aux == 1) {
            BtnAES.setSelected(true);
            BtnDES.setSelected(false);
        } else if (aux == 2) {
            BtnAES.setSelected(false);
            BtnDES.setSelected(true);
        }
        
            DESPanel.setEnabled(false);
            AESPanel.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void BtnDESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDESActionPerformed
        if (BtnDES.isSelected()) {
            PainelTabulado.setVisible(true);
            
            BtnAES.setSelected(false);
            BtnSemCrypt.setSelected(false);
            
            DESPanel.setEnabled(true);
            AESPanel.setEnabled(false);
        }
    }//GEN-LAST:event_BtnDESActionPerformed

    private void BtnAESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAESActionPerformed
        if(BtnAES.isSelected()){
            PainelTabulado.setVisible(true);
            
            BtnDES.setSelected(false);
            BtnSemCrypt.setSelected(false);
            
            DESPanel.setEnabled(false);
            AESPanel.setEnabled(true);
        }
    }//GEN-LAST:event_BtnAESActionPerformed

    private void BtnSemCryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemCryptActionPerformed
        if (BtnSemCrypt.isSelected()) {
            PainelTabulado.setVisible(false);

            GroupTipoCrypt.clearSelection();
            //BtnDES.setSelected(false);
            //BtnAES.setSelected(false);
            BtnSemCrypt.setSelected(true);

            DESPanel.setEnabled(false);
            AESPanel.setEnabled(false);
        }
    }//GEN-LAST:event_BtnSemCryptActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AESPanel;
    public javax.swing.JButton BotaoSalvar;
    private javax.swing.JRadioButton BtnAES;
    private javax.swing.JRadioButton BtnDES;
    private javax.swing.JRadioButton BtnSemCrypt;
    private javax.swing.ButtonGroup ChaveDESGroup;
    private javax.swing.JPanel DESPanel;
    public javax.swing.JTextField FieldChaveDESUser;
    public javax.swing.JTextField FieldChaveDESUser1;
    public javax.swing.JTextField FieldChaveGeradaDESserver;
    public javax.swing.JTextField FieldChaveGeradaDESserver1;
    private javax.swing.ButtonGroup GroupTipoCrypt;
    private javax.swing.JTabbedPane PainelTabulado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    // End of variables declaration//GEN-END:variables

    public int RetornaTPCrypt() {
        int tpcrypt = 0;
        if (BtnAES.isSelected()) {
            tpcrypt =  1;
        } else if(BtnDES.isSelected()){
            tpcrypt =  2;
        }
        return tpcrypt;
    }
}
