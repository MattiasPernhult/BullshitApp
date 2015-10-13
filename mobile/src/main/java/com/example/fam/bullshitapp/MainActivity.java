package com.example.fam.bullshitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements RecyclerAdapter.ClickListener {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        RecyclerAdapter adapter = new RecyclerAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(View view, int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, SpaceActivity.class);
                break;
            case 1:
                intent = new Intent(this, YodaActivity.class);
                break;
            case 2:
                intent = new Intent(this, GiphyActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is some problem", Toast.LENGTH_SHORT).show();
        }
    }
}
