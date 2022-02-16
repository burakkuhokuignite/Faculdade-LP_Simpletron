package Principal;


import java.util.Stack;

public class InfixToPostFix {

    public InfixToPostFix() {
    }

public String infixToPostfix(String str) {
    Stack<String> stack = new Stack<String>();
    String[] st = str.split(" ");
    String result = "";
    for (String s : st) {
        if (operator(s)) {
            if(("").equals(s)) {
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    result += " " + stack.pop();
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                if (!stack.isEmpty() && !isLowerPrecedence(s, stack.peek())) {
                    stack.push(s);
                } else {
                    while (!stack.isEmpty() && isLowerPrecedence(s, stack.peek())) {
                        String top = stack.pop();
                        if (!"(".equals(top)) {
                            result += " " + top;
                        }
                    }
                    stack.push(s);
                }
            }
        } else {
            result += " " + s;
        }
    }
    while (!stack.isEmpty()) {
        result += " " + stack.pop();
    }

    return result;
}

private boolean isLowerPrecedence(String s, String s1) {
    switch (s) {
    case "+":
        return !("+".equals(s1) || "(".equals(s1));
    case "-":
        return !("-".equals(s1) || "(".equals(s1));

    case "*":
        return "/".equals(s1) || "^".equals(s1) || "(".equals(s1);
    case "/":
        return "*".equals(s1) || "^".equals(s1) || "(".equals(s1);

    case "^":
        return "(".equals(s1);

    case "(":
        return false;

    default:
        return false;
    }

}

public static boolean operator(String s) {
    return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "^".equals(s) || "(".equals(s) ||
            ")".equals(s);
}

public static void main(String[] args) {
    InfixToPostFix itp = new InfixToPostFix();
    System.out.println("The Postfix expression for A*B-(C+D)+E is: " + itp.infixToPostfix("(a + b) + 3"));
    System.out.println("The Postfix expression for 1+2*4/5-7+3/6 is: " + itp.infixToPostfix("1+2*4/5-7+3/6"));
    System.out.println("The Postfix expression for a+(b*c)/d is: " + itp.infixToPostfix("a+(b*c)/d"));
}
}
