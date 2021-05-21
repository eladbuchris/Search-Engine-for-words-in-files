import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This is an interface declaring the needed methods to evaluate a query by the index.
 */
public interface QueryEvaluator {
    ArrayList<String> evaluateQueryCaseInsensitive(ArrayList<String> postFixQuery);
    ArrayList<String> evaluateQueryCaseSensitive(ArrayList<String> postFixQuery);
}
