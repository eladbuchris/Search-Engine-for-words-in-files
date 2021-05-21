/**
 * This class handles the needed properties of the CaseInsensitive Object, which wasn't handled
 * in the parent class.
 * It uses a singleton design pattern, to create only one object of the class.
 */
public class CaseInsensitive extends AbstractInvertedIndex{
    private static CaseInsensitive caseInsensitiveInstance = null;
    private CaseInsensitive(){}
    public static CaseInsensitive getInstance(){
        if(caseInsensitiveInstance == null){
            caseInsensitiveInstance = new CaseInsensitive();
            System.out.println("New CaseInsensitive index is created");
        }
        else {
            System.out.println("You already have a CaseInsensitive index");
        }
        return caseInsensitiveInstance;
        }
    }

