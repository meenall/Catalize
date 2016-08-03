/**
 * Responsible for creation and execution of introduction
 * BACKEND communicates here
 */

package com.catalizeapp.catalize_ss16_v5;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;


public class Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean cancel = false;
    Context context = null;
    boolean flag = false;
    private String result;
    private String result2;
    private SharedPreferences sharedPreferences;
    private String firstName;
    private String lastName;
    private String personEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav2);
        //setContentView(R.layout.intro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }

        context = this;
        if (ContextCompat.checkSelfPermission(Account.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Account.this,
                    Manifest.permission.SEND_SMS)) {

            } else {

                ActivityCompat.requestPermissions(Account.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);
            }
        }
        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);

        //Your toolbar is now an action bar and you can use it like you always do, for example:
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final Dialog dialog = new Dialog(Account.this);
        final EditText prompt =(EditText) findViewById(R.id.prompt);
//        prompt.requestFocus();
        if (Nav.person1.contains("@") && Nav.person2.contains("@")) {
            TextView name = (TextView) findViewById(R.id.nameerror);
            //name.setText("Set a name for " + Contacts.person1 + ": ");
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.layout_prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            final EditText userInput2 = (EditText) promptsView
                    .findViewById(R.id.input2);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    flag = true;
                                    // get user input and set it to result
                                    // edit text
                                    result = userInput.getText().toString();
                                    result2 = userInput2.getText().toString();
                                    prompt.setText("Hello " + result + ", meet " + result2 + ". I am introducing you two because ");
                                   // prompt.requestFocus();
                                    //prompt.setText("Hello " + result + ", meet " + result2 + ". I am introducing you two because...");
                                    //Contacts.person1 = result;
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cancel = true;
                                    startActivityForResult(new Intent(Account.this, Nav.class), 10);
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } else if (Nav.person1.contains("@") && !Nav.person2.contains("@") || Nav.person2.contains("@") && !Nav.person1.contains("@")) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.layout_prompt, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    flag = true;
                                    // get user input and set it to result
                                    // edit text
                                    result = userInput.getText().toString();
                                    if (Nav.person1.contains("@")) {
                                        prompt.setText("Hello " + result + ", meet " + Nav.person2 + ". I am introducing you two because ");
                                       // prompt.requestFocus();
                                        //prompt.setText("Hello " + result + ", meet " + Contacts.person2 + ". I am introducing you two because...");
                                    } else {

                                        prompt.setText("Hello " + Nav.person1 + ", meet " + result + ". I am introducing you two because ");
                                       // prompt.requestFocus();
                                    }
                                    //Contacts.person1 = result;
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } else {
            prompt.setText("Hello " + Nav.person1 + ", meet " + Nav.person2 + ". I am introducing you two because ");
            //prompt.requestFocus();
        }
        prompt.requestFocus();

        if (cancel) {
            cancel = false;
            //Toast.makeText(context, "HI",
            //      Toast.LENGTH_SHORT).show();
        }
        final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        //final EditText et= (EditText)findViewById(R.id.prompt);
        Nav.number2 = Nav.number2.replaceAll("[^0-9]","");
        Nav.number1 = Nav.number1.replaceAll("[^0-9]","");
        //people.setText(Contacts.people);

        //Toast.makeText(context, Contacts.numbers,
        //      Toast.LENGTH_SHORT).show();
        //sendIntent.putExtra(et.getText().toString(), "default content");
        //sendIntent.setType("vnd.android-dir/mms-sms");

        Button send = (Button) findViewById(R.id.send);
        firstName = sharedPreferences.getString("first_name","");
        lastName = sharedPreferences.getString("last_name","");

        personEmail  = sharedPreferences.getString("email", "");
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //try {
                //Toast.makeText(context, "HI",
                //      Toast.LENGTH_SHORT).show();
                SmsManager.getDefault().sendTextMessage("9154719427", null, "Introduction made by: "+ firstName + " " + lastName + " " + personEmail, null, null);
                SmsManager.getDefault().sendTextMessage("9154719427", null, "1: " + Nav.person1 + ": " + Nav.number1, null, null);
                SmsManager.getDefault().sendTextMessage("9154719427", null, "2: " + Nav.person2 + ": " + Nav.number2, null, null);
                SmsManager.getDefault().sendTextMessage("9154719427", null, prompt.getText().toString(), null, null);

                SmsManager.getDefault().sendTextMessage("2013751471", null, "Introduction made by: "+ firstName + " " + lastName + " " + personEmail, null, null);
                SmsManager.getDefault().sendTextMessage("2013751471", null, "1: " + Nav.person1 + ": " + Nav.number1, null, null);
                SmsManager.getDefault().sendTextMessage("2013751471", null, "2: " + Nav.person2 + ": " + Nav.number2, null, null);
                SmsManager.getDefault().sendTextMessage("2013751471", null, prompt.getText().toString(), null, null);
                //Toast.makeText(context, "Permissions",
                //      Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(Account.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

                dialog.setContentView(R.layout.sent);

                Button dismissButton = (Button) dialog.findViewById(R.id.buttonback);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(Account.this, Nav.class), 10);
                        dialog.dismiss();
                    }

                });
                dialog.show();

                //SmsManager.getDefault().sendTextMessage(Contacts.number2, null, "Hello " + Contacts.person2 + "! You've recieved an introduction from " + personName + " through Catalize, the networking and connections app:", null, null);
                //SmsManager.getDefault().sendTextMessage(Contacts.number2, null, et.getText().toString(), null, null);
                //SmsManager.getDefault().sendTextMessage(Contacts.number2, null, "Respond to this message to continue the conversation.", null, null);

                //SmsManager.getDefault().sendTextMessage(Contacts.number1, null, "Hello " + Contacts.person1 + "! You've recieved an introduction from " + personName + " through Catalize, the networking and connections app:", null, null);
                //SmsManager.getDefault().sendTextMessage(Contacts.number1, null, et.getText().toString(), null, null);
                //SmsManager.getDefault().sendTextMessage(Contacts.number1, null, "Respond to this message to continue the conversation.", null, null);


                //SmsManager.getDefault().sendTextMessage(Contacts.number2, null, "Meenal says: Hey " + Contacts.person2 +  "! Thanks for stopping by the Catalize booth at CreateX Product Day at Georgia Tech!", null, null);
                //SmsManager.getDefault().sendTextMessage(Contacts.number2, null, "Want to know  more about us? Check us out at catalizeapp.com." + "\n" + "Happy connecting!", null, null);

                //} catch (Exception e) {
                //    AlertDialog.Builder alertDialogBuilder = new
                //            AlertDialog.Builder(Account.this);
                //    AlertDialog dialog = alertDialogBuilder.create();


                //    dialog.setMessage(e.getMessage());


                //    dialog.show();
                //startActivity(sendIntent);
            }
            //}
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Account.this);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Account.this, Account.class);
            startActivity(intent);
        } else {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.denied, null);

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    flag = true;
                                    //ok = true;
                                    Intent intentLogOut = new Intent(Account.this, LoginActivity.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    startActivity(intentLogOut);
                                    finish();
                                }
                            });

            // create alert dialog
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bug) {
            Intent intentReportBug = new Intent(Account.this, ReportBug.class); //
            startActivity(intentReportBug);
        } else if (id == R.id.settings) {
            //Intent change = new Intent(Nav.this, Contacts.class);
            //startActivity(change);
        } else if (id == R.id.contacts) {
            Intent change = new Intent(Account.this, Nav.class);
            startActivity(change);
        } else if (id == R.id.log_out) {
            Intent intentLogOut = new Intent(Account.this, LoginActivity.class);
            startActivity(intentLogOut);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        //searchView.clearFocus();
        //searchView.setQuery("", false);
        return true;
    }

}
