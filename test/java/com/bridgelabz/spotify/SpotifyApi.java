package com.bridgelabz.spotify;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class SpotifyApi {
	
	public String token;
	String user_id = "tx9me6zidf344x9sbteqkqwff";
	String playlist_id = "0zkOM0C58Rz7rhXfSfEuFA";
	String track;
	@BeforeTest
	public void getToken() {
		token = "Bearer BQCuPwIuC0SdMuHmvmoCAl78N-rCYpI1hHyIAI4LydxNmsfoJJfmbgA7-MrWaOXju43pSwX1GKminQIRP09Hjz7iikVAOth2XPgmJYqgRqHwTLUYEAooNFIcNoU7OD8l5UZr0xxIKjyjGUiI0O1kgjCFX-yM2Rbny-ao1SmN1T-7a3i1__iIQWHTES5IBqK8v5b07J_t538aV86HDrrYlZ4-VdcfKkQaLMEjYhAuqbbOcrMid1o8-a3I0R5JhQpSPCwbaWcuelv6PYYSIXvFK9WC--4WXYSYIoavEFlT7xQ-R1fBrThkg5492zEuRoCK0i8";
	}
	@BeforeTest
	public void getTrack() {
		track = "spotify:track:2Kja7rWFWrg3GcMQS3hyFN";
	}
	
	
	@Test(priority = 0)
	public void testGet_CurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 1)
	public void testGet_Users_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/users/" + user_id +"/");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 2)
	public void testCreate_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Sorry Playlist\",\r\n"
									+ "  \"description\": \"New playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.post("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
		//playListId = response.path("id");
	}
	
	@Test(priority = 3)
	public void testGet_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4)
	public void testGet_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 5)
	public void testGetCurrent_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 6)
	public void testChange_Playlist_Details() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Rani Playlist \",\r\n"
									+ "  \"description\": \"Updated playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.put("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 7)
	public void testAdd_Items_to_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParams("uris", track)
							.when()
							.post("https://api.spotify.com/v1/playlists/"+playlist_id+"/track");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	
	@Test(priority = 8)
	public void testGet_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 9)
	public void testRemove_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
						.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\"track\":[{\"uris\":\"spotify:track:2Kja7rWFWrg3GcMQS3hyFN\"}]}")
							.when()
							.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 10)
	public void testUpdate_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"range_start\": 1,\r\n"
									+ "  \"insert_before\": 3,\r\n"
									+ "  \"range_length\": 2\r\n"
									+ "}")
							.when()
							.put("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 11)
	public void testGet_Playlist_Cover_image() {
		Response response = RestAssured.given().contentType(ContentType.JSON)							.accept(ContentType.JSON)
							.header("Authorization", token)
							//.body("{\"track\":[{\"uri\":\"spotify:track:0dnDTvdUco2UbaBjUtPxNS\"}]}"))
							.when()
							.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 12)
	public void testSearch_for_Item() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParam("q", "Arijit Singh")
							.queryParam("type", "track")
							.when()
							.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 13)
	public void testGet_Tracks_Audio_Analysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)						
							.when()
							.get("	https://api.spotify.com/v1/audio-analysis/"+track+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 14)
	public void testGet_Tracks_Audio_Features_for_a_Track() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)						
							.when()
							.get("https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 15)
	public void testGet_Tracks_Audio_Features_for_a_Several() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)						
							.when()
							.get("https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 16)
	public void testGet_Several_Tracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)						
							.when()
							.get("https://api.spotify.com/v1/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 17)
	public void test_Get_Track() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)						
							.when()
							.get("	https://api.spotify.com/v1/tracks/track");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
}
