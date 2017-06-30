package pl.edu.wat.jokeboxandroid.component;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import pl.edu.wat.jokeboxandroid.activity.AdminDataBaseActivity;
import pl.edu.wat.jokeboxandroid.activity.AdminJokesActivity;
import pl.edu.wat.jokeboxandroid.activity.AdminManagementActivity;
import pl.edu.wat.jokeboxandroid.activity.ScrollingActivity;
import pl.edu.wat.jokeboxandroid.model.Joke;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hubert on 29.06.2017.
 */

public class AdminOnSwipeListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public AdminOnSwipeListener (Context context){
        gestureDetector = new GestureDetector(context, new AdminOnSwipeListener.GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeTop() {
        if(AdminJokesActivity.currentJokeIndex == AdminJokesActivity.simpleJokeDtos.size()-1){
            AdminJokesActivity.currentJokeIndex = 0;
        } else {
            AdminJokesActivity.currentJokeIndex++;
        }
        refreshView();
    }

    public void onSwipeBottom() {
        if(AdminJokesActivity.currentJokeIndex == 0){
            AdminJokesActivity.currentJokeIndex = AdminJokesActivity.simpleJokeDtos.size()-1;
        } else {
            AdminJokesActivity.currentJokeIndex--;
        }
        refreshView();
    }

    private void refreshView(){
        AdminJokesActivity.textView.setText(AdminJokesActivity.simpleJokeDtos.get(AdminJokesActivity.currentJokeIndex).getContent());
        AdminJokesActivity.likesNumberText.setText("Likes: " + String.valueOf(AdminJokesActivity.simpleJokeDtos.get(AdminJokesActivity.currentJokeIndex).getLikeNumber()));
        AdminJokesActivity.disLikesNumberText.setText("Dislikes: " + String.valueOf(AdminJokesActivity.simpleJokeDtos.get(AdminJokesActivity.currentJokeIndex).getUnlikeNumber()));
        AdminJokesActivity.deleteJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AdminJokesActivity.simpleJokeDtos.isEmpty()){
                    AdminJokesActivity.textView.setText("No Jokes!");
                    AdminJokesActivity.disLikesNumberText.setText("");
                    AdminJokesActivity.likesNumberText.setText("");
                    return;
                }

                Call<Joke> jokeCall = AdminManagementActivity.administrationRestService.deleteJoke(AdminJokesActivity.simpleJokeDtos.get(AdminJokesActivity.currentJokeIndex).getId(), AdminManagementActivity.token);
                jokeCall.enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        if(response.isSuccessful()) {
                            AdminJokesActivity.simpleJokeDtos.remove(AdminJokesActivity.simpleJokeDtos.get(AdminJokesActivity.currentJokeIndex));
                            if(AdminJokesActivity.simpleJokeDtos.size() != 0){
                                if (AdminJokesActivity.currentJokeIndex == 0) {
                                    onSwipeTop();
                                } else {
                                    onSwipeBottom();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {

                    }
                });
            }

        });
    }

}
