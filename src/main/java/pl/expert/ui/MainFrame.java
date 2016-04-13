/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.reader.KnowledgeReader;
import pl.expert.core.exception.KnowledgeReaderException;

/**
 *
 * @author Mariusz Batyra
 */
public class MainFrame extends javax.swing.JFrame {

    private Knowledge knowledge = null;
    
    public MainFrame() {
        initComponents();
        setResizable(false);
    }
    
    private void loadKnowledge(File selectedFile) {
        KnowledgeReader knowledgeReader = new KnowledgeReader();
        try {
            knowledge = knowledgeReader.loadKnowledge(selectedFile);
            JOptionPane.showMessageDialog(this, "Plik załadowany poprawnie", "Odczytu pliku", JOptionPane.INFORMATION_MESSAGE);
        } catch (KnowledgeReaderException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Odczytu pliku", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadFileMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Plik");

        loadFileMenuItem.setText("Wczytaj plik");
        loadFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFileMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(loadFileMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pomoc");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFileMenuItemActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int userResponse = fileChooser.showDialog(this, null);
        if(userResponse == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadKnowledge(selectedFile);
        }
    }//GEN-LAST:event_loadFileMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem loadFileMenuItem;
    // End of variables declaration//GEN-END:variables
}
