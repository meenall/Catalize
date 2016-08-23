/**
 * Main screen - displays contact list.
 * Asks for contact permission.
 * Deny permission twice = crash. Dependent on Shared Prefs working
 */

package com.catalizeapp.catalize_ss16_v5;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catalizeapp.catalize_ss16_v5.ui.login.FireLoginActivity;
import com.catalizeapp.catalize_ss16_v5.utils.DbManager;
import com.catalizeapp.catalize_ss16_v5.utils.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;



/**
 * Main screen - displays contact list.
 * Asks for contact permission.
 * Deny permission twice = crash. Dependent on Shared Prefs working
 */

public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = null;
    public static String person1 = "";
    public static String person2 = "";
    public static String number1 = "";
    public static String number2 = "";
    public TextView displayName;
    public TextView displayEmail;
    boolean flag = false;
    public static boolean newbie = false;
    private  ImageView profileImage;
    ContactsAdapter objAdapter;
    SearchView searchView;
    ListView lv = null;
    LinearLayout llContainer = null;
    Button btnOK = null;
    RelativeLayout rlPBContainer = null;
    private SharedPreferences sharedPreferences;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    private FirebaseAnalytics mFirebaseAnalytics;


    boolean isUserFirstTime;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nav);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        DbManager.getInstance().loadIntros();
        if( getIntent().getBooleanExtra("Exit me", false)) {
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }
        super.onCreate(savedInstanceState);
        context = this;

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(Nav.this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(Nav.this, PagerActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
        if(isUserFirstTime){
            startActivity(introIntent);

        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_nav, null);
       navView.addHeaderView(header);
        displayName = (TextView) header.findViewById(R.id.textViewPerson);
        displayEmail  = (TextView) header.findViewById(R.id.textViewEmail);
        profileImage  = (ImageView) header.findViewById(R.id.profileImage);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        displayEmail.setText(user.getEmail());
        displayName.setText(user.getDisplayName());

        if(user.getPhotoUrl()!= null){
            Picasso.with(getApplicationContext())
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(profileImage);
        }



        rlPBContainer = (RelativeLayout) findViewById(R.id.pbcontainer);
        llContainer = (LinearLayout) findViewById(R.id.data_container);
        btnOK = (Button) findViewById(R.id.connect);
        btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!flag) {
                    //searchView2 = (ActionMenuItemView) findViewById(R.id.menu_search);
                    //searchView2.clearFocus();
                    //searchView2.setQuery("", false);
                } else {
                    //searchView = (SearchView) findViewById(R.id.menu_search);
                    //searchView.clearFocus();
                    //searchView.setQuery("", false);
                }
                //startActivityForResult(new Intent(Nav.this, PagerActivity.class), 10);

                searchView.clearFocus();
                searchView.setQuery("", false);
                getSelectedContacts();
            }
        });

        if (ContextCompat.checkSelfPermission(Nav.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Nav.this,
                    Manifest.permission.READ_CONTACTS)) {

            } else {

                ActivityCompat.requestPermissions(Nav.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        0);
            }
        }

        addContactsInList();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    searchView = (SearchView) findViewById(R.id.menu_search);
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                getSelectedContacts();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //addContactsInList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getSelectedContacts() {
        number1 = "";
        number2 = "";
        person1 = "";
        person2 = "";
        newbie = false;

        int total = 0;
        StringBuffer sb = new StringBuffer();
        for (ContactObject bean : ContactsListClass.phoneList) {
            if (bean.isSelected()) {
                total++;
            }
        }

        if (total == 0) {
            final Dialog dialog = new Dialog(Nav.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

            dialog.setContentView(R.layout.two);

            Button dismissButton = (Button) dialog.findViewById(R.id.buttonback);
            dismissButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                }

            });
            dialog.show();

        } else if (total == 1) {
            final Dialog dialog = new Dialog(Nav.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

            dialog.setContentView(R.layout.addnumber);

            final EditText newperson = (EditText) dialog.findViewById(R.id.newperson);
            final EditText newname = (EditText) dialog.findViewById(R.id.newname);

            Button dismissButton = (Button) dialog.findViewById(R.id.buttonback);
            dismissButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Button enter = (Button) dialog.findViewById(R.id.next);
            enter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    if (newname.getText().toString() == "" || newperson.getText().toString() == "") {
                        //Toast.makeText(context, "Please fill in both fields.",
                          //      Toast.LENGTH_SHORT).show();
                    } else {
                        person2 = newname.getText().toString();
                        number2 = newperson.getText().toString();
                        newbie = true;
                        startActivityForResult(new Intent(Nav.this, Account.class), 10);
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        } else if (total > 2) {
            searchView.clearFocus();
            searchView.setQuery("", false);
            final Dialog dialog = new Dialog(Nav.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

            dialog.setContentView(R.layout.three);

            Button dismissButton = (Button) dialog.findViewById(R.id.buttonback);
            dismissButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });
            dialog.show();
        }

        for (ContactObject bean : ContactsListClass.phoneList) {
            if (bean.isSelected()) {
                bean.setSelected(false);
                sb.append(bean.getName());
                if (number1 == "") {
                    number1 = bean.getNumber();
                } else {
                    if (!newbie) {
                        number2 = bean.getNumber();
                    }
                }
                sb.append(",");
                if (person1 == "") {
                    person1 = bean.getName();
                } else {
                    if (!newbie) {
                        person2 = bean.getName();
                    }
                }
            }
        }

        CheckBox cb;

        for(int i=0; i<lv.getChildCount();i++)
        {
            cb = (CheckBox)lv.getChildAt(i).findViewById(R.id.contactcheck);
            cb.setChecked(false);
        }

        if (total == 2) {
            startActivityForResult(new Intent(Nav.this, Account.class), 10);
        }
    }

    void showPB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.VISIBLE);
