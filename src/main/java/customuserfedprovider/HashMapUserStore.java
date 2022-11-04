package customuserfedprovider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.util.HashMap;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;

public class HashMapUserStore {

	/// TODO: do HttpClient asynchronous keep track of request and response?
	// TODO: having problem to include google gson on maven package or directly on
	/// quay-keycloak doker server
	// TODO: currenlty server is returning name and password with comma separated
	/// string
	// private Gson gson;

	public HashMapUserStore() {
		// gson = new Gson();
	}

	public User getUser(String username) {

		HttpRequest request;
		HttpResponse<String> response = null;
		try {
			request = HttpRequest.newBuilder()
					.uri(new URI("http://host.docker.internal:8100/user-service/credential/" + username)).GET().build();

			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!response.body().isBlank()) {
			String[] results = response.body().split(",");
			// User user = gson.fromJson(result, User.class);
			User user = new User(results[0], results[1]);
			return user;

		} else {
			return null;
		}

	}
}
