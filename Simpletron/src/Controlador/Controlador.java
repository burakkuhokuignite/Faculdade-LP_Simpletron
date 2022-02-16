/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Principal.Analisador;
import Principal.ItemTabelaEntrada;
import Principal.Memoria;
import Principal.TabelaEntrada;

public class Controlador {
    public static TabelaEntrada table = new TabelaEntrada();
    public static final int tamMemory = 100;
    public static  Memoria memory = new Memoria(tamMemory);
    public static  Analisador analisador = new Analisador();
    
    public static void addTabelaLineNumber(int num) {
        table.addNumeroLinha(num);
    }
    public static void geraLexemas(String codigo) {
//        this.classificaLexemas(analisador.geraLexemas(codigo));
    }
    public static void classificaLexemas(String str[]){
        analisador.classificaLexemas(str);   
    }
    public static int addTabelaVariavel(int num){
        return table.addVariavel(num,memory.addVar(num));
    }
    public static int callocTemp(){
        return memory.addVar(0);
    }
    public static int addTabelaConstante(int num) {
        return table.addConstante(num,memory.addVar(num));
    }

    public static int TabelaContainsVar(int parseInt) {
         ItemTabelaEntrada [] it = Controlador.table.getItens();
        for (ItemTabelaEntrada i: it) {
            if(i.getSimbolo() == parseInt && i.getTipo() == 'V'){
                return i.getLocal();
            }
        }
        return Controlador.addTabelaVariavel(parseInt);
    }
    public static int TabelaContainsConst(int parseInt) {
         ItemTabelaEntrada [] it = Controlador.table.getItens();
        for (ItemTabelaEntrada i: it) {
            if(i.getSimbolo() == parseInt && i.getTipo() == 'C'){
                return i.getLocal();
            }
        }
        return Controlador.addTabelaConstante(parseInt);
    }
    public static int TabelaContainsLine(int parseInt) {
         ItemTabelaEntrada [] it = Controlador.table.getItens();
        for (ItemTabelaEntrada i: it) {
            if(i.getSimbolo() == parseInt && i.getTipo() == 'L'){
                return i.getLocal();
            }
        }
        return -1;
    }

    public static void addTabelaInstrucao(int i, int val) {
            i*=tamMemory;
            i+=val;
            Controlador.memory.addInstrucao(i);
            Controlador.table.incLinhaContador();
    }
}

