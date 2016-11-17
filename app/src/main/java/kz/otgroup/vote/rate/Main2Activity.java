package kz.otgroup.vote.rate;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Main2Activity extends AppCompatActivity {

    private String path;

    private VideoView player;

    RatingBar mRatingBar;
    File sdPath;
    private final String FILENAME_SD = "rate_result.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        path = Environment.getExternalStorageDirectory() + "/videorate.mp4";

        new Thread(new Runnable() {
            @Override
            public void run() {
                playVideo();

            }
        }).start();



//        Toast.makeText(this, var, Toast.LENGTH_SHORT).show();

        mRatingBar = (RatingBar) findViewById(R.id.rating);
        // получаем путь к SD
        sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath());


        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Toast.makeText(Main2Activity.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();

                String ratingValue = String.valueOf(rating);

                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy",
                        Locale.ENGLISH);
                String dateTime = dateFormat.format(date);

                CreateAndWrite(ratingValue, dateTime);
            }
        });

    }


    private void playVideo() {
        // Grabs a reference to the player view
        player = (VideoView) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
//        player(this);
//        player.disableControls();

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
//        player.setVideoURI(Uri.fromFile(file));
        player.setVideoPath(path);

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setLooping(true);
                player.start();
//                mediaPlayer.prepareAsync();
//                mediaPlayer.start();
            }
        });
//        player.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        player.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    void CreateAndWrite(String ratingValue, String dateTime) {
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile, true));
            // пишем данные
            bw.write(dateTime + ".  Оценка: " + ratingValue + "\n");
            // закрываем поток
            bw.close();
            Log.d("Main3", "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
