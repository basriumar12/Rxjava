package com.example.learnrx.TestApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learnrx.R;
import com.example.learnrx.TestApi.model.Repo;

import java.util.List;




public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private static final String TAG = ReposAdapter.class.getSimpleName();

    private Context context;
    private List<Repo> list;
    private AdapterCallback mAdapterCallback;

    public ReposAdapter(Context context, List<Repo> list, AdapterCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo,
                parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo item = list.get(position);

        String name = item.getName();
        String description = item.getDescription();

        holder.tvName.setText(name);
        holder.tvDesc.setText(description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc =itemView.findViewById(R.id.tvDesc);
        }
    }

    public interface AdapterCallback {
        void onRowClicked(int position);
    }
}