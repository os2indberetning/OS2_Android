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
import java.util.Date;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.PurposeAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Purpose;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Used to select a stored purpose or creating a new one
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

    //Initialize the list and set adapter
    private void initList(){
        purposes = MainSettings.getInstance(this).getPurpose();

        if (purposes == null || purposes.size() == 0) {
            lw.setVisibility(View.GONE);
            findViewById(R.id.purpose_list_empty_view).setVisibility(View.VISIBLE);
            return;
        } else {
            lw.setVisibility(View.VISIBLE);
            findViewById(R.id.purpose_list_empty_view).setVisibility(View.GONE);
        }

        PurposeAdapter adapter = new PurposeAdapter(this, purposes);

        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Purpose purpose = purposes.get((int) id);
                purpose.setLastUsed(new Date());
                MainSettings.getInstance(PurposeActivity.this).setPurposes(purposes);
                usePurpose(purpose);
            }
        });

        lw.setLongClickable(true);
        lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                ConfirmationDialog dialog = new ConfirmationDialog(
                        PurposeActivity.this,
                        "Slet formål",
                        "Er du sikker på du vil slette formålet?",
                        "Slet",
                        "Annuller",
                        null,
                        new ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                //Remove from the ArrayList
                                purposes.remove(purposes.get((int) id));
                                //Save the new ArrayList to SharedPreferences
                                MainSettings.getInstance(getApplicationContext()).setPurposes(purposes);

                                //Update view and list
                                initList();
                            }

                            @Override
                            public void onError(Exception error) {
                                //Do nothing
                            }
                        });
                dialog.showDialog();

                return true;
            }
        });
    }

    private void usePurpose(Purpose purpose){
        Intent i = new Intent();
        i.putExtra(IntentIndexes.DATA_INDEX, purpose.getDescription());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEXT_INPUT_CODE && data != null) {
            //Check if the user entered anything, else we don't add it
            if (!data.getStringExtra(IntentIndexes.DATA_INDEX).equals("")) {
                Purpose newPurpose = new Purpose(data.getStringExtra(IntentIndexes.DATA_INDEX), new Date());
                appendPurposes(newPurpose);
            }
        }
    }

    private void appendPurposes(Purpose purpose) {
        //Get the old list
        ArrayList<Purpose> purposes = MainSettings.getInstance(this).getPurpose();
        if (purposes != null) {
            //Append the new purpose
            if(purposes.contains(purpose)){
                purposes.set(purposes.indexOf(purpose), purpose);
            }else{
                purposes.add(purpose);
            }
            MainSettings.getInstance(this).setPurposes(purposes);
        //If first item on list init a new Array
        } else {
            ArrayList<Purpose> newPurp = new ArrayList<>();
            newPurp.add(purpose);
            MainSettings.getInstance(this).setPurposes(newPurp);
        }

        usePurpose(purpose);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
