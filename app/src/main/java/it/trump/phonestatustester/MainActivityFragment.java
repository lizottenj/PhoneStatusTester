package it.trump.phonestatustester;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv = (TextView) getActivity().findViewById(R.id.infoView);

        final String output = getTelephonyData();

        tv.setText(output);

        Button email = (Button) getActivity().findViewById(R.id.emailButton);
        if(email != null) {
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    composeEmail(output);
                }
            });
        }
    }

    private String getTelephonyData() {
        TelephonyManager tMan = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        String output = "";

        output = addKey(output, "Line 1 Number", tMan.getLine1Number());
        output = addKey(output, "Device software version", tMan.getDeviceSoftwareVersion());
        if(Build.VERSION.SDK_INT >= 19) {
            output = addKey(output, "Group Id Level 1", tMan.getGroupIdLevel1());
            output = addKey(output, "MMS UA Prof URL", tMan.getMmsUAProfUrl());
            output = addKey(output, "MMS UA", tMan.getMmsUserAgent());
        }
        output = addKey(output, "Network Country ISO", tMan.getNetworkCountryIso());
        output = addKey(output, "Network Operator", tMan.getNetworkOperator());
        output = addKey(output, "Network Operator Name", tMan.getNetworkOperatorName());
        output = addKey(output, "SIM Country ISO", tMan.getSimCountryIso());
        output = addKey(output, "SIM Operator", tMan.getSimOperator());
        output = addKey(output, "SIM Operator Name", tMan.getSimOperatorName());
        output = addKey(output, "SIM Serial Number", tMan.getSimSerialNumber());
        output = addKey(output, "SIM Subscriber ID", tMan.getSubscriberId());
        output = addKey(output, "Voicemail Alpha Tag", tMan.getVoiceMailAlphaTag());
        output = addKey(output, "Voicemail number", tMan.getVoiceMailNumber());
        output = addKey(output, "Is Network Roaming", String.valueOf(tMan.isNetworkRoaming()));

        if(Build.VERSION.SDK_INT >= 21) {
            output = addKey(output, "Is SMS Enabled", String.valueOf(tMan.isSmsCapable()));
        }

        CellLocation cl = tMan.getCellLocation();
        if(cl instanceof GsmCellLocation) {
            output += "\n\nGSM INFO\n";
            output = addKey(output, "GSM cid", String.valueOf(((GsmCellLocation) cl).getCid()));
            output = addKey(output, "GSM lac", String.valueOf(((GsmCellLocation) cl).getLac()));
            output = addKey(output, "GSM psc", String.valueOf(((GsmCellLocation) cl).getPsc()));
        }
        return output;
    }

    private String addKey(String output, String key, String value) {

        output += (key + ":\t" + value + "\n");
        return output;
    }

    public void composeEmail(String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nick@trump.it"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Telephony Data");
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
