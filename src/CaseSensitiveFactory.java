/**
 * This class made to create an CaseSensitive object.
 */
public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * This method calls for the getInstance method of the CaseSensitive class,
     * Which will create a new CaseSensitive Object.
     * @return The created Object.
     */
    public CaseSensitive createInvertedIndex(){
        return CaseSensitive.getInstance();
    }
}
