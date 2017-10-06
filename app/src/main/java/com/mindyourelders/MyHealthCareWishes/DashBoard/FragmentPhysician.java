package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DoctorQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentPhysician extends Fragment implements View.OnClickListener{
    View rootview;
    SwipeMenuListView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    TextView txtTitle;
    TextView txtAdd;
    DBHelper dbHelper;
    SpecialistAdapter specialistAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_physician,null);
       initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        DoctorQuery d=new DoctorQuery(getActivity(),dbHelper);
        SpecialistQuery s=new SpecialistQuery(getActivity(),dbHelper);
    }

    private void setListData() {
       specialistAdapter=new SpecialistAdapter(getActivity(),specialistList);
        lvSpecialist.setAdapter(specialistAdapter);


    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddSpecialist.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle= (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Primary Physician");
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddSpecialist= (RelativeLayout) rootview.findViewById(R.id.llAddSpecialist);
        lvSpecialist = (SwipeMenuListView) rootview.findViewById(R.id.lvSpecialist);
        if (specialistList.size()!=0||specialistList!=null)
        {
            setListData();
        }
        lvSpecialist.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvSpecialist.setMenuCreator(creator);
        lvSpecialist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Specialist item = specialistList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
                        // delete
                        deleteSpecialist(item);
                        break;
                }
                return false;
            }
        });

    }

    private void deleteSpecialist(Specialist item) {
        boolean flag= MyConnectionsQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
       specialistList=SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),1);

       /*specialistList=new ArrayList<>();

        Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setPhone("789-789-5236");


        Specialist P2=new Specialist();
        P2.setName("Dr. James");
        P2.setType("Neuro Surgeon");
        P2.setAddress("#204,10 upper Street, los Angeles, California.");
        P2.setImage(R.drawable.docto);
        P2.setPhone("987-789-5236");

        Specialist P3=new Specialist();
        P3.setName("Dr. Smith");
        P3.setType("Neuro Surgeon");
        P3.setAddress("#205,10 Left Street, los Angeles, California.");
        P3.setImage(R.drawable.doctors);
        P3.setPhone("789-789-5236");

        specialistList.add(P1);
       specialistList.add(P2);
        specialistList.add(P3);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddSpecialist:
                preferences.putString(PrefConstants.SOURCE,"Physician");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
                // DialogManager dialogManager=new DialogManager(new FragmentSpecialist());
                // dialogManager.showCommonDialog("Add?","Do you want to add new specialist?",getActivity(),"ADD_SPECIALIST",null);
                break;
        }
    }

    public void postCommonDialog() {
        //preferences.putString(PrefConstants.SOURCE,"Speciality");
        Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
