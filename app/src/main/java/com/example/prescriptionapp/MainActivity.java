package com.example.prescriptionapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text Written Successfully!";
    public static final String Write_Error = "Error during Writing, Try Again!";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView edit_message;
    TextView nfc_contents;
    Button ActivateButton;
    private TextToSpeech tts;
    String file1, file2, file3, file4;
//    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        file1 = readfromFile("drugs0.txt");
        file2 = readfromFile("drugs1.txt");
        file3 = readfromFile("drugs2.txt");
        file4 = readfromFile("drugs3.txt");
        /*
        if(fuser == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
            setContentView(R.layout.activity_main);
        }
        else {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new scanFragment()).commit();
        setContentView(R.layout.activity_main);
        }
        */
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new scanFragment()).commit();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "For help Email: blaked.poulson@calbaptist.edu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //edit_message = (TextView) findViewById(R.id.edit_message);
        nfc_contents = (TextView) findViewById(R.id.nfc_contents);

        //ActivateButton =  findViewById(R.id.ActivateButton);
        context = this;
        /*
        ActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (myTag == null) {
                        Toast.makeText(context, Error_Detected, Toast.LENGTH_LONG).show();
                    } else {
                        write(edit_message.getText().toString(), myTag);
                        Toast.makeText(context, Write_Success, Toast.LENGTH_LONG).show();
                    }
                }catch (IOException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

         */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //TODO!!!
        if (nfcAdapter == null) {
            Toast.makeText(this, "This Device does not support NFC", Toast.LENGTH_SHORT).show();
            finish();
        }
        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[] {tagDetected};
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.US);
                //textToSpeech.setSpeechRate();
            }
        });


        getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void loginButton (View v) {
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new scanFragment()).commit();
    }

    public void goHome (View v) {
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new scanFragment()).commit();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    public void goDrugs (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
    }

    public void goRegister (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegisterFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
    }

    public void goPrescriptions (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PrescriptionLogFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
    }

    /*
    public void onScan (View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SecondFragment()).commit();
    }
     */

    public void TextToSpeechButton(View v) {
        if (nfc_contents.getText().toString() != "") {
            tts.speak(nfc_contents.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        } else {
            tts.speak("Nothing has been scanned", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    int i = 0;

    public void SaveButton (View v) {
        String fileName = "drugs.txt";
        if (readfromFile(fileName) != "" && i < 4) {
            fileName = "drugs" + i + ".txt";
            i++;
            writeToFile(fileName, nfc_contents.getText().toString());
        } else {
            fileName = "drugs0.txt";
            writeToFile(fileName, nfc_contents.getText().toString());
        }
        file1 = readfromFile("drugs0.txt");
        file2 = readfromFile("drugs1.txt");
        file3 = readfromFile("drugs2.txt");
        file4 = readfromFile("drugs3.txt");
    }

    public void writeToFile (String fileName, String content) {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Wrote to File:" + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readfromFile(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int)readFrom.length()];
        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private void readfromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new scanFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
        }
    }


    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            text = new String(payload, languageCodeLength + 1, payload.length -languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
        nfc_contents.setText(text);
    }


    /*
    private void write (String text, Tag tag)  throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }



    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }
*/
    @Override
    protected void onNewIntent (Intent intent) {
        super.onNewIntent (intent);
        setIntent(intent);
        readfromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }

    private void WriteModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null);
    }

    private void WriteModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

}