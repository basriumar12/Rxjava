package com.example.learnrx.TestApi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnrx.R;
import com.example.learnrx.TestApi.api.BaseApiService;
import com.example.learnrx.TestApi.api.UtilsApi;
import com.example.learnrx.TestApi.model.Repo;
import com.example.learnrx.TestApi.model.ResponseRepos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class GithubActivity extends AppCompatActivity {

    ProgressBar pbLoading;
    RecyclerView rvRepos;
    EditText etUsername;
    Button btnSearch;

    BaseApiService mApiService;
    ReposAdapter mRepoAdapter;

    List<Repo> repoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        etUsername = (EditText) findViewById(R.id.etUsername) ;
        rvRepos = (RecyclerView) findViewById(R.id.rvRepos);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        mApiService = UtilsApi.getAPIService();

        mRepoAdapter = new ReposAdapter(this, repoList, null);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setItemAnimator(new DefaultItemAnimator());
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(mRepoAdapter);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etUsername.getText().toString().isEmpty()) {
                    String username = etUsername.getText().toString();
                    requestRepos(username);
                }
            }
        });

    }

    private void requestRepos(String username) {
        pbLoading.setVisibility(View.VISIBLE);

        mApiService.requestRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ResponseRepos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ResponseRepos> responseRepos) {

                        for (int i = 0; i < responseRepos.size(); i++) {
                            String name = responseRepos.get(i).getName();
                            String description = responseRepos.get(i).getDescription();

                            repoList.add(new Repo(name, description));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GithubActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(GithubActivity.this, "Berhasil mengambil data", Toast.LENGTH_SHORT).show();

                        mRepoAdapter = new ReposAdapter(GithubActivity.this, repoList, null);
                        rvRepos.setAdapter(mRepoAdapter);
                        mRepoAdapter.notifyDataSetChanged();
                    }
                });
    }

}
