package br.com.willtrkapp.execpa1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random geradorRandomico;
    private Button comecarButton;
    private Button sortearButton;
    private Button finalizarButton;
    private TextView headerResultadoTextView;
    private TextView resultadoTextView;
    private ArrayList<Integer> pedrasSorteadas = new ArrayList();
    private Boolean jogoIniciado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geradorRandomico = new Random(System.currentTimeMillis());

        comecarButton = findViewById(R.id.comecarButton);
        sortearButton = findViewById(R.id.sortearButton);
        finalizarButton = findViewById(R.id.finalizarButton);
        headerResultadoTextView = findViewById(R.id.headerResultadotextView);
        resultadoTextView = findViewById(R.id.resultadoTextView);

        sortearButton.setVisibility(View.GONE);
        finalizarButton.setVisibility(View.GONE);
        headerResultadoTextView.setVisibility(View.GONE);
        resultadoTextView.setVisibility(View.GONE);
    }

    public void comecarClick(View pView)
    {
        comecarButton.setVisibility(View.GONE);
        sortearButton.setVisibility(View.VISIBLE);
        finalizarButton.setVisibility(View.VISIBLE);
        headerResultadoTextView.setVisibility(View.VISIBLE);
        resultadoTextView.setVisibility(View.VISIBLE);

        jogoIniciado = true;
        headerResultadoTextView.setText("Nenhuma pedra sorteada ainda.");
    }

    public void finalizarClick(View pView)
    {
        //Variáveis
        pedrasSorteadas.clear();
        resultadoTextView.setText("");
        jogoIniciado = false;


        //Tela
        comecarButton.setVisibility(View.VISIBLE);
        sortearButton.setVisibility(View.GONE);
        finalizarButton.setVisibility(View.GONE);
        headerResultadoTextView.setVisibility(View.GONE);
        resultadoTextView.setVisibility(View.GONE);
    }

    public void sortearClick(View pView)
    {
        headerResultadoTextView.setText("Pedras sorteadas:");
        sortear();
    }


    private void sortear()
    {
        if(pedrasSorteadas.size() < 75)
        {
            int pedra;
            do {
                pedra = geradorRandomico.nextInt(75) + 1;
            }while (pedrasSorteadas.contains(pedra) && pedrasSorteadas.size() < 75);

            pedrasSorteadas.add(pedra);

            String currentText = resultadoTextView.getText().toString();
            if(currentText == null || currentText.equals(""))
                resultadoTextView.setText(String.format("%02d", pedra));
            else
                resultadoTextView.setText(currentText + ", " + String.format("%02d", pedra));
        }
        else
            Toast.makeText(this, "Todas as 75 pedras já foram sorteadas.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //SALVAR OS DADOS DE ESTADO DINÂMICO
        outState.putIntegerArrayList("pedras", pedrasSorteadas);
        outState.putBoolean("iniciado", jogoIniciado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            pedrasSorteadas = savedInstanceState.getIntegerArrayList("pedras");
            jogoIniciado = savedInstanceState.getBoolean("iniciado");

            if(pedrasSorteadas.size() > 0 || jogoIniciado)
                comecarClick(null);

            if(pedrasSorteadas.size() > 0)
            {
                String strResultado = "";
                for(int i = 0; i< pedrasSorteadas.size(); i++)
                    strResultado = strResultado + String.format("%02d", pedrasSorteadas.get(i)) + ", ";

                headerResultadoTextView.setText("Pedras sorteadas:");
                resultadoTextView.setText(strResultado.substring(0, strResultado.length() - 2));
            }
        }
    }
}
