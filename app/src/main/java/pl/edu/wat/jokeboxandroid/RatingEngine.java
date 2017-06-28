package pl.edu.wat.jokeboxandroid;

import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;

/**
 * Created by Hubert on 28.06.2017.
 */

public class RatingEngine {

    private RatingEngine(){

    }

    public static void setRate(SimpleJokeDto simpleJokeDto){
        int likes = simpleJokeDto.getLikeNumber();
        int dislikes = simpleJokeDto.getUnlikeNumber();
        double sum = likes + dislikes;
        double result = 0.0;
        if(sum != 0){
            result = ((likes-dislikes)/sum)*5.0;
            if(result < 1.2){
                ScrollingActivity.ratingBar.setImageResource(R.drawable.stars1);
            } else if(result >= 1.2 && result < 2.2){
                ScrollingActivity.ratingBar.setImageResource(R.drawable.stars2);
            } else if(result >= 2.2 && result < 3.2){
                ScrollingActivity.ratingBar.setImageResource(R.drawable.stars3);
            } else if(result >= 3.2 && result < 4.2) {
                ScrollingActivity.ratingBar.setImageResource(R.drawable.stars4);
            } else {
                ScrollingActivity.ratingBar.setImageResource(R.drawable.stars5);
            }
        } else {
            ScrollingActivity.ratingBar.setImageResource(R.drawable.stars0);
        }
    }
}
