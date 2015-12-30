package everdes.examplepermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String mPhoneNumber;
    private int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtPhone = (EditText) findViewById(R.id.edtPhone);

        Button btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneNumber = edtPhone.getText().toString().trim();

                // Verificando se a aplicacao possui permissao de chamadas telefonicas
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Caso não possua, é solicitado ao usuario que a conceda
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                } else {
                    call();
                }
            }
        });
    }

    // Método invocado logo apos o usuario permitir ou nao o acesso
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verifica se o usuário deu permissao
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            call();
        }
    }

    private void call() {
        Uri uri = Uri.parse("tel:" + mPhoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
