package com.teamoptimal.cse110project;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.teamoptimal.cse110project.AmazonClientManager;
import com.teamoptimal.cse110project.R;
import com.teamoptimal.cse110project.data.Report;
import com.teamoptimal.cse110project.data.Restroom;
import com.teamoptimal.cse110project.data.Review;
import com.teamoptimal.cse110project.data.User;

public class ReportActivity extends AppCompatActivity {

    public static AmazonClientManager clientManager = null;

    private Report report = new Report();
    private User user = new User();

    private SharedPreferences sharedPreferences;

    private static final String PREFERENCES = "AppPrefs";

    private static Restroom restroom;
    private static Review review;

    private static String reportObj;
    private static String objId;
    private static DynamoDBMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(PREFERENCES, 0);

        Bundle extra = getIntent().getExtras();
        reportObj = extra.getString("Reported_Object");
        objId = extra.getString("ObjectId");
        TextView text = (TextView) findViewById(R.id.Title);

        text.setText(reportObj+": "+objId);

        clientManager = new AmazonClientManager(this);

        mapper = new DynamoDBMapper(clientManager.ddb());

        new getUserTask(sharedPreferences.getString("user_email",""));

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText description = (EditText) findViewById(R.id.reportDesc);
                String desc = description.getText().toString();
                if(reportObj == "Restroom"){
                    new getRestroomTask(objId);
                    new ReportRestroomTask(user, restroom, desc);
                    finish();
                }
                else if(reportObj == "Review"){
                    new getReviewTask(objId);
                    new ReportReviewTask(user, review, desc);
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(), "Cannot find the item to be reported",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class getUserTask extends AsyncTask<Void, Void, Void> {
        private String user_email;

        public getUserTask(String user_email) {
            this.user_email = user_email;
        }

        // To do in the background
        protected Void doInBackground(Void... inputs) {
            user = mapper.load(user.getClass(), this.user_email); // Use the method from the User class to create it
            return null;
        }

        // To do after doInBackground is executed
        // We can use UI elements here
        protected void onPostExecute(Void result) {
        }
    }

    private class getRestroomTask extends AsyncTask<Void, Void, Void> {
        private String restroomId;

        public getRestroomTask(String restroomId) {
            this.restroomId = restroomId;
        }

        // To do in the background
        protected Void doInBackground(Void... inputs) {
            restroom = mapper.load(restroom.getClass(), this.restroomId); // Use the method from the User class to create it
            return null;
        }

        // To do after doInBackground is executed
        // We can use UI elements here
        protected void onPostExecute(Void result) {
        }
    }

    private class getReviewTask extends AsyncTask<Void, Void, Void> {
        private String reviewId;

        public getReviewTask(String reviewId) {
            this.reviewId = reviewId;
        }

        // To do in the background
        protected Void doInBackground(Void... inputs) {
            review = mapper.load(review.getClass(), this.reviewId); // Use the method from the User class to create it
            return null;
        }

        // To do after doInBackground is executed
        // We can use UI elements here
        protected void onPostExecute(Void result) {
        }
    }

    private class ReportRestroomTask extends AsyncTask<Void, Void, Void> {
        private User user;
        private Restroom restroom;
        private String description;

        public ReportRestroomTask(User user, Restroom restroom, String description) {
            this.user = user;
            this.description = description;
            this.restroom = restroom;
        }

        // To do in the background
        protected Void doInBackground(Void... inputs) {
            user.reportRestroom(this.restroom,this.description); // Use the method from the User class to create it
            return null;
        }

        // To do after doInBackground is executed
        // We can use UI elements here
        protected void onPostExecute(Void result) {
        }
    }

    private class ReportReviewTask extends AsyncTask<Void, Void, Void> {
        private User user;
        private Review review;
        private String description;

        public ReportReviewTask(User user, Review review, String description) {
            this.user = user;
            this.description = description;
            this.review = review;
        }

        // To do in the background
        protected Void doInBackground(Void... inputs) {
            user.reportReview(this.review,this.description); // Use the method from the User class to create it
            return null;
        }

        // To do after doInBackground is executed
        // We can use UI elements here
        protected void onPostExecute(Void result) {
        }
    }

}
