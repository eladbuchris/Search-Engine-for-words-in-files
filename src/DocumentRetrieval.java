import java.io.*;
import java.util.ArrayList;
import java.util.SortedMap;

/**
 * The main user class.
 */
public class DocumentRetrieval {

    /**
     * Pass two arguments: 1. The path of the directory of documents, 2. The path of the boolean query file
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        AbstractInvertedIndexFactory caseInsensitiveFactory = new CaseInsensitiveFactory();
        AbstractInvertedIndexFactory caseSensitiveFactory = new CaseSensitiveFactory();
        AbstractInvertedIndex caseInsensitiveIndex = caseInsensitiveFactory.createInvertedIndex();
        AbstractInvertedIndex caseSensitiveIndex = caseSensitiveFactory.createInvertedIndex();
        caseInsensitiveIndex.buildInvertedIndex((new File(args[0])).listFiles()); // Add folder of text files.
        caseSensitiveIndex.buildInvertedIndex( (new File(args[0])).listFiles());

        for (String query : Utils.readLines(new File(args[1]))) {
            System.out.println("######################################");
            System.out.println("Query: " + query);
            System.out.println("----NonCaseSensitiveIndex----");
            Utils.printList(caseInsensitiveIndex.runQuery(query));
            System.out.println("----CaseSensitiveIndex----");
            Utils.printList(caseSensitiveIndex.runQuery(query));
        }
        /********  YOUR CODE FOR PART 2 STARTS HERE ********/
        /**
         * This part writes to file, only the joint word and file names from the two indexes
         */
        FileOutputStream output = new FileOutputStream("intersectionOutput.txt");
        PrintStream outPutFile = new PrintStream(output);
        SortedMap<String, ArrayList<String>> intersection;
        intersection = caseInsensitiveIndex.intersectionMap();
        outPutFile.print("######################################" +"\n" + "----Intersections----" +"\n");
        for (SortedMap.Entry<String, ArrayList<String>> entry : intersection.entrySet()) {
            outPutFile.println(entry.getKey() + " : " + entry.getValue());
        }
        outPutFile.close();
        /********  YOUR CODE FOR PART 2 ENDS HERE ********/
    }
}



