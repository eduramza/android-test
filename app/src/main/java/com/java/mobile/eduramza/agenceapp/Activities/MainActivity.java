package com.java.mobile.eduramza.agenceapp.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.java.mobile.eduramza.agenceapp.R;

public class MainActivity extends AppCompatActivity {
    //ATRIBUTOS
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private WebView webView;
    private String nome = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando componentes
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_main);

        //Ações do navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Checando se um item tem o estado de checked ou não
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                //Fechando o drawer
                drawerLayout.closeDrawers();

                return menuSelecao(item.getItemId());

            }
        });


        //Configurando Drawer
        drawerLayout = findViewById(R.id.dl_main);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer,
                R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();

        //Iniciando o WebView
        webView = findViewById(R.id.wv_home);
        webView.getSettings().setJavaScriptEnabled(true);

        //Recebendo o usuário da tela de login
        if (getIntent() != null && getIntent().hasExtra("user")){
            nome = getIntent().getExtras().getString("user");
        }

        String html = "<html><body><h1>Seja bem vindo "+nome+"!</h1></body></html>";
        webView.loadData(html, "text/html", "UTF-8");

    }

    /***************************** METODOS ****************************/
    private boolean menuSelecao(int item){
        switch (item){

            case R.id.mn_notificar:
                //Criando a ação de toque
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);

                //Enviar notificação para o celular
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Notification")
                        .setContentText("Agence Test")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pending)
                        .setAutoCancel(true);

                //Mostrando a notificação
                NotificationManagerCompat manager = NotificationManagerCompat.from(this);
                manager.notify(1, builder.build());

                return true;

            case R.id.mn_home:
                webView.loadUrl("https://www.google.com.br/");
                return true;

            case R.id.mn_sair:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();

        }
        return true;
    }
}
