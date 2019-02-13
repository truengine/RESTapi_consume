package RESTapi_consume;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testExecutedRequests {

	@BeforeAll
	static void setUpBeforeClass() throws IOException {
	}

	@Test
	public void testResponseCode() throws IOException {
	    String POST_URL = "http://dummy.restapiexample.com/api/v1/create";	
	    String params ="{\"name\": \"truengine\",\"salary\": \"99999\",\"age\": \"9999\",\"id\": \"\"}";
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(params.getBytes());
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
		
		Assertions.assertTrue(responseCode == 200);
	}





}
