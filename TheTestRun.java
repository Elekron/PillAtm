import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.phidgets.PhidgetException;
import com.phidgets.RFIDPhidget;
import com.phidgets.ServoPhidget;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.ServoPositionChangeEvent;
import com.phidgets.event.ServoPositionChangeListener;
import com.phidgets.event.TagGainEvent;
import com.phidgets.event.TagGainListener;
import com.phidgets.event.TagLossEvent;
import com.phidgets.event.TagLossListener;


public class TheTestRun {
	public static void main(String[] args) {
		/*Rfid*/
		RfidRun rfidRun = new RfidRun();
		rfidRun.rfidSet();
		rfidRun.run();
		rfidRun.start();

		/*Servo*/		
		ServoRun servoRun = new ServoRun();
		servoRun.servoSet();
//		for(int i=0;i<8;i++){
//			servoRun.servoGo();
//			System.out.println(RfidRun.rfidBoolean);
//		}

		/*Time*/
//		TimeCalc timeCalc = new TimeCalc();
//		timeCalc.getTime();
		
		
		
		System.out.println(RfidRun.rfidBoolean);
		while(true){
			TimeCalc timeCalc = new TimeCalc();
			timeCalc.getTime();
			if(timeCalc.checkTime()){
				System.out.println("The Time Is Right");
			}
			
			if(RfidRun.rfidBoolean){
				servoRun.servoGo();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

/**
 * TimeCalc code
 * @author andreasbolzyk
 *
 */
class TimeCalc {
	
	private Calendar cal;
	private String time1;
	private String time2 = "08:00:00";
	private String time3 = "13:00:00";
	private String time4 = "18:00:00";
	
	public TimeCalc(){
		cal = Calendar.getInstance();
	}
	
	public void getTime(){
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	time1 = sdf.format(cal.getTime());
//		time1 = "13:00:00";
	}
	
	public boolean checkTime(){
		if(time1.contentEquals(time2)){
			return true;
		}else if(time1.contentEquals(time3)){
			return true;
		}else if(time1.contentEquals(time4)){
			return true;
		}
		return false;
	}
}



class IrRun {

}


/**
 * Rfid code
 * @author andreasbolzyk
 *
 */
class RfidRun extends Thread{

	public static boolean rfidBoolean = false;
	private RFIDPhidget rfid;

	public RfidRun(){
		try {
			rfid = new RFIDPhidget();
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rfidSet(){
		rfid.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae)
			{
				try
				{
					((RFIDPhidget)ae.getSource()).setAntennaOn(true);
					((RFIDPhidget)ae.getSource()).setLEDOn(true);
				}
				catch (PhidgetException ex) { }
				System.out.println("attachment of " + ae);
			}
		});

		rfid.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae)
			{
				try
				{
					((RFIDPhidget)ae.getSource()).setAntennaOn(true);
					((RFIDPhidget)ae.getSource()).setLEDOn(true);
				}
				catch (PhidgetException ex) { }
				System.out.println("attachment of " + ae);
			}
		});

		rfid.addTagGainListener(new TagGainListener()
		{
			public void tagGained(TagGainEvent oe)
			{
				System.out.println("Tag Gained: " +oe.getValue() + " (Proto:"+ oe.getProtocol()+")");
				rfidBoolean = true;
			}
		});

		rfid.addTagLossListener(new TagLossListener()
		{
			public void tagLost(TagLossEvent oe)
			{
				System.out.println(oe);
				rfidBoolean = false;
			}
		});
	}

	public void run(){
		try {
			rfid.openAny();
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("waiting for RFID attachment...");
		try {
			rfid.waitForAttachment(1000);
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println("Serial: " + rfid.getSerialNumber());
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("Outputs: " + rfid.getOutputCount());
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * Servo code 
 * @author andreasbolzyk
 *
 */
class ServoRun {

	private ServoPhidget servo;

	public ServoRun(){
		try {
			servo =  new ServoPhidget();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
	}

	public void servoGo(){
		int k = 0;
		try {
			k = (int) servo.getPosition(0);
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(k==180){
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				servo.setPosition(0, 0);
			} catch (PhidgetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Position: " + servo.getPosition(0));
			} catch (PhidgetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				servo.setPosition(0, k+=45);
			} catch (PhidgetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Position: " + servo.getPosition(0));
			} catch (PhidgetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void servoSet(){
		servo.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		servo.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		servo.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		servo.addServoPositionChangeListener(new ServoPositionChangeListener()
		{
			public void servoPositionChanged(ServoPositionChangeEvent oe)
			{
				System.out.println(oe);
			}
		});

		try {
			servo.openAny();
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("waiting for Servo attachment...");
		try {
			servo.waitForAttachment();
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			servo.setPosition(0, 0);
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}