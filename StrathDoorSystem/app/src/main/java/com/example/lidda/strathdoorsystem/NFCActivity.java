package com.example.lidda.strathdoorsystem;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NFCActivity extends AppCompatActivity {

    private static final String TAG = "NFCActivity";
    private String uidString;
    private NfcAdapter mAdapter;
    private boolean backPressedOnce, exiting, intentInLastSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        backPressedOnce = false;
        exiting = false;
        intentInLastSec = false;
        //Get the value of uidString from the login
        Bundle b = getIntent().getExtras();
        if(!b.isEmpty()){
            uidString = b.getString("EXTRA_UID");
        }

        mAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mAdapter);
    }

    @Override
    public void onPause(){
        super.onPause();
        stopForegroundDispatch(this, mAdapter);
    }
    @Override
    public void onBackPressed() {
        //If back has been pressed in the last second, exit the app
        if (backPressedOnce){
            exiting = true;
            finish();
        }
        //If not, set the varable and run a handler in a second to set it back
        //to false
        backPressedOnce = true;
        Toast.makeText(this, "Double click BACK to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, 1000);
    }


    @Override
    public void onDestroy(){
        //If the method is called by exiting and not by logging out
        if(exiting){
            //Reads the currently saved uidString
            String s =FileSaver.loadLogin(getApplicationContext());
            //If there's none, save this one
            if(s==null){
                FileSaver.saveLogin(getApplicationContext(), uidString);
            //If it's not the current user, delete it and save this one
            } else if(!s.equals(uidString)){
                //Something has gone wrong - just keep the current user logged in
                FileSaver.delete(getApplicationContext());
                FileSaver.saveLogin(getApplicationContext(),uidString);
            }
            //If they've closed the app while logged in before do nothing - it's already saved

        }
        super.onDestroy();
    }

    /**
     * Called by pressing the Log out button - Logs the user out
     * @param view a view object of the log out button
     */
    public void backToLogin(View view){
        FileSaver.delete(getApplicationContext());
        uidString = null;
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent){
        Toast toast;
        if(!intentInLastSec) {
            if (tagRW(intent)) {
                toast = Toast.makeText(getApplicationContext(), "Successfuly Sent!", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "Failed to send. Try again.", Toast.LENGTH_SHORT);
            }
            toast.show();
        } else{
            intentInLastSec = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    intentInLastSec = false;
                }
            }, 1000);
        }

    }

    /**
     * Method used to create an NdefRecord containing a string
     * @param text the string to be added to the NdefRecord
     * @return the created NdefRecord obkect
     * @throws UnsupportedEncodingException If the phone doesn't support US-ASCII encoding
     */
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

        //create the message in according with the standard
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
    }

    /**
     * Sets up foreground dispatch for NFC intents for the Activity
     * @param activity The corresponding Activity requesting the foreground dispatch.
     * @param adapter The NfcAdapter used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * Stops foreground dispatch of NFC intents
     * @param activity The Activity requesting the foreground dispatch.
     * @param adapter The NfcAdapter used for the foreground dispatch.
     */
    public void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * Reads a tag and writes to it if the tag is valid
     * @param intent the NFC intent triggered by the android system
     * @return true if the tag was written successfully
     */
    private boolean tagRW(Intent intent) {
        //Get the tag
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        for (int i = 0; i < techList.length; i++) {
            //The card emulation registers as an Ndef tag
            if (techList[i].equals(Ndef.class.getName())) {
                Ndef ndefTag = Ndef.get(tag);
                //Check if it's a type 2 tag
                if(ndefTag.getType().equals(Ndef.NFC_FORUM_TYPE_2)) {
                    //Read the message from the tag
                    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    NdefRecord relayRecord = ((NdefMessage) rawMsgs[0]).getRecords()[0];
                    String nfcData = new String(relayRecord.getPayload());
                    //If the read data is as expected
                    if (nfcData.contains("Room: ")) {
                        Ndef ndef = null;
                        try {
                            //Write to the tag
                            NdefRecord[] writeRecord = {createRecord(uidString)};
                            NdefMessage message = new NdefMessage(writeRecord);
                            ndef = Ndef.get(tag);
                            ndef.connect();
                            ndef.writeNdefMessage(message);
                            ndef.close();
                        } catch (UnsupportedEncodingException e) {
                            if(ndef!=null&&ndef.isConnected()){
                                try {
                                    ndef.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        } catch (FormatException e) {
                            if(ndef!=null&&ndef.isConnected()){
                                try {
                                    ndef.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        } catch (IOException e) {
                            if(ndef!=null&&ndef.isConnected()){
                                try {
                                    ndef.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
