package com.conga.qa.scenario3;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ScenarioThreeTestCase {

	public final String url = "http://api.openweathermap.org/data/3.0/stations";
	public final String contentType = "application/json";
	public final String apiKey = "9ab90c6f2a2033119f5775cbc097256e";
	public Map<String, Object> station1 = new HashMap<String, Object>();
	public Map<String, Object> station2 = new HashMap<String, Object>();
	public static String station1id = "";
	public static String station2id = "";

	public ScenarioThreeTestCase() {

		station1.put("external_id", "DEMO_TEST001");
		station1.put("name", "Interview Station 1");
		station1.put("latitude", 33.33);
		station1.put("longitude", -111.43);
		station1.put("altitude", 444);

		station2.put("external_id", "Interview1");
		station2.put("name", "Interview Station 2");
		station2.put("latitude", 33.44);
		station2.put("longitude", -12.44);
		station2.put("altitude", 444);
	}

	@Test(priority = 1)
	public void registerStationWithoutAPIKey() {

		Response response = given().contentType(contentType).accept(contentType).body(station1).when().post(url).then()
				.statusCode(401).contentType(contentType).extract().response();

		int responseCode = response.getStatusCode();
		String errorMsg = response.jsonPath().getString("message");

		Assert.assertEquals(responseCode, 401);
		Assert.assertEquals(errorMsg,
				"Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.");
	}

	@Test(priority = 2)
	public void registerStationsWithAPIKeyScenario3() {

		Response station1Response = given().contentType(contentType).accept(contentType).body(station1).when()
				.post(url + "?appId=" + apiKey).then().statusCode(201).contentType(contentType).extract().response();

		int responseCode1 = station1Response.getStatusCode();

		Response station2Response = given().contentType(contentType).accept(contentType).body(station2).when()
				.post(url + "?appId=" + apiKey).then().statusCode(201).contentType(contentType).extract().response();

		int responseCode2 = station2Response.getStatusCode();

		Assert.assertEquals(responseCode1, 201);
		Assert.assertEquals(responseCode2, 201);
	}

	@Test(priority = 3)
	public void validateStations() {

		Response response = get(url + "?appId=" + apiKey);

		int responseCode = response.getStatusCode();

		boolean station1Status = false;
		boolean station2Status = false;

		List<HashMap<String, Object>> stationDetails = response.jsonPath().getList("$");

		for (HashMap<String, Object> stationDetail : stationDetails) {
			if (stationDetail.get("external_id").equals(station1.get("external_id"))) {
				if (stationDetail.get("name").equals(station1.get("name"))
						&& stationDetail.get("latitude").toString().equals(station1.get("latitude").toString())
						&& stationDetail.get("longitude").toString().equals(station1.get("longitude").toString())
						&& stationDetail.get("altitude").toString().equals(station1.get("altitude").toString())) {
					station1Status = true;
				}
			}

			if (stationDetail.get("external_id").equals(station2.get("external_id"))) {
				if (stationDetail.get("name").equals(station2.get("name"))
						&& stationDetail.get("latitude").toString().equals(station2.get("latitude").toString())
						&& stationDetail.get("longitude").toString().equals(station2.get("longitude").toString())
						&& stationDetail.get("altitude").toString().equals(station2.get("altitude").toString())) {
					station2Status = true;
				}
			}
		}

		Assert.assertEquals(responseCode, 200);
		Assert.assertEquals(station1Status, true);
		Assert.assertEquals(station2Status, true);
	}

	@Test(priority = 4)
	public void deleteAllRegisteredStations() {

		Response allStationsResponse = get(url + "?appId=" + apiKey);

		List<HashMap<String, Object>> stationDetails = allStationsResponse.jsonPath().getList("$");

		for (HashMap<String, Object> stationDetail : stationDetails) {
			if (stationDetail.get("external_id").equals(station1.get("external_id"))) {
				station1id = stationDetail.get("id").toString();
			}

			if (stationDetail.get("external_id").equals(station2.get("external_id"))) {
				station2id = stationDetail.get("id").toString();
			}
		}

		Response responseStation1 = given().contentType(contentType).accept(contentType).when()
				.delete(url + "/" + station1id + "?appId=" + apiKey).then().statusCode(204).extract().response();

		Response responseStation2 = given().contentType(contentType).accept(contentType).when()
				.delete(url + "/" + station2id + "?appId=" + apiKey).then().statusCode(204).extract().response();

		int responseCode1 = responseStation1.getStatusCode();
		int responseCode2 = responseStation2.getStatusCode();

		Assert.assertEquals(responseCode1, 204);
		Assert.assertEquals(responseCode2, 204);
	}

	@Test(priority = 5)
	public void validateStationsPostDeletion() {

		Response deleteStation1 = get(url + "/" + station1id + "?appId=" + apiKey);
		Response deleteStation2 = get(url + "/" + station1id + "?appId=" + apiKey);

		int responseCode1 = deleteStation1.getStatusCode();
		int responseCode2 = deleteStation2.getStatusCode();

		String errorMsg1 = deleteStation1.jsonPath().getString("message");
		String errorMsg2 = deleteStation2.jsonPath().getString("message");

		Assert.assertEquals(responseCode1, 404);
		Assert.assertEquals(responseCode2, 404);
		Assert.assertEquals(errorMsg1, "Station not found");
		Assert.assertEquals(errorMsg2, "Station not found");
	}

}
