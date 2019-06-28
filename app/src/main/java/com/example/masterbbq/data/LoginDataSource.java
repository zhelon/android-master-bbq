package com.example.masterbbq.data;

import android.os.AsyncTask;

import com.example.masterbbq.data.model.LoggedInUser;
import com.example.masterbbq.utils.Settings;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    Result.Success success;
    Result.Error error;

    public Result<LoggedInUser> login(String username, String password) {

        try {

            new LoginTask(username, password).execute();
            if(success != null){
                return success;
            }else {
                return error;
            }
            // TODO: handle loggedInUser authentication
            /*LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);*/
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public class LoginTask extends AsyncTask<Void, Void, ResponseEntity<JsonNode>>{
        RestTemplate restTemplate = new RestTemplate();
        String email, password;
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<JsonNode> responseEntity;
        String url = Settings.BASE_URL+Settings.BASE_PORT+Settings.API+Settings.SERVICE_LOGIN;
        String response = "{\"id\":2,\"role_id\":\"1\",\"name\":\"test\",\"email\":\"test@test.com\",\"auth_token\":\"e91a0b4211c05ae1ec8937845a203d1591691d22a02b628e20665a097bb09029\",\"email_verified_at\":null,\"active\":\"1\",\"verify\":\"0\",\"created_at\":\"2019-05-07 04:23:50\",\"updated_at\":\"2019-05-10 15:43:11\",\"role\":{\"id\":2,\"role_name\":\"ADMIN\",\"created_at\":\"2019-05-07 04:23:37\",\"updated_at\":\"2019-05-07 04:23:37\"}}";

        public LoginTask(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected ResponseEntity<JsonNode> doInBackground(Void... voids) {
            String credentials = email+"/"+ password;
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(url+credentials, JsonNode.class);
            return responseEntity;
        }

        @Override
        protected void onPostExecute(ResponseEntity<JsonNode> result) {

            if(result.getStatusCode() == HttpStatus.CREATED){
                Settings.PREFERENCES_EDITOR.putString(Settings.LOGIN, "true");
                Settings.PREFERENCES_EDITOR.commit();

                JsonNode data = result.getBody().get("data");

                LoggedInUser loggedInUser =  new LoggedInUser(data.get("id").toString(),
                        data.get("name").toString(),
                        data.get("auth_token").toString(),
                        data.get("email").toString(),
                        data.get("active").asBoolean(),
                        data.get("verify").asBoolean());
                success =  new Result.Success(loggedInUser);
            }else{
                error = new Result.Error(new IOException("Error logging in"+ result.getBody()));
            }




        }
    }





    public void logout() {
        // TODO: revoke authentication
    }
}
