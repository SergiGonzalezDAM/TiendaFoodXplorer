package com.foodxplorer.tiendafoodxplorer;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class CocineroActivity extends ExpandableListActivity {
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create ArrayList to hold parent Items and Child Items

        // Create Expandable List and set it's properties
            ExpandableListView expandableList = getExpandableListView();
            expandableList.setDividerHeight(2);
            expandableList.setGroupIndicator(null);
            expandableList.setClickable(true);

            // Set the Items of Parent
            setGroupParents();
            // Set The Child Data
            setChildData();

            // Create the Adapter
            MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

            // Set the Adapter to expandableList
            expandableList.setAdapter(adapter);
            expandableList.setOnChildClickListener(this);
        }

        // method to add parent Items
    public void setGroupParents()
    {
        parentItems.add("Pedido 1");
        parentItems.add("Pedido 2");
        parentItems.add("Pedido 3");
        parentItems.add("Pedido 4");
    }

    // method to set child data of each parent
    public void setChildData()
    {

        // Add Child Items for Fruits
        ArrayList<String> child = new ArrayList<String>();
        child.add("Margarita");
        child.add("Barbacoa");
        child.add("4 Quesos");

        childItems.add(child);

        // Add Child Items for Flowers
        child = new ArrayList<String>();
        child.add("Jamón y Queso");

        childItems.add(child);

        // Add Child Items for Animals
        child = new ArrayList<String>();
        child.add("Barbacoa");
        child.add("Tropical");

        childItems.add(child);

        // Add Child Items for Birds
        child = new ArrayList<String>();
        child.add("5 Quesos");
        child.add("Capricho Ibérico");

        childItems.add(child);
    }

}