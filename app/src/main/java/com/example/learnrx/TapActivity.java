package com.example.learnrx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class TapActivity extends AppCompatActivity {

    TextView textCount;
    TextView textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        final Button btnTap = (Button) findViewById(R.id.btn_tap);
        textCount = (TextView) findViewById(R.id.text_count);
        textTotal = (TextView) findViewById(R.id.text_total);

        //inisialisasi tap stream dengan button yang akan di tap
        Observable<Void> tapStream = RxView.clicks(btnTap).share();

        /*
        Operator map berfungsi untuk melakukan perubahan/pengolahan nilai yang terhadap setiap
        item yang di-emit oleh Observer. Contohnya, kita mau mengubah nilai awal yang diberikan adalah
        array integer yang di-emit kemudian, dengan operator map maka akan dilakukan
        pengolahan untuk menampilkan apakah bilangan integer tersebut bilangan genap atau ganjil.

         filter = melakukan operasi yang ada dalam map, contoh dibawah yaitu integer yang lebih dari 2
        */



        //buat objek untuk menentukan jumlah tap pada button
        Observable<Integer> multipleTapCountStream = tapStream.buffer(tapStream
                .debounce(1, TimeUnit.SECONDS))
                .map(new Func1<List<Void>, Integer>() {
                    @Override
                    public Integer call(List<Void> voids) {
                        Log.d("multipleTap map",String.valueOf(voids.size()));
                        return voids.size();
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        Log.d("multipleTap filter",String.valueOf(integer));
                        return integer >= 2;
                    }
                });



        //data di konsumsi oleh observer
        Observer<Integer> multipleTapObserver = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d("multipleTapStream","Tap Stream Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("multipleTapStream",e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                showTapCount(integer);
            }
        };


        multipleTapCountStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe(multipleTapObserver);

    }

    private void showTapCount(int size) {
        textCount.setText(String.valueOf(size) + " x Taps");
        textCount.setVisibility(View.VISIBLE);
        textCount.setScaleX(1f);
        textCount.setScaleY(1f);
        ViewCompat.animate(textCount)
                .scaleXBy(-1f)
                .scaleYBy(-1f)
                .setDuration(800)
                .setStartDelay(100);
    }}