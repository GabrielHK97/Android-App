package com.example.gabriel.conversor;

//http://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
//http://stackoverflow.com/questions/13194081/how-to-open-a-second-activity-on-click-of-button-in-android-app
//http://stackoverflow.com/questions/14206768/how-to-check-if-a-string-is-numeric
//http://stackoverflow.com/questions/4858026/android-alternate-layout-xml-for-landscape-mode
//http://stackoverflow.com/questions/17667059/how-to-add-vertical-scroll-bars-to-view-in-android
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private String[] simbolos = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H",
                              "I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int opb = 1,opo = 1,oph = 1;

    private EditText tNumero,tBaseOriginal,tBaseDesejada,tResultado;
    private Button btConverter,btBinario,btOctal,btHexadecimal;

    protected static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void Info(View view){
        Intent intent = new Intent(this, InformacoesActivity.class);
        startActivity(intent);
    }

    public void Creditos(View view){
        Intent intent = new Intent(this, CreditosActivity.class);
        startActivity(intent);
    }

    protected int ValidaNumero(String numero,String baseOriginal,String baseDesejada) {

        int erro = 0;

        if(!numero.matches("") && !baseOriginal.matches("") && !baseDesejada.matches("")) {
            if(Integer.parseInt(baseOriginal) > 1 && Integer.parseInt(baseOriginal) < 37 &&
               Integer.parseInt(baseDesejada) > 1 && Integer.parseInt(baseDesejada) < 37) {
                for (int i = 0; i < numero.length(); i++) {
                    for (int j = Integer.parseInt(baseOriginal); j < simbolos.length; j++) {
                        if (numero.substring(i, i + 1).toUpperCase().equals(simbolos[j])) {
                            erro++;
                        }
                    }
                }
            }else{
                erro = 1;
            }
        }else{
            erro = 1;
        }
        return erro;
    }

    protected String BaseParaDecimal(String numero,String baseOriginal) {

        int num = 0,pos = 0;

        for(int i = 0;i<numero.length();i++) {
            if(isNumeric(numero.substring(numero.length()-i-1,numero.length()-i))) {
                pos = Integer.parseInt(numero.substring(numero.length()-i-1,numero.length()-i));
            }else{
                for(int j = 10;j<simbolos.length;j++) {
                    if(numero.substring(numero.length()-i-1,numero.length()-i).toUpperCase().equals(simbolos[j])) {
                        pos = j;
                    }
                }
            }
            num += pos*(int)Math.pow(Integer.parseInt(baseOriginal),i);
        }

        String numeroConvertido = Integer.toString(num);

        return numeroConvertido;
    }

    protected String DecimalParaBase(String numero, String baseDesejada) {

        String numeroConvertido = "";
        int num = Integer.parseInt(numero);

        if(num >= Integer.parseInt(baseDesejada)) {
            while (num >= Integer.parseInt(baseDesejada)) {
                if (Integer.parseInt(numero) % Integer.parseInt(baseDesejada) < 10) {
                    numeroConvertido += Integer.toString(num % Integer.parseInt(baseDesejada));
                } else {
                    numeroConvertido += simbolos[num % Integer.parseInt(baseDesejada)];
                }
                num = (int)(num / Integer.parseInt(baseDesejada));
            }

            numeroConvertido += Integer.toString(num);
            numeroConvertido = new StringBuffer(numeroConvertido).reverse().toString();
        }else{
            numeroConvertido = simbolos[num];
        }
        return numeroConvertido;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tNumero = (EditText)findViewById(R.id.tNumero);
        tBaseOriginal = (EditText)findViewById(R.id.tBaseOriginal);
        tBaseDesejada = (EditText)findViewById(R.id.tBaseDesejada);
        tResultado = (EditText)findViewById(R.id.tResultado);

        btConverter = (Button)findViewById(R.id.btConverter);
        btBinario = (Button)findViewById(R.id.btBinario);
        btOctal = (Button)findViewById(R.id.btOctal);
        btHexadecimal = (Button)findViewById(R.id.btHexadecimal);


        btConverter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String numero = tNumero.getText().toString();
                String baseOriginal = tBaseOriginal.getText().toString();
                String baseDesejada = tBaseDesejada.getText().toString();

                //if(!numero.matches("") && !baseOriginal.matches("") && !baseDesejada.matches("")) {

                    if (ValidaNumero(numero,baseOriginal,baseDesejada) == 0) {
                        String resultado = BaseParaDecimal(numero, baseOriginal);
                        resultado = DecimalParaBase(resultado, baseDesejada);
                        tResultado.setText(resultado);
                    } else {
                        tResultado.setText("Dados inválidos!");
                    }
                /*}else{
                    tResultado.setText("Dados inválidos!");
                }*/
            }
        });

        btBinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opb == 1){
                    tBaseOriginal.setText("10");
                    tBaseDesejada.setText("2");
                    opb = 2;
                }else{
                    tBaseOriginal.setText("2");
                    tBaseDesejada.setText("10");
                    opb = 1;
                }
                btConverter.callOnClick();
            }
        });

        btOctal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opo == 1){
                    tBaseOriginal.setText("10");
                    tBaseDesejada.setText("8");
                    opo = 2;
                }else{
                    tBaseOriginal.setText("8");
                    tBaseDesejada.setText("10");
                    opo = 1;
                }
                btConverter.callOnClick();
            }
        });

        btHexadecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oph == 1){
                    tBaseOriginal.setText("10");
                    tBaseDesejada.setText("16");
                    oph = 2;
                }else{
                    tBaseOriginal.setText("16");
                    tBaseDesejada.setText("10");
                    oph = 1;
                }
                btConverter.callOnClick();
            }
        });

    }
}
