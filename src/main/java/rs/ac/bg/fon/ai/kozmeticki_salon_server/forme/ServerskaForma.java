/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.forme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.controller.Controller;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.modeli.ModelTabeleMenadzer;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.server.Server;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;

/**
 * Klasa koja predstavlja glavnu formu za server aplikaciju na kojoj su
 * prikazani svi prijavljeni menadzeri. Ova klasa nasleđuje JFrame i omogućava
 * korisnicima da pokrenu i zaustave server, kao i da konfigurišu parametre
 * konekcije sa bazom podataka i portom.
 *
 *
 * @author Nikolina Baros
 */
public class ServerskaForma extends javax.swing.JFrame {

    /**
     * Instanca Server objekta koji predstavlja server aplikacije.
     */
    Server server;

    /**
     * Kreira novu instancu klase ServerskaForma i novu instancu klase Server.
     * Inicijalizuje sve potrebne komponente i postavlja model tabele za prikaz
     * svih menadzera. Takodje podesava i vrednost serverske forme u kontroleru
     * na novu formu.
     *
     * @throws Exception ako dođe do greške pri inicijalizaciji.
     */
    public ServerskaForma() throws Exception {
        initComponents();
        jTable1.setModel(new ModelTabeleMenadzer(new ArrayList<>()));
        server = new Server();

        Controller.getInstance().setSf(this);
        jButtonZAUSTAVI.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jButtonPOKRENI = new javax.swing.JButton();
        jButtonZAUSTAVI = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setLocation(new java.awt.Point(500, 150));

        jButtonPOKRENI.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonPOKRENI.setText("POKRENI SERVER");
        jButtonPOKRENI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPOKRENIActionPerformed(evt);
            }
        });

        jButtonZAUSTAVI.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonZAUSTAVI.setText("ZAUSTAVI SERVER");
        jButtonZAUSTAVI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZAUSTAVIActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Status:");

        jLabelStatus.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelStatus.setText(" ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ulogovani menadzeri", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 12))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jMenuBar1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jMenu1.setText("Konfiguracija");
        jMenu1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem1.setText("baza");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem2.setText("port");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonPOKRENI)
                                .addGap(62, 62, 62)
                                .addComponent(jButtonZAUSTAVI))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelStatus))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPOKRENI)
                    .addComponent(jButtonZAUSTAVI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * Metoda koja se poziva kada se izabere opcija za konfiguraciju baze
     * podataka iz menija. Otvara prozor za konfiguraciju baze podataka.
     *
     * @param evt Događaj koji se javlja prilikom izbora opcije iz menija.
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        FormaKonfiguracijaBaza fkb = new FormaKonfiguracijaBaza(this, false);
        fkb.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed
    /**
     * Metoda koja se poziva kada se izabere opcija za konfiguraciju porta iz
     * menija. Otvara prozor za konfiguraciju porta.
     *
     * @param evt Događaj koji se javlja prilikom izbora opcije iz menija.
     */
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        FormaKonfiguracijaPort fkp = new FormaKonfiguracijaPort(this, false);
        fkp.setVisible(true);

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * Metoda koja se poziva kada se klikne na dugme "Pokreni server". Pokreće
     * server ako već nije pokrenut i ažurira status servera na formi.
     *
     * @param evt Događaj koji se javlja prilikom klika na dugme "Pokreni
     * server".
     */
    private void jButtonPOKRENIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPOKRENIActionPerformed

        if (server == null || !server.isAlive()) {
            server = new Server();
            server.start();

            jLabelStatus.setText("SERVER JE POKRENUT");
            jLabelStatus.setForeground(Color.decode("177245"));
            jButtonPOKRENI.setEnabled(false);
            jButtonZAUSTAVI.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonPOKRENIActionPerformed

    /**
     * Metoda koja se poziva kada se klikne na dugme "Zaustavi server".
     * Zaustavlja server i ažurira status servera na formi.
     *
     * @param evt Događaj koji se javlja prilikom klika na dugme "Zaustavi
     * server".
     */
    private void jButtonZAUSTAVIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZAUSTAVIActionPerformed

        server.zaustaviServer();
        server = null;

        jLabelStatus.setText("SERVER JE ZAUSTAVLJEN");
        jLabelStatus.setForeground(Color.red);
        jButtonPOKRENI.setEnabled(true);
        jButtonZAUSTAVI.setEnabled(false);
    }//GEN-LAST:event_jButtonZAUSTAVIActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPOKRENI;
    private javax.swing.JButton jButtonZAUSTAVI;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void ucitajUlogovaneMenadzere(List<Menadzer> prijavljeni) {

        ModelTabeleMenadzer mtm = (ModelTabeleMenadzer) jTable1.getModel();
        mtm.setLista(prijavljeni);
        mtm.fireTableDataChanged();

    }
}
