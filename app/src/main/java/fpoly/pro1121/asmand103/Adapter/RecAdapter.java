package fpoly.pro1121.asmand103.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.pro1121.asmand103.Model.Product;
import fpoly.pro1121.asmand103.R;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    ArrayList<Product> list = new ArrayList();
    Context context;

    public RecAdapter(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product item = list.get(position);
        holder.txt_nameProd.setText(item.getName());
        holder.txt_desProd.setText(item.getDescription());
        holder.txt_priceProd.setText(String.valueOf(item.getPrice()));
        Glide.with(this.context)
                .load("http://172.21.64.1:3000/"+item.getImage())
                .into(holder.img_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nameProd , txt_desProd ,txt_priceProd;
        Button btn_updateProd , btn_deleteProd;
        ImageView img_image;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_nameProd = itemView.findViewById(R.id.txt_nameProd);
            txt_desProd = itemView.findViewById(R.id.txt_desProd);
            txt_priceProd = itemView.findViewById(R.id.txt_priceProd);
            btn_updateProd = itemView.findViewById(R.id.btn_updateProd);
            btn_deleteProd = itemView.findViewById(R.id.btn_deleteProd);
            img_image = itemView.findViewById(R.id.img_image);
        }
    }
}
