/**
 * This class handles the needed properties of the CaseSensitive Object, which wasn't handled
 * in the parent class.
 * It uses a singleton design pattern, to create only one object of the class.
 */
public class CaseSensitive extends AbstractInvertedIndex {
    private static CaseSensitive caseSensitiveInstance = null;
    private CaseSensitive(){}
    public static CaseSensitive getInstance(){
        if(caseSensitiveInstance == null){
            caseSensitiveInstance = new CaseSensitive();
            System.out.println("New CaseSensitive index is created");
        }
        else {
            System.out.println("You already have a CaseSensitive index");
        }
        return caseSensitiveInstance;
        }
    }
