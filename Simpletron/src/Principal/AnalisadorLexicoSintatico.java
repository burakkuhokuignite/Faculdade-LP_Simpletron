/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.ArrayList;

/**
 *
 * @author luciano_simeaochrome
 */
public class AnalisadorLexicoSintatico {
    private String Comando = "C";
    private String Variavel = "V";
    private String CadeiaVariavel = "CV";
    private String Numero = "N";
    private String Operador = "O";
    private String OperadorLogico = "OL";
    private String Invalido = "I";
    private String Espaco = "E";
    private String FimLinha = "EL";
    private String CaractereIgnorado = "CI";
    
    
    private ArrayList<String> comandos = new ArrayList();
    private ArrayList<String> tokens = new ArrayList();
    private ArrayList<String> lexemas = new ArrayList();
    
    public AnalisadorLexicoSintatico(){
        start();
    }
    private void start(){
        comandos.add("input");//10
        comandos.add("rem");//
        comandos.add("let");
        comandos.add("print");//11
        comandos.add("goto");//40
        comandos.add("if");
        comandos.add("end");//43
    }
    
    private String erro = "";
    private String erro2 = "";
    private int erroLex = 0;
    private int erroSin = 0;
    
    
    public String recebeString(String linhas[]){
        for(int i = 0; i < linhas.length; i ++){
            erro += "     Linha: " + i + "\n\n";
            analisaLinha(linhas[i]);
        }
        return erro;
    }
    
    public void analisaLinha(String linha){
        String palavras[] = linha.split(" ");
        int flagRem = 0;
        for(int i = 0; i < palavras.length; i ++){
            lexemas.add(palavras[i]);
            erro += "- Lexema : " + palavras[i] + "\n";
            //System.out.println(palavras[i] + "\n");
            if(flagRem == 1)tokens.add(CaractereIgnorado);
            else analiseLexica(palavras[i]);
            if(palavras[i].equals("rem"))flagRem =1;
        }
        tokens.add(FimLinha);
        lexemas.add("\n");
    }
    
    
    public boolean analiseLexica(String palavra){
        if(palavra.equals("")){
            tokens.add(Espaco);
            erroLex++;
            return false;
        }
        for(int i = 0; i < comandos.size(); i ++){ // Verifica se ?? um comando
            if(comandos.get(i).equals(palavra)){
                tokens.add(Comando);
                return true;
            }
        }
        if(isNumber(palavra)){ // Verifica se ?? um n??mero
            tokens.add(Numero);
            return true;
        }
        if(palavra.length() == 2){
            if(palavra.equals("==") || palavra.equals("<=") || palavra.equals(">=") || palavra.equals("<") || palavra.equals(">")){
                tokens.add(OperadorLogico);
                return true;
            }
            char c[] = palavra.toCharArray();
            int n = (int)c[0];
            if((n >= 97 || n <= 122 || n >= 65 || n <= 90) && c[1] == ','){
                tokens.add(CadeiaVariavel);
                return true;
            }
        }
        if(palavra.length() == 1){  // Verifica se o tamanho ?? 
           if(palavra.equals("=")){
               tokens.add(OperadorLogico);
               return true;
           }
           char c[] = palavra.toCharArray();
           int num = (int)c[0];
           if(palavra.equals("+") || palavra.equals("-") || palavra.equals("*") || palavra.equals("/") || palavra.equals("^") || palavra.equals("%")){
               tokens.add(Operador);
               return true;
           }
           else if(num >= 97 || num <= 122){ //Verifica se ?? uma letra
               tokens.add(Variavel);
               return true;
           }
           else if(num >= 65 || num <= 90){
               tokens.add(Variavel);
               return true;
           }
        }
        tokens.add(Invalido);
        erro += "   CARACTERE INVALIDO \n";
        erroLex++;
        return false;
    }
    