//                btnOK.setVisibility(View.GONE);
            }
        });
    }

    void hidePB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.GONE);
                //         btnOK.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addContactsInList() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                showPB();
                try {
                    Cursor cEmail = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            null, null, null);

                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, null, null, null);
                    try {
                        ContactsListClass.phoneList.clear();
                    } catch (Exception e) {
                    }
                    while (phones.moveToNext()) {
                        String phoneName = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String phoneImage = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                        ContactObject cp = new ContactObject();

                        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
                        cp.setName(phoneName);
                        cp.setNumber(phoneNumber);
                        cp.setImage(phoneImage);
                        boolean found = false;

                        for (ContactObject o : ContactsListClass.phoneList) {
                            if (o.getNumber().equals(cp.getNumber())) {
                                found = true;
                            }
                        }
                        if (!found) {
                            ContactsListClass.phoneList.add(cp);
                        }
                    }
                    while (cEmail.moveToNext()) {
                        if (cEmail.getCount() > 0) {
                            String email = cEmail
                                    .getString(cEmail
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            ContactObject cp2 = new ContactObject();
                            cp2.setName(email);
                            cp2.setNumber("none");
                            cp2.setImage(null);
                            ContactsListClass.phoneList.add(cp2);
                            //Toast.makeText(context, email,
                            //              Toast.LENGTH_SHORT).show();
                            //break;
                        }
                    }
                    phones.close();
                    cEmail.close();

                    lv = new ListView(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lv.setLayoutParams(lp);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            llContainer.addView(lv);
                        }
                    });
                    Collections.sort(ContactsListClass.phoneList,
                            new Comparator<ContactObject>() {
                                @Override
                                public int compare(ContactObject lhs,
                                                   ContactObject rhs) {
                                    return lhs.getName().compareTo(
                                            rhs.getName());
                                }
                            });

                    objAdapter = new ContactsAdapter(Nav.this,
                            ContactsListClass.phoneList);
                    lv.setAdapter(objAdapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            final CheckBox chk = (CheckBox) view
                                    .findViewById(R.id.contactcheck);

                            ContactObject bean = ContactsListClass.phoneList
                                    .get(position);
                            if (bean.isSelected()) {
                                bean.setSelected(false);
                                chk.setChecked(false);
                            } else {
                                bean.setSelected(true);
                                chk.setChecked(true);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hidePB();
            }
        };
        thread.start();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Nav.this, Nav.class);
            startActivity(intent);
        } else {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.denied, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Utils.saveSharedSetting(Nav.this, Nav.PREF_USER_FIRST_TIME, "true");
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(Nav.this, FireLoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        return;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                newText.toLowerCase(Locale.getDefault());
                objAdapter.filter(newText);
                flag = true;
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery("", false);
                searchView.clearFocus();
                //Here u can get the value "query" which is entered in the search box.
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //searchView.setOnQueryTextListener(queryTextListener);

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == findViewById(R.id.menu_search).getId()) {
        //btnOK = (Button) findViewById(R.id.include).findViewById(R.id.include).findViewById(R.id.connect);
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bug) {
            Intent intentReportBug = new Intent(Nav.this, ReportBug.class); //
            startActivity(intentReportBug);
        /*} else if (id == R.id.settings) {
            Intent intentSettings = new Intent(Nav.this, SettingsActivity.class);
            startActivity(intentSettings);*/
        } else if (id == R.id.contacts) {
            Intent change = new Intent(Nav.this, Nav.class);
            startActivity(change);
        } else if (id == R.id.log) {
            Intent change = new Intent(Nav.this, com.catalizeapp.catalize_ss16_v5.Log.class);
            startActivity(change);
        } else if (id == R.id.log_out) {
            Utils.saveSharedSetting(Nav.this, Nav.PREF_USER_FIRST_TIME, "true");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, FireLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        searchView.clearFocus();
        searchView.setQuery("", false);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();


    }

}
