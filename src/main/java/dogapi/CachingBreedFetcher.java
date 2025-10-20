package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private HashMap<String, List<String>> cacheMap = new HashMap<>();
    private BreedFetcher breedFetcher;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.breedFetcher = fetcher;

    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        List<String> returnList = new ArrayList<>();
        List<String> subBreedList;

        try {
            if  (cacheMap.containsKey(breed)) {
                returnList = cacheMap.get(breed);
            } else {
                subBreedList = breedFetcher.getSubBreeds(breed);
                cacheMap.put(breed, subBreedList);
                callsMade++;
                return subBreedList;
            }
        } catch (Exception e) {
            callsMade++;
            throw new BreedNotFoundException(breed + " not found");
        }
        return returnList;
    }

    public int getCallsMade() {
        return callsMade;
    }
}