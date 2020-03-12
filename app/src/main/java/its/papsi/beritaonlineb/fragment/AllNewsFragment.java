package its.papsi.beritaonlineb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import its.papsi.beritaonlineb.R;
import its.papsi.beritaonlineb.adapter.NewsAdapter;
import its.papsi.beritaonlineb.model.News;

import static its.papsi.beritaonlineb.GlobalVariable.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllNewsFragment extends Fragment {

    private RecyclerView rvList;
    private NewsAdapter newsAdapter;

    public AllNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.rv_list);

        newsAdapter = new NewsAdapter(getContext(), new ArrayList<News>());
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(newsAdapter);

        getData();
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "ambil_berita.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<News> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                News news = new News();
                                news.setTitle(item.getString("judul"));
                                news.setAuthor(item.getString("nama_penulis"));
                                news.setCreatedAt(item.getString("created_at"));
                                news.setImageUrl(item.getString("gambar"));

                                data.add(news);
                            }

                            newsAdapter.setData(data);

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getContext()).add(request);
    }
}
