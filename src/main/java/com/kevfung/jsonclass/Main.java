package com.kevfung.jsonclass;

public class Main
{
    String humidity;
    String pressure;
    String temp_max;
    String temp_min;
    String temp;

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public String getPressure ()
    {
        return pressure;
    }

    public void setPressure (String pressure)
    {
        this.pressure = pressure;
    }

    public String getTemp_max ()
    {
        return temp_max;
    }

    public void setTemp_max (String temp_max)
    {
        this.temp_max = temp_max;
    }

    public String getTemp_min ()
    {
        return temp_min;
    }

    public void setTemp_min (String temp_min)
    {
        this.temp_min = temp_min;
    }

    public String getTemp ()
    {
        return temp;
    }

    public void setTemp (String temp)
    {
        this.temp = temp;
    }

    @Override
    public String toString()
    {
        return "Main: [humidity = "+humidity+", pressure = "+pressure+", temp_max = "+temp_max+", temp_min = "+temp_min+", temp = "+temp+"]";
    }
}