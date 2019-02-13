package RESTapi_consume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExecuteRequests {

	// this String variable will store the employee ID we capture from POST response
	// we will then use the employee ID to put/update and delete the created record
	public static String employeeID;

	// this is the API URL for creating new records
	private static final String POST_URL = "http://dummy.restapiexample.com/api/v1/create";	
	
	// these are the key value pairs that will be sent via POST to create a record
	private static final String params ="{\"name\": \"truengine\",\"salary\": \"99999\",\"age\": \"9999\",\"id\": \"\"}";
	
	// these are the key value pairs that will be sent via PUT to update salary of a record
	private static final String updatedParams ="{\"name\": \"truengine\",\"salary\": \"99991\",\"age\": \"9999\"}";

//===============================================================================================
	// we will execute all requests in a sequence from the main method
	public static void main(String[] args) throws IOException {
		
		sendPOST(); // create record
		System.out.println("POST DONE"); 
		sendGET(); // return record
		System.out.println("GET DONE"); 
		sendPUT(); // update record (salary)
		System.out.println("PUT DONE");
		sendDELETE(); // delete record
		System.out.println("DELETE DONE");
	}
//===============================================================================================
	// POST method to be called by main method
	public static void sendPOST() throws IOException {
		
		// create a URL object that for which we open a connection of type HTTP and set request method to POST
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		// this allows us to open a stream that can write/POST via the connection
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		// parameters are written to connection output stream
		os.write(params.getBytes());
		// output stream parameters are written to destination
		os.flush();
		// stream closes
		os.close();

		// get connection response code
		int responseCode = con.getResponseCode();
		// show response code in console
	//	System.out.println("POST Response Code :: " + responseCode);
		// if response code is 200, we are POSTing in accordance with API
		if (responseCode == HttpURLConnection.HTTP_OK) {
			
			// this reads input from HTTP connection
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			// create a variable to store data from destination
			String inputLine;
			// store response data
			StringBuffer response = new StringBuffer();
			// capture response data
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// close BufferedReader
			in.close();

		// convert POST response to string and show in console
		System.out.println(response.toString());
		// capture employee 'id' from POST response
		employeeID = response.toString().substring(56,61); 
		// show id in console
		System.out.println(employeeID);
		} else {
			System.out.println("POST request failed"); // if response error
		}
	}
	
	//======================================
	// GET method
	public static void sendGET() throws IOException {
		
		// we want to go to the newly created record's URL via the employee id captured from POST response
		String newURL = "http://dummy.restapiexample.com/api/v1/employee/" + employeeID ;
		URL obj = new URL(newURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
	//	System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
			con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("GET request failed");
		}

	}
//================================================================================================
	
	
	public static void sendPUT() throws IOException {
		// we want to access the newly created and verified existent record via employee id captured in POST response
		String putURL = "http://dummy.restapiexample.com/api/v1/update/" + employeeID ;
		
		URL obj = new URL(putURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");
		// throws an error if content type header not included
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		// we use a different set of parameters with updated salary
		os.write(updatedParams.getBytes());
		os.flush();
		os.close();

		int responseCode = con.getResponseCode();
//		System.out.println("PUT Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in2 = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in2.readLine()) != null) {
				response.append(inputLine);
			}
			in2.close();

			System.out.println(response.toString());		
		} else {
			System.out.println("PUT request failed");
		}
	}
	
//================================================================================================================	
	public static void sendDELETE() throws IOException {
		String deleteURL = "http://dummy.restapiexample.com/api/v1/delete/" + employeeID ;
		URL obj = new URL(deleteURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("DELETE");

		int responseCode = con.getResponseCode();
	//	System.out.println("DELETE Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in2 = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in2.readLine()) != null) {
				response.append(inputLine);
			}
			in2.close();

			System.out.println(response.toString());
		
		} else {
			System.out.println("DELETE request failed");
		}
	}
	
	
}
