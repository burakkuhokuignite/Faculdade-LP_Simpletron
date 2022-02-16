/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;
import Controlador.Controlador;
import java.util.ArrayList;

/**
 *
 * @author luciano_simeao
 */
public class Analisador {
    public boolean otimizado = true;

    public enum Cmd{
        REM(0), INPUT(10),PRINT(11), IF(13), LOAD(20), STORE(21),
        ADD(30), SUB(31), DIV(32), MUL(33), MOD(34), POT(35),
        GOTO(40), GOTON(41), GOTOZ(42), END(43), 
        LET(12), NOLET(13), NFOUND(-1);
        public int valor;
        Cmd(int valor){
            this.valor = valor;
        }
        public int getValor(){
            return valor;
        }
    }
    //Declaracao de constantes:

    private Cmd isConversible(String lexema) {
        if(lexema.equals("input"))return Cmd.INPUT;
        if(lexema.equals("print"))return Cmd.PRINT;
        if(lexema.equals("goto"))return Cmd.GOTO;
        if(lexema.equals("if"))return Cmd.IF;
        if(lexema.equals("let")){
            if(otimizado) return Cmd.LET;
            else return Cmd.NOLET;
        }
        if(lexema.equals("end")) return Cmd.END;
        if(lexema.equals("rem"))return Cmd.REM;
        else return Cmd.NFOUND;
        
    }

    
    enum PalavrasReservadas{
        PALAVRA_RESERVADA, END, INDENTIFICADOR;
    }
    private ArrayList<String> comandos = new ArrayList<String>();
    private ArrayList<String> tokens = new ArrayList<String>();
    
    public Analisador(){
        start();
    }
    public String[] splitLinhas(String codigoFonte) {
        String linhas[] = codigoFonte.split("\n");
        return linhas;
    }
    private void start(){
        comandos.add("input");
        comandos.add("rem");
        comandos.add("let");
        comandos.add("print");
        comandos.add("goto");
        comandos.add("if");
        comandos.add("end");
    }

    public ArrayList<String> getComandos() {
        return comandos;
    }

    public void setComandos(ArrayList<String> comandos) {
        this.comandos = comandos;
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }
   
  

