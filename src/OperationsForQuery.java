import java.util.ArrayList;

/**
 * This is an interface declaring the needed methods for dealing with a query operations.
 */
public interface OperationsForQuery {
    <E> ArrayList<E> dealsWithAnd(ArrayList<E> first, ArrayList<E> second);
    <E> ArrayList<E> dealsWithOr(ArrayList<E> first, ArrayList<E> second);
    <E> ArrayList<E> dealsWithAndNot(ArrayList<E> first, ArrayList<E> second);
    ArrayList<String> inFixToPostFix(String[] query);
    boolean isOperand(String word);

}
