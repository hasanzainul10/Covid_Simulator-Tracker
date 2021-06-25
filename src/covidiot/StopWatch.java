package covidiot;

import java.util.Date;

public class StopWatch
{
	

	private transient long startTime;
	

	private transient long endTime;
	

	private transient long elapsedTime;
	
	public StopWatch(final long startTime, final long endTime)
	{
		super();
		
		this.startTime = startTime;
		this.endTime = endTime;
		
		this.elapsedTime = endTime - startTime;
	}
	
	public StopWatch(final Date startTime, final Date endTime)
	{
		super();
		
		this.startTime = startTime.getTime();
		this.endTime = endTime.getTime();
		
		this.elapsedTime = this.endTime - this.startTime;
	}
	
	public StopWatch()
	{
		super();
		
		this.startTime = System.currentTimeMillis();
	}
	
	public void reset()
	{
		this.startTime = System.currentTimeMillis();
		this.endTime = 0;
	}
	
	@Override
	public String toString()
	{
		boolean isEndTimeNotSet = false; 
                
		if (this.endTime == 0)
		{
			this.endTime = System.currentTimeMillis();
			isEndTimeNotSet = true;
		}
		this.elapsedTime = this.endTime - this.startTime;
		
		final StringBuilder builder = new StringBuilder();
		
		builder.append("Timer [startTime = ");
		builder.append(startTime);
		builder.append(" ms, endTime = ");
		builder.append(endTime);
		builder.append(" ms, elapsedTime = ");
		builder.append(elapsedTime);
		builder.append(" ms]");
		
		if (isEndTimeNotSet)
		{
			this.endTime = 0;
		}
		return builder.toString();
	}
	
	public long getStartTime()
	{
		return startTime;
	}
	
	public long getEndTime()
	{
		return endTime;
	}
	
	public long getElapsedDay()
	{
		this.endTime = System.currentTimeMillis();
		
		return ((elapsedTime = endTime - startTime)/1008) + 1;
	}
        
        public String getElapsedHour()
        {
            this.endTime = System.currentTimeMillis();
            
            elapsedTime = endTime - startTime;
            
            if((elapsedTime/42)%24 <10)
                return "0" + (elapsedTime/42)%24; 
            else
                return "" + (elapsedTime/42)%24; 
        }
        
        public String getElapsedMinute()
        {
            this.endTime = System.currentTimeMillis();
            
            elapsedTime = endTime - startTime;
            
            double remainder = (elapsedTime%42);
            
            
            
            if((remainder/0.7)%60 <10)
                return "0" + (long)(remainder/0.7)%60; 
            else
                return "" + (long)(remainder/0.7)%60; 
        }    
}        

