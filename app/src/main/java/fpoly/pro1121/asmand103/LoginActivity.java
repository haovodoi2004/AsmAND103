package fpoly.pro1121.asmand103;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout ed_email ,ed_password;
    private Button btn_login;
    private FirebaseAuth mAuth;
    private TextView txt_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        txt_signup = findViewById(R.id.txt_signup);
        btn_login.setOnClickListener(v->{
            String email = ed_email.getEditText().getText().toString();
            String password = ed_password.getEditText().getText().toString();
            mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this , task -> {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this ,MainActivity.class));
                }
            });
        });
        txt_signup.setOnClickListener(v ->{
            startActivity(new Intent(LoginActivity.this ,SignupActivity.class));
        });
    }
}