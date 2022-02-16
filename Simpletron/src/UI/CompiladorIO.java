/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Controlador.Compilador;
import Controlador.Controlador;
import Principal.Analisador;
import Principal.AnalisadorLexicoSintatico;
import Principal.ItemTabelaEntrada;
import Principal.Memoria;
import Principal.TabelaEntrada;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author luciano_simeao
 */
public class CompiladorIO extends javax.swing.JFrame {
    
    public void addTextInputTextArea(String codigo) {
        inputAreaTexto.setText(codigo);
    }

    /**
     * Creates new form compiladorIO
     */
    public CompiladorIO(String code) {
        initComponents();
        addTextInputTextArea(code);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputAreaTexto = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        outputAreaTexto = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        botaoCompilar = new javax.swing.JButton();
        botaoLimpar = new javax.swing.JButton();
        otimizacaoCheckBox = new javax.swing.JCheckBox();
        lexicosintaticoCheckBox = new javax.swing.JCheckBox();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador Simpletron");

        inputAreaTexto.setColumns(20);
        inputAreaTexto.setRows(5);
        jScrollPane2.setViewportView(inputAreaTexto);

        outputAreaTexto.setEditable(false);
        outputAreaTexto.setColumns(20);
        outputAreaTexto.setRows(5);
        jScrollPane3.setViewportView(outputAreaTexto);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+3));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIMPLETRON");

        botaoCompilar.setText("Compilar");
        botaoCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCompilarActionPerformed(evt);
            }
        });

        botaoLimpar.setText("Limpar");
        botaoLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLimparActionPerformed(evt);
            }
        });

        otimizacaoCheckBox.setSelected(true);
        otimizacaoCheckBox.setText("Código otimizado");
        otimizacaoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otimizacaoCheckBoxActionPerformed(evt);
            }
        });

        lexicosintaticoCheckBox.setSelected(true);
        lexicosintaticoCheckBox.setText("Analise Sintática e Léxica");
        lexicosintaticoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lexicosintaticoCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(otimizacaoCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(lexicosintaticoCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoLimpar)
                        .addGap(18, 18, 18)
                        .addComponent(botaoCompilar)
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botaoCompilar)
                            .addComponent(botaoLimpar)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(otimizacaoCheckBox)
                            .addComponent(lexicosintaticoCheckBox))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLimparActionPerformed
        this.inputAreaTexto.setText("");
        
    }//GEN-LAST:event_botaoLimparActionPerformed

    
    
    
    private void botaoCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCompilarActionPerformed
        this.outputAreaTexto.setText("");
        String erro="";
        boolean compilou = true;
        boolean analiseenable = true;
        if(this.lexicosintaticoCheckBox.isSelected() == true){
        
            AnalisadorLexicoSintatico analise = new AnalisadorLexicoSintatico();
            String code[] = this.inputAreaTexto.getText().split("\n");
            String erroLex = analise.recebeString(code);
            String erroSintaxe = analise.chamaAnaliseSintatica();
            int flagErroLex = analise.getErroLex();
            int flagErroSintaxe = analise.getErroSin();
            if(flagErroLex!=0){
                compilou = false;
                System.out.println(erroLex);
                erro = erroLex+"\n";
            }
            if(flagErroSintaxe!=0){
                compilou = false;
                System.out.println(erroSintaxe);
                erro += erroSintaxe+"\n";
            }
        } 
        
        if(compilou == true){
            String sucesso = "CONTRUÍDO COM SUCESSO!";
            String codigoFonte = this.inputAreaTexto.getText();    
            String tabelaSimbolos = "";
            String linhas[] = Controlador.analisador.splitLinhas(codigoFonte);
            Controlador.analisador.classificaLexemas(linhas);
            int [] mem = Controlador.memory.getMemoria();
            Controlador.analisador.resolveReferencias();
            mem = Controlador.memory.getMemoria();
            //instanciar uma tela de relatorio de memoria
            String instr="";
            for (int m : mem) {
                    instr+= "+"+Integer.toString(m)+"\n";
            }
            MemoryDumpUI telaInstrucao = new MemoryDumpUI();
            telaInstrucao.addText(instr);
            telaInstrucao.setVisible(true);
            this.outputAreaTexto.setText(sucesso);
            ItemTabelaEntrada [] itens = Controlador.table.getItens();
            for (ItemTabelaEntrada t : itens) {
                tabelaSimbolos += t.getSimbolo() + " " + t.getTipo() + " " + t.getLocal() + "\n";
            }
            TabelaSimbolosUI tableSymbol = new TabelaSimbolosUI();
            tableSymbol.addText(tabelaSimbolos);
            tableSymbol.setVisible(true);
        }else{
            this.outputAreaTexto.setText(erro);
        }
     
    }//GEN-LAST:event_botaoCompilarActionPerformed

    private void otimizacaoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otimizacaoCheckBoxActionPerformed
        if(!this.otimizacaoCheckBox.isSelected()){
            Controlador.analisador.otimizado = false;
        }
    }//GEN-LAST:event_otimizacaoCheckBoxActionPerformed

    private void lexicosintaticoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lexicosintaticoCheckBoxActionPerformed
        if(this.lexicosintaticoCheckBox.isSelected() == false){
            
        }
            
    }//GEN-LAST:event_lexicosintaticoCheckBoxActionPerformed

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
            java.util.logging.Logger.getLogger(compiladorIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(compiladorIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(compiladorIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(compiladorIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompiladorIO("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCompilar;
    private javax.swing.JButton botaoLimpar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea inputAreaTexto;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox lexicosintaticoCheckBox;
    private javax.swing.JCheckBox otimizacaoCheckBox;
    private javax.swing.JTextArea outputAreaTexto;
    // End of variables declaration//GEN-END:variables

    
    public void setText(String code){
        inputAreaTexto.setText(code);
    }

    private void analise(String[] linha) {
        int i = 0;
        
        String error="";
        int countError=0;
        
        
        
        while(i < linha.length){
           String lexema[] = linha[i].split(" ");
           int j = 0;
           if(Integer.parseInt(lexema[j]) > 0 && Integer.parseInt(lexema[j])
                   <100){
                table.addNumeroLinha(Integer.parseInt(lexema[j]));
           }else{
               countError++;
               error+="Erro na linha: "+Integer.toString(countError)+"\n";
               error+="Numero de linha incorreto\n";
           }
            j++;
            while(j < lexema.length){
                char result = classifica(lexema[j]);
               switch (result) {
                   
                   case 'V':
                       
                       table.addVariavel(Integer.parseInt(lexema[j]),
                               mem.addVar(Integer.parseInt(lexema[j])));
                       break;
                   case 'C':
                       table.addConstante(Integer.parseInt(lexema[j]),
                               mem.addVar(Integer.parseInt(lexema[j])));
                       break;
                   
                       
                   default:
                       error +="Erro na linha: "+Integer.toString(countError)+"\n";
                       error +="Expressao Invalida";
                       break;
               }
                j++;
            }
            i++;
        }
        if(countError > 0){
            this.outputAreaTexto.setText(error);
        }
    }
    char classifica(String lex){
        int value;
        if(lex.length() == 1){
            if(isChar(lex))
                return 'V';
            else if(isNumber(lex))
                return 'C';
            return '0';
        }else if(isComando(lex) !="0"){
            String var = isComando(lex);
            
        }
        return '0';
    }
    boolean isChar(String str){
        if(Integer.parseInt(str) > 96 && Integer.parseInt(str) < 123)
            return true;
        return false;
    }
    boolean isNumber(String str){
        if(Integer.parseInt(str) > 47 && Integer.parseInt(str) < 58)
            return true;
        return false;
    }
    String isComando(String lex){
        Analisador analise = new Analisador();
        if(lex == "input")return "10";
        else if(lex == "print")return "11";
        else if(lex == "goto")return "40";
        
        return "0";
    }
}
