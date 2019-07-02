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



    public Result<LoggedInUser> login(String username, String password) {

        try {

            ResponseEntity<JsonNode> responseEntity =  new LoginTask(username, password).execute().get();

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                //Settings.PREFERENCES_EDITOR.putString(Settings.LOGIN,  null);
                //Settings.PREFERENCES_EDITOR.commit();

                JsonNode data = responseEntity.getBody().get("data");

                LoggedInUser loggedInUser =  new LoggedInUser(data.get("id").toString(),
                        data.get("name").toString(),
                        data.get("auth_token").toString(),
                        data.get("email").toString(),
                        data.get("active").asBoolean(),
                        data.get("verify").asBoolean());
                return new Result.Success(loggedInUser);
            }else{
                return new Result.Error(new IOException("Error logging in"+ responseEntity.getBody()));
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
        String response = "{\"data\":{\"id\":3,\"role_id\":\"1\",\"name\":\"test\",\"type_user_id\":\"2\",\"email\":\"test@test.com\",\"auth_token\":\"4baf773a40eeea34743b21ebf0a0a173260e781322b851f1446cf8c0ab30f00f\",\"email_verified_at\":null,\"active\":\"1\",\"verify\":\"0\",\"created_at\":\"2019-06-25 02:52:27\",\"updated_at\":\"2019-07-02 02:31:17\",\"role\":null}}";

        public LoginTask(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected ResponseEntity<JsonNode> doInBackground(Void... voids) {
            String credentials = email+"/"+ password;
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(url+credentials, JsonNode.class);
            ResponseEntity<JsonNode> responseEntity = null;
            try {
                responseEntity = new ResponseEntity<JsonNode>(new ObjectMapper().readTree(response), HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseEntity;
        }

    }





    public void logout() {
        // TODO: revoke authentication
    }
}
