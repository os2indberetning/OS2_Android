package it_minds.dk.eindberetningmobil_android.views.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.PurposeAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.Purpose;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by Claus on 02-02-2016.
 */
public class PurposeActivity extends ProvidedSimpleActivity {

    private final static int TEXT_INPUT_CODE = 556;

    private ListView lw;

    ArrayList<Purpose> purposes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purpose_select_list);
        lw = getViewById(R.id.purpose_select_list_view);

        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);

        setActionbarBackDisplay(title);
    }

    //We use onResume to update the list every time we return to this activity
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    //Initialize or refresh the list
    private void initList(){
        purposes = MainSettings.getInstance(this).getPurpose();



        if (purposes == null) {
            lw.setVisibility(View.GONE);
            findViewById(R.id.purpose_list_empty_view).setVisibility(View.VISIBLE);
            return;
        } else {
            lw.setVisibility(View.VISIBLE);
            findViewById(R.id.purpose_list_empty_view).setVisibility(View.GONE);
            //Always sort the list
            MainSettings.getInstance(getApplicationContext()).setPurpose(sortList(purposes));
        }

        lw.setAdapter(new PurposeAdapter(this, purposes));
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Increments
                Purpose purpose = purposes.get((int) id);
//                incrementPurposeUse(purposes, purpose);

                Intent i = new Intent();
                i.putExtra(IntentIndexes.DATA_INDEX, purpose.getDescription());
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        /*lw.setLongClickable(true);
        lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Remove from the ArrayList
                purposes.remove(purposes.get((int) id));
                //Save the new ArrayList to SharedPreferences
                MainSettings.getInstance(getApplicationContext()).setPurpose(purposes);

                //Update view and list
                initList();
                return true;
            }
        });*/
    }

    private ArrayList<Purpose> sortList(ArrayList<Purpose> purposes) {
        ArrayList<Purpose> list = purposes;
        Comparator<Purpose> comparator = new Comparator<Purpose>() {
            public int compare(Purpose p1, Purpose p2) {
                return p2.getUses() - p1.getUses(); // Most uses on top
            }
        };

        Collections.sort(list, comparator);
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEXT_INPUT_CODE && data != null) {
            //Check if the user entered anything, else we don't add it
            if (!data.getStringExtra(IntentIndexes.DATA_INDEX).equals("")) {
                Purpose newPurpose = new Purpose(data.getStringExtra(IntentIndexes.DATA_INDEX), 0);
                appendPurposes(newPurpose);
            }
        }
    }

    //Increment and save
    private void incrementPurposeUse(ArrayList<Purpose> purposes, Purpose purpose) {
        purpose.setUses(purpose.getUses()+1);
        MainSettings.getInstance(getApplicationContext()).setPurpose(purposes);
    }

    private void appendPurposes(Purpose purpose) {
        //Get the old list
        ArrayList<Purpose> purposes = MainSettings.getInstance(this).getPurpose();
        if (purposes != null) {
            //Append the new purpose
            purposes.add(purpose);
            MainSettings.getInstance(this).setPurpose(purposes);
        //If first item on list init a new Array
        } else {
            ArrayList<Purpose> newPurp = new ArrayList<>();
            newPurp.add(purpose);
            MainSettings.getInstance(this).setPurpose(newPurp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.purposeactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_new_purpose) {

            //show activity
            Intent intent = new Intent(this, TextInputView.class);
            intent.putExtra(IntentIndexes.TITLE_INDEX, getString(R.string.save));
            startActivityForResult(intent, TEXT_INPUT_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
