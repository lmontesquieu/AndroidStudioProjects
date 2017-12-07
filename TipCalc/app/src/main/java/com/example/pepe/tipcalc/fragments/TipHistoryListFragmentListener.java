package com.example.pepe.tipcalc.fragments;

import com.example.pepe.tipcalc.model.TipRecord;

/**
 * Created by Pepe on 10/3/2016.
 */

public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
