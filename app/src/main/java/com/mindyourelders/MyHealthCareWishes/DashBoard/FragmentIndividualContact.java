package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.PetQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.Pet;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentIndividualContact extends Fragment implements View.OnClickListener{
   
    TextView txtSignUp, txtLogin, txtForgotPassword;
    ImageView imgEdit,imgProfile,imgDone,imgAddpet;
    TextView txtHeight,txtWeight,txtProfession,txttelephone,txtEmployed,txtReligion,txtIdNumber,txtOtherRelation,txtTitle, txtName, txtEmail,txtAddress, txtCountry, txtPhone,txtHomePhone,txtWorkPhone, txtBdate,txtGender, txtPassword,txtRelation;
    TextInputLayout tilOtherRelation,tilId;
    RelativeLayout rlPet;
    String name="", email="", phone="",manager_phone="", country="", bdate="",address="",homePhone="",workPhone="",gender="";
    String height="",weight="",profession="",employed="",religion="",idnumber="";
    String pet="No",veteran="No";
    String eyes,language,marital_status;
    String otherRelation;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    RadioGroup rgPet,rgVeteran;
    RadioButton rbYes,rbNo,rbYesPet,rbNoPet;
    public static final int REQUEST_PET= 400;
    
    ListView ListPet;

    MySpinner spinner,spinnerRelation,spinnerEyes,spinnerLanguage,spinnerMarital;
    String[] countryList = {"Canada", "Mexico", "USA", "UK", "california", "India"};

    String imagepath = "";//
    String relation;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    DBHelper dbHelper;
    View rootview;
    Preferences preferences;
    ImageView imgBack;
    RelativeConnection connection;
    PersonalInfo personalInfo;

    TextInputLayout tilBdate,tilName,tilWorkPhone;
    String[] Relationship = {"Mom", "Dad", "Wife", "Husband", "Daughter", "Son", "Sister", "Brother", "Friend", "GrandFather", "GrandMother", "GrandSon", "GrandDaughter","Aunt","Uncle","Niece","Nephew","Cousin","Mother-in-law","Father-in-law","Neighbor", "Other"};
    String[] EyesList = {"Blue", "Brown", "Green", "Hazel"};
    String[] MaritalList = {"Single", "Married"};
    String[] LangList = {"English", "Spanish", "French", "Russian", "Japanese", "Greek", "German", "Italian"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_individual_contact,null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        dbHelper=new DBHelper(getActivity());
        PersonalInfoQuery s=new PersonalInfoQuery(getActivity(),dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        PetQuery p=new PetQuery(getActivity(),dbHelper);
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL)))
        {
            personalInfo = PersonalInfoQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
        }
        else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
        }
    }


    private void initListener() {
        imgBack.setOnClickListener(this);
        txtBdate.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        imgAddpet.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("PERSONAL PROFILE");
        ListPet= (ListView) rootview.findViewById(R.id.ListPet);
        imgProfile = (ImageView) rootview.findViewById(R.id.imgProfile);
        imgAddpet = (ImageView) rootview.findViewById(R.id.imgAddPet);
        txtSignUp = (TextView) rootview.findViewById(R.id.txtSignUp);
        tilName = (TextInputLayout) rootview.findViewById(R.id.tilName);
        tilOtherRelation = (TextInputLayout) rootview.findViewById(R.id.tilOtherRelation);
        tilWorkPhone = (TextInputLayout) rootview.findViewById(R.id.tilWorkPhone);
        tilId = (TextInputLayout) rootview.findViewById(R.id.tilId);
        rlPet= (RelativeLayout) rootview.findViewById(R.id.rlPet);
        txtOtherRelation = (TextView) rootview.findViewById(R.id.txtOtherRelation);
        txtLogin = (TextView) rootview.findViewById(R.id.txtLogin);
        txtForgotPassword = (TextView) rootview.findViewById(R.id.txtForgotPassword);
        txtBdate = (TextView) rootview.findViewById(R.id.txtBdate);
        txtGender = (TextView) rootview.findViewById(R.id.txtGender);
        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgEdit = (ImageView) rootview.findViewById(R.id.imgEdit);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);
        txtRelation = (TextView) rootview.findViewById(R.id.txtRelation);
        tilBdate = (TextInputLayout) rootview.findViewById(R.id.tilBdate);
        spinnerRelation = (MySpinner) rootview.findViewById(R.id.spinnerRelation);
        txtAddress = (TextView) rootview.findViewById(R.id.txtAddress);
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtEmail = (TextView) rootview.findViewById(R.id.txtEmail);
        txtCountry = (TextView) rootview.findViewById(R.id.txtCountry);
        txtPhone = (TextView) rootview.findViewById(R.id.txtPhone);
        txtHomePhone = (TextView) rootview.findViewById(R.id.txtHomePhone);
        txtWorkPhone = (TextView) rootview.findViewById(R.id.txtWorkPhone);

        txtHeight = (TextView) rootview.findViewById(R.id.txtHeight);
        txtWeight = (TextView) rootview.findViewById(R.id.txtWeight);
        txtProfession = (TextView) rootview.findViewById(R.id.txtProfession);
        txtEmployed = (TextView) rootview.findViewById(R.id.txtEmployedBy);
        txttelephone= (TextView) rootview.findViewById(R.id.txttelephone);
        txtReligion = (TextView) rootview.findViewById(R.id.txtReligion);
        txtIdNumber = (TextView) rootview.findViewById(R.id.txtId);



        rbYes = (RadioButton) rootview.findViewById(R.id.rbYes);
        rbNo = (RadioButton) rootview.findViewById(R.id.rbNo);
        rbYesPet = (RadioButton) rootview.findViewById(R.id.rbYesPet);
        rbNoPet = (RadioButton) rootview.findViewById(R.id.rbNoPet);
        rgPet= (RadioGroup) rootview.findViewById(R.id.rgPet);
        rgVeteran= (RadioGroup) rootview.findViewById(R.id.rgVeteran);

        spinner = (MySpinner) rootview.findViewById(R.id.spinner);
        spinnerEyes = (MySpinner) rootview.findViewById(R.id.spinnerEyes);
        spinnerLanguage= (MySpinner) rootview.findViewById(R.id.spinnerLanguage);
        spinnerMarital= (MySpinner) rootview.findViewById(R.id.spinnerMarital);

        ArrayAdapter<String> adapterm = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, MaritalList);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarital.setAdapter(adapterm);
        spinnerMarital.setHint("Marital Status");


        ArrayAdapter<String> adapters = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, EyesList);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(adapters);
        spinnerEyes.setHint("Eyes Color");

        ArrayAdapter<String> adapterl = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, LangList);
        adapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterl);
        spinnerLanguage.setHint("Language Spoken");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        rgPet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYesPet) {
                    pet = "Yes";
                    rlPet.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.rbNoPet) {
                    pet = "No";
                    rlPet.setVisibility(View.GONE);
                }
            }
        });

        rgVeteran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    veteran = "Yes";
                    tilId.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbNo) {
                    veteran = "No";
                    tilId.setVisibility(View.GONE);
                }
            }
        });

        txtPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txttelephone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txttelephone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        txtHomePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHomePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtWorkPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtWorkPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });



        txtHeight.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHeight.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 1)) {
                    editable.append("-");
                }
            }
        });
        setValues();

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        setPetData();
    }

    private void setValues() {
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
            tilBdate.setVisibility(View.VISIBLE);
           // spinner.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.GONE);
            tilWorkPhone.setVisibility(View.GONE);
            txtHomePhone.setVisibility(View.VISIBLE);
            if (personalInfo != null) {
                txtGender.setText(personalInfo.getGender());
                txtName.setText(personalInfo.getName());
                txtEmail.setText(personalInfo.getEmail());
                txtAddress.setText(personalInfo.getAddress());
                txtHomePhone.setText(personalInfo.getHomePhone());
/*
                if (personalInfo.getCountry()!=null) {
                    int index = 0;
                    for (int i = 0; i < countryList.length; i++) {
                        if (personalInfo.getCountry().equals(countryList[i])) {
                            index = i;
                        }
                    }
                    spinner.setSelection(index);
                }
                else{
                    spinner.setHint("Country");
                }*/
                txtPhone.setText(personalInfo.getPhone());
                txtBdate.setText(personalInfo.getDob());

                txtHeight.setText(personalInfo.getHeight());
                txtWeight.setText(personalInfo.getWeight());
                txtProfession.setText(personalInfo.getProfession());
                txtEmployed.setText(personalInfo.getEmployed());
                txttelephone.setText(personalInfo.getManager_phone());
                txtReligion.setText(personalInfo.getReligion());
                txtIdNumber.setText(personalInfo.getIdnumber());

                if (personalInfo.getEyes()!=null) {
                    int index = 0;
                    for (int i = 0; i < EyesList.length; i++) {
                        if (personalInfo.getEyes().equalsIgnoreCase(EyesList[i])) {
                            index = i;
                        }
                    }
                    spinnerEyes.setSelection(index + 1);
                }

                if (personalInfo.getLanguage()!=null) {
                    int indexs = 0;
                    for (int i = 0; i < LangList.length; i++) {
                        if (personalInfo.getLanguage().equalsIgnoreCase(LangList[i])) {
                            indexs = i;
                        }
                    }
                    spinnerLanguage.setSelection(indexs + 1);
                }
                if (personalInfo.getMarital_status()!=null) {
                    int indexss = 0;
                    for (int i = 0; i < MaritalList.length; i++) {
                        if (personalInfo.getMarital_status().equalsIgnoreCase(MaritalList[i])) {
                            indexss = i;
                        }
                    }
                    spinnerMarital.setSelection(indexss + 1);
                }
                if (personalInfo.getVeteran()!=null) {
                    if (personalInfo.getVeteran().equals("Yes")) {
                        rbYes.setChecked(true);
                        rbNo.setChecked(false);
                    } else {
                        rbYes.setChecked(false);
                        rbNo.setChecked(true);
                    }
                }
                if (personalInfo.getPet()!=null) {
                    if (personalInfo.getPet().equals("Yes")) {
                        rbYesPet.setChecked(true);
                        rbNoPet.setChecked(false);
                    } else {
                        rbYesPet.setChecked(false);
                        rbNoPet.setChecked(true);
                    }
                }
                byte[] photo=personalInfo.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
               imgProfile.setImageBitmap(bmp);
            }
        }
        else{
            tilBdate.setVisibility(View.GONE);
           // spinner.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.VISIBLE);
            tilWorkPhone.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.GONE);
            if (connection != null) {
                txtName.setText(connection.getName());
                txtEmail.setText(connection.getEmail());
                txtAddress.setText(connection.getAddress());
                txtPhone.setText(connection.getMobile());
                txtOtherRelation.setText(connection.getOtherRelation());
                txtHomePhone.setText(connection.getPhone());
                txtWorkPhone.setText(connection.getWorkPhone());
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (connection.getRelationType().equalsIgnoreCase(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);
                byte[] photo=connection.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                imgProfile.setImageBitmap(bmp);


                txtHeight.setText(connection.getHeight());
                txtWeight.setText(connection.getWeight());
                txtProfession.setText(connection.getProfession());
                txtEmployed.setText(connection.getEmployed());
                txttelephone.setText(connection.getManager_phone());
                txtReligion.setText(connection.getReligion());
                txtIdNumber.setText(connection.getIdnumber());
                int indexd = 0;

                if (connection.getEyes()!=null) {
                    for (int i = 0; i < EyesList.length; i++) {
                        if (connection.getEyes().equalsIgnoreCase(EyesList[i])) {
                            indexd = i;
                        }
                    }
                    spinnerEyes.setSelection(indexd + 1);
                }

                if (connection.getLanguage()!=null) {
                    int indexs = 0;
                    for (int i = 0; i < LangList.length; i++) {
                        if (connection.getLanguage().equalsIgnoreCase(LangList[i])) {
                            indexs = i;
                        }
                    }
                    spinnerLanguage.setSelection(indexs + 1);
                }

                if (connection.getMarital_status()!=null) {
                    int indexss = 0;
                    for (int i = 0; i < MaritalList.length; i++) {
                        if (connection.getMarital_status().equalsIgnoreCase(MaritalList[i])) {
                            indexss = i;
                        }
                    }
                    spinnerMarital.setSelection(indexss + 1);
                }
                if (connection.getVeteran()!=null) {
                    if (connection.getVeteran().equals("Yes")) {
                        rbYes.setChecked(true);
                        rbNo.setChecked(false);
                    } else {
                        rbYes.setChecked(false);
                        rbNo.setChecked(true);
                    }
                }
                if (connection.getPet()!=null) {
                    if (connection.getPet().equals("Yes")) {
                        rbYesPet.setChecked(true);
                        rbNoPet.setChecked(false);
                    } else {
                        rbYesPet.setChecked(false);
                        rbNoPet.setChecked(true);
                    }
                }
            }
            }
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgAddPet:
                Intent intent = new Intent(getActivity(), AddPetActivity.class);
                startActivityForResult(intent, REQUEST_PET);
                break;

            case R.id.imgDone:

                    Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    byte[] photo = baos.toByteArray();
                    if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
                        if (validateUser()) {
                            Boolean flag = PersonalInfoQuery.updatePersonalInfoData(preferences.getInt(PrefConstants.USER_ID), name, email, address, country, phone, bdate, photo,homePhone,gender,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet,manager_phone);
                            if (flag == true) {
                                Toast.makeText(getActivity(), "You have updated Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                editToConnection(photo);
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (validateConnection()) {
                            editToConnection(photo);
                            getActivity().finish();
                        }
                    }


                break;

            
            case R.id.imgBack:
                getActivity().finish();
                break;

            case R.id.txtGender:
                final Dialog dialogs = new Dialog(getActivity());
                dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogs.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf1 = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview1 = lf1.inflate(R.layout.dialog_gender, null);
                final TextView textOptions1 = (TextView) dialogview1.findViewById(R.id.txtOption1);
                final TextView textOptions2 = (TextView) dialogview1.findViewById(R.id.txtOption2);
                TextView textCancels = (TextView) dialogview1.findViewById(R.id.txtCancel);
                textOptions1.setText("Male");
                textOptions2.setText("Female");
                dialogs.setContentView(dialogview1);
                WindowManager.LayoutParams lps = new WindowManager.LayoutParams();
                lps.copyFrom(dialogs.getWindow().getAttributes());
                int widths = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
                lps.width = widths;
                lps.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lps.gravity = Gravity.CENTER;
                dialogs.getWindow().setAttributes(lps);
                dialogs.show();
                textOptions1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     txtGender.setText("Male");
                        dialogs.dismiss();
                    }
                });
                textOptions2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Female");
                        dialogs.dismiss();
                    }
                });
                textCancels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogs.dismiss();
                    }
                });

                break;
            case R.id.imgEdit:
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.dialog_gender, null);
                final TextView textOption1 = (TextView) dialogview.findViewById(R.id.txtOption1);
                final TextView textOption2 = (TextView) dialogview.findViewById(R.id.txtOption2);
                TextView textCancel = (TextView) dialogview.findViewById(R.id.txtCancel);
                textOption1.setText("Take Picture");
                textOption2.setText("Gallery");
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dispatchTakePictureIntent();
                        dialog.dismiss();
                    }
                });
                textOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_SELECT_PHOTO);
                        dialog.dismiss();
                    }
                });
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.txtBdate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d=new DateClass();
                        d.setDate(reportDate);
                        txtBdate.setText(reportDate);
                    }
                }, year, month, day);
                dpd.show();
                break;


           /* case R.id.imgGoogleSignup:

                break;*/
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    private boolean validateConnection() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        workPhone=txtWorkPhone.getText().toString().trim();
        homePhone=txtHomePhone.getText().toString().trim();
        otherRelation=txtOtherRelation.getText().toString().trim();
        address=txtAddress.getText().toString().trim();
        int i= spinnerRelation.getSelectedItemPosition();
        if (i!=0)
        relation=Relationship[i-1];

        int i1= spinnerEyes.getSelectedItemPosition();
        if (i1!=0)
        eyes=EyesList[i1-1];
        int i2= spinnerLanguage.getSelectedItemPosition();
        if (i2!=0)
        language=LangList[i2-1];
        int i3= spinnerMarital.getSelectedItemPosition();
        if (i3!=0)
        marital_status=MaritalList[i3-1];



        idnumber=txtIdNumber.getText().toString();
        height=txtHeight.getText().toString();
        weight=txtWeight.getText().toString();
        profession=txtProfession.getText().toString();
        employed=txtEmployed.getText().toString();
        manager_phone=txttelephone.getText().toString();
        religion=txtReligion.getText().toString();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        } /*else if (height.length()!=0 && height.length()<5)
        {
            txtHeight.setError("Enter height");
            showAlert("Enter correct height",  getActivity());
        }*/
       /* else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
       else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        }*/ else if (phone.length()!=0&&phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }
        else if (manager_phone.length()!=0 && manager_phone.length() < 10) {
            txttelephone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", getActivity());
        }
      /*  else if (homePhone.equals("")) {
            txtHomePhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        }else if (homePhone.length() < 10) {
            txtHomePhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }*/
        else {
            return true;
        }
        return false;


    }

    private boolean validateUser() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        bdate = txtBdate.getText().toString().trim();
        homePhone=txtHomePhone.getText().toString().trim();
        gender=txtGender.getText().toString().trim();
        idnumber=txtIdNumber.getText().toString();
        if (spinner.getSelectedItem()!=null) {
            country = spinner.getSelectedItem().toString();
        }
        else{
            country="";
        }
        address=txtAddress.getText().toString().trim();

        height=txtHeight.getText().toString();
        weight=txtWeight.getText().toString();
        profession=txtProfession.getText().toString();
        employed=txtEmployed.getText().toString();
        manager_phone=txttelephone.getText().toString();
        religion=txtReligion.getText().toString();

        int indexValue = spinnerEyes.getSelectedItemPosition();
        if (indexValue!=0)
         eyes =EyesList[indexValue-1];

        int indexValuex = spinnerLanguage.getSelectedItemPosition();
        if (indexValuex!=0)
        language =LangList[indexValuex-1];

        int indexValues = spinnerMarital.getSelectedItemPosition();
        if (indexValues!=0)
        marital_status =MaritalList[indexValues-1];


        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }/* else if (height.length()!=0 && height.length()<5)
        {
            txtHeight.setError("Enter height");
            showAlert("Enter correct height",  getActivity());
        }*/
       /* else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
        else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        }*/  else if (phone.length()!=0&&phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }
        else if (manager_phone.length()!=0 && manager_phone.length() < 10) {
            txttelephone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", getActivity());
        }/*else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date",  getActivity());
        }*/ else {
            return true;
        }
        return false;
    }

    private void editToConnection(byte[] photo) {
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), name, email, address, phone," "," ", "Self", photo," ", 1, 2, otherRelation,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet,manager_phone);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        }
        else{
            int indexValuex = spinnerRelation.getSelectedItemPosition();
           String relation =Relationship[indexValuex-1];
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, phone,homePhone,workPhone,relation , photo,"", 1, 2, otherRelation,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet, manager_phone);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validate() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        bdate = txtBdate.getText().toString().trim();
        country=spinner.getSelectedItem().toString();
        address=txtAddress.getText().toString().trim();
        relation= spinnerRelation.getSelectedItem().toString();


        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        }/* else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }
        else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
       *//* else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        } *//*else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        } */else if (phone.length()!=0&&phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        } /*else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date",  getActivity());
        } */else {
            return true;
        }
        return false;
    }
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = (ImageView) rootview.findViewById(R.id.imgProfile);
     if (REQUEST_PET == requestCode) {
           /* String value=data.getExtras().getString("Value");
            String reaction=data.getExtras().getString("Reaction");
            String treatment=data.getExtras().getString("Treatment");
            String allergy="Pet: "+value+"\nReaction: "+reaction+"\nTreatment: "+treatment;
            allergyList.add(allergy);*/
         setPetData();
         ListPet.requestFocus();
     } else
     if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE && null != data) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWProfile/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }
            try {

                imagepath = path + "/MHCWProfile_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }


        }

    }

    private void setPetData() {
        final ArrayList allergyList = new ArrayList();
        final ArrayList<Pet> AllargyLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            ListPet.setVisibility(View.VISIBLE);
            for (int i = 0; i < AllargyLists.size(); i++) {
                Pet a = AllargyLists.get(i);
                String allergy = "Pet Name: " + a.getName() + "\nBreed: " + a.getBreed() + "\nColor: " + a.getColor()+ "\nVeterinarian: " + a.getVeterian()+ "\nGuard: " + a.getGuard();
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, allergyList);
                ListPet.setAdapter(adapter);
                ListPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        ImageView imgEdit= (ImageView) view.findViewById(R.id.imgEdit);
                        ImageView imgDelete= (ImageView) view.findViewById(R.id.imgDelete);
                        imgEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Pet a=AllargyLists.get(position);
                                Intent allergyIntent = new Intent(getActivity(), AddPetActivity.class);

                                allergyIntent.putExtra("PetObject",a);
                                startActivityForResult(allergyIntent, REQUEST_PET);
                            }
                        });

                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Pet a=AllargyLists.get(position);
                                boolean flag= PetQuery.deleteRecord(a.getId());
                                if(flag==true)
                                {
                                    Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                                    setPetData();
                                    ListPet.requestFocus();
                                }
                            }
                        });
                    }
                });
            }
        }
        else{
            ListPet.setVisibility(View.GONE);
        }
    }
}
