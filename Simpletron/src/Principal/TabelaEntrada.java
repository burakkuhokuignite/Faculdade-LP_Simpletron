/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author luciano_simeao
 */

public class TabelaEntrada {
    private ItemTabelaEntrada[] item;
    private int proxDispo = 0;
    private int linhaContador = 0;
    private int [] flags;

    public TabelaEntrada() {
        this.item = new ItemTabelaEntrada[100];
        this.flags = new int[100];
        for (int i = 0; i < flags.length; i++) flags[i] = -1;
    }
    
    public void addNumeroLinha(int numeroLinha) {
        ItemTabelaEntrada i = new ItemTabelaEntrada();
        item[proxDispo] = i;
        item[proxDispo].setTipo('L');
        item[proxDispo].setLocal(linhaContador);
        item[proxDispo].setSimbolo(numeroLinha);
        proxDispo++;
    }

    public int addVariavel(int var, int pos) {
        if(proxDispo < 100){
            ItemTabelaEntrada i = new ItemTabelaEntrada();
            item[proxDispo] = i;
            item[proxDispo].setTipo('V');
            item[proxDispo].setSimbolo(var);
            item[proxDispo].setLocal(pos);
            proxDispo++;
            return pos;
        }
        return -1;
    }

    public int addConstante(int numeroConstante, int pos) {
        if(proxDispo < 100){
        ItemTabelaEntrada i = new ItemTabelaEntrada();
        item[proxDispo] = i;
        item[proxDispo].setTipo('C');
        item[proxDispo].setSimbolo(numeroConstante);
        item[proxDispo].setLocal(pos);
        proxDispo++;
        return pos;
        }
        return -1;
    }
    public  ItemTabelaEntrada[] getItens() {
        ItemTabelaEntrada [] itens = new ItemTabelaEntrada[proxDispo];
        System.arraycopy(this.item, 0, itens, 0, proxDispo);
        return itens;
    }
    
    public void incLinhaContador(){
        this.linhaContador++;
    }
    public void decLinhaContador(){
        this.linhaContador++;
    }
    public int getLinhaContador(){
        return this.linhaContador;
    }
    public void setFlag(int line, int pos){
        if(pos >= 0 && pos < 100){
            this.flags[pos] = line;
        }
    }
    public int[] getFlags(){
       return flags;
    }
}
