package com.android2ee.formation.view.graphs;

import java.util.ArrayList;
import java.util.List;

import com.jcertif.dieudonne.kimiacup.graphique.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements TensionValuesOwner {
	List<TensionValues> values;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        values=new ArrayList<TensionValues>();
        fillValues();
        setContentView(R.layout.activity_main);
        ((ValueChart)findViewById(R.id.valuesChart)).setParent(this);
        
    }
    
    /**
     * Rempli de données fake pour l'affichage
     */
    private void fillValues() {
    	int min, max;
    	TensionValues current;
    	for(int i=0;i<25;i++) {
    		//matin
    		min=(int)(150*Math.random());
    		max=min+(int)(50*Math.random());
    		current=new TensionValues(min,max,TensionValues.heureJour.matin);
    		values.add(current);
    		//midi
    		min=(int)(150*Math.random());
    		max=min+(int)(50*Math.random());
    		current=new TensionValues(min,max,TensionValues.heureJour.midi);
    		values.add(current);
    		//soir
    		min=(int)(150*Math.random());
    		max=min+(int)(50*Math.random());
    		current=new TensionValues(min,max,TensionValues.heureJour.nuit);
    		values.add(current);
    		//nuit
    		min=(int)(150*Math.random());
    		max=min+(int)(50*Math.random());
    		current=new TensionValues(min,max,TensionValues.heureJour.soir);
    		values.add(current);
    	}
    }
    /**
     * Assesseur aux values
     * @return les values à dessiner
     */
    public List<TensionValues>getValues(){
    	return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
