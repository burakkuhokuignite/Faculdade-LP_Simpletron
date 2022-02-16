package simpletron;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Simpletron {

    public static final int MEMORIA = 100;
    public static final int LEITURA = 10;
    public static final int ESCRITA = 11;
    public static final int CARREGAR = 20;
    public static final int ARMAZENAR = 21;
    public static final int ADICIONAR = 30;
    public static final int SUBTRAIR = 31;
    public static final int DIVIDIR = 32;
    public static final int MULTIPLICAR = 33;
    public static final int BRANCH = 40;
    public static final int BRANCHNEG = 41;
    public static final int BRANCHZERO = 42;
    public static final int HALT = 43;
    private final DecimalFormat memoryFormatter;
    private int[] memoria;
    private int acumulador;
    private int contadorInstrucao;
    private int codigoOperacao;
    private int operando;
    private Scanner scanner;
    private boolean processando;

    public Simpletron() {
        processando = false;
        memoria = new int[MEMORIA];

        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = 0;
        }

        acumulador = 0;
        contadorInstrucao = 0;
        codigoOperacao = 0;
        operando = 0;

        scanner = new Scanner(System.in);
        memoryFormatter = new DecimalFormat("+0000;-0000");
    }

    private void load() throws LoadException {
        BufferedReader leitor = null;
        System.out.print("Digite o nome do seu programa: ");
        String source = "arquivosSML/" + scanner.nextLine();

        try {
            leitor = new BufferedReader(new FileReader(source));
            String line = leitor.readLine();
            int counter = 0;
            while (line != null) {
                int word = Integer.parseInt(line);
                if ((word >= -9999) && (word <= 9999)) {
                    memoria[counter] = word;
                    counter = counter + 1;
                } else {
                    throw new NumberFormatException();
                }
                line = leitor.readLine();
            }
        } catch (FileNotFoundException exception) {
            throw new LoadException("Erro: arquivo não encontrado!", exception);
        } catch (IOException exception) {
            throw new LoadException("Erro: arquivo inválido!", exception);
        } catch (NumberFormatException exception) {
            throw new LoadException("Erro: instrução inválida!", exception);
        } finally {
            try {
                leitor.close();
            } catch (IOException exception) {
                
            }
        }
        System.out.println("Arquivo Simpletron carregado com sucesso!");
    }

    private void readInstruction() {
        System.out.print("Entrada: ");
        int number = 0;

        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            number = Integer.MIN_VALUE;
        }
        if ((number >= -9999) && (number <= 9999)) {
            memoria[operando] = number;
            contadorInstrucao = contadorInstrucao + 1;
        } else {
            System.out.println("Erro: número inválido!");
        }
    }

    private void writeInstruction() {
        System.out.print("Saída: ");
        System.out.println(memoryFormatter.format(memoria[operando]));
        contadorInstrucao = contadorInstrucao + 1;
    }

    private void loadInstruction() {
        acumulador = memoria[operando];
        contadorInstrucao = contadorInstrucao + 1;
    }

    private void storeInstruction() {
        memoria[operando] = acumulador;
        contadorInstrucao = contadorInstrucao + 1;
    }

    private void addInstruction() throws InterpretException {
        acumulador = acumulador + memoria[operando];
        if ((acumulador >= -9999) && (acumulador <= 9999)) {
            contadorInstrucao = contadorInstrucao + 1;
        } else {
            throw new InterpretException("Erro: valor fora dos limites definidos (-9999, +9999)!");
        }
    }

    private void subtractInstruction() throws InterpretException {
        acumulador = acumulador - memoria[operando];
        if ((acumulador >= -9999) && (acumulador <= 9999)) {
            contadorInstrucao = contadorInstrucao + 1;
        } else {
            throw new InterpretException("Erro: valor fora dos limites definidos (-9999, +9999)!");
        }
    }

    private void divideInstruction() throws InterpretException {
        if (memoria[operando] != 0) {
            acumulador = acumulador / memoria[operando];
            contadorInstrucao = contadorInstrucao + 1;
        } else {
            throw new InterpretException("Erro: não é possível fazer divisão por zero!");
        }
    }

    private void multiplyInstruction() throws InterpretException {
        acumulador = acumulador * memoria[operando];
        if ((acumulador >= -9999) && (acumulador <= 9999)) {
            contadorInstrucao = contadorInstrucao + 1;
        } else {
            throw new InterpretException("Erro: valor fora dos limites definidos (-9999, +9999)!");
        }
    }

    private void branchInstruction() {
        contadorInstrucao = operando;
    }

    private void branchnegInstruction() {
        if (acumulador < 0) {
            contadorInstrucao = operando;
        } else {
            contadorInstrucao = contadorInstrucao + 1;
        }
    }

    private void branchzeroInstruction() {
        if (acumulador == 0) {
            contadorInstrucao = operando;
        } else {
            contadorInstrucao = contadorInstrucao + 1;
        }
    }

    private void haltInstruction() {
        processando = false;
    }

    private void interpret() throws InterpretException {
        System.out.println("Execução Simpletron iniciada!");
        processando = true;
        while (processando) {
            int instructionRegister = memoria[contadorInstrucao];
            codigoOperacao = instructionRegister / 100;
            operando = instructionRegister % 100;
            switch (codigoOperacao) {
                case LEITURA:
                    readInstruction();
                    break;
                case ESCRITA:
                    writeInstruction();
                    break;
                case CARREGAR:
                    loadInstruction();
                    break;
                case ARMAZENAR:
                    storeInstruction();
                    break;
                case ADICIONAR:
                    addInstruction();
                    break;
                case SUBTRAIR:
                    subtractInstruction();
                    break;
                case DIVIDIR:
                    divideInstruction();
                    break;
                case MULTIPLICAR:
                    multiplyInstruction();
                    break;
                case BRANCH:
                    branchInstruction();
                    break;
                case BRANCHNEG:
                    branchnegInstruction();
                    break;
                case BRANCHZERO:
                    branchzeroInstruction();
                    break;
                case HALT:
                    haltInstruction();
                    break;
                default:
                    throw new InterpretException("Erro: instrução inválida!");
            }
        }
        System.out.println("Execução do Simpletron finalizada!");
    }

    private void dump() {
        DecimalFormat variableFormatter = new DecimalFormat("   00");

        System.out.println();
        System.out.println("REGISTRADORES:");

        String temp = memoryFormatter.format(acumulador).substring(0, 5);
        System.out.println(temp + " Acumulador");

        temp = variableFormatter.format(contadorInstrucao);
        System.out.println(temp + " Contador de instrução");

        temp = memoryFormatter.format(memoria[contadorInstrucao]);
        System.out.println(temp + " Registrador de instrução");

        temp = variableFormatter.format(codigoOperacao);
        System.out.println(temp + " Código de operação");

        temp = variableFormatter.format(operando);
        System.out.println(temp + " Operando");

        System.out.println();
        System.out.println("MEMÓRIA:");
        System.out.print(" ");

        for (int i = 0; i < 10; i++) {
            System.out.print("     " + i);
        }
        System.out.println();
        for (int i = 0; i < memoria.length; i += 10) {
            System.out.print((i / 10) + " ");
            for (int j = i; j < i + 10; j++) {
                System.out.print(memoryFormatter.format(memoria[j]) + " ");
            }
            System.out.println();
        }
    }

    public void execute() {
        System.out.println("Bem vindo ao Simpletron! Aqui você poderá executar seus códigos escritos em SML.");

        try {
            load();
            interpret();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Execução do Simpletron terminada anormalmente!");
        } finally {
            dump();
        }
    }

    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();
        simpletron.execute();
    }
}
