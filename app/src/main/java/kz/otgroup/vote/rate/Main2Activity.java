package kz.otgroup.vote.rate;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;


public class Main2Activity extends AppCompatActivity {

    private String path;

    private VideoView player;


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

}
