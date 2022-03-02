package com.example.sh;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import java.util.Locale;
import static com.example.sh.HelperKt.toast;

public class VoiceFragment extends Fragment {
    ImageView imageView,bulb,fan;
    private TextToSpeech TTS;
    private DatabaseReference relay1_db = FirebaseDatabase.getInstance().getReference().child("REL1");
    private DatabaseReference relay2_db = FirebaseDatabase.getInstance().getReference().child("REL2");
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    TextView textView;
    private static final int SPEECH_REQUEST_CODE = 0;
    public VoiceFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_voice, container, false);
        imageView = view.findViewById(R.id.speaker_icon);
        textView = view.findViewById(R.id.tv);
        bulb = view.findViewById(R.id.bulb_auto);
        fan = view.findViewById(R.id.fan_auto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });
        return view;
    }
    private void displaySpeechRecognizer() {
        Log.e("speech", "inside displaySpeechRecognizer() and speechRequestCode = " + SPEECH_REQUEST_CODE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }
    @Override
    public void onStart() {
        super.onStart();
        initTextToSpeech();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("REL1").getValue().toString().equals("0")){
                    bulb.setVisibility(View.VISIBLE);
                }else{
                    bulb.setVisibility(View.INVISIBLE);
                }
                if(dataSnapshot.child("REL2").getValue().toString().equals("0")){
                    fan.setVisibility(View.VISIBLE);
                }else{
                    fan.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("speech", "inside onActivityForResult in child fragment. requestCode = " + requestCode + " resultCode = " + resultCode);
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            Log.e("speech", "spokenText = " + spokenText);
            //textView.setText(spokenText);
        if(spokenText.contains("turn on light")){
            relay1_db.setValue("0");
            bulb.setVisibility(View.VISIBLE);
            speak("turning on light");
        }
        if(spokenText.contains("turn off light")){
            relay1_db.setValue("1");
            bulb.setVisibility(View.INVISIBLE);
            speak("turning off light");
        }
        if(spokenText.contains("turn off fan")){
            relay2_db.setValue("1");
            fan.setVisibility(View.INVISIBLE);
            speak("turning off fan");
        }
        if(spokenText.contains("turn on fan")){
            relay2_db.setValue("0");
            fan.setVisibility(View.VISIBLE);
            speak("turning on fan");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initTextToSpeech() {
        TTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(TTS.getEngines().size() == 0){
                    toast(getContext(),"There is no TTS Engine Found!!");
                }else{
                    TTS.setLanguage(Locale.US);
                    speak("Hi! I'm Ready");
                }
            }
        });
    }
    private void speak(String s) {
        if(Build.VERSION.SDK_INT >= 21){
            TTS.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
        }else{
            TTS.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
}
