/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

public class Memoria {
    int memory[];
    static int posLivre = 99;
    static int posInstrucao = 0;
    
    public Memoria(int tamanho){
         memory = new int[tamanho];
         posLivre = tamanho-1;
    }
    public int addVar(int var){
        memory[posLivre--] = var;
        return posLivre+1;
    }
    public void addInstrucao(int instr){
        memory[posInstrucao++] = instr;
    }

    public int getPosInstrucao(){
        return posInstrucao;
    }
    public int[] getMemoria(){
        int [] mem = new int[posInstrucao];
       System.arraycopy(this.memory, 0, mem, 0, posInstrucao);
       return mem;
    }
}
