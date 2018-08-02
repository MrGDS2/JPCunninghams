package build.cunninghams.jp.jpcunninghamsapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import build.cunninghams.jp.jpcunninghamsapp.Interface.ItemClickListener;
import build.cunninghams.jp.jpcunninghamsapp.R;

    public  class MenuViewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public MenuViewholder(View itemView) {
            super(itemView);
            tvMenuName =(TextView) itemView.findViewById(R.id.menu_itemtext);

            ivMenuImage=(ImageView) itemView.findViewById(R.id.menu_itemimage);

            itemView.setOnClickListener(this);
        }


       public TextView tvMenuName;
       public ImageView ivMenuImage;


       private ItemClickListener itemClickListener;


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {

            itemClickListener.OnClick(view,getAdapterPosition(),false);

        }
    }

