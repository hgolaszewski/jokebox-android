package pl.edu.wat.jokeboxandroid.component;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v4.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import pl.edu.wat.jokeboxandroid.activity.MainActivity;
import pl.edu.wat.jokeboxandroid.activity.ScrollingActivity;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hubert on 28.06.2017.
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context context){
        gestureDetector = new GestureDetector(context, new GestureListener());
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
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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

    public void onSwipeRight() {
        Pair<Integer, String> requestParams = new Pair<>(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex).getId(), "unlike");
        doRequest(requestParams);
        ScrollingActivity.textView.setTextColor(Color.rgb(255,64,64));
    }

    public void onSwipeLeft() {
        Pair<Integer, String> requestParams = new Pair<>(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex).getId(), "like");
        doRequest(requestParams);
        ScrollingActivity.textView.setTextColor(Color.rgb(34,139,34));
    }

    public void onSwipeTop() {
        ScrollingActivity.textView.setTextColor(Color.rgb(0,0,0));
        if(ScrollingActivity.currentJokeIndex == ScrollingActivity.simpleJokeDtos.size()-1){
            ScrollingActivity.currentJokeIndex = 0;
        } else {
            ScrollingActivity.currentJokeIndex++;
        }
        ScrollingActivity.textView.setText(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex).getContent());
        RatingEngine.setRate(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex));
    }

    public void onSwipeBottom() {
        ScrollingActivity.textView.setTextColor(Color.rgb(0,0,0));
        if(ScrollingActivity.currentJokeIndex == 0){
            ScrollingActivity.currentJokeIndex = ScrollingActivity.simpleJokeDtos.size()-1;
        } else {
            ScrollingActivity.currentJokeIndex--;
        }
        ScrollingActivity.textView.setText(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex).getContent());
        RatingEngine.setRate(ScrollingActivity.simpleJokeDtos.get(ScrollingActivity.currentJokeIndex));
    }

    private void doRequest(final Pair<Integer, String> requestParams){

        Call<SimpleJokeDto> callList = ScrollingActivity.jokeRestService.markJoke(requestParams.first, requestParams.second);
        callList.enqueue(new Callback<SimpleJokeDto>() {

            @Override
            public void onResponse(Call<SimpleJokeDto> call, Response<SimpleJokeDto> response) {
                if(response.isSuccessful()){
                    SimpleJokeDto simpleJokeDto = response.body();
                    int index = ScrollingActivity.simpleJokeDtos.indexOf(simpleJokeDto);
                    SimpleJokeDto finded = ScrollingActivity.simpleJokeDtos.get(index);
                    finded.setLikeNumber(simpleJokeDto.getLikeNumber());
                    finded.setUnlikeNumber(simpleJokeDto.getUnlikeNumber());
                    RatingEngine.setRate(simpleJokeDto);
                }else{

                }
            }

            @Override
            public void onFailure(Call<SimpleJokeDto> call, Throwable t) {

            }
        });
    }

}
