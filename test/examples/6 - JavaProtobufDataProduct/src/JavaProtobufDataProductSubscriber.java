import java.util.logging.Formatter;

import com.aphysci.gravity.GravityDataProduct;
import com.aphysci.gravity.GravitySubscriber;
import com.aphysci.gravity.swig.GravityNode;
import com.aphysci.gravity.swig.Log;
import com.aphysci.gravity.swig.Log.LogLevel;


public class JavaProtobufDataProductSubscriber {

	class SimpleGravityCounterSubscriber implements GravitySubscriber
	{
		@Override
		public void subscriptionFilled(GravityDataProduct dataProduct)
		{
			//Get the protobuf object from the message
			BasicCounterDataProduct.BasicCounterDataProductPB counterDataPB = new BasicCounterDataProduct.BasicCounterDataProductPB(true);
			dataProduct.populateMessage(counterDataPB);
			
			//Process the message
			Log.message(String.format("Current Count: %d", counterDataPB.getCount()));
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		GravityNode gn;
		//Initialize gravity, giving this node a componentID.  
		gn.init("SimpleGravityComponentID2");

		//Tell the logger to also log to the console.  
		Log.initAndAddConsoleLogger(LogLevel.MESSAGE);
		
		//Declare an object of type SimpleGravityCounterSubscriber (this also initilizes the total count to 0).  
		SimpleGravityCounterSubscriber counterSubscriber;
		//Subscribe a SimpleGravityCounterSubscriber to the counter data product.  
		gn.subscribe("BasicCounterDataProduct", counterSubscriber); 

		//Wait for us to exit (Ctrl-C or being killed).  
		gn.waitForExit();

		//Currently this will never be hit because we will have been killed (unfortunately).  
		//But this shouldn't make a difference because the OS should close the socket and free all resources.  
		gn.unsubscribe("BasicCounterDataProduct", counterSubscriber);	


	}


}
