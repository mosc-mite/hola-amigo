package android.developerstrio.com.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ChatView chatView;
    TextView response;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatView = (ChatView) findViewById(R.id.chat_view);
          chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
              @Override
              public boolean sendMessage(final ChatMessage chatMessage){
                  String messageToSend = chatMessage.getMessage().toString();
                  URL messageUrl = NetworkUtils.buildUrl(messageToSend);
                  new MessageAsyncTask().execute(messageUrl);
                  // perform actual message sending
              return true;

          }

          });

      }
    class MessageAsyncTask extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String message = null;
            try {
                message = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && !s.equals("")) {
                JSONArray arr = null;
                try {
                    arr = new JSONArray(s);
                    JSONObject jObj = arr.getJSONObject(0);
                    String message = jObj.getString("message");
                    long timeStamp = jObj.getLong("timestamp");
                    ChatMessage.Type type = ChatMessage.Type.RECEIVED;
                    ChatMessage chatMessage = new ChatMessage(message,timeStamp,type);
                    chatView.addMessage(chatMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(ChatActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
