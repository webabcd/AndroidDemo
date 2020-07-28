/**
 * RatingBar - 评分条
 *     setOnRatingBarChangeListener(OnRatingBarChangeListener listener) - 评分条发生改变时的回调
 * OnRatingBarChangeListener
 *     onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) - 评分值发生变化
 *         rating - 当前评分值
 *         fromUser - 是否是用户操作导致的评分值变化（比如用户拖动了评分条则此值为 true；程序修改了评分值则此值为 false）
 */

package com.webabcd.androiddemo.view.progress;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class RatingBarDemo1 extends AppCompatActivity {

    private RatingBar _ratingBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress_ratingbardemo1);

        _ratingBar1 = (RatingBar)findViewById(R.id.ratingBar1);

        sample();
    }

    private void sample() {
        _ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(RatingBarDemo1.this, String.format("rating:%f, fromUser:%b", rating, fromUser), Toast.LENGTH_LONG).show();
            }
        });

        // 在 java 中设置评分条的评分值
        _ratingBar1.setRating(4.5f);
    }
}
