package com.emrayd.artfragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.emrayd.artfragment.R;
import com.emrayd.artfragment.adapter.ListAdapter;
import com.emrayd.artfragment.databinding.FragmentListBinding;
import com.emrayd.artfragment.model.Art;
import com.emrayd.artfragment.roomdb.ArtDao;
import com.emrayd.artfragment.roomdb.ArtDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    ListAdapter listAdapter;

    ArtDao artDao;
    ArtDatabase artDatabase;
    private final CompositeDisposable disposable = new CompositeDisposable();



    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        artDatabase= Room.databaseBuilder(requireContext(),ArtDatabase.class,"Arts").build();
        artDao=artDatabase.artDao();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.materialToolbar.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@org.jspecify.annotations.NonNull Menu menu, @org.jspecify.annotations.NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@org.jspecify.annotations.NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.add_art){
                    com.emrayd.artfragment.view.ListFragmentDirections.ActionListFragmentToDetailsFragment action= ListFragmentDirections.actionListFragmentToDetailsFragment("new");
                    Navigation.findNavController(view).navigate(action);
                }
                return false;
            }
        });
        getdata();

    }

    public void getdata(){
        disposable.add(artDao.getArtWithNameIdImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ListFragment.this::handleResponse));
    }

    private void handleResponse(List<Art> arts) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter= new ListAdapter(arts);
        binding.recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        disposable.clear();
    }

}