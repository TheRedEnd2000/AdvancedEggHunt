package de.theredend2000.advancedegghunt.util;

import de.theredend2000.advancedegghunt.Main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Checker {

    private static final String API_BASE_URL = "https://discord.com/api/v9";
    private static final String GUILD_ID = "1247918692436676730";
    private static final String USER_ID = "1187069106327916655";
    private static final String TOKEN = "TOKEN";

    public Checker(){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/guilds/" + GUILD_ID + "/members/" + USER_ID))
                .header("Authorization", "Bot " + TOKEN)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Main.getInstance().getLogger().warning("The user is on the server.");
            } else if (response.statusCode() == 404) {
                Main.getInstance().getLogger().warning("The user is not on the server.");
            } else {
                Main.getInstance().getLogger().warning("Error getting user presence. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            Main.getInstance().getLogger().warning("Error getting user presence: " + e.getMessage());
        }
    }
}
