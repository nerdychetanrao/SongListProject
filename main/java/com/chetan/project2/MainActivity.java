package com.chetan.project2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chetan.project2.SongItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<SongItem> songlist = new ArrayList<SongItem>();
    ArrayAdapter<SongItem> adapter;
    ArrayList<String> remove_list = new ArrayList<>();
    ListView remove_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ArrayList<SongItem> songlist = new ArrayList<SongItem>();
        // Store string resources into an Array
        SongItem s1 = new SongItem("Comfortably Numb","Pink Floyd","https://en.wikipedia.org/wiki/Comfortably_Numb","https://www.youtube.com/watch?v=LTseTg48568","https://en.wikipedia.org/wiki/Pink_Floyd");
        SongItem s2 = new SongItem("Wish You Were Here","Pink Floyd","https://en.wikipedia.org/wiki/Wish_You_Were_Here_(Pink_Floyd_album)","https://www.youtube.com/watch?v=3j8mr-gcgoI","https://en.wikipedia.org/wiki/Pink_Floyd");
        SongItem s3 = new SongItem("Dear God","Avenged Sevenfold","https://en.wikipedia.org/wiki/Avenged_Sevenfold_(album)","https://www.youtube.com/watch?v=mzX0rhF8buo","https://en.wikipedia.org/wiki/Avenged_Sevenfold");
        SongItem s4 = new SongItem("So Far Away","Avenged Sevenfold","https://en.wikipedia.org/wiki/So_Far_Away_(Avenged_Sevenfold_song)","https://www.youtube.com/watch?v=A7ry4cx6HfY","https://en.wikipedia.org/wiki/Avenged_Sevenfold");
        SongItem s5 = new SongItem("Sweet Child O' Mine","Guns 'N Roses","https://en.wikipedia.org/wiki/Sweet_Child_o%27_Mine","https://www.youtube.com/watch?v=bRfc_Y_AsLo","https://en.wikipedia.org/wiki/Guns_N%27_Roses");
        songlist.add(s1);
        songlist.add(s2);
        songlist.add(s3);
        songlist.add(s4);
        songlist.add(s5);

        // Locate ListView in listview_main.xml
        listView = (ListView) findViewById(R.id.listview);
        registerForContextMenu(listView);
        adapter = new ArrayAdapter<SongItem>(this,android.R.layout.simple_list_item_2, android.R.id.text1,songlist) {
            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(songlist.get(position).getSongTitle());

                text2.setText(songlist.get(position).getBandName());

                return view;
            }

        };


        listView.setAdapter(adapter);

        // Capture ListView item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String value = songlist.get(position).getSongTitle();
                Toast.makeText(getApplicationContext(), "Opening Video "+value, Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("video_url",songlist.get(position).getVideoUrl() );
                startActivity(i);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listview) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_list, menu);

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch(item.getItemId()) {
            case R.id.view_video:
                Toast.makeText(getApplicationContext(), "Opening Video", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("video_url",songlist.get(index).getVideoUrl() );
                startActivity(i);
                return true;
            case R.id.view_songs_wiki:
                Toast.makeText(getApplicationContext(), "Opening Song's Wiki Page", Toast.LENGTH_SHORT).show();
                Intent j = new Intent(MainActivity.this, WebViewActivity.class);
                j.putExtra("wiki_url",songlist.get(index).getWikiUrl() );
                startActivity(j);
                return true;
            case R.id.view_bands_wiki:
                Toast.makeText(getApplicationContext(), "Opening Band's Wiki Page", Toast.LENGTH_SHORT).show();
                Intent k = new Intent(MainActivity.this, WebViewActivity.class);
                k.putExtra("wiki_url",songlist.get(index).getBandWiki());
                startActivity(k);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        int init_val=10;
        SubMenu delete_sub_menu = menu.findItem(R.id.delete_song).getSubMenu();
        delete_sub_menu.clear();
        for(int i = 0;i < songlist.size();i++) {
            delete_sub_menu.add(0,init_val+i, Menu.NONE, songlist.get(i).getSongTitle().toString());
        }
            /* Add the menu items */

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add_song:
                add_song_to_list();
                return true;
            case R.id.delete_song:
               //Toast.makeText(getApplicationContext(), "in delete", Toast.LENGTH_SHORT).show();
                //remove_song_from_list();
                return true;
            case R.id.exit_app:
                this.finish();
                System.exit(0);
            default:
                int position = id-10;
                remove_song_from_list(position);
                return super.onOptionsItemSelected(item);
        }

    }



    public void add_song_to_list(){
        //songlist.add(new SongItem("Wish YOu were here","Pink Folyd","xya","xyz"));
        //adapter.notifyDataSetChanged();
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_song, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        final EditText songTitle = (EditText) promptsView.findViewById(R.id.enter_song_title);
        final EditText bandName = (EditText) promptsView.findViewById(R.id.enter_band_name);
        final EditText wikilink = (EditText) promptsView.findViewById(R.id.enter_wiki_link);
        final EditText videolink = (EditText) promptsView.findViewById(R.id.enter_video_link);
        final EditText bandwikilink = (EditText) promptsView.findViewById(R.id.enter_band_wiki_link);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                songlist.add(new SongItem(songTitle.getText().toString(),bandName.getText().toString(),wikilink.getText().toString(),videolink.getText().toString(),bandwikilink.getText().toString()));
                                adapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public void remove_song_from_list(int position){
        Toast.makeText(getApplicationContext(), "Deleted "+songlist.get(position).getSongTitle().toString(), Toast.LENGTH_SHORT).show();
        songlist.remove(position);
        adapter.notifyDataSetChanged();
    }
}
