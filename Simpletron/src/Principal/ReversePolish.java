package Principal;

import java.util.Stack;

class ReversePolish {

    public static String convertToReversePolish(String exp) {
        if (exp == null) {
            return null;
        }
        String res = "";
        int len = exp.length();
        Stack<Character> operator = new Stack<Character>();
        Stack<String> reversePolish = new Stack<String>();
        //avoid checking empty
        operator.push('#');
        for (int i = 0; i < len;) {
            //deal with space
            while (i < len && exp.charAt(i) == ' ') {
                i++;
            }
            if (i == len) {
                break;
            }
            //if is number
            if (isVariable(exp.charAt(i) + " ")) {
                reversePolish.push(exp.charAt(i) + " ");
                i++;
            } else if (isNum(exp.charAt(i))) {
                String num = "";
                while (i < len && isNum(exp.charAt(i))) {
                    num += exp.charAt(i++);
                }
                reversePolish.push(num);
                //is operator
            } else if (isOperator(exp.charAt(i))) {
                char op = exp.charAt(i);
                switch (op) {
                    case '(':
                        operator.push(op);
                        break;
                    case ')':
                        while (operator.peek() != '(') {
                            reversePolish.push(Character.toString(operator.pop()));
                        }
                        operator.pop();
                        break;
                    case '+':
                    case '-':
                        if (operator.peek() == '(') {
                            operator.push(op);
                        } else {
                            while (operator.peek() != '#' && operator.peek() != '(') {
                                reversePolish.push(Character.toString(operator.pop()));
                            }
                            operator.push(op);
                        }
                        break;
                    case '*':
                    case '/':
                    case '^':
                    case '%':
                        if (operator.peek() == '(') {
                            operator.push(op);
                        } else {
                            while (operator.peek() != '#' && operator.peek() != '+'
                                    && operator.peek() != '-' && operator.peek() != '(') {
                                reversePolish.push(Character.toString(operator.pop()));
                            }
                            operator.push(op);
                        }
                        break;
                }
                i++;
            }
        }
        while (operator.peek() != '#') {
            reversePolish.push(Character.toString(operator.pop()));
        }
        while (!reversePolish.isEmpty()) {
            res = res.length() == 0 ? reversePolish.pop() + res : reversePolish.pop() + " " + res;
        }
        return res;
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '%' || c == '^' || c == '/' || c == '(' || c == ')';
    }

    public static  boolean isNum(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }

    public static  int StringToInt(String s) {
        char[] c = s.toCharArray();
        return (int) c[0];
    }

    private static  boolean isVariable(String lexema) {
        int charAsc = StringToInt(lexema);
        return charAsc > 76 && charAsc < 123;
    }

    public static void main(String[] args) throws Exception {
//        ReversePolish s = new ReversePolish();
//        System.out.println(s.convertToReversePolish("( ( 1  + 3 ) / 4 ) ^ 5"));
//        System.out.println(ReversePolish.isOperator('%'));
    }
}







