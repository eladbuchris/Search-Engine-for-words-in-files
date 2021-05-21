/**
 * This class made to create an CaseInsensitive object.
 */
public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * This method calls for the getInstance method of the CaseInsensitive class,
     * Which will create a new CaseInsensitive Object.
     * @return The created Object.
     */
    CaseInsensitive createInvertedIndex(){
        return CaseInsensitive.getInstance();
    }
}
