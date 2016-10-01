/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengineproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Timothy
 */
public class SearchEngineGUI extends javax.swing.JFrame {
    SearchEngineProject sep = new SearchEngineProject();
    
    
    /**
     * Creates new form SearchEngineGUI
     */
    public SearchEngineGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        mainLayout = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        stemButton = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        docTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        vocabMenu = new javax.swing.JMenuItem();

        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        mainLayout.setPreferredSize(new java.awt.Dimension(550, 300));
        mainLayout.setLayout(null);

        searchBar.setToolTipText("");
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        mainLayout.add(searchBar);
        searchBar.setBounds(10, 10, 350, 30);

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        mainLayout.add(searchButton);
        searchButton.setBounds(460, 10, 80, 30);

        stemButton.setText("Stem");
        stemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stemButtonActionPerformed(evt);
            }
        });
        mainLayout.add(stemButton);
        stemButton.setBounds(370, 10, 80, 30);

        docTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documents"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        docTable.getSelectionModel().addListSelectionListener(new
            ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int row = docTable.getSelectedRow();

                    if(row >= 0 && !e.getValueIsAdjusting() && !docTable.getSelectionModel()
                        .isSelectionEmpty()) {
                        String file = docTable.getValueAt(row, 0).toString();

                        if(file.endsWith(".json")) {
                            openFile(file);
                        }
                    }
                }
            });
            tableScrollPane.setViewportView(docTable);
            if (docTable.getColumnModel().getColumnCount() > 0) {
                docTable.getColumnModel().getColumn(0).setResizable(false);
            }

            mainLayout.add(tableScrollPane);
            tableScrollPane.setBounds(10, 50, 530, 220);

            getContentPane().add(mainLayout);

            fileMenu.setText("File");

            openMenu.setText("Open");
            openMenu.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    openMenuActionPerformed(evt);
                }
            });
            fileMenu.add(openMenu);

            exitMenu.setText("Exit");
            exitMenu.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    exitMenuActionPerformed(evt);
                }
            });
            fileMenu.add(exitMenu);

            jMenuBar1.add(fileMenu);

            viewMenu.setText("View");

            vocabMenu.setText("Vocabulary");
            vocabMenu.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    vocabMenuActionPerformed(evt);
                }
            });
            viewMenu.add(vocabMenu);

            jMenuBar1.add(viewMenu);

            setJMenuBar(jMenuBar1);

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = fileChooser.getSelectedFile();
            Path path = Paths.get(dir.getAbsolutePath());
            sep = new SearchEngineProject();
            System.out.println("Indexing " + dir.getName());
            
            try {
                sep.indexDirectory(path);
                JOptionPane.showMessageDialog(this, "Successfully indexed "
                        + sep.getFileNames().size() + " files.", "Indexed",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(SearchEngineGUI.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }   
    }//GEN-LAST:event_openMenuActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        searchButtonActionPerformed(evt);
    }//GEN-LAST:event_searchBarActionPerformed

    private void vocabMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vocabMenuActionPerformed
        String[] terms = sep.getPositionalInvertedIndex().getTerms();
        int termCount = sep.getPositionalInvertedIndex().getTermCount();
        String columnNames[] = new String[] {"Terms"};
        DefaultTableModel model = (DefaultTableModel) docTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);
        
        for(String term : terms) {
            model.addRow(new Object[]{term});
        }
        
        model.addRow(new Object[]{termCount + " terms in the index."});
    }//GEN-LAST:event_vocabMenuActionPerformed

    private void stemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stemButtonActionPerformed
        String token = searchBar.getText();
        String columnNames[] = new String[] {"Stemmed Term"};
        DefaultTableModel model = (DefaultTableModel) docTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);
        
        model.addRow(new Object[]{PorterStemmer.processToken(token)});        
    }//GEN-LAST:event_stemButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String query = searchBar.getText();
        String columnNames[] = new String[] {"Documents"};
        DefaultTableModel model = (DefaultTableModel) docTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);
        
        ArrayList<String> fileNames = sep.searchResults(query);
        
        for(String file : fileNames) {
            model.addRow(new Object[]{file});
        }
        
        model.addRow(new Object[]{fileNames.size() + " documents found."});
    }//GEN-LAST:event_searchButtonActionPerformed

    private void openFile(String file) {
        JFrame frame = new JFrame(file);
        frame.setMinimumSize(new Dimension(800, 600));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        JLabel label = new JLabel(); 
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try {
            NPSDocument json = gson.fromJson(new FileReader(fileChooser
                    .getSelectedFile() + "\\" + file), NPSDocument.class);
            label.setText("<html><div WIDTH=600px>" + json.title + "<br>"
                    + json.body + "<br>" + json.url + "</div><html>");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchEngineGUI.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        label.setHorizontalAlignment(JLabel.LEFT);
        label.setVerticalAlignment(JLabel.NORTH);
        panel.add(label);
        JScrollPane scrollBar = new JScrollPane(panel);
        frame.add(scrollBar);
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchEngineGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchEngineGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchEngineGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchEngineGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchEngineGUI().setVisible(true);
            }
        });
    }

    class NPSDocument {
	String title;
	String body;
	String url;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable docTable;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel mainLayout;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton stemButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JMenuItem vocabMenu;
    // End of variables declaration//GEN-END:variables
}
