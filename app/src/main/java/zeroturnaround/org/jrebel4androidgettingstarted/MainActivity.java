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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";

    private RecyclerView eventsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("events");
        myRef.keepSynced(true);

        eventsRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.meetup.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MeetupService meetupService = retrofit.create(MeetupService.class);
        Call<Results> call =
                meetupService.getEventsWithPizza();

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results results = response.body();
                Log.d(TAG, "Event count from API: " + results.getEvents().size());
                final List<Event> eventsFromRest = results.getEvents();
                EventsAdapter adapter = new EventsAdapter(eventsFromRest);
                eventsRecyclerView.setAdapter(adapter);

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Event> cleanEventArrayList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Event event = postSnapshot.getValue(Event.class);


                            Iterator<Event> iter = eventsFromRest.iterator();

                            while (iter.hasNext()) {
                                Event nextEvent = iter.next();
                                if (event.getId().equals(nextEvent.getId()))
                                    iter.remove();
                            }


                        }
                        Log.d(TAG, "After cleaning we have this many events: " + eventsFromRest.size());
                        EventsAdapter adapter = new EventsAdapter(eventsFromRest);
                        eventsRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
            View quoteView = inflater.inflate(R.layout.event_choose_item, parent, false);

            return new EventsAdapter.ViewHolder(quoteView);
        }

        @Override
        public void onBindViewHolder(EventsAdapter.ViewHolder viewHolder, int position) {
            Event event = events.get(position);

            viewHolder.author.setText(event.getName());
            viewHolder.group.setText(event.getGroup().getName());

            if (null != event.getDescription()) {

                String description = event.getDescription().replaceAll("pizza", "<font color='red'>" + "PIZZA" + "</font>");
                description = description.replaceAll("Pizza", "<font color='red'>" + "PIZZA" + "</font>");
                description = description.replaceAll("provide", "<font color='red'>" + "PROVIDE" + "</font>");
                description = description.replaceAll("provided", "<font color='red'>" + "PROVIDED" + "</font>");

                viewHolder.quote.setText(Html.fromHtml(description));
            }

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
            private TextView group;
            private TextView quote;
            private TextView link;
            private Button addEvent;
            private Button removeEvent;


            public ViewHolder(View itemView) {
                super(itemView);

                author = (TextView) itemView.findViewById(R.id.quote_author);
                group = (TextView) itemView.findViewById(R.id.event_group_name);
                quote = (TextView) itemView.findViewById(R.id.quote_text);
                link = (TextView) itemView.findViewById(R.id.quote_link);
                addEvent = (Button) itemView.findViewById(R.id.add_event);
                removeEvent = (Button) itemView.findViewById(R.id.remove_event);

                addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event event = getItem(getAdapterPosition());
                        event.setFood(true);

                        String eventName = event.getName();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("events");

                        myRef.push().setValue(event);
                        Toast.makeText(v.getContext(), eventName + " added!",
                                Toast.LENGTH_SHORT).show();

                    }
                });

                removeEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event event = getItem(getAdapterPosition());
                        event.setFood(false);

                        String eventName = event.getName();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("events");

                        myRef.push().setValue(event);

                        Toast.makeText(v.getContext(), eventName + " removed!",
                                Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }
    }
}
