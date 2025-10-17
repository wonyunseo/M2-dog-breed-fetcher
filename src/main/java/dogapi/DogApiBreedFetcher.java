package dogapi;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     *
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        final OkHttpClient client = new OkHttpClient();
        final String URL = "https://dog.ceo/api/breed/" + breed + "/list" ;

        final Request request = new Request.Builder()
                .url(URL)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            final JSONObject jsonList = new JSONObject(response.body().string());
            JSONArray breedList = jsonList.getJSONArray("message");
            ArrayList<String> subBreedList = new ArrayList<>();

            for (int i = 0; i < breedList.length(); i++) {
                subBreedList.add(breedList.getString(i));
            }

            return subBreedList;

        } catch (IOException | JSONException e) {
            throw new BreedNotFoundException(breed + "not found");
        }


        // return statement included so that the starter code can compile and run
        // return new ArrayList<>();
    }
}