    private int Finaliza = 0;
    private ArrayList<String> arrayGo = new ArrayList();
    private ArrayList<String> arrayLi = new ArrayList();
    
    
    public String chamaAnaliseSintatica(){
        int cont = 1, aux2;
        ArrayList<String> aux = new ArrayList();
        for(int i = 0; i < tokens.size(); i ++){
            aux.clear();
            aux2 = i;
            while(tokens.get(i) != FimLinha && i < tokens.size()){
                aux.add(tokens.get(i));
                i ++;
            }
            aux.add(tokens.get(i));
            erro2 += "      LINHA: " + cont + "\n\n";
            if(aux.size() > 0) analiseSintatica(aux, aux2);
            erro2 += "\n\n";
            if(Finaliza == 1 && (i+1) < tokens.size()){
                erroSin ++;
                erro2 += "Erro Sint??tico: N??o deve haver instru????es ap??s o comando 'end'!!! \n";
                i = tokens.size();
            }
            cont ++;
        }
        if(Finaliza == 0){
            erro2 += "Erro Sint??tico: Comando 'end' n??o encontrado!! \n";
        }
        linhasGoto();
        return erro2;
    }
    
    
    public boolean temEspaco(ArrayList<String> linha){
        for(int i = 0; i < linha.size(); i ++){
            if(linha.get(i).equals(Espaco)) return true;
        }
        return false;
    }
    
    
    public boolean analiseSintatica(ArrayList<String> linha, int j){
        int i = 0;
        int tam = linha.size();
        if(i < tam){
            if(!linha.get(i).equals(Numero)){  // Caso n??o tenha o n??mero da linha
                erro2 += "Erro Sint??tico: N??mero de linha n??o encontrado!! \n";
                erroSin ++;
                return false;
            }
            arrayLi.add(lexemas.get(j));
            i++;
            j++;
            if(linha.get(i).equals(Comando)){ // Caso tenha um comando ap??s o n??mero de linha
                if(lexemas.get(j).equals("rem")); // Caso o comando seja 'rem', ignora tudo que vem depois
                else if(temEspaco(linha)){
                    erro2 += "Erro Sint??tico: Todos os lexemas da linha devem ser separado por apenas UM espa??o!! \n";
                    erroSin ++;
                    return false;
                }   
                else if(lexemas.get(j).equals("end")){  // Caso seja "end", supostamente acabou o c??digo
                    Finaliza = 1;
                    if(tam > 3){
                        erro2 += "Comando 'end' n??o ?? final!! \n";
                        erroSin ++;
                        return false;
                    }
                }
                else if(lexemas.get(j).equals("print")){
                    if(tam < 4){
                        erro2 += "Comando 'print' deve ser acompanhado por uma ou mais vari??veis!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i + 1).equals(Variavel) && !linha.get(i+1).equals(CadeiaVariavel)){
                        erro2 += "Erro Sint??tico: Comando 'print' n??o est?? seguido por uma ou mais vari??veis!! \n";
                        erroSin ++;
                        return false;
                    }
        //            System.out.println("Flag1");
                    if(linha.get(i + 1).equals(CadeiaVariavel) && (i+1)<tam-1){
                        i++;
                        while(!linha.get(i).equals(Variavel) && i < tam-1){
          //                  System.out.println("FLAG while");
                            if(!linha.get(i).equals(CadeiaVariavel)){
                                erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'print' em ordem inv??lida!! \n";
                                erroSin ++;
                                return false;
                            }
                            i++;
                        }
                        if(linha.get(i).equals(Variavel) && !linha.get(i+1).equals(FimLinha)){
                            erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'print' em ordem inv??lida!! \n";
                            erroSin ++;
                            return false;
                        }
                        else if(i == tam-1 && !linha.get(i-1).equals(Variavel)){
                            erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'print' em ordem inv??lida!! \n";
                            erroSin ++;
                            return false;
                        }
                    }
                }
                else if(lexemas.get(j).equals("input")){
                    if(tam < 4){
                        erro2 += "Comando 'input' deve ser acompanhado por uma ou mais vari??veis!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i + 1).equals(Variavel) && !linha.get(i+1).equals(CadeiaVariavel)){
                        erro2 += "Erro Sint??tico: Comando 'input' n??o est?? seguido por uma ou mais vari??veis!! \n";
                        erroSin ++;
                        return false;
                    }
            //        System.out.println("Flag1");
                    if(linha.get(i + 1).equals(CadeiaVariavel) && (i+1)<tam-1){
                        i++;
                        while(!linha.get(i).equals(Variavel) && i < tam-1){
              //              System.out.println("FLAG while");
                            if(!linha.get(i).equals(CadeiaVariavel)){
                                erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'input' em ordem inv??lida!! \n";
                                erroSin ++;
                                return false;
                            }
                            i++;
                        }
                        if(linha.get(i).equals(Variavel) && !linha.get(i+1).equals(FimLinha)){
                            erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'input' em ordem inv??lida!! \n";
                            erroSin ++;
                            return false;
                        }
                        else if(i == tam-1 && !linha.get(i-1).equals(Variavel)){
                            erro2 += "Erro Sint??tico: Vari??veis ap??s o comando 'input' em ordem inv??lida!! \n";
                            erroSin ++;
                            return false;
                        }
                    }
                }
                else if(lexemas.get(j).equals("let")){
                    //System.out.println("FLAG LET");
                    if(tam < 5){
                        erro2 += "Erro Sint??tico: Comando 'let' deve ser acompanhado por uma express??o v??lida!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+1).equals(Variavel)){
                        erro2 += "Erro Sint??tico: Comando 'let deve ser seguido por uma vari??vel para atribui????o!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+2).equals(OperadorLogico) || !lexemas.get(j+2).equals("=")){
                        erro2 += "Erro Sint??tico: Comando 'let' deve ser seguido por uma opera????o de atribui????o!! \n";
                        erroSin ++;
                        return false;
                    }
                    else {
                        i = i + 3;
                        while(i < tam-1){
                            if(!linha.get(i).equals(Variavel) && !linha.get(i).equals(Numero)){
                                erro2 += "Erro Sint??tico: Express??o que procede 'let' ?? inv??lida 1!! \n";
                                erroSin ++;
                                return false;
                            }
                            i++;
                            if(i < tam-1){
                                if(!linha.get(i).equals(Operador)){
                                    erro2 += "Erro Sint??tico: Express??o que procede 'let' ?? inv??lida 2!! \n";
                                    erroSin ++;
                                    return false;
                                }
                                else if(!linha.get(i+1).equals(Numero) && !linha.get(i+1).equals(Variavel)){
                                    erro2 += "Erro Sint??tico: Express??o que procede 'let' ?? inv??lida 2!! \n";
                                    erroSin ++;
                                    return false;
                                }
                                i++;
                            }
                        }
                        if(!linha.get(i).equals(FimLinha)){
                            erro2 += "Erro Sint??tico: Comando 'let' deve ser acompanhado apenas por uma express??o!! \n";
                            erroSin ++;
                            return false;
                        }
                    }
                }
                else if(lexemas.get(j).equals("if")){
                    if(tam != 8){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    if(!linha.get(i+1).equals(Variavel) && !linha.get(i+1).equals(Numero)){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+2).equals(OperadorLogico)){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+3).equals(Variavel) && !linha.get(i+3).equals(Numero)){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    j += 4; 
                    if(!linha.get(i+4).equals(Comando) || !lexemas.get(j).equals("goto")){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+5).equals(Numero)){
                        erro2 += "Erro Sint??tico: Comando 'if' deve ser acompanhado de uma express??o l??gica e de um 'goto'!! \n";
                        erroSin ++;
                        return false;
                    }
                    else arrayGo.add(lexemas.get(j+1));
                }
                else if(lexemas.get(j).equals("goto")){
                    if(tam != 4){
                        erro2 += "Erro Sint??tico: Comando 'goto' deve ser acompanhado apenas por um n??mero!! \n";
                        erroSin ++;
                        return false;
                    }
                    else if(!linha.get(i+1).equals(Numero)){
                        erro2 += "Erro Sint??tico: Comando 'goto' deve ser acompanhado por um n??mero!! \n";
                        erroSin ++;
                        return false;
                    }
                    else arrayGo.add(lexemas.get(j+1));
                }
            }
            else{ // Caso n??o tenha um comando ap??s o n??mero de linha
                erro2 += "Erro Sint??tico: Comando n??o encontrado!! \n";
                erroSin ++;
                return false;
            } 
        }
        return true;
    }
    
    
    public void linhasGoto(){
        int aux;
        for(int i = 0; i < arrayGo.size(); i ++){
            aux = 0;
            int a = Integer.parseInt(arrayGo.get(i));
            System.out.println(a);
            aux = 1;
            for(int j = 0; j < arrayLi.size(); j ++){
                int b = Integer.parseInt(arrayLi.get(j));
                System.out.println(b);
                if(a == b) aux = 0;
            }
            if(aux == 1){
                erro2 += "Erro Sint??tico: Comando 'goto' ligado ?? linha inexistente!! Linha Referenciada: " + a + "\n";
                erroSin ++;
            }
        }
    }
    
    
    public boolean isNumber(String str){
        char vet[] = str.toCharArray();
        for(int i = 0; i < vet.length; i ++){
            if(!Character.isDigit(vet[i])) return false;
        }
        return true;
    }
    
    public int getErroLex(){
        return erroLex;
    }
    
    public int getErroSin(){
        return erroSin;
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
    
    public ArrayList<String> getLexemas(){
        return lexemas;
    }

    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }
   
}
