/**<ul>
 * <li>KimiacupGraphique</li>
 * <li>com.jcertif.dieudonne.kimiacup.graphique</li>
 * <li>6 sept. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.view.graphs;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class ValueChart extends View {
	/******************************************************************************************/
	/** Attributes associated to the data *****************************/
	/******************************************************************************************/
	/**
	 * Main activity
	 */
	private TensionValuesOwner parent;
	/**
	 * The list of values to display
	 */
	List<TensionValues> parentValues;
	/******************************************************************************************/
	/** Attributes associated to canvas **************************************************************************/
	/******************************************************************************************/
	/**
	 * The paint to draw the view
	 */
	private Paint paint = new Paint();
	/**
	 * The paint to draw the view
	 */
	private Paint paintMax = new Paint();
	/**
	 * The paint to draw the view
	 */
	private Paint paintMin = new Paint();
	
	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/


	/**
	 * @param context
	 */
	public ValueChart(Context context) {
		super(context);

	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ValueChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ValueChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/******************************************************************************************/
	/** Calcul **************************************************************************/
	/******************************************************************************************/
	/**
	 * Valeur maximale des données
	 */
	int max=0;
	int dataNumber=0;
	int w=0,h=0,h0;
	/**
	 * The method that is normalizing the data to fille the space
	 */
	private void normalize() {
		//recherche du maximum
		for(TensionValues value:parentValues) {
			Log.e("ValuesChart drawAxes", "value.max " + value.max);
			if(value.max>max) {
				max=value.max;
			}
		}
		//trick
		max=max+20;
		Log.e("ValuesChart drawAxes", "max" + max);
		//recherche de la largeur
		dataNumber=parentValues.size();
	}
	
	/******************************************************************************************/
	/** Drawing **************************************************************************/
	/******************************************************************************************/


	@Override
	public void onDraw(Canvas canvas) {

		 w = canvas.getWidth();
		 h = canvas.getHeight();
		 h0=h-150;
		drawAxes(canvas);
		if (parent != null) {
			drawValues(canvas);

		}
	}
	
	/**
	 * Return the good ordinate according to the height
	 * @param value
	 * @return
	 */
	private int getOrdinate(int value) {
		return h0-(value*h0/max);
	}
	
	/**
	 * Return the good ordinate according to the height
	 * @param value
	 * @return
	 */
	private int getValue(int ordinate) {
		return max-((h0-ordinate)*max/h0);
	}

	/**
	 * @param canvas
	 */
	public void drawValues(Canvas canvas) {
		paint.setARGB(255, 255, 255, 255);
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		Log.e("ValuesChart", "valeur de w " + w + ", valeur de h " + h);
		// canvas.drawRect(0, 0, w, h, paint);
		paintMax.setColor(Color.RED);
		paintMin.setColor(Color.BLUE);
		int x0, yMin0, yMax0, x1, yMin1, yMax1;

		
		int step = w / (parentValues.size()+2) != 0 ? w / (parentValues.size()+2) : 1;
		x0 = 0;
		yMin0 = getOrdinate(parentValues.get(0).min);
		yMax0 = getOrdinate(parentValues.get(0).max);
		yMin1 = getOrdinate(parentValues.get(1).min);
		yMax1 = getOrdinate(parentValues.get(1).max);
		x1 = x0 + step;
		paint.setColor(Color.BLACK);
		paint.setTextSize(10);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		canvas.save();
		canvas.translate(20, 0);
		for (TensionValues value : parentValues) {
			canvas.drawLine(x0, yMin0, x1, yMin1, paintMin);
			canvas.drawLine(x0, yMax0, x1, yMax1, paintMax);
			
			x0 = x1;
			x1 = x0 + step;
			yMin0 = yMin1;
			yMin1 = getOrdinate(value.min);
			yMax0 = yMax1;
			yMax1 = getOrdinate(value.max);
			canvas.drawText(""+value.min, x1, yMin1, paint);
			canvas.drawText(""+value.max, x1, yMax1, paint);
			Log.w("drawValues", "h0 "+h0+"min "+value.min+" yMin1 "+yMin1+" (ho-value.min)= "+(h0-value.min));
			Log.w("drawValues", "h0 "+h0+"max "+value.max+" yMax1 "+yMax1);
			
		}
		//draw the last lines
		canvas.drawLine(x0, yMin0, x1, yMin1, paintMin);
		canvas.drawLine(x0, yMax0, x1, yMax1, paintMax);
	}

	/**
	 * Draw the axes
	 * @param canvas
	 */
	public void drawAxes(Canvas canvas) {
		//definition de la couleur de l'axe
		paint.setARGB(255, 0, 0, 0);
		paint.setStrokeWidth(1);
		Log.e("ValuesChart drawAxes", "valeur de w " + w + ", valeur de h " + h);
		//tracage des axes
		//axe horizontal
		canvas.drawLine(20, 20, 20, h0, paint);
		//axe vertical
		canvas.drawLine(15, h0, w, h0, paint);
		int step = h0 / 10;
		Log.e("ValuesChart drawAxes", "valeur de w " + w + ", valeur de h " + h+" step "+step);
		// then draw the values of the y axes
		// Define how to draw text
		paint.setTextSize(10);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		canvas.save();
		canvas.translate(0, h0);
		for (int i = 0; i < 25; i++) {
			canvas.drawText(""+getValue(step*i), 10, 0, paint);
			canvas.drawLine(20, -5, 25,-5, paint);
			canvas.translate(0, -step);
		}
		canvas.restore();

	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public final void setParent(TensionValuesOwner parent) {
		this.parent = parent;
		 parentValues = parent.getValues();
		//normalize
		normalize();
		// and redraw
		invalidate();
	}

}
