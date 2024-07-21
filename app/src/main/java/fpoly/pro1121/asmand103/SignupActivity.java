package fpoly.pro1121.asmand103;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout ed_newEmail ,ed_newPassword ,ed_confirmPassword;
    private Button btn_signup;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        img=findViewById(R.id.ba);
        btn_signup = findViewById(R.id.btn_signup);
        ed_newEmail = findViewById(R.id.ed_newEmail);
        ed_newPassword = findViewById(R.id.ed_newPassword);
        ed_confirmPassword = findViewById(R.id.ed_confirmPassword);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_signup.setOnClickListener(v ->{
            String email = ed_newEmail.getEditText().getText().toString();
            String password = ed_newPassword.getEditText().getText().toString();
            String confPass = ed_confirmPassword.getEditText().getText().toString();
            if (password.equals(confPass)){
                mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(this , task -> {
                   if (task.isSuccessful()){
                       FirebaseUser user = mAuth.getCurrentUser();
                       Toast.makeText(this, "đăng kí thành công", Toast.LENGTH_SHORT).show();
                       finish();

                   }else{
                       Toast.makeText(this, "đăng kí ko thành công", Toast.LENGTH_SHORT).show();
                   }
                });
            }else {
                Toast.makeText(this, "Mật khẩu xác nhận ko ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}