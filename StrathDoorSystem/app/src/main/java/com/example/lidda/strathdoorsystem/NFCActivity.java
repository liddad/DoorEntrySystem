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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NFCActivity extends AppCompatActivity {

    private static final String TAG = "NFCActivity";
    private String uidString;
    private NfcAdapter mAdapter;
    private boolean backPressedOnce, exiting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        backPressedOnce = false;
        exiting = false;
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
        if (backPressedOnce){
            exiting = true;
            finish();
        }
        backPressedOnce = true;
        Toast.makeText(this, "Double click BACK to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedOnce=false;
            }
        }, 1000);
    }


    @Override
    public void onDestroy(){
        if(exiting){
            String s =FileSaver.loadLogin(getApplicationContext());
            if(s==null){
                FileSaver.saveLogin(getApplicationContext(), uidString);
            } else if(!s.equals(uidString)){
                //Something has gone wrong - just keep the current user logged in
                FileSaver.delete(getApplicationContext());
                FileSaver.saveLogin(getApplicationContext(),uidString);
            }
            //If they've closed the app while logged in before do nothing - it's already saved

        }
        super.onDestroy();
    }
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
        if(getTagInfo(intent)){
            toast = Toast.makeText(getApplicationContext(),"Successfuly Sent!", Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(getApplicationContext(),"Failed to send. Try again.", Toast.LENGTH_SHORT);
        }
        toast.show();

    }

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
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
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

    public void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private boolean getTagInfo(Intent intent) {
        //Get the tag
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        for (int i = 0; i < techList.length; i++) {
            if (techList[i].equals(Ndef.class.getName())) {
                Ndef ndefTag = Ndef.get(tag);
                if(ndefTag.getType().equals(Ndef.NFC_FORUM_TYPE_2)) {
                    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    NdefRecord relayRecord = ((NdefMessage) rawMsgs[0]).getRecords()[0];
                    String nfcData = new String(relayRecord.getPayload());
                    if (nfcData.contains("Room: ")) {
                        Ndef ndef = null;
                        try {
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
