package its.papsi.beritaonlineb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static its.papsi.beritaonlineb.GlobalVariable.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegistrasi;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegistrasi = findViewById(R.id.tv_registrasi);

        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);

        tvRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistrasi = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(intentRegistrasi);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();

        //validasi dulu
        if (username.trim().isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            utilMessage.showProgressBar();

            StringRequest request= new StringRequest(Request.Method.POST,
                    BASE_URL + "login.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    utilMessage.dismissProgressBar();

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        int status = jsonResponse.getInt("status");
                        String message = jsonResponse.getString("message");

                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (status == 0) {
                            //simpan session loginnya
                            String userId = jsonResponse.getString("data");
                            sessionManager.setUserId(userId);

                            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intentMain);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();

                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        }
    }
}
