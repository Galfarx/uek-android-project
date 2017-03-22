package pl.dkubis.solarsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolarObjectsFragment extends Fragment implements SolarObjectsAdapter.SolarObjectClickedListener {


    public static final String OBJECTS_KEY = "objects";
    @Bind(R.id.objectsRecycleView)
    RecyclerView objectsRecycleView;

    public SolarObjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_solar_objects, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SolarObject[] objects = (SolarObject[]) getArguments().getSerializable(OBJECTS_KEY);

        objectsRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        SolarObjectsAdapter adapter = new SolarObjectsAdapter(objects);
        adapter.setSolarObjectClickedListener(this);
        objectsRecycleView.setAdapter(adapter);

    }

    public static SolarObjectsFragment newInstance(SolarObject[] objects) {
        SolarObjectsFragment fragment = new SolarObjectsFragment();
        Bundle args = new Bundle();

        args.putSerializable(OBJECTS_KEY, objects);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void solarObjectClicked(SolarObject solarObject) {
        SolarObjectActivity.start(getActivity(), solarObject);
    }
}