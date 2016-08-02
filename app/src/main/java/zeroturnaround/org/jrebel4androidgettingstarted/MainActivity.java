package zeroturnaround.org.jrebel4androidgettingstarted;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventsRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        MeetupService meetupService = MeetupService.retrofit.create(MeetupService.class);
        Call<Results> call =
                meetupService.getEventsWithPizza();

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results results = response.body();
                Log.d("MainAct", "onResponse: " + results.getQuotes().get(0));
                EventsAdapter adapter = new EventsAdapter(results.getQuotes());
                eventsRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

        private List<Event> events;


        public EventsAdapter(List<Event> events) {
            setList(events);
        }

        @Override
        public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View quoteView = inflater.inflate(R.layout.item_quote, parent, false);

            return new EventsAdapter.ViewHolder(quoteView);
        }

        @Override
        public void onBindViewHolder(EventsAdapter.ViewHolder viewHolder, int position) {
            Event event = events.get(position);

            viewHolder.author.setText(event.getName());

            String description = event.getDescription().replaceAll("pizza","<font color='red'>"+"pizza"+"</font>");
            description = description.replaceAll("Pizza","<font color='red'>"+"Pizza"+"</font>");

            viewHolder.quote.setText(Html.fromHtml(description));

            viewHolder.link.setText(event.getEvent_url());

        }

        private void setList(List<Event> quotes) {
            events = checkNotNull(quotes);
        }

        public void replaceData(List<Event> quotes){
            setList(quotes);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public Event getItem(int position) {
            return events.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView author;
            private TextView quote;
            private TextView link;


            public ViewHolder(View itemView) {
                super(itemView);

                author = (TextView) itemView.findViewById(R.id.quote_author);
                quote = (TextView) itemView.findViewById(R.id.quote_text);
                link = (TextView) itemView.findViewById(R.id.quote_link);

            }

        }
    }
}
