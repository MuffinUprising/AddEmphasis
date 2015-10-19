package com.example.casey.addemphasis;

import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmphasisActivity extends AppCompatActivity implements DialogListener {

    //local variables - EditText and Button
    public TextView prompt;
    public EditText statement;
    Button addEmphasis;

    String phrase = "";
    ArrayList<Integer> emphasis;
    String[] emphasisList;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emphasis);
        setEmphasisList();

        emphasis = new ArrayList<>();

        statement = (EditText)findViewById(R.id.emphasis_edit_text_et);
        prompt = (TextView)findViewById(R.id.emphasis_edit_text_tv);

        addEmphasis = (Button)findViewById(R.id.emphasis_button);
        addEmphasis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phrase = statement.getText().toString();
                setPhrase(phrase);

                if(getPhrase().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter a phrase to emphasize", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), phrase + " was entered", Toast.LENGTH_SHORT).show();
                    showList();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emphasis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showList() {
        //bundle and emphasis options
        Bundle dialogArgs = new Bundle();
        dialogArgs.putStringArray("emphasisList", getEmphasisList());

        //new list fragment
        EmphasisDialog dialog = new EmphasisDialog();
        dialog.setArguments(dialogArgs);
        dialog.show(getSupportFragmentManager(), "EmphasisListDialogFragment");

    }

    public void showResult() {
        //bundle up the emphasized phrase
        Bundle bundle = new Bundle();
        bundle.putString("emphasizedPhrase", getPhrase());

        //create new frag, pass on the bundle
        DialogFragment dialog = new EmphasisDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "EmphasisDialog");
    }

    //emphasized the phrase entered by the user
    protected void makeEmphasizedPhrase(ArrayList<Integer> index, String[] emphasis) {
        //string to work with
        String newString = getPhrase();

        //for each selected emphasis in the ArrayList
        for (int i = 0; i < index.size(); i++) {
            //if 0: make phrase ALL CAPS
            if (index.get(i).equals(0)) {
                newString = newString.toUpperCase();
            }
            //if 1: add !!!!!!!!
            if (index.get(i).equals(1)) {
                newString = newString + emphasis[index.get(i)];
            }
            //if 2: add ¯\_(ツ)_/¯
            if (index.get(i).equals(2)) {
                newString = newString + emphasis[index.get(i)];
            }
        }
    }

    //when ok is clicked
    @Override
    public void onListOk(ArrayList<Integer> selected) {
        //populate array of choices
        setArray(selected);
        //construct emphasized phrase
        makeEmphasizedPhrase(selected, emphasisList);
        //show result dialog
        showResult();
    }

    //after results received & ok clicked, reset EditText
    @Override
    public void onResultOk(DialogFragment dialog) {
        statement.setText("");
    }

    public void onCancel(){
        statement.setText("");
    }

    //sets the array of choices
    public void setArray(ArrayList<Integer> array) {
        emphasis = array;
    }

    //getter & setter for phrase
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String setPhrase) {
        phrase = setPhrase;
    }

    protected String[] getEmphasisList() {
        return emphasisList;
    }
    protected void setEmphasisList() {
        emphasisList = getResources().getStringArray(R.array.choices);
    }
}
