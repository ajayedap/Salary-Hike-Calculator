package com.ajay.salhikecalc;


import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SalHikeCalc extends Activity {
	public EditText amtEt,perEt,newSalEt;
	public Button calcBtn,thanksBtn,clearBt;
	public TextView curSalpm,newSalpm;
	private String msgToDisp; 
	private static final int RESULT_DIALOG=0;
	private static final int QUIT_DIALOG=1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v("Ajay", "On Create");
        amtEt=(EditText)this.findViewById(R.id.amtEt);
        amtEt.setText("3.53");
        perEt=(EditText)this.findViewById(R.id.discEt);
        perEt.setText("15");
        newSalEt=(EditText)this.findViewById(R.id.newsalEt);
        curSalpm=(TextView)this.findViewById(R.id.curSalTV);
        newSalpm=(TextView)this.findViewById(R.id.newSalTV);
        
        Button calcBtn=(Button)this.findViewById(R.id.calcBt);
        calcBtn.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		calcAmount();
        	}
        }
        		
        );
      Button clearBtn=(Button)this.findViewById(R.id.clearBt);
      clearBtn.setOnClickListener(new OnClickListener()
      {
    	  public void onClick(View v)
    	  {    		  
    		  handleClearBt();
    	  }
      }
      );
    }
    
    protected Dialog onCreateDialog(int id,Bundle state)
    {
    	AlertDialog ad = null;
    	switch(id)
    	{
    	case RESULT_DIALOG : 
    		AlertDialog.Builder bldrRes=new AlertDialog.Builder(this);
    		bldrRes.setCancelable(false)
    			.setTitle("Final Amount:")
    			.setMessage(msgToDisp)
    			.setPositiveButton("OK", new EmptyOnClickListener());
    	  ad=bldrRes.create();
    	  break;
    	case QUIT_DIALOG :
    		AlertDialog.Builder bldrQit=new AlertDialog.Builder(this);
        	bldrQit.setCancelable(false)
        	.setTitle("Exit?")
        	.setMessage("The app is going to quit!")
        	.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        	{
        		public void onClick(DialogInterface v,int btnId)
        		{
        			SalHikeCalc.this.finish();
        		}
        	}
        	)
        	.setNegativeButton("No, no", new EmptyOnClickListener());
        	ad=bldrQit.create();
        	break;
    	}
    	return ad;
    }
    protected void onPrepareDialog(int id,Dialog dg)
    {
    	switch(id)
    	{
    	case RESULT_DIALOG :
    		AlertDialog ad=(AlertDialog)dg;
    		ad.setMessage(msgToDisp);
    		break;
    	}
    }
    public class EmptyOnClickListener implements android.content.DialogInterface.OnClickListener
    {
    	public void onClick(DialogInterface v,int btnId)
    	{
    		//do nothing
    	}
    }
   
    public void calcAmount()
    {    	
    	try
    	{
 		
		Float newsalc,percc;
		
		if(newSalEt.getText()==null || newSalEt.getText().toString().equals(""))
		{
		   	Float amount=new Float(amtEt.getText().toString());		
			Float perc=new Float(perEt.getText().toString());
			Float val=amount+(amount *(perc/100));
			  DecimalFormat df = new DecimalFormat("##.##");
			  DecimalFormat df1=new DecimalFormat("##,##,###.##");
			newSalEt.setText(df.format(val));
			curSalpm.setText(df1.format((amount*100000)/12)+" / mnth");
			newSalpm.setText(df1.format((val*100000)/12)+" / mnth");
		}
		else if(perEt.getText()==null||perEt.getText().toString().equals(""))
		{
			Float amount=new Float(amtEt.getText().toString());
			Float newsal=new Float(newSalEt.getText().toString());
			Float val=100*(newsal-amount)/amount;
			DecimalFormat df = new DecimalFormat("##.##");
			DecimalFormat df1=new DecimalFormat("##,##,###.##");
			perEt.setText(df.format(val));
			newSalpm.setText(df1.format((newsal*100000)/12)+" /M");
			curSalpm.setText(df1.format((amount*100000)/12)+" /M");
		}	
    	}catch (Exception e)
    	{
    		Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    		
    	}
    }
    public void handleClearBt()
    {
    	amtEt.setText("");
    	perEt.setText("");
    	newSalEt.setText("");
    }
    public void byeBye()
    {       	
    	showDialog(QUIT_DIALOG);
     }
}