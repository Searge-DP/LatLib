package latmod.lib.config;

import com.google.gson.*;
import latmod.lib.*;
import latmod.lib.util.IntBounds;

public class ConfigEntryInt extends ConfigEntry
{
	private int value;
	public IntBounds bounds;
	
	public ConfigEntryInt(String id, IntBounds b)
	{
		super(id);
		bounds = (b == null) ? new IntBounds(0) : b;
		set(bounds.defValue);
	}
	
	public PrimitiveType getType()
	{ return PrimitiveType.INT; }
	
	public void set(int v)
	{ value = bounds.getVal(v); }
	
	public int get()
	{ return value; }
	
	public void add(int i)
	{ set(get() + i); }
	
	public final void setJson(JsonElement o)
	{ set((o == null || o.isJsonNull()) ? bounds.defValue : o.getAsInt()); }
	
	public final JsonElement getJson()
	{ return new JsonPrimitive(get()); }
	
	public void write(ByteIOStream io)
	{ io.writeInt(get()); }
	
	public void read(ByteIOStream io)
	{ set(io.readInt()); }
	
	public void writeExtended(ByteIOStream io)
	{
		write(io);
		io.writeInt(bounds.defValue);
		io.writeInt(bounds.minValue);
		io.writeInt(bounds.maxValue);
	}
	
	public void readExtended(ByteIOStream io)
	{
		read(io);
		int def = io.readInt();
		int min = io.readInt();
		int max = io.readInt();
		bounds = new IntBounds(def, min, max);
	}
	
	public String getMinValue()
	{
		if(bounds.minValue == Integer.MIN_VALUE) return null;
		else return Integer.toString(bounds.minValue);
	}
	
	public String getMaxValue()
	{
		if(bounds.maxValue == Integer.MAX_VALUE) return null;
		else return Integer.toString(bounds.maxValue);
	}
	
	public String getAsString()
	{ return Integer.toString(get()); }
	
	public boolean getAsBoolean()
	{ return get() == 1; }
	
	public int getAsInt()
	{ return get(); }
	
	public double getAsDouble()
	{ return get(); }
	
	public String getDefValue()
	{ return Integer.toString(bounds.defValue); }
}