    private boolean isLineNumber(String lexema) {
        try{
            Integer.parseInt(lexema);
        }catch(NumberFormatException e){
            return false;
        }return true;
    }
    public void classificaLexemas(String[] lexemas) {
        int contaLinhas   = 0;
        while(contaLinhas < lexemas.length){
            int contaPalavras  = 0;
           String palavras[] = lexemas[contaLinhas].split(" ");
            if(this.isLineNumber(palavras[contaPalavras])){
                Controlador.addTabelaLineNumber(Integer.parseInt(palavras[contaPalavras]));
                contaPalavras++;
            }
            Cmd convert = this.isConversible(palavras[contaPalavras]);
            switch(convert){
                case INPUT:
                    {
                        for (int i = contaPalavras+1; i < palavras.length; i++) {
                            if(isVariable(palavras[i])) {
                                 int c = StringToInt(palavras[i]);
                                 Controlador.addTabelaVariavel(c);
                                 int value = Controlador.TabelaContainsVar(c);
                                 Controlador.addTabelaInstrucao((Cmd.INPUT.getValor()), value);
                            }
                        }
                    }
                    break;
                case PRINT:
                    {
                        for (int i = contaPalavras+1; i < palavras.length; i++) {
                            if(isVariable(palavras[i])) {
                                 int c = StringToInt(palavras[i]);
                                 Controlador.addTabelaVariavel(c);
                                 int value = Controlador.TabelaContainsVar(c);
                                 Controlador.addTabelaInstrucao((Cmd.PRINT.getValor()), value);
                            }
                        }
                    }
                    break;
                case IF:
                    {
                        int c;
                        int primOp = 0;
                        if(isVariable(palavras[contaPalavras])){
                             c = StringToInt(palavras[++contaPalavras]);
                            primOp = Controlador.TabelaContainsVar(c);
                        } else if(isConstant(palavras[contaPalavras])){
                            c = Integer.parseInt((palavras[++contaPalavras]));
                            primOp = Controlador.TabelaContainsConst(c);
                        }
                        
                        String op = palavras[++contaPalavras];
                        
                        int secOp = 0;
                        if(isVariable(palavras[++contaPalavras])){
                            c = StringToInt(palavras[contaPalavras]);
                            secOp = Controlador.TabelaContainsVar(c);
                        }
                        else if(isConstant(palavras[++contaPalavras])){
                             c = Integer.parseInt((palavras[contaPalavras]));
                            secOp = Controlador.TabelaContainsConst(c);
                        }
                        
                        contaPalavras++;
                        
                        int lGoto = 0;
                        int linha = Controlador.TabelaContainsLine(Integer.parseInt(palavras[++contaPalavras]));
                        if(linha != -1){
                            lGoto = linha; 
                        } else {
                            lGoto = 0;
                            //pegar o valor do goto e jogar nas flags
                            Controlador.table.setFlag(Integer.parseInt(palavras[contaPalavras]), Controlador.table.getLinhaContador()+2);
                        }
                         if(op.equals("==")){
                             Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), primOp);
                             Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), secOp);
                             Controlador.addTabelaInstrucao((Cmd.GOTOZ.getValor()), lGoto);
                         } else if(op.equals("<")){
                             Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), primOp);
                             Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), secOp);
                            Controlador.addTabelaInstrucao((Cmd.GOTON.getValor()), lGoto);
                         } else if(op.equals(">")){
                             Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), secOp);
                             Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), primOp);
                             Controlador.addTabelaInstrucao((Cmd.GOTON.getValor()), lGoto);
                         } else if(op.equals("<=")){
                             Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), primOp);
                             Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), secOp);
                             Controlador.addTabelaInstrucao((Cmd.GOTOZ.getValor()), lGoto);
                             Controlador.table.setFlag(Integer.parseInt(palavras[contaPalavras-1]), Controlador.table.getLinhaContador());
                             Controlador.addTabelaInstrucao((Cmd.GOTON.getValor()), lGoto);
                         } else if(op.equals(">=")){
                             Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), secOp);
                             Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), primOp);
                             Controlador.addTabelaInstrucao((Cmd.GOTOZ.getValor()), lGoto);
                             Controlador.table.setFlag(Integer.parseInt(palavras[contaPalavras-1]), Controlador.table.getLinhaContador());
                             Controlador.addTabelaInstrucao((Cmd.GOTON.getValor()), lGoto);
                         }
                    }
                    break;
                case GOTO:
                    {
                        int lGoto;
                        int linha = Controlador.TabelaContainsLine(Integer.parseInt(palavras[2]));
                        if(linha != -1){
                            lGoto = linha; 
                        } else {
                            lGoto = 0;
                            Controlador.table.setFlag(Integer.parseInt(palavras[2]), Controlador.table.getLinhaContador());
                        }
                       Controlador.addTabelaInstrucao((Cmd.GOTO.getValor()), lGoto);
                       //implementar valor do goto e jogar nas flags
                    }
                    break;
                case LET:
                    {
                        int c = StringToInt(palavras[++contaPalavras]);
                        int locResult = Controlador.TabelaContainsVar(c);
                        
                        
                        String op = "";
                        for (int i = 4; i < palavras.length; i++) {
                            op+=" " + palavras[i];
                        }
                        op = ReversePolish.convertToReversePolish(op);
                        String[] st = op.split(" ");
                        int loc = 0;
                        for(int i = 0; i < st.length;i++) {
                            if(!st[i].equals("") ){
                                if(ReversePolish.isOperator(st[i].charAt(0))){
                                    switch(st[i]){
                                            case "+":
                                                Controlador.addTabelaInstrucao((Cmd.ADD.getValor()), loc);
                                                break;
                                            case "-":
                                                Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), loc);
                                                break;
                                            case "*":
                                                Controlador.addTabelaInstrucao((Cmd.MUL.getValor()), loc);
                                                break;
                                            case "^":
                                                Controlador.addTabelaInstrucao((Cmd.POT.getValor()), loc);
                                                break;
                                            case "%":
                                                Controlador.addTabelaInstrucao((Cmd.MOD.getValor()), loc);
                                                break;
                                            case "/":
                                                Controlador.addTabelaInstrucao((Cmd.DIV.getValor()), loc);
                                                //verificar divisão por zero
                                                break;
                                            default:
                                                break;
                                        }

                                } else if(isVariable(st[i])) {
                                    int v = StringToInt(st[i]);
                                    loc = Controlador.TabelaContainsVar(v);
                                    if(i == 0 ) {
                                        Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                    }

                                } else if (isConstant(st[i])) {
                                    int co = Integer. parseInt(st[i]);
                                    loc = Controlador.TabelaContainsConst(co);
                                    if(i == 0 ) {
                                        Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), +loc);
                                    }
                                }
                            }
                        }
                        Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), locResult);
                    }
                    break;
                case NOLET:
                    {
                        int c = StringToInt(palavras[++contaPalavras]);
                        int locResult = Controlador.TabelaContainsVar(c);
                        
                        InfixToPostFix ip = new InfixToPostFix();
                        String op = "";
                        for (int i = 4; i < palavras.length; i++) {
                            op+=" " + palavras[i];
                        }
                        op = ReversePolish.convertToReversePolish(op);
                        String[] st = op.split(" ");
                        int loc = 0;
                        for(int i = 0; i < st.length;i++) {
                            if(!st[i].equals("") ){
                               if(ReversePolish.isOperator(st[i].charAt(0))){
                                    switch(st[i]){
                                        case "+":
                                            Controlador.addTabelaInstrucao((Cmd.ADD.getValor()), loc);
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()),  loc);
                                            break;
                                        case "-":
                                            Controlador.addTabelaInstrucao((Cmd.SUB.getValor()), loc);
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                            break;
                                        case "*":
                                            Controlador.addTabelaInstrucao((Cmd.MUL.getValor()), loc);
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                            break;
                                        case "^":
                                            Controlador.addTabelaInstrucao((Cmd.POT.getValor()), loc);
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                            break;
                                        case "%":
                                            Controlador.addTabelaInstrucao((Cmd.MOD.getValor()), loc);
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                            break;
                                        case "/":
                                            Controlador.addTabelaInstrucao((Cmd.DIV.getValor()), loc);
                                            //verificar divisão por zero
                                            loc = Controlador.callocTemp();
                                            Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), loc);
                                            Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                            break;
                                        default:
                                            break;
                                    }
                                } else if(isVariable(st[i])) {
                                    int v = StringToInt(st[i]);
                                    loc = Controlador.TabelaContainsVar(v);
                                    if(i == 0 ) {
                                        locResult = loc;
                                        Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), loc);
                                    }

                                } else if (isConstant(st[i])) {
                                    int co = Integer. parseInt(st[i]);
                                    loc = Controlador.TabelaContainsConst(co);
                                    if(i == 0 ) {
                                        locResult = loc;
                                        Controlador.addTabelaInstrucao((Cmd.LOAD.getValor()), +loc);
                                    }
                                }
                            }
                        }
                        Controlador.addTabelaInstrucao((Cmd.STORE.getValor()), locResult);
                    }
                    break;
                case END:
                    Controlador.addTabelaInstrucao((Cmd.END.getValor()), 0);
                    break;
                case REM:
                       break;
                default:
                    
                    break;
            }
            contaLinhas++;
        }             
    }
    public void resolveReferencias(){
        int [] flags = Controlador.table.getFlags();
        for (int i = 0; i < flags.length; i++) {
            if(flags[i] != -1){
                int line = Controlador.TabelaContainsLine(flags[i]);
                Controlador.memory.memory[i] += line;
            }
            
        }
    }
    public int StringToInt(String s){
        char[] c = s.toCharArray();
        return (int)c[0];
    }
    private boolean isVariable(String lexema){
        int charAsc = StringToInt(lexema);
        return charAsc > 76 && charAsc < 123;
    }
    private boolean isConstant(String lexema){
        char[] c = lexema.toCharArray();
        for (int i = 0; i < c.length; i++)
            if (!Character.isDigit(c[i])) 
                return false;
        return true;
    }
    private boolean isCommand(String lexema){
        for(int i=0; i<comandos.size(); i++){
            if(lexema.equals(comandos.get(i))){
                return true;
            }
        }return false;
    }
}
