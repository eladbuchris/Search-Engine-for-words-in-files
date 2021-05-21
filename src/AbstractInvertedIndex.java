
import java.io.File;
import java.util.*;

/**
 * This class creates and handles the invertedIndex data structure.
 * At the moment, it has two types of data structures.
 * Instance invertedIndexInsensitive,a HashMap which holds only lower case letter words
 * Instance invertedIndexSensitive, HashMap,  which holds both lower case and upper case words.
 * It also has an instance setOfFileNames, a TreeSet, Which will hold the file names.
 */
public abstract class AbstractInvertedIndex{
    static final HashMap<String, ArrayList<String>> invertedIndexInsensitive = new HashMap<>();
    static final HashMap<String, ArrayList<String>> invertedIndexSensitive = new HashMap<>();
    private final TreeSet<String> setOfFileNames = new TreeSet<>();

    /**
     * This method returns the invertedIndexInsensitive field.
     * @return HashMap invertedIndexInsensitive
     */
    static HashMap<String, ArrayList<String>> getInvertedIndexInsensitive(){
        return invertedIndexInsensitive;
    }
    /**
     * This method returns the invertedIndexSensitive field.
     * @return HashMap invertedIndexSensitive
     */
    static HashMap<String, ArrayList<String>> getInvertedIndexSensitive(){
        return invertedIndexSensitive;
    }
    /**
     * This method builds the above explained data structures.
     * It checks if the user wants to create a Case Sensitive data structure, A non Case sensitive data structure
     * and if it wants another one, it asks him to specify demands for another one.
     * @param files The array of files in which it would search for words to put in the built data structures.
     */
    protected void buildInvertedIndex(File[] files) {
        if (this instanceof CaseInsensitive) {
            for (File file : files) {
                for (String line : Utils.readLines(file)) {
                    for (int i = 0; i < Utils.splitBySpace(line).length; i++) {
                        if (invertedIndexInsensitive.containsKey(Utils.splitBySpace(line)[i].toLowerCase())) {
                            if (!(invertedIndexInsensitive.get(Utils.splitBySpace(line)[i].toLowerCase()).contains(
                                    Utils.substringBetween(Utils.readLines(file).get(1),
                                            " ", " ")))) {
                                invertedIndexInsensitive.get(Utils.splitBySpace(line)[i].toLowerCase()).add(
                                        Utils.substringBetween(Utils.readLines(file).get(1),
                                        " ", " "));
                            }
                        }
                        else {
                            invertedIndexInsensitive.put(Utils.splitBySpace(line)[i].toLowerCase(),
                                    new ArrayList<String>());
                            invertedIndexInsensitive.get(Utils.splitBySpace(line)[i].toLowerCase()).add(
                                    Utils.substringBetween(Utils.readLines(file).get(1),
                                    " ", " "));
                        }
                    }
                }
            }
        } else if (this instanceof CaseSensitive) {
            for (File file : files) {
                for (String line : Utils.readLines(file)) {
                    for (int i = 0; i < Utils.splitBySpace(line).length; i++) {
                        if (invertedIndexSensitive.containsKey(Utils.splitBySpace(line)[i])) {
                            if (!(invertedIndexSensitive.get(Utils.splitBySpace(line)[i]).contains(
                                    Utils.substringBetween(Utils.readLines(file).get(1),
                                            " ", " ")))) {
                                invertedIndexSensitive.get(Utils.splitBySpace(line)[i]).add(
                                        Utils.substringBetween(Utils.readLines(file).get(1),
                                        " ", " "));
                            }
                        }
                        else {
                            invertedIndexSensitive.put(Utils.splitBySpace(line)[i], new ArrayList<String>());
                            invertedIndexSensitive.get(Utils.splitBySpace(line)[i]).add(
                                    Utils.substringBetween(Utils.readLines(file).get(1),
                                    " ", " "));
                        }
                    }
                }
            }
        } else {
            System.out.println("You need to specify demands for the new index");
        }
    }

    /**
     * This method has the following instances:
     * queryFileNames, A String Arraylist which will hold the returned value from the evaluateQuery method.
     * queryPostFix,  A String Arraylist which will hold the returned value from infixToPostFix method.
     * @param query A String query asked by the user
     * @return A String TreeSet, which holds the file names which meets the query.
     */
    protected TreeSet<String> runQuery(String query) {
    ArrayList<String> queryFileNames = null;
    ArrayList<String> queryPostFix;
    QueryEvaluation queryEvaluator = new QueryEvaluation();
    queryPostFix = queryEvaluator.inFixToPostFix(Utils.splitBySpace(query));
    if(this instanceof CaseInsensitive){
        queryFileNames = queryEvaluator.evaluateQueryCaseInsensitive(queryPostFix);
    }
    else if(this instanceof CaseSensitive){
        queryFileNames = queryEvaluator.evaluateQueryCaseSensitive(queryPostFix);
    }
    else{
        System.out.println("You need to specify demands for a new index");
    }
    if(!(setOfFileNames.isEmpty())){
        setOfFileNames.clear();
    }
    setOfFileNames.addAll(queryFileNames);
    return setOfFileNames;
    }

    /**
     * This method checks if a word contains only lower case letters.
     * @param word A String.
     * @return Boolean, true if the word contains only lower case letters, false otherwise.
     */
    private Boolean onlyLowerCase(String word){
        char[] charOfWord = word.toCharArray();
        int counter = 0;
        for(char c : charOfWord){
            if(Character.isLowerCase(c)){
                 counter +=1;
            }
        }
        if(counter == word.length()){
            return true;
        }
        return false;
    }

    /**
     * This method creates the joint words and file names between the
     * Case sensitive data structure and Case Insensitive data structure, which meets certain conditions.
     * The word will contain only letters, and the number of files it is in is at most 4.
     * @return A TreeMap which holds the joint entries from the two data structures.
     */
    protected SortedMap<String ,ArrayList<String>> intersectionMap(){
        SortedMap<String,ArrayList<String>> intersection = new TreeMap<>();
        for(String word : invertedIndexSensitive.keySet()){
            if((onlyLowerCase(word) && (invertedIndexSensitive.get(word).size())<=4)){
                intersection.put(word,invertedIndexSensitive.get(word));
            }
        }
        return intersection;
    }
}