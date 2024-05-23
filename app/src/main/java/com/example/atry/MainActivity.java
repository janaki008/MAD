package com.example.atry;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.multidex.MultiDex;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button soundButton;
    private EditText editTextPhoneNumber;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditText fields
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextMessage = findViewById(R.id.editTextMessage);

        // Set onClickListener for the "Send SMS" button
        findViewById(R.id.buttonSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Initialize UI elements
        soundButton = findViewById(R.id.soundButton);
        Button whatsappButton = findViewById(R.id.button);
        Button locationButton = findViewById(R.id.button2);

        // Set up WhatsApp button
        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });

        // Set up Location button
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationOnMap();
            }
        });

        // Initialize MediaPlayer with the sound file
        mediaPlayer = MediaPlayer.create(this, R.raw.ghajini); // Replace with your sound file name

        // Set up Sound button
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSoundButtonClick();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void handleSoundButtonClick() {
        // Check if MediaPlayer is playing
        if (mediaPlayer.isPlaying()) {
            // Pause MediaPlayer
            mediaPlayer.pause();
            // Change button text to "Play"
            soundButton.setText(R.string.play_button_text);
        } else {
            // Start MediaPlayer
            mediaPlayer.start();
            // Change button text to "Pause"
            soundButton.setText(R.string.pause_button_text);
        }
    }

    private void openWhatsApp() {
        String packageName = "com.whatsapp";
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            // WhatsApp not installed, open WhatsApp in Play Store
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            startActivity(intent);
        }
    }

    private void showLocationOnMap() {
        // Start MapsActivity
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    private void sendMessage() {
        // Get phone number and message from EditText fields
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String message = editTextMessage.getText().toString();

        // Check if phone number and message are not empty
        if (phoneNumber.isEmpty() || message.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter phone number and message.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();

        // Send the SMS message
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        // Display a toast message indicating that the SMS has been sent
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_SHORT).show();

        // Clear EditText fields
        editTextPhoneNumber.setText("");
        editTextMessage.setText("");
    }
}
