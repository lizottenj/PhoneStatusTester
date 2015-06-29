package it.trump.phonestatustester;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TelephonyManager tMan = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        tMan.getLine1Number();

        String output = "";

        output = addKey(output, "Line 1 Number", tMan.getLine1Number());
        output = addKey(output, "Device software version", tMan.getDeviceSoftwareVersion());
        output = addKey(output, "Group Id Level 1", tMan.getGroupIdLevel1());
        output = addKey(output, "MMS UA Prof URL", tMan.getMmsUAProfUrl());
        output = addKey(output, "MMS UA", tMan.getMmsUserAgent());
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

        if(Build.VERSION.SDK_INT >= 17) {
            output = addKey(output, "Is SMS Enabled", String.valueOf(tMan.isSmsCapable()));
        }

        CellLocation cl = tMan.getCellLocation();
        if(cl instanceof GsmCellLocation) {
            output += "\n\nGSM INFO\n";
            output = addKey(output, "GSM cid", String.valueOf(((GsmCellLocation) cl).getCid()));
            output = addKey(output, "GSM lac", String.valueOf(((GsmCellLocation) cl).getLac()));
            output = addKey(output, "GSM psc", String.valueOf(((GsmCellLocation) cl).getPsc()));
        }

        tv.setText(output);
    }

    private String addKey(String output, String key, String value) {

        output += (key + ":\t" + value + "\n");
        return output;
    }
}
