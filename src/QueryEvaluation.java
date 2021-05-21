import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class QueryEvaluation extends AbstractInvertedIndex implements OperationsForQuery {
    String[] operands = new String[]{"NOT", "AND", "OR"};
    HashMap<String, ArrayList<String>> invertedIndexInsensitive = getInvertedIndexInsensitive();
    HashMap<String, ArrayList<String>> invertedIndexSensitive = getInvertedIndexSensitive();

    /**
     * This method evaluates the query of a CaseSensitive index given to it, by checking if we are now
     * dealing with an operand or an operator.
     * It goes over the *postfix* query. If the word is an operand it pushes it into the stack.
     * If the word is an operator, it checks which one is it and works accordingly. then it pops the operands
     * from the stack.
     *
     * @param postFixQuery The query in its postfix notation.
     * @return A String Arraylist holds the file names asked by the query.
     */
    public ArrayList<String> evaluateQueryCaseSensitive(ArrayList<String> postFixQuery) {
        ArrayList<ArrayList<String>> fileNames = new ArrayList<>();
        Stack<String> operandsStack = new Stack<>();
        boolean metNot = false;
        for (String word : postFixQuery) {
            if (postFixQuery.size() == 1) {
                fileNames.add(invertedIndexSensitive.get(word));
                return fileNames.get(0);
            }
            if (word.equals("NOT")) {
                metNot = true;
            }
            if (isOperand(word)) {
                operandsStack.push(word);
            } else {
                if (operandsStack.size() >= 2) {
                    String secondOperand = operandsStack.pop();
                    String firstOperand = operandsStack.pop();
                    if (word.equals("OR")) {
                        fileNames.add(this.dealsWithOr(invertedIndexSensitive.get(
                                firstOperand),
                                invertedIndexSensitive.get(secondOperand)));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(this.dealsWithAnd(invertedIndexSensitive.get(
                                firstOperand),
                                invertedIndexSensitive.get(secondOperand)));
                    } else if (word.equals("AND")) {
                        fileNames.add(this.dealsWithAndNot(invertedIndexSensitive.get(
                                firstOperand.toLowerCase()),
                                invertedIndexSensitive.get(secondOperand)));
                        metNot = false;
                    }
                } else if (operandsStack.size() == 1) {
                    if (word.equals("OR")) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithOr(fileNames.get(fileNames.size() - 1),
                                invertedIndexSensitive.get(operandsStack.pop())));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithAnd(fileNames.get(fileNames.size() - 1),
                                invertedIndexSensitive.get(operandsStack.pop())));
                    } else if (word.equals("AND")) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithAndNot(fileNames.get(fileNames.size() - 1),
                                invertedIndexSensitive.get(operandsStack.pop())));
                        metNot = false;
                    }
                } else if (operandsStack.isEmpty()) {
                    if (word.equals("OR")) {
                        fileNames.add(0, this.dealsWithOr(fileNames.get(0),
                                fileNames.get(1)));
                        fileNames.remove(fileNames.get(1));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(0, this.dealsWithAnd(fileNames.get(0),
                                fileNames.get(1)));
                        fileNames.remove(fileNames.get(1));
                    } else if (word.equals("AND")) {
                        fileNames.add(0, this.dealsWithAndNot(fileNames.get(0),
                                fileNames.get(1)));
                        metNot = false;
                        fileNames.remove(fileNames.get(1));
                    }
                }
            }
        }
        return fileNames.get(0);
    }

    /**
     * This method works the same as the precious one, only for CaseInsensitive index.
     */
    public ArrayList<String> evaluateQueryCaseInsensitive(ArrayList<String> postFixQuery) {
        ArrayList<ArrayList<String>> fileNames = new ArrayList<>();
        Stack<String> operandsStack = new Stack<>();
        boolean metNot = false;
        for (String word : postFixQuery) {
            if (postFixQuery.size() == 1) {
                fileNames.add(invertedIndexInsensitive.get(word.toLowerCase()));
                return fileNames.get(0);
            }
            if (word.equals("NOT")) {
                metNot = true;
            }
            if (isOperand(word)) {
                operandsStack.push(word);
            } else {
                if (operandsStack.size() >= 2) {
                    String secondOperand = operandsStack.pop();
                    String firstOperand = operandsStack.pop();
                    if (word.equals("OR")) {
                        fileNames.add(this.dealsWithOr(invertedIndexInsensitive.get(
                                firstOperand.toLowerCase()),
                                invertedIndexInsensitive.get(secondOperand.toLowerCase())));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(this.dealsWithAnd(invertedIndexInsensitive.get(
                                firstOperand.toLowerCase()),
                                invertedIndexInsensitive.get(secondOperand.toLowerCase())));
                    } else if (word.equals("AND")) {
                        fileNames.add(this.dealsWithAndNot(invertedIndexInsensitive.get(
                                firstOperand.toLowerCase()),
                                invertedIndexInsensitive.get(secondOperand.toLowerCase())));
                        metNot = false;
                    }
                } else if (operandsStack.size() == 1) {
                    if (word.equals("OR")) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithOr(fileNames.get(fileNames.size() - 1),
                                invertedIndexInsensitive.get(operandsStack.pop().toLowerCase())));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithAnd(fileNames.get(fileNames.size() - 1),
                                invertedIndexInsensitive.get(operandsStack.pop().toLowerCase())));
                    } else if (word.equals("AND")) {
                        fileNames.add(fileNames.size() - 1, this.dealsWithAndNot(fileNames.get(fileNames.size() - 1),
                                invertedIndexInsensitive.get(operandsStack.pop().toLowerCase())));
                        metNot = false;
                    }
                } else if (operandsStack.isEmpty()) {
                    if (word.equals("OR")) {
                        fileNames.add(0, this.dealsWithOr(fileNames.get(0),
                                fileNames.get(1)));
                        fileNames.remove(fileNames.get(fileNames.size() - 1));
                    } else if (word.equals("AND") && !metNot) {
                        fileNames.add(0, this.dealsWithAnd(fileNames.get(0),
                                fileNames.get(1)));
                        fileNames.remove(fileNames.get(fileNames.size() - 1));
                    } else if (word.equals("AND")) {
                        fileNames.add(0, this.dealsWithAndNot(fileNames.get(0),
                                fileNames.get(1)));
                        metNot = false;
                        fileNames.remove(fileNames.get(fileNames.size() - 1));
                    }
                }
            }
        }
        return fileNames.get(0);
    }

    /**
     * This method gets the joint elements of two arraylists.
     *
     * @param first  The first arraylist
     * @param second The second arraylist
     * @param <E>    A generic Element, so it could to it for many types.
     * @return An Arraylist of the joint Elements.
     */
    public <E> ArrayList<E> dealsWithAnd(ArrayList<E> first, ArrayList<E> second) {
        for (int i = first.size() - 1; i >= 0; i--) {
            if (!(second.contains(first.get(i)))) {
                first.remove(first.get(i));
            }
        }
        return first;
    }

    /**
     * This method gets the un joint elements of two arraylists.
     *
     * @param first  The first arraylist
     * @param second The second arraylist
     * @param <E>    A generic Element, so it could to it for many types.
     * @return An Arraylist of the un joint Elements.
     */
    public <E> ArrayList<E> dealsWithAndNot(ArrayList<E> first, ArrayList<E> second) {
        for (int i = first.size() - 1; i >= 0; i--) {
            if (second.contains(first.get(i))) {
                first.remove(first.get(i));
            }
        }
        return first;
    }

    /**
     * This method makes an arraylist holds the elements of two arraylists.
     *
     * @param first  The first arraylist
     * @param second The second arraylist
     * @param <E>    A generic Element, so it could to it for many types.
     * @return An Arraylist of the united arraylists.
     */
    public <E> ArrayList<E> dealsWithOr(ArrayList<E> first, ArrayList<E> second) {
        first.addAll(second);
        return first;
    }

    /**
     * This method checks if a word is an Operand.
     *
     * @param word A string.
     * @return A boolean which is true if the word is an operand, false otherwise.
     */
    public boolean isOperand(String word) {
        String[] nonOperands = new String[]{"AND", "OR", "NOT", "(", ")"};
        if (Arrays.asList(nonOperands).contains(word)) {
            return false;
        }
        return true;
    }

    /**
     * This method switches and infix notation query into a postfix notation
     * It has an instance tmpStack, which is a String stack, which allows us to make the
     * transition easily.
     *
     * @param query A String array which holds the original infix query, each word in place.
     * @return A String Arraylist which holds the postfix query, each words in each place.
     */
    public ArrayList<String> inFixToPostFix(String[] query) {
        ArrayList<String> postFix = new ArrayList<>();
        Stack<String> tmpStack = new Stack();
        for (String s : query) {
            if (isOperand(s)) {
                postFix.add(s);
            } else if (s.equals("(")) {
                tmpStack.push(s);
            } else if (s.equals(")")) {
                while (!(tmpStack.isEmpty()) && !(tmpStack.peek().equals("("))) {
                    postFix.add(tmpStack.pop());
                }
                tmpStack.pop();
            } else {
                while (!tmpStack.isEmpty() && !(tmpStack.peek().equals("(")) && !(s.equals("NOT"))) {
                    postFix.add(tmpStack.pop());
                }
                tmpStack.push(s);
            }
        }
        while (!tmpStack.isEmpty() && !(tmpStack.peek().equals("("))) {
            {
                postFix.add(tmpStack.pop());
            }
        }
    return postFix;
    }
}
