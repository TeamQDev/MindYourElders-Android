package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.utility.CallDialog;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentPharmacy extends Fragment implements View.OnClickListener{
    View rootview;
    SwipeMenuListView lvPharmacy;
    ArrayList<Pharmacy> PharmacyList;
    RelativeLayout llAddPharmacy;
    Preferences preferences;
DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_pharmacy,null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        PharmacyQuery p=new PharmacyQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (PharmacyList.size()!=0) {
            PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(getActivity(), PharmacyList);
            lvPharmacy.setAdapter(pharmacyAdapter);
            lvPharmacy.setVisibility(View.VISIBLE);
        }
        else{
            lvPharmacy.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        llAddPharmacy.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddPharmacy= (RelativeLayout) rootview.findViewById(R.id.llAddPharmacy);
        lvPharmacy= (SwipeMenuListView) rootview.findViewById(R.id.lvPharmacy);
        if (PharmacyList.size()!=0) {
            setListData();
        }
        lvPharmacy.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvPharmacy.setMenuCreator(creator);
        lvPharmacy.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Pharmacy item = PharmacyList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deletePharmacy(item);
                        break;
                }
                return false;
            }
        });
    }
    private void callUser(Pharmacy item) {
        String mobile=item.getPhone();
        String hphone="";
        String wPhone="";

        if (mobile.length()!=0||hphone.length()!=0||wPhone.length()!=0)
        {
            CallDialog c=new CallDialog();
            c.showCallDialog(getActivity(),mobile,hphone,wPhone);
        }
        else{
            Toast.makeText(getActivity(),"You have not added phone number for call",Toast.LENGTH_SHORT).show();
        }
    }
    private void deletePharmacy(Pharmacy item) {
        boolean flag= PharmacyQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
     /*   PharmacyList=new ArrayList<>();

        Pharmacy P1=new Pharmacy();
        P1.setName("Health Care Medico");
        P1.setNote("Emily Holms");
        P1.setImage(R.drawable.pharmacy);
        P1.setPhone("789-456-2135");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");

        Pharmacy P2=new Pharmacy();
        P2.setName("City Medico");
        P2.setNote("Emily Holms");
        P2.setImage(R.drawable.pharmacys);
        P2.setPhone("985-456-2135");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");


        PharmacyList.add(P1);
        PharmacyList.add(P2);*/
          }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddPharmacy:
                preferences.putString(PrefConstants.SOURCE,"Pharmacy");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
