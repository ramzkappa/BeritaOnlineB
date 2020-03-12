package its.papsi.beritaonlineb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import its.papsi.beritaonlineb.fragment.AddNewsFragment;
import its.papsi.beritaonlineb.fragment.AllNewsFragment;
import its.papsi.beritaonlineb.fragment.MyNewsFragment;
import its.papsi.beritaonlineb.fragment.SearchNewsFragment;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setPage(menuItem.getItemId());

                return true;
            }
        });

        //check session user
        if (!sessionManager.isLoggedIn()) {
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        }

        //set default page
        setPage(R.id.navigation_semua_berita);
    }

    private void setPage(int itemId) {
        String title = "";
        Fragment fragment = null;

        switch (itemId) {
            case R.id.navigation_semua_berita:
                title = "Semua Berita";
                fragment = new AllNewsFragment();
                break;
            case R.id.navigation_berita_baru:
                title = "Berita Baru";
                fragment = new AddNewsFragment();
                break;
            case R.id.navigation_berita_saya:
                title = "Berita Saya";
                fragment = new MyNewsFragment();
                break;
            case R.id.navigation_cari_berita:
                title = "Cari Berita";
                fragment = new SearchNewsFragment();
                break;
        }

        //ubah judul
        MainActivity.this.getSupportActionBar().setTitle(title);

        //pasang fragment
        if (fragment != null) {
            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                sessionManager.clearEditor();
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
