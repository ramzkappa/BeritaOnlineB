package its.papsi.beritaonlineb.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import its.papsi.beritaonlineb.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewsFragment extends Fragment {

    public AddNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_news, container, false);
    }
}
