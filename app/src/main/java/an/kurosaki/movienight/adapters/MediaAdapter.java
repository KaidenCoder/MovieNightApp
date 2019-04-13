package an.kurosaki.movienight.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import an.kurosaki.movienight.R;
import an.kurosaki.movienight.model.Media;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private final Context context;
    private List<? extends Media> mediaList;

    public MediaAdapter(Context context, List<? extends Media> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    class MediaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView poster;
        TextView title;
        TextView name;
        TextView date;
        TextView firstAirDate;
        TextView ratingBar;
        TextView language;
        TextView overview;

        MediaViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //TODO Show Alert dialog with overview
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.original_title);
            name = itemView.findViewById(R.id.original_name);
            date = itemView.findViewById(R.id.release_date);
            firstAirDate = itemView.findViewById(R.id.first_air_date);
            overview = itemView.findViewById(R.id.overview);
            ratingBar = itemView.findViewById(R.id.movie_vote_average);
            language = itemView.findViewById(R.id.original_language);
        }

        @Override
        public void onClick(View view) {
            Media media = mediaList.get(getAdapterPosition());
            showOverviewAlertDialog(media.getOverview());
        }
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.media_list_item_layout, viewGroup, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        String posterUrl = "https://image.tmdb.org/t/p/w500"
                + mediaList.get(position).getPosterPath();

        Picasso.with(context)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_poster)
                .into(holder.poster);

        holder.title.setText(mediaList.get(position).getOriginalTitle());
        holder.name.setText(mediaList.get(position).getOriginalName());
        holder.date.setText(mediaList.get(position).getReleaseDate());
        holder.firstAirDate.setText(mediaList.get(position).getFirstAirDate());
        holder.ratingBar.setText(mediaList.get(position).getVoteAverage());
        holder.language.setText(mediaList.get(position).getOriginalLang());
        holder.overview.setText(mediaList.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return (mediaList == null) ?  0 : mediaList.size();
    }

    public void clearData() {
        mediaList.clear();
        notifyDataSetChanged();
    }

    private void showOverviewAlertDialog(String overview) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("OVERVIEW");
        builder.setMessage(overview);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}



