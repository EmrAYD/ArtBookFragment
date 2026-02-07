package com.emrayd.artfragment.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.emrayd.artfragment.model.Art;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ArtDao {
    @Query("SELECT name,id,image FROM Art")
    Flowable<List<Art>> getArtWithNameIdImg();

    @Query("SELECT * FROM Art WHERE id = :id")
    Flowable<Art> getArtById(int id);

    @Insert
    Completable insert(Art art);

    @Delete
    Completable delete(Art art);
